package com.jagaad.miguelpilotesorders.validator;

import com.jagaad.miguelpilotesorders.dto.ClientDTO;
import com.jagaad.miguelpilotesorders.dto.OrderDTO;
import com.jagaad.miguelpilotesorders.payload.request.OrderUpdateRequest;
import com.jagaad.miguelpilotesorders.payload.request.SearchOrdersRequest;
import com.jagaad.miguelpilotesorders.payload.request.TakeOrderRequest;
import com.jagaad.miguelpilotesorders.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;
@Component
public class InputValidator {

    public static String errorMessage = "";

    public boolean validate(TakeOrderRequest takeOrderRequest) {
        return validateClient(takeOrderRequest.getClient()) &&
                validateOrders(takeOrderRequest.getOrders());
    }


    public boolean validate(OrderUpdateRequest orderUpdateRequest) {
        return isValidDeliveryAddress(orderUpdateRequest.getOrderDeliveryAddress()) &&
                isValidPilotesQuantity(orderUpdateRequest.getPilotesQuantity());
    }

    public boolean validate(SearchOrdersRequest searchOrdersRequest) {
        return isValidSearchingField(searchOrdersRequest.getFieldSearchingFor());
    }

    private boolean validateOrders(List<OrderDTO> orders) {
        for(OrderDTO order : orders) {
            if(!validateOrder(order)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateOrder(OrderDTO order) {
        return isValidPilotesQuantity(order.getPilotesQuantity()) && isValidDeliveryAddress(order.getDeliveryAddress());
    }


    private boolean validateClient(ClientDTO client) {
        return isValidNameOrSurname(client.getName()) &&
                isValidNameOrSurname(client.getSurname()) &&
                isValidEmail(client.getEmail()) &&
                isValidPhoneNumber(client.getTelephoneNumber());
    }


    private boolean isValidPilotesQuantity(int pilotesQuantity) {
        if(pilotesQuantity % 5 == 0 && pilotesQuantity <= 15) {
            return true;
        }
        errorMessage = "One of the orders has a wrong pilotes quantity inserted, valid quantities are: 5, 10 or 15";
        return false;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(Constants.REGEX_PHONE_NUMBER);
        if(pattern.matcher(phoneNumber).matches()) {
            return true;
        }
        errorMessage = "The phone number used is not valid";
        return false;
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(Constants.REGEX_EMAIL);
        if(pattern.matcher(email).matches()) {
            return true;
        }
        errorMessage = "The email used is not valid";
        return false;
    }

    public boolean isValidNameOrSurname(String nameOrUsername) {
        Pattern pattern = Pattern.compile(Constants.REGEX_NAME_SURNAME_STREET);
        if(pattern.matcher(nameOrUsername).matches()) {
            return true;
        }
        errorMessage = "The name or surname used is not valid, only characters are allowed";
        return false;
    }

    public boolean isValidDeliveryAddress(String deliveryAddress){
        String[] arrayDeliveryAddress = deliveryAddress.split(" ");
        String toponym = arrayDeliveryAddress[0];
        String streetName = arrayDeliveryAddress[1];
        String houseNumber = arrayDeliveryAddress[2];
        if(isValidToponym(toponym) && isValidStreetName(streetName) && isValidHouseNumber(houseNumber)) {
            return true;
        }
        errorMessage = "The delivery address is invalid, it must respect the following format: " +
                " -- toponym streetName houseNumber -- " +
                "The only toponym allowed are: 'via', 'viale', 'piazza' - " +
                "the street name must contain only characters -  " +
                "the houseNumber must be an integer " +
                "*** example valid delivery address: Via Montaditos 15";

        return false;
    }

    public boolean isValidToponym(String toponym){
        switch(toponym.toLowerCase()) {
            case "via", "viale", "piazza":
                return true;
        }
        return false;
    }

    public boolean isValidStreetName(String streetName){
        Pattern pattern = Pattern.compile(Constants.REGEX_NAME_SURNAME_STREET);
        return pattern.matcher(streetName).matches();
    }

    public boolean isValidHouseNumber(String houseNumber) {
        try{
            Integer.parseInt(houseNumber);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean isValidSearchingField(String searchedField) {
        switch(searchedField){
            case "name", "surname", "telephoneNumber", "email":
                return true;
        }
        errorMessage = "The search failed, the field chosen to search for wasn't valid";
        return false;
    }

}
