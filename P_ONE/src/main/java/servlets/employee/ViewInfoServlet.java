package servlets.employee;


import com.fasterxml.jackson.databind.ObjectMapper;
import models.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;
import services.EmployeeService;
import util.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ViewInfoServlet", urlPatterns = "/viewInfo")
public class ViewInfoServlet extends HttpServlet {
    private DBConnection db;
    private EmployeeService es;
    private ObjectMapper om;
    private static final Logger logger = LogManager.getLogger(ViewInfoServlet.class.getName());

    @Override
    public void init() throws ServletException {
        db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
                "J0311", "BestPasswordEver", new Driver());
        om = new ObjectMapper();
        es = new EmployeeService(db);
        System.out.println("init success");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("inside viewInfoServlet do get method");
        HttpSession userSession = request.getSession();
        Integer currentId = 0;
        currentId = (Integer) userSession.getAttribute("currentId");
        if(userSession.getAttribute("currentId") != null && userSession.getAttribute("user") == "employee") {
            es.setCurrentUserId(currentId);
            Employee employee = es.viewInfo();
            response.setContentType("text/plain");
            response.getWriter().write("name: " + employee.getfName()+" " + employee.getlName() +"\n"+"email: " + employee.getEmail());
        } else{
            logger.debug("should enter here when get attribute is null");
            response.setContentType("text/plain");
            response.getWriter().write("You don't have access to this page. Please login as an employee first");
            response.setStatus(403);
        }
    }

}
