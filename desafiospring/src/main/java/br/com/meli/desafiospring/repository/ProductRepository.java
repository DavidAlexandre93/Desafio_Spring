package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private ObjectMapper objectMapper = new ObjectMapper();

    public void writeFile(List<Product> input) {
        try {
            objectMapper.writeValue(Paths.get("src/main/java/br/com/meli/desafiospring/files/products.json").toFile(), input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> readFile() {
        try {
            List<Product> products = objectMapper.readValue(new File("src/main/java/br/com/meli/desafiospring/files/products.json"), new TypeReference<List<Product>>(){});
            return products;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
