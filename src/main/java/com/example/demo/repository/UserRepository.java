package com.example.demo.repository;

import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    List<Product> findByProductsId(Long id);
}
