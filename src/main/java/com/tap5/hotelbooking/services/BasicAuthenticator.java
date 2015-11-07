package com.tap5.hotelbooking.services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;
import org.slf4j.Logger;

import com.tap5.hotelbooking.dal.CrudServiceDAO;
import com.tap5.hotelbooking.dal.QueryParameters;
import com.tap5.hotelbooking.entities.User;
import com.tap5.hotelbooking.security.AuthenticationException;

/**
 * Basic Security Realm implementation
 * 
 * @author karesti
 * @version 1.0
 */
public class BasicAuthenticator implements Authenticator
{

    private static final String PASSWORD_CHARSET = "UTF-8";

    private static final String DIGEST_ALGORITHM = "SHA-512";

    public static final String AUTH_TOKEN = "authToken";

    @Inject
    private CrudServiceDAO crudService;

    @Inject
    private Request request;
    
    @Inject Logger logger;

    @Override
    public void login(String username, String password) throws AuthenticationException
    {
        String digest = encryptPassword(password);

        User user = crudService.findUniqueWithNamedQuery(User.BY_CREDENTIALS, QueryParameters.with(
                "username", username).and("password", digest).parameters());

        if (user == null)
        {
            throw new AuthenticationException("The user doesn't exist");
        }

        request.getSession(true).setAttribute(AUTH_TOKEN, user);
    }

    @Override
    public String encryptPassword(String password) throws AuthenticationException
    {
        MessageDigest digest;
        byte[] result;
        try
        {
            digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new AuthenticationException("No such algorithm: " + DIGEST_ALGORITHM, e);
        }
        try
        {
            result = digest.digest(password.getBytes(PASSWORD_CHARSET));
        }
        catch (UnsupportedEncodingException e)
        {
            throw new AuthenticationException("Unsupported encoding: " + PASSWORD_CHARSET, e);
        }
        return DIGEST_ALGORITHM + "::" + bytesToHex(result);
    }
    
    /**
     * Convert an array of bytes to a hex-encoded string
     * @param input the array of bytes
     * @return the equivalent hex-encoded string
     */
    private static String bytesToHex(byte[] input) {
        try (Formatter form = new Formatter() )
        {
            for (int i = 0; i < input.length; i++)
               form.format("%02x", input[i]);
            return form.toString();
        }
    }

    @Override
    public boolean isLoggedIn()
    {
        Session session = request.getSession(false);
        if (session != null)
        {
            return session.getAttribute(AUTH_TOKEN) != null;
        }
        return false;
    }

    @Override
    public void logout()
    {
        Session session = request.getSession(false);
        if (session != null)
        {
            session.setAttribute(AUTH_TOKEN, null);
            session.invalidate();
        }
    }

    @Override
    public User getLoggedUser()
    {
        User user = null;

        if (isLoggedIn())
        {
            user = (User) request.getSession(true).getAttribute(AUTH_TOKEN);
        }
        else
        {
            throw new IllegalStateException("The user is not logged in.");
        }
        return user;
    }

    @Override
    public boolean verifyPassword(String candidatePassword) throws AuthenticationException
    {
        if (candidatePassword == null)
        {
            return false;
        }
        return encryptPassword(candidatePassword).equals(getLoggedUser().getPassword());
    }

}
