package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.repository.ProductRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import br.com.meli.desafiospring.dto.ArticlesDTO;
import br.com.meli.desafiospring.dto.ProductPurchaseRequestDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.entity.ShoppingCart;
import br.com.meli.desafiospring.enums.ProductOrderByEnum;
import br.com.meli.desafiospring.exception.DuplicatedResourceException;
import br.com.meli.desafiospring.exception.OutOfStockException;
import br.com.meli.desafiospring.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final List<ShoppingCartValidator> shoppingCartValidators;

    /**
     * R005, R006, R007
     */
    private static Function<Integer, Comparator<Product>> p = orderBy -> (Comparator<Product>) (o1, o2) -> {
        int result = 0;

        if (ProductOrderByEnum.PRICE_ASC.getValue().equals(orderBy)) {
            result = o2.getPrice().compareTo(o1.getPrice());
        } else if (ProductOrderByEnum.PRICE_DESC.getValue().equals(orderBy)) {
            result = o1.getPrice().compareTo(o2.getPrice());
        } else if (ProductOrderByEnum.NAME_ASC.getValue().equals(orderBy)) {
            result = o1.getName().compareTo(o2.getName());
        } else if (ProductOrderByEnum.NAME_DESC.getValue().equals(orderBy)) {
            result = o2.getName().compareTo(o1.getName());
        }

        return result;
    };


    public List<Product> createProducts(ArticlesDTO input) {
        List<Product> newProducts = input.getArticles();
        productRepository.writeFile(newProducts);
        return newProducts;
    }

    public List<Product> findByCriteria(String category, Boolean freeShipping, Integer orderBy) {
        return productRepository.findAll()
                .stream().sorted(ProductService.p.apply(orderBy)).collect(Collectors.toList());
    }

    public ShoppingCart sellProducts(ShoppingCart shoppingCart) {
        ShoppingCart result = new ShoppingCart();
        List<Product> boughtProducts = findTargetProducts(shoppingCart);

        BigDecimal total = calculateTotalPrice(boughtProducts);
        result.setTotal(total);
        result.setArticlesPurchaseRequest(boughtProducts);
        return result;
    }

    private List<Product> findTargetProducts(ShoppingCart shoppingCart) {
        Map<Long, Product> productsMap = productRepository.getProductsMap();

        return shoppingCart.getArticlesPurchaseRequest().stream()
                .map(shoppingCartProduct -> {
                    Product product = productsMap.get(shoppingCartProduct.getProductId());
                    shoppingCartValidators.stream().forEachOrdered(validator -> validator.validate(product, shoppingCartProduct));
                    int newProductQuantity = product.getQuantity() - shoppingCartProduct.getQuantity();
                    updateProductQuantity(product, newProductQuantity);
                    product.setQuantity(shoppingCartProduct.getQuantity());
                    return product;
                }).collect(Collectors.toList());
    }

    public void updateProductQuantity(Product product, int newProductQuantity) {
        try {
            Product newProduct = (Product) product.clone();
            newProduct.setQuantity(newProductQuantity);
            productRepository.updateProduct(product, newProduct);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("An error occured while updating the database");
        }
    }

    private BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream().map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {

        List<Product> products = productRepository.findAll();

        if (category != null && !category.isEmpty()) {
            return products.stream()
                    .filter(product -> product.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        return products;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Author: David Alexandre
     * Method: Retornar somente o dado buscado na lista de produtos
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos
     * @param category
     * @param freeShipping
     * @return
     */
    public List<Product> listaPorCategoriaFreeshipping(String category, Boolean freeShipping){
        List<Product> productList = new ArrayList<>();
        for (Product p: productRepository.findAll()){
            if (p.getCategory().equals(category) && (p.getFreeShipping().equals(freeShipping))){
                productList.add(p);
            }
        }
        return productList;
    }

    /**
     * Author: David Alexandre
     * Method: Retornar somente o dado buscado na lista de produtos
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos
     * @param name
     * @param brand
     * @return
     */
    public List<Product> listaPorNameBrand(String name, String brand){
        List<Product> productList = new ArrayList<>();
        for (Product p: productRepository.findAll()){
            if(p.getName().equals(name) && (p.getBrand().equals(brand))){
                productList.add(p);
            }
        }
        return productList;
    }

    /**
     * Author: David Alexandre
     * Method: Retornar somente o dado buscado na lista de produtos
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos
     * @param price
     * @param quantity
     * @return
     */
    public List<Product> listaPorPriceQuantity(BigDecimal price, Integer quantity){
        List<Product> productList = new ArrayList<>();
        for (Product p: productRepository.findAll()){

            //String convertToString = p.getPrice().toString().valueOf(price);

            if(p.getPrice().equals(price) && (p.getQuantity().equals(quantity))){
                productList.add(p);
            }
        }
        return productList;
    }

    /**
     * Author: David Alexandre
     * Method: Retornar somente o dado buscado na lista de produtos
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos
     * @param name
     * @param category
     * @return
     */
    public List<Product> listaPorNameCategory(String name, String category){
        List<Product> productList = new ArrayList<>();
        for (Product p: productRepository.findAll()){
            if(p.getName().equals(name) && (p.getCategory().equals(category))){
                productList.add(p);
            }
        }
        return productList;
    }

    /**
     * Author: David Alexandre
     * Method: Retornar somente o dado buscado na lista de produtos
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos
     * @param productId
     * @param prestige
     * @return
     */
    public List<Product> listaPorProductIdPrestige(Long productId, String prestige){
        List<Product> productList = new ArrayList<>();
        for (Product p: productRepository.findAll()){

            if(p.getProductId().equals(productId) && (p.getPrestige().equals(prestige))){
                productList.add(p);
            }
        }
        return productList;
    }
}





