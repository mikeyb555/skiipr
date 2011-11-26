/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Configurable;
import com.skiipr.server.model.DAO.PlanDao;
import com.skiipr.server.model.Plan;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:testApplicationContext.xml"})
public class PlanDaoImplTest {
    
    @Autowired
    private PlanDao planDao;

    @Test
    public void testFindAll() {
        List<Plan> plans = planDao.findAll();
        assertEquals(plans.size(), 2);
    }
}
