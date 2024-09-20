package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
class MemberServiceV1Test {

    private final String MEMBER_A = "memberA";
    private final String MEMBER_B = "memberB";
    private final String MEMBER_EX = "ex";

    MemberServiceV1 memberServiceV1;
    MemberRepositoryV1 memberRepositoryV1;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(URL, USER, PASS);
        memberRepositoryV1 = new MemberRepositoryV1(driverManagerDataSource);
        memberServiceV1 = new MemberServiceV1(memberRepositoryV1);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepositoryV1.delete(MEMBER_A);
        memberRepositoryV1.delete(MEMBER_B);
        memberRepositoryV1.delete(MEMBER_EX);
    }

    @Test //정상이체
    void accountTest() throws SQLException {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);

        memberRepositoryV1.save(memberA);
        memberRepositoryV1.save(memberB);

         memberServiceV1.account(MEMBER_A, MEMBER_B, 2000);

        Member findMemberA = memberRepositoryV1.findMember(MEMBER_A);
        Member findMemberB = memberRepositoryV1.findMember(MEMBER_B);

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test //예외발생
    void accountTest1()throws SQLException {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);

        memberRepositoryV1.save(memberA);
        memberRepositoryV1.save(memberEx);

        Assertions.assertThatThrownBy(()->memberServiceV1.account(MEMBER_A, MEMBER_EX, 2000))
                .isInstanceOf(IllegalStateException.class);


        Member findMemberA = memberRepositoryV1.findMember(MEMBER_A);
        Member findMemberEx = memberRepositoryV1.findMember(MEMBER_EX);

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }

}