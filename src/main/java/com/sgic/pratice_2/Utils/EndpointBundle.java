package com.sgic.pratice_2.Utils;

public class EndpointBundle {
    public static final String BASE_URL = "/api/v1";
    public static final String ID ="/{id}";
    public static final String SEARCH = "/search";

    //    user controller
    public static final String USER = BASE_URL + "/users";
    public static final String USER_ID = USER + ID;

    //    school controller
    public static final String SCHOOL = BASE_URL + "/school";
}
