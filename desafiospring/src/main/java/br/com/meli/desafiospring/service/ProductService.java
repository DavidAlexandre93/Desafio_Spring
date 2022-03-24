package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.entity.ProdutoSimplificado;
import br.com.meli.desafiospring.enums.ProductOrderByEnum;
import org.springframework.stereotype.Service;
import br.com.meli.desafiospring.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;





import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> createProducts(InputDTO input) {
        List<Product> newProducts = input.getArticles();
        productRepository.writeFile(newProducts);
        return newProducts.stream().map(a -> new ProductDTO().convert(a)).collect(Collectors.toList());
    }
    public List<Product> findByCritirion(String category, Boolean freeShipping, Integer orderBy ){
        return productRepository.findAll().stream().sorted(ProductService.p.apply(orderBy)).collect(Collectors.toList());
    }

    /**
     *  R005, R006, R007
     */
    public static Function<Integer, Comparator<Product>> p = orderBy -> (Comparator<Product>) (o1, o2) -> {
        int result = 0;

        if (ProductOrderByEnum.PRICE_ASC.getValue().equals(orderBy)) {
            result = o2.getPrice().compareTo(o1.getPrice());
        }
        else if(ProductOrderByEnum.PRICE_DESC.getValue().equals(orderBy)){
            result = o1.getPrice().compareTo(o2.getPrice());
        }
        else if(ProductOrderByEnum.NAME_ASC.getValue().equals(orderBy)){
            result = o1.getName().compareTo(o2.getName());
        }
        else if(ProductOrderByEnum.NAME_DESC.getValue().equals(orderBy)){
            result = o2.getName().compareTo(o1.getName());
        }

        return result;
    };


}





