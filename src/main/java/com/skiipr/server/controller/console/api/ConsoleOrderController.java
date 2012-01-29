package com.skiipr.server.controller.console.api;

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
    
    @RequestMapping(value="/console/api/order/list", method = RequestMethod.GET)
    public @ResponseBody List<Order> getOrders(){
        return orderDao.findConsoleOrders();
    }
    
    @RequestMapping(value="/console/api/order/{id}", method = RequestMethod.GET)
    public @ResponseBody Order getSingleOrder(@PathVariable("id") Long orderID){
        return orderDao.findOrderByMerchant(orderID);
    }
}
