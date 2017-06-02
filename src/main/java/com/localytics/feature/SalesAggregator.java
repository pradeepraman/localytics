package com.localytics.feature;

import com.localytics.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * Created by pradeepraman on 6/1/17.
 */
public class SalesAggregator {

    /**
     * method prepares a list of Product containing the name of the product, its category and its sales
     * @param productCategories a map containing product name as its key and category as its value
     * @param productTotalSales a map containing product name as its key and total sales as its value
     * @return set of Product containing the name of the product, its category and its sales
     */
    public Set<Product> prepProductsWithSalesAndCategories(Map<String, String> productCategories,
                                                                    Map<String, BigDecimal> productTotalSales){

        if(null == productCategories || null == productTotalSales){
            return null;
        }

        Set<Product> products = new HashSet<>();
        Iterator productCategoryIterator = productCategories.entrySet().iterator();
        while (productCategoryIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) productCategoryIterator.next();

            String productName = (String)pair.getKey();
            String productCategory = (String)pair.getValue();
            BigDecimal productSales = productTotalSales.get(productName);

            Product product = new Product(productName, productCategory, productSales);
            if(null != productSales)
                products.add(product);

        }

        return products;

    }

    public static void main(String[] args) {

        try {
            Product product = new Product();
            Map<String, String> productCategories = product.categorizeProducts();
            Map<String, BigDecimal> productTotalSales = product.sumSalesForProduct();

            SalesAggregator salesAggregator = new SalesAggregator();
            Set<Product> products = salesAggregator.prepProductsWithSalesAndCategories(productCategories, productTotalSales);
            System.out.println(salesAggregator.totalSalesForOrganicProducts(products, "Organic"));
            System.out.println(salesAggregator.minimumSalesPrice(products, "Canned Goods"));
            System.out.println(salesAggregator.maximumSalesPrice(products, "Canned Goods"));

            /* the following is the answer to the question:
            1. What were the total sales for products that have the word 'Organic' in them?
            A: 507.226730845

            2. What is the minimum and maximum sales price for 'Canned Goods'?
            A:
            12.6453779
            12.6453779
            (min and max are both the same for Canned Goods)
             */


        } catch (IOException ioex){
            System.out.println("Error reading the product and sales files");
            ioex.printStackTrace();
        }

    }

    /**
     * find the total sales for a product containing a certain word in it
     * @param products set of Product containing the name of the product, its category and its sales
     * @param productName word to search for in the name of the product
     * @return bigDecimal containing the total sales for all product with the word specified in its name
     */
    public BigDecimal totalSalesForOrganicProducts(Set<Product> products, String productName){

        if(null == products){
            return null;
        }

        Function<Product, BigDecimal> bigDAdder = product -> product.getSales();
        BigDecimal result = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(productName.toLowerCase()))
                .map(bigDAdder)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return result;
    }

    /**
     * minimum sales of the product
     * @param products set of Product containing the name of the product, its category and its sales
     * @param category category to search for
     * @return minimum sales of the product
     */
    public BigDecimal minimumSalesPrice(Set<Product> products, String category){
        if(null == products){
            return null;
        }

        return products.stream()
                .filter(p -> p.getCategory().toLowerCase().equals(category.toLowerCase()))
                .map(p -> p.getSales())
                .min(Comparator.naturalOrder())  //minimum
                .orElse(null);

    }

    /**
     * maximum sales of the product
     * @param products set of Product containing the name of the product, its category and its sales
     * @param category category to search for
     * @return maximum sales of the product
     */
    public BigDecimal maximumSalesPrice(Set<Product> products, String category){
        if(null == products){
            return null;
        }

        return products.stream()
                .filter(p -> p.getCategory().toLowerCase().equals(category.toLowerCase()))
                .map(p -> p.getSales())
                .max(Comparator.naturalOrder()) //maximum
                .orElse(null);

    }

}
