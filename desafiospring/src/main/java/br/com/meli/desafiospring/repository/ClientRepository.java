package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.util.FilePersistenceJson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@AllArgsConstructor
public class ClientRepository {

    private final FilePersistenceJson<Client> filePersistence;

    private static final String filePath = "src/main/java/br/com/meli/desafiospring/files/clients.json";

    public void writeFile(Client input) {
        try {
            filePersistence.writeToFile(input, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Client> findAll() {
        return filePersistence.readObjects(filePath, Client.class);
    }
}
