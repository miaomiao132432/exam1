import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

@WebServlet("/get_prompt")

public class GetPrompt extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null) {
            throw new ServletException("please input id");
        }

        PrintWriter out = resp.getWriter();

        String prompt = jedis.get(id);
        if (prompt.isEmpty()) {
            prompt = PromptService.getPrompt(conn, id);
            jedis.set(id, prompt);
            out.printf("%s", prompt);

        } else {
            out.printf("%s", prompt);
        }
        out.close();
    }

}
