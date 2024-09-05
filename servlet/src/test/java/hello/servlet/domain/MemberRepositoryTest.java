package hello.servlet.domain;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @BeforeEach
    void setUp() {
        memberRepository.clear();
    }

    @Test
    void save(){
        Member member = new Member();
        member.setUsername("joooo");
        member.setAge(24);

        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId());

        assertEquals(member.getUsername(), findMember.getUsername());
    }

    @Test
    void findAll() {
        Member member1 = new Member("asdf", 1);
        Member member2 = new Member("asdfasdf", 2);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(members).hasSize(2);
    }
}