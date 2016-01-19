package com.tap5.hotelbooking.security;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import com.tap5.hotelbooking.annotations.AnonymousAccess;
import com.tap5.hotelbooking.pages.Signin;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * Intercepts the current page request and redirects to the Signin page
 * if login is required. For more understanding read the following tutorial <a
 * href="http://tapestryjava.blogspot.com/2009/12/securing-tapestry-pages-with.html"> Securing
 * Tapestry Pages with annotations </a>
 * 
 * @author karesti
 * @version 1.0
 */
public class AuthenticationFilter implements ComponentRequestFilter
{

    @Inject
    Logger logger;

    private final PageRenderLinkSource renderLinkSource;

    private final ComponentSource componentSource;

    private final Response response;

    private final Authenticator authenticator;

    public AuthenticationFilter(PageRenderLinkSource renderLinkSource,
            ComponentSource componentSource, Response response, Authenticator authenticator)
    {
        this.renderLinkSource = renderLinkSource;
        this.componentSource = componentSource;
        this.response = response;
        this.authenticator = authenticator;
    }

    /**
     * Handle page render requests. For restricted pages, the user must be
     * logged in.
     */
    public void handlePageRender(PageRenderRequestParameters parameters,
            ComponentRequestHandler handler) throws IOException
    {

        String pageName = parameters.getLogicalPageName();
        if (accessPermitted(pageName))
        {
            logger.debug("Page render access allowed for {}", pageName);
            handler.handlePageRender(parameters);
            return;
        }
        logger.debug("Page render DENIED for {}; redirecting to login page", pageName);
        redirectToLoginPage(pageName);
    }

    /**
     * Handle component events. For events on restricted pages, the user must be
     * logged in.
     */
    public void handleComponentEvent(ComponentEventRequestParameters parameters,
            ComponentRequestHandler handler) throws IOException
    {
        String pageName = parameters.getActivePageName();
        if (accessPermitted(pageName))
        {
            logger.debug("Component event access allowed for {}", pageName);
            handler.handleComponentEvent(parameters);
            return; 
        }
        logger.debug("Component event DENIED for {}; redirecting to login page", pageName);
        redirectToLoginPage(pageName);
    }

    /**
     * Determine whether the user is allowed to access the given page. Access
     * is allowed only if the page is annotated with @AnonymousAccess or the
     * user is already logged in.
     * @param pageName the name of the page being requested
     * @return true if access is permitted, false otherwise.
     */
    private boolean accessPermitted(String pageName)
    {
        Component page = componentSource.getPage(pageName);

        // allow access to users who are already logged in
        if (authenticator.isLoggedIn())
        {
            logger.debug("Allowing logged-in access to page {}", pageName);
            return true;
        }

        // allow access to pages annotated with @AnonymousAccess
        if (page.getClass().isAnnotationPresent(AnonymousAccess.class))
        {
            logger.debug("Allowing unauthenticated access to page {}", pageName);
            return true;
        }
        
        // every other request is denied
        logger.debug("Denying access to page {}", pageName);
        return false;
    }

    /**
     * Redirect the user to the sign-in page
     * @param targetPageName the name of the page to send the user <em>after</em>
     * logging in
     * @throws IOException
     */
    private void redirectToLoginPage(String targetPageName) throws IOException
    {
        Link link = renderLinkSource.createPageRenderLinkWithContext(Signin.class, targetPageName);
        response.sendRedirect(link);
    }
}

