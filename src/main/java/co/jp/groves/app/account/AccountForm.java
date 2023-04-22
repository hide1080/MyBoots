package co.jp.groves.app.account;

import co.jp.groves.domain.validation.Confirm;
import co.jp.groves.domain.validation.UnusedEmail;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Confirm(field = "password")
class AccountForm implements Serializable {

    @Email
    @Size(min = 1, max = 100)
    @NotNull @UnusedEmail
    private String email;

    @Size(min = 6, max = 30)
    @NotNull private String password;

    @NotNull private String confirmPassword;

    @Size(min = 1, max = 20)
    @NotNull private String nickname;

    @Size(min = 1, max = 20)
    @NotNull private String firstname = "太郎";

    @Size(min = 1, max = 20)
    @NotNull private String lastname = "山田";

    @Size(min = 1, max = 30)
    @NotNull private String firstnameKana = "たろう";

    @Size(min = 1, max = 30)
    @NotNull private String lastnameKana = "やまだ";

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull private LocalDate birthDay = LocalDate.of(1990, 1, 1);

    @NotNull @Pattern(regexp = "[0-9]{2,4}")
    private String phone1 = "012";

    @NotNull @Pattern(regexp = "[0-9]{2,4}")
    private String phone2 = "345";

    @NotNull @Pattern(regexp = "[0-9]{2,4}")
    private String phone3 = "6789";

    @NotNull @Pattern(regexp = "[0-9]{7}")
    private String zip = "1234567";

    @NotNull private Integer prefecture = 13;

    @Size(min = 1, max = 20)
    @NotNull private String address1 = "中央区";

    @Size(min = 1, max = 40)
    @NotNull private String address2 = "自由市場１－２－３";

    @Size(min = 1, max = 40)
    private String address3 = "メゾン・ド・ブーツ１号室";

    @Size(min = 1, max = 500)
    private String description;

    private String prefectureName;
}
