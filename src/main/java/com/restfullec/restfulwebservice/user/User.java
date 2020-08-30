package com.restfullec.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@JsonIgnoreProperties(value = {"password","ssn"}) // 제외하고싶은 필드값 지정. (jsonIgnore와 동일한 기능)
//@JsonFilter("UserInfo") // controller 에서 호출할 이름.
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;

    @Past // 지금보다 과거 데이터값만 들어갈 수 있음
    @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요.")
    private Date joinDate;

    // null이나 다른 문자로 치환해서 return해야하는 필드값. (보안이 필요한 필드값이라는 가정)
//    @JsonIgnore // 해당 어노테이션이 붙은 필드는 "제외"하고 클라이언트에 전달.
    @ApiModelProperty(notes = "사용자 패스워드를 입력해 주세요.")
    private String password;
    @ApiModelProperty(notes = "사용자 주민번호를 입력해 주세요.")
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    public User(int id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.ssn = ssn;
    }
}
