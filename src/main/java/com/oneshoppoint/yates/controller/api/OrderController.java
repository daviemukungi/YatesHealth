package com.oneshoppoint.yates.controller.api;


import com.oneshoppoint.yates.service.OrderService;
import com.oneshoppoint.yates.util.*;
import com.oneshoppoint.yates.wrapper.Invoice;
import com.oneshoppoint.yates.wrapper.OrderSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The order api contains endpoints for updating order status, accessing order summaries and accessing invoices
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/order")
@Secured("ROLE_VIEW_ORDERS")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * List order summary with specific pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of categories to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<OrderSummary>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<OrderSummary> pagination = new Paginator<OrderSummary>().paginate(page,orderService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/order?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/order?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<OrderSummary>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List order summary with specific pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of categories to be returned in a particular page
     * @param status This is an integer used to specify the status to filter the result set with
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts","status"})
    public RestMessage<List<OrderSummary>> get (@RequestParam int page,@RequestParam int parts,@RequestParam String status) {
        Pagination<OrderSummary> pagination = new Paginator<OrderSummary>().paginate(page,orderService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/order?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/order?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<OrderSummary>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all order summaries
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<OrderSummary>> get () {
        return new RestMessage<List<OrderSummary>>(Status.OK,orderService.getAll());
    }

    /**
     * Get order invoice
     * @param invoice This is a string that is used to specify a particular invoice
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(value="/{invoice}",method = RequestMethod.GET)
    public RestMessage<Invoice> get (@PathVariable String invoice) {
        return new RestMessage<Invoice>(Status.OK,orderService.getInvoice(invoice));
    }

    /**
     * update order status
     * @param invoice This is a string that is used to specify a particular invoice
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(value="/{invoice}/update_status",method = RequestMethod.POST)
    public RestMessage updateStatus (@PathVariable String invoice) {
        return new RestMessage(Status.OK,"Successfully updated order to : "+orderService.updateStatus(invoice));
    }
}
