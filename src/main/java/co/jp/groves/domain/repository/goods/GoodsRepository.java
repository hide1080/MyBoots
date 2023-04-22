package co.jp.groves.domain.repository.goods;

import co.jp.groves.domain.model.Account;
import co.jp.groves.domain.model.Category;
import co.jp.groves.domain.model.Goods;
import co.jp.groves.domain.model.Prefecture;
import co.jp.groves.domain.repository.SqlFinder;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GoodsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SqlFinder sql;

    private final RowMapper<Goods> goodsRowMapper;

    GoodsRepository(final NamedParameterJdbcTemplate jdbcTemplate, final SqlFinder sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.sql = sql;
        this.goodsRowMapper = (rs, i) -> Goods.builder()
                .goodsId(rs.getInt("goods_id"))
                .goodsName(rs.getString("goods_name"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .state(rs.getInt("state"))
                .deliveryCharge(rs.getInt("delivery_charge"))
                .deliveryMethod(rs.getInt("delivery_method"))
                .deliveryDays(rs.getInt("delivery_days"))
                .image(rs.getString("image"))
                .thumbnail(rs.getString("thumbnail"))
                .soldOut(rs.getBoolean("sold_out"))
                .category(new Category(rs.getInt("category_id"), rs.getString("category_name"), rs.getInt("parent_id")))
                .account(Account.builder()
                        .accountId(rs.getInt("account_id"))
                        .nickname(rs.getString("nickname"))
                        .build())
                .prefecture(new Prefecture(rs.getInt("prefecture_id"), rs.getString("prefecture_name")))
                .build();
    }

    public Optional<Goods> findById(int goodsId) {
        var source = new MapSqlParameterSource().addValue("goodsId", goodsId);
        return jdbcTemplate.query(sql.get("sql/goods/findById.sql"), source, goodsRowMapper).stream()
                .findFirst();
    }

    public long countByCategoryId(List<Integer> categoryIds) {
        var source = new MapSqlParameterSource().addValue("categoryIds", categoryIds);
        return jdbcTemplate.queryForObject(sql.get("sql/goods/countByCategoryId.sql"), source, Long.class);
    }

    public Page<Goods> findByCategoryId(List<Integer> categoryIds, Pageable pageable) {
        var source = new MapSqlParameterSource()
                .addValue("categoryIds", categoryIds)
                .addValue("pageSize", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());
        var content = jdbcTemplate.query(sql.get("sql/goods/findByCategoryId.sql"), source, goodsRowMapper);
        var total = countByCategoryId(categoryIds);
        return new PageImpl<>(content, pageable, total);
    }

    public long countMyTotalExhibition(Integer accountId) {
        var source = new MapSqlParameterSource().addValue("accountId", accountId);
        return jdbcTemplate.queryForObject(sql.get("sql/goods/countMyTotalExhibition.sql"), source, Long.class);
    }

    public long countMyExhibiting(Integer accountId) {
        var source = new MapSqlParameterSource().addValue("accountId", accountId);
        return jdbcTemplate.queryForObject(sql.get("sql/goods/countMyExhibiting.sql"), source, Long.class);
    }

    public Page<Goods> findMyExhibiting(Integer accountId, Pageable pageable) {
        var source = new MapSqlParameterSource()
                .addValue("accountId", accountId)
                .addValue("pageSize", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());
        var content = jdbcTemplate.query(sql.get("sql/goods/findMyExhibiting.sql"), source, goodsRowMapper);
        var total = countMyExhibiting(accountId);
        return new PageImpl<>(content, pageable, total);
    }

    public long countMyTrading(Integer accountId) {
        var source = new MapSqlParameterSource().addValue("accountId", accountId);
        return jdbcTemplate.queryForObject(sql.get("sql/goods/countMyTrading.sql"), source, Long.class);
    }

    public Page<Goods> findMyTrading(Integer accountId, Pageable pageable) {
        var source = new MapSqlParameterSource()
                .addValue("accountId", accountId)
                .addValue("pageSize", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());
        var content = jdbcTemplate.query(sql.get("sql/goods/findMyTrading.sql"), source, goodsRowMapper);
        var total = countMyTrading(accountId);
        return new PageImpl<>(content, pageable, total);
    }

    public long countMyPurchases(Integer accountId) {
        var source = new MapSqlParameterSource().addValue("accountId", accountId);
        return jdbcTemplate.queryForObject(sql.get("sql/goods/countMyPurchases.sql"), source, Long.class);
    }

    public Page<Goods> findMyPurchases(Integer accountId, Pageable pageable) {
        var source = new MapSqlParameterSource()
                .addValue("accountId", accountId)
                .addValue("pageSize", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());
        var content = jdbcTemplate.query(sql.get("sql/goods/findMyPurchases.sql"), source, goodsRowMapper);
        var total = countMyPurchases(accountId);
        return new PageImpl<>(content, pageable, total);
    }

    public long countByKeyword(String keyword) {
        var source = new MapSqlParameterSource().addValue("keyword", "%" + keyword + "%");
        return jdbcTemplate.queryForObject(sql.get("sql/goods/countByKeyword.sql"), source, Long.class);
    }

    public Page<Goods> findByKeyword(String keyword, Pageable pageable) {
        var source = new MapSqlParameterSource().addValue("keyword", "%" + keyword + "%");
        var content = jdbcTemplate.query(sql.get("sql/goods/findByKeyword.sql"), source, goodsRowMapper);
        var total = countByKeyword(keyword);
        return new PageImpl<>(content, pageable, total);
    }

    public Goods create(Goods goods, Integer accountId, Integer categoryId, Integer deliveryOrigin) {
        var source = new MapSqlParameterSource()
                .addValue("goodsName", goods.getGoodsName())
                .addValue("description", goods.getDescription())
                .addValue("price", goods.getPrice())
                .addValue("state", goods.getState())
                .addValue("deliveryCharge", goods.getDeliveryCharge())
                .addValue("deliveryMethod", goods.getDeliveryMethod())
                .addValue("deliveryOrigin", deliveryOrigin)
                .addValue("deliveryDays", goods.getDeliveryDays())
                .addValue("image", goods.getImage())
                .addValue("thumbnail", goods.getThumbnail())
                .addValue("accountId", accountId)
                .addValue("categoryId", categoryId);
        jdbcTemplate.update(sql.get("sql/goods/create.sql"), source);
        return goods;
    }

    public int updateForPurchased(Goods goods) {
        var source = new MapSqlParameterSource()
                .addValue("goodsId", goods.getGoodsId())
                .addValue("saleEndDate", Date.valueOf(goods.getSaleEndDate()));
        return jdbcTemplate.update(sql.get("sql/goods/updateForPurchased.sql"), source);
    }
}
