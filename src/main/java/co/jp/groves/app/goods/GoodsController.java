package co.jp.groves.app.goods;

import static java.util.Objects.*;

import co.jp.groves.domain.model.Account;
import co.jp.groves.domain.model.Cart;
import co.jp.groves.domain.model.Category;
import co.jp.groves.domain.model.OrderLine;
import co.jp.groves.domain.service.category.CategoryService;
import co.jp.groves.domain.service.goods.GoodsNotFoundException;
import co.jp.groves.domain.service.goods.GoodsService;
import co.jp.groves.domain.service.userdetails.ShopUserDetails;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Slf4j
class GoodsController {

    private final GoodsService goodsService;
    private final CategoryService categoryService;
    private final Cart cart;

    GoodsController(final GoodsService goodsService, final CategoryService categoryService, final Cart cart) {
        this.goodsService = goodsService;
        this.categoryService = categoryService;
        this.cart = cart;
    }

    @ModelAttribute("categories")
    List<Category> getCategories() {
        return categoryService.findCategories(0);
    }

    @ModelAttribute("loginUser")
    Account getLoginUser(@AuthenticationPrincipal final ShopUserDetails userDetails) {
        if (isNull(userDetails)) {
            return null;
        }
        return userDetails.getAccount();
    }

    @ModelAttribute
    AddToCartForm addToCartForm() {
        return new AddToCartForm();
    }

    @GetMapping(value = "/")
    String index(@PageableDefault(size = 20) Pageable pageable, Model model) {
        return list(0, pageable, model);
    }

    @GetMapping(value = "/category/{categoryId}")
    String list(@PathVariable Integer categoryId, @PageableDefault(size = 20) Pageable pageable, Model model) {

        if (categoryId > 0) {
            var category = categoryService.findById(categoryId);
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("categoryName", category.categoryName());

            var subCategories = categoryService.findCategories(categoryId);
            if (subCategories.isEmpty()) {
                // サブカテゴリの検索結果が空ということは要求されたカテゴリが末端のカテゴリ
                subCategories = categoryService.findSiblings(categoryId);
                var parentCategory = categoryService.findById(category.parentId());
                model.addAttribute("topCategoryId", parentCategory.categoryId());
                model.addAttribute("topCategoryName", parentCategory.categoryName());
            }
            model.addAttribute("subCategories", subCategories);
        }

        model.addAttribute("page", goodsService.findByCategoryId(categoryId, pageable));

        return "goods/list";
    }

    @GetMapping(value = "/search")
    String search(@RequestParam String keyword, @PageableDefault(size = 20) Pageable pageable, Model model) {

        if (StringUtils.isBlank(keyword)) {
            return index(pageable, model);
        }

        model.addAttribute("page", goodsService.findByKeyword(keyword, pageable));
        model.addAttribute("keyword", keyword);

        return "goods/list";
    }

    @GetMapping(value = "/goods/{goodsId}")
    String detail(@PathVariable Integer goodsId, Model model) {

        var goods = goodsService.findById(goodsId);
        model.addAttribute("goods", goods);
        // 商品が直接属しているのはサブカテゴリ
        model.addAttribute("categoryId", goods.getCategory().categoryId());
        model.addAttribute("categoryName", goods.getCategory().categoryName());
        // サブカテゴリの親カテゴリを取得して表示
        var parentCategory = categoryService.findById(goods.getCategory().parentId());
        model.addAttribute("topCategoryId", parentCategory.categoryId());
        model.addAttribute("topCategoryName", parentCategory.categoryName());

        return "goods/detail";
    }

    @PostMapping(value = "/addToCart")
    String addToCart(
            @Validated AddToCartForm form, BindingResult result, @PageableDefault Pageable pageable, Model model) {

        if (result.hasErrors()) {
            return list(form.getCategoryId(), pageable, model);
        }

        cart.add(OrderLine.builder()
                .goods(goodsService.findById(form.getGoodsId()))
                .quantity(form.getQuantity())
                .status(0)
                .build());

        return "redirect:/order/form";
    }

    @ExceptionHandler(GoodsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleGoodsNotFoundException() {
        return "goods/notFound";
    }
}
