package co.jp.groves.domain.repository.prefecture;

import co.jp.groves.domain.model.Prefecture;
import co.jp.groves.domain.repository.SqlFinder;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PrefectureRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SqlFinder sql;

    private final RowMapper<Prefecture> prefectureRowMapper;

    PrefectureRepository(final NamedParameterJdbcTemplate jdbcTemplate, final SqlFinder sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.sql = sql;
        this.prefectureRowMapper =
                (rs, i) -> new Prefecture(rs.getInt("prefecture_id"), rs.getString("prefecture_name"));
    }

    public List<Prefecture> findAll() {
        return jdbcTemplate.query(sql.get("sql/prefecture/findAll.sql"), prefectureRowMapper);
    }

    public Prefecture findById(int prefectureId) {
        SqlParameterSource source = new MapSqlParameterSource().addValue("prefectureId", prefectureId);
        return jdbcTemplate.queryForObject(sql.get("sql/prefecture/findById.sql"), source, prefectureRowMapper);
    }
}
