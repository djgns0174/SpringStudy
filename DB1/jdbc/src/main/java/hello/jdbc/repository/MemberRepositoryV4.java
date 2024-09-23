package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV4 implements MemberRepository {

    private final DataSource dataSource;

    public MemberRepositoryV4(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?,?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = getConnection();
            log.info("connection : {} , con.getClass", conn, conn.getClass());
            ps = conn.prepareStatement(sql);
            ps.setString(1, member.getMemberId());
            ps.setInt(2, member.getMoney());
            ps.executeUpdate();
            return member;
        }catch (SQLException e){
            throw new MyDBException(e);
        }finally {
            close(conn, ps, null);
        }

    }

    @Override
    public Member findMember(String memberId) {
        String sql = "select * from member where member_id= ? ";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            log.info("connection : {} , con.getClass", conn, conn.getClass());

            ps = conn.prepareStatement(sql);
            ps.setString(1, memberId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException();
            }
        } catch (SQLException e) {
            throw new MyDBException(e);
        } finally {
            close(conn, ps, rs);
        }
    }

    @Override
    public Member update(String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
             conn = getConnection();
            log.info("connection : {} , con.getClass", conn, conn.getClass());
             ps = conn.prepareStatement(sql);
             ps.setInt(1, money);
             ps.setString(2, memberId);
             ps.executeUpdate();
            return findMember(memberId);
        } catch (SQLException e) {
            throw new MyDBException(e);
        }finally {
            close(conn, ps, rs);
        }
    }

    @Override
    public void delete(String memberId){
        String sql = "delete from member where member_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            log.info("connection : {} , con.getClass", conn, conn.getClass());
            ps = conn.prepareStatement(sql);
            ps.setString(1, memberId);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new MyDBException(e);
        }finally {
            close(conn, ps, null);
        }
    }

    private Connection getConnection() {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        return connection;
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
