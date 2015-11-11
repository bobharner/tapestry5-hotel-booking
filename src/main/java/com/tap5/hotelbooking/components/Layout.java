package com.tap5.hotelbooking.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.pages.Index;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * Layout component for all pages
 */
@Import(stylesheet =
{ "context:/static/style.css" }, library =
{ "context:/static/hotel-booking.js" }) //
public class Layout
{
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    @Inject
    private Authenticator authenticator;

    @Inject
    private Messages messages;

    /**
     * Respond to the user clicking on the "Log Out" link
     */
    @Log
    public Object onActionFromLogout()
    {
        authenticator.logout();
        return Index.class;
    }

    /**
     * Return a "Welcome, John Smith" message
     * @return the message, or an empty string if there is no logged-in user
     */
    public String getWelcomeMessage()
    {
        if (authenticator.isLoggedIn())
        {
            return messages.format("nav.welcome", authenticator.getLoggedUser().getFullname());
        }
        return "";
    }
}
