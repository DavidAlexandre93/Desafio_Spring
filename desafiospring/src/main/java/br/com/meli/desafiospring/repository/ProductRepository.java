package br.com.meli.desafiospring.repository;


import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.util.Util;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository<T>{

    private Util<Product> Util;
    private static final String PRODUTOS_TXT = "produtos.txt";

    /**
     * Author: David Alexandre
     * Method: Realizar leitura do txt
     * Description: Realizar leitura do arquivo txt e organizando os campos dentro do array
     * @return
     */
    public List<Product> lista() {
        Util = new Util<Product>();
        List<String> registros = Util.leitura(PRODUTOS_TXT);
        List<Product> result = new ArrayList<Product>();
        registros.forEach(r->{
            String[] campos = r.split(";");
            Product product = new Product((campos[0]), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], campos[7]);
            result.add(product);
        });
        return result;
    }

}
