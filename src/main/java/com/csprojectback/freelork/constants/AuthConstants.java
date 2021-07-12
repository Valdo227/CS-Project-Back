package com.csprojectback.freelork.constants;

public class AuthConstants {

    /**
     * Url authenticated
     */
    public static final String URL_PRIVATE_AUTHENTICATION = "/freelork";


    /**
     * Base Url Authenticated with version
     */
    public static final String URL_PRIVATE_AUTHENTICATION_BASE = "/freelork/api/v1/";

    /**
     * config Url access
     */
    public static final String URL_CONFIG_PRIVATE_AUTHENTICATION = "/freelork/api/**";

    private AuthConstants() {
        throw new IllegalStateException("Constants class");
    }
}
