package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject nameJSON = sandwichJSON.getJSONObject("name");
            String mainName = nameJSON.optString("mainName");

            String placeOfOrigin = sandwichJSON.optString("placeOfOrigin");
            String description = sandwichJSON.optString("description");
            String image = sandwichJSON.optString("image");

            JSONArray alsoKnownAsJSONArray = nameJSON.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = convertJSONArrayToListOfStrings(alsoKnownAsJSONArray);

            JSONArray ingredientsJSONArray = sandwichJSON.getJSONArray("ingredients");
            List<String> ingredientsList = convertJSONArrayToListOfStrings(ingredientsJSONArray);

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> convertJSONArrayToListOfStrings(JSONArray jsonArray) throws JSONException {
        List<String> listOfStrings = new ArrayList<>();

        for(int index = 0; index < jsonArray.length(); index++) {
            listOfStrings.add(jsonArray.getString(index));
        }

        return listOfStrings;
    }
}
