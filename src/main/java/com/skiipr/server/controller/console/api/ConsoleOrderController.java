package com.skiipr.server.controller.console.api;

import com.skiipr.server.components.PushNotify;
import com.skiipr.server.enums.OrderStatus;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConsoleOrderController {
    
    @Autowired
    OrderDao orderDao;
    
    @Autowired
    MerchantDao merchantDao;
    
    @RequestMapping(value="/console/api/order/list", method = RequestMethod.GET)
    public @ResponseBody List<Order> getOrders(){
        List<Order> orders = orderDao.findConsoleOrders();
        for(Order order : orders){
            order.setOrderProducts(null);
        }
        return orders;
    }
    
    @RequestMapping(value="/console/api/order/{id}", method = RequestMethod.GET)
    public @ResponseBody Order getSingleOrder(@PathVariable("id") Long orderID){
        return orderDao.findOrderByMerchant(orderID);
    }
    
    @RequestMapping(value="/console/api/merchant/open", method = RequestMethod.GET)
    public @ResponseBody String getMerchantOpen(){
        Merchant merchant = merchantDao.findCurrentMerchant();
        return merchant.getOpen().toString();
    }
    
    @RequestMapping(value="/console/api/order/change", method = RequestMethod.GET)
    public @ResponseBody Long getLastChanged(){
        return merchantDao.getChangeToken();
    }
    
     @RequestMapping(value="/console/api/merchant/open", method = RequestMethod.POST)
    public @ResponseBody String updateStatus(@RequestParam("open") String open){
         Merchant merchant = merchantDao.findCurrentMerchant();
         merchant.setOpen(open.equals("true"));
         merchantDao.update(merchant);
         return "complete";
    }
    
    @RequestMapping(value="/console/api/order/{id}/update", method = RequestMethod.POST)
    public @ResponseBody String updateStatus(@PathVariable("id") Long orderID, @RequestParam("status") String status){
        Order order = orderDao.findOrderByMerchant(orderID);
        if(status.contentEquals("READY")){
            order.setStatus(OrderStatus.READY);
            try {
                PushNotify.notifyOrderReady(order.getApid());
            } catch (Exception ex){
                System.out.println(ex.toString());
            }
        }else if(status.contentEquals("COMPLETE")){
            order.setStatus(OrderStatus.COMPLETE);
        }else{
            return "error";
        }
        Merchant merchant = merchantDao.findCurrentMerchant();
        Long time = new java.util.Date().getTime() / 1000;
        merchant.setLastChange(time);
        orderDao.update(order);
        merchantDao.update(merchant);
        return "complete";
    }

}
