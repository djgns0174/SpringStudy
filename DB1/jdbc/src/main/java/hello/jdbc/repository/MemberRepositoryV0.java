package hello.jdbc.repository;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?,?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DBConnectionUtil.getConnection();
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
            conn = DBConnectionUtil.getConnection();
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

    public Member update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
             conn = DBConnectionUtil.getConnection();
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

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = DBConnectionUtil.getConnection();
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
        if (rs != null) {
            try{
                rs.close();
            }catch (SQLException e){
                throw e;
            }
        }

        if (ps != null) {
            try{
                ps.close();
            }catch (SQLException e){
                throw e;
            }
        }

        if (conn != null) {
            try{
                conn.close();
            }catch (SQLException e){
                throw e;
            }
        }

    }
}
