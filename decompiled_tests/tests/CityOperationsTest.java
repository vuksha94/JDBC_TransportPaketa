// 
// Decompiled by Procyon v0.5.30
// 

package tests;

import java.util.Random;
import org.junit.Test;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import operations.CityOperations;
import operations.GeneralOperations;

public class CityOperationsTest
{
    private TestHandler testHandler;
    private GeneralOperations generalOperations;
    private CityOperations cityOperations;
    
    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull((Object)(this.testHandler = TestHandler.getInstance()));
        Assert.assertNotNull((Object)(this.cityOperations = this.testHandler.getCityOperations()));
        Assert.assertNotNull((Object)(this.generalOperations = this.testHandler.getGeneralOperations()));
        this.generalOperations.eraseAll();
    }
    
    @After
    public void tearDown() throws Exception {
        this.generalOperations.eraseAll();
    }
    
    @Test
    public void insertCity_OnlyOne() throws Exception {
        final String name = "Tokyo";
        final String postalCode = "100";
        final int rowId = this.cityOperations.insertCity(name, postalCode);
        final List<Integer> list = this.cityOperations.getAllCities();
        Assert.assertEquals(1L, (long)list.size());
        Assert.assertTrue(list.contains(rowId));
    }
    
    @Test
    public void insertCity_TwoCities_SameBothNameAndPostalCode() throws Exception {
        final String name = "Tokyo";
        final String postalCode = "100";
        final int rowIdValid = this.cityOperations.insertCity(name, postalCode);
        final int rowIdInvalid = this.cityOperations.insertCity(name, postalCode);
        Assert.assertEquals(-1L, (long)rowIdInvalid);
        final List<Integer> list = this.cityOperations.getAllCities();
        Assert.assertEquals(1L, (long)list.size());
        Assert.assertTrue(list.contains(rowIdValid));
    }
    
    @Test
    public void insertCity_TwoCities_SameName() throws Exception {
        final String name = "Tokyo";
        final String postalCode1 = "100";
        final String postalCode2 = "1020";
        final int rowIdValid = this.cityOperations.insertCity(name, postalCode1);
        final int rowIdInvalid = this.cityOperations.insertCity(name, postalCode2);
        Assert.assertEquals(-1L, (long)rowIdInvalid);
        final List<Integer> list = this.cityOperations.getAllCities();
        Assert.assertEquals(1L, (long)list.size());
        Assert.assertTrue(list.contains(rowIdValid));
    }
    
    @Test
    public void insertCity_TwoCities_SamePostalCode() throws Exception {
        final String name1 = "Tokyo";
        final String name2 = "Beijing";
        final String postalCode = "100";
        final int rowIdValid = this.cityOperations.insertCity(name1, postalCode);
        final int rowIdInvalid = this.cityOperations.insertCity(name2, postalCode);
        Assert.assertEquals(-1L, (long)rowIdInvalid);
        final List<Integer> list = this.cityOperations.getAllCities();
        Assert.assertEquals(1L, (long)list.size());
        Assert.assertTrue(list.contains(rowIdValid));
    }
    
    @Test
    public void insertCity_MultipleCities() throws Exception {
        final String name1 = "Tokyo";
        final String name2 = "Beijing";
        final String postalCode1 = "100";
        final String postalCode2 = "065001";
        final int rowId1 = this.cityOperations.insertCity(name1, postalCode1);
        final int rowId2 = this.cityOperations.insertCity(name2, postalCode2);
        final List<Integer> list = this.cityOperations.getAllCities();
        Assert.assertEquals(2L, (long)list.size());
        Assert.assertTrue(list.contains(rowId1));
        Assert.assertTrue(list.contains(rowId2));
    }
    
    @Test
    public void deleteCity_WithId_OnlyOne() {
        final String name = "Beijing";
        final String postalCode = "065001";
        final int rowId = this.cityOperations.insertCity(name, postalCode);
        Assert.assertNotEquals(-1L, (long)rowId);
        Assert.assertTrue(this.cityOperations.deleteCity(rowId));
        Assert.assertEquals(0L, (long)this.cityOperations.getAllCities().size());
    }
    
    @Test
    public void deleteCity_WithId_OnlyOne_NotExisting() {
        final Random random = new Random();
        final int rowId = random.nextInt();
        Assert.assertFalse(this.cityOperations.deleteCity(rowId));
        Assert.assertEquals(0L, (long)this.cityOperations.getAllCities().size());
    }
    
    @Test
    public void deleteCity_WithName_One() {
        final String name = "Beijing";
        final String postalCode = "065001";
        final int rowId = this.cityOperations.insertCity(name, postalCode);
        Assert.assertNotEquals(-1L, (long)rowId);
        Assert.assertEquals(1L, (long)this.cityOperations.deleteCity(name));
        Assert.assertEquals(0L, (long)this.cityOperations.getAllCities().size());
    }
    
    @Test
    public void deleteCity_WithName_MultipleCities() throws Exception {
        final String name1 = "Tokyo";
        final String name2 = "Beijing";
        final String postalCode1 = "100";
        final String postalCode2 = "065001";
        final int rowId1 = this.cityOperations.insertCity(name1, postalCode1);
        final int rowId2 = this.cityOperations.insertCity(name2, postalCode2);
        final List<Integer> list = this.cityOperations.getAllCities();
        Assert.assertEquals(2L, (long)list.size());
        Assert.assertEquals(2L, (long)this.cityOperations.deleteCity(name1, name2));
    }
    
    @Test
    public void deleteCity_WithName_OnlyOne_NotExisting() {
        final String name = "Tokyo";
        Assert.assertEquals(0L, (long)this.cityOperations.deleteCity(name));
        Assert.assertEquals(0L, (long)this.cityOperations.getAllCities().size());
    }
}
