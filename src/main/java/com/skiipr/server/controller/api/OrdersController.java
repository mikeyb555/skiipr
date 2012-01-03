package com.skiipr.server.controller.api;

import com.skiipr.server.components.POJOBuilders;
import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.Order;
import com.skiipr.server.model.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrdersController {
    
    @Autowired
    private POJOBuilders builder;
    
    @Autowired
    private OrderDao orderDao;

    public class RequestBody{
        private String order;
        
        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }
    
    @ModelAttribute("order")
    public RequestBody getRequestBinder(){
        return new RequestBody();
    }
    
    @RequestMapping(value="/api/orders/submit", method = RequestMethod.POST)
    public @ResponseBody OrderResponse submitOrder(@ModelAttribute("order") RequestBody body){
        String json = body.getOrder();
        System.out.println(json);
        Order order = builder.createOrderFromJson(json);
        System.out.println("email: " + order.getEmail());
        orderDao.save(order);
        OrderResponse response = new OrderResponse();
        response.setResponse(OrderResponse.ResponseStatus.SUCCESS);
        response.setError(OrderResponse.ResponseErrors.NONE);
        response.setOrderID(12345);
        return response;
    }
}
