package hello.core.service;

import hello.core.AppConfig;
import hello.core.domain.Grade;
import hello.core.domain.Member;
import hello.core.domain.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class OrderServiceImplTest {

    private MemberService memberService;
    private OrderService orderService;


    @BeforeEach
    void setUp() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        memberService = applicationContext.getBean("memberService", MemberService.class);
        orderService = applicationContext.getBean("orderService", OrderService.class);
    }

    @Test
    void fixOrder(){
        Member member = new Member(1L, "test", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(1L, "itemA", 10000);

        Assertions.assertThat(order.calculateDiscount()).isEqualTo(9000);
    }

    @Test
    void rateOrder(){
        Member member = new Member(1L, "rate", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(1L, "itemA", 20000);

        Assertions.assertThat(order.calculateDiscount()).isEqualTo(18000);
    }

}