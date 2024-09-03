package hello.core.service;

import hello.core.AppConfig;
import hello.core.domain.Grade;
import hello.core.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class MemberServiceImplTest {

    MemberService memberService;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        memberService = (MemberService) applicationContext.getBean("memberService");
    }

    @Test
    void join() {
        Member member = new Member(1L, "test", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findById(member.getId());

        Assertions.assertThat(findMember).isEqualTo(member);
    }

}