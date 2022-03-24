package br.com.meli.desafiospring.dto;


import br.com.meli.desafiospring.entity.Product;
import lombok.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String productld;
    private String name;
    private String category;
    private String brand;
    private String price;
    private String quantity;
    private String freeShipping;
    private String prestige;

    /*public ProductDTO converte(Product product) {
        this.productld = product.getProductld();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.category = product.getCategory();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.freeShipping = product.getFreeShipping();
        this.prestige = product.getPrestige();
        return this;
    }*/

    public static List<ProductDTO> converte(List<Product> product) {
        return product.stream().map(p -> new ProductDTO(p.getProductld(), p.getName(), p.getCategory(), p.getBrand(),
                p.getPrice(), p.getQuantity(), p.getFreeShipping(), p.getPrestige())).collect(Collectors.toList());
    }
}
