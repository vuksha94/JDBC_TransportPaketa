/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import DB.DB;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.VehicleOperations;

/**
 *
 * @author stefan
 */
public class vs173101_VehicleOperations implements VehicleOperations {

    @Override
    public boolean insertVehicle(String licencePlateNumber, int fuelType, BigDecimal fuelConsumption) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "insert into Vozilo (regBroj, tipGoriva, potrosnja) values (?, ?, ?)";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, licencePlateNumber);
            ps.setInt(2, fuelType);
            ps.setBigDecimal(3, fuelConsumption.setScale(3, RoundingMode.HALF_UP));
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public int deleteVehicles(String... licensePlateNumber) {
        try {
            if (licensePlateNumber.length > 0){
                Connection connection = DB.getInstance().getConnection();
                String sql = "delete from Vozilo where ";               
                for (int i = 0; i < licensePlateNumber.length - 1; i++){
                    sql += "regBroj=? or ";
                }
                sql += "regBroj=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                
                for (int i = 0; i < licensePlateNumber.length; i++){
                    ps.setString(i + 1, licensePlateNumber[i]);
                }                
                return ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }      
        return 0;
    }
    
    

    @Override
    public List<String> getAllVehichles() {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select regBroj from Vozilo";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<String> lst = new ArrayList<>();
            while(rs.next()){
                lst.add(rs.getString("regBroj"));
            }
            return lst;           
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public boolean changeFuelType(String licensePlateNumber, int fuelType) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "update Vozilo set tipGoriva=? where regBroj=?";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, fuelType);
            ps.setString(2, licensePlateNumber);            
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    @Override
    public boolean changeConsumption(String licensePlateNumber, BigDecimal fuelConsumption) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "update Vozilo set potrosnja=? where regBroj=?";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, fuelConsumption.setScale(3, RoundingMode.HALF_UP));
            ps.setString(2, licensePlateNumber);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
              
}

