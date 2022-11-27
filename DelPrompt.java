import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/del_prompt")

public class DelPrompt extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null) {
            throw new ServletException("please input id");
        }

        delKey(id);
        PromptService.delPrompt(conn, id);

        PrintWriter out = resp.getWriter();
        out.printf("[del]ok");
        out.close();

    }

}
