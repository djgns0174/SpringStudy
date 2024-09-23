package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV3;
import hello.jdbc.repository.MemberRepositoryV4;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
@SpringBootTest
class MemberServiceV4Test {

    private final String MEMBER_A = "memberA";
    private final String MEMBER_B = "memberB";
    private final String MEMBER_EX = "ex";

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberServiceV4 memberService;

    @TestConfiguration
    static class Config {

        private final DataSource dataSource;

        public Config(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        public MemberRepository memberRepository() {
            return new MemberRepositoryV4(dataSource);
        }

        @Bean
        public MemberServiceV4 memberService() {
            return new MemberServiceV4(dataSource, memberRepository());
        }
    }

    @Test
    void AOPTest(){
        log.info("memberRepository : {}", memberRepository.getClass());
        log.info("memberService : {}", memberService.getClass());
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @Test //정상이체
    void accountTest() throws SQLException {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        memberService.account(MEMBER_A, MEMBER_B, 2000);

        Member findMemberA = memberRepository.findMember(MEMBER_A);
        Member findMemberB = memberRepository.findMember(MEMBER_B);

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    void accountTest2() {
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);

        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        Assertions.assertThatThrownBy(()->
                        memberService.account(MEMBER_A, MEMBER_B, 2000))
                .isInstanceOf(NoSuchElementException.class);

        Member findMemberA = memberRepository.findMember(MEMBER_A);
        Member findMemberEx = memberRepository.findMember(MEMBER_EX);

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(10000);
        Assertions.assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }
}