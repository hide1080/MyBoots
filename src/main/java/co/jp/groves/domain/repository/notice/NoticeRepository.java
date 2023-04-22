package co.jp.groves.domain.repository.notice;

import co.jp.groves.domain.model.UserNotice;
import co.jp.groves.domain.repository.SqlFinder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class NoticeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JdbcTemplate jdbcTemplateForBatch;

    private final SqlFinder sql;

    private final RowMapper<UserNotice> userNoticeRowMapper;

    NoticeRepository(
            final NamedParameterJdbcTemplate jdbcTemplate,
            final JdbcTemplate jdbcTemplateForBatch,
            final SqlFinder sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplateForBatch = jdbcTemplateForBatch;
        this.sql = sql;
        this.userNoticeRowMapper = (rs, i) -> new UserNotice(
                rs.getInt("user_notice_id"),
                rs.getInt("account_id"),
                rs.getInt("notice_id"),
                rs.getInt("goods_id"),
                rs.getInt("status"));
    }

    public List<UserNotice> findByAccountId(int accountId) {

        var source = new MapSqlParameterSource().addValue("accountId", accountId);

        return jdbcTemplate.query(sql.get("sql/notice/findByAccountId.sql"), source, userNoticeRowMapper);
    }

    public UserNotice create(UserNotice userNotice) {

        var source = new MapSqlParameterSource()
                .addValue("accountId", userNotice.accountId())
                .addValue("noticeId", userNotice.noticeId())
                .addValue("goodsId", userNotice.goodsId())
                .addValue("status", userNotice.status());

        jdbcTemplate.update(sql.get("sql/notice/create.sql"), source);

        return userNotice;
    }

    public List<UserNotice> updateBatch(final List<UserNotice> userNotices) {

        jdbcTemplateForBatch.batchUpdate(sql.get("sql/notice/update.sql"), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                var userNotice = userNotices.get(i);
                ps.setInt(1, userNotice.status());
                ps.setInt(2, userNotice.accountId());
                ps.setInt(3, userNotice.goodsId());
            }

            @Override
            public int getBatchSize() {
                return userNotices.size();
            }
        });

        return userNotices;
    }
}
