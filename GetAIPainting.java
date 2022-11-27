import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/get_ai_painting")
public class GetAIPainting extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null) {
            throw new ServletException("please input id");
        }

        String painId = PromptService.getPaintId(conn, id);

        AIPaintingEngine engine = new AIPaintingEngine();
        String url = engine.get(painId);
        if (url.isEmpty()) {
            PrintWriter out = resp.getWriter();
            out.print("正在创作中，请稍后重试...");
            out.close();
        } else {
            resp.sendRedirect(url);
        }
    }

}
