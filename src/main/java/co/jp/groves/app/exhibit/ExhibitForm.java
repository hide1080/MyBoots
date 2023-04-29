package co.jp.groves.app.exhibit;

import co.jp.groves.domain.validation.UploadFileMaxSize;
import co.jp.groves.domain.validation.UploadFileNotEmpty;
import co.jp.groves.domain.validation.UploadFileRequired;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
class ExhibitForm implements Serializable {

    @Size(min = 1, max = 50)
    @NotNull private String goodsName;

    @Size(min = 1, max = 500)
    @NotNull private String description;

    @NotNull @Min(1)
    @Max(1000000)
    private Integer price;

    @NotNull @Min(0)
    @Max(4)
    private Integer state;

    @NotNull @Min(0)
    @Max(2)
    private Integer deliveryCharge;

    @NotNull @Min(0)
    @Max(4)
    private Integer deliveryMethod;

    @NotNull @Min(1)
    @Max(47)
    private Integer deliveryOrigin;

    @NotNull @Min(0)
    @Max(3)
    private Integer deliveryDays;

    @UploadFileRequired
    @UploadFileNotEmpty
    @UploadFileMaxSize(value = 1024 * 1024)
    private MultipartFile image;

    @NotNull private Integer categoryId;

    @NotNull private Integer topCategoryId;

    private String fileName;
}
