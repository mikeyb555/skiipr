package com.skiipr.server.components;

import com.skiipr.server.enums.OrderStatus;
import com.skiipr.server.enums.OrderType;
import com.skiipr.server.enums.PaymentType;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Order;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class POJOBuilders {

    @Autowired
    private MerchantDao merchantDao;
    
    public Order createOrderFromJson(String json){
        Order order = new Order();
        JSONObject jObject = JSONObject.fromObject(json);
        Long merchantID = jObject.getLong("merchantID");
        System.out.println("Merchant ID: " + merchantID);
        if(merchantDao == null){
            System.out.println("merchantDao is null");
        }
        Merchant merchant = merchantDao.findById(merchantID);
        order.setMerchant(merchant);
        order.setOrderTime(jObject.getLong("orderTime"));
        String stringTotal = jObject.getString("total");
        order.setTotal(new Float(stringTotal));
        order.setOrderType(OrderType.NORMAL);
        order.setEmail(jObject.getString("email"));
        order.setDeviceID(jObject.getString("deviceID"));
        Long time = System.currentTimeMillis()/1000;
        order.setLastUpdated(time);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentType(PaymentType.PAYPAL);
        return order;
    }
}
