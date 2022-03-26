package br.com.meli.desafiospring.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
/**
 * @Metodo: Escrever no filepath e Atualizar
 */
public interface FilePersistenceUtil<T> {
    void writeToFile(T object, String filePath) throws IOException;
    List<T> readObjects(String filePath, Class<T> javaType);
    boolean updateElement(String filePath, T oldElement, T newElement, Class<T> javaType);
}
