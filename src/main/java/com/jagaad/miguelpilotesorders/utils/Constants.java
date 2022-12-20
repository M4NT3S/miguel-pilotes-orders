package com.jagaad.miguelpilotesorders.utils;

public class Constants {

    public final static String REGEX_PHONE_NUMBER = "^3\\d{9}$";

    public final static String REGEX_NAME_SURNAME = "^[a-zA-Z]+$";

    public final static String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    // This value is expressed in milliseconds, 300000 equals to 5 minutes
    public final static long UPDATE_TIME_LIMIT = 300000;
}
