package co.jp.groves.domain.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class PasswordData implements Serializable {
    private String plainText;
}
