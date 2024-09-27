package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceV3_1Test {

    private final String MEMBER_A = "memberA";
    private final String MEMBER_B = "memberB";
    private final String MEMBER_EX = "ex";

    MemberServiceV3_1 memberServiceV3_1;
    MemberRepositoryV3 memberRepositoryV3;

    @BeforeEach
    void setUp() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USER, PASS);
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        memberRepositoryV3 = new MemberRepositoryV3(dataSource);
        memberServiceV3_1 = new MemberServiceV3_1(dataSourceTransactionManager, memberRepositoryV3);
    }

    @AfterEach
    void after() throws SQLException {
        memberRepositoryV3.delete(MEMBER_A);
        memberRepositoryV3.delete(MEMBER_B);
        memberRepositoryV3.delete(MEMBER_EX);
    }


    @Test //정상이체
    void accountTest() throws SQLException {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);

        memberRepositoryV3.save(memberA);
        memberRepositoryV3.save(memberB);

        memberServiceV3_1.account(MEMBER_A, MEMBER_B, 2000);

        Member findMemberA = memberRepositoryV3.findMember(MEMBER_A);
        Member findMemberB = memberRepositoryV3.findMember(MEMBER_B);

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

}