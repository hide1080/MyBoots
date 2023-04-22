package co.jp.groves.app.api;

import co.jp.groves.domain.model.Category;
import co.jp.groves.domain.service.category.CategoryService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
class CategoryRestController {
    private final CategoryService categoryService;

    CategoryRestController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/{categoryId}")
    List<Category> getCategories(@PathVariable Integer categoryId) {
        return categoryService.findCategories(categoryId);
    }
}
