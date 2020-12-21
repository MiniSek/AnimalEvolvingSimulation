package evolution;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class World {
    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("input_parameters\\parameters.json")) {
            Object obj = jsonParser.parse(reader);

            JSONArray parametersObjectsList = (JSONArray)obj;

            for(Object parametersObject : parametersObjectsList) {
                JSONObject parameters = (JSONObject)((JSONObject)parametersObject).get("parameters");
                String[] data = {(String)parameters.get("width"), (String)parameters.get("height"), (String)parameters.get("jungle ratio"),
                        (String)parameters.get("number of animals at start"), (String)parameters.get("animals start energy"),
                        (String)parameters.get("animals move energy"), (String)parameters.get("grass energy")};
                if(DataValidator.validateInputData(data))
                    new ParametersInformWindow(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }
}

