package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.util.FilePersistenceJson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.List;

@Repository
@AllArgsConstructor
public class ClienteRepository<T> {


    private final FilePersistenceJson<Client> filePersistence;
    private static final String filePath = "src/main/java/br/com/meli/desafiospring/files/client.json";

    /**
     * Author: David Alexandre
     * Method: Realizar leitura do json
     * Description: Realizar leitura do arquivo json e organizando os campos dentro do array
     * @return
     */
    public void writeFile(List<Client> input) {
        input.stream().forEach(c -> {
            try {
                filePersistence.writeToFile(c, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Client> findAll(){
        return filePersistence.readObjects(filePath,Client.class);
    }
}
