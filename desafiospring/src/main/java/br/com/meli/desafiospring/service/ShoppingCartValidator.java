package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import org.springframework.stereotype.Component;

@Component
public interface ShoppingCartValidator {
    void validate(Product stockProduct, Product targetProduct);
}
