package services;

import repositories.EmployeeDAO;
import repositories.ReimbursementDAO;
import models.Employee;
import models.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBConnection;

import java.util.ArrayList;

public class EmployeeService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class.getName());
    public int currentUserId = 0;
    EmployeeDAO eDAO;
    ReimbursementDAO rDAO;
    DBConnection db;


    public EmployeeService(DBConnection db, EmployeeDAO eDAO, ReimbursementDAO rDAO) {
        this.db = db;
        this.eDAO = eDAO;
        this.rDAO = rDAO;
    }

    public EmployeeService(DBConnection db) {
        this.db = db;
        this.eDAO = new EmployeeDAO(db);
        this.rDAO = new ReimbursementDAO(db);
    }

    /**
     * employee log in
     * @param username
     * @param password
     * @return 0 if login failed and employee's id if login success
     */
    
    public int login(String username, String password) {
        Employee employee = eDAO.verifyEmployee(username,password);
        logger.debug("employee is: " + employee);
        if(employee != null) {
            currentUserId = employee.getE_id();
            logger.debug("currentId is: " + currentUserId);
        } else {
            currentUserId = 0;
            logger.debug("employee login verification failed");
        }
        return currentUserId;
    }


    /**
     * view employee's information
     * @return employee
     */
    public Employee viewInfo() {
        Employee employee = eDAO.getById(currentUserId);
        logger.debug("Employee is: " + employee);
        return employee;
    }

    /**
     * update employee's information
     * @param firstname
     * @param lastname
     * @param password
     * @param-age
     * @return true if updated successfully and false if updated unsuccessfully
     */

    public boolean updateInfo(String firstname, String lastname, String password, String email) {
        return eDAO.updateInfo(currentUserId, firstname, lastname, password, email);
    }

    /**
     * submit a new request
     * @param request
     * @return the id of the newly inserted request
     */
    public int submitRequest(Reimbursement request) {
        return rDAO.insert(request);
    }

    /**
     * view all pending requests
     * @return all pending requests
     */
    public ArrayList<Reimbursement> viewPendingRequests() {
        ArrayList<Reimbursement> requests = rDAO.getByEmployeeId(currentUserId);
        ArrayList<Reimbursement> target = new ArrayList<>();
        for (Reimbursement request : requests) {
            if(request.getStatus().equals("pending")) {
                target.add(request);
            }
        }
        return target;
    }

    /**
     * view all resolved requests
     * @return all resolved requests
     */

    public ArrayList<Reimbursement> viewResolvedRequests() {
        ArrayList<Reimbursement> requests = rDAO.getByEmployeeId(currentUserId);
        ArrayList<Reimbursement> target = new ArrayList<>();
        for(Reimbursement request : requests) {
            if(request.getStatus().equals("denied") || request.getStatus().equals("approved")) {
                target.add(request);
            }
        }
        return target;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }
}
