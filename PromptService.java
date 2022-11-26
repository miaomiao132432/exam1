import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;

import redis.clients.jedis.Jedis;

public class PromptService {

    public static String addPrompt(final Connection conn, String prompt, String comment)
            throws ServletException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("[sql: 向t_ai_painting插入数据]",
                    stmt.RETURN_GENERATED_KEYS);
            stmt.setString(1, [参数1]);
            stmt.setString(2, [参数2]);
            int row = stmt.executeUpdate();
            if (row == 0) {
                throw new ServletException("insert error.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    String id = generatedKeys.getLong(1) + "";
                    return id;
                } else {
                    throw new ServletException("Creating prompt failed, no ID obtained.");
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPrompt(final Connection conn, String id) throws ServletException {
        Statement stmt = null;
        ResultSet rs = null;
        String prompt = "";
        try {
            stmt = conn.createStatement();
            String sql = String.format("[sql: 按id字段查询表t_ai_painting]", id);
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                do {
                    prompt = rs.getString("prompt");
                } while (rs.next());
            } else {
                throw new ServletException("prompt info not exist");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return prompt;
    }

    public static String getPaintId(final Connection conn, String id) throws ServletException {
        Statement stmt = null;
        ResultSet rs = null;
        String paintId = "";
        try {
            stmt = conn.createStatement();
            String sql = String.format("[sql: 按id字段查询表t_ai_painting]", id);
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                do {
                    paintId = rs.getString("paint_id");
                } while (rs.next());
            } else {
                throw new ServletException("prompt info not exist");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return paintId;
    }

    public static void updatePrompt(final Connection conn, String id, String pid)
            throws ServletException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("[sql: 根据id字段 修改paint_id字段]");
            stmt.setString(1, pid);
            stmt.setString(2, id);
            int row = stmt.executeUpdate();
            if (row == 0) {
                throw new ServletException("update error.");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delPrompt(final Connection conn, String id)
            throws ServletException {
            [仿照以上函数, 完成delPrompt函数]
    }
}
