package com.localytics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by pradeepraman on 6/1/17.
 */
public class Product {

    private String name;
    private String category;
    // BigDecimal is the most accurate to store dollar values and the precision is not lost.
    private BigDecimal sales;

    public static final String PRODUCTS_CATEGORIES_FILE_PATH= "src/main/resources/products.tab";
    public static final String PRODUCTS_SALES_FILE_PATH= "src/main/resources/sales.tab";

    /**
     * methos reads the products file
     * @return a map containing product name as its key and category as its value
     * @throws IOException
     */
    public Map<String, String> categorizeProducts() throws IOException {

        Map<String, String> productAndCategories = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_CATEGORIES_FILE_PATH))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] productAndItsCategory = productFactory(line);
                if(null != productAndItsCategory && productAndItsCategory.length==2){
                    productAndCategories.put(productAndItsCategory[0].trim(), productAndItsCategory[1].trim());
                }
            }
        }

        return productAndCategories;
    }

    /**
     * methos reads the sales file and sums up the sales for each of the products
     * @return a map containing product name as its key and total sales as its value
     *
     * @throws IOException
     */
    public Map<String, BigDecimal> sumSalesForProduct() throws IOException {

        Map<String, BigDecimal> productsAndSales  = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_SALES_FILE_PATH))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] productsAndItsSales = productFactory(line);
                if(null != productsAndItsSales && productsAndItsSales.length==2){

                    if(productsAndSales.containsKey(productsAndItsSales[0])){
                        BigDecimal currentSales = productsAndSales.get(productsAndItsSales[0]);
                        BigDecimal newSales = currentSales.add(new BigDecimal(productsAndItsSales[1].trim()));
                        productsAndSales.put(productsAndItsSales[0].trim(), newSales);
                    } else {
                        productsAndSales.put(productsAndItsSales[0].trim(), new BigDecimal(productsAndItsSales[1].trim()));
                    }

                }
            }
        }

        return productsAndSales;

    }

    /**
     * this  method splits the line on tabs and preps the key value to be stored
     * @param line
     * @return a string array of size 2 in which the first and second elements are produced from the tab delimited string
     */
    private static String[] productFactory(String line){
        if(null == line || line.isEmpty())
            return null;

        String[] productCategory = line.trim().split("\\t");
        if(productCategory.length != 2)
            return null;

        return productCategory;

    }

    public Product(String name, String category, BigDecimal sales) {
        this.name = name;
        this.category = category;
        this.sales = sales;
    }

    public Product() {}

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getSales() {
        return sales;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Product)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Product c = (Product) o;

        // Compare the data members and return accordingly
        return ((Product) o).getName().equals(c.getName())
                && ((Product) o).getCategory().equals(c.getCategory())
                && ((Product) o).getSales().equals(c.getSales());
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + sales.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}
