package br.com.meli.desafiospring.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

   private String productId;
   private String name;
   private String category;
   private String brand;
   private String price;
   private String quantity;
   private String freeShipping;
   private String prestige;

   /**
    * Author: David Alexandre
    * Method: Entity com os dados Produto
    * Description: Concatenar os dados Entity com ";", retornando valueof String de todos
    * @return
    */
   @Override
   public String toString() {
      return String.valueOf(productId).concat(";").concat(name).concat(";")
              .concat(category).concat(";").concat(brand).concat(";").concat(String.valueOf(price).concat(";").concat(String.valueOf(quantity).concat(";"))
                      .concat(String.valueOf(freeShipping).concat(";")).concat(prestige).concat(";"));
   }


}
