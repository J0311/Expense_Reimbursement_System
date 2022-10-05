package services;

import repositories.EmployeeDAO;
import repositories.ManagerDAO;
import repositories.ReimbursementDAO;
import models.Employee;
import models.Manager;
import models.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBConnection;

import java.util.ArrayList;

public class ManagerService {

    private static final Logger logger = LogManager.getLogger(ManagerService.class.getName());
    public int currentUserId = 0;
    EmployeeDAO eDAO;
    ManagerDAO mDAO;
    ReimbursementDAO rDAO;
    DBConnection db;

    public ManagerService(DBConnection db) {
        this.db = db;
        this.eDAO = new EmployeeDAO(db);
        this.mDAO = new ManagerDAO(db);
        this.rDAO = new ReimbursementDAO(db);
    }

    public ManagerService(EmployeeDAO eDAO, ManagerDAO mDAO, ReimbursementDAO rDAO, DBConnection db) {
        this.eDAO = eDAO;
        this.mDAO = mDAO;
        this.rDAO = rDAO;
        this.db = db;
    }

    /**
     * manager login
     * @param-username
     * @param-password
     * @return 0 if login failed and manager's id if login successfully
     */
    
    public int login(String username, String password) {
        Manager manager = mDAO.verifyManager(username,password);
        logger.debug("manager is: " + manager);
        if(manager != null) {
            currentUserId = manager.getM_id();
            logger.debug("currentId is: " + currentUserId);
        } else {
            currentUserId = 0;
            logger.debug("manager login verification failed");
            logger.debug("failed currentId is " + currentUserId);
        }
        return currentUserId;
    }

    /**
     * get all employees
     * @return all employees
     */
    public ArrayList<Employee> viewEmployees() {
        return eDAO.getAll();
    }

    /**
     * get requests of a specified employee
     * @param e_id
     * @return all requests of a specified employee
     */
    
    public ArrayList<Reimbursement> viewEmployeeRequests(Integer e_id) {
        return rDAO.getByEmployeeId(e_id);
    }

    /**
     * get all pending requests
     * @return all pending requests
     */
    public ArrayList<Reimbursement> getAllPending() {
        return rDAO.getAllPending();
    }

    /**
     * get all resolved requests
     * @return all resolved requests
     */
    
    public ArrayList<Reimbursement> getAllResolved() {
        return rDAO.getAllResolved();
    }

    /**
     * approve or deny a specific request
     * @param requestId
     * @param decision
     * @return true if resolve successfully and false if resolve unsuccessfully
     */
    
    public boolean resolve(int requestId, String decision) {
        return rDAO.resolve(requestId,currentUserId,decision);
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }
}
