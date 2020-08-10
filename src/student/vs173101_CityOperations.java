/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import DB.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.CityOperations;

/**
 *
 * @author stefan
 */
public class vs173101_CityOperations implements CityOperations {

    @Override
    public int insertCity(String name, String postalCode) {
        try {
            Connection connection = DB.getInstance().getConnection();
            /*  *****************ne radi SCOPE_IDENTITY() ****************
            String sql = "insert into Grad (naziv, pBroj) values (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, name);
            ps.setString(2, postalCode);
            
            ps.executeUpdate();
            
            sql = "select SCOPE_IDENTITY() AS scope_identity";
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();*/
            String sql = "insert into Grad (naziv, pBroj) values ('" + name + "','" + postalCode + "')";
            Statement st = connection.createStatement();
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rs = st.getGeneratedKeys();
            //vraca se PK unetog grada            
            if (rs.next()){
                return rs.getInt(1);
            }
            else {
                return -1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public int deleteCity(String... names) {
        try {
            if (names.length > 0){
                Connection connection = DB.getInstance().getConnection();
                String sql = "delete from Grad where ";               
                for (int i = 0; i < names.length - 1; i++){
                    sql += "naziv='" + names[i] + "' or ";
                }
                sql += "naziv='" + names[names.length - 1] + "'";

                Statement st = connection.createStatement();
                return st.executeUpdate(sql);
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }      
    }

    @Override
    public boolean deleteCity(int i) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "delete from Grad where idGrad=?";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, i);
            // vraca se uspeh brisanja true/false
            
            return ps.executeUpdate() == 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Integer> getAllCities() {
        List<Integer> lst = new ArrayList<>();
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select idGrad from Grad";
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                lst.add(rs.getInt("idGrad"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }
    
}
