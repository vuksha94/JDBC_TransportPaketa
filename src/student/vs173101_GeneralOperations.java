/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import DB.DB;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.GeneralOperations;

/**
 *
 * @author stefan
 */
public class vs173101_GeneralOperations implements GeneralOperations {
    @Override
    public void eraseAll() {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "{call sp_MSforeachtable (?)}";
            /*String sql = "-- disable referential integrity\n" +
                    "EXEC sp_MSForEachTable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL' \nGO\n" +
                    "EXEC sp_MSForEachTable 'DELETE FROM ?' \nGO\n" +
                    "-- enable referential integrity again \n" +
                    "EXEC sp_MSForEachTable 'ALTER TABLE ? WITH CHECK CHECK CONSTRAINT ALL' \nGO";*/
            
            CallableStatement cst = connection.prepareCall(sql);
            cst.setString(1, "ALTER TABLE ? NOCHECK CONSTRAINT ALL");
            cst.execute();

            cst = connection.prepareCall(sql);
            cst.setString(1, "DELETE FROM ?");
            cst.execute();  
           
            cst = connection.prepareCall(sql);
            cst.setString(1, "ALTER TABLE ? WITH CHECK CHECK CONSTRAINT ALL");
            cst.execute();    
            
            } catch (SQLException ex) {
            Logger.getLogger(vs173101_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
