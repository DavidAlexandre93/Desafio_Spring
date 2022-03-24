package br.com.meli.desafiospring.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util<T> {


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
