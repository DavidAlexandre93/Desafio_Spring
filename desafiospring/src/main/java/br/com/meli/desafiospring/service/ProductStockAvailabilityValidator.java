package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.exception.ProductDoesNotExistsException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProductStockAvailabilityValidator implements ShoppingCartValidator {

    /**
     * Author:
     * Method:
     * Description: Validador responsavel por garantir que existe pelo menos uma unidade de um tipo de produto cadastrado;
     * Utilizado no processo de validação de uma compra;
     *
     * @param stockProduct produto presente na lista de produtos cadastrados na aplicação;
     * @param targetProduct produto presente na lista de compras a ser validada;
     */
    @Override
    public void validate(Product stockProduct, Product targetProduct) {
        if (stockProduct == null) {
            throw new ProductDoesNotExistsException(String.format("Product of id %d wasn't found.", targetProduct.getProductId()));
        }
    }
}
