package co.jp.groves.app.exhibit;

import static java.util.Objects.*;

import co.jp.groves.domain.model.Account;
import co.jp.groves.domain.model.Goods;
import co.jp.groves.domain.service.category.CategoryService;
import co.jp.groves.domain.service.exhibit.ExhibitService;
import co.jp.groves.domain.service.prefecture.PrefectureService;
import co.jp.groves.domain.service.userdetails.ShopUserDetails;
import java.awt.Rectangle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/exhibit")
@SessionAttributes(value = "exhibitForm")
@Slf4j
class ExhibitController {

    private final ExhibitService exhibitService;

    private final CategoryService categoryService;

    private final PrefectureService prefectureService;

    private final String imagesDir;

    private final String imagesGoods;

    private final String imagesTemp;

    private final int imageSize;

    ExhibitController(
            final ExhibitService exhibitService,
            final CategoryService categoryService,
            final PrefectureService prefectureService,
            @Value("${images.dir}") final String imagesDir,
            @Value("${images.goods}") final String imagesGoods,
            @Value("${images.temp}") final String imagesTemp,
            @Value("${image.size}") final int imageSize) {

        this.exhibitService = exhibitService;
        this.categoryService = categoryService;
        this.prefectureService = prefectureService;
        this.imagesDir = imagesDir;
        this.imagesGoods = imagesGoods;
        this.imagesTemp = imagesTemp;
        this.imageSize = imageSize;
    }

    @ModelAttribute("exhibitForm")
    ExhibitForm setupForm() {
        return new ExhibitForm();
    }

    @ModelAttribute("loginUser")
    Account getLoginUser(@AuthenticationPrincipal final ShopUserDetails userDetails) {
        if (isNull(userDetails)) {
            return null;
        }
        return userDetails.getAccount();
    }

    @GetMapping(value = "/form")
    String createForm(
            final ExhibitForm form, @AuthenticationPrincipal final ShopUserDetails userDetails, final Model model) {
        model.addAttribute("topCategories", this.categoryService.findCategories(0));
        model.addAttribute("prefectures", this.prefectureService.findAll());
        form.setDeliveryOrigin(userDetails.getAccount().getPrefecture());
        return "exhibit/form";
    }

    @PostMapping(value = "/confirm")
    String confirm(
            @Validated final ExhibitForm form,
            final BindingResult bindingResult,
            @AuthenticationPrincipal final ShopUserDetails userDetails,
            final Model model) {

        if (bindingResult.hasErrors()) {
            return this.createForm(form, userDetails, model);
        }

        if (!form.getImage().isEmpty()) {
            final var file = form.getImage();
            final var tempDir = /*System.getProperty("user.home")+*/ this.imagesDir + "/" + this.imagesTemp;
            final var extension = FilenameUtils.getExtension(file.getOriginalFilename());
            final var fileName = "%1$09d_%1$019d.%s"
                    .formatted(userDetails.getAccount().getAccountId(), System.currentTimeMillis(), extension);
            try (final var output =
                    new BufferedOutputStream(new FileOutputStream(FilenameUtils.concat(tempDir, fileName)))) {
                final var image =
                        Thumbnails.of(file.getInputStream()).scale(1.0).asBufferedImage();
                var builder = Thumbnails.of(image);
                final var width = image.getWidth();
                final var height = image.getHeight();

                if (height > width) {
                    // 縦の方が大きい場合、縦を横と同じ長さになるように上下をカットして正方形にする
                    builder = builder.sourceRegion(new Rectangle(0, (height - width) / 2, width, width));
                } else {
                    // 横の方が大きい場合
                    builder = builder.sourceRegion(new Rectangle((width - height) / 2, 0, height, height));
                }

                builder.size(this.imageSize, this.imageSize)
                        .outputFormat(extension)
                        .toOutputStream(output);

                final var topCategory = this.categoryService.findById(form.getTopCategoryId());
                final var category = this.categoryService.findById(form.getCategoryId());
                final var prefecture = this.prefectureService.findById(form.getDeliveryOrigin());

                form.setFileName(fileName);
                model.addAttribute("topCategoryName", topCategory.categoryName());
                model.addAttribute("categoryName", category.categoryName());
                model.addAttribute("deliveryOriginName", prefecture.prefectureName());
            } catch (final Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        return "exhibit/confirm";
    }

    @PostMapping(value = "/create")
    String create(
            final ExhibitForm form,
            @AuthenticationPrincipal final ShopUserDetails userDetails,
            final RedirectAttributes attributes)
            throws Exception {

        final var goods = Goods.builder()
                .goodsName(form.getGoodsName())
                .description(form.getDescription())
                .price(form.getPrice())
                .state(form.getState())
                .deliveryCharge(form.getDeliveryCharge())
                .deliveryMethod(form.getDeliveryMethod())
                .deliveryDays(form.getDeliveryDays())
                .image(form.getFileName())
                .thumbnail(form.getFileName())
                .build();

        this.exhibitService.register(
                goods, userDetails.getAccount().getAccountId(), form.getCategoryId(), form.getDeliveryOrigin());

        attributes.addFlashAttribute(goods);

        final var srcDir = /*System.getProperty("user.home") +*/ this.imagesDir + "/" + this.imagesTemp;
        final var srcFile = new File(FilenameUtils.concat(srcDir, form.getFileName()));
        final var destDir = /*System.getProperty("user.home")+*/ this.imagesDir + "/" + this.imagesGoods;
        final var destFile = new File(FilenameUtils.concat(destDir, form.getFileName()));
        FileUtils.copyFile(srcFile, destFile);
        FileUtils.deleteQuietly(srcFile);

        return "redirect:/exhibit/finish";
    }

    @GetMapping(value = "/finish")
    String createFinish(
            final SessionStatus sessionStatus,
            @AuthenticationPrincipal final ShopUserDetails userDetails,
            final Model model) {
        sessionStatus.setComplete();
        model.addAttribute("account", userDetails.getAccount());
        return "exhibit/finish";
    }
}
