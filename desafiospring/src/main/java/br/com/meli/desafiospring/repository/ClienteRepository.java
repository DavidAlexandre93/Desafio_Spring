package br.com.meli.desafiospring.repository;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.util.Util;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository<T> {


    private Util<Client> Util;
    private static final String CLIENTES_TXT = "clientes.txt";

    /**
     * Author: David Alexandre
     * Method: Realizar leitura do txt
     * Description: Realizar leitura do arquivo txt e organizando os campos dentro do array
     * @return
     */
    public List<Client> lista() {
            Util = new Util<Client>();
            List<String> registros = Util.leitura(CLIENTES_TXT);
            List<Client> result = new ArrayList<Client>();
            registros.forEach(r->{
                String[] campos = r.split(";");
                Client cliente = new Client((campos[0]), campos[1], campos[2], campos[3]);
                result.add(cliente);
            });
            return result;
        }
}
