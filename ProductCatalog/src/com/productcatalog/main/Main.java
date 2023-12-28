package com.productcatalog.main;

import com.productcatalog.exceptions.FailedToUpdateException;
import com.productcatalog.model.Product;
import com.productcatalog.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Product> allProducts;

    public static void main(String[] args) {
//        System.out.println("Hello world!");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("-------------------------");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. List Available Products");
            System.out.println("4. List All Products");
            System.out.println("5. Delete a product");
            System.out.print("Enter your choice: ");

            int value = scanner.nextInt();
            scanner.nextLine();

            switch (value) {
                case 1:
                    handleAddProduct();
                    break;
                case 2:
                    handleUpdateProduct();
                    break;
                case 3:
                    listAvailable();
                    break;
                case 4:
                    listAllProducts();
                    break;
                case 5:
                    handleDeleteProduct();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
//            System.out.println("-------------------------");

        }

//////////////////////////---workspace---//////////////////////////
//        handleDeleteProduct(3);
//////////////////////////---workspace---//////////////////////////
    }

    private static void handleDeleteProduct() {
        ProductService productService=new ProductService();
        listAllProducts();
        Scanner scanner=new Scanner(System.in);
        System.out.println("select an ID");
        int productId=scanner.nextInt();
        scanner.nextLine();
        int status =productService.deleteProduct(productId);
        if(status==1) System.out.println("successfully deleted");
        else System.out.println("unsuccessful");
    }
    private static void listAllProducts() {
        ProductService productService=new ProductService();
        List<Product> productList=productService.listAllProducts();
        for(Product product:productList){
            System.out.println(product);
        }
    }

    private static void listAvailable() {
        ProductService productService=new ProductService();
        List<Product> productList=productService.listAvailable();
        for(Product product:productList){
            System.out.println(product);
        }
    }

    private static void handleUpdateProduct() {
        listAllProducts();
        ProductService productService = new ProductService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a product Id:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Is the product available? (y/n)?: ");
        String yesOrNo = scanner.next();
        boolean isAvailable = yesOrNo.equalsIgnoreCase("y");

        Product newProduct = new Product(id, productName, price, quantity, isAvailable);
        try {
            int status = productService.updateProduct(newProduct);
            if (status == 0) {
                throw new FailedToUpdateException("Failed to update the product.");
            } else {
                System.out.println("Successfully Updated!!");
            }
        } catch (FailedToUpdateException exception) {
            // Handling the exception //
            System.out.println("Error!!!: " + exception.getMessage());
        }

    }


    private static void handleAddProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Is the product available? (y/n)?: ");
        String yesOrNo = scanner.next();
        boolean isAvailable = yesOrNo.equalsIgnoreCase("y");

        Product newProduct = new Product(0, productName, price, quantity, isAvailable);
        ProductService productService = new ProductService();
        int status = productService.addNewProduct(newProduct);
        if (status == 1) {
            System.out.println("Successfully added!");
        } else {
            System.out.println("Failed to add the product");
        }
//        scanner.close();
    }

}