/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.dashboard;

import com.skiipr.server.enums.OrderType;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.OrderDao;
import com.skiipr.server.model.DAO.OrderProductDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Order;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private OrderProductDao orderProductDao;
    
     @RequestMapping(value = "/dashboard/orders/view/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("order", orderDao.findOrderByMerchant(id));
        uiModel.addAttribute("products", orderProductDao.findByOrderID(id));
        uiModel.addAttribute("itemId", id);
        return "/dashboard/orders/view";
    }
    
   @RequestMapping(value = "/dashboard/orders", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if(size == null){
            size = 10;
        }
        if(page == null){
            page = 1;
        }
        Integer sizeNo = this.getPageSize(size);
        Integer startPage = this.getStartPage(page, sizeNo);
        model.addAttribute("orders", orderDao.findRange(startPage, sizeNo));
        model.addAttribute("maxPages", this.getMaxPages(sizeNo));
        return "/dashboard/orders/list";
    }
   @RequestMapping(value = "/dashboard/orders/products/{id}", method = RequestMethod.GET)
   public String products(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if(size == null){
            size = 10;
        }
        if(page == null){
            page = 1;
        }
        Integer sizeNo = this.getPageSize(size);
        Integer startPage = this.getStartPage(page, sizeNo);
        model.addAttribute("products", orderProductDao.findByOrderID(id));
        model.addAttribute("maxPages", this.getMaxPages(sizeNo));
        return "/dashboard/orders/products";
    }
   
   @RequestMapping(value = "/dashboard/orders/edit", method = RequestMethod.PUT)
    public String update(@Valid Order order, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("order", order);
            return "/dashboard/orders/edit";
        }
        uiModel.asMap().clear();
        orderDao.update(order);
        return "redirect:/dashboard/orders/edit/" + order.getOrderID().toString();
    }
    
    @RequestMapping(value = "/dashboard/orders/edit/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("order", orderDao.findByID(id));
        return "/dashboard/orders/update";
    }
    
    @RequestMapping(value = "/dashboard/orders/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Order order = orderDao.findByID(id);
        orderDao.delete(order);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect://dashboard/orders";
    }
    
    @RequestMapping(value = "/dashboard/orders/new", method = RequestMethod.POST)
    public String create(@Valid Order order, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("order", order);
            return "/dashboard/orders/create";
        }
        uiModel.asMap().clear();
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.getMerchant().setMerchantID(user.getMerchantId());
        orderDao.save(order);
        return "redirect:/dashboard/orders/view/" + order.getOrderID().toString();
    }
    
    @RequestMapping(value = "/dashboard/orders/new", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("order", new Order());
        return "/dashboard/orders/create";
    }
    
    @RequestMapping(value = "/dashboard/orders/cancel/{id}", method = RequestMethod.GET)
    public String cancelOrder(@PathVariable("id") Long id, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        Order order = orderDao.findOrderByMerchant(id);
        order.setOrderType(OrderType.NORMAL);
        orderDao.update(order);
        uiModel.addAttribute("order", new Order());
        return "redirect:/dashboard/orders";
    }
    
    private Integer getPageSize(Integer size){
        if(size == null){
            return 10;
        }else{
            return size;
        }
    }
    
    private Integer getStartPage(Integer page, Integer pageSize){
        if(page == null){
            return 0;
        }else{
            return (page - 1) * pageSize;
        }
    }
    
    private Integer getMaxPages(Integer sizeNo){
        float nrOfPages = (float) orderDao.countByMerchant() / sizeNo;
        if(nrOfPages > (int) nrOfPages || nrOfPages == 0.0){
            return (int) nrOfPages + 1;
        }else{
            return (int) nrOfPages;
        }
    }
    
    
}
    
    

