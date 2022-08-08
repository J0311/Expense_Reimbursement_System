package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;
import services.EmployeeService;
import services.ManagerService;
import util.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class.getName());
    private DBConnection db;
    private ManagerService ms;
    private  EmployeeService es;
    private ObjectMapper om;
    @Override
    public void init() throws ServletException {
        db = new DBConnection("jdbc:postgresql://javadb-0.cpz1wwb1y5uo.us-east-1.rds.amazonaws.com:5432/postgres",
                "J0311", "BestPasswordEver", new Driver());
        om = new ObjectMapper();
        ms = new ManagerService(db);
        es = new EmployeeService(db);
        System.out.println("init success");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(true);
        Integer currentId = 0;
        String user = null;
        logger.debug("usersessionId: " + userSession.getId());
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        logger.debug("username is " + username + " password is " + password);
        if(username == null || password == null || type == null) {
            response.setContentType("text/plain");
            response.getWriter().write("Complete login information was not provided");
            response.setStatus(400);
        }
        if(type.equals("manager")) {
            currentId = ms.login(username,password);
            user = "manager";
            userSession.setAttribute("currentId",currentId);
            userSession.setAttribute("user",user);
            logger.debug("currentId is: " + currentId);
            if(currentId != 0) {
                response.setContentType("text/plain");
                response.getWriter().write("Login Successfully. Your manager id is: " + currentId);
                response.setStatus(200);
            } else {
                response.setContentType("text/plain");
                response.getWriter().write("incorrect username/password");
                response.setStatus(401);
            }
        }
        else if(type.equals("employee")) {
            currentId = es.login(username,password);
            user = "employee";
            userSession.setAttribute("currentId",currentId);
            userSession.setAttribute("user",user);
            logger.debug("currentId is: " + currentId);
            if(currentId != 0) {
                response.setContentType("text/plain");
                response.getWriter().write("Login Successfully. Your employee id is: " + currentId);
                response.setStatus(200);
            } else {
                response.setContentType("text/plain");
                response.getWriter().write("incorrect username/password");
                response.setStatus(401);
            }

        }
    }

}