package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.repository.*;
import com.oneshoppoint.yates.service.StockService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.StockForm;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by robinson on 4/8/16.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {
    @Autowired
    private StockDao stockDao;
    @Autowired
    private RetailerDao retailerDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private GenericDao<Inn> innDao;

    private final Integer PRODUCT_CODE = 0;
    private final Integer PRODUCT_NAME = 1;
    private final Integer PRODUCT_WEIGHT = 2;
    private final Integer PRODUCT_HEIGHT = 3;
    private final Integer PRODUCT_LENGTH = 4;
    private final Integer PRODUCT_PRICE = 5;
    private final Integer PRODUCT_QUANTITY = 6;
    private final Integer INN_ID = 0;
    private final Integer INN_NAME = 1;
    private final Integer INN_PRICE = 2;
    private final Integer INN_QUANTITY = 3;

    public void save (StockForm stockForm) {
        if(stockDao.getByProductAndRetailer(productDao.getByUUID(stockForm.getProductUUID()),retailerDao.find(stockForm.getRetailerId())) != null) {
            throw new RestException(Status.ERROR,"The stock data already exists");
        }

        Stock stock = new Stock();
        stock.setCreatedBy(currentUser.getUser().getId());
        stock.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        stock.setPrice(stockForm.getPrice());
        if(currentUser.getUser().getAffiliate().getPharmacist()) {
            stock.setInn(innDao.find(stockForm.getInnId()));
        } else {
            stock.setProduct(productDao.getByUUID(stockForm.getProductUUID()));
        }
        stock.setRetailer(retailerDao.find(stockForm.getRetailerId()));
        stock.setQuantity(stockForm.getQuantity());
        stock.setEnabled(stockForm.getEnabled());

        stockDao.save(stock);
    }

    public void update (StockForm stockForm) {
        Stock stock = stockDao.find(stockForm.getId());

        if(stock == null) {
            throw new RestException(Status.ERROR,"The stock does not exist");
        }

        stock.setUpdatedBy(currentUser.getUser().getId());
        stock.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        stock.setPrice(stockForm.getPrice());
        if(currentUser.getUser().getAffiliate().getPharmacist()) {
            stock.setInn(innDao.find(stockForm.getInnId()));
        } else {
            stock.setProduct(productDao.getByUUID(stockForm.getProductUUID()));
        }
        stock.setRetailer(retailerDao.find(stockForm.getRetailerId()));
        stock.setQuantity(stockForm.getQuantity());
        stock.setPrice(stockForm.getPrice());
        stock.setEnabled(stockForm.getEnabled());
        stockDao.update(stock);
    }

    public void importExcel (byte[] bytes,String filename) {
        Workbook wb;
        try {
            if(filename.endsWith(".xls")) {
                wb = new HSSFWorkbook(new ByteArrayInputStream(bytes));
            } else if(filename.endsWith(".xlsx")){
                wb = new XSSFWorkbook(new ByteArrayInputStream(bytes));
            } else {
                throw new RestException(Status.ERROR,"The excel format is not supported");
            }
        } catch (IOException ioE) {
            throw new RestException(Status.ERROR,ioE.getMessage());
        }

        for(int sheetIdx = 0;sheetIdx < wb.getNumberOfSheets(); sheetIdx ++ ) {
            Sheet sheet = wb.getSheetAt(sheetIdx);
            for (int rowIdx = 1; rowIdx < sheet.getPhysicalNumberOfRows(); rowIdx ++) {
                Row row = sheet.getRow(rowIdx);
                if(row == null) {
                    continue;
                }
                Stock stock = new Stock();

                stock.setPrice(row.getCell(PRODUCT_PRICE).getNumericCellValue());
                stock.setProduct(productDao.getByUUID(row.getCell(PRODUCT_CODE).getStringCellValue()));
                stock.setRetailer(currentUser.getUser().getAffiliate().getRetailer());
                stock.setQuantity(new Double(row.getCell(PRODUCT_QUANTITY).getNumericCellValue()).intValue());

                stock.setEnabled(true);
                Stock oldStock = stockDao.getByProductAndRetailer(productDao.getByUUID(row.getCell(PRODUCT_CODE).getStringCellValue()),currentUser.getUser().getAffiliate().getRetailer());
                if(oldStock != null) {
                    oldStock.setPrice(stock.getPrice());
                    oldStock.setQuantity(stock.getQuantity());
                    oldStock.setUpdatedBy(currentUser.getUser().getId());
                    oldStock.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    stockDao.update(oldStock);
                } else {
                    stock.setCreatedBy(currentUser.getUser().getId());
                    stock.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    stockDao.save(stock);
                }
            }
        }

    }

    public byte[] exportExcel (String name,Integer categoryId) {
       Workbook wb;
       if(name.endsWith(".xls")) {
           wb = new HSSFWorkbook();
       } else if(name.endsWith(".xlsx")){
           wb = new XSSFWorkbook();
       } else {
           throw new RestException(Status.ERROR,"The excel format is not supported");
       }
        List<Product> unfilteredProducts = productDao.getByCategoryId(categoryId);
        List<Product> products = new ArrayList<Product>();
        List<Manufacturer> manufacturers = currentUser.getUser().getAffiliate().getRetailer().getManufacturers();
        if (manufacturers != null) {
            for(Product product : unfilteredProducts) {
                for (Manufacturer manufacturer : manufacturers) {
                    if (product.getManufacturer().getId() == manufacturer.getId()) {
                        products.add(product);
                    }
                }
            }
        } else {
            products = null;
        }

       if(products != null) {
           Sheet sheet = wb.createSheet(products.get(0).getCategory().getName()+" products");
           Row row = sheet.createRow((short)0);
           CellStyle cs = wb.createCellStyle();
           Font defaultFont= wb.createFont();
           defaultFont.setFontHeightInPoints((short)10);
           defaultFont.setFontName("Arial");
           defaultFont.setColor(IndexedColors.BLACK.getIndex());
           defaultFont.setBold(false);
           defaultFont.setItalic(false);

           Font font= wb.createFont();
           font.setFontHeightInPoints((short)10);
           font.setFontName("Arial");
           font.setColor(IndexedColors.BLACK.getIndex());
           font.setBold(true);
           font.setItalic(false);
           cs.setFont(font);

           Cell codeTitle = row.createCell(PRODUCT_CODE);
           codeTitle.setCellValue("Product Code");
           codeTitle.setCellStyle(cs);
           Cell productNameTitle = row.createCell(PRODUCT_NAME);
           productNameTitle.setCellValue("Product Name");
           productNameTitle.setCellStyle(cs);
           Cell productWeightTitle = row.createCell(PRODUCT_WEIGHT);
           CellUtil.setAlignment(productWeightTitle,wb,CellStyle.ALIGN_CENTER);
           productWeightTitle.setCellValue("Product Weight");
           productWeightTitle.setCellStyle(cs);
           Cell productHeightTitle = row.createCell(PRODUCT_HEIGHT);
           productHeightTitle.setCellValue("Product Height");
           productHeightTitle.setCellStyle(cs);
           CellUtil.setAlignment(productHeightTitle,wb,CellStyle.ALIGN_CENTER);
           Cell productLengthTitle = row.createCell(PRODUCT_LENGTH);
           productLengthTitle.setCellValue("Product Length");
           productLengthTitle.setCellStyle(cs);
           CellUtil.setAlignment(productLengthTitle,wb,CellStyle.ALIGN_CENTER);
           Cell productPriceTitle = row.createCell(PRODUCT_PRICE);
           productPriceTitle.setCellValue("Product Price");
           productPriceTitle.setCellStyle(cs);
           CellUtil.setAlignment(productPriceTitle,wb,CellStyle.ALIGN_CENTER);
           Cell quantityTitle = row.createCell(PRODUCT_QUANTITY);
           quantityTitle.setCellValue("Product Quantity");
           quantityTitle.setCellStyle(cs);
           CellUtil.setAlignment(quantityTitle,wb,CellStyle.ALIGN_CENTER);

           Integer idx = 1;
           for(Product product : products) {
               row = sheet.createRow(idx);
               CellStyle csInner = wb.createCellStyle();
               csInner.setFont(defaultFont);
               Cell productCode = row.createCell(PRODUCT_CODE);
               productCode.setCellValue(product.getUUID());
               productCode.setCellStyle(csInner);
               Cell productName = row.createCell(PRODUCT_NAME);
               productName.setCellValue(product.getName());
               productName.setCellStyle(csInner);
               Cell productWeight = row.createCell(PRODUCT_WEIGHT);
               productWeight.setCellValue(product.getWeight());
               productWeight.setCellStyle(csInner);
               CellUtil.setAlignment(productWeight,wb,CellStyle.ALIGN_CENTER);
               Cell productHeight = row.createCell(PRODUCT_HEIGHT);
               productHeight.setCellValue(product.getHeight());
               productHeight.setCellStyle(csInner);
               CellUtil.setAlignment(productHeight,wb,CellStyle.ALIGN_CENTER);
               Cell productLength = row.createCell(PRODUCT_LENGTH);
               productLength.setCellValue(product.getLength());
               productLength.setCellStyle(csInner);
               CellUtil.setAlignment(productLength,wb,CellStyle.ALIGN_CENTER);

               Cell productPrice = row.createCell(PRODUCT_PRICE);
               CellUtil.setAlignment(productPrice,wb,CellStyle.ALIGN_CENTER);

               Cell productQuantity = row.createCell(PRODUCT_QUANTITY);
               CellUtil.setAlignment(productQuantity,wb,CellStyle.ALIGN_CENTER);
               idx++;
           }

           sheet.setColumnHidden(PRODUCT_CODE,true);
           sheet.autoSizeColumn(PRODUCT_NAME);
           sheet.autoSizeColumn(PRODUCT_WEIGHT);
           sheet.autoSizeColumn(PRODUCT_HEIGHT);
           sheet.autoSizeColumn(PRODUCT_LENGTH);
           sheet.autoSizeColumn(PRODUCT_PRICE);
           sheet.autoSizeColumn(PRODUCT_QUANTITY);
           CellStyle quantityStyle = wb.createCellStyle();
           quantityStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
           Font quantityFont = wb.createFont();
           quantityFont.setFontHeightInPoints((short)10);
           quantityFont.setFontName("Arial");
           quantityFont.setColor(IndexedColors.BLACK.getIndex());
           quantityFont.setBold(true);
           quantityFont.setItalic(false);
           quantityStyle.setFont(quantityFont);
           sheet.setDefaultColumnStyle(PRODUCT_QUANTITY,quantityStyle);

           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           try {
               wb.write(outputStream);
           } catch (Exception e) {
               e.printStackTrace();
           }

           return outputStream.toByteArray();
       } else {
           throw new RestException(Status.ERROR,"No product with that category was found");
       }

    }

    public byte[] exportInnExcel (String name,Integer innId) {
        Workbook wb;
        if(name.endsWith(".xls")) {
            wb = new HSSFWorkbook();
        } else if(name.endsWith(".xlsx")){
            wb = new XSSFWorkbook();
        } else {
            throw new RestException(Status.ERROR,"The excel format is not supported");
        }
        List<Product> unfilteredProducts = productDao.getByInnId(innId);
        List<Product> products = new ArrayList<Product>();
        List<Manufacturer> manufacturers = currentUser.getUser().getAffiliate().getRetailer().getManufacturers();
        if (manufacturers != null) {
            for(Product product : unfilteredProducts) {
                for (Manufacturer manufacturer : manufacturers) {
                    if (product.getManufacturer().getId() == manufacturer.getId()) {
                        products.add(product);
                    }
                }
            }
        } else {
            products = null;
        }

        if(products != null && !products.isEmpty()) {
            Sheet sheet = wb.createSheet(products.get(0).getInn().getName()+" products");
            Row row = sheet.createRow((short)0);
            CellStyle cs = wb.createCellStyle();
            Font defaultFont= wb.createFont();
            defaultFont.setFontHeightInPoints((short)10);
            defaultFont.setFontName("Arial");
            defaultFont.setColor(IndexedColors.BLACK.getIndex());
            defaultFont.setBold(false);
            defaultFont.setItalic(false);

            Font font= wb.createFont();
            font.setFontHeightInPoints((short)10);
            font.setFontName("Arial");
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setBold(true);
            font.setItalic(false);
            cs.setFont(font);

            Cell codeTitle = row.createCell(PRODUCT_CODE);
            codeTitle.setCellValue("Product Code");
            codeTitle.setCellStyle(cs);
            Cell productNameTitle = row.createCell(PRODUCT_NAME);
            productNameTitle.setCellValue("Product Name");
            productNameTitle.setCellStyle(cs);
            Cell productWeightTitle = row.createCell(PRODUCT_WEIGHT);
            CellUtil.setAlignment(productWeightTitle,wb,CellStyle.ALIGN_CENTER);
            productWeightTitle.setCellValue("Product Weight");
            productWeightTitle.setCellStyle(cs);
            Cell productHeightTitle = row.createCell(PRODUCT_HEIGHT);
            productHeightTitle.setCellValue("Product Height");
            productHeightTitle.setCellStyle(cs);
            CellUtil.setAlignment(productHeightTitle,wb,CellStyle.ALIGN_CENTER);
            Cell productLengthTitle = row.createCell(PRODUCT_LENGTH);
            productLengthTitle.setCellValue("Product Length");
            productLengthTitle.setCellStyle(cs);
            CellUtil.setAlignment(productLengthTitle,wb,CellStyle.ALIGN_CENTER);
            Cell productPriceTitle = row.createCell(PRODUCT_PRICE);
            productPriceTitle.setCellValue("Product Price");
            productPriceTitle.setCellStyle(cs);
            CellUtil.setAlignment(productPriceTitle,wb,CellStyle.ALIGN_CENTER);
            Cell quantityTitle = row.createCell(PRODUCT_QUANTITY);
            quantityTitle.setCellValue("Product Quantity");
            quantityTitle.setCellStyle(cs);
            CellUtil.setAlignment(quantityTitle,wb,CellStyle.ALIGN_CENTER);

            Integer idx = 1;
            for(Product product : products) {
                row = sheet.createRow(idx);
                CellStyle csInner = wb.createCellStyle();
                csInner.setFont(defaultFont);
                Cell productCode = row.createCell(PRODUCT_CODE);
                productCode.setCellValue(product.getUUID());
                productCode.setCellStyle(csInner);
                Cell productName = row.createCell(PRODUCT_NAME);
                productName.setCellValue(product.getName());
                productName.setCellStyle(csInner);
                Cell productWeight = row.createCell(PRODUCT_WEIGHT);
                productWeight.setCellValue(product.getWeight());
                productWeight.setCellStyle(csInner);
                CellUtil.setAlignment(productWeight,wb,CellStyle.ALIGN_CENTER);
                Cell productHeight = row.createCell(PRODUCT_HEIGHT);
                productHeight.setCellValue(product.getHeight());
                productHeight.setCellStyle(csInner);
                CellUtil.setAlignment(productHeight,wb,CellStyle.ALIGN_CENTER);
                Cell productLength = row.createCell(PRODUCT_LENGTH);
                productLength.setCellValue(product.getLength());
                productLength.setCellStyle(csInner);
                CellUtil.setAlignment(productLength,wb,CellStyle.ALIGN_CENTER);

                Cell productPrice = row.createCell(PRODUCT_PRICE);
                CellUtil.setAlignment(productPrice,wb,CellStyle.ALIGN_CENTER);

                Cell productQuantity = row.createCell(PRODUCT_QUANTITY);
                CellUtil.setAlignment(productQuantity,wb,CellStyle.ALIGN_CENTER);
                idx++;
            }

            sheet.setColumnHidden(PRODUCT_CODE,true);
            sheet.autoSizeColumn(PRODUCT_NAME);
            sheet.autoSizeColumn(PRODUCT_WEIGHT);
            sheet.autoSizeColumn(PRODUCT_HEIGHT);
            sheet.autoSizeColumn(PRODUCT_LENGTH);
            sheet.autoSizeColumn(PRODUCT_PRICE);
            sheet.autoSizeColumn(PRODUCT_QUANTITY);
            CellStyle quantityStyle = wb.createCellStyle();
            quantityStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
            Font quantityFont = wb.createFont();
            quantityFont.setFontHeightInPoints((short)10);
            quantityFont.setFontName("Arial");
            quantityFont.setColor(IndexedColors.BLACK.getIndex());
            quantityFont.setBold(true);
            quantityFont.setItalic(false);
            quantityStyle.setFont(quantityFont);
            sheet.setDefaultColumnStyle(PRODUCT_QUANTITY,quantityStyle);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                wb.write(outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return outputStream.toByteArray();
        } else {
            throw new RestException(Status.ERROR,"No product with that category was found");
        }

    }

    public List<Stock> getAll () {
        return stockDao.getAll();
    }

    public List<Stock> getByRetailer(Integer id) {
        return stockDao.getByRetailer(id);
    }

    public Stock getById (Integer id) {
        return stockDao.find(id);
    }

    public void delete (Integer id) {
        Stock stock = stockDao.find(id);

        if(stock == null) {
            throw new RestException(Status.ERROR,"The stock does not exist");
        }
        stock.setDeletedBy(currentUser.getUser().getId());
        stock.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        stockDao.delete(stock);
    }

    public List<Stock> search (String search) {
        return stockDao.getAll();
    }
}