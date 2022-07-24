package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotEnoughAmountException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Transactional
    public List<User> index() {
        return userRepository.findAll();
    }

    @Transactional
    public User show(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional
    public User store(UserRequest request) {
        if (existsFullName(request.getFirstName(), request.getLastName())) {
            throw new BadRequestException("Full name is already taken");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAmount(request.getAmount());

        return userRepository.save(user);
    }

    public Boolean existsFullName(String firstName, String lastName) {
        return userRepository.existsByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional
    public User update(Long id, UserRequest request) {
        User user = show(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAmount(request.getAmount());

        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = show(id);

        userRepository.delete(user);
    }

    @Transactional
    public List<Product> getProductsByUserId(Long id) {
        User user = show(id);

        return user.getProducts();
    }

    @Transactional
    public boolean addProductByUserId(Long id, Long productId) {
        User user = show(id);
        Product product = productService.show(productId);

        if (boughtProduct(user, product)) {
            throw new BadRequestException("Product is already bought by user");
        }

        if (user.getAmount() >= product.getPrice()) {
            user.getProducts().add(product);
            user.setAmount(user.getAmount() - product.getPrice());
            userRepository.save(user);
        } else {
            throw new NotEnoughAmountException();
        }

        return true;
    }

    public Boolean boughtProduct(User user, Product product) {
        return user.getProducts().contains(product);
    }

    @Transactional
    public boolean removeProductByUserId(Long id, Long productId) {
        User user = show(id);
        Product product = productService.show(productId);
        user.getProducts().remove(product);
        user.setAmount(user.getAmount() + product.getPrice());
        userRepository.save(user);

        return true;
    }
}
