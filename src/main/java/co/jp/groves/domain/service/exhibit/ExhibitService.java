package co.jp.groves.domain.service.exhibit;

import co.jp.groves.domain.model.Goods;
import co.jp.groves.domain.repository.goods.GoodsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExhibitService {

    private final GoodsRepository goodsRepository;

    ExhibitService(final GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public void register(Goods goods, Integer accountId, Integer categoryId, Integer deliveryOrigin) {
        goodsRepository.create(goods, accountId, categoryId, deliveryOrigin);
    }
}
