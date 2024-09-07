package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.repository.MemberRepository;
import hello.core.service.MemberService;
import hello.core.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberServiceImpl = ac.getBean("memberService", MemberService.class);
        OrderService orderServiceImpl = ac.getBean("orderService", OrderService.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

    }
}
