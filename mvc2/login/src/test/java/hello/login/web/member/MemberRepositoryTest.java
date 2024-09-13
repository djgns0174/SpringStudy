package hello.login.web.member;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = new MemberRepository();

    @Test
    void save(){
        Member member = new Member("asdf", "testA", "asdf");
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findAll(){
        Member member = new Member("asdf", "testA", "asdf");
        memberRepository.save(member);

        Member member1 = new Member("asdfasdf", "testB", "asdfasdf");
        memberRepository.save(member1);

        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all).hasSize(2);
    }

    @Test
    void findByLoginId(){
        Member member = new Member("asdf", "testA", "asdf");
        memberRepository.save(member);

        Optional<Member> byLoginId = memberRepository.findByLoginId("asdf");
        Assertions.assertThat(byLoginId).isEqualTo(Optional.of(member));
    }

}