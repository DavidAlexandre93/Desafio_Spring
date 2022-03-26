package br.com.meli.desafiospring.dto;

import br.com.meli.desafiospring.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Integer id;
    private String name;
    private String cpf;
    private String state;

    /**
     * Author: David Alexandre
     * Method: Coletor para retornar todos os dados da lista Cliente
     * Description: Buscar em entity todos os dados mapeados Cliente
     * @param cliente
     * @return
     */
    public static List<ClienteDTO> converte(List<Client> cliente) {
        return cliente.stream().map(c -> new ClienteDTO(c.getId(), c.getName(), c.getCpf(), c.getState())).collect(Collectors.toList());
    }
}
