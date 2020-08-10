/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class DB {
    private static final String serverName = "localhost";
    private static String instanceName = "STEFAN";
    private static int portNumber = 1433;
    private static String database = "TransportPaketa";
    private static String username = "Stefan";
    private static String password = "vuksha159";
    private static String connectionString = "jdbc:sqlserver://" + serverName + ";databaseName=" + database + ";username=" + username + ";password=" + password;
    
    private static Connection connection;
    private static DB db;

    public Connection getConnection() {
        return connection;
    }
    
    private DB(){
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DB getInstance(){
        if(db == null){
            db = new DB();
        }
        return db;
    }
}
