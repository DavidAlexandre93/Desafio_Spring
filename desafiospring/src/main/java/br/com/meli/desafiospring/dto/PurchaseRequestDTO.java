package br.com.meli.desafiospring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequestDTO {
    private List<ProductPurchaseRequestDTO> articlesPurchaseRequest;
}
