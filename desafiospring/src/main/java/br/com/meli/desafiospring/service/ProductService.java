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


    private ProductRepository productRepository;


//    public static List<Product> produtos = Collections.synchronizedList(new ArrayList<Product>());
//
//    static {
//        produtos.addAll(Arrays.asList(
//                new Product(1L,"Serra de Bancada","Ferramentas","FORTGPRO", BigDecimal.valueOf(1800.0), 5,true,"****"),
//                new Product(2L,"Furadeira","Ferramentas","Black & Decker", BigDecimal.valueOf(500.0), 7,true,"****"),
//                new Product(3L,"Carro","Veiculo","Aston Martin", BigDecimal.valueOf(2000.0), 1,true,"******"),
//                new Product(4L,"Bola","Lazer","Nike", BigDecimal.valueOf(50.0), 5,true,"***"),
//                new Product(5L,"Lapis","Papelaria","Bic", BigDecimal.valueOf(5.0), 7,true,"*")
//        ));
//    }


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





