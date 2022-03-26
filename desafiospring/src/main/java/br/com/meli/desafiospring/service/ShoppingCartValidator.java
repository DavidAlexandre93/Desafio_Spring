package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import org.springframework.stereotype.Component;

/**
 *  @Description: Interface que representa as operações de validação da aplicação em relação ao processo de compra.
 */
@Component
public interface ShoppingCartValidator {
    void validate(Product stockProduct, Product targetProduct);
}
