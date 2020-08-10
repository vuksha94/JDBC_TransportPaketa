/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import DB.DB;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.CourierOperations;

/**
 *
 * @author stefan
 */
public class vs173101_CourierOperations implements CourierOperations {

    @Override
    public boolean insertCourier(String courierUserName, String licencePlateNumber) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "update Zahtev_Kurir set status=2 " +
                    "where idKorisnik=(select idKorisnik from Korisnik where kIme=?) " +
                    "and idVozilo=(select idVozilo from Vozilo where regBroj=?)";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courierUserName);
            ps.setString(2, licencePlateNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean deleteCourier(String courierUserName) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "delete from Kurir where idKorisnik=(select idKorisnik from Korisnik where kIme=?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courierUserName);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }

    @Override
    public List<String> getCouriersWithStatus(int statusOfCourier) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select Korisnik.kIme from Kurir, Korisnik where Kurir.idKorisnik=Korisnik.idKorisnik and Kurir.status=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, statusOfCourier);
            ResultSet rs = ps.executeQuery();
            
            List<String> lst = new ArrayList<>();
            while(rs.next()){
                lst.add(rs.getString("kIme"));
            }
            return lst; 
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
       
    }

    @Override
    public List<String> getAllCouriers() {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select Korisnik.kIme from Kurir, Korisnik where " +
                            "Kurir.idKorisnik=Korisnik.idKorisnik order by Kurir.profit desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<String> lst = new ArrayList<>();
            while(rs.next()){
                lst.add(rs.getString("kIme"));
            }
            return lst; 
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public BigDecimal getAverageCourierProfit(int numberOfDeliveries) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select avg(profit) as p from Kurir where brIspPak >= ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, numberOfDeliveries);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getBigDecimal("p");
            }            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
