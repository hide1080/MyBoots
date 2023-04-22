package co.jp.groves.app.order;

import static java.util.Objects.*;

import co.jp.groves.domain.model.Account;
import co.jp.groves.domain.model.Cart;
import co.jp.groves.domain.model.UserNotice;
import co.jp.groves.domain.service.notice.NoticeService;
import co.jp.groves.domain.service.order.EmptyCartOrderException;
import co.jp.groves.domain.service.order.EndOfSaleException;
import co.jp.groves.domain.service.order.InvalidCartOrderException;
import co.jp.groves.domain.service.order.OrderService;
import co.jp.groves.domain.service.prefecture.PrefectureService;
import co.jp.groves.domain.service.userdetails.ShopUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
class OrderController {
    private final OrderService orderService;
    private final NoticeService noticeService;
    private final PrefectureService prefectureService;
    private final Cart cart;

    OrderController(
            final OrderService orderService,
            final NoticeService noticeService,
            final PrefectureService prefectureService,
            final Cart cart) {

        this.orderService = orderService;
        this.noticeService = noticeService;
        this.prefectureService = prefectureService;
        this.cart = cart;
    }

    @ModelAttribute("loginUser")
    Account getLoginUser(@AuthenticationPrincipal final ShopUserDetails userDetails) {
        if (isNull(userDetails)) {
            return null;
        }
        return userDetails.getAccount();
    }

    @GetMapping(value = "/form")
    String form(Model model, @AuthenticationPrincipal ShopUserDetails userDetails) {

        if (cart.isEmpty()) {
            model.addAttribute("error", "買い物カゴに商品が入っていません");
            return "redirect:/";
        }

        model.addAttribute("goods", cart.getOrderLines().getList().get(0).getGoods());
        var prefecture = prefectureService.findById(userDetails.getAccount().getPrefecture());
        model.addAttribute("prefectureName", prefecture.prefectureName());

        return "order/form";
    }

    @PostMapping(value = "confirm")
    String confirm(Model model, @AuthenticationPrincipal ShopUserDetails userDetails) {

        if (cart.isEmpty()) {
            model.addAttribute("error", "買い物カゴに商品が入っていません");
            return "redirect:/";
        }

        model.addAttribute("goods", cart.getOrderLines().getList().get(0).getGoods());
        model.addAttribute("signature", orderService.calcSignature(cart));
        var prefecture = prefectureService.findById(userDetails.getAccount().getPrefecture());
        model.addAttribute("prefectureName", prefecture.prefectureName());

        return "order/confirm";
    }

    @PostMapping(value = "purchase")
    String order(
            @AuthenticationPrincipal ShopUserDetails userDetails,
            @RequestParam String signature,
            RedirectAttributes attributes) {

        // 購入記録を登録
        var order = orderService.purchase(userDetails.getAccount(), cart, signature);
        // 出品者へ通知するための情報を登録
        var goods = order.orderLines().getList().get(0).getGoods();
        noticeService.register(new UserNotice(
                null,
                goods.getAccount().getAccountId(),
                NoticeService.NoticeId.Purchased.getId(),
                goods.getGoodsId(),
                NoticeService.Status.Unnotified.getStatus()));
        attributes.addFlashAttribute(order);

        return "redirect:/order/finish";
    }

    @GetMapping(value = "finish")
    String finish() {
        return "order/finish";
    }

    @ExceptionHandler({EmptyCartOrderException.class, InvalidCartOrderException.class, EndOfSaleException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    ModelAndView handleOrderException(RuntimeException e) {
        return new ModelAndView("order/error").addObject("error", e.getMessage());
    }
}
