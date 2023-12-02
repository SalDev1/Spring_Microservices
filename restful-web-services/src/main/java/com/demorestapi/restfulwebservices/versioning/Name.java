package com.demorestapi.restfulwebservices.versioning;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Name {
    private String firstName;
    private String lastName;
}
