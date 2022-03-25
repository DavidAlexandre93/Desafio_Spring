package br.com.meli.desafiospring.dto;

import br.com.meli.desafiospring.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPurchaseRequestDTO {

    @NotNull(message = "The productId field can not be null")
    private Long productId;

    @NotBlank(message = "Plaese provide a name for the product")
    private String name;

    @NotNull
    @Range(min = 1, message = "The minimum product quantity is 1")
    private Integer quantity;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Please provide a brand")
    private String brand;


}
