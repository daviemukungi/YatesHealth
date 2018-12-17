package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.repository.*;
import com.oneshoppoint.yates.service.CheckoutService;
import com.oneshoppoint.yates.util.PublicStorage;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by robinson on 4/26/16.
 */

@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private RetailerDao retailerDao;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private PrescriptionDao prescriptionDao;
    @Autowired
    private GenericRecursiveDao<Location> locationDao;
    @Autowired
    private GenericDao<Carrier> carrierDao;
    @Autowired
    private OrderDao orderDao;

    public List<RetailerBasket> getBasketByLocationAndCart(Integer locationId,PublicStorage storage) {
        List<Location> locations = flatten(locationDao.find(locationId));
        List<RetailerBasket> retailerBaskets = new ArrayList<RetailerBasket>();
        for (Location location : locations) {
            List<Retailer> retailers = retailerDao.getByLocationId(location.getId());
            if(retailers != null) {
                for (Retailer retailer : retailers) {
                    RetailerBasket retailerBasket = getRetailerBasket(retailer,storage);
                    if(retailerBasket != null) {
                        retailerBaskets.add(retailerBasket);
                    }
                }
            }
        }
        Collections.sort(retailerBaskets);
        return retailerBaskets;
    }

    public RetailerBasket getBasketByRetailerId(Integer id,PublicStorage storage) {
        Retailer retailer = retailerDao.find(id);
        if(retailer != null) {
            return getRetailerBasket(retailer,storage);
        } else {
            throw new RestException(Status.ERROR,"The retailer with that Id does not exist");
        }
    }

    private RetailerBasket getRetailerBasket(Retailer retailer,PublicStorage storage) {
        RetailerBasket retailerBasket = new RetailerBasket();
        retailerBasket.setRetailer(retailer);
        Double totalPrice = 0.0;
        List<Item> items = storage.getCart();
        List<ProductItem> productList = new ArrayList<ProductItem>();
        Integer count = 0;
        for(Item item : items) {
            if(item.getMedical()) {
                List<PrescriptionItem> prescriptionItems = prescriptionDao.getByCode(item.getUUID()).getPrescriptionItems();
                List<Double> prices = new ArrayList<Double>();
                Boolean ignoreRetailer = true;
                for(PrescriptionItem prescriptionItem : prescriptionItems) {
                    List<Product> products = productDao.getByInnId(prescriptionItem.getInn().getId());
                    double min = 0;
                    Product prod = null;
                    boolean minIsSet = false;
                    for(Product medicalProduct : products) {
                        Stock stock = stockDao.getByProductAndRetailer(medicalProduct,retailer);
                        if(stock != null && !minIsSet) {
                            min = stock.getPrice();
                            prod = stock.getProduct();
                            minIsSet = true;
                        } else if(stock != null && stock.getPrice() < min){
                            min = stock.getPrice();
                            prod = stock.getProduct();
                        }
                    }
                    if(prod != null) {
                        if (prescriptionItem.getUnit().equals("weeks")) {
                            min = min*(prescriptionItem.getFrequencyQuantity()*prescriptionItem.getFrequencyPerDay()*7*prescriptionItem.getDuration());
                        } else {
                            min = min*(prescriptionItem.getFrequencyQuantity()*prescriptionItem.getFrequencyPerDay()*prescriptionItem.getDuration());
                        }
                        prices.add(min);
                        ignoreRetailer = false;
                    }
                }
                if(!ignoreRetailer) {
                    double sum = 0.0;
                    for (Double price : prices) {
                        sum+=price;
                    }
                    totalPrice +=sum;
                    count++;
                }
            } else {
                Product product = productDao.getByUUID(item.getUUID());
                Stock stock = stockDao.getByProductAndRetailer(product,retailer);
                if(stock != null && item.getQuantity() <= stock.getQuantity()){
                    totalPrice+=stock.getPrice()*item.getQuantity();
                    ProductItem productItem = new ProductItem();
                    productItem.setUUID(product.getUUID());
                    productItem.setImage(product.getPrimaryImage());
                    productItem.setQuantity(item.getQuantity());
                    productItem.setName(product.getName());
                    productItem.setPrice(stock.getPrice());
                    productList.add(productItem);
                    count++;
                }
            }
        }
        retailerBasket.setProducts(productList);
        retailerBasket.setTotalPrice(totalPrice);

        if(count > 0) {
            return  retailerBasket;
        }

        return null;
    }

    public List<CarrierCharge> calculateCarrierCharges (Integer retailerId,Integer destinationId,PublicStorage storage) {
        List<Item> items = storage.getCart();
        List<CarrierCharge> carrierCharges = new ArrayList<CarrierCharge>();
        Retailer retailer = retailerDao.find(retailerId);
        double totalWeight = 0.0,length = 0.0,height = 0.0, width = 0.0;

        if(retailer == null) {
            throw new RestException(Status.ERROR,"The retailer does not exist");
        }

        for(Item item : items) {
            if(item.getMedical()) {
                List<PrescriptionItem> prescriptionItems = prescriptionDao.getByCode(item.getUUID()).getPrescriptionItems();
                List<Double> weights = new ArrayList<Double>();
                Boolean ignoreRetailer = true;
                for(PrescriptionItem prescriptionItem : prescriptionItems) {
                    List<Product> products = productDao.getByInnId(prescriptionItem.getInn().getId());
                    double min = 0;
                    Product prod = null;
                    boolean minIsSet = false;
                    for(Product medicalProduct : products) {
                        Stock stock = stockDao.getByProductAndRetailer(medicalProduct,retailer);
                        if(stock != null && !minIsSet) {
                            min = stock.getPrice();
                            prod = stock.getProduct();
                            minIsSet = true;
                        } else if(stock != null && stock.getPrice() < min){
                            min = stock.getPrice();
                            prod = stock.getProduct();
                        }
                    }
                    if(prod != null) {
                        weights.add(prod.getWeight());
                        length += prod.getLength();
                        height += prod.getHeight();
                        width  += prod.getWidth();
                        ignoreRetailer = false;
                    }
                }
                if(!ignoreRetailer) {
                    double sum = 0.0;
                    for (Double weight : weights) {
                        sum+=weight;
                    }
                    totalWeight +=sum;
                }
            } else {
                Product product = productDao.getByUUID(item.getUUID());
                Stock stock = stockDao.getByProductAndRetailer(product,retailer);
                if(stock != null && item.getQuantity() <= stock.getQuantity()){
                    totalWeight+=product.getWeight();
                    length += product.getLength();
                    height += product.getHeight();
                    width  += product.getWidth();
                }
            }
        }


        if(totalWeight == 0) {
            return null;
        } else {
            List<Carrier> carriers = carrierDao.getAll();
            Double packSize = length + (2*width) + (2*height);
            if(carriers != null) {
                for(Carrier carrier : carriers) {
                    List<CarrierPlan> carrierPlans = carrier.getCarrierPlans();
                    for(CarrierPlan carrierPlan : carrierPlans) {
                        Double dimensionalWeight = (length * width * height)/carrierPlan.getShippingFactor();
                        if(totalWeight < dimensionalWeight ) {
                            totalWeight = dimensionalWeight;
                        }

                        if(totalWeight < carrierPlan.getMaximumWeight() && packSize < carrierPlan.getMaximumPackSize()) {
                            CarrierCharge carrierCharge = new CarrierCharge();
                            carrierCharge.setCarrier(carrier);
                            carrierCharge.setPlanName(carrierPlan.getName());
                            List<CarrierRate> carrierRates = carrierPlan.getCarrierRates();
                            Double shippingCharge = null;
                            for(CarrierRate carrierRate : carrierRates) {
                                if(carrierRate.getDestination().getId() == destinationId &&
                                        carrierRate.getOrigin().getId() == retailer.getAddress().getLocation().getId()) {
                                    shippingCharge = carrierRate.getPrice();
                                    Double balanceWeight = totalWeight - carrierPlan.getBaseWeight();
                                    if(balanceWeight > 0) {
                                        if(carrierRate.getExceedWeight() != null && carrierRate.getExceedCharge() != null) {
                                            int parts =(int) Math.ceil(balanceWeight/carrierRate.getExceedWeight());
                                            shippingCharge += parts * carrierRate.getPrice();
                                        } else {
                                            int parts =(int) Math.ceil(balanceWeight/carrierPlan.getExceedWeight());
                                            shippingCharge += parts * carrierPlan.getExceedCharge();
                                        }
                                    }
                                }
                            }
                            if(shippingCharge != null) {
                                carrierCharge.setCharge(shippingCharge);
                                carrierCharges.add(carrierCharge);
                            }
                        }
                    }
                }
            }
        }

        return carrierCharges;
    }

    public void makeOrder (Checkout checkout,PublicStorage storage) {
        List<Item> items = storage.getCart();
        String invoice ;
        if(orderDao.getAll() != null) {
            invoice = "INV-"+Calendar.getInstance().getTimeInMillis()+"-"+(orderDao.getAll().size()+1);
        } else {
            invoice = "INV-"+Calendar.getInstance().getTimeInMillis()+"- 1";
        }
        for (Item item : items) {
            Location location = locationDao.find(checkout.getLocationId());
            Order order = new Order();
            order.setEnabled(true);
            order.setCreatedBy(0);
            order.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            order.setInvoiceNo(invoice);
            order.setCustomerFirstname(checkout.getFirstname());
            order.setCustomerLastname(checkout.getLastname());
            order.setCustomerPhoneNumber(checkout.getPhoneNumber());
            order.setCustomerEmail(checkout.getEmail());
            order.setCustomerStreetAddress(checkout.getStreetAddress());
            order.setCustomerResidentialName(checkout.getResidentialName());
            order.setCustomerLocation(location.getName()+"("+location.getLabel()+")");


            Retailer retailer = retailerDao.find(checkout.getRetailerId());

            if (item.getMedical()) {
                Double price = 0.0;
                Prescription prescription = prescriptionDao.getByCode(item.getUUID());
                List<PrescriptionItem> prescriptionItems = prescription.getPrescriptionItems();
                for (PrescriptionItem prescriptionItem : prescriptionItems) {
                    List<Product> products = productDao.getByInnId(prescriptionItem.getInn().getId());
                    Stock minStock = null;
                    for (Product product : products) {
                        Stock stock = stockDao.getByProductAndRetailer(product,retailer);
                        if (minStock == null) {
                            minStock = stock;
                        } else {
                            if (stock.getPrice() < minStock.getPrice()) {
                                minStock = stock;
                            }
                        }
                    }

                    if(minStock != null) {
                        //calculate stock balance
                        int stockBalance;
                        if (prescriptionItem.getUnit().equals("weeks")) {
                            stockBalance = minStock.getQuantity()-(prescriptionItem.getFrequencyQuantity()*prescriptionItem.getFrequencyPerDay()*7*prescriptionItem.getDuration());
                            price+=minStock.getPrice()*(prescriptionItem.getFrequencyQuantity()*prescriptionItem.getFrequencyPerDay()*7*prescriptionItem.getDuration());
                        } else {
                            stockBalance = minStock.getQuantity()-(prescriptionItem.getFrequencyQuantity()*prescriptionItem.getFrequencyPerDay()*prescriptionItem.getDuration());
                            price+=minStock.getPrice()*(prescriptionItem.getFrequencyQuantity()*prescriptionItem.getFrequencyPerDay()*prescriptionItem.getDuration());
                        }

                        if(stockBalance < 0) {
                            throw new RestException(Status.ERROR,"The retailer selected does not have the required quantity for the order");
                        }
                        minStock.setQuantity(stockBalance);
                        stockDao.update(minStock);
                    }
                }

                order.setProductUUID(item.getUUID());
                order.setProductName("MEDICAL PRESCRIPTION");
                order.setProductPrice(price);
                order.setProductQuantity(1);
                order.setProductTotalPrice(price);
                prescription.setDispensed(true);
                prescription.setUpdatedBy(0);
                prescription.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                prescriptionDao.update(prescription);

            } else {
                Product product = productDao.getByUUID(item.getUUID());
                Stock stock = stockDao.getByProductAndRetailer(product,retailer);
                order.setProductUUID(product.getUUID());
                order.setProductName(product.getName());
                order.setProductPrice(stock.getPrice());

                //calculate stock balance
                int stockBalance = stock.getQuantity()-item.getQuantity();
                if(stockBalance < 0) {
                    throw new RestException(Status.ERROR,"The retailer selected does not have the required quantity for the order");
                }

                order.setProductQuantity(item.getQuantity());
                stock.setQuantity(stockBalance);
                order.setProductTotalPrice(stock.getPrice()*item.getQuantity());
                stockDao.update(stock);
            }


            //retailer info
            order.setRetailerName(retailer.getName());
            order.setRetailerPhoneNumber(retailer.getAddress().getPhoneNumber());
            order.setRetailerEmail(retailer.getAddress().getEmail());
            order.setRetailerStreetAddress(retailer.getAddress().getStreetAddress());
            order.setRetailerResidentialName(retailer.getAddress().getResidentialName());
            order.setRetailerLocation(retailer.getAddress().getLocation().getName()+"("+retailer.getAddress().getLocation().getLabel()+")");
            order.setRetailerPayBillNo(retailer.getPayBillNo());
            order.setRetailerTransactionCode(checkout.getRetailerTransactionCode());

            //carrier info
            if(checkout.getCarrierId() != null) {
                Carrier carrier = carrierDao.find(checkout.getCarrierId());
                if(carrier == null) {
                    throw new RestException(Status.FAILED,"Specified carrier does not exist");
                }

                order.setCarrierName(carrier.getName());
                order.setCarrierPhoneNumber(carrier.getAddress().getPhoneNumber());
                order.setCarrierEmail(carrier.getAddress().getEmail());
                order.setCarrierStreetAddress(carrier.getAddress().getStreetAddress());
                order.setCarrierResidentialName(carrier.getAddress().getResidentialName());
                order.setCarrierLocation(carrier.getAddress().getLocation().getName() + "(" + carrier.getAddress().getLocation().getLabel() + ")");
                order.setCarrierPayBillNo(carrier.getPayBillNo());
                order.setCarrierTransactionCode(checkout.getCarrierTransactionCode());
                List<CarrierCharge> carrierCharges = calculateCarrierCharges(retailer.getId(),location.getId(),storage);
                CarrierCharge shippingCharge = null;
                for(CarrierCharge carrierCharge : carrierCharges) {
                    if(carrierCharge.getPlanName().equalsIgnoreCase(checkout.getCarrierPlan())) {
                        shippingCharge = carrierCharge;
                    }
                }
                if(shippingCharge == null) {
                    throw new RestException(Status.ERROR,"Failed to find the carrier plan");
                }
                order.setCarrierPlan(checkout.getCarrierPlan());
                order.setCarrierPrice(shippingCharge.getCharge());
            }
            order.setStatus("ordered");

            orderDao.save(order);
        }



    }

    private List<Location> flatten (Location location) {
        Queue<Location> locationQueue = new LinkedList<Location>();
        List<Location> locationList = new ArrayList<Location>();
        locationQueue.add(location);
        while(!locationQueue.isEmpty()) {
            Location loc = locationQueue.remove();
            if(!loc.getChildren().isEmpty()) {
                locationQueue.addAll(loc.getChildren());
            }
            locationList.add(loc);
        }

        return locationList;
    }
}
