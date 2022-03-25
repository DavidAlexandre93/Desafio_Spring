package br.com.meli.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entidade que representa um carrinho de compras, necessario no processo de compra;
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCart {
    private List<Product> articlesPurchaseRequest;
    private BigDecimal total;
}
