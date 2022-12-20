package com.jagaad.miguelpilotesorders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private String name;
    private String surname;

    private String telephoneNumber;

    private String email;
}
