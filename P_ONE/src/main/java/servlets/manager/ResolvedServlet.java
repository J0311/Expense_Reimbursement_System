package servlets.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;
import services.ManagerService;
import util.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ResolveServlet", urlPatterns = "/resolve")
public class ResolvedServlet extends HttpServlet {
    private DBConnection db;
    private ManagerService ms;
    private ObjectMapper om;
    private static final Logger logger = LogManager.getLogger(ResolvedServlet.class.getName());
    @Override
    public void init() throws ServletException {
        db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
                "J0311", "BestPasswordEver", new Driver());
        om = new ObjectMapper();
        ms = new ManagerService(db);
        logger.debug("init success");
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(true);
        Integer currentId = 0;
        String user = null;
        String decision = request.getParameter("decision");
        String id = request.getParameter("id");
        currentId = (Integer) userSession.getAttribute("currentId");
        if(userSession.getAttribute("currentId") != null && userSession.getAttribute("user") == "manager") {
            if (decision == null || id == null) {
                response.setContentType("text/plain");
                response.getWriter().write("A complete resolve information was not provided");
                response.setStatus(400);
            } else {
                try {
                    ms.setCurrentUserId(currentId);
                    ms.resolve(Integer.parseInt(id), decision);
                    response.setStatus(200);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.setContentType("text/plain");
                    response.getWriter().write("A complete resolve information was not provided");
                    response.setStatus(400);
                }
            }
        }
    }
}