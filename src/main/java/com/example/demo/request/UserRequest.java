package com.example.demo.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "First name cannot be blank")
    @Length(min = 2, max = 255, message = "Last name must be between 2 and 255 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Length(min = 2, max = 255, message = "Last name must be between 2 and 255 characters")
    private String lastName;

    @NotNull(message = "Amount cannot be null")
    @PositiveOrZero(message = "Amount should be positive")
    @Min(value = 0, message = "Amount should not be less than 0")
    @Max(value = 10000000, message = "Amount should not be greater than 10000000")
    private Double amount;

}