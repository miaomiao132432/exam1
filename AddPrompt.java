import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

@WebServlet("/add_prompt")

public class AddPrompt extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String prompt = req.getParameter("prompt");
        String comment = req.getParameter("comment");

        if (prompt == null || comment == null) {
            throw new ServletException("please input all parameters");
        }

        String id = PromptService.addPrompt(conn, prompt, comment);
        jedis.del(id);

        PrintWriter out = resp.getWriter();
        out.printf("[add]ok");
        out.close();

    }

}
