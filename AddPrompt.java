import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

        String id = PromptService.addPrompt(getJDBCConnection(), prompt, comment);
        delKey(id);

        PrintWriter out = resp.getWriter();
        out.printf("[add]ok");
        out.close();

    }

}
