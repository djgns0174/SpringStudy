package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;

class MemberRepositoryV1Test {

    MemberRepositoryV1 memberRepository;

    @BeforeEach
    void setUp() throws SQLException {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USER, PASS);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("test");
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        dataSource.setMaximumPoolSize(10);
        memberRepository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("testC", 1000);
        memberRepository.save(member);

        //findMember
        Member findMember = memberRepository.findMember("testC");
        Assertions.assertThat(findMember).isEqualTo(member);

        //update
        Member updateMember = memberRepository.update(member.getMemberId(), 2000);
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(2000);

        //delete
        memberRepository.delete(member.getMemberId());
        Assertions.assertThatThrownBy(()-> memberRepository.findMember(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }

}