package com.restfullec.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonFilter("UserInfoV2")
public class UserV2 extends User{

    private String grade;
}
