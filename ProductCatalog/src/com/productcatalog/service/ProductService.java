package com.productcatalog.service;

import com.productcatalog.dao.ProductDao;
import com.productcatalog.model.Product;

import java.util.List;

public class ProductService {
    Product product;

    public ProductService() {
    }
    public ProductService(Product product) {
        this.product = product;
    }
    List<Product> productList;
    ProductDao productDao=new ProductDao();

    public int addNewProduct(Product product){
        return productDao.addNewProduct(product);
    }
    public int updateProduct(Product product){
        return productDao.updateProduct(product);
    }
    public List<Product> listAvailable(){
        return productDao.listAvailable();
    }
    public List<Product> listAllProducts(){
        return productDao.listAllProducts();
    }
    public int deleteProduct(int productId){
        return productDao.deleteProduct(productId);
    }

}
