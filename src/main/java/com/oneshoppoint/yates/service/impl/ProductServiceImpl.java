package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import com.oneshoppoint.yates.repository.ProductDao;
import com.oneshoppoint.yates.service.ProductService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.FeatureValueForm;
import com.oneshoppoint.yates.wrapper.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by robinson on 4/8/16.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Autowired
    GenericRecursiveDao<Category> categoryDao;
    @Autowired
    GenericDao<Inn> innDao;
    @Autowired
    GenericDao<Manufacturer> manufacturerDao;
    @Autowired
    CurrentUser currentUser;
    @Autowired
    GenericDao<Feature> featureDao;


    public void save (ProductForm productForm) {
        if(productDao.getByName(productForm.getName()) != null) {
            throw new RestException(Status.ERROR,"The product "+productForm.getName()+" already exists");
        }

        Product product = new Product();
        product.setCreatedBy(currentUser.getUser().getId());
        product.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        byte[] encodedBytes = Base64.encode(UUID.randomUUID().toString().getBytes());
        product.setUUID(new String(encodedBytes, Charset.forName("UTF-8")));
        product.setName(productForm.getName());
        product.setWeight(productForm.getWeight());
        product.setWidth(productForm.getWidth());
        product.setHeight(productForm.getHeight());
        product.setLength(productForm.getLength());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        if(productForm.getCategoryId() != null) {
            product.setCategory(categoryDao.find(productForm.getCategoryId()));
        }

        if(productForm.getInnId() != null) {
            product.setInn(innDao.find(productForm.getInnId()));
        }

        product.setManufacturer(manufacturerDao.find(productForm.getManufacturerId()));
        List<FeatureValueForm> featureValueForms = productForm.getFeatureValues();
        List<FeatureValue> featureValues = new ArrayList<FeatureValue>();
        for(FeatureValueForm featureValueForm : featureValueForms) {
            FeatureValue featureValue = new FeatureValue();
            featureValue.setFeature(featureDao.find(featureValueForm.getFeatureId()));
            featureValue.setVal(featureValueForm.getVal());
            featureValues.add(featureValue);
        }

        product.setFeatureValues(featureValues);
        product.setPrimaryImage(productForm.getImage());
        product.setEnabled(productForm.getEnabled());

        productDao.save(product);
    }

    public void update (ProductForm productForm) {
        Product product = productDao.find(productForm.getId());

        if(product == null) {
            throw new RestException(Status.ERROR,"The product does not exist");
        }

        product.setUpdatedBy(currentUser.getUser().getId());
        product.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        product.setName(productForm.getName());
        product.setWeight(productForm.getWeight());
        product.setWidth(productForm.getWidth());
        product.setHeight(productForm.getHeight());
        product.setLength(productForm.getLength());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        if(productForm.getCategoryId() != null) {
            product.setCategory(categoryDao.find(productForm.getCategoryId()));
        }

        if(productForm.getInnId() != null) {
            product.setInn(innDao.find(productForm.getInnId()));
        }
        product.setManufacturer(manufacturerDao.find(productForm.getManufacturerId()));
        List<FeatureValueForm> featureValueForms = productForm.getFeatureValues();
        List<FeatureValue> featureValues = new ArrayList<FeatureValue>();
        for(FeatureValueForm featureValueForm : featureValueForms) {
            FeatureValue featureValue = new FeatureValue();
            featureValue.setFeature(featureDao.find(featureValueForm.getFeatureId()));
            featureValue.setVal(featureValueForm.getVal());
            featureValues.add(featureValue);
        }

        product.setFeatureValues(featureValues);
        product.setPrimaryImage(productForm.getImage());
        product.setEnabled(productForm.getEnabled());
        productDao.update(product);
    }

    public List<Product> getAll () {
        return productDao.getAll();
    }

    public Product getById (Integer id) {
        return productDao.find(id);
    }

    public Product getByUUID(String uuid) {
        return productDao.getByUUID(uuid);
    }

    public List<Product> getByCategoryId(Integer id) {
        Category category = categoryDao.find(id);
        List<Product> products = new LinkedList<Product>();
        Queue<Category> categoryQueue = new LinkedList<Category>();
        categoryQueue.add(category);

        while(!categoryQueue.isEmpty()) {
            Category cat = categoryQueue.remove();
            Set<Category> children = cat.getChildren();
            for(Category child : children) {
                categoryQueue.add(child);
            }
            List<Product> catProducts = productDao.getByCategoryId(cat.getId());
            if(catProducts != null) {
                products.addAll(catProducts);
            }
        }

        return products;

    }

    public List<Product> getByInnId(Integer id) {
        return productDao.getByInnId(id);
    }

    public List<Product> getByFeatureId(Integer id) {
        return productDao.getByFeatureId(id);
    }

    public List<Product> getByManufacturerId(Integer id) {
        return productDao.getByManufacturerId(id);
    }

    public void delete (Integer id) {
        Product product = productDao.find(id);

        if(product == null) {
            throw new RestException(Status.ERROR,"The product does not exist");
        }
        product.setDeletedBy(currentUser.getUser().getId());
        product.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        productDao.delete(product);
    }

    public void addImage (Integer id,Image image) {
        Product product = productDao.find(id);
        if(product == null) {
            throw new RestException(Status.ERROR,"The product does not exist");
        }
        Set<Image> images =  product.getImages();
        Set<Image> imagesNew = new HashSet<Image>();
        if(images.size() == 3) {
            Image oldestImage = null;
            int idx = 0;
            for(Image img : images) {
                if(idx == 0) {
                    oldestImage = img;
                } else if(oldestImage.getId() > img.getId()) {
                    oldestImage = img;
                }

                idx++;
            }

            if(oldestImage != null) {
                for(Image img : images) {
                    if(img.getId().equals(oldestImage.getId()))  {
                        img = image;
                    }
                    imagesNew.add(img);
                }
            }

        } else {
            imagesNew.addAll(images);
            imagesNew.add(image);
        }

        product.setImages(imagesNew);
        productDao.update(product);

    }

    public List<Product> search (String pattern) {
        return productDao.search(pattern);
    }
}