package hello.jdbc.exception.translator;

import hello.jdbc.repository.ex.DuplicatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.springframework.jdbc.support.JdbcUtils.closeConnection;
import static org.springframework.jdbc.support.JdbcUtils.closeStatement;

@Slf4j
public class ExTranslatorV1Test {

    private Repository repository;
    private Service service;

    @BeforeEach
    public void beforeEach() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USER, PASS);
        repository = new Repository(dataSource);
        service = new Service(repository);
    }

    @Test
    void test() {
        service.save("testA", 10000);
        service.save("testA", 10000);
    }

    @RequiredArgsConstructor
    static class Service{

        private final Repository repository;

        public void save(String memberId, int money) {
            try{
                repository.save(memberId, money);
            } catch (DuplicatedException e) {
                log.error("Duplicated exception, 처리로직 실행", e);
                memberId = createdKey(memberId);
                repository.save(memberId, money);
                log.info("중복된 키 오류 복구완료 memberId :{}", memberId);
            }
        }

        String createdKey(String memberId){
            return memberId + "_" + System.currentTimeMillis();
        }
    }


    @RequiredArgsConstructor
    static class Repository {

        private final DataSource dataSource;

        public void save(String memberId, int money) {
            String sql = "insert into member(member_id,money) values(?,?)";

            Connection conn = null;
            PreparedStatement ps = null;

            try{
                conn = dataSource.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, memberId);
                ps.setInt(2, money);
                ps.executeUpdate();

            }catch (SQLException e){
                if (e.getErrorCode() == 23505) {
                    throw new DuplicatedException(e);
                }
            }finally {
                closeStatement(ps);
                closeConnection(conn);
            }
        }
    }
}
