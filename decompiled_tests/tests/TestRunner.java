// 
// Decompiled by Procyon v0.5.30
// 

package tests;

import org.junit.runner.Result;
import org.junit.runner.Request;
import org.junit.runner.JUnitCore;

public final class TestRunner
{
    private static final int MAX_POINTS_ON_PUBLIC_TEST = 10;
    private static Class[] unitTestClasses;
    private static Class[] moduleTestClasses;
    
    private static double runUnit() {
        double numberOfSuccessfulCases = 0.0;
        double numberOfAllCases = 0.0;
        double points = 0.0;
        final JUnitCore jUnitCore = new JUnitCore();
        for (final Class testClass : TestRunner.unitTestClasses) {
            System.out.println("\n" + testClass.getName());
            final Request request = Request.aClass(testClass);
            final Result result = jUnitCore.run(request);
            System.out.println("Successful:" + (result.getRunCount() - result.getFailureCount()));
            System.out.println("All:" + result.getRunCount());
            numberOfAllCases = result.getRunCount();
            numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
            points += numberOfSuccessfulCases / numberOfAllCases;
        }
        return points;
    }
    
    private static double runModule() {
        double numberOfSuccessfulCases = 0.0;
        double numberOfAllCases = 0.0;
        double points = 0.0;
        final JUnitCore jUnitCore = new JUnitCore();
        for (final Class testClass : TestRunner.moduleTestClasses) {
            System.out.println("\n" + testClass.getName());
            final Request request = Request.aClass(testClass);
            final Result result = jUnitCore.run(request);
            System.out.println("Successful:" + (result.getRunCount() - result.getFailureCount()));
            System.out.println("All:" + result.getRunCount());
            numberOfAllCases = result.getRunCount();
            numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
            points += numberOfSuccessfulCases / numberOfAllCases;
        }
        return points;
    }
    
    private static double runPublic() {
        double res = 0.0;
        res += runUnit() * 2.0;
        res += runModule() * 2.0;
        return res;
    }
    
    public static void runTests() {
        final double resultsPublic = runPublic();
        System.out.println("Points won on public tests is: " + resultsPublic);
    }
    
    static {
        TestRunner.unitTestClasses = new Class[] { CityOperationsTest.class, DistrictOperationsTest.class, UserOperationsTest.class, VehicleOperationsTest.class };
        TestRunner.moduleTestClasses = new Class[] { PublicModuleTest.class };
    }
}
