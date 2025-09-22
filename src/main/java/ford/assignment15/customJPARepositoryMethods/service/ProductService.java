package ford.assignment15.customJPARepositoryMethods.service;

import ford.assignment15.customJPARepositoryMethods.exception.*;
import ford.assignment15.customJPARepositoryMethods.model.Product;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductService {


    Product addProduct(Product product) throws ProductException, InvalidProductDataException;

    Product getProductById(Integer productId) throws ProductNotFoundException;

    Collection<Product> getAllProducts() throws ProductNotFoundException;

    Product updateProductPriceById(Integer productId, Double price) throws ProductNotFoundException, InvalidProductDataException;

    Product updateStock(Integer productId, Integer quantity) throws ProductNotFoundException, InvalidProductDataException;

    String deleteProduct(Integer productId) throws ProductNotFoundException;

    //---------------------------------------------------------------------------------------------------

    List<Product> getProductsByPriceRange(Double min, Double max) throws ProductNotFoundException;

    List<Product> getProductsByNameContaining(String name) throws ProductNotFoundException;

    List<Product> getProductsByCategory(List<String> categories) throws ProductNotFoundException;

    //---------------------------------------------------------------------------------------------------

    List<Product> getTop3CheapestProductsInCategory(String category) throws ProductNotFoundException;

    List<Product> getNewestProductsAddedLastWeek() throws ProductNotFoundException;

    //---------------------------------------------------------------------------------------------------

    List<Object[]> getAveragePricePerCategory() throws ProductNotFoundException;

    List<Object[]> getCategoryWithMaxProducts() throws ProductNotFoundException;

    List<Object[]> getTotalStockValuePerCategory() throws ProductNotFoundException;

    //---------------------------------------------------------------------------------------------------

    List<Product>getStockEquals(Integer stock) throws ProductNotFoundException;

    List<Product>getStockLessThan(Integer stock) throws ProductNotFoundException;

    List<Product>getProductsBeforeExpiryDate(LocalDate date) throws ProductNotFoundException;

    //---------------------------------------------------------------------------------------------------

    List<Product> increasePriceByPercentageInCategory(String category, Double percentage) throws ProductNotFoundException, InvalidProductDataException;

    List<Product> removeProductsEqualsZero() throws ProductNotFoundException;

    //---------------------------------------------------------------------------------------------------

    List<Product>getMostExpensiveProductInEachCategory() throws ProductNotFoundException;

    List<Product>getProductsNotPurchasedInLastNMonths(Integer months) throws ProductNotFoundException;

    List<Product>getProductsBelongingToMultipleCategories(List<String> categories) throws ProductNotFoundException;

    //---------------------------------------------------------------------------------------------------

    Page<Product>getProductsByCategory(String category, int page, int size) throws ProductNotFoundException;

    Page<Product>getCheapestProducts(int page, int size) throws ProductNotFoundException;

    //---------------------------------------------------------------------------------------------------



}
