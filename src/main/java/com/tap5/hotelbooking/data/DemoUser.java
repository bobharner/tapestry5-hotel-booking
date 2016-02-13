package com.tap5.hotelbooking.data;

/**
 * An enumeration of the demo accounts used by this app.
 */
public enum DemoUser
{
    ACCOUNT_1 ( "Martin Gosling", "gosling@example.com", "gosling", "gosling" ),
    ACCOUNT_2 ( "James Fowler", "fowler@example.com", "fowler", "fowler" );

    String fullname;
    String email;
    String username;
    String password; // unencrypted password (hey, it's not a secret!)

    private DemoUser(String fullname, String email, String username, String password)
    {
        this.fullname = fullname;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getFullname()
    {
        return fullname;
    }

    public String getEmail()
    {
        return email;
    }

    public String getUsername()
    {
        return username;
    }

    /**
     * @return the unencrypted password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Determine whether the given username is the username of one of the demo
     * user accounts
     * @param username the username to check
     * @return true if found, false otherwise
     */
    public static boolean isDemoUser(String username) {
        for (DemoUser one : DemoUser.values()) {
            if (one.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
