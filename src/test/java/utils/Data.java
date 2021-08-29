package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Data {

    //Funcao para ler arquivo JSon
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Funcao para ler arquivo CSV
    public List<String[]> lerCSV(String caminhoCSV) throws IOException {

        CSVReaderBuilder builder = new CSVReaderBuilder(new FileReader(caminhoCSV));
        CSVReader reader = builder.withSkipLines(0).build();

        List<String[]> users = reader.readAll();
        return users;
    }
}
