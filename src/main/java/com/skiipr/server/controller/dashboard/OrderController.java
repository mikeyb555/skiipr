/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.controller.dashboard;

import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.OrderDao;
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

/**
 *
 * @author Michael
 */
@Controller
public class OrderController {
    
    @Autowired
    private OrderDao orderDao;
    
     @RequestMapping(value = "/dashboard/orders/view/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("order", orderDao.findByID(id));
        uiModel.addAttribute("itemId", id);
        return "/dashboard/orders/view";
    }
    
   @RequestMapping(value = "/dashboard/orders", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            model.addAttribute("orders", orderDao.findAllByMerchant());
        } else {
            model.addAttribute("orders", orderDao.findAllByMerchant());
        }
        model.addAttribute("orders", orderDao.findAllByMerchant());
        return "/dashboard/orders/list";
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
    
    @RequestMapping(value = "/dashboard/orders/delete/{id}", method = RequestMethod.DELETE)
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
}
    
    

