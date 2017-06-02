package com.localytics;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by pradeepraman on 6/1/17.
 */
public class ProductTest {

    @Test
    public void testCategorizeProducts() throws Exception{
        Product product = new Product();
        Map<String, String> products = product.categorizeProducts();
        Assert.assertEquals(383, products.size());

        Assert.assertTrue(products.containsKey("Azar Gold Label Apple Cinnamon Nut Crunch"));
        String actualCategory = products.get("Azar Gold Label Apple Cinnamon Nut Crunch");
        Assert.assertEquals("Snacks" ,actualCategory);

    }

    @Test
    public void testSumSalesForProduct() throws Exception{
        Product product = new Product();
        Map<String, BigDecimal> products = product.sumSalesForProduct();
        Assert.assertEquals(360, products.size());

        Assert.assertTrue(products.containsKey("Endangered Species Gorilla Bar Milk Chocolate With Pecan Praline"));
        BigDecimal actualSales = products.get("Endangered Species Gorilla Bar Milk Chocolate With Pecan Praline");

        Assert.assertEquals(new BigDecimal("247.24805868"), actualSales);

    }
}
