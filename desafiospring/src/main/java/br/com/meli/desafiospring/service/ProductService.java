package br.com.meli.desafiospring.service;


import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.entity.ProdutoSimplificado;
import br.com.meli.desafiospring.enums.ProductOrderByEnum;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.function.Function;

@Service
public class ProductService {


    public static Function<Integer, Comparator<ProdutoSimplificado>> p = orderBy -> (Comparator<ProdutoSimplificado>) (o1, o2) -> {
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
