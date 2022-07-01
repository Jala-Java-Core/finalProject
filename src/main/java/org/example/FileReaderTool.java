package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileReaderTool {

    private JSONParser jsonParser;

    public FileReaderTool() {
        jsonParser = new JSONParser();
    }

    public JSONArray readJsonArrayFile (String filePath) {
        JSONArray jsonArrayObject = null;
        try (FileReader reader = new FileReader(filePath))
        {
            Object obj = jsonParser.parse(reader);
            jsonArrayObject = (JSONArray) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonArrayObject;
    }
}
