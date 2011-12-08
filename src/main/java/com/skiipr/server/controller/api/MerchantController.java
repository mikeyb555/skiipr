/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.api;

import com.skiipr.server.httpresponses.NotFound;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MerchantController {
    
    @Autowired
    MerchantDao merchantDao;
    
    @RequestMapping(value="/api/merchant/find/{id}", method = RequestMethod.GET)
    public @ResponseBody Merchant findMerchant(@PathVariable Long id){
        Merchant merchant = merchantDao.findById(id);
        if(merchant == null){
            throw new NotFound();
        }
        return merchant;
    }
    
    @RequestMapping(value="/api/merchant/all", method = RequestMethod.GET)
    public @ResponseBody List<Merchant> findAll(){
        List<Merchant> merchants = merchantDao.findAll();
        return merchants;
    }
    
    @RequestMapping(value="/api/merchant/withintenkm/{lat}/{lon}", method = RequestMethod.GET)
    public @ResponseBody List<Merchant> withinTenKm(@PathVariable double lat, @PathVariable double lon){
        List<Merchant> merchants = merchantDao.findWithinRadius(lat, lon, 10);
        return merchants;
    } 
}
