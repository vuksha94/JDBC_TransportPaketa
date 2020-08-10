// 
// Decompiled by Procyon v0.5.30
// 

package operations;

import java.util.List;
import com.sun.istack.internal.NotNull;

public interface UserOperations
{
    boolean insertUser(@NotNull final String p0, @NotNull final String p1, @NotNull final String p2, @NotNull final String p3);
    
    int declareAdmin(@NotNull final String p0);
    
    Integer getSentPackages(@NotNull final String... p0);
    
    int deleteUsers(@NotNull final String... p0);
    
    List<String> getAllUsers();
}
