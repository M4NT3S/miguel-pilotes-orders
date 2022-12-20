package com.jagaad.miguelpilotesorders.validator;

import com.jagaad.miguelpilotesorders.payload.request.OrderUpdateRequest;
import com.jagaad.miguelpilotesorders.payload.request.SearchOrdersRequest;
import com.jagaad.miguelpilotesorders.payload.request.TakeOrderRequest;
import com.jagaad.miguelpilotesorders.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class InputValidator {

    public static String errorMessage = "";

    public boolean validate(TakeOrderRequest takeOrderRequest) {
        errorMessage = "A field wasn't mapped correctly";
        return false;
    }

    public boolean validate(OrderUpdateRequest orderUpdateRequest) {
        return true;
    }

    public boolean validate(SearchOrdersRequest searchOrdersRequest) { return true; }

    public boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(Constants.REGEX_PHONE_NUMBER);
        return pattern.matcher(phoneNumber).matches();
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(Constants.REGEX_EMAIL);
        return pattern.matcher(email).matches();
    }

    public boolean isValidNameOrSurname(String nameOrUsername) {
        Pattern pattern = Pattern.compile(Constants.REGEX_NAME_SURNAME);
        return pattern.matcher(nameOrUsername).matches();
    }
}
