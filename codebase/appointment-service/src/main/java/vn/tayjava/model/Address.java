package vn.tayjava.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Address {
    @NotBlank(message = "Province must not be empty")
    private String province;

    @NotBlank(message = "Province must not be empty")
    private String district;

    @NotBlank(message = "Ward must not be empty")
    private String ward;

    @NotBlank(message = "Street must not be empty")
    private String street;

}
