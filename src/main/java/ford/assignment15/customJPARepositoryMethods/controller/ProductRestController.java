package ford.assignment15.customJPARepositoryMethods.controller;

import ford.assignment15.customJPARepositoryMethods.exception.*;
import ford.assignment15.customJPARepositoryMethods.model.Product;
import ford.assignment15.customJPARepositoryMethods.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws ProductException,InvalidProductDataException{
    Product createdProduct = productService.addProduct(product);
    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @GetMapping
    public Collection<Product> getAllProducts() throws ProductNotFoundException {
        return productService.getAllProducts();
    }

    @PatchMapping("/{id}/price")
    public Product updateProductPriceById(@PathVariable Integer id, @RequestParam(value="price",defaultValue = "product", required=false) Double newPrice) throws ProductNotFoundException, InvalidProductDataException {
        return productService.updateProductPriceById(id, newPrice);
    }

    @PatchMapping("/{id}/stock")
    public Product updateProductStockById(@PathVariable Integer id, @RequestParam(value="stock",defaultValue = "Product",required = false) Integer newStock) throws ProductNotFoundException, InvalidProductDataException {
        return productService.updateStock(id, newStock);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable Integer id) throws ProductNotFoundException {
        return productService.deleteProduct(id);
    }

    //---------------------------------------------------------------------------------------------------

    @GetMapping("/search/price-range")
    public List<Product> getProductsByPriceRange(
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) throws ProductNotFoundException {
        return productService.getProductsByPriceRange(min, max);
    }

    @GetMapping("/search/name")
    public List<Product> getProductsByNameContaining(@RequestParam String name) throws ProductNotFoundException {
        return productService.getProductsByNameContaining(name);
    }

    @GetMapping("/search/category")
    public List<Product> getProductsByCategory(@RequestParam List<String> categories) throws ProductNotFoundException {
        return productService.getProductsByCategory(categories);
    }

    //---------------------------------------------------------------------------------------------------


    @GetMapping("/search/top3-cheapest")
    public List<Product> getTop3CheapestProductsInCategory(@RequestParam String category) throws ProductNotFoundException {
        return productService.getTop3CheapestProductsInCategory(category);
    }

    @GetMapping("/search/newest")
    public List<Product> getNewestProductsAddedLastWeek() throws ProductNotFoundException {
        return productService.getNewestProductsAddedLastWeek();
    }

    //---------------------------------------------------------------------------------------------------

    @GetMapping("/analytics/average-price-per-category")
    public List<Object[]> getAveragePricePerCategory() throws ProductNotFoundException {
        return productService.getAveragePricePerCategory();
    }

    @GetMapping("/analytics/category-with-max-products")
    public List<Object[]> getCategoryWithMaxProducts() throws ProductNotFoundException {
        return productService.getCategoryWithMaxProducts();
    }

    @GetMapping("/analytics/total-stock-value-per-category")
    public List<Object[]> getTotalStockValuePerCategory() throws ProductNotFoundException {
        return productService.getTotalStockValuePerCategory();
    }

    //---------------------------------------------------------------------------------------------------

    @GetMapping("/search/stock-equals")
    public List<Product> getStockEquals(@RequestParam Integer stock) throws ProductNotFoundException {
        return productService.getStockEquals(stock);
    }
    @GetMapping("/search/stock-less-than")
    public List<Product> getStockLessThan(@RequestParam Integer stock) throws ProductNotFoundException {
        return productService.getStockLessThan(stock);
    }
    @GetMapping("/search/before-expiry-date")
    public List<Product> getProductsBeforeExpiryDate(@RequestParam String date) throws ProductNotFoundException {
        return productService.getProductsBeforeExpiryDate(java.time.LocalDate.parse(date));
    }
    //---------------------------------------------------------------------------------------------------

    @PutMapping("/update/price-by-percentage")
    public List<Product> increasePriceByPercentageInCategory(@RequestParam String category, @RequestParam Double percentage) throws ProductNotFoundException, InvalidProductDataException {
        return productService.increasePriceByPercentageInCategory(category, percentage);
    }

    @DeleteMapping("/remove/zero-stock")
    public List<Product> removeProductsEqualsZero() throws ProductNotFoundException {
        return productService.removeProductsEqualsZero();
    }
    //---------------------------------------------------------------------------------------------------
    @GetMapping("/search/most-expensive-per-category")
    public List<Product> getMostExpensiveProductInEachCategory() throws ProductNotFoundException {
        return productService.getMostExpensiveProductInEachCategory();
    }
    //---------------------------------------------------------------------------------------------------

    @GetMapping("/page")
    public List<Product> getProductsByCategoryWithPagination(@RequestParam String category,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) throws ProductNotFoundException {
        return productService.getProductsByCategory(category, page, size).getContent();
    }
    @GetMapping("/page/cheapest")
    public List<Product> getCheapestProductsWithPagination(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) throws ProductNotFoundException {
        return productService.getCheapestProducts(page, size).getContent();
    }

    //---------------------------------------------------------------------------------------------------


























//    @GetMapping("/name/{keyword}")
//    public Collection<Product> searchProductByName(@PathVariable String keyword) throws ProductNotFoundException {
//        return productService.getAllProductsByName(keyword);
//    }
//
//    @GetMapping("/price")
//    public Collection<Product> getAllProductsByPriceRange(@RequestParam Double min , @RequestParam(value="max",required = false) Double max) throws ProductNotFoundException {
//        return productService.getAllProductsByPriceRange(min, max);
//    }
//
//    @GetMapping("/stock")
//    public Collection<Product> getAllProductsByStock(@RequestParam(value= "stock" ,required = false) Integer stock) throws ProductNotFoundException {
//        return productService.getAllProductsByStock(stock);
//    }
//
//    @GetMapping("/nameAndPrice")
//    public Collection<Product> getAllProductByNameAndPrice(@RequestParam String name,@RequestParam Double price) throws ProductNotFoundException {
//        return productService.getAllProductByNameAndPrice(name,price);
//    }
//
//    @GetMapping("/searchByName")
//    public Collection<Product> getProductsByName(@RequestParam String name) throws ProductNotFoundException {
//        return productService.getProductsByName(name);
//    }


}
