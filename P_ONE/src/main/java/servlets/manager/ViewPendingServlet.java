package servlets.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;
import services.ManagerService;
import util.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ViewPendingsServlet", urlPatterns = "/viewPendings")
public class ViewPendingServlet extends HttpServlet {
    private DBConnection db;
    private ManagerService ms;
    private ObjectMapper om;
    private static final Logger logger = LogManager.getLogger(ViewPendingServlet.class.getName());
    @Override
    public void init() throws ServletException {
        db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
                "J0311", "BestPasswordEver", new Driver());
        om = new ObjectMapper();
        ms = new ManagerService(db);
        System.out.println("init success");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("inside ViewPendings doGet method");
        HttpSession userSession = request.getSession();
        Integer currentId = 0;
        logger.debug("currentId is " + currentId);
        logger.debug("getAttribute: " + userSession.getAttribute("currentId"));
        if(userSession.getAttribute("currentId") != null && userSession.getAttribute("user") == "manager") {
            currentId = (Integer) userSession.getAttribute("currentId");
            ArrayList<Reimbursement> requests = new ArrayList<>();
            requests = ms.getAllPending();
            logger.debug("size of requests array is " + requests.size());
            response.setContentType("application/json");
            response.getWriter().write(om.writeValueAsString(requests));
        } else{
            logger.debug("should enter here when get attribute is null");
            response.setContentType("text/plain");
            response.getWriter().write("You don't have access to this page. Please login as a manager first");
            response.setStatus(403);
        }
    }

}