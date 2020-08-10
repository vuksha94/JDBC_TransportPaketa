// 
// Decompiled by Procyon v0.5.30
// 

package operations;

import java.util.List;
import com.sun.istack.internal.NotNull;

public interface CityOperations
{
    int insertCity(final String p0, final String p1);
    
    int deleteCity(@NotNull final String... p0);
    
    boolean deleteCity(final int p0);
    
    List<Integer> getAllCities();
}
