package com.example.demo.request;

import lombok.*;
import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {

    @NotNull(message = "Product ID cannot be null")
    @Min(value = 1)
    private Long productId;
}