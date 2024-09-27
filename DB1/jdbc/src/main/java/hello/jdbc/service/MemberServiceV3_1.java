package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void account(String fromMemberId, String toMemberId, int amount){

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            bizlogic(fromMemberId, toMemberId, amount);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new IllegalStateException();
        }
    }

    private void bizlogic(String fromMemberId, String toMemberId, int amount) throws SQLException {
        Member fromMember = memberRepository.findMember(fromMemberId);
        Member toMember = memberRepository.findMember(toMemberId);

        int fromMemberMoney = fromMember.getMoney() - amount;
        int toMemberMoney = toMember.getMoney() + amount;

        fromMember.setMoney(fromMemberMoney);
        toMember.setMoney(toMemberMoney);

        memberRepository.update(fromMemberId, fromMemberMoney);
        validation(toMemberId);
        memberRepository.update(toMemberId, toMemberMoney);
    }

    private void validation(String toMemberId) {
        if (toMemberId.equals("ex")) {
            throw new IllegalStateException("이체중 예외발생");
        }
    }
}