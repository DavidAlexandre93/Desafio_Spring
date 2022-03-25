package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductPurchaseRequestDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.entity.ShoppingCart;
import br.com.meli.desafiospring.enums.ProductOrderByEnum;
import br.com.meli.desafiospring.exception.ProductDoesNotExistsException;
import br.com.meli.desafiospring.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import br.com.meli.desafiospring.repository.ProductRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public List<ProductPurchaseRequestDTO> createProducts(InputDTO input) {
        List<Product> newProducts = input.getArticles();
        productRepository.writeFile(newProducts);
        return newProducts.stream().map(a -> new ProductPurchaseRequestDTO().convert(a)).collect(Collectors.toList());
    }

    public List<Product> findByCriteria(String category, Boolean freeShipping, Integer orderBy ){
        return productRepository.findAll().stream().sorted(ProductService.p.apply(orderBy)).collect(Collectors.toList());
    }

    public List<Product> sellProducts(ShoppingCart shoppingCart) {
        Map<Long, Product> idToProductMap = productRepository.findAll().stream()
                .collect(Collectors.toMap(Product::getProductId, Function.identity(), (r1, r2) -> r1));

        return shoppingCart.getArticlesPurchaseRequest().stream().map(shopppingCartProduct -> {
            long targetProductId = shopppingCartProduct.getProductId();
            Product product = idToProductMap.get(shopppingCartProduct.getProductId());
            if (product == null) {
                throw new ProductDoesNotExistsException(String.format("Product of id %d wasn't found.", targetProductId));
            }
            return product;

        }).collect(Collectors.toList());
    }

    /**
     *  R005, R006, R007
     */
    private static Function<Integer, Comparator<Product>> p = orderBy -> (Comparator<Product>) (o1, o2) -> {
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

    public List<Product> getProductsByCategory(String category){

        List<Product> products = productRepository.findAll();

        if(category!=null && !category.isEmpty() ) {
            return products.stream()
                    .filter(product -> product.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        return products;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}





