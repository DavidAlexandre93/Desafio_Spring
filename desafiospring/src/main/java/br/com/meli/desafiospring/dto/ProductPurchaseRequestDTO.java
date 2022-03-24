package br.com.meli.desafiospring.dto;

import br.com.meli.desafiospring.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPurchaseRequestDTO {

    private Long productId;
    private String name;
    private Integer quantity;
    private String brand;

    public ProductPurchaseRequestDTO convert (Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        return this;
    }

}