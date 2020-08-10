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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import operations.UserOperations;

/**
 *
 * @author stefan
 */
public class vs173101_UserOperations implements UserOperations {

    @Override
    public boolean insertUser(String userName, String firstName, String lastName, String password) {
        try {
            Pattern pName = Pattern.compile("[A-Z][a-z]+");//pattern za firstName i lastName
            Pattern pPass = Pattern.compile("([A-Z]|[a-z]|[0-9]){8,}");
            Matcher mFisrt = pName.matcher(firstName);
            Matcher mLast = pName.matcher(lastName);
            Matcher mPass = pPass.matcher(password);
            if(mFisrt.matches() && mLast.matches() && mPass.matches() && password.matches(".*[a-zA-z].*") && password.matches(".*[0-9].*")){
                Connection connection = DB.getInstance().getConnection();
                String sql = "insert into Korisnik (ime, prezime, kIme, sifra) values (?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, userName);
                ps.setString(4, password);
                return ps.executeUpdate() == 1;
            }
            else{
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public int declareAdmin(String userName) {
        try {
            Connection connection = DB.getInstance().getConnection();
            //provera da li postoji korisnik sa datim userName-om
            String sql = "select idKorisnik from Korisnik where kIme=?";
            int idKorisnik;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false){
                return 2;
            }
            else{
                idKorisnik = rs.getInt("idKorisnik");
            }
            //provera da li je dati korisnik vec admin
            sql = "select A.idKorisnik from Administrator A, Korisnik K where A.idKorisnik=K.idKorisnik and K.kIme=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if(rs.next() == true){
                return 1;
            }
            sql = "insert into Administrator (idKorisnik) values (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idKorisnik);
            if (ps.executeUpdate() == 1){
                return 0;
            }           
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_UserOperations.class.getName()).log(Level.SEVERE, null, ex);           
        }
        return -1;
    }

    @Override
    public Integer getSentPackages(String... userNames) {
        try {
            if (userNames.length > 0){
                Connection connection = DB.getInstance().getConnection();
                String sql = "select sum(brPoslPak) as suma from Korisnik where ";
                for (int i = 0; i < userNames.length - 1; i++){
                    sql += "kIme=? or ";
                }
                sql += "kIme=? ";
                PreparedStatement ps = connection.prepareStatement(sql);
                for (int i = 0; i < userNames.length; i++){
                    ps.setString(i + 1, userNames[i]);
                }          
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    int res = rs.getInt("suma");
                    if(rs.wasNull()){//u slucaju da nema korisnika sa datim username-om
                        return null;
                    }
                    else{
                        return res;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return null;
    }

    @Override
    public int deleteUsers(String... userNames) {
        try {
            if (userNames.length > 0){
                Connection connection = DB.getInstance().getConnection();
                String sql = "delete from Korisnik where ";               
                for (int i = 0; i < userNames.length - 1; i++){
                    sql += "kIme=? or ";
                }
                sql += "kIme=? ";
                PreparedStatement ps = connection.prepareStatement(sql);                
                for (int i = 0; i < userNames.length; i++){
                    ps.setString(i + 1, userNames[i]);
                }          
                return ps.executeUpdate();
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } 
    }

    @Override
    public List<String> getAllUsers() {
        try {
            Connection connection = DB.getInstance().getConnection();
            String sql = "select kIme from Korisnik";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<String> lst = new ArrayList<>();
            while(rs.next()){
                lst.add(rs.getString("kIme"));
            }
            return lst;           
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
