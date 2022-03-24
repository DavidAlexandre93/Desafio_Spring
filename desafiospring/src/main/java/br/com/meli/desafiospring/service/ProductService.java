package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository = new ProductRepository();

    public List<ProductDTO> createProducts(InputDTO input) {
        List<Product> newProducts = input.getArticles();
        productRepository.writeFile(newProducts);
        return newProducts.stream().map(a -> new ProductDTO().convert(a)).collect(Collectors.toList());
    }
}
