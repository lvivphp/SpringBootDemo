package com.example.demo.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Name cannot be blank")
    @Length(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    //@UniqueName
    private String name;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price should be positive")
    @Min(value = 0, message = "Price should not be less than 0")
    @Max(value = 10000000, message = "Price should not be greater than 10000000")
    private Double price;

}