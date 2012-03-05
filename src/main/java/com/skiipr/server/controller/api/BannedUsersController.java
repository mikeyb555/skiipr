/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.api;

import com.skiipr.server.components.POJOBuilders;
import com.skiipr.server.httpresponses.NotFound;
import com.skiipr.server.model.Banned;
import com.skiipr.server.model.DAO.BannedDao;
import com.skiipr.server.model.response.BannedResponse;
import java.net.BindException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Michael
 */
@Controller
public class BannedUsersController {
    @Autowired
    BannedDao bannedDao;
    
    @Autowired
    private POJOBuilders builder;
    
    @RequestMapping(value="/api/banned/find/{id}", method = RequestMethod.GET)
    public @ResponseBody Banned findCategory(@PathVariable Long id){
        Banned banned = bannedDao.getBan(id);
        if(banned== null){
            throw new NotFound();
        }
        return banned;
    }
    
    @RequestMapping(value="/api/banned/all", method = RequestMethod.GET)
    public @ResponseBody List<Banned> findAll(){
        List<Banned> bannedAll = bannedDao.findAll();
        return bannedAll;
    }  
    
    public class RequestBody{
        private String payload;
        
        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }
    
    @ModelAttribute("payload")
    public RequestBody getRequestBinder(){
        return new RequestBody();
    }
    
    
    
    @RequestMapping(value="/api/banned/submit", method = RequestMethod.POST)
    public @ResponseBody BannedResponse submitOrder(@ModelAttribute("payload") RequestBody body){
        BannedResponse response = new BannedResponse();
        try{
            String json = body.getPayload();
            System.out.println(json);
            Banned banned = builder.createBannedFromJson(json);
            if(true){
                bannedDao.save(banned);
                response.setResponse(BannedResponse.ResponseStatus.SUCCESS);
                response.setError(BannedResponse.ResponseErrors.NONE);
                response.setBannedID(banned.getBannedID());
            }
            else{
                response.setError(BannedResponse.ResponseErrors.SERVER_ERROR);
                response.setResponse(BannedResponse.ResponseStatus.ERROR);
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
            for(StackTraceElement el : ex.getStackTrace()){
                System.out.println(el);
            }
            response.setResponse(BannedResponse.ResponseStatus.ERROR);
            response.setError(BannedResponse.ResponseErrors.SERVER_ERROR);
            response.setBannedID(null);           
        }
        return response;
    }
    
    
   
    

    
}
