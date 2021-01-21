package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    private BufferedReader bufferedReader;

    public CSVReader(String filePath) {
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean authentication(String username, String pwd){
        try {
            String row;
            while((row = bufferedReader.readLine()) != null) {
                String[] data = row.split(",");
                if(data.length >= 2 && data[0].equals(username) && data[1].equals(pwd))
                    return true;
            }
            bufferedReader.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return false;
        }
    }
}
