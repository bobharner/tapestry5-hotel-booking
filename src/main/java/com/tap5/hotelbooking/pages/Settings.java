package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.dal.CrudServiceDAO;
import com.tap5.hotelbooking.data.DemoUser;
import com.tap5.hotelbooking.entities.User;
import com.tap5.hotelbooking.security.AuthenticationException;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * Allows the user to modify password
 * 
 * @author karesti
 */
public class Settings
{
    @Inject
    private CrudServiceDAO crudServiceDAO;

    @Inject
    private Messages messages;

    @Inject
    private Authenticator authenticator;
    
    @Inject
    AlertManager alertManager;

    @InjectPage
    private Signin signin;

    @Property
    private String oldPassword;

    @Property
    private String newPassword;

    @Property
    private String verifyPassword;

    @Component
    private Form settingsForm;

    public Object onSuccess() throws AuthenticationException
    {
        // verify that the correct old password was given
        if (!authenticator.verifyPassword(oldPassword))
        {
            settingsForm.recordError(messages.get("error.wrongOldpassword"));
            return null;
        }

        // verify that the 2 new passwords match
        if (!verifyPassword.equals(newPassword))
        {
            settingsForm.recordError(messages.get("error.verifypassword"));
            return null;
        }

        User user = authenticator.getLoggedUser();

        // prevent users from changing the out-of-the-box account passwords
        if (DemoUser.isDemoUser(user.getUsername())) {
            settingsForm.recordError(messages.get("error.unchangeableAccounts"));
            return null;
        }

        // update the password
        user.setPassword(authenticator.encryptPassword(newPassword));
        crudServiceDAO.update(user);

        // display a transient "success" message
        alertManager.alert(Duration.TRANSIENT, Severity.SUCCESS, messages.get("settings.password-changed"));

        return null; // stay on the same page
    }

    public String getUserName()
    {
        return authenticator.getLoggedUser().getUsername();
    }

}
