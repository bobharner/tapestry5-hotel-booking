package com.tap5.hotelbooking.services;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.ImportModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.modules.Bootstrap4Module;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.validator.ValidatorMacro;

import com.tap5.hotelbooking.dal.DataModule;
import com.tap5.hotelbooking.dal.HibernateModule;
import com.tap5.hotelbooking.security.AuthenticationFilter;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@ImportModule(
        { HibernateModule.class, // DAO layer
            DataModule.class,     // Demo data loading
            Bootstrap4Module.class // use Bootstrap 4 (instead of 3)
        })
public class AppModule
{
	
	/**
	 *  Service Control layer
	 *  > Interface of a service / Implementation of the service
	 *  > location: fr.tc.services
	 *  
	 * @param binder
	 */
    public static void bind(ServiceBinder binder)
    {
        binder.bind(Authenticator.class, BasicAuthenticator.class);
    }

    @ApplicationDefaults
    @Contribute(SymbolProvider.class)
    public static void configureTapestryHotelBooking(
            MappedConfiguration<String, String> configuration)
    {

        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,es");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "2.0-SNAPSHOT");
        configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
        configuration.add(SymbolConstants.RESTRICTIVE_ENVIRONMENT, "true");
        configuration.add(SymbolConstants.BEAN_DISPLAY_CSS_CLASS, "bg-light border p-2 dl-horizontal");

        // Generate a random HMAC key for form signing (not cluster safe).
        // Normally it would be better to use a fixed password-like string, but
        // we can't because this file is published as open source software.
        configuration.add(SymbolConstants.HMAC_PASSPHRASE,
                new BigInteger(130, new SecureRandom()).toString(32));

        // use jquery instead of prototype as foundation JS library
        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
        
        // false turns off switching between HTTP and HTTPS (ignoring @Secure
        // annotations), so if app is served under HTTP it will stay that way,
        // and if served under HTTPS it will also stay that way, for all pages
        configuration.add(SymbolConstants.SECURE_ENABLED, "false");

        configuration.add(SymbolConstants.ENABLE_PAGELOADING_MASK, "false");
    }

    @Contribute(ValidatorMacro.class)
    public static void combineValidators(MappedConfiguration<String, String> configuration)
    {
        configuration.add("username", "required, minlength=3, maxlength=15");
        configuration.add("password", "required, minlength=6, maxlength=12");
    }

    @Contribute(ComponentRequestHandler.class)
    public static void contributeComponentRequestHandler(
            OrderedConfiguration<ComponentRequestFilter> configuration)
    {
        configuration.addInstance("RequiresLogin", AuthenticationFilter.class);
    }

    /**
     * Redirect the user to the intended page when browsing through
     * tapestry forms through browser history or over-eager auto-complete
     * Credit to Lenny Primak.
     */
    public RequestExceptionHandler decorateRequestExceptionHandler(
            final ComponentSource componentSource,
            final Response response,
            final RequestExceptionHandler oldHandler)
    {
        return new RequestExceptionHandler()
        {
            @Override
            public void handleRequestException(Throwable exception) throws IOException
            {
                if (exception.getMessage() == null || !exception.getMessage().contains("Forms require that the request method be POST and that the t:formdata query parameter have values"))
                {
                    oldHandler.handleRequestException(exception);
                    return;
                }
                ComponentResources cr = componentSource.getActivePage().getComponentResources();
                Link link = cr.createEventLink("");
                String uri = link.toRedirectURI().replaceAll(":", "");
                response.sendRedirect(uri);
            }
        };
    }
}
