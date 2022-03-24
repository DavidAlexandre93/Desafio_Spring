package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public void createProducts(InputDTO input) {
        List<Product> existingData = productRepository.readFile();
        List<Product> newProducts = input.getArticles();
        newProducts.stream().forEach(a -> existingData.add(a));
    }
}
