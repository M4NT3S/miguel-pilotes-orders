package com.jagaad.miguelpilotesorders.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {
    private String name;
    private String surname;

    private String telephoneNumber;

    private String email;
}
