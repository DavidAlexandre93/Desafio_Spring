package br.com.meli.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Author: Bruno Mendes
 * Method: Entidade do cliente
 * Description: cria uma entidade para definir as propriedades do cliente
 */

public class Client {
    private Integer id;
    private String name;
    private String cpf;
    private String state;


}
