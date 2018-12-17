package com.oneshoppoint.yates.controller.admin;


import com.oneshoppoint.yates.service.OrderService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.wrapper.OrderSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Davie on 4/9/16.
 */

@Controller
@RequestMapping("/order/view")
@Secured("ROLE_VIEW_ORDERS")
public class AdminOrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value="/admin/table",params = {"page","parts"},method = RequestMethod.GET)
    public ModelAndView table (@RequestParam Integer page,@RequestParam Integer parts) {
        ModelAndView mv = new ModelAndView();
        List<OrderSummary> orderSummaries = orderService.getAll();
        if(orderSummaries != null) {
            Pagination<OrderSummary> pagination = new Paginator<OrderSummary>().paginate(page,orderSummaries,parts);
            if(pagination.getPrev() != null) {
                mv.addObject("prev","/order/view/admin/table?page="+pagination.getPrev()+"&parts="+parts);
            }
            if(pagination.getNext() != null) {
                mv.addObject("next","/order/view/admin/table?page="+pagination.getNext()+"&parts="+parts);
            }
            mv.addObject("orders",pagination.getList());
        }

        mv.setViewName("admin/tables/order");
        return mv;
    }

    @RequestMapping(value="/admin/table",params = {"page","parts","status"},method = RequestMethod.GET)
    public ModelAndView table (@RequestParam Integer page,@RequestParam Integer parts,@RequestParam String status) {
        ModelAndView mv = new ModelAndView();
        List<OrderSummary> orderSummaries = orderService.getOrderSummariesByStatus(status);
        if(orderSummaries != null) {
            Pagination<OrderSummary> pagination = new Paginator<OrderSummary>().paginate(page,orderSummaries,parts);
            if(pagination.getPrev() != null) {
                mv.addObject("prev","/order/view/admin/table?page="+pagination.getPrev()+"&parts="+parts);
            }
            if(pagination.getNext() != null) {
                mv.addObject("next","/order/view/admin/table?page="+pagination.getNext()+"&parts="+parts);
            }
            mv.addObject("orders",pagination.getList());
        }

        mv.setViewName("admin/tables/order");
        return mv;
    }

    @RequestMapping(value="/admin/table",params = {"page","parts","search"},method = RequestMethod.GET)
    public ModelAndView search (@RequestParam Integer page,@RequestParam Integer parts,@RequestParam String search) {
        ModelAndView mv = new ModelAndView();
        List<OrderSummary> orderSummaries = orderService.search(search);
        if(orderSummaries != null) {
            Pagination<OrderSummary> pagination = new Paginator<OrderSummary>().paginate(page,orderSummaries,parts);
            if(pagination.getPrev() != null) {
                mv.addObject("prev","/order/view/admin/table?page="+pagination.getPrev()+"&parts="+parts);
            }
            if(pagination.getNext() != null) {
                mv.addObject("next","/order/view/admin/table?page="+pagination.getNext()+"&parts="+parts);
            }
            mv.addObject("orders",pagination.getList());
        }

        mv.setViewName("admin/tables/order");
        return mv;
    }

    @RequestMapping(value="/admin",params = {"invoice"},method = RequestMethod.GET)
    public ModelAndView invoice (@RequestParam String invoice) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("invoice",orderService.getInvoice(invoice));
        mv.setViewName("admin/other/invoice");
        return mv;
    }
}
