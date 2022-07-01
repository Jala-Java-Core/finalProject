package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFileReaderTool {
    FileReaderTool fileReaderTool;

    @BeforeEach
    public void setup() {
        fileReaderTool = new FileReaderTool();
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void readJsonFile() {
        JSONArray arrayObject = fileReaderTool.readJsonArrayFile("src/test/resources/jsonFile.json");
        JSONObject jsonObject = (JSONObject) arrayObject.get(0);

        assertEquals(arrayObject.size(), 1);
        assertEquals(jsonObject.get("nameFile"), "jsonFile");
        assertEquals(jsonObject.get("typeFile"), "json");
    }
}
