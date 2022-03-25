package br.com.meli.desafiospring.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Entidade principal da aplicação que representa um produto;
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Product implements Cloneable {

   private Long productId;
   private String name;
   private String category;
   private String brand;
   private BigDecimal price;
   private Integer quantity;
   private Boolean freeShipping;
   private String prestige;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Product product = (Product) o;
      return Objects.equals(productId, product.productId);
   }

   @Override
   public int hashCode() {
      return Objects.hash(productId);
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }
}
