package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

class MemberServiceV2Test {

    private final String MEMBER_A = "memberA";
    private final String MEMBER_B = "memberB";
    private final String MEMBER_EX = "ex";

    MemberRepositoryV2 memberRepositoryV2;
    MemberServiceV2 memberServiceV2;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(URL, USER, PASS);
        memberRepositoryV2 = new MemberRepositoryV2(driverManagerDataSource);
        memberServiceV2 = new MemberServiceV2(driverManagerDataSource, memberRepositoryV2);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepositoryV2.delete(MEMBER_A);
        memberRepositoryV2.delete(MEMBER_B);
        memberRepositoryV2.delete(MEMBER_EX);
    }

    @Test //정상이체
    void accountTest() throws SQLException {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);

        memberRepositoryV2.save(memberA);
        memberRepositoryV2.save(memberB);

        memberServiceV2.account(MEMBER_A, MEMBER_B, 2000);

        Member findMemberA = memberRepositoryV2.findMember(MEMBER_A);
        Member findMemberB = memberRepositoryV2.findMember(MEMBER_B);

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    void accountTest2() throws SQLException {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);

        memberRepositoryV2.save(memberA);
        memberRepositoryV2.save(memberEx);

        Assertions.assertThatThrownBy(()->
                memberServiceV2.account(MEMBER_A, MEMBER_B, 2000))
                .isInstanceOf(IllegalStateException.class);

        Member findMemberA = memberRepositoryV2.findMember(MEMBER_A);
        Member findMemberEx = memberRepositoryV2.findMember(MEMBER_EX);

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(10000);
        Assertions.assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }
}