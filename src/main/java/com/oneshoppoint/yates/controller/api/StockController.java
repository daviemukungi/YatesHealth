package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Stock;
import com.oneshoppoint.yates.service.RetailerService;
import com.oneshoppoint.yates.service.StockService;
import com.oneshoppoint.yates.util.*;
import com.oneshoppoint.yates.wrapper.StockForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * The stock api contains endpoints for adding,updating,deleting and accessing stock data for a particular
 * retailer
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/23/16.
 */

@RestController
@RequestMapping("api/stock")
@Secured("ROLE_RETAILER")
public class StockController {
    @Autowired
    StockService stockService;
    @Autowired
    RetailerService retailerService;
    @Autowired
    CurrentUser currentUser;

    /**
     * Create a new stock
     * @param stockForm This is a wrapper for the new stock
     * @param result This is a BindingResult object that holds the validation result of the stockForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Stock> add (@Valid @RequestBody StockForm stockForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Stock> (Status.FAILED,"Failed to add new stock ",result.getFieldErrors());
        }

        stockService.save(stockForm);
        return new RestMessage<Stock>(Status.CREATED,"Successfully added a new stock");
    }

    /**
     * List stock with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of stock to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Stock>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Stock> pagination = new Paginator<Stock>().paginate(page,stockService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/stock?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/stock?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Stock>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all stock data
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Stock>> get () {
        return new RestMessage<List<Stock>>(Status.OK,stockService.getAll());
    }

    /**
     * Get information of a particular stock
     * @param id Integer specifying the id of the stock
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"id"})
    public RestMessage<Stock> get (@RequestParam Integer id) {
        return new RestMessage<Stock>(Status.OK,stockService.getById(id));
    }

    /**
     * Search for stock(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the stock
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<Stock>> search (@RequestParam String search) {
        return new RestMessage<List<Stock>>(Status.OK,stockService.search(search));
    }

    /**
     * Update a stock
     * @param stockForm This is a wrapper for the stock
     * @param result This is a BindingResult object that holds the validation result of the stockForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<Stock> update (@Valid @RequestBody StockForm stockForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Stock> (Status.FAILED,"Failed to updated the stock ",result.getFieldErrors());
        }

        stockService.update(stockForm);
        return new RestMessage<Stock>(Status.MODIFIED,"Successfully updated the stock");
    }

    /**
     * Delete stock(s)
     * @param ids Integer specifying the id(s) of the categories to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<Stock>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            stockService.delete(id);
        }

        return new RestMessage<List<Stock>>(Status.DELETED,"successfully deleted the stock(s)");
    }

    /**
     * This endpoint is used to import excel stock data. It can be used to update or create new stock data
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is just a string specifying the status of the upload.
     */
    @RequestMapping(value="/import",method = RequestMethod.POST)
    public RestMessage  importExcel(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                stockService.importExcel(file.getBytes(),file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to import stock data => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to import stock data because the file was empty.");
        }

        return new RestMessage<List<Stock>>(Status.OK,"successfully imported the stock information");
    }

    /**
     * This endpoint is used to export excel stock data. It can be used as a template for updating stock data during import
     * abd as a stock report for a particular category of goods
     * @param format This is a string specifying the excel type, accepted values are <strong>xls</strong> and <strong>xlsx</strong>
     * @param id This is an integer specifying the category of interest
     */
    @RequestMapping(method = RequestMethod.GET,params = {"format","id"})
    public @ResponseBody void exportExcel (HttpServletResponse response,@RequestParam String format,@RequestParam Integer id) {
        byte[] bytes;
        if(currentUser.getUser().getAffiliate().getPharmacist()) {
            bytes = stockService.exportInnExcel("stocks."+format,id);
        } else {
            bytes = stockService.exportExcel("stocks."+format,id);
        }

        response.reset();
        if(format.equalsIgnoreCase("xls")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename=stock.xls");
        } else if (format.equalsIgnoreCase("xlsx")) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "inline; filename=stock.xlsx");
        } else {
            throw new RestException(Status.ERROR,"The excel format is not supported");
        }

        try {
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            throw new RestException(Status.ERROR,e.getMessage());
        }
    }
}
