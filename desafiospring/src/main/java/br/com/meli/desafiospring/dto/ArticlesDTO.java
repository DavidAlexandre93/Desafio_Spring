package br.com.meli.desafiospring.dto;

import br.com.meli.desafiospring.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesDTO {

    @NotNull(message = "Please, provide the product's list")
    /**
     * @Metodo: Cadastrar produtos em Lista
     * @Description: Realizar validacao no body para cadastrar novos produtos em lista
     */
    private List<Product> articles;
}
