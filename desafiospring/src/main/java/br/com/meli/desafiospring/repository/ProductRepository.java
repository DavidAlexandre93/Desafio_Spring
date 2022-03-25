package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.util.FilePersistenceJson;
import br.com.meli.desafiospring.util.FilePersistenceUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.List;

@Repository
@AllArgsConstructor
public class ProductRepository {

    private final FilePersistenceUtil<Product> filePersistence;

    private static final String FILE_PATH = "src/main/java/br/com/meli/desafiospring/files/products.json";

    public void writeFile(List<Product> input) {
        input.stream().forEach(p -> {
            try {
                filePersistence.writeToFile(p, FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Product> findAll(){
        return filePersistence.readObjects(FILE_PATH, Product.class);
    }

    public boolean updateProduct(Product oldProduct, Product newProduct) {
        return filePersistence.updateElement(FILE_PATH, oldProduct, newProduct, Product.class);
    }

}

