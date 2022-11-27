import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        String prompt = getValue(id);

        if (prompt != null && prompt.length() != 0) {
            out.printf("%s", prompt);
        } else {
            prompt = PromptService.getPrompt(conn, id);
            setValue(id, prompt);
            out.printf("%s", prompt);
        }
        out.close();
    }

}
