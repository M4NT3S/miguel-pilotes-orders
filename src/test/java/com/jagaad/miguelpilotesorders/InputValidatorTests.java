package com.jagaad.miguelpilotesorders;

import com.jagaad.miguelpilotesorders.validator.InputValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InputValidatorTests {

    @Test
    public void phoneNumberValidation() {
        InputValidator inputValidator = new InputValidator();
        Assertions.assertThat(inputValidator.isValidPhoneNumber("3280964336")).isTrue();
        Assertions.assertThat(inputValidator.isValidPhoneNumber("0000000000")).isFalse();
    }

    @Test
    public void emailValidation(){
        InputValidator inputValidator = new InputValidator();
        Assertions.assertThat(inputValidator.isValidEmail("elgranmiguelmontoro@pilotes.com")).isTrue();
        Assertions.assertThat(inputValidator.isValidEmail("elgranmiguelmontoropilotes.com")).isFalse();
    }

    @Test
    public void nameOrSurnameValidation(){
        InputValidator inputValidator = new InputValidator();
        Assertions.assertThat(inputValidator.isValidNameOrSurname("Miguel")).isTrue();
        Assertions.assertThat(inputValidator.isValidNameOrSurname("Montor@")).isFalse();
    }

}
