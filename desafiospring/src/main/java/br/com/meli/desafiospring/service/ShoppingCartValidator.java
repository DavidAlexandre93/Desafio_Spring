package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import org.springframework.stereotype.Component;

@Component
public interface ShoppingCartValidator {
    void isValid(Product stockProduct, Product targetProduct);
}
