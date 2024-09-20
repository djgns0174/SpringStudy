package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {
    private final MemberRepositoryV1 repositoryV1;

    public void account(String fromMemberId, String toMemberId, int amount) throws SQLException {

        Member fromMember = repositoryV1.findMember(fromMemberId);
        Member toMember = repositoryV1.findMember(toMemberId);

        int fromMemberMoney = fromMember.getMoney() - amount;
        int toMemberMoney = toMember.getMoney() + amount;

        fromMember.setMoney(fromMemberMoney);
        toMember.setMoney(toMemberMoney);

        repositoryV1.update(fromMemberId, fromMemberMoney);
        validation(toMemberId);
        repositoryV1.update(toMemberId, toMemberMoney);
    }

    private void validation(String toMemberId){
        if (toMemberId.equals("ex")) {
            throw new IllegalStateException("이체중 예외발생");
        }
    }
}
