package com.example.chatappserverside.Background;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;



@Service
public class ConfiqRead {

    String filePath = "/home/coral/ScalaServerConfiq.txt";
    Map<String, String> configMap = new HashMap<>();

    public ConfiqRead instance;

    public ConfiqRead() {
        loadConfiqServerFile();
    }

    public ConfiqRead getInstance() {
        if (instance == null) {
            instance = new ConfiqRead();
        }

        return instance;

    }

    public void loadConfiqServerFile() {
        try {

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("#") || line.trim().isEmpty()) {
                        continue;

                    }

                    String[] keyValue = line.split("=", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String Value = keyValue[1].trim();
                        configMap.put(key, Value);

                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR UNABLE TO FORMATE " + e.getMessage());

            }

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("ERROR ARE FOUND IN READ CONFIQ FILE" + e.getMessage());
        }
        System.out.println("============== THE SERVER FILE LOAD SUCCESSFULLY =================");

        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public String getValue(String key) {
        return configMap.get(key);
    }


    public Map<String, String> getAllConfigurations() {
        System.out.println("============== THE SERVER VALUE ID:" + configMap);
        return configMap;
    }

}
