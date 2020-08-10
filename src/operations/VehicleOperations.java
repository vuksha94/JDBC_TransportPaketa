// 
// Decompiled by Procyon v0.5.30
// 

package operations;

import java.util.List;
import java.math.BigDecimal;
import com.sun.istack.internal.NotNull;

public interface VehicleOperations
{
    boolean insertVehicle(@NotNull final String p0, final int p1, final BigDecimal p2);
    
    int deleteVehicles(@NotNull final String... p0);
    
    List<String> getAllVehichles();
    
    boolean changeFuelType(@NotNull final String p0, final int p1);
    
    boolean changeConsumption(@NotNull final String p0, final BigDecimal p1);
}
