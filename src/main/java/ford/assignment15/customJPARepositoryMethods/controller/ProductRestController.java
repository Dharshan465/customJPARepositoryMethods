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
