package servlets.employee;


import com.fasterxml.jackson.databind.ObjectMapper;
import models.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;
import services.EmployeeService;
import util.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SubmitServlet", urlPatterns = "/submit")
public class SubmitServlet extends HttpServlet {
    private DBConnection db;
    private EmployeeService es;
    private ObjectMapper om;
    private static final Logger logger = LogManager.getLogger(SubmitServlet.class.getName());

    @Override
    public void init() throws ServletException {
        db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
                "J0311", "BestPasswordEver", new Driver());
        om = new ObjectMapper();
        es = new EmployeeService(db);
        System.out.println("initialization successful");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        logger.debug("inside submit servlet");
        HttpSession userSession = request.getSession();
        Integer currentId = 0;
        currentId = (Integer) userSession.getAttribute("currentId");
        if(userSession.getAttribute("currentId") != null && userSession.getAttribute("user") == "employee") {
            logger.debug("inputStream: " + request.getInputStream());
            Reimbursement re = om.readValue(request.getInputStream(), Reimbursement.class);
            re.setE_id(currentId);
            es.submitRequest(re);
            logger.debug("reimbursement = " + re);
            response.setStatus(201);
        } else{
            logger.debug("Attribute is null");
            response.setContentType("text/plain");
            response.getWriter().write("Access denied. Please login as an employee first");
            response.setStatus(403);
        }

    }
}