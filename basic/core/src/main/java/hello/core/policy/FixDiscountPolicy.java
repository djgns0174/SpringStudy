package hello.core.policy;

import hello.core.domain.Grade;
import hello.core.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements DiscountPolicy {

    private final int fixDiscountPrice = 1000;

    @Override
    public int discount(Member member, int price) {
        Grade grade = member.getGrade();
        if (grade == Grade.VIP) {
            return fixDiscountPrice;
        }
        return 0;
    }
}
