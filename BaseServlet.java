import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import redis.clients.jedis.Jedis;

public class BaseServlet extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/linux_exam";
    static final String USER = "root";
    static final String PASS = "1234@";

    static Connection conn = null;
    static Jedis jedis = null;

    public void init() throws ServletException {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            jedis = new Jedis("localhost");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public void destroy() {
        try {
            conn.close();
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
