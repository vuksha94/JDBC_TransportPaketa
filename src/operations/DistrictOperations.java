// 
// Decompiled by Procyon v0.5.30
// 

package operations;

import java.util.List;
import com.sun.istack.internal.NotNull;
import java.math.BigDecimal;

public interface DistrictOperations
{
    int insertDistrict(final String p0, final int p1, final int p2, final int p3);
    
    int deleteDistricts(@NotNull final String... p0);
    
    boolean deleteDistrict(final int p0);
    
    int deleteAllDistrictsFromCity(@NotNull final String p0);
    
    List<Integer> getAllDistrictsFromCity(final int p0);
    
    List<Integer> getAllDistricts();

    public BigDecimal districtDistance(int i, int i0);
}
