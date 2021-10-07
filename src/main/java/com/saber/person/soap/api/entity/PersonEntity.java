package com.saber.person.soap.api.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "persons",uniqueConstraints = @UniqueConstraint(name = "nationalUnique",columnNames ={"nationalCode"} ))
@Data
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "firstName",length = 70)
    private String firstName;
    @Column(name = "lastName",length = 90)
    private String lastName;
    @Column(name = "nationalCode",length = 10,unique = true)
    private String nationalCode;
    @Column(name = "age")
    private Integer age;
    @Column(name = "email",length = 30)
    private String email;
}
