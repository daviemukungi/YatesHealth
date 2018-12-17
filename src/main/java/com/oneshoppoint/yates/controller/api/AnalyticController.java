package com.oneshoppoint.yates.controller.api;


import com.oneshoppoint.yates.service.AnalyticService;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The analytics api gives you access to the sales, prescriptions, orders and products summary
 * @author robinson odhiambo.
 * @version 1.0
 * @since 14-5-2016
 */

@RestController
@RequestMapping("/api/analytics")
@Secured({"ROLE_ADMIN","ROLE_APP"})
public class AnalyticController {
    @Autowired
    AnalyticService analyticService;

    /**
     * This endpoint is used to access product sales data over a period of one year
     * @param year This is an integer specifying the sales year of interest
     * @param locationId This is an integer specifying the location Id to get sales for retailers in a particular location
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/sales",params = {"year","locationId"},method = RequestMethod.GET)
    public RestMessage<Graph> sales (@RequestParam Integer year, @RequestParam Integer locationId) {
        return new RestMessage<Graph>(Status.OK,analyticService.salesByLocation(year, locationId));
    }
    /**
     * This endpoint is used to access product sales data over a period of one year for a particular outlet
     * @param retailerId This is an integer specifying the retailer of interest
     * @param year This is an integer specifying the sales year of interest
     * @param locationId This is an integer specifying the location Id to get sales for outlets in a particular location
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/sales",params = {"retailerId","year","locationId"},method = RequestMethod.GET)
    public RestMessage<Graph> salesByRetailer (@RequestParam Integer year,@RequestParam Integer retailerId, @RequestParam Integer locationId) {
        return new RestMessage<Graph>(Status.OK,analyticService.salesByLocationAndRetailer(year, locationId, retailerId));
    }

    /**
     * This endpoint is used to access product sales data over a period of one year for a particular retailer
     * @param retailerId This is an integer specifying the retailer of interest
     * @param year This is an integer specifying the sales year of interest
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/sales",params = {"retailerId","year"},method = RequestMethod.GET)
    public RestMessage<Graph> salesByRetailer (@RequestParam Integer year,Integer retailerId) {
        return new RestMessage<Graph>(Status.OK,analyticService.salesByRetailer(year, retailerId));
    }

    /**
     * This endpoint is used to access carrier sales data over a period of one year
     * @param carrier This is a String that is used for differentiation of other sales endpoints and therefore the value is
     *                of no importance
     * @param year This is an integer specifying the sales year of interest
     * @param locationId This is an integer specifying the location Id to get sales for carriers in a particular location
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/sales",params = {"year","locationId","carrier"},method = RequestMethod.GET)
    public RestMessage<Graph> totalCarrierSales (@RequestParam Integer year,@RequestParam Integer locationId,@RequestParam String carrier) {
        return new RestMessage<Graph>(Status.OK,analyticService.carrierSales(year, locationId));
    }

    /**
     * This endpoint is used to access carrier sales data over a period of one year for a particular carrier
     * @param carrierId this is an integer specifying the carrier of interest
     * @param year This is an integer specifying the sales year of interest
     * @param locationId This is an integer specifying the location Id to get sales for a particular carrier in a particular location
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/sales",params = {"carrierId","year","locationId"},method = RequestMethod.GET)
    public RestMessage<Graph> salesByCarrier (@RequestParam Integer year,@RequestParam Integer carrierId, @RequestParam Integer locationId) {
        return new RestMessage<Graph>(Status.OK,analyticService.salesByLocationAndCarrier(year, locationId, carrierId));
    }

    /**
     * This endpoint is used to access carrier sales data over a period of one year for a particular carrier
     * @param carrierId this is an integer specifying the carrier of interest
     * @param year This is an integer specifying the sales year of interest
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/sales",params = {"carrierId","year"},method = RequestMethod.GET)
    public RestMessage<Graph> salesByCarrier (@RequestParam Integer year,Integer carrierId) {
        return new RestMessage<Graph>(Status.OK,analyticService.salesByCarrier(year, carrierId));
    }

    /**
     * This endpoint is used to access top three most popular products for each month over a specified year
     * @param year This is an integer specifying the sales year of interest
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/products",params = {"year"},method = RequestMethod.GET)
    public RestMessage<Graph> products (@RequestParam Integer year) {
        return new RestMessage<Graph>(Status.OK,analyticService.popularProducts(year));
    }

    /**
     * This endpoint is used to access top three most popular products for each month over a specified year for a particular retailer
     * @param year This is an integer specifying the sales year of interest
     * @param retailerId This is an integer specifying the retailer of interest
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/products",params = {"year","retailerId"},method = RequestMethod.GET)
    public RestMessage<Graph> productsByRetailer (@RequestParam Integer year,@RequestParam Integer retailerId) {
        return new RestMessage<Graph>(Status.OK,analyticService.popularProductsByRetailer(year, retailerId));
    }
    /**
     * This endpoint is used to access number of orders made over the course of one year it is categorised into
     * ordered,received by retailer,prepared,received by carrier,transporting and completed orders
     * @param year This is an integer specifying the sales year of interest
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/orders",params = {"year"},method = RequestMethod.GET)
    public RestMessage<Graph> productsByRetailer (@RequestParam Integer year) {
        return new RestMessage<Graph>(Status.OK,analyticService.orders(year));
    }

    /**
     * This endpoint is used to access prescriptions made over the course of one year
     * @param year This is an integer specifying the sales year of interest
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/prescriptions",params = {"year"},method = RequestMethod.GET)
    public RestMessage<Graph> prescriptions (@RequestParam Integer year) {
        return new RestMessage<Graph>(Status.OK,analyticService.prescriptions(year));
    }

    /**
     * This endpoint is used to access prescriptions made over the course of one year by a particular medic
     * @param year This is an integer specifying the sales year of interest
     * @param medicId This is an integer specifying the medic of interest
     * @return It is a Graph object modeled against chart.js data object for more information see Graph
     * @see Graph
     */
    @RequestMapping(value = "/prescriptions",params = {"year","medicId"},method = RequestMethod.GET)
    public RestMessage<Graph> prescriptionsByMedic (@RequestParam Integer year,@RequestParam Integer medicId) {
        return new RestMessage<Graph>(Status.OK,analyticService.prescriptionsByMedic(year, medicId));
    }

    /**
     * This endpoint is used to access prescriptions made over the course of one year categorised by dispensed  and un-dispensed
     * @param year This is an integer specifying the sales year of interest
     * @param pie This is a String that is used for differentiation of other prescription endpoints and therefore the value is
     *                of no importance
     * @return It is a Graph object modeled against chart.js data object it is suitable for pie chart data
     * @see Graph
     */
    @RequestMapping(value = "/prescriptions",params = {"year","pie"},method = RequestMethod.GET)
    public RestMessage<Graph> prescriptionsPie (@RequestParam Integer year,@RequestParam String pie) {
        return new RestMessage<Graph>(Status.OK,analyticService.prescriptionsPie(year));
    }
}
