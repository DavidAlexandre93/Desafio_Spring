package br.com.meli.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Integer id;
    private String name;
    private String cpf;
    private String state;

    /**
     * Author: David Alexandre
     * Method: Entity com os dados Cliente
     * Description: Concatenar os dados Entity com ";", retornando valueof String de todos
     * @return
     */

   /* @Override
    public String toString() {
        return String.valueOf(id).concat(";").concat(name).concat(";")
                .concat(cpf).concat(";").concat(state).concat(";");
    }*/
}
