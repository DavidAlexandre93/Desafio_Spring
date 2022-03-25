package br.com.meli.desafiospring.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Data
public class ClientInputValidationDTO {

    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Length(min = 3, message = "Minimum name length is 3 characters")
    private String name;

    @NotBlank(message = "cpf is mandatory")
    @CPF(message = "Pleas insert a valid CPF")
    private String cpf;

    @NotBlank(message = "state is mandatory")
    @Length(min = 4, message = "Minimum state length is 4 characters")
    private String state;
}
