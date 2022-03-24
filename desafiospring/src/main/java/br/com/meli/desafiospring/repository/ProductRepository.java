package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.util.FilePersistenceJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@AllArgsConstructor
public class ProductRepository {
    private final FilePersistenceJson<Product> filePersistence;

    public void writeFile(List<Product> input) {
        input.stream().forEach(p -> {
            try {
                filePersistence.writeToFile(p, "src/main/java/br/com/meli/desafiospring/files/products.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
