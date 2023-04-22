package co.jp.groves.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account implements Serializable {
    private Integer accountId;
    private String email;
    private String password;
    private String nickname;
    private String firstname;
    private String lastname;
    private String firstnameKana;
    private String lastnameKana;
    private LocalDate birthDay;
    private String phone1;
    private String phone2;
    private String phone3;
    private String zip;
    private Integer prefecture;
    private String address1;
    private String address2;
    private String address3;
    private String description;
}
