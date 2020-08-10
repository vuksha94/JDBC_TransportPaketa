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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.CourierRequestOperation;

/**
 *
 * @author stefan
 */
public class vs173101_CourierRequestOperation implements CourierRequestOperation {

    @Override
    public boolean insertCourierRequest(String userName, String licencePlateNumber) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "insert into Zahtev_Kurir (idKorisnik, idVozilo) values ("+
                    "(select idKorisnik from Korisnik where kIme=?), (select idVozilo from Vozilo where regBroj=?))";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, licencePlateNumber);
            return ps.executeUpdate() == 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCourierRequest(String userName) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "delete from Zahtev_Kurir where idKorisnik=(select idKorisnik from Korisnik where kIme=?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeVehicleInCourierRequest(String userName, String licencePlateNumber) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "update Zahtev_Kurir set idVozilo=(select idVozilo from Vozilo where regBroj=?) where " + 
                    "idKorisnik=(select idKorisnik from Korisnik where kIme=?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, licencePlateNumber);
            ps.setString(2, userName);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<String> getAllCourierRequests() {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select K.kIme, V.regBroj from Zahtev_Kurir Z, Korisnik K, Vozilo V" +
                            " where Z.idKorisnik=K.idKorisnik and Z.idVozilo=V.idVozilo";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int i=0;
            List<String> lst = new ArrayList<>();
            while(rs.next()){
                lst.add(++i + ". " + rs.getString("kIme") + " " + rs.getString("regBroj"));
            }
            return lst;           
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public boolean grantRequest(String username) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "update Zahtev_Kurir set status=2 " +
                    "where idKorisnik=(select idKorisnik from Korisnik where kIme=?)";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
