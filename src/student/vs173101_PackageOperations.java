/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import DB.DB;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.PackageOperations;

/**
 *
 * @author stefan
 */
public class vs173101_PackageOperations implements PackageOperations {

    @Override
    public int insertPackage(int districtFrom, int districtTo, String userName, int packageType, BigDecimal weight) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "insert into Paket_Prevoz (opstPreuzmi, opstDostavi, idKorisnik, tipPaketa, tezinaPaketa) " +
                    "values (?,?,(select idKorisnik from Korisnik where kIme=?),?,?)";
            PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, districtFrom);
            ps.setInt(2, districtTo);
            ps.setString(3, userName);
            ps.setInt(4, packageType);
            ps.setBigDecimal(5, weight);
            
            if (ps.executeUpdate() > 0){
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int insertTransportOffer(String couriersUserName, int packageId, BigDecimal pricePercentage) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "insert into Ponude_Kurir (idKurir,idPaket,procenat) " +
                    "values ((select idKorisnik from Korisnik where kIme=?),?,?)";
            PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, couriersUserName);
            ps.setInt(2, packageId);
            if (pricePercentage == null){
                //slucajan procenat u granicama -10/+10%
                Random rand = new Random();
                ps.setInt(3, rand.nextInt(20) - 10);
            }
            else {
                ps.setBigDecimal(3, pricePercentage);
            }
            if (ps.executeUpdate() > 0){
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public boolean acceptAnOffer(int offerId) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "update Ponude_Kurir set status=1 where idPonuda=?";
            PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, offerId);
            if (ps.executeUpdate() >  0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Integer> getAllOffers() {
        List<Integer> lst = new ArrayList<>();
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "select idPonuda from Ponude_Kurir";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lst.add(rs.getInt("idPonuda"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }
    
    class PackagePair implements PackageOperations.Pair<Integer, BigDecimal> {
        private Integer first;
        private BigDecimal second;
        
        public PackagePair(Integer first, BigDecimal second){
            this.first = first;
            this.second = second;
        }
        @Override
        public Integer getFirstParam() {
            return first;
        }

        @Override
        public BigDecimal getSecondParam() {
            return second;
        }
        
    }
    @Override
    public List<Pair<Integer, BigDecimal>> getAllOffersForPackage(int packageId) {
        List<Pair<Integer, BigDecimal>> lst = new ArrayList<>();
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "select idPaket, procenat from Ponude_Kurir";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery(); 
            while(rs.next()){
                lst.add(new PackagePair(new Integer(rs.getInt("idPaket")), rs.getBigDecimal("procenat")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    @Override
    public boolean deletePackage(int packageId) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "delete from Paket_Prevoz where idPaket=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, packageId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean changeWeight(int packageId, BigDecimal newWeight) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "update Paket_Prevoz set tezinaPaketa=? where idPaket=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setBigDecimal(1, newWeight);
            ps.setInt(2, packageId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean changeType(int packageId, int newType) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "update Paket_Prevoz set tipPaketa=? where idPaket=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, newType);
            ps.setInt(2, packageId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Integer getDeliveryStatus(int packageId) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "select idPaket from Paket_Prevoz where idPaket=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                query = "select status from Paket_Isporuka where idPaket=?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, packageId);
                ResultSet rsStatus = ps.executeQuery();
                if(rsStatus.next()){
                    return rsStatus.getInt("status");   //paket se nalazi u tabeli Paket_Isporuka->status={1,2,3,4}
                }
                else{
                    return 0;   //paket se nalazi samo u tabeli Paket_Prevoz->status=0
                }
            }
            else {
                return null;    //nema paket sa datim id-om
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }        
    

    @Override
    public BigDecimal getPriceOfDelivery(int packageId) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "select cena from Paket_Isporuka where idPaket=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){  //paket se nalazi u tabeli Paket_Isporuka 
                return rs.getBigDecimal("cena");
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Date getAcceptanceTime(int packageId) {
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "select vremePrihvatanja from Paket_Isporuka where idPaket=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){  //paket se nalazi u tabeli Paket_Isporuka 
                return rs.getDate("vremePrihvatanja");
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }

    @Override
    public List<Integer> getAllPackagesWithSpecificType(int type) {
        List<Integer> lst = new ArrayList<>();
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "select idPaket from Paket_Prevoz where tipPaketa=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, type);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lst.add(rs.getInt("idPaket"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;        
    }

    @Override
    public List<Integer> getAllPackages() {
        List<Integer> lst = new ArrayList<>();
        try {
            Connection connection = DB.getInstance().getConnection();
            String query = "select idPaket from Paket_Prevoz";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lst.add(rs.getInt("idPaket"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;  
    }

    @Override
    public List<Integer> getDrive(String courierUsername) {
        List<Integer> lst = new ArrayList<>();
        try {
            Connection connection = DB.getInstance().getConnection();
            //paketi za trenutnu voznju datog kurira, koji nisu jos dostavljeni
            String query = "select P.idPaket from Voznja V,Paket_Isporuka P where V.status=1 and P.status<>3 and " +
                    "P.idVoznja=V.idVoznja and P.idKurir=(select idKorisnik from Korisnik where kIme=?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, courierUsername);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.wasNull()){//u slucaju da nema korisnika sa datim username-om
                    return null;
                }
                lst.add(rs.getInt("idPaket"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst; 
    }

    @Override
    public int driveNextPackage(String courierUserName) {
        try {           
            Connection connection = DB.getInstance().getConnection();
            String query = "select idKorisnik from Korisnik where kIme=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, courierUserName);
            ResultSet rs = ps.executeQuery();
            int idKurir, idPaketPrevoz;
            if(rs.next()){//postoji kurir sa datim username-om
                idKurir = rs.getInt("idKorisnik");
            }
            else{//kurir ne postoji
                return -2;
            }
            query = "select top 1 idPaket from Paket_Isporuka where idKurir=? " +
                        "and status<>3 order by vremePrihvatanja asc";
            ps = connection.prepareStatement(query);
            ps.setInt(1, idKurir);
            rs = ps.executeQuery();
            
            if(rs.next()){//kurir ima paket koji treba da preveze
                idPaketPrevoz = rs.getInt("idPaket");
                int idVoznja;
                query = "select idVoznja from Voznja where idKurir=? and status=1";
                ps = connection.prepareStatement(query);
                ps.setInt(1, idKurir);
                rs = ps.executeQuery();
                if(rs.next()){ //postoji tekuca voznja za datog kurira  
                    idVoznja = rs.getInt("idVoznja");                    
                }
                else{   //ne postoji tekuca voznja za datog kurira -> pravi se voznja
                    query = "insert into Voznja (idKurir) values(?)";
                    ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, idKurir);
                    ps.executeUpdate();
                    rs = ps.getGeneratedKeys();
                    rs.next();
                    idVoznja = rs.getInt(1);
                }
                //prevozi se paket
                query = "update Paket_Isporuka set status=3 where idPaket=? and idVoznja=?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, idPaketPrevoz);
                ps.setInt(2, idVoznja);
                ps.executeUpdate();
                return idPaketPrevoz;
            }
            else {//kurir nema pakete za prevoz
                return -1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vs173101_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
    }


    
}
