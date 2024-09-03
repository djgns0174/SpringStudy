package hello.core.policy;

import hello.core.domain.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}
