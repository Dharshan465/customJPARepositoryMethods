package ford.assignment15.customJPARepositoryMethods.service;

import ford.assignment15.customJPARepositoryMethods.Repository.ProductRepository;
import ford.assignment15.customJPARepositoryMethods.exception.*;
import ford.assignment15.customJPARepositoryMethods.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    //save() : First Time adds it after that updates it.
    // findById() : returns Optional<T> object(entity)
    // findAll() : returns List<T> object(entity)
    // deleteById() : void
    // saveAndFlush() : It saves the entity and flushes changes instantly.
    //flush() : Synchronizes the persistence context to the underlying database.It forces the changes made in the entity manager to be written to the database immediately.


    @Override
    public Product addProduct(Product product) throws ProductException, InvalidProductDataException {
        this.productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductById(Integer productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        } else {
            return optionalProduct.get();
        }
    }

    @Override
    public Collection<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> products = this.productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No Products Found in the Database");
        } else {
            return products;
        }
    }

    @Override
    public Product updateProductPriceById(Integer productId, Double price) throws ProductNotFoundException, InvalidProductDataException {
        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        } else if (price <= 0) {
            throw new InvalidProductDataException("Product Price cannot be negative");
        } else {
            Product product = optionalProduct.get();
            product.setPrice(price);
            this.productRepository.save(product);
            return product;
        }
    }

    @Override
    public Product updateStock(Integer productId, Integer quantity) throws ProductNotFoundException, InvalidProductDataException {
        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        } else if (quantity <= 0) {
            throw new InvalidProductDataException("Product Quantity cannot be negative");
        } else {
            Product product = optionalProduct.get();
            product.setStock(quantity);
            this.productRepository.save(product);
            return product;
        }
    }

    @Override
    public String deleteProduct(Integer productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        } else {
            Product deleteProduct= optionalProduct.get();
            this.productRepository.delete(deleteProduct);
            return "Product with id " + productId + " deleted successfully";
        }
    }

    @Override
    public List<Product> getProductsByPriceRange(Double min, Double max) throws ProductNotFoundException {
        List<Product> products = this.productRepository.findByPriceBetween(min, max);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No Products Found in the price range " + min + " to " + max);
        } else {
            return products;
        }
    }

    @Override
    public List<Product> getProductsByNameContaining(String name) throws ProductNotFoundException {
        List<Product> products = this.productRepository.findByNameContainingIgnoreCase(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No Products Found with the name containing " + name);
        } else {
            return products;
        }
    }

    @Override
    public List<Product> getProductsByCategory(List<String> categories) throws ProductNotFoundException {
        List<Product> products = this.productRepository.findByCategoryIn(categories);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No Products Found in the categories " + categories);
        } else {
            return products;
        }
    }
//

//    @Override
//    public Product searchProductByName(String keyword) throws ProductNotFoundException {
//        Optional<Product> optionalProduct = this.productRepository.findOne(Example.of(new Product(null, keyword, null, null,null,null,null)));
//        if (optionalProduct.isEmpty()) {
//            throw new ProductNotFoundException("No Products Found with the keyword " + keyword);
//        } else {
//            return optionalProduct.get();
//        }
//    }
//
//    @Override
//    public Collection<Product> getAllProductsByName(String name) throws ProductNotFoundException {
//        //Collection <Product> products=this.productRepository.findByName(name);
//        //Collection<Product> products=this.productRepository.findByNameContaining(name);
//        // Collection<Product> products=this.productRepository.findByNameContainingOrderByPriceDesc(name);
//        Collection<Product> products = this.productRepository.searchProductByNameContainingOrderByStockAsc(name);
//        if (products.isEmpty()) {
//            throw new ProductNotFoundException("No Products Found with the name " + name);
//        } else {
//            return products;
//        }
//    }
//
//    @Override
//    public Collection<Product> getAllProductsByStock(Integer stock) throws ProductNotFoundException {
//        Collection <Product> products=this.productRepository.findProductsByStockLessThanEqual(stock);
//        if (products.isEmpty()) {
//            throw new ProductNotFoundException("No Products Found with stock less than or equal to " + stock);
//        } else {
//            return products;
//        }
//    }
//
//    @Override
//    public Collection<Product> getAllProductsByPriceRange(Double min, Double max) throws ProductNotFoundException {
//        Collection<Product> products = this.productRepository.findProductsByPriceBetween(min, max);
//        if (products.isEmpty()) {
//            throw new ProductNotFoundException("No Products Found in the price range " + min + " to " + max);
//        } else {
//            return products;
//        }
//    }
//
//    @Override
//    public Collection<Product> getAllProductByNameAndPrice(String name, Double price) throws ProductNotFoundException {
//Collection<Product> products = this.productRepository.getProductsByNameAndPrice(name, price);
//        if (products.isEmpty()) {
//            throw new ProductNotFoundException("No Products Found with name " + name + " and price less than or equal to " + price);
//        } else {
//            return products;
//        }
//    }
//
//
//
//
//    @Override
//    public Collection<Product> getProductsByName(String name) throws ProductNotFoundException {
//        Collection<Product> products = this.productRepository.searchByName(name);
//        if (products.isEmpty()) {
//            throw new ProductNotFoundException("No Products Found with name " + name);
//        } else {
//            return products;
//        }
//    }
//
////    @Override
////    public ElectronicProduct addElectronicProduct(ElectronicProduct electronicProduct) throws ProductException, InvalidProductDataException {
////        this.productRepository.save(electronicProduct);
////        return electronicProduct;
////
////    }
}
