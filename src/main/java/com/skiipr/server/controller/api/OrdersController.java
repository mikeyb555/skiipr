package com.skiipr.server.controller.api;

import com.skiipr.server.components.POJOBuilders;
import com.skiipr.server.enums.CouponType;
import com.skiipr.server.enums.OrderStatus;
import com.skiipr.server.enums.PaymentType;
import com.skiipr.server.model.Coupon;
import com.skiipr.server.model.DAO.BannedDao;
import com.skiipr.server.model.response.CouponResponse;
import com.skiipr.server.model.DAO.CouponDao;
import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.DAO.OrderProductDao;
import com.skiipr.server.model.Order;
import com.skiipr.server.model.OrderProduct;
import com.skiipr.server.model.response.OrderResponse;
import com.skiipr.server.model.response.StatusResponse;
import com.skiipr.server.model.validators.OrderValidator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrdersController {
    
    @Autowired
    private POJOBuilders builder;
    
    @Autowired
    private BannedDao bannedDao;
    
    @Autowired
    private OrderProductDao orderProductDao;
    
//    @Autowired
//    private OrderValidator orderValidator;
    
    @Autowired
    private CouponDao couponDao;
    
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
            if(orderValidator(order, response)){
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
    
    @RequestMapping(value = "api/orders/coupon", method = RequestMethod.POST)
    public @ResponseBody CouponResponse returnCoupon(@ModelAttribute("payload") RequestBody body){
        CouponResponse response = new CouponResponse();
        try{
            String json = body.getPayload();
            JSONObject jObject = JSONObject.fromObject(json);
            int couponCode = jObject.getInt("couponCode");
            Long merchantID = jObject.getLong("merchantID");
            System.out.println("Yhe coupon code is " + couponCode);
            Coupon coupon = couponDao.findByCode(couponCode, merchantID);
            System.out.println("The coupon is" + coupon.getCouponID());
            response.setResponse(CouponResponse.ResponseStatus.VALID);
            response.setCouponType(coupon.getCouponType());
            response.setValue(coupon.getPercentage());
            
        }catch(Exception e){
            
            response.setResponse(CouponResponse.ResponseStatus.INVALID);
            
        }
        
        return response;
    }
    
    @RequestMapping(value="/api/orders/status/{id}", method = RequestMethod.GET)
    public @ResponseBody StatusResponse findStatus(@PathVariable Long id){
        StatusResponse response = new StatusResponse();
        try{
            
            Order order = orderDao.findByID(id);
            response.setLastUpdated(order.getLastUpdated());
            response.setOrderID(id);
            response.setStatus(order.getStatus());
            
        }catch(Exception e){
            response.setLastUpdated(0l);
            response.setStatus(OrderStatus.ERROR);
        }
        
        return response;
        
        
    }
    
    private Boolean orderValidator(Order order, OrderResponse response){
        
            if(order.getTotal() <= 0.00){
                response.setError(OrderResponse.ResponseErrors.TOTAL_MISMATCH);
            }
            float expectedTotal = 0;
            Set<OrderProduct> orderProducts = order.getOrderProducts();
            for (OrderProduct op: orderProducts){
                float amount = (op.getQuantity() * op.getProduct().getPrice().floatValue());
                expectedTotal += amount;
                
            }
            
            if (order.getTotal() != expectedTotal){
                response.setError(OrderResponse.ResponseErrors.TOTAL_MISMATCH);
                return false;
            }
            
            if (!order.getEmail().matches("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")){
                response.setError(OrderResponse.ResponseErrors.INVALID_EMAIL);
                return false;
            }
            ArrayList<String> identifiers = new ArrayList();
            identifiers.add(order.getDeviceID());
            identifiers.add(order.getEmail());
            if(bannedDao.isBanned(identifiers, order.getMerchantID())){
                response.setError(OrderResponse.ResponseErrors.BLOCKED);
                return false;
            }
            
            if(!order.getMerchant().getOpen()){
                response.setError(OrderResponse.ResponseErrors.MERCHANT_CLOSED);
                return false;
            }
            
            if(order.getPaymentType().equals(PaymentType.PAYPAL) && !(order.getMerchant().getPaypalEnabled())){
                
                response.setError(OrderResponse.ResponseErrors.PAYMENT_UNSUPPORTED);
                return false;
            
                
            }
            
            if(order.getPaymentType().equals(PaymentType.COD) && !(order.getMerchant().getCodEnabled())){
                response.setError(OrderResponse.ResponseErrors.PAYMENT_UNSUPPORTED);
                return false;
            }
            return true;
            
            
            
            
            
            
        
        
        
    }
    
    
}
