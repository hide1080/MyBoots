package co.jp.groves.app.account;

import static java.util.Objects.*;

import co.jp.groves.domain.model.Account;
import co.jp.groves.domain.model.PasswordData;
import co.jp.groves.domain.model.Prefecture;
import co.jp.groves.domain.service.account.AccountService;
import co.jp.groves.domain.service.goods.GoodsService;
import co.jp.groves.domain.service.prefecture.PrefectureService;
import co.jp.groves.domain.service.userdetails.ShopUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
@SessionAttributes(value = "passwordData")
class AccountController {

    private final AccountService accountService;

    private final PrefectureService prefectureService;

    private final GoodsService goodsService;

    AccountController(
            final AccountService accountService,
            final PrefectureService prefectureService,
            final GoodsService goodsService) {

        this.accountService = accountService;
        this.prefectureService = prefectureService;
        this.goodsService = goodsService;
    }

    @ModelAttribute
    AccountForm setupForm() {
        return new AccountForm();
    }

    @ModelAttribute("passwordData")
    PasswordData passwordData() {
        return new PasswordData();
    }

    @ModelAttribute("loginUser")
    Account getLoginUser(@AuthenticationPrincipal final ShopUserDetails userDetails) {
        if (isNull(userDetails)) {
            return null;
        }
        return userDetails.getAccount();
    }

    @GetMapping(value = "/form")
    String form() {
        return "account/form";
    }

    @PostMapping(value = "/confirm")
    String confirm(@Validated AccountForm form, BindingResult bindingResult, PasswordData passwordData) {

        if (bindingResult.hasErrors()) {
            return "account/form";
        }

        passwordData.setPlainText(form.getPassword());
        form.setPassword("dummypassword");
        form.setConfirmPassword("dummypassword");
        Prefecture prefecture = prefectureService.findById(form.getPrefecture());
        form.setPrefectureName(prefecture.prefectureName());

        return "account/confirm";
    }

    @PostMapping(value = "/create")
    String create(
            @Validated AccountForm form,
            BindingResult bindingResult,
            PasswordData passwordData,
            RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            return "account/form";
        } else if (StringUtils.isEmpty(passwordData.getPlainText())) {
            return "account/form";
        }

        var account = Account.builder()
                .nickname(form.getNickname())
                .email(form.getEmail())
                .firstname(form.getFirstname())
                .lastname(form.getLastname())
                .firstnameKana(form.getFirstnameKana())
                .lastnameKana(form.getLastnameKana())
                .birthDay(form.getBirthDay())
                .phone1(form.getPhone1())
                .phone2(form.getPhone2())
                .phone3(form.getPhone3())
                .zip(form.getZip())
                .prefecture(form.getPrefecture())
                .address1(form.getAddress1())
                .address2(form.getAddress2())
                .address3(form.getAddress3())
                .description(form.getDescription())
                .build();

        accountService.register(account, passwordData.getPlainText());
        attributes.addFlashAttribute(account);

        return "redirect:/account/finish";
    }

    @GetMapping(value = "/finish")
    String finish(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "account/finish";
    }

    @GetMapping(value = "/profile/{accountId}")
    String profile(@PathVariable Integer accountId, @PageableDefault(size = 20) Pageable pageable, Model model) {
        setupProfile(accountId, model);
        model.addAttribute("page", goodsService.findMyExhibiting(accountId, pageable));
        model.addAttribute("pageName", "exhibiting");
        return "account/profile";
    }

    @GetMapping(value = "/sold/{accountId}")
    String soldHistory(@PathVariable Integer accountId, @PageableDefault(size = 20) Pageable pageable, Model model) {
        setupProfile(accountId, model);
        model.addAttribute("page", goodsService.findMyTrading(accountId, pageable));
        model.addAttribute("pageName", "sold");
        return "account/profile";
    }

    @GetMapping(value = "/purchaced/{accountId}")
    String purchacedHistory(
            @PathVariable Integer accountId, @PageableDefault(size = 20) Pageable pageable, Model model) {
        setupProfile(accountId, model);
        model.addAttribute("page", goodsService.findMyPurchases(accountId, pageable));
        model.addAttribute("pageName", "purchaced");
        return "account/profile";
    }

    private void setupProfile(Integer accountId, Model model) {
        model.addAttribute("account", accountService.findById(accountId));
        model.addAttribute("exhibitingNum", goodsService.countMyExhibiting(accountId));
        model.addAttribute("totalExhibitNum", goodsService.countMyTotalExhibition(accountId));
    }
}
