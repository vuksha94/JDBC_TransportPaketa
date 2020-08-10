import java.util.regex.Matcher;
import java.util.regex.Pattern;
import operations.*;
import tests.TestHandler;
import tests.TestRunner;
import student.*;


public class StudentMain {

    public static void main(String[] args) {


        CityOperations cityOperations = new vs173101_CityOperations();
        DistrictOperations districtOperations = new vs173101_DistrictOperations();
        VehicleOperations vehicleOperations = new vs173101_VehicleOperations();
        UserOperations userOperations = new vs173101_UserOperations();
        CourierRequestOperation courierRequestOperation = new vs173101_CourierRequestOperation();
        CourierOperations courierOperations = new vs173101_CourierOperations();
        GeneralOperations generalOperations = new vs173101_GeneralOperations();
        PackageOperations packageOperations = new vs173101_PackageOperations();
        System.out.println(userOperations.insertUser("le", "Stefan", "Prezime", "asdasd123"));
        TestHandler.createInstance(
                cityOperations,
                courierOperations,
                courierRequestOperation,
                districtOperations,
                generalOperations,
                userOperations,
                vehicleOperations,
                packageOperations);

        TestRunner.runTests();
        
        System.out.println((districtOperations.districtDistance(746,747)));
        /*
         
        generalOperations.er
        
        
        UserOperations userOperations = null;
        
        */

       /* cityOperations.insertCity("Beograd", "11000");
        cityOperations.insertCity("NS", "11000");
        cityOperations.insertCity("SD", "11000");
       districtOperations.insertDistrict("Zvezdara", 18, 0, 0);
       districtOperations.insertDistrict("Palilula", 18, 0, 0);
       districtOperations.insertDistrict("Stari Grad", 18, 0, 0);
       districtOperations.insertDistrict("Stari Grad", 17, 0, 0);
       
       vehicleOperations.insertVehicle("NS100LL", 0, new BigDecimal("6"));
       vehicleOperations.insertVehicle("ČA122LJ", 2, new BigDecimal("6.5"));
       if(vehicleOperations.deleteVehicle(new String[]{"NS100LL", "ČA122LJ"})){
           System.out.println(1);
       }
       List<String> lst = vehicleOperations.getAllVehichles();
       for(String s: lst){
           System.out.println(s);
       }*/
       //userOperations.insertUser("vuksha94", "Stefan", "Vukašinović", "vuksha");
       //userOperations.insertUser("mika94", "Milan", "Petric", "miki");
       //System.out.println(userOperations.getSentPackages(new String[]{"vuksha94","mika94"}));
       //System.out.println(userOperations.declareAdmin("marin"));
        //System.out.println(userOperations.deleteUsers(new String[]{"vuksha94","marin","asd"}));
        //List<String> lst = courierRequestOperation.getAllCourierRequests();
        //for(String s: lst){
        //   System.out.println(s);
        //}
        //System.out.println(courierOperations.insertCourier("vuksha94","NS100LL"));
        //System.out.println(courierRequestOperation.grantRequest("mika94"));

        //System.out.println(courierOperations.insertCourier("vuksha94","BG609SS"));
        //System.out.println(courierOperations.getCouriersWithStatus(0));
        
        //System.out.println(courierOperations.getAverageCourierProfit(0));
        

       
/*      
        System.out.println(courierRequestOperation.changeVehicleInCourierRequest("vuksha94", "BG609SS"));
        vehicleOperations.insertVehicle("NS100LL", 0, new BigDecimal("6"));
        vehicleOperations.insertVehicle("ČA122LJ", 2, new BigDecimal("6.5"));
        System.out.println(courierRequestOperation.insertCourierRequest("vuksha94", "NS100Lg"));
        System.out.println(courierRequestOperation.insertCourierRequest("vuksha94", "NS100LL"));

        System.out.println(courierRequestOperation.insertCourierRequest("vuksha94", "ČA122LJ"));
        System.out.println(courierRequestOperation.insertCourierRequest("mika943", "NS100LL"));
        System.out.println(courierRequestOperation.insertCourierRequest("mika94", "NS100LL"));

        courierRequestOperation.insertCourierRequest("vuksha94", "NS100LL");
        courierRequestOperation.insertCourierRequest("vuksha94", "ČA122LJ");
        courierRequestOperation.insertCourierRequest("mika943", "NS100LL");
        courierRequestOperation.insertCourierRequest("mika94", "NS100LL");
*/
       // System.out.println(pk);
      
        


    }
}
