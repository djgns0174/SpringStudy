package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.StatefulService;
import hello.core.service.MemberService;
import hello.core.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class SingletonTest {
    @Test
    @DisplayName("스프링없는 순수한 DI 컨테이너")
    void test() {
        AppConfig appConfig = new AppConfig();

        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        log.info("memberService1: {}", memberService1);
        log.info("memberService2: {}", memberService2);

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴 적용")
    void singleton(){
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        log.info("singletonService1: {}", singletonService1);
        log.info("singletonService2: {}", singletonService2);

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 DI 컨테이너 싱글톤 제공")
    void spring(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

        AppConfig bean = ac.getBean(AppConfig.class);
        log.info("bean: {}", bean);

//        log.info("memberService1: {}", memberService1);
//        log.info("memberService2: {}", memberService2);

    }

    @Test
    void statefulServiceTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        statefulService1.order(1000);

        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);
        statefulService2.order(2000);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(2000);
    }
}
