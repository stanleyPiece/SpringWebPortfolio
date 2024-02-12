package cz.itnetwork.springblog.models.dto.mappers;

import cz.itnetwork.springblog.database.entities.UserEntity;
import cz.itnetwork.springblog.models.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper { //@Mapper - "mapovač" atributů, parametr compnentModel - říká, že chceme vygenerovat implementaci pro framework SpringBoot


    @Mapping(target = "password", ignore = true) //@Mapping chceme-li označit specifický atribut, který nechceme například namapovat
    UserEntity toEntity(UserDTO sourceUser); //metoda pro převedení atributů uživatele z webové aplikace na atributy sloupců záznamu (řádku) v tabulce databáze SQL

}
