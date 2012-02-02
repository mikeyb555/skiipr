package com.skiipr.server.model.form;

import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import org.springframework.validation.Errors;

public class CategoryForm {
    private Long categoryID;
    private String name;

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setAttributes(Category category){
        category.setName(name);
    }
    
    public void getAttributes(Category category){
        name = category.getName();
        categoryID = category.getCategoryID();
    }
    
    public boolean validate(CategoryDao dao, Errors errors){
        if(dao.nameInUse(name, categoryID)){
            errors.rejectValue("name", "invalid.category.name.inuse");
        }
        
        if(name.isEmpty()){
            errors.rejectValue("name", "invalid.category.name.null");
        }
        return !errors.hasErrors();
    }
}
