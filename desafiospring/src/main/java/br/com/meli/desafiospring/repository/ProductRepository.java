package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.List;
import br.com.meli.desafiospring.exception.DuplicatedResourceException;
import br.com.meli.desafiospring.util.FilePersistenceUtil;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ProductRepository {

    private static final String FILE_PATH = "src/main/java/br/com/meli/desafiospring/files/products.json";
    private final FilePersistenceUtil<Product> filePersistence;

    /**
     * Author: Bruno Mendes
     * Method: Metodo para salvar novos produtos
     * Description: Recebe uma lista de novos produtos e envia para a função do arquivo de salvar
     *
     * @param input lista de produtos a serem cadastrados no repositorio;
     */

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

    /**
     * Author:
     * Method:
     * Description: permite recuperar uma lista com todos os objetos produto presentes no repositorio;
     *
     * @return lista com todos os objetos presentes produto no repositorio;
     */
    public List<Product> findAll() {
        return filePersistence.readObjects(FILE_PATH, Product.class);
    }

    /**
     * Author:
     * Method:
     * Description: permite realizar a substituição de um produto por outro;
     *
     * @param oldProduct produto a ser retirado do repositorio;
     * @param newProduct produto a ser adicionado no lugar do produto retirado;
     *
     * @return confirmação do sucesso ou não da operação de atualização;
     */
    public boolean updateProduct(Product oldProduct, Product newProduct) {
        return filePersistence.updateElement(FILE_PATH, oldProduct, newProduct, Product.class);
    }


    /**
     * Author:
     * Method:
     * Description: permite a criação de um Map contendo os produtos cadastrados na aplicação;
     *
     * @return um Map contendo todos os produtos cadastrados na aplicação, usando o atributo ProductId como chave;
     */
    public Map<Long, Product> getProductsMap() {
        return findAll().stream()
                .collect(Collectors.toMap(Product::getProductId, Function.identity(), (r1, r2) -> r2));
    }

}

