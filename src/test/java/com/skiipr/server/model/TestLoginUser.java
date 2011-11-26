package com.skiipr.server.model;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestLoginUser {

    private static LoginUser user;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        
        String username = "username1";
        String password = "password1";
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean accountNonLocked = true;
        boolean credentialsNonExpired = true;
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = Mockito.mock(GrantedAuthority.class);
        Mockito.when(authority.getAuthority()).thenReturn("ROLE_USER");
        authorities.add(authority);
        user = new LoginUser(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        user.setMerchantId(5l);
        user.setSalt("helloworld");
    }

    @Test
    public void testMerchantId() {
        Long merchantId = user.getMerchantId();
        Long id = 5l;
        assertEquals(merchantId, id);
    }

    @Test
    public void testSalt() {
        String salt = user.getSalt();
        assertEquals(salt, "helloworld");
    }
}
