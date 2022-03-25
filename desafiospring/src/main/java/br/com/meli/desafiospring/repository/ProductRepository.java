package br.com.meli.desafiospring.repository;


import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.util.FilePersistenceJson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.List;

@Repository
@AllArgsConstructor
public class ProductRepository<T>{

    private final FilePersistenceJson<Product> filePersistence;
    private static final String filePath = "src/main/java/br/com/meli/desafiospring/files/products.json";

    /**
     * Author: David Alexandre
     * Method: Realizar leitura do txt
     * Description: Realizar leitura do arquivo txt e organizando os campos dentro do array
     * @return
     */
    public void writeFile(List<Product> input) {
        input.stream().forEach(p -> {
            try {
                filePersistence.writeToFile(p, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Product> findAll(){
        return filePersistence.readObjects(filePath,Product.class);
    }

}
