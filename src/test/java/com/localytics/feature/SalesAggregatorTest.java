package com.localytics.feature;

import com.localytics.Product;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by pradeepraman on 6/1/17.
 */
public class SalesAggregatorTest {

    @Test
    public void testPrepProductsWithSalesAndCategories() {
        SalesAggregator salesAggregator = new SalesAggregator();

        Map<String, String> productCategories = new HashMap<>();
        productCategories.put("Kellogs", "Snacks");
        productCategories.put("Smuckers", "Spices");

        Map<String, BigDecimal> productTotalSales = new HashMap<>();
        productTotalSales.put("Kellogs", new BigDecimal("100.0"));
        productTotalSales.put("Smuckers", new BigDecimal("50.0"));

        Set<Product> actualProducts = salesAggregator.prepProductsWithSalesAndCategories(productCategories, productTotalSales);

        Product product = new Product("Kellogs", "Snacks", new BigDecimal("100.0"));
        Product anotherProduct = new Product("Smuckers", "Spices", new BigDecimal("50.0"));
        Assert.assertTrue(actualProducts.contains(product));
        Assert.assertTrue(actualProducts.contains(anotherProduct));
    }

    @Test
    public void testTotalSalesForOrganicProducts() {
        SalesAggregator salesAggregator = new SalesAggregator();


        Product product = new Product("Kellogs Organic", "Snacks", new BigDecimal("100.0"));
        Product anotherproduct = new Product("Smuckers", "Snacks", new BigDecimal("50.0"));
        Product yetAnotherproduct = new Product("Heinz Organic", "Snacks", new BigDecimal("70.0"));
        Set<Product> products = new HashSet<>();
        products.add(product);
        products.add(anotherproduct);
        products.add(yetAnotherproduct);

        BigDecimal actualSalesForOrganic = salesAggregator.totalSalesForOrganicProducts(products, "Organic");

        Assert.assertEquals(new BigDecimal("170.0"), actualSalesForOrganic);
    }

    @Test
    public void testMinimumSalesPrice() {
        SalesAggregator salesAggregator = new SalesAggregator();


        Product product = new Product("Kellogs Organic", "Canned Goods", new BigDecimal("100.0"));
        Product anotherproduct = new Product("Smuckers", "Canned Goods", new BigDecimal("50.0"));
        Product yetAnotherproduct = new Product("Heinz Organic", "Snacks", new BigDecimal("70.0"));
        Set<Product> products = new HashSet<>();
        products.add(product);
        products.add(anotherproduct);
        products.add(yetAnotherproduct);

        BigDecimal actualSalesForCannedGoods = salesAggregator.minimumSalesPrice(products, "Canned Goods");

        Assert.assertEquals(new BigDecimal("50.0"), actualSalesForCannedGoods);
    }

    @Test
    public void testMaximumSalesPrice() {
        SalesAggregator salesAggregator = new SalesAggregator();


        Product product = new Product("Kellogs Organic", "Canned Goods", new BigDecimal("100.0"));
        Product anotherproduct = new Product("Smuckers", "Canned Goods", new BigDecimal("50.0"));
        Product yetAnotherproduct = new Product("Heinz Organic", "Snacks", new BigDecimal("70.0"));
        Set<Product> products = new HashSet<>();
        products.add(product);
        products.add(anotherproduct);
        products.add(yetAnotherproduct);

        BigDecimal actualSalesForCannedGoods = salesAggregator.maximumSalesPrice(products, "Canned Goods");

        Assert.assertEquals(new BigDecimal("100.0"), actualSalesForCannedGoods);
    }
}
