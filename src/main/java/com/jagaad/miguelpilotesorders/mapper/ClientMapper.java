package com.jagaad.miguelpilotesorders.mapper;


import com.jagaad.miguelpilotesorders.dto.ClientDTO;
import com.jagaad.miguelpilotesorders.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface ClientMapper {

    public ClientDTO entityToDto(Client client);

    public Client DtoToEntity(ClientDTO clientDTO);

}
