package br.com.meli.desafiospring.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface FilePersistenceUtil<T> {
    void writeToFile(T object, String filePath) throws IOException;

    List<T> readObjects(String filePath, Class<T> javaType);
}
