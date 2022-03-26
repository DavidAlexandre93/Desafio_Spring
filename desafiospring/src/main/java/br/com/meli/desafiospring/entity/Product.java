package br.com.meli.desafiospring.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

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

   /**
    * Author: David Alexandre
    * Method: Entity com os dados Produto
    * Description: Concatenar os dados Entity com ";", retornando valueof String de todos
    * @return
    */
   /*@Override
   public String toString() {
      return String.valueOf(productId).concat(";").concat(name).concat(";")
              .concat(category).concat(";").concat(brand).concat(";").concat(String.valueOf(price).concat(";").concat(String.valueOf(quantity).concat(";"))
                      .concat(String.valueOf(freeShipping).concat(";")).concat(prestige).concat(";"));
   }*/
}
