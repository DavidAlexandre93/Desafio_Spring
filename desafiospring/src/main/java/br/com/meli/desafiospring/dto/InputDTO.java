package br.com.meli.desafiospring.dto;

import br.com.meli.desafiospring.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDTO {
    private List<Product> articles;
}
