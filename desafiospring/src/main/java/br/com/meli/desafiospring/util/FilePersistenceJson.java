package br.com.meli.desafiospring.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
@AllArgsConstructor
public class FilePersistenceJson<T> implements FilePersistenceUtil<T> {

    private final ObjectMapper objectMapper;

    @Override
    public void writeToFile(Object value, String jsonFilePath) throws IOException {
        File jsonFile = Paths.get(jsonFilePath).toFile();

        if (jsonFile.isDirectory()) {
            throw new IllegalArgumentException("Path needs to be of a file not a directory");
        }

        if (!jsonFile.exists()) {
            jsonFile.createNewFile();
        }

        JsonNode node = readArrayOrCreateNew(jsonFile);
        if (node.isArray()) {
            ArrayNode array = (ArrayNode) node;
            array.addPOJO(value);
        } else {
            ArrayNode rootArray = objectMapper.createArrayNode();
            rootArray.add(node);
            rootArray.addPOJO(value);
            node = rootArray;
        }
        objectMapper.writeValue(jsonFile, node);
    }

    @Override
    public List<T> readObjects(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(Paths.get(filePath).toFile(), TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    private JsonNode readArrayOrCreateNew(File jsonFile) throws IOException {
        if (jsonFile.exists() && jsonFile.length() > 0) {
            return objectMapper.readTree(jsonFile);
        }
        return objectMapper.createArrayNode();
    }

}
