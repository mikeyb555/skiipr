package com.skiipr.server.controller.api;

import com.skiipr.server.components.POJOBuilders;
import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.Order;
import com.skiipr.server.model.OrderResponse;
import com.skiipr.server.model.validators.OrderValidator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrdersController {
    
    @Autowired
    private POJOBuilders builder;
    
    @Autowired
    private OrderValidator orderValidator;
    
    @Autowired
    private OrderDao orderDao;

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
    
    
    
    @RequestMapping(value="/api/orders/submit", method = RequestMethod.POST)
    public @ResponseBody OrderResponse submitOrder(@ModelAttribute("payload") RequestBody body){
        OrderResponse response = new OrderResponse();
        try{
            String json = body.getPayload();
            System.out.println(json);
            Order order = builder.createOrderFromJson(json);
            BindException error = new BindException(order, "Order");
            orderValidator.validate(order, error);
            System.out.println("Has Errors 2: " + error.hasErrors());
            if(error.hasErrors() == true){
                response.setResponse(OrderResponse.ResponseStatus.ERROR);
                response.setError(OrderResponse.ResponseErrors.SERVER_ERROR);
                response.setOrderID(null); 
                if(error.hasFieldErrors("total")){
                    response.setError(OrderResponse.ResponseErrors.TOTAL_MISMATCH);
                }
            }else{
                orderDao.save(order);
                response.setResponse(OrderResponse.ResponseStatus.SUCCESS);
                response.setError(OrderResponse.ResponseErrors.NONE);
                response.setOrderID(order.getOrderID());
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
            response.setResponse(OrderResponse.ResponseStatus.ERROR);
            response.setError(OrderResponse.ResponseErrors.SERVER_ERROR);
            response.setOrderID(null);           
        }
        return response;
    }
    
    @RequestMapping(value = "api/orders/cancel", method = RequestMethod.POST)
    public @ResponseBody OrderResponse cancelOrder(@ModelAttribute("payload") RequestBody body){
        OrderResponse response = new OrderResponse();
        try{
            String json = body.getPayload();
            System.out.println(json);
            JSONObject jObject = JSONObject.fromObject(json);
            Long orderID = jObject.getLong("orderID");
            Order order = orderDao.findByID(orderID);
            orderDao.delete(order);
            response.setResponse(OrderResponse.ResponseStatus.SUCCESS);
            response.setOrderID(orderID);
            response.setError(OrderResponse.ResponseErrors.NONE);
        }catch(Exception e){
            
            response.setResponse(OrderResponse.ResponseStatus.ERROR);
            response.setError(OrderResponse.ResponseErrors.DETAILS_INVALID);
        }
        System.out.println(response.toString());
        return response;
    }
}
