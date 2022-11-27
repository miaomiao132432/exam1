import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ai_painting")
public class AIPainting extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null) {
            throw new ServletException("please input id");
        }

        String prompt = PromptService.getPrompt(getJDBCConnection(), id);

        AIPaintingEngine engine = new AIPaintingEngine();
        String pid = engine.paint(prompt);
        PromptService.updatePrompt(getJDBCConnection(), id, pid);

        PrintWriter out = resp.getWriter();
        out.printf("开始作画, 画作id为: %s", pid);
        out.close();
    }

}
