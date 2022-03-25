package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.exception.OutOfStockException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProductStockQuantityValidator implements  ShoppingCartValidator{

    /**
     * Validador responsavel por garantir que a quantidade de um tipo de produto presente no carrinho de compras
     * seja menor ou igual a quantidade presente no estoque;
     *
     * @param stockProduct produto presente na lista de produtos cadastrados na aplicação;
     * @param targetProduct produto presente na lista de compras a ser validada;
     */
    @Override
    public void validate(Product stockProduct, Product targetProduct) {
        if (stockProduct.getQuantity() < targetProduct.getQuantity()) {
            throw new OutOfStockException(String.format("The product of id %d only has %d units available", targetProduct.getProductId(),
                    stockProduct.getQuantity()));
        }
    }
}
