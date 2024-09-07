package hello.core.policy;

import hello.core.domain.Grade;
import hello.core.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    private final int rateDiscountPrice = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * rateDiscountPrice / 100;
        }

        return 0;
    }
}
