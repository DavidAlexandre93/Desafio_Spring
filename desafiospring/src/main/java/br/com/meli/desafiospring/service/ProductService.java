package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.repository.ProductRepository;
import br.com.meli.desafiospring.dto.ArticlesDTO;
import br.com.meli.desafiospring.dto.ProductPurchaseRequestDTO;
import br.com.meli.desafiospring.entity.ShoppingCart;
import br.com.meli.desafiospring.enums.ProductOrderByEnum;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final List<ShoppingCartValidator> shoppingCartValidators;

    public List<ProductPurchaseRequestDTO> createProducts(ArticlesDTO input) {
        List<Product> newProducts = input.getArticles();
        productRepository.writeFile(newProducts);
        return newProducts.stream().map(a -> new ProductPurchaseRequestDTO().convert(a)).collect(Collectors.toList());
    }

    public List<Product> findByCriteria(String category, Boolean freeShipping, Integer orderBy ){
        return productRepository.findAll().stream().sorted(ProductService.p.apply(orderBy)).collect(Collectors.toList());
    }

    public List<Product> sellProducts(ShoppingCart shoppingCart) {
        Map<Long, Product> idToProductMap = productRepository.findAll().stream()
                .collect(Collectors.toMap(Product::getProductId, Function.identity(), (r1, r2) -> r2));

        return shoppingCart.getArticlesPurchaseRequest().stream().map(shoppingCartProduct -> {
            Product product = idToProductMap.get(shoppingCartProduct.getProductId());
            shoppingCartValidators.stream().forEachOrdered(validator -> validator.isValid(product, shoppingCartProduct));
            int newProductQuantity = product.getQuantity() - shoppingCartProduct.getQuantity();
            updateProductQuantity(product, newProductQuantity);
            return product;
        }).collect(Collectors.toList());
    }

    private void updateProductQuantity(Product product, int newProductQuantity) {
        try {
            Product newProduct = (Product) product.clone();
            newProduct.setQuantity(newProductQuantity);
            productRepository.updateProduct(product, newProduct);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
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

    public List<Product> findAll() {
        return productRepository.findAll();
    }
  
    public List<Product> getProductsByCategory(String category){

        List<Product> products = productRepository.findAll();

        if(category!=null && !category.isEmpty() ) {
            return products.stream()
                    .filter(product -> product.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        return products;
    }
}





