package com.saber.person.soap.api.dto;

import com.saber.person.soap.api.entity.PersonEntity;
import lombok.Data;
import java.util.List;
@Data
public class PersonResponse {
    private List<PersonEntity> persons ;
}
