package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private Product product;

    public void ProductValidator(Product product) {
        this.product = product;
    }

    @Autowired
    private ProductRepository<Product> repository;

    /**
     * Author: David Alexandre
     * Method: Retornar somente o dado buscado na lista de produtos
     * Description: Realizar a leitura da lista e pegar somente os dados passados em produtos
     * @param category
     * @param freeShipping
     * @return
     */
    public List<Product> listaPorCategoriaFreeshipping(String category, String freeShipping){
        List<Product> productList = new ArrayList<>();
        for (Product p: repository.findAll()){
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
        for (Product p: repository.findAll()){
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
    public List<Product> listaPorPriceQuantity(String price, String quantity){
        List<Product> productList = new ArrayList<>();
        for (Product p: repository.findAll()){
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
        for (Product p: repository.findAll()){
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
    public List<Product> listaPorProductIdPrestige(String productId, String prestige){
        List<Product> productList = new ArrayList<>();
        for (Product p: repository.findAll()){
            if(p.getProductId().equals(productId) && (p.getPrestige().equals(prestige))){
                productList.add(p);
            }
        }
        return productList;
    }


}
