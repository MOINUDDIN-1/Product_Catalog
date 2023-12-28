package com.productcatalog.dao;

import com.productcatalog.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    String jdbcUrl = "jdbc:mysql://localhost:3306/product_catalog_db";
    String userName = "root";
    String passwordField = "root@12345";
    Connection connection = null;

//    queries
//    CREATE TABLE product_table (
//            productId INT AUTO_INCREMENT PRIMARY KEY,
//            productName VARCHAR(255),
//    price DOUBLE,
//    quantity INT,
//    isAvailable TINYINT(1)  ///1-true 0-false
//);

    public void connectionOpen() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, userName, passwordField);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }
    }

    public void connectionClose() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public int addNewProduct(Product product) {
        int status = 0;
        try {
            connectionOpen();
            String query = "INSERT INTO product_table (productId, productName, price, quantity, isAvailable) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, product.getProductId());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.isAvailable() ? 1 : 0);

            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionClose();
        }
        return status;
    }

    public int deleteProduct(int productId) {
        int status = 0;
        try {
            connectionOpen(); // Assuming this function opens the connection
            String query = "DELETE FROM product_table WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, productId);

            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionClose();
        }

        return status;
    }

    public List<Product> listAllProducts() {
        List<Product> productList = new ArrayList<>();

        try {
            connectionOpen();
            String query = "SELECT * FROM product_table";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                boolean isAvailable = resultSet.getInt("isAvailable") == 1;

                Product product = new Product(productId, productName, price, quantity, isAvailable);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionClose();
        }

        return productList;
    }


    public List<Product> listAvailable() {
        List<Product> availableProducts = new ArrayList<>();

        try {
            connectionOpen();
            String query = "SELECT * FROM product_table WHERE isAvailable = 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                boolean isAvailable = resultSet.getInt("isAvailable") == 1;

                Product product = new Product(productId, productName, price, quantity, isAvailable);
                availableProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionClose();
        }

        return availableProducts;

    }


    public int updateProduct( Product updatedProduct) {
        int status = 0;

        try {
            connectionOpen();
            String query = "UPDATE product_table SET productName=?, price=?, quantity=?, isAvailable=? WHERE productId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, updatedProduct.getProductName());
            preparedStatement.setDouble(2, updatedProduct.getPrice());
            preparedStatement.setInt(3, updatedProduct.getQuantity());
            preparedStatement.setInt(4, updatedProduct.isAvailable() ? 1 : 0);
            preparedStatement.setInt(5, updatedProduct.getProductId());

            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionClose();
        }
        return status;

    }
}
