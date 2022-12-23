package com.example.tuyendv.constant;

public class Authority {

    public static final String SUPPER_ADMIN = "hasAuthority('SUPER_ADMIN')";
    public static final String ADMIN = "hasAuthority('ADMIN')";
    public static final String USER = "hasAuthority('USER')";

    public static final String SUPPER_ADMIN_AND_ADMIN = "hasAnyAuthority('SUPER_ADMIN','ADMIN')";
    public static final String SUPPER_ADMIN_AND_USER = "hasAnyAuthority('SUPER_ADMIN','USER')";
    public static final String ADMIN_AND_USER = "hasAnyAuthority('ADMIN','USER')";

}
