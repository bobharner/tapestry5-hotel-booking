package com.tap5.hotelbooking.services;

import com.tap5.hotelbooking.entities.User;
import com.tap5.hotelbooking.security.AuthenticationException;

/**
 * Basic security interface
 * 
 * @author karesti
 */
public interface Authenticator
{

    /**
     * Gets the logged-in user
     * 
     * @return User, the logged in user
     */
    User getLoggedUser();

    /**
     * Checks if the current user is logged in
     * 
     * @return true if the user is logged in
     */
    boolean isLoggedIn();

    /**
     * Logs in the user.
     * 
     * @param username
     * @param password
     * @throws AuthenticationException
     *             throw if an error occurs
     */
    void login(String username, String password) throws AuthenticationException;

    /**
     * Logs out the user
     */
    void logout();

    /**
     * Encrypt the given password, using a one-way hash
     * @param password the original, plain-text password
     * @return the encrypted password, with the digest algorithm and "::" prepended
     */
    String encryptPassword(String password) throws AuthenticationException;

    /**
     * Determine whether the given password matches the one stored for the
     * current user
     * @param password the plain-text password to check
     * @return true if matching, false otherwise
     * @throws AuthenticationException if the encryption fails for some reason
     */
    boolean verifyPassword(String password) throws AuthenticationException;
}
