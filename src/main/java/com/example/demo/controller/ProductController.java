package com.example.demo.controller;

import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.request.ProductRequest;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<Product>> index() {
        List<Product> products = productService.index();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Product> store(@RequestBody @Valid ProductRequest request) {
        Product product = productService.store(request);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Product product = productService.show(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody @Valid ProductRequest request) {
        Product product = productService.update(id, request);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> destroy(@PathVariable("id") Long id) {
        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}/user")
    public ResponseEntity<Set<User>> listProduct(@PathVariable("id") Long id) {
        Set<User> users = productService.getUsersByProductId(id);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}