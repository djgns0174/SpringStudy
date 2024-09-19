package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 memberRepository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("testC", 1000);
        memberRepository.save(member);

        //findMember
        Member findMember = memberRepository.findMember("testC");
        Assertions.assertThat(findMember).isEqualTo(member);

        //update
        Member updateMember = memberRepository.update(member.getMemberId(), 2000);
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(2000);

        //delete
        memberRepository.delete(member.getMemberId());
        Assertions.assertThatThrownBy(()-> memberRepository.findMember(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }

}