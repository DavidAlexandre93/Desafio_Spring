package br.com.meli.desafiospring.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 *
 * Method: Entity Product
 * Description: Validacaes e busca por hash em producId, declaracao dos atributos Product
 *
 */
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
