package repositories;

import models.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class ReimbursementDAO {
    private DBConnection db;
    private static final Logger logger = LogManager.getLogger(ReimbursementDAO.class.getName());

    public ReimbursementDAO(DBConnection db) {
        this.db = db;
    }


    /**
     * get all requests in the database
     * @return all requests
     */
    public ArrayList<Reimbursement> getAll() {
        Connection connection = null;
        ArrayList<Reimbursement> requests = new ArrayList<>();
        try {
            String sql = "select * from reimbursement;";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            connection.commit();
            while (rs.next()) {
                Reimbursement request = new Reimbursement(
                        rs.getInt("r_id"),
                        rs.getDouble("amount"),
                        rs.getInt("e_id"),
                        rs.getInt("m_id"),
                        rs.getString("status"),
                        rs.getString("reimbursement_type")
                );
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error("get all requests failed");
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return requests;
    }

    /**
     * get all pending requests in database
     * @return all pending requests
     */
    public ArrayList<Reimbursement> getAllPending() {
        Connection connection = null;
        ArrayList<Reimbursement> requests = new ArrayList<>();
        try {
            String sql = "select * from reimbursement where status = ?;";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "pending");
            ResultSet rs = ps.executeQuery();
            connection.commit();
            while (rs.next()) {
                Reimbursement request = new Reimbursement(
                        rs.getInt("r_id"),
                        rs.getDouble("amount"),
                        rs.getInt("e_id"),
                        rs.getInt("m_id"),
                        rs.getString("status"),
                        rs.getString("reimbursement_type")
                );
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error("get all pending requests failed");
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return requests;
    }

    /**
     * get all resolved requests in database
     * @return all resolved requests
     */
    public ArrayList<Reimbursement> getAllResolved() {
        Connection connection = null;
        ArrayList<Reimbursement> requests = new ArrayList<>();
        try {
            String sql = "select * from reimbursement where status = ? or status = ?;";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "approved");
            ps.setString(2,"denied");
            ResultSet rs = ps.executeQuery();
            connection.commit();
            while (rs.next()) {
                Reimbursement request = new Reimbursement(
                        rs.getInt("r_id"),
                        rs.getDouble("amount"),
                        rs.getInt("e_id"),
                        rs.getInt("m_id"),
                        rs.getString("status"),
                        rs.getString("reimbursement_type")
                );
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error("get all resolved requests failed");
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return requests;
    }

    /**
     * find a specific request by its id
     * @param id
     * @return request that has the exact id
     */
    public Reimbursement getById(Integer id) {
        Connection connection = null;
        Reimbursement request = null;
        try {
            String sql = "select * from reimbursement where r_id = ?";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            connection.commit();
            if (rs.next()) {
                request = new Reimbursement(
                        rs.getInt("r_id"),
                        rs.getDouble("amount"),
                        rs.getInt("e_id"),
                        rs.getInt("m_id"),
                        rs.getString("status"),
                        rs.getString("reimbursement_type")
                );
                return request;
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error("get reimbursement by id failed");
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * get all requests of employee with the specific id
     * @param id
     * @return all requests from a single employee
     */
    public ArrayList<Reimbursement> getByEmployeeId(Integer id) {
        Connection connection = null;
        ArrayList<Reimbursement> requests = new ArrayList<>();
        try {
            String sql = "select * from reimbursement where e_id = ?";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            connection.commit();
            while (rs.next()) {
                Reimbursement request = new Reimbursement(
                        rs.getInt("r_id"),
                        rs.getDouble("amount"),
                        rs.getInt("e_id"),
                        rs.getInt("m_id"),
                        rs.getString("status"),
                        rs.getString("reimbursement_type")
                );
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error("get request by employee id failed");
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return requests;
    }

    /**
     * insert a new request into database.
     * The m_id field will be "NULL" due to pending status
     * @param request
     * @return the id of the newly inserted request
     */
    public Integer insert(Reimbursement request) {
        Connection connection = null;
        int newId = -1;
        try {
            String sql = "insert into reimbursement (amount, e_id , m_id , status, reimbursement_type) values (?,?,?,?,?)";
            connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, request.getAmount());
            ps.setInt(2, request.getE_id());
            ps.setNull(3, Types.NULL);
            ps.setString(4,"pending");
            ps.setString(5,request.getType());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()) {
                newId = keys.getInt(1);
                logger.debug("newId is: " + newId);
            }
            return newId;
        } catch (SQLException se) {
            logger.error("insert new request failed");
            se.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return newId;
    }

    /**
     * change the status of a specified request
     * @param r_id
     * @param m_id
     * @param reimbursement_type
     * @return true if changed successfully and false if changed unsuccessfully
     */

    public boolean resolve(int r_id, int m_id, String reimbursement_type) {
        Connection connection = null;
        boolean resolved = false;
        try {
            String sql = "update reimbursement set status = ?, m_id = ? where r_id = ?";
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, reimbursement_type);
            ps.setInt(2, m_id);
            ps.setInt(3, r_id);
            ps.executeUpdate();
            connection.commit();
            resolved = true;
        } catch (SQLException e) {
            logger.error("resolved request failed");
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
        return resolved;
    }
}