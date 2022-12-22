package com.jagaad.miguelpilotesorders.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

    public final static String REGEX_PHONE_NUMBER = "^3\\d{9}$";

    public final static String REGEX_NAME_SURNAME_STREET = "^[a-zA-Z]+$";

    public final static String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    // This value is expressed in milliseconds, 300000 equals to 5 minutes
    public final static long UPDATE_TIME_LIMIT = 300000;

    public final static double SINGLE_PILOTE_PRICE = 1.33;

    public final static String BAD_ADDRESS_TEXT = "The delivery address is invalid, it must respect the following format: " +
            " -- toponym streetName houseNumber -- " +
            "The only toponym allowed are: 'via', 'viale', 'piazza' - " +
            "the street name must contain only characters -  " +
            "the houseNumber must be an integer " +
            "*** example valid delivery address: Via Montaditos 15";
}
