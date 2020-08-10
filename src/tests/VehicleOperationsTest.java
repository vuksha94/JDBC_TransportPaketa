// 
// Decompiled by Procyon v0.5.30
// 

package tests;

import org.junit.Test;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import operations.VehicleOperations;
import operations.GeneralOperations;

public class VehicleOperationsTest
{
    private GeneralOperations generalOperations;
    private VehicleOperations vehicleOperations;
    private TestHandler testHandler;
    
    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull((Object)(this.testHandler = TestHandler.getInstance()));
        Assert.assertNotNull((Object)(this.vehicleOperations = this.testHandler.getVehicleOperations()));
        Assert.assertNotNull((Object)(this.generalOperations = this.testHandler.getGeneralOperations()));
        this.generalOperations.eraseAll();
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void insertVehicle() {
        final String licencePlateNumber = "BG234DU";
        final BigDecimal fuelConsumption = new BigDecimal(6.3);
        final int fuelType = 1;
        Assert.assertTrue(this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption));
    }
    
    @Test
    public void deleteVehicles() {
        final String licencePlateNumber = "BG234DU";
        final BigDecimal fuelConsumption = new BigDecimal(6.3);
        final int fuelType = 1;
        this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption);
        Assert.assertEquals(1L, (long)this.vehicleOperations.deleteVehicles(licencePlateNumber));
    }
    
    @Test
    public void getAllVehichles() {
        final String licencePlateNumber = "BG234DU";
        final BigDecimal fuelConsumption = new BigDecimal(6.3);
        final int fuelType = 1;
        this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption);
        Assert.assertTrue(this.vehicleOperations.getAllVehichles().contains(licencePlateNumber));
    }
    
    @Test
    public void changeFuelType() {
        final String licencePlateNumber = "BG234DU";
        final BigDecimal fuelConsumption = new BigDecimal(6.3);
        final int fuelType = 1;
        this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption);
        Assert.assertTrue(this.vehicleOperations.changeFuelType(licencePlateNumber, 2));
    }
    
    @Test
    public void changeConsumption() {
        final String licencePlateNumber = "BG234DU";
        final BigDecimal fuelConsumption = new BigDecimal(6.3);
        final int fuelType = 1;
        this.vehicleOperations.insertVehicle(licencePlateNumber, fuelType, fuelConsumption);
        Assert.assertTrue(this.vehicleOperations.changeConsumption(licencePlateNumber, new BigDecimal(7.3)));
    }
}
