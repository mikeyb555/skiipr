package com.skiipr.server.components;

import com.skiipr.server.enums.OrderStatus;
import com.skiipr.server.enums.OrderType;
import com.skiipr.server.enums.PaymentType;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Order;
import com.skiipr.server.model.Product;
import java.util.HashSet;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class POJOBuilders {

    @Autowired
    private MerchantDao merchantDao;
    
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
        Set<Product> products = new HashSet<Product>();
        JSONArray productList = jObject.getJSONArray("products");
        for(int i=0;i < productList.size();i++){
            JSONObject jProduct = productList.getJSONObject(i);
            
            Product product = productDao.findByID(jProduct.getLong("productID"));
            for(int count=0;count <= jProduct.getInt("quantity");count++){
                products.add(product);
            }
            
            
        }
        order.setProducts(products);
        
        return order;
    }
}
