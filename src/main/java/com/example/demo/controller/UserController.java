package com.example.demo.controller;

import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.UserRequest;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<User>> index() {
        List<User> users = userService.index();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> store(@Valid @RequestBody UserRequest request) {
        User user = userService.store(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        User user = userService.show(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody @Valid UserRequest request) {
        User user = userService.update(id, request);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> destroy(@PathVariable("id") Long id) {
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}/product")
    public ResponseEntity<List<Product>> listProduct(@PathVariable("id") Long id) {
        List<Product> products = userService.getProductsByUserId(id);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("{id}/product")
    public ResponseEntity<?> addProduct(@PathVariable("id") Long id, @RequestBody @Valid AddProductRequest request) {
        boolean result = userService.addProductByUserId(id, request.getProductId());
        if (result) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("{id}/product/{product_id}")
    public ResponseEntity<?> removeProduct(@PathVariable("id") Long id, @PathVariable("product_id") Long productId) {
        boolean result = userService.removeProductByUserId(id, productId);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
