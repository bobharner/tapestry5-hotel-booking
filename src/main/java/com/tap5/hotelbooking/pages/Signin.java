package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tap5.hotelbooking.annotations.AnonymousAccess;
import com.tap5.hotelbooking.security.AuthenticationException;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * User can sign up on the
 * 
 * @author karesti
 */
@AnonymousAccess
public class Signin
{
	 private final static Logger LOG = LoggerFactory.getLogger(Signin.class);
	 
    @Property
    private String flashmessage;

    @Property
    private String username;

    @Property
    private String password;

    @Property
    String target; // target page name

    @Inject
    private Authenticator authenticator;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    /**
     * Respond to page activation by capturing the "target" path info as the
     * name of the target page (the page to return to after login)
     * @param context the EventContext
     */
    public void onActivate(EventContext context)
    {
        if (context.getCount() > 0)
        {
            target = context.get(String.class, 0);
        }
        else
        {
            target = "Index";
        }
    }

    @Log
    public Object onSubmitFromLoginForm()
    {
        LOG.debug("onSubmitFromLoginForm");
        try
        {
            authenticator.login(username, password);
        }
        catch (AuthenticationException ex)
        {
            // bad username or password entered
            loginForm.recordError(messages.get("error.login"));
            return null;
        }
        // was login successful?
        if (authenticator.isLoggedIn())
        {
            // redirect to the page the user wanted before being sent to the login page
            return target;
        }
        return Index.class;
    }

    public String getFlashMessage()
    {
        return flashmessage;
    }

    public void setFlashMessage(String flashmessage)
    {
        this.flashmessage = flashmessage;
    }

}
