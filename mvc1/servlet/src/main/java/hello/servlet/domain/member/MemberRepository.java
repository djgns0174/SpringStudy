package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    private static MemberRepository instance = new MemberRepository();
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    private MemberRepository() {}

    public static MemberRepository getInstance() {
        return instance;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<Member>(store.values());
    }

    public void clear(){
        store.clear();
    }
}
