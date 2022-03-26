package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.util.FilePersistenceJson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Repositorio referente aos clientes cadastrados na aplicação;
 */

@Repository
@AllArgsConstructor
public class ClientRepository {

    private final FilePersistenceJson<Client> filePersistence;

    private static final String filePath = "src/main/java/br/com/meli/desafiospring/files/clients.json";

    /**
     * Author: Bruno Mendes
     * Method: Metodo para salvar novos clientes
     * Description: Recebe a informação de um novo cliente envia para a função do arquivo de salvar
     *
     * @param input lista de clientes a serem cadastrados no repositorio;
     */
    public void writeFile(Client input) {
        try {
            filePersistence.writeToFile(input, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Method: Busca por todos os clientes cadastrados
     * Description: permite recuperar uma lista com todos os objetos cliente presentes no repositorio;
     *
     * @return lista com todos os objetos cliente presentes no repositorio;
     */
    public List<Client> findAll() {
        return filePersistence.readObjects(filePath, Client.class);
    }
}
