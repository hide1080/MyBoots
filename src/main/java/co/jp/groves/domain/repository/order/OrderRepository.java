package co.jp.groves.domain.repository.order;

import co.jp.groves.domain.model.Order;
import co.jp.groves.domain.repository.SqlFinder;
import java.sql.Date;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SqlFinder sql;

    OrderRepository(final NamedParameterJdbcTemplate jdbcTemplate, final SqlFinder sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.sql = sql;
    }

    public Order create(Order order) {
        var source = new MapSqlParameterSource()
                .addValue("orderId", order.orderId())
                .addValue("accountId", order.accountId())
                .addValue("orderDate", Date.valueOf(order.orderDate()));

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql.get("sql/order/create.sql"), source, keyHolder);

        order.orderLines().orderId(keyHolder.getKey().intValue());
        jdbcTemplate.batchUpdate(
                sql.get("sql/orderLine/create.sql"),
                order.orderLines().stream()
                        .map(orderLine -> new MapSqlParameterSource()
                                .addValue("orderId", orderLine.getOrderId())
                                .addValue("lineNo", orderLine.getLineNo())
                                .addValue(
                                        "goodsId",
                                        orderLine.getGoods().getGoodsId().toString())
                                .addValue("quantity", orderLine.getQuantity())
                                .addValue("status", orderLine.getStatus()))
                        .toArray(SqlParameterSource[]::new));

        return new Order(keyHolder.getKey().intValue(), order.accountId(), order.orderLines(), order.orderDate());
    }
}
