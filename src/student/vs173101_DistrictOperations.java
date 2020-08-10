/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import DB.DB;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.DistrictOperations;

/**
 *
 * @author stefan
 */
public class vs173101_DistrictOperations implements DistrictOperations {
    
    public BigDecimal districtDistance(int idDistrict1, int idDistrict2){
        try {
            Connection conn = DB.getInstance().getConnection();
            String sql = "{ ? = call fDistancaOpstine(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.registerOutParameter(1, Types.DECIMAL);
            cs.setInt(2, idDistrict1);
            cs.setInt(3, idDistrict2);
            cs.execute();
            BigDecimal distance = cs.getBigDecimal(1);
            return distance;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public int insertDistrict(String name, int cityId, int xCord, int yCord) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "insert into Opstina (naziv, xK, yK, idGrad) values ('" +
                    name + "','" + xCord + "','" + yCord + "','" + cityId + "')";
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
            Logger.getLogger(vs173101_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public int deleteDistricts(String... names) {
        try {
            if (names.length > 0){
                Connection connection = DB.getInstance().getConnection();
                String sql = "delete from Opstina where ";                
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
    public boolean deleteDistrict(int idDistrict) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "delete from Opstina where idOpstina=" + idDistrict;           
            Statement st = connection.createStatement();
            return st.executeUpdate(sql) == 1;      
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public int deleteAllDistrictsFromCity(String nameOfTheCity) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "delete from Opstina where idGrad=" +
                    "(select idGrad from Grad where naziv='" + nameOfTheCity + "')";
            Statement st = connection.createStatement();
            return st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Integer> getAllDistrictsFromCity(int idCity) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select idOpstina from Opstina where idGrad=" + idCity;
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            List<Integer> lst = new ArrayList<>();
            int cnt = 0; //sluzi za proveru postojanja Opstine sa datim idGrada
            while(rs.next()){
                cnt++;
                lst.add(rs.getInt("idOpstina"));
            }
            if(cnt > 0){
                return lst;
            }
            else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }

    @Override
    public List<Integer> getAllDistricts() {
        List<Integer> lst = new ArrayList<>();
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select idOpstina from Opstina";
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                lst.add(rs.getInt("idOpstina"));
            }            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }
    
}
