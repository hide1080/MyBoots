/**
 * 以下を参考にした。
 * https://github.com/making/jsug-spring-boot-handson
 */
package co.jp.groves.app.cart;

import co.jp.groves.domain.model.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
class CartController {
    private final Cart cart;

    CartController(final Cart cart) {
        this.cart = cart;
    }

    @ModelAttribute
    CartForm setUpForm() {
        return new CartForm();
    }

    @GetMapping
    String viewCart(Model model) {
        model.addAttribute("orderLines", cart.getOrderLines());
        return "cart/viewCart";
    }

    @PostMapping()
    String removeFromCart(@Validated CartForm cartForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "商品がチェックされていません");
            return viewCart(model);
        }

        cart.remove(cartForm.getLineNo());

        return "redirect:/cart";
    }
}
