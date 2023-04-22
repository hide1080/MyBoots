package co.jp.groves.domain.repository.account;

import co.jp.groves.domain.model.Account;
import co.jp.groves.domain.repository.SqlFinder;
import java.sql.Date;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AccountRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SqlFinder sql;

    private final RowMapper<Account> accountRowMapper;

    AccountRepository(final NamedParameterJdbcTemplate jdbcTemplate, final SqlFinder sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.sql = sql;
        this.accountRowMapper = (rs, i) -> Account.builder()
                .accountId(rs.getInt("account_id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .nickname(rs.getString("nickname"))
                .firstname(rs.getString("firstname"))
                .lastname(rs.getString("lastname"))
                .firstnameKana(rs.getString("firstname_kana"))
                .lastnameKana(rs.getString("lastname_kana"))
                .birthDay(rs.getDate("birth_day").toLocalDate())
                .phone1(rs.getString("phone1"))
                .phone2(rs.getString("phone2"))
                .phone3(rs.getString("phone3"))
                .prefecture(rs.getInt("prefecture"))
                .address1(rs.getString("address1"))
                .address2(rs.getString("address2"))
                .address3(rs.getString("address3"))
                .zip(rs.getString("zip"))
                .description(rs.getString("description"))
                .build();
    }

    public int countByEmail(String email) {
        var source = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.queryForObject(sql.get("sql/account/countByEmail.sql"), source, Integer.class);
    }

    public Optional<Account> findById(Integer id) {
        var source = new MapSqlParameterSource().addValue("accountId", id);
        return jdbcTemplate.query(sql.get("sql/account/findById.sql"), source, accountRowMapper).stream()
                .findFirst();
    }

    public Optional<Account> findByEmail(String email) {
        var source = new MapSqlParameterSource().addValue("email", email);
        return jdbcTemplate.query(sql.get("sql/account/findByEmail.sql"), source, accountRowMapper).stream()
                .findFirst();
    }

    public Account create(Account account) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("email", account.getEmail())
                .addValue("password", account.getPassword())
                .addValue("nickname", account.getNickname())
                .addValue("firstname", account.getFirstname())
                .addValue("lastname", account.getLastname())
                .addValue("firstnameKana", account.getFirstnameKana())
                .addValue("lastnameKana", account.getLastnameKana())
                .addValue("birthDay", Date.valueOf(account.getBirthDay()))
                .addValue("phone1", account.getPhone1())
                .addValue("phone2", account.getPhone2())
                .addValue("phone3", account.getPhone3())
                .addValue("zip", account.getZip())
                .addValue("prefecture", account.getPrefecture())
                .addValue("address1", account.getAddress1())
                .addValue("address2", account.getAddress2())
                .addValue("address3", account.getAddress3())
                .addValue("description", account.getDescription());
        jdbcTemplate.update(sql.get("sql/account/create.sql"), source);
        return account;
    }
}
