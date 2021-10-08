package com.saber.person.soap.api.soap.dto;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",propOrder = {
        "persons",

})
@XmlRootElement(name = "PersonResponse")
public class PersonResponse {
    private List<PersonEntity> persons ;
}
