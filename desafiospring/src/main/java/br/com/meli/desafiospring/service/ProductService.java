package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private Product product;

    public void ProductValidator(Product product) {
        this.product = product;
    }


    /*
    Método pra validar produto em falta no estoque

    @Override
    public void valida() throws ProductException {
        if(product.getQuantity().toString().isEmpty())
            throw new ProductException("Não à produto suficiente em estoque!");
    }
    */

    @Autowired
    private ProductRepository<Product> repository;

    /*public synchronized List<Product> lista(String category, Double freeshipping, String name, String brand){

        if(category!=null && !category.isEmpty()) {
            return repository.lista(category).stream()
                    .filter(p->p.getCategory().equals(freeshipping))
                    .collect(Collectors.toList());
        }
        if(name!=null && !brand.isEmpty()) {
            return repository.lista(category).stream()
                    .filter(p->p.getName().equals(brand))
                    .collect(Collectors.toList());
        }

        return repository.lista(category);
    }*/

    public List<Product> listaPorCategoriaFreeshipping(String category, String freeShipping){
        List<Product> productList = new ArrayList<>();
        for (Product p: repository.lista()){
            if (p.getCategory().equals(category) && (p.getFreeShipping().equals(freeShipping))){
                productList.add(p);
            }
        }
        return productList;
    }

    public List<Product> listaPorNameBrand(String name, String brand){
        List<Product> productList = new ArrayList<>();
        for (Product p: repository.lista()){
            if(p.getName().equals(name) && (p.getBrand().equals(brand))){
                productList.add(p);
            }
        }
        return productList;
    }

    public List<Product> listaPorPriceQuantity(String price, String quantity){
        List<Product> productList = new ArrayList<>();
        for (Product p: repository.lista()){
            if(p.getPrice().equals(price) && (p.getQuantity().equals(quantity))){
                productList.add(p);
            }
        }
        return productList;
    }

    public List<Product> listaPorNameCategory(String name, String category){
        List<Product> productList = new ArrayList<>();
        for (Product p: repository.lista()){
            if(p.getName().equals(name) && (p.getCategory().equals(category))){
                productList.add(p);
            }
        }
        return productList;
    }

    public List<Product> listaPorProductIdPrestige(String productId, String prestige){
        List<Product> productList = new ArrayList<>();
        for (Product p: repository.lista()){
            if(p.getProductld().equals(productId) && (p.getPrestige().equals(prestige))){
                productList.add(p);
            }
        }
        return productList;
    }




    /*public synchronized Product obter(String category) {
        Optional<Product> optional = repository.lista(category).stream().filter(a->a.getCategory().equals(category)).findFirst();
        return optional.orElse(new Product());
    }*/


}
