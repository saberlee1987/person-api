package com.saber.person.soap.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "persons",uniqueConstraints = @UniqueConstraint(name = "nationalUnique",columnNames ={"nationalCode"} ))
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity entity = (PersonEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(firstName, entity.firstName) && Objects.equals(lastName, entity.lastName) && Objects.equals(nationalCode, entity.nationalCode) && Objects.equals(age, entity.age) && Objects.equals(email, entity.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nationalCode, age, email);
    }
}
