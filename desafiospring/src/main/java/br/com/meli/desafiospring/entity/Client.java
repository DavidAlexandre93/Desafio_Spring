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
}
