package servlets.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;
import services.ManagerService;
import util.DBConnection;
import servlets.LoginServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "viewEmployeesServlet", urlPatterns = "/viewEmployees")
public class ViewEmployeesServlet extends HttpServlet {
    private DBConnection db;
    private ManagerService ms;
    private ObjectMapper om;
    private static final Logger logger = LogManager.getLogger(LoginServlet.class.getName());
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
        logger.debug("inside viewEmployeesServlet do get method");
        HttpSession userSession = request.getSession();
        Integer currentId = 0;
        logger.debug("currentId is " + currentId);
        logger.debug("getAttribute: " + userSession.getAttribute("currentId"));
        if(userSession.getAttribute("currentId") != null && userSession.getAttribute("user") == "manager") {
            currentId = (Integer) userSession.getAttribute("currentId");
            ArrayList<Employee> employees = new ArrayList<>();
            employees = ms.viewEmployees();
            logger.debug("size of employee " + employees.size());
            response.setContentType("application/json");
            response.getWriter().write(om.writeValueAsString(employees));
            response.setStatus(200);
        } else{
            logger.debug("should enter here when get attribute is null");
            response.setContentType("text/plain");
            response.getWriter().write("You don't have access to this page. Please login as a manager first");
            response.setStatus(403);
        }
    }

}
