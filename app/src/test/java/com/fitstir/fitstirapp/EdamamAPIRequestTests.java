package com.fitstir.fitstirapp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.EdamamAPI_FoodSearch;
import com.fitstir.fitstirapp.ui.utility.enums.CategoryOptions;
import com.fitstir.fitstirapp.ui.utility.enums.HealthOptions;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import okhttp3.Headers;
import okhttp3.ResponseBody;

public class EdamamAPIRequestTests {
    @Test
    public void validCodeReceived_onlyQuantityUnitIngredientAndNutritionType() {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "", "", "");

        int code = api.getResponseCode();

        assertEquals(200, code);
    }

    @Test
    public void validHeaderReceived_onlyQuantityUnitIngredientAndNutritionType() {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "", "", "");

        Headers header = api.getResponseHeader();
        ArrayList<String> actual = new ArrayList<String>() {{
            add(header.get("server"));
            add(header.get("content-type"));
            add(header.get("cache-control"));
            add(header.get("vary"));
            add(header.get("strict-transport-security"));
        }};
        ArrayList<String> expected = new ArrayList<String>() {{
            add("openresty");
            add("application/json;charset=UTF-8");
            add("private");
            add("accept-encoding");
            add("max-age=15552001");
        }};

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void validBodyReceived_onlyQuantityUnitIngredientAndNutritionType() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "", "", "");

        ResponseBody body = api.getResponseBody();
        byte[] bytes = body.bytes();
        String actual = new String(bytes, StandardCharsets.UTF_8);

        String expected = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                "\"label\":\"Whole\"," +
                "\"weight\":43.0," +
                "\"qualified\":[" +
                "{" +
                "\"qualifiers\":[" +
                "{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                "\"label\":\"large\"" +
                "}" +
                "]," +
                "\"weight\":50.0" +
                "}" +
                "]" +
                "}" +
                "}" +
                "],";

        assertEquals(expected, actual.substring(0, expected.length()));
    }

    @Test
    public void validStringReceivedFromStringMethod_onlyQuantityUnitIngredientAndNutritionType() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "", "", "");

        String actual = api.getResponseAsString();
        String expected = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                "\"label\":\"Whole\"," +
                "\"weight\":43.0," +
                "\"qualified\":[" +
                "{" +
                "\"qualifiers\":[" +
                "{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                "\"label\":\"large\"" +
                "}" +
                "]," +
                "\"weight\":50.0" +
                "}" +
                "]" +
                "}" +
                "}" +
                "],";

        assertEquals(expected, actual.substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_HEALTH_ALCOHOLFREE() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", HealthOptions.ALCOHOL_FREE.getKey(), "", "", "");

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                "\"label\":\"Whole\"," +
                "\"weight\":43.0," +
                "\"qualified\":[" +
                "{" +
                "\"qualifiers\":[" +
                "{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                "\"label\":\"large\"" +
                "}" +
                "]," +
                "\"weight\":50.0" +
                "}" +
                "]" +
                "}" +
                "}" +
                "],";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_HEALTH_CELERYFREE() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", HealthOptions.CELERY_FREE.getKey(), "", "", "");

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                "\"label\":\"Whole\"," +
                "\"weight\":43.0," +
                "\"qualified\":[" +
                "{" +
                "\"qualifiers\":[" +
                "{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                "\"label\":\"large\"" +
                "}" +
                "]," +
                "\"weight\":50.0" +
                "}" +
                "]" +
                "}" +
                "}" +
                "],";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_HEALTH_KETOFRIENDLY() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", HealthOptions.KETO_FRIENDLY.getKey(), "", "", "");

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[]," +
                "\"hints\":" +
                "[" +
                "{" +
                "\"food\":" +
                "{" +
                "\"foodId\":\"food_a9jx75mbg7qnsyb6urjvfbyco91a\"," +
                "\"label\":\"Egg Bhurji (Indian Scrambled Eggs)\"," +
                "\"knownAs\":\"Egg Bhurji (indian Scrambled Eggs)\"," +
                "\"nutrients\":" +
                "{" +
                "\"ENERC_KCAL\":139.698936419195," +
                "\"PROCNT\":5.7143781856509115," +
                "\"FAT\":10.890511591881154," +
                "\"CHOCDF\":5.327699388505787," +
                "\"FIBTG\":1.1256755045583922}," +
                "\"category\":\"Generic meals\"," +
                "\"categoryLabel\":\"meal\"," +
                "\"foodContentsLabel\":\"Eggs; oil; cumin seeds; fennel seeds; ginger; curry leaves; green chillies; onion; asafoetida; salt; turmeric; pepper; spring onion; coriander leaves\"" +
                "}," +
                "\"measures\":" +
                "[" +
                "{" +
                "\"uri\":\"http:/";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_MINCALORIES_100() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "100", "", "");

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                "\"label\":\"Whole\"," +
                "\"weight\":43.0," +
                "\"qualified\":[" +
                "{" +
                "\"qualifiers\":[" +
                "{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                "\"label\":\"large\"" +
                "}" +
                "]," +
                "\"weight\":50.0" +
                "}" +
                "]" +
                "}" +
                "}" +
                "],";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_MINCALORIES_10() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "10", "", "");

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                "\"label\":\"Whole\"," +
                "\"weight\":43.0," +
                "\"qualified\":[" +
                "{" +
                "\"qualifiers\":[" +
                "{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                "\"label\":\"large\"" +
                "}" +
                "]," +
                "\"weight\":50.0" +
                "}" +
                "]" +
                "}" +
                "}" +
                "],";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_MAXCALORIES_200() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "", "200", "");

        String expectedBody =
                "{" +
                    "\"text\":\"2 large eggs\"," +
                    "\"parsed\":" +
                    "[" +
                        "{" +
                            "\"food\":" +
                            "{" +
                                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                                "\"label\":\"Egg\"," +
                                "\"knownAs\":\"egg\"," +
                                "\"nutrients\":" +
                                "{" +
                                    "\"ENERC_KCAL\":143.0," +
                                    "\"PROCNT\":12.6," +
                                    "\"FAT\":9.51," +
                                    "\"CHOCDF\":0.72," +
                                    "\"FIBTG\":0.0" +
                                "}," +
                                "\"category\":\"Generic foods\"," +
                                "\"categoryLabel\":\"food\"," +
                                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                            "}," +
                            "\"quantity\":2.0," +
                            "\"measure\":" +
                            "{" +
                                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                                "\"label\":\"Whole\"," +
                                "\"weight\":43.0," +
                                "\"qualified\":" +
                                "[" +
                                    "{" +
                                        "\"qualifiers\":" +
                                        "[" +
                                            "{" +
                                                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                                                "\"label\":\"large\"" +
                                            "}" +
                                        "]," +
                                        "\"weight\":50.0" +
                                    "}" +
                                "]" +
                            "}" +
                        "}" +
                    "],";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_MAXCALORIES_20() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "", "20", "");

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[]," +
                "\"hints\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bwcbloiblfxzwtbz782vwbuiwgxh\"," +
                "\"label\":\"MacChiato w/ 2% Milk, Large\"," +
                "\"knownAs\":\"Macchiato w/ 2% Milk, Large\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":11.271340900614334," +
                "\"PROCNT\":1.69070113509215," +
                "\"FAT\":0.5635670450307166," +
                "\"CHOCDF\":0.5635670450307166," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"brand\":\"Caribou Coffee\"," +
                "\"category\":\"Fast foods\"," +
                "\"categoryLabel\":\"meal\"";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_MINCALORIES_100_MAXCALORIES_200() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "100", "200", "");

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                "\"label\":\"Whole\"," +
                "\"weight\":43.0," +
                "\"qualified\":[" +
                "{" +
                "\"qualifiers\":[" +
                "{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                "\"label\":\"large\"" +
                "}" +
                "]," +
                "\"weight\":50.0" +
                "}" +
                "]" +
                "}" +
                "}" +
                "],";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_CATEGORY_GENERICFOODS() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "", "", CategoryOptions.GENERIC_FOODS.getKey());

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
                "\"label\":\"Egg\"," +
                "\"knownAs\":\"egg\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":143.0," +
                "\"PROCNT\":12.6," +
                "\"FAT\":9.51," +
                "\"CHOCDF\":0.72," +
                "\"FIBTG\":0.0" +
                "}," +
                "\"category\":\"Generic foods\"," +
                "\"categoryLabel\":\"food\"," +
                "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
                "}," +
                "\"quantity\":2.0," +
                "\"measure\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
                "\"label\":\"Whole\"," +
                "\"weight\":43.0," +
                "\"qualified\":[" +
                "{" +
                "\"qualifiers\":[" +
                "{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
                "\"label\":\"large\"" +
                "}" +
                "]," +
                "\"weight\":50.0" +
                "}" +
                "]" +
                "}" +
                "}" +
                "],";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test
    public void validResponses_onlyQuantityUnitIngredientAndNutritionType_CATEGORY_FASTFOODS() throws IOException {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", "", "", "", CategoryOptions.FAST_FOODS.getKey());

        String expectedBody = "{" +
                "\"text\":\"2 large eggs\"," +
                "\"parsed\":[]," +
                "\"hints\":[" +
                "{" +
                "\"food\":{" +
                "\"foodId\":\"food_a4vwb7xagt9toib6ax2rjao2p1dv\"," +
                "\"label\":\"2 Large Eggs\"," +
                "\"knownAs\":\"2 Large Eggs\"," +
                "\"nutrients\":{" +
                "\"ENERC_KCAL\":184.18367346938777," +
                "\"PROCNT\":11.224489795918368," +
                "\"FAT\":6.63265306122449," +
                "\"CHOCDF\":21.93877551020408," +
                "\"FIBTG\":2.5510204081632653" +
                "}," +
                "\"brand\":\"Mimi's Cafe\"," +
                "\"category\":\"Fast foods\"," +
                "\"categoryLabel\":\"meal\"";


        assertEquals(200, api.getResponseCode());
        assertEquals(expectedBody, api.getResponseAsString().substring(0, expectedBody.length()));
    }

    @Test(expected = RuntimeException.class)
    public void RuntimeException_spaceForField() {
        EdamamAPI_FoodSearch api = new EdamamAPI_FoodSearch(2, "large", "eggs", "cooking", " ", "", "", CategoryOptions.FAST_FOODS.getKey());
    }
}
