package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void dataSource() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USER, PASS);
        Connection con2 = DriverManager.getConnection(URL, USER, PASS);
        log.info("connection : {}, con.getClass:{}", con1, con1.getClass());
        log.info("connection : {}, con.getClass:{}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {

        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USER, PASS);

        useDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(URL);
        hikariDataSource.setUsername(USER);
        hikariDataSource.setPassword(PASS);
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setPoolName("test");
        
        useDataSource(hikariDataSource);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();

        log.info("connection : {}, con.getClass:{}", con1, con1.getClass());
        log.info("connection : {}, con.getClass:{}", con2, con2.getClass());
    }
}
