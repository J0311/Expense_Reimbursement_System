package repositories;

import models.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAO {
    private DBConnection db;
    private static final Logger logger = LogManager.getLogger(ManagerDAO.class.getName());

    public ManagerDAO(DBConnection db) {
        this.db = db;
    }

    /**
     * find a specific manager id
     * @param-id
     * @return manager who matching id
     */
    public Manager getById(int id) {
        Connection connection = null;
        Manager manager = null;
        try {
            String sql = "select * from manager where m_id = ?";
            connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                manager = new Manager(
                        rs.getInt("m_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"));

                logger.debug("return manager: " + manager);
            }
            return manager;
        } catch (SQLException e) {
            logger.error("get manager by m_id failed");
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
        return manager;
    }

    /**
     * verify if a manager with the provided username and password is in database
     * @param username
     * @param password
     * @return the corresponding manager
     */
    public Manager verifyManager(String username, String password) {
        Connection connection = null;
        Manager manager = null;
        try{
            String sql = "select * from manager where username = ?" +
                    " and password = ?";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            connection.commit();
            if(rs.next()) {
                manager = new Manager(
                        rs.getInt("m_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"));
            }
            return manager;
        }catch (SQLException e) {
            logger.error("verify manager failed");
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