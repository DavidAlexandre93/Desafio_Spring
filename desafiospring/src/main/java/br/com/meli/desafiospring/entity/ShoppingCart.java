package br.com.meli.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 *
 * Method: Carrinho de compras
 * Description: Listar valor de produtos carrinho de compras
 *
 */

public class ShoppingCart {
    private List<Product> articlesPurchaseRequest;
    private BigDecimal total;
}
