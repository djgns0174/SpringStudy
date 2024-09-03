package hello.core.repository;

import hello.core.domain.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Primary
public class MemoryMemberRepository implements MemberRepository {

    private static HashMap<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
