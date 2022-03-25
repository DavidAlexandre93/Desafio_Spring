package br.com.meli.desafiospring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

        private String clienteId;
        private String name;
        private String cpf;
        private String state;

        @Override
        public String toString() {
            return String.valueOf(clienteId).concat(";").concat(name).concat(";")
                    .concat(cpf).concat(";").concat(state).concat(";");
        }




}
