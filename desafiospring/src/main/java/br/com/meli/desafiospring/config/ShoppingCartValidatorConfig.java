package br.com.meli.desafiospring.config;

import br.com.meli.desafiospring.service.ProductStockAvailabilityValidator;
import br.com.meli.desafiospring.service.ProductStockQuantityValidator;
import br.com.meli.desafiospring.service.ShoppingCartValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import java.util.List;

@Configuration
public class ShoppingCartValidatorConfig {
    @Bean
    public List<ShoppingCartValidator> shoppingCartValidators() {
        return Arrays.asList(
                new ProductStockAvailabilityValidator(),
                new ProductStockQuantityValidator());
    }
}
