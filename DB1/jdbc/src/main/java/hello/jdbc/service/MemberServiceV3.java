package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV3 {

    private final DataSource dataSource;
    private final MemberRepositoryV3 repository;

    @Transactional
    public void account(String fromMemberId, String toMemberId, int amount) throws SQLException {
        bizlogic(fromMemberId, toMemberId, amount);
    }

    private void bizlogic(String fromMemberId, String toMemberId, int amount) throws SQLException {
        Member fromMember = repository.findMember(fromMemberId);
        Member toMember = repository.findMember(toMemberId);

        int fromMemberMoney = fromMember.getMoney() - amount;
        int toMemberMoney = toMember.getMoney() + amount;

        fromMember.setMoney(fromMemberMoney);
        toMember.setMoney(toMemberMoney);

        repository.update(fromMemberId, fromMemberMoney);
        validation(toMemberId);
        repository.update(toMemberId, toMemberMoney);
    }

    private void validation(String toMemberId) throws SQLException {
        if (toMemberId.equals("ex")) {
            throw new IllegalStateException("이체중 예외발생");
        }
    }
}
