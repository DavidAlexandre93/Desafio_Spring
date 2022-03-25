package br.com.meli.desafiospring.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Component
@AllArgsConstructor
public class FilePersistenceJson<T> implements FilePersistenceUtil<T> {

    private final ObjectMapper objectMapper;

    @Override
    public void writeToFile(T value, String jsonFilePath) throws IOException {
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
    public List<T> readObjects(String filePath, Class<T> type) {
        try {
            CollectionType objectType = TypeFactory.defaultInstance().constructCollectionType(List.class, type);
            return objectMapper.readValue(Paths.get(filePath).toFile(), objectType);
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Override
    public boolean updateElement(String jsonFilePath, T oldElement, T newElement, Class<T> type) {
        File filePath = Paths.get(jsonFilePath).toFile();

        if (!filePath.isFile()) {
            throw new IllegalArgumentException("Path needs to be of a file not a directory");
        }

        try {
            ArrayNode arrayNode = (ArrayNode) readArrayOrCreateNew(filePath);
            Iterator<JsonNode> arrayNodeIterator = arrayNode.iterator();

            while (arrayNodeIterator.hasNext()) {
                JsonNode node = arrayNodeIterator.next();
                T fetchedObject = objectMapper.treeToValue(node, type);

                if (fetchedObject.equals(oldElement)) {
                    arrayNodeIterator.remove();
                    arrayNode.addPOJO(newElement);
                    break;
                }
            }

            objectMapper.writeValue(filePath, arrayNode);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private JsonNode readArrayOrCreateNew(File jsonFile) throws IOException {
        if (jsonFile.exists() && jsonFile.length() > 0) {
            return objectMapper.readTree(jsonFile);
        }
        return objectMapper.createArrayNode();
    }

}
