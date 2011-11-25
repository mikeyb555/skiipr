package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Plan;
import java.util.List;

public interface PlanDao {
    public void save(Plan plan);
    public void update(Plan plan);
    public void delete(Plan plan);
    public Plan findByID(Long id);
    public List<Plan> findAll();
}
