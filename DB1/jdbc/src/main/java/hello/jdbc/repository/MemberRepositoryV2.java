package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?,?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = dataSource.getConnection();
            log.info("connection : {} , con.getClass", conn, conn.getClass());
            ps = conn.prepareStatement(sql);
            ps.setString(1, member.getMemberId());
            ps.setInt(2, member.getMoney());
            ps.executeUpdate();
            return member;
        }catch (SQLException e){
            throw e;
        }finally {
            close(conn, ps, null);
        }

    }

    public Member findMember(String memberId) throws SQLException {
        String sql = "select * from member where member_id= ? ";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
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
            throw e;
        } finally {
            close(conn, ps, rs);
        }
    }

    public Member findMember(Connection conn, String memberId) throws SQLException {
        String sql = "select * from member where member_id= ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
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
            throw e;
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }
    }

    public Member update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
             conn = dataSource.getConnection();
            log.info("connection : {} , con.getClass", conn, conn.getClass());
             ps = conn.prepareStatement(sql);
             ps.setInt(1, money);
             ps.setString(2, memberId);
             ps.executeUpdate();
            return findMember(memberId);
        } catch (SQLException e) {
            throw e;
        }finally {
            close(conn, ps, rs);
        }
    }

    public Member update(Connection conn, String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            log.info("connection : {} , con.getClass", conn, conn.getClass());
            ps = conn.prepareStatement(sql);
            ps.setInt(1, money);
            ps.setString(2, memberId);
            ps.executeUpdate();
            return findMember(memberId);
        } catch (SQLException e) {
            throw e;
        }finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = dataSource.getConnection();
            log.info("connection : {} , con.getClass", conn, conn.getClass());
            ps = conn.prepareStatement(sql);
            ps.setString(1, memberId);
            ps.executeUpdate();
        }catch (SQLException e){
            throw e;
        }finally {
            close(conn, ps, rs);
        }
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        JdbcUtils.closeConnection(conn);
    }
}
