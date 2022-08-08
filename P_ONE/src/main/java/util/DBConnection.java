package util;

import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final String url;
    private final String username;
    private final String password;

    private final Driver driver;
    private final boolean driverReady;

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Driver.class.getName());

    public DBConnection(String url, String username, String password, Driver driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.driverReady = false;
    }

    /**
     * Constructor for unit testing
     // * @param driver
     // * @param username
     // * @param sa
     // * @param s
     */

//        public DBConnection(org.postgresql.Driver driver, String username, String sa, String s) {
//
//        }

    //If our driver is ready, this method will register our driver

    private void registerDriver() throws SQLException {
        if(!driverReady) {
            DriverManager.registerDriver(this.driver);
        }
    }

    //to get connection to the database url, username and password of the database

    public Connection getConnection() throws SQLException{

        logger.info(">> New connection initiated <<");


        if(!driverReady) {
            registerDriver();
        }
        logger.info(">> Trying to establish connection with database at this location :" + url + " <<");
        return DriverManager.getConnection(url, username, password);
    }
}

