package com.restfullec.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String name;
    private Date joinDate;

}
