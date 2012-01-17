package com.skiipr.server.components;

import com.skiipr.server.enums.OrderStatus;
import com.skiipr.server.enums.OrderType;
import com.skiipr.server.enums.PaymentType;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.DAO.OrderProductDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Order;
import com.skiipr.server.model.OrderProduct;
import com.skiipr.server.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class POJOBuilders {
    
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired
    private OrderProductDao orderProductDao;
    
    @Autowired
    private ProductDao productDao;
    
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
    
    public ArrayList<OrderProduct> createOrderProductFromJson(String json, Order order){
        JSONObject jObject = JSONObject.fromObject(json);
        ArrayList<OrderProduct> opList = new ArrayList();
        JSONArray productList = jObject.getJSONArray("products");
        
        ArrayList<Long> productIDs = new ArrayList();
        ArrayList<Integer> productQuantity = new ArrayList();
        
        for(int i=0;i < productList.size();i++){
            JSONObject jProduct = productList.getJSONObject(i);
            productIDs.add(jProduct.getLong("productID"));
            
        }
        for(int i=0;i < productList.size();i++){
            JSONObject jQuantity = productList.getJSONObject(i);
            productQuantity.add(jQuantity.getInt("quantity"));
            
        }
        List<Product> products = productDao.findByCollection(productIDs, jObject.getLong("merchantID"));
        
        for(int b=0;b < productIDs.size();b++){
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(products.get(b));
            orderProduct.setUserOrder(order);
            orderProduct.setQuantity(productQuantity.get(b));
            opList.add(orderProduct);
            
        }
        
        
        
        
        return opList;
        
        
        
    }
}
