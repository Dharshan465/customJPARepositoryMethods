package ford.relationalMapping.assignment17.ECommerceSystem.controller;

import ford.relationalMapping.assignment17.ECommerceSystem.dto.CartDto;
import ford.relationalMapping.assignment17.ECommerceSystem.dto.UserDto;
import ford.relationalMapping.assignment17.ECommerceSystem.exception.CartException;
import ford.relationalMapping.assignment17.ECommerceSystem.exception.UserException;
import ford.relationalMapping.assignment17.ECommerceSystem.model.Cart;
import ford.relationalMapping.assignment17.ECommerceSystem.repository.CartRepository;
import ford.relationalMapping.assignment17.ECommerceSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ford.relationalMapping.assignment17.ECommerceSystem.model.User;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/ecommerce")
public class EcommerceController {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    public EcommerceController(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }


    //-------------------------------------------------------------------
    // USER MANAGEMENT ENDPOINTS
    //-------------------------------------------------------------------

    @PostMapping("/users")
    public String addUser(@RequestBody UserDto userDto) throws UserException {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserException("Username already exists.");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserException("Email already exists.");
        }
    User newUser = new User();
    newUser.setUsername(userDto.getUsername());
    newUser.setEmail(userDto.getEmail());
    newUser.setPassword(userDto.getPassword());

    Cart cart = new Cart();
    cart.setCreatedAt(LocalDateTime.now());
    cart.setTotalItems(0);
    cart.setTotalPrice(0.00);
    cart.setUser(newUser);

    userRepository.save(newUser);
    cartRepository.save(cart);
    return "User added successfully with email: " + newUser.getEmail();

    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) throws UserException {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    @DeleteMapping("/users/{id}")
    public String deleteUserById(@PathVariable Long id) throws UserException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User not found with id: " + id));
        userRepository.delete(user);
        return "User deleted successfully with id: " + id;
    }

    //-------------------------------------------------------------------
    //CART MANAGEMENT ENDPOINTS
    //-------------------------------------------------------------------

    @GetMapping("/users/{id}/cart")
    public Cart getCartByUserId(@PathVariable Long id) throws UserException,CartException {
        User user = userRepository.findById(id).orElseThrow(() -> new CartException("User not found with id: " + id));
        return cartRepository.findByUserId(id).orElseThrow(() -> new CartException("Cart not found for user with id: " + id));
    }

    @PutMapping("/users/{id}/cart")
    public Cart updateCartByUserId(@PathVariable Long id, @RequestBody CartDto updatedCart) throws UserException,CartException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User not found with id: " + id));
        Cart cart = cartRepository.findByUserId(id).orElseThrow(() -> new CartException("Cart not found for user with id: " + id));

        cart.setTotalItems(updatedCart.getTotalItems());
        cart.setTotalPrice(updatedCart.getTotalPrice());
        return cartRepository.save(cart);
    }

    @PatchMapping("/users/{id}/cart/add")
    public Cart addItemToCart(@PathVariable Long id, @RequestParam Double itemPrice) throws UserException,CartException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User not found with id: " + id));
        Cart cart = cartRepository.findByUserId(id).orElseThrow(() -> new CartException("Cart not found for user with id: " + id));

        cart.setTotalItems(cart.getTotalItems() + 1);
        cart.setTotalPrice(cart.getTotalPrice() + itemPrice);
        return cartRepository.save(cart);
    }

    @PatchMapping("/users/{id}/cart/remove")
    public Cart removeItemFromCart(@PathVariable Long id, @RequestParam Double itemPrice) throws UserException,CartException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User not found with id: " + id));
        Cart cart = cartRepository.findByUserId(id).orElseThrow(() -> new CartException("Cart not found for user with id: " + id));

        if (cart.getTotalItems() <= 0 || cart.getTotalPrice() < itemPrice) {
            throw new CartException("Cannot remove item. Cart is empty or item price is invalid.");
        }

        cart.setTotalItems(cart.getTotalItems() - 1);
        cart.setTotalPrice(cart.getTotalPrice() - itemPrice);
        return cartRepository.save(cart);
    }








}
