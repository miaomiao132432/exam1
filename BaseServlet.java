import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import redis.clients.jedis.Jedis;

public class BaseServlet extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/xxxxxx";
    static final String USER = "xxxxxxx";
    static final String PASS = "xxxxxxx";

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

    public static Connection getJDBCConnection() throws ServletException {
        try {
            if (conn.isValid(0)) {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
        return conn;
    }

    public static Jedis getJedisConnection() throws SQLException {
        return jedis;
    }

    public static String getValue(String key) {
        return jedis.get(key);
    }

    public static void setValue(String key, String value) {
        jedis.set(key, value);
    }

    public static void delKey(String key) {
        jedis.del(key);
    }

}