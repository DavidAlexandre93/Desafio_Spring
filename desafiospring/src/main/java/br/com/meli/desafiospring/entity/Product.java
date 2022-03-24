package br.com.meli.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {

   private Long productId;
   private String name;
   private String category;
   private String brand;
   private BigDecimal price;
   private Integer quantity;
   private Boolean freeShipping;
   private String prestige;

}
