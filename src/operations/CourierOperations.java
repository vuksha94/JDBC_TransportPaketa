// 
// Decompiled by Procyon v0.5.30
// 

package operations;

import java.math.BigDecimal;
import java.util.List;
import com.sun.istack.internal.NotNull;

public interface CourierOperations
{
    boolean insertCourier(@NotNull final String p0, @NotNull final String p1);
    
    boolean deleteCourier(@NotNull final String p0);
    
    List<String> getCouriersWithStatus(final int p0);
    
    List<String> getAllCouriers();
    
    BigDecimal getAverageCourierProfit(final int p0);
}
