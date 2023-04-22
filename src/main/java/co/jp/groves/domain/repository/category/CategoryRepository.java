package co.jp.groves.domain.repository.category;

import co.jp.groves.domain.model.Category;
import co.jp.groves.domain.repository.SqlFinder;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CategoryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SqlFinder sql;

    private final RowMapper<Category> categoryRowMapper;

    CategoryRepository(final NamedParameterJdbcTemplate jdbcTemplate, final SqlFinder sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.sql = sql;
        this.categoryRowMapper = (rs, i) ->
                new Category(rs.getInt("category_id"), rs.getString("category_name"), rs.getInt("parent_id"));
    }

    public List<Category> findAll() {
        return jdbcTemplate.query(
                sql.get("sql/category/findAll.sql"),
                (rs, i) ->
                        new Category(rs.getInt("category_id"), rs.getString("category_name"), rs.getInt("parent_id")));
    }

    public List<Category> findCategories(int parentId) {
        var source = new MapSqlParameterSource().addValue("parentId", parentId);
        return jdbcTemplate.query(sql.get("sql/category/findCategories.sql"), source, categoryRowMapper);
    }

    public List<Category> findSiblings(int categoryId) {
        var source = new MapSqlParameterSource().addValue("categoryId", categoryId);
        return jdbcTemplate.query(sql.get("sql/category/findSiblings.sql"), source, categoryRowMapper);
    }

    public Category findById(int categoryId) {
        var source = new MapSqlParameterSource().addValue("categoryId", categoryId);
        return jdbcTemplate.queryForObject(sql.get("sql/category/findById.sql"), source, categoryRowMapper);
    }
}
