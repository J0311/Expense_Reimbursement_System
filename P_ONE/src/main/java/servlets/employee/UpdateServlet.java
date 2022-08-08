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

@WebServlet(name = "UpdateServlet", urlPatterns = "/update")
public class UpdateServlet extends HttpServlet {
    private DBConnection db;
    private EmployeeService es;
    private ObjectMapper om;
    private static final Logger logger = LogManager.getLogger(UpdateServlet.class.getName());

    @Override
    public void init() throws ServletException {
        db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
                "J0311", "BestPasswordEver", new Driver());
        om = new ObjectMapper();
        es = new EmployeeService(db);
        System.out.println("init success");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("inside update servlet");
        HttpSession userSession = request.getSession();
        Integer currentId = 0;
        currentId = (Integer) userSession.getAttribute("currentId");
        if(userSession.getAttribute("currentId") != null && userSession.getAttribute("user") == "employee") {
            logger.debug("inputStream: " + request.getInputStream());
            Employee employee = om.readValue(request.getInputStream(), Employee.class);
            es.setCurrentUserId(currentId);
            logger.debug("employee = " + employee);
            es.updateInfo(employee.getfName(),employee.getlName(),employee.getPassword(),employee.getEmail());
            response.setStatus(200);
        } else{
            logger.debug("Attribute is null");
            response.setContentType("text/plain");
            response.getWriter().write("Access denied. Please login as an employee first");
            response.setStatus(403);
        }

    }
}