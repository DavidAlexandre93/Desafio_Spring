package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.exception.ProductDoesNotExistsException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProductStockAvailabilityValidator implements ShoppingCartValidator {

    @Override
    public void isValid(Product stockProduct, Product targetProduct) {
        if (stockProduct == null) {
            throw new ProductDoesNotExistsException(String.format("Product of id %d wasn't found.", targetProduct.getProductId()));
        }
    }
}
