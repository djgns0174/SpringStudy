package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepositoryV2;

    public void account(String fromMemberId, String toMemberId, int amount) throws SQLException {

        Connection con = dataSource.getConnection();

        try{
            bizlogic(fromMemberId, toMemberId, amount, con);
            con.commit();
        }catch (Exception e){
            con.rollback();
            throw new IllegalStateException();
        }finally {
            release(con);
        }
    }

    private void bizlogic(String fromMemberId, String toMemberId, int amount, Connection con) throws SQLException {
        Member fromMember = memberRepositoryV2.findMember(con, fromMemberId);
        Member toMember = memberRepositoryV2.findMember(con, toMemberId);

        int fromMemberMoney = fromMember.getMoney() - amount;
        int toMemberMoney = toMember.getMoney() + amount;

        fromMember.setMoney(fromMemberMoney);
        toMember.setMoney(toMemberMoney);

        memberRepositoryV2.update(con, fromMemberId, fromMemberMoney);
        validation(con, toMemberId);
        memberRepositoryV2.update(con, toMemberId, toMemberMoney);
    }

    private void validation(Connection con, String toMemberId) throws SQLException {
        if (toMemberId.equals("ex")) {
            con.rollback();
            throw new IllegalStateException("이체중 예외발생");
        }
    }

    private void release(Connection con) throws SQLException {
        if (con != null) {
            con.setAutoCommit(true);
            con.close();
        }
    }
}
