package br.com.meli.desafiospring.dto;

import br.com.meli.desafiospring.entity.Product;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;
    private String name;
    private String category;
    private String brand;
    private BigDecimal price;
    private Integer quantity;
    private Boolean freeShipping;
    private String prestige;

    /**
     * Author: David Alexandre
     * Method: Coletor para retornar todos os dados da lista Produto
     * Description: Buscar em entity todos os dados mapeados Produto
     * @param product
     * @return
     */
    public static List<br.com.meli.desafiospring.dto.ProductDTO> converte(List<Product> product) {
        return product.stream().map(p -> new br.com.meli.desafiospring.dto.ProductDTO(p.getProductId(), p.getName(), p.getCategory(), p.getBrand(),
                p.getPrice(), p.getQuantity(), p.getFreeShipping(), p.getPrestige())).collect(Collectors.toList());
    }
}
