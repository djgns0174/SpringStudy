package hello.core.service;

import hello.core.domain.Member;
import hello.core.domain.Order;
import hello.core.policy.DiscountPolicy;
import hello.core.repository.MemberRepository;

public class OrderServiceImpl implements OrderService {

    private final DiscountPolicy discountPolicy;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public OrderServiceImpl(DiscountPolicy discountPolicy, MemberService memberService, MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member findMember = memberService.findById(memberId);
        int discountPrice = discountPolicy.discount(findMember, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    //test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
