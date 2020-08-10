// 
// Decompiled by Procyon v0.5.30
// 

package operations;

import java.util.List;
import com.sun.istack.internal.NotNull;

public interface CourierRequestOperation
{
    boolean insertCourierRequest(@NotNull final String p0, @NotNull final String p1);
    
    boolean deleteCourierRequest(@NotNull final String p0);
    
    boolean changeVehicleInCourierRequest(@NotNull final String p0, @NotNull final String p1);
    
    List<String> getAllCourierRequests();
    
    boolean grantRequest(@NotNull final String p0);
}
