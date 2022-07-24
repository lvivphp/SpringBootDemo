package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "users",
    uniqueConstraints={
        @UniqueConstraint(columnNames = {"first_name", "last_name"})
    }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "varchar(255)", length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "varchar(255)", length = 255)
    private String lastName;

    @Column(name = "amount", nullable = false, columnDefinition="DECIMAL(10,2) default '0.00'")
    private Double amount;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "user_product",
        joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )

    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, Double amount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}