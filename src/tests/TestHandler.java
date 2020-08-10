// 
// Decompiled by Procyon v0.5.30
// 

package tests;

import com.sun.istack.internal.NotNull;
import operations.PackageOperations;
import operations.VehicleOperations;
import operations.UserOperations;
import operations.GeneralOperations;
import operations.DistrictOperations;
import operations.CourierRequestOperation;
import operations.CourierOperations;
import operations.CityOperations;

public class TestHandler
{
    private static TestHandler testHandler;
    private CityOperations cityOperations;
    private CourierOperations courierOperations;
    private CourierRequestOperation courierRequestOperation;
    private DistrictOperations districtOperations;
    private GeneralOperations generalOperations;
    private UserOperations userOperations;
    private VehicleOperations vehicleOperations;
    private PackageOperations packageOperations;
    
    private TestHandler(@NotNull final CityOperations cityOperations, @NotNull final CourierOperations courierOperations, @NotNull final CourierRequestOperation courierRequestOperation, @NotNull final DistrictOperations districtOperations, @NotNull final GeneralOperations generalOperations, @NotNull final UserOperations userOperations, @NotNull final VehicleOperations vehicleOperations, @NotNull final PackageOperations packageOperations) {
        this.cityOperations = cityOperations;
        this.courierOperations = courierOperations;
        this.courierRequestOperation = courierRequestOperation;
        this.districtOperations = districtOperations;
        this.generalOperations = generalOperations;
        this.userOperations = userOperations;
        this.vehicleOperations = vehicleOperations;
        this.packageOperations = packageOperations;
    }
    
    public static void createInstance(@NotNull final CityOperations cityOperations, @NotNull final CourierOperations courierOperations, @NotNull final CourierRequestOperation courierRequestOperation, @NotNull final DistrictOperations districtOperations, @NotNull final GeneralOperations generalOperations, @NotNull final UserOperations userOperations, @NotNull final VehicleOperations vehicleOperations, @NotNull final PackageOperations packageOperations) {
        TestHandler.testHandler = new TestHandler(cityOperations, courierOperations, courierRequestOperation, districtOperations, generalOperations, userOperations, vehicleOperations, packageOperations);
    }
    
    static TestHandler getInstance() {
        return TestHandler.testHandler;
    }
    
    CityOperations getCityOperations() {
        return this.cityOperations;
    }
    
    CourierOperations getCourierOperations() {
        return this.courierOperations;
    }
    
    CourierRequestOperation getCourierRequestOperation() {
        return this.courierRequestOperation;
    }
    
    DistrictOperations getDistrictOperations() {
        return this.districtOperations;
    }
    
    GeneralOperations getGeneralOperations() {
        return this.generalOperations;
    }
    
    UserOperations getUserOperations() {
        return this.userOperations;
    }
    
    VehicleOperations getVehicleOperations() {
        return this.vehicleOperations;
    }
    
    PackageOperations getPackageOperations() {
        return this.packageOperations;
    }
    
    static {
        TestHandler.testHandler = null;
    }
}
