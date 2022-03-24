package br.com.meli.desafiospring.entity;


import lombok.*;
import org.springframework.context.annotation.Conditional;


import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

   private String productld;
   private String name;
   private String category;
   private String brand;
   private String price;
   private String quantity;
   private String freeShipping;
   private String prestige;


   @Override
   public String toString() {
      return String.valueOf(productld).concat(";").concat(name).concat(";")
              .concat(category).concat(";").concat(brand).concat(";").concat(String.valueOf(price).concat(";").concat(String.valueOf(quantity).concat(";"))
                      .concat(String.valueOf(freeShipping).concat(";")).concat(prestige).concat(";"));
   }


}
