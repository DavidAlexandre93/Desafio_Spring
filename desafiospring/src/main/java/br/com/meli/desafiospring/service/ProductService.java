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
     * Author: Maik Henrique e Matheus Guerra
     * Method:
     * Description: Comparador utilizado no processo de filtragem da lista de produtos existentes correspondentes aos requisitos:
     * R005, R006, R007;
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

    /**
     * Author: Bruno Mendes
     * Method: Metodo para criar novos produtos
     * Description: Recebe lista de produtos com campos validados, e envia para repository
     *
     * @param input Entrada com lista de objetos seguindo os atribuitos da Class Product no formato JSON;
     *
     * @return Lista com os produtos cadastrados;
     */
    public List<Product> createProducts(ArticlesDTO input) {
  
        List<Product> newProducts = input.getArticles();
        productRepository.writeFile(newProducts);
        return newProducts;
    }

    /**
     * Author: Maik Henrique e Matheus Guerra
     * Method: Procurar produtos por parametros
     * Description: Servi??o responsavel por filtras a lista de produtos em fun????o dos parametros informados pelo usuario;
     *
     * @param category atributo de Product usada no processo de filtragem;
     * @param freeShipping atributo de Product usada no processo de filtragem;
     * @param orderBy op????o escolhida para filtragem em rela????o a ordem alfabetica ou por pre??o:
     *                             orderBy = 0: Orderm alfab??tica crescente;
     *                             orderBy = 1: Orderm alfab??tica decrescente;
     *                             orderBy = 2: Ordem do maior pre??o para o menor;
     *                             orderBy = 3: Ordem do menor pre??o para o maior;
     *
     * @return Lista de produtos apos o processo de filtragem;
     */
    public List<Product> findByCriteria(String category, Boolean freeShipping, Integer orderBy) {
        return productRepository.findAll()
                .stream().sorted(ProductService.p.apply(orderBy)).collect(Collectors.toList());
    }

    /**
     * Author: Maik
     * Method: Venda de produtos do carrinho de compras
     * Description: Servi??o responsavel pelo processo de venda na aplica????o
     *
     * @param shoppingCart representa lista de produtos que estao na requisi??ao de compra;
     *
     * @return informa????es sobre os produtos informados na requisi????o de compra mais valor total da compra;
     */
    public ShoppingCart sellProducts(ShoppingCart shoppingCart) {
        ShoppingCart result = new ShoppingCart();
        List<Product> boughtProducts = findTargetProducts(shoppingCart);

        BigDecimal total = calculateTotalPrice(boughtProducts);
        result.setTotal(total);
        result.setArticlesPurchaseRequest(boughtProducts);
        return result;
    }

    /**
     * Author: Maik
     * Method: Procurar produtos e adicionar ao carrinho de compras
     * Description: Servi??o responsavel pelo processo de busca de produtos de uma lista de compras, atualiza????o dos valores de
     * estoque para cada tipo de produto presente na lista de compras e valida????o da compra;
     *
     * @param shoppingCart lista de compras apos os processos de valida????o;
     *
     * @return lista com as informa????es dos produtos presentes na requesi????o de compra
     */
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

    /**
     * Author: Maik
     * Method: Atualizar quantidade de produtos na lista
     * Description: Servi??o responsavel por atualizar a quantidade de um tipo de produto cadastrado apos uma venda;
     *
     * @param product tipo de produto a ter a sua quantidade atualizada;
     * @param newProductQuantity nova quantidade de produtos disponivel no sistema;
     */
    public void updateProductQuantity(Product product, int newProductQuantity) {
        try {
            Product newProduct = (Product) product.clone();
            newProduct.setQuantity(newProductQuantity);
            productRepository.updateProduct(product, newProduct);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("An error occured while updating the database");
        }
    }

    /**
     * Author: Maik
     * Method: Calcular preco total
     * Description: Servi??o que calcula o pre??o total de uma requisi????o de venda;
     *
     * @param products lista de produtos presentes no carrinho de compras;
     * @return valor total da compra requisitada;
     */
    private BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream().map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     *
     * Method: Buscar todos os produtos na lista
     * Description: Servi??o responsavel por retornar uma lista com todos os produtos presentes na aplica????o;
     *
     * @return lista de produtos cadastrados;
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Author: Mariana Galdino
     * Method: retorno de lista
     * Description: leitura da lista de produtos e filtra por categoria
     * @return
     */
    public List<Product> getProductsByCategory(String category) {

        List<Product> products = productRepository.findAll();

        if (category != null && !category.isEmpty()) {
            return products.stream()
                    .filter(product -> product.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        return products;
    }

    /**
     * Author:
     * Method:
     * Description: Servi??o responsavel por retornar uma lista com todos os produtos presentes na aplica????o;
     *
     * @return lista de produtos cadastrados;
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Author: David Alexandre
     * Method: Retornar somente o dado buscado na lista de produtos
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos;
     *
     * @param category Atributo da Class Product usada no processo de filtragem;
     * @param freeShipping Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por categoria e condicao de envio;
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
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos;
     *
     * @param name Atributo da Class Product usada no processo de filtragem;
     * @param brand Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por nome e marca;
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
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos;
     *
     * @param price Atributo da Class Product usada no processo de filtragem;
     * @param quantity Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por preco e quantidade disponivel;
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
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos;
     *
     * @param name Atributo da Class Product usada no processo de filtragem;
     * @param category Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por nome e categoria;
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
     *
     * @param productId Atributo da Class Product usada no processo de filtragem;
     * @param prestige Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados pelo Id do produto e prestigio;
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





