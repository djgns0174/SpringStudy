package hello.jdbc.repository;

import hello.jdbc.domain.Member;

public interface MemberRepository {

    Member save(Member member);
    Member findMember(String memberId);
    Member update(String memberId, int money);
    void delete(String memberId);
}
