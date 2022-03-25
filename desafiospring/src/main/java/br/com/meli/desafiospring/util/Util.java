package br.com.meli.desafiospring.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util<T> {

    /**
     * Author: David Alexandre
     * Method: realizar a leitura do arquivo
     * Description: Realizar leitura do arquivo passado em repository scanneando item por item
     * @param arquivo
     * @return
     */
    public static List<String> leitura(String arquivo){
        Scanner scanner;
        List<String> result = new ArrayList<String>();
        try {
            scanner = new Scanner(new FileInputStream(arquivo));
            while(scanner.hasNext()) {
                result.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return result;
    }
}
