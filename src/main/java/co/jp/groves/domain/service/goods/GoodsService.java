package co.jp.groves.domain.service.goods;

import co.jp.groves.domain.model.Category;
import co.jp.groves.domain.model.Goods;
import co.jp.groves.domain.repository.goods.GoodsRepository;
import co.jp.groves.domain.service.category.CategoryService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GoodsService {

    private final GoodsRepository goodsRepository;

    private final CategoryService categoryService;

    GoodsService(final GoodsRepository goodsRepository, final CategoryService categoryService) {
        this.goodsRepository = goodsRepository;
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    //    @Cacheable("goods")
    public Goods findById(int goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(GoodsNotFoundException::new);
    }

    @Transactional(readOnly = true)
    //    @Cacheable("goods")
    public Page<Goods> findByCategoryId(int categoryId, Pageable pageable) {

        List<Category> categories;
        if (categoryId == 0) {
            // 全カテゴリ下の全商品を検索するため全カテゴリを取得する
            categories = categoryService.findAll();
        } else {
            // 特定のカテゴリ下の全商品を検索するためサブカテゴリを取得する
            categories = categoryService.findCategories(categoryId);
        }

        List<Integer> categoryIds = new ArrayList<>();
        for (var c : categories) {
            categoryIds.add(c.categoryId());
        }
        if (categoryIds.isEmpty()) {
            // 引数が末端のカテゴリの場合
            categoryIds.add(categoryId);
        }
        return goodsRepository.findByCategoryId(categoryIds, pageable);
    }

    @Transactional(readOnly = true)
    //  @Cacheable("goods")
    public long countMyTotalExhibition(int accountId) {
        return goodsRepository.countMyTotalExhibition(accountId);
    }

    @Transactional(readOnly = true)
    //  @Cacheable("goods")
    public long countMyExhibiting(int accountId) {
        return goodsRepository.countMyExhibiting(accountId);
    }

    @Transactional(readOnly = true)
    //  @Cacheable("goods")
    public Page<Goods> findMyExhibiting(int accountId, Pageable pageable) {
        return goodsRepository.findMyExhibiting(accountId, pageable);
    }

    @Transactional(readOnly = true)
    //  @Cacheable("goods")
    public Page<Goods> findMyTrading(int accountId, Pageable pageable) {
        return goodsRepository.findMyTrading(accountId, pageable);
    }

    @Transactional(readOnly = true)
    //  @Cacheable("goods")
    public Page<Goods> findMyPurchases(int accountId, Pageable pageable) {
        return goodsRepository.findMyPurchases(accountId, pageable);
    }

    @Transactional(readOnly = true)
    //  @Cacheable("goods")
    public Page<Goods> findByKeyword(String keyword, Pageable pageable) {
        return goodsRepository.findByKeyword(keyword, pageable);
    }
}
