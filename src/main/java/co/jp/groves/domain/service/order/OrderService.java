package co.jp.groves.domain.service.order;

import co.jp.groves.domain.model.Account;
import co.jp.groves.domain.model.Cart;
import co.jp.groves.domain.model.Order;
import co.jp.groves.domain.model.OrderLines;
import co.jp.groves.domain.repository.goods.GoodsRepository;
import co.jp.groves.domain.repository.order.OrderRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SerializationUtils;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final GoodsRepository goodsRepository;

    OrderService(final OrderRepository orderRepository, final GoodsRepository goodsRepository) {
        this.orderRepository = orderRepository;
        this.goodsRepository = goodsRepository;
    }

    public String calcSignature(Cart cart) {
        var serialized = SerializationUtils.serialize(cart.getOrderLines());
        byte[] signature = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            signature = messageDigest.digest(serialized);
        } catch (NoSuchAlgorithmException ignored) {
        }
        return Base64.getEncoder().encodeToString(signature);
    }

    public Order purchase(Account account, Cart cart, String signature) {
        if (cart.isEmpty()) {
            throw new EmptyCartOrderException("買い物カゴに商品が入っていません。");
        }
        if (!Objects.equals(calcSignature(cart), signature)) {
            throw new InvalidCartOrderException("買い物カゴの状態が変わっています。");
        }

        // 買い物カゴから削除するため、ディープコピーしておく
        var orderLines =
                (OrderLines) SerializationUtils.deserialize(SerializationUtils.serialize(cart.getOrderLines()));

        // 商品の販売終了日を更新
        var goods = orderLines.getList().get(0).getGoods();
        goods.setSaleEndDate(LocalDate.now());
        if (goodsRepository.updateForPurchased(goods) == 0) {
            // 別の人に商品を買われてしまったため更新できなかった
            cart.clear();
            throw new EndOfSaleException("商品の販売が終了しています。");
        }

        var order = new Order(null, account.getAccountId(), orderLines, LocalDate.now());
        var ordered = orderRepository.create(order);
        cart.clear();
        return ordered;
    }
}
