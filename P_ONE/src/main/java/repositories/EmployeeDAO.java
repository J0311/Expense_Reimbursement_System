package repositories;

import models.Employee;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmployeeDAO {
    private DBConnection db;
    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class.getName());

    public EmployeeDAO(DBConnection db) {
        this.db = db;
    }

    /**
     * get all employees in the database
     * @return all employees
     */

    public ArrayList<Employee> getAll() {
        Connection connection = null;
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            String sql = "select * from employee;";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            connection.commit();
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("e_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
                employees.add(employee);
            }
            return employees;
        } catch (SQLException se) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    logger.error("get all employee failed");
                    se.printStackTrace();
                }
            }
            se.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        return employees;
    }

    /**
     *  find employee by id
     * @param id
     * @return employee with mathcing id
     */

    public Employee getById(Integer id) {
        Connection connection = null;
        Employee employee = null;
        try {
            String sql = "select * from employee where e_id=?";
            connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                employee = new Employee(
                        rs.getInt("e_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
                logger.debug("return employee: " + employee);
            }
            return employee;
        } catch (SQLException se) {
            logger.error("get employee by id failed");
            se.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        return employee;
    }

    /**
     * update an employee's information who has the id number provided
     * @param e_id
     * @param firstname
     * @param lastname
     * @param email
     * @param username
     * @return true for updating successfully and false for updating unsuccessfully
     */

    public boolean updateInfo(Integer e_id, String firstname, String lastname, String email, String username) {
        Connection connection = null;
        try {
            String sql = "update employee set first_name = ?, last_name = ?, email = ?, username = ? where e_id = ?;";
            connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4,username);
            ps.setInt(5,e_id);
            int rowsAffected = ps.executeUpdate();
            logger.debug(rowsAffected + "rows updated");
            return rowsAffected > 0;

        }catch (SQLException e) {
            logger.error("employee info not updated correctly");
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;

    }

    /**
     * verify if an employee with the provided username and password is in database
     * @param username
     * @param password
     * @return the corresponding employee
     */

    public Employee verifyEmployee(String username, String password) {
        Connection connection = null;
        Employee employee = null;
        try{
            String sql = "select e_id * from employee where username = ? and password = ?";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            connection.commit();
            if(rs.next()) {
                employee = new Employee(
                        rs.getInt("e_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
            return employee;
        }catch (SQLException e) {
            logger.error("verify employee failed");
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}