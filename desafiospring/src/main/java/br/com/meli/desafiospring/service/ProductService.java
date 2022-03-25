package br.com.meli.desafiospring.service;

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
     * Comparador utilizado no processo de filtragem da lista de produtos existentes correspondentes aos requisitos:
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
     * Service responsavel pela criação e cadastro de produtos na lista de produtos existentes;
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
     * Serviço responsavel por filtras a lista de produtos em função dos parametros informados pelo usuario;
     *
     * @param category atributo de Product usada no processo de filtragem;
     * @param freeShipping atributo de Product usada no processo de filtragem;
     * @param orderBy opção escolhida para filtragem em relação a ordem alfabetica ou por preço:
     *                             orderBy = 0: Orderm alfabética crescente;
     *                             orderBy = 1: Orderm alfabética decrescente;
     *                             orderBy = 2: Ordem do maior preço para o menor;
     *                             orderBy = 3: Ordem do menor preço para o maior;
     *
     * @return Lista de produtos apos o processo de filtragem;
     */
    public List<Product> findByCriteria(String category, Boolean freeShipping, Integer orderBy) {
        return productRepository.findAll()
                .stream().sorted(ProductService.p.apply(orderBy)).collect(Collectors.toList());
    }

    /**
     * Serviço responsavel pela
     *
     * @param shoppingCart representa lista de produtos que estao na requisiçao de compra;
     *
     * @return informações sobre os produtos informados na requisição de compra mais valor total da compra;
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
     * Serviço responsavel pelo processo de busca de produtos de uma lista de compras, atualização dos valores de
     * estoque para cada tipo de produto presente na lista de compras e validação da compra;
     *
     * @param shoppingCart lista de compras apos os processos de validação;
     *
     * @return lista com as informações dos produtos presentes na requesição de compra
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
     * Serviço responsavel por atualizar a quantidade de um tipo de produto cadastrado apos uma venda;
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
     * Serviço que calcula o preço total de uma requisição de venda;
     *
     * @param products lista de produtos presentes no carrinho de compras;
     * @return valor total da compra requisitada;
     */
    private BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream().map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Serviço responsavel por retornar uma lista com todos os produtos presentes na aplicação;
     *
     * @return lista de produtos cadastrados;
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Serviço responsavel por retornar lista de produtos cadastrados na aplicação apos filtragem por categoria;
     *
     * @param category atributo de Product usada no processo de filtragem;
     *
     * @return lista de produtos resultantes da filtragem usando a categoria selecionada;
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
     * Serviço responsavel por retornar uma lista com todos os produtos presentes na aplicação;
     *
     * @return lista de produtos cadastrados;
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}





