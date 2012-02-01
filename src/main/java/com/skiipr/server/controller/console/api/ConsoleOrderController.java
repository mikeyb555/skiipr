package com.skiipr.server.controller.console.api;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.Order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @RequestMapping(value="/console/api/order/change", method = RequestMethod.GET)
    public @ResponseBody int getLastChanged(){
        return merchantDao.getChangeToken();
    }
}
