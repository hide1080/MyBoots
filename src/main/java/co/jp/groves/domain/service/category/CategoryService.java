package co.jp.groves.domain.service.category;

import co.jp.groves.domain.model.Category;
import co.jp.groves.domain.repository.category.CategoryRepository;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable("category")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    //    @Cacheable("category")
    public List<Category> findCategories(int parentId) {
        return categoryRepository.findCategories(parentId);
    }

    @Transactional(readOnly = true)
    //    @Cacheable("category")
    public List<Category> findSiblings(int categoryId) {
        return categoryRepository.findSiblings(categoryId);
    }

    @Transactional(readOnly = true)
    @Cacheable("category")
    public Category findById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
