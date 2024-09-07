package hello.core;

import hello.core.policy.DiscountPolicy;
import hello.core.policy.RateDiscountPolicy;
import hello.core.repository.MemberRepository;
import hello.core.repository.MemoryMemberRepository;
import hello.core.service.MemberService;
import hello.core.service.MemberServiceImpl;
import hello.core.service.OrderService;
import hello.core.service.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService(){
        log.info("Call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService(){
        log.info("Call AppConfig.orderService");
        return new OrderServiceImpl(discountPolicy(), memberService(), memberRepository());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }

    @Bean
    public MemberRepository memberRepository(){
        log.info("Call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public StatefulService statefulService(){
        return new StatefulService();
    }
}
