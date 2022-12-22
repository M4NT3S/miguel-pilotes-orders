package com.jagaad.miguelpilotesorders.validator;

import com.jagaad.miguelpilotesorders.dto.ClientDTO;
import com.jagaad.miguelpilotesorders.dto.OrderDTO;
import com.jagaad.miguelpilotesorders.payload.request.OrderUpdateRequest;
import com.jagaad.miguelpilotesorders.payload.request.SearchOrdersRequest;
import com.jagaad.miguelpilotesorders.payload.request.TakeOrderRequest;
import com.jagaad.miguelpilotesorders.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class InputValidator {

    public static String errorMessage = "";

    @Value("${security.validation.username}")
    private String username;

    @Value("${security.validation.password}")
    private String password;


    public boolean validate(TakeOrderRequest takeOrderRequest) {
        return validateClient(takeOrderRequest.getClient()) && validateOrders(takeOrderRequest.getOrders());
    }


    public boolean validate(OrderUpdateRequest orderUpdateRequest) {
        return isValidDeliveryAddress(orderUpdateRequest.getOrderDeliveryAddress(), false) &&
                isValidPilotesQuantity(orderUpdateRequest.getPilotesQuantity()) &&
                isPresentOrderId(orderUpdateRequest.getIdOrderToUpdate());
    }


    public boolean validate(SearchOrdersRequest searchOrdersRequest) {
        return isValidSearchingField(searchOrdersRequest.getFieldSearchingFor());
    }

    public boolean validateCredentials(String username, String password) {
        if(this.username.equals(username) && this.password.equals(password)) {
            return true;
        }
        errorMessage = "Username or password used are wrong or not allowed";
        return false;
    }

    private boolean validateOrders(List<OrderDTO> orders) {
        if(orders == null) {
            errorMessage = "The orders are missing, they must be defined";
            return false;
        }
        for (OrderDTO order : orders) {
            if (!validateOrder(order)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateOrder(OrderDTO order) {
        return isValidPilotesQuantity(order.getPilotesQuantity()) && isValidDeliveryAddress(order.getDeliveryAddress(), true);
    }


    private boolean validateClient(ClientDTO client) {
            if(client == null) {
                errorMessage = "The client it's missing, it must be defined";
                return false;
            }
        return isValidNameOrSurname(client.getName()) && isValidNameOrSurname(client.getSurname()) && isValidEmail(client.getEmail()) && isValidPhoneNumber(client.getTelephoneNumber());
    }

    private boolean isPresentOrderId(String idOrderToUpdate) {
        if(idOrderToUpdate == null) {
            errorMessage = "The order id it's missing, it must be defined";
            return false;
        }
        return true;
    }



    private boolean isValidPilotesQuantity(int pilotesQuantity) {
        if (pilotesQuantity % 5 == 0 && pilotesQuantity <= 15) {
            return true;
        }
        errorMessage = "One of the orders has a wrong pilotes quantity inserted or not defined at all, valid quantities are: 5, 10 or 15 and it must be present";
        return false;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) {
            errorMessage = "The phone number it's missing, it must be specified";
            return false;
        }
        Pattern pattern = Pattern.compile(Constants.REGEX_PHONE_NUMBER);
        if (pattern.matcher(phoneNumber).matches()) {
            return true;
        }
        errorMessage = "The phone number used is not valid";
        return false;
    }

    public boolean isValidEmail(String email) {
        if(email == null) {
            errorMessage = "The email it's missing, it must be specified";
        }
        Pattern pattern = Pattern.compile(Constants.REGEX_EMAIL);
        if (pattern.matcher(email).matches()) {
            return true;
        }
        errorMessage = "The email used is not valid";
        return false;
    }

    public boolean isValidNameOrSurname(String nameOrUsername) {
        if(nameOrUsername == null) {
            errorMessage = "The name or surname it's missing, it must be specified";
            return false;
        }
        Pattern pattern = Pattern.compile(Constants.REGEX_NAME_SURNAME_STREET);
        if (pattern.matcher(nameOrUsername).matches()) {
            return true;
        }
        errorMessage = "The name or surname used is not valid, only characters are allowed";
        return false;
    }

    public boolean isValidDeliveryAddress(String deliveryAddress, boolean mandatory) {
        if(deliveryAddress == null && mandatory) {
            errorMessage = "The delivery address must be specified, in one of the orders it's missing";
            return false;
        } else if(deliveryAddress == null){
            return true;
        }

        String[] arrayDeliveryAddress = deliveryAddress.split(" ");
        String toponym = arrayDeliveryAddress[0];
        String streetName = arrayDeliveryAddress[1];
        String houseNumber = arrayDeliveryAddress[2];
        if (isValidToponym(toponym) && isValidStreetName(streetName) && isValidHouseNumber(houseNumber)) {
            return true;
        }
        errorMessage = Constants.BAD_ADDRESS_TEXT;
        return false;
    }

    public boolean isValidToponym(String toponym) {
        switch (toponym.toLowerCase()) {
            case "via", "viale", "piazza":
                return true;
        }
        return false;
    }

    public boolean isValidStreetName(String streetName) {
        Pattern pattern = Pattern.compile(Constants.REGEX_NAME_SURNAME_STREET);
        return pattern.matcher(streetName).matches();
    }

    public boolean isValidHouseNumber(String houseNumber) {
        try {
            Integer.parseInt(houseNumber);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidSearchingField(String searchedField) {
        switch (searchedField) {
            case "name", "surname", "telephoneNumber", "email":
                return true;
        }
        errorMessage = "The search failed, the field chosen to search for wasn't valid";
        return false;
    }

}
