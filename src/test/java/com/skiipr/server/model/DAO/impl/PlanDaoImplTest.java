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
import junit.framework.Assert;
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
        Plan plan1 = plans.get(0);
        Assert.assertEquals(plan1.getPlanName(), "plan1");
        Plan plan2 = plans.get(1);
        Assert.assertEquals(plan2.getPlanName(), "plan3");
    }
    
    @Test
    public void testFindById(){
        Plan plan = planDao.findByID(3l);
        Assert.assertEquals(plan.getPlanName(), "plan3");
    }
    
    @Test
    public void testSave(){
        Plan plan = new Plan();
        plan.setPlanName("foobar");
        plan.setCanPaypal(true);
        planDao.save(plan);
        List<Plan> plans = planDao.findAll();
        assertEquals(plans.size(), 3);
        plan = plans.get(2);
        Assert.assertEquals(plan.getPlanName(), "foobar");
    }
    
    @Test
    public void testUpdate(){
        Plan plan = planDao.findByID(3l);
        Assert.assertEquals(plan.getPlanName(), "plan3");
        plan.setPlanName("hello_world");
        planDao.update(plan);
        plan = planDao.findByID(3l);
        Assert.assertEquals(plan.getPlanName(), "hello_world");
    }
    
    @Test
    public void testDelete(){
        Plan plan = planDao.findByID(3l);
        planDao.delete(plan);
        List<Plan> plans = planDao.findAll();
        assertEquals(plans.size(), 2);
    }
}
