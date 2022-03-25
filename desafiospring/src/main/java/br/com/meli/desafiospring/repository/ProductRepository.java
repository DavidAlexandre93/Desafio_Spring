package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.exception.DuplicatedResourceException;
import br.com.meli.desafiospring.util.FilePersistenceUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ProductRepository {

    private static final String FILE_PATH = "src/main/java/br/com/meli/desafiospring/files/products.json";
    private final FilePersistenceUtil<Product> filePersistence;

    public void writeFile(List<Product> input) {
        Map<Long, Product> productsMap = getProductsMap();
        input.forEach(i -> {
            if (productsMap.containsKey(i.getProductId()))
                throw new DuplicatedResourceException(String.format("Product of %d is already registered", i.getProductId()));
        });

        input.forEach(p -> {
            try {
                filePersistence.writeToFile(p, FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Product> findAll() {
        return filePersistence.readObjects(FILE_PATH, Product.class);
    }

    public boolean updateProduct(Product oldProduct, Product newProduct) {
        return filePersistence.updateElement(FILE_PATH, oldProduct, newProduct, Product.class);
    }

    public Map<Long, Product> getProductsMap() {
        return findAll().stream()
                .collect(Collectors.toMap(Product::getProductId, Function.identity(), (r1, r2) -> r2));
    }

}

