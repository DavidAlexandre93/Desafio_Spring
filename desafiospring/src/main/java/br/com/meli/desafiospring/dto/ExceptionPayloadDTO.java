package br.com.meli.desafiospring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * @Metodo: Excecao para formatos no payload
 * @Description: Realizar a validacao de formatos no payload com jsonformat
 */
public class ExceptionPayloadDTO {

    private int statusCode;
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Map<String, String> fieldToMessageMap;
}
