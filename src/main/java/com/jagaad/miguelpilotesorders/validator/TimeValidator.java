package com.jagaad.miguelpilotesorders.validator;

import com.jagaad.miguelpilotesorders.utils.Constants;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TimeValidator {

    public boolean stillUpdatable(Timestamp orderRequestTime, Timestamp updateRequestTime) {
        // If the difference between the requested time to update and the time the order request was taken it's minor than
        // the time limit, then the update is valid
        return updateRequestTime.getTime() - orderRequestTime.getTime() < Constants.UPDATE_TIME_LIMIT;
    }

}
