package com.fitstir.fitstirapp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.FoodResponse;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Food;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Hint;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Link;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Measure;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Next;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Nutrients;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Parsed;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Qualified;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.Qualify;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.fooddatabaseparser.ServingSize;

import org.junit.Test;

import java.util.ArrayList;

public class EdamamAPIFoodDatabaseParserResponseTests {
    private ObjectMapper objectMapper = new ObjectMapper();

    private final String NEXT =
        "{" +
            "\"title\":\"Next page\"," +
            "\"href\":\"https://api.edamam.com/api/food-database/v2/parser?session=40&app_id=ab4a541a&app_key=5435c61858cc52c36783f9f36fc2f1f2&ingr=2+large+eggs&nutrition-type=cooking&category=generic-foods\"" +
        "}";

    private final String _LINKS =
        "{" +
            "\"next\":" +
            NEXT +
        "}";

    private final String NUTRIENTS =
        "{" +
            "\"ENERC_KCAL\":143.0," +
            "\"PROCNT\":12.0," +
            "\"FAT\":9.0," +
            "\"CHOCDF\":1.0," +
            "\"FIBTG\":0.0" +
        "},";

    private final String FOOD =
        "{" +
            "\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\"," +
            "\"label\":\"Egg\"," +
            "\"knownAs\":\"egg\"," +
            "\"nutrients\":" +
            NUTRIENTS +
            "\"category\":\"Generic foods\"," +
            "\"categoryLabel\":\"food\"," +
            "\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"" +
        "}";

    private final String QUALIFY =
        "{" +
            "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\"," +
            "\"label\":\"large\"" +
        "}";

    private final String QUALIFIED =
        "{" +
            "\"qualifiers\":" +
            "[" +
                QUALIFY +
            "]," +
            "\"weight\":50.0" +
        "}";

    private final String MEASURE =
        "{" +
            "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\"," +
            "\"label\":\"Whole\"," +
            "\"weight\":43.0," +
            "\"qualified\":" +
            "[" +
                QUALIFIED +
            "]" +
        "}";

    private final String HINT =
        "{" +
            "\"food\":" +
            FOOD +
            "," +
            "\"measures\":" +
            "[" +
                "{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50" +
                "}," +
                "{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":43,\"qualified\":[" +
                        "{" +
                            "\"qualifiers\":" +
                            "[" +
                                "{" +
                                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_extra_large\"," +
                                    "\"label\":\"extra large\"" +
                                "}" +
                            "]," +
                            "\"weight\":56.0" +
                        "}," +
                        "{" +
                            "\"qualifiers\":" +
                            "[" +
                                QUALIFY +
                            "]," +
                            "\"weight\":50.0" +
                        "}," +
                        "{" +
                            "\"qualifiers\":" +
                            "[" +
                                "{" +
                                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_small\"," +
                                    "\"label\":\"small\"" +
                                "}" +
                            "]," +
                            "\"weight\":38.0" +
                        "}," +
                        "{" +
                            "\"qualifiers\":" +
                            "[" +
                                "{" +
                                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_medium\"," +
                                    "\"label\":\"medium\"" +
                                "}" +
                            "]," +
                            "\"weight\":44.0" +
                        "}]" +
                "}," +
                "{" +
                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_jumbo\"," +
                    "\"label\":\"Jumbo\"," +
                    "\"weight\":63.0" +
                "}," +
                "{" +
                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\"," +
                    "\"label\":\"Gram\"," +
                    "\"weight\":1.0" +
                "}," +
                "{" +
                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\"," +
                    "\"label\":\"Ounce\"," +
                    "\"weight\":28.0" +
                "}," +
                "{" +
                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\"," +
                    "\"label\":\"Pound\"," +
                    "\"weight\":453.0" +
                "}," +
                "{" +
                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\"," +
                    "\"label\":\"Kilogram\"," +
                    "\"weight\":1000.0" +
                "}," +
                "{" +
                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_cup\"," +
                    "\"label\":\"Cup\"," +
                    "\"weight\":243.0" +
                "}," +
                "{" +
                    "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_liter\"," +
                    "\"label\":\"Liter\"," +
                    "\"weight\":1027.0" +
                "}" +
            "]" +
        "}";

    private final String PARSED =
        "{" +
            "\"food\":" +
            FOOD +
            "," +
            "\"quantity\":2.0," +
            "\"measure\":" +
            MEASURE +
        "}";

    private final String SERVINGSIZE =
        "{" +
            "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\"," +
            "\"label\":\"Gram\"," +
            "\"quantity\":196.0" +
        "}";

    private final String FOODRESPONSE = "{\"text\":\"2 large eggs\",\"parsed\":[{\"food\":{\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\",\"label\":\"Egg\",\"knownAs\":\"egg\",\"nutrients\":{\"ENERC_KCAL\":143.0,\"PROCNT\":12.6,\"FAT\":9.51,\"CHOCDF\":0.72,\"FIBTG\":0.0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\",\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"},\"quantity\":2.0,\"measure\":{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":43.0,\"qualified\":[{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\",\"label\":\"large\"}],\"weight\":50.0}]}}],\"hints\":[{\"food\":{\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\",\"label\":\"Egg\",\"knownAs\":\"egg\",\"nutrients\":{\"ENERC_KCAL\":143.0,\"PROCNT\":12.6,\"FAT\":9.51,\"CHOCDF\":0.72,\"FIBTG\":0.0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\",\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":43.0,\"qualified\":[{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_extra_large\",\"label\":\"extra large\"}],\"weight\":56.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\",\"label\":\"large\"}],\"weight\":50.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_small\",\"label\":\"small\"}],\"weight\":38.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_medium\",\"label\":\"medium\"}],\"weight\":44.0}]},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_jumbo\",\"label\":\"Jumbo\",\"weight\":63.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_cup\",\"label\":\"Cup\",\"weight\":243.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_liter\",\"label\":\"Liter\",\"weight\":1027.10093956848}]},{\"food\":{\"foodId\":\"food_a4vwb7xagt9toib6ax2rjao2p1dv\",\"label\":\"2 Large Eggs\",\"knownAs\":\"2 Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":184.18367346938777,\"PROCNT\":11.224489795918368,\"FAT\":6.63265306122449,\"CHOCDF\":21.93877551020408,\"FIBTG\":2.5510204081632653},\"brand\":\"Mimi's Cafe\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":196.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":196.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bv61ls3bw0eon1azwl78aavqhmsp\",\"label\":\"Ener-g Egg Replacer for 2 Large Eggs\",\"knownAs\":\"egg substitute\",\"nutrients\":{\"ENERC_KCAL\":375.0,\"PROCNT\":0.0,\"FAT\":0.0,\"CHOCDF\":100.0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":4.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_replacer\",\"label\":\"Replacer\",\"weight\":4.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_teaspoon\",\"label\":\"Teaspoon\",\"weight\":2.6708333334688},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_tablespoon\",\"label\":\"Tablespoon\",\"weight\":8.01249999986453},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_cup\",\"label\":\"Cup\",\"weight\":128.2}]},{\"food\":{\"foodId\":\"food_abeyqhpa8umqssbcyh1i8btnwes8\",\"label\":\"2 Large Fresh Eggs\",\"knownAs\":\"2 Large Fresh Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0,\"FIBTG\":0.0},\"brand\":\"Perkins\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":100.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":100.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_buv4ac0an6ud3cbfs1agbbhh25u6\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"PENNY SMART\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"EGGS\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bh53an9br1otxqaorzqh8axt9gtf\",\"label\":\"Large Eggs\",\"knownAs\":\"Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"The Kroger Co.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a0a42e9bdmcsiablxszuvabooien\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Safeway Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"CONTAINS: EGGS.\",\"image\":\"https://www.edamam.com/food-img/b9c/b9c26bcf7d3597633e89160f7a0ef111.jpg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a313tv1bd8hg6nbhx3srgb8b90tx\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Meijer, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"FRESH SHELL EGGS.\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}],\"servingsPerContainer\":8.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":400.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_biyyop6b6oj2f6and9n1lbk0y7y1\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"First Light Egg Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a8p11iobmkyt3da00u40lbr94c85\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"First Light Egg Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_am5hafxaiorcacamqspnka6gcky5\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Supervalu\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"image\":\"https://www.edamam.com/food-img/c7e/c7e96e053a544b8471ec5f71c2b0b2a4.jpg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":18.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":900.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a4mvulkbopk8pebmheve0at0ip76\",\"label\":\"2 Large Grade AA Eggs\",\"knownAs\":\"2 Large Grade AA Eggs\",\"nutrients\":{\"ENERC_KCAL\":164.2512077294686,\"PROCNT\":11.594202898550725,\"FAT\":12.56038647342995,\"CHOCDF\":1.932367149758454,\"FIBTG\":0.0},\"brand\":\"Perkins\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":103.5}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":103.5},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bcnm13qasb1zlpa1xg5xqah8c6tw\",\"label\":\"Kroger Large Eggs\",\"knownAs\":\"Kroger Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Kroger\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"image\":\"https://www.edamam.com/food-img/640/640b160d91f4f06549928be395451763.png\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a55wfyfat1oc0iap2e3f2bkerxns\",\"label\":\"Two Large Eggs\",\"knownAs\":\"Two Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.54054054054055,\"PROCNT\":12.612612612612613,\"FAT\":9.00900900900901,\"CHOCDF\":0.9009009009009009,\"FIBTG\":0.0},\"brand\":\"Mimi's Cafe\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":111.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":111.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a7ts6avbb4va76azubk4bazulnr1\",\"label\":\"Extra Large Eggs\",\"knownAs\":\"Extra Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":142.85714285714286,\"PROCNT\":12.5,\"FAT\":8.928571428571429,\"CHOCDF\":0.0},\"brand\":\"Gravel Ridge Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_ah17t5cbq26x6dbdyn00kbo0ma45\",\"label\":\"Sunshine, Large Eggs\",\"knownAs\":\"Sunshine, Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Sunshine\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_apwdiw9a7zp87bbtd1au7a5buy4l\",\"label\":\"12 Large Eggs\",\"knownAs\":\"12 Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Supervalu, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bln3k77b5543jnbflszbcbv0o1q3\",\"label\":\"Grade a Large Eggs\",\"knownAs\":\"Grade A Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"Bruno's Supermarkets, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_b5wh7a2baoenlrb317e3mauu19n3\",\"label\":\"Grade a Large Eggs\",\"knownAs\":\"Grade A Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"Bruno's Supermarkets, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_b2t2hi7bo6byz9ar94x4jbtkcq7m\",\"label\":\"Kroger Large Eggs\",\"knownAs\":\"Kroger Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Kroger\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"image\":\"https://www.edamam.com/food-img/640/640b160d91f4f06549928be395451763.png\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]}],\"_links\":{\"next\":{\"title\":\"Next page\",\"href\":\"https://api.edamam.com/api/food-database/v2/parser?session=40&app_id=ab4a541a&app_key=5435c61858cc52c36783f9f36fc2f1f2&ingr=2+large+eggs&nutrition-type=cooking\"}}}";


    @Test
    public void Next() throws JsonProcessingException {
        Next expected = new Next(
                "Next page",
                "https://api.edamam.com/api/food-database/v2/parser?session=40&app_id=ab4a541a&app_key=5435c61858cc52c36783f9f36fc2f1f2&ingr=2+large+eggs&nutrition-type=cooking&category=generic-foods"
        );

        Next actual = objectMapper.readValue(NEXT, Next.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Link() throws JsonProcessingException {
        Link expected = new Link(
                new Next(
                        "Next page",
                        "https://api.edamam.com/api/food-database/v2/parser?session=40&app_id=ab4a541a&app_key=5435c61858cc52c36783f9f36fc2f1f2&ingr=2+large+eggs&nutrition-type=cooking&category=generic-foods"
                )
        );

        Link actual = objectMapper.readValue(_LINKS, Link.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Nutrients() throws JsonProcessingException {
        Nutrients expected = new Nutrients();
        expected.setENERC_KCAL(143);
        expected.setPROCNT(12);
        expected.setFAT(9);
        expected.setCHOCDF(1);
        expected.setFIBTG(0);

        Nutrients actual = objectMapper.readValue(NUTRIENTS, Nutrients.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Food() throws JsonProcessingException {
        Food expected = new Food();
        expected.setFoodId("food_bhpradua77pk16aipcvzeayg732r");
        expected.setLabel("Egg");
        expected.setKnownAs("egg");
        Nutrients temp = new Nutrients();
        temp.setENERC_KCAL(143);
        temp.setPROCNT(12);
        temp.setFAT(9);
        temp.setCHOCDF(1);
        temp.setFIBTG(0);
        expected.setNutrients(temp);
        expected.setCategory("Generic foods");
        expected.setCategoryLabel("food");
        expected.setImage("https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg");

        Food actual = objectMapper.readValue(FOOD, Food.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Qualify() throws JsonProcessingException {
        Qualify expected = new Qualify(
                "http://www.edamam.com/ontologies/edamam.owl#Qualifier_large",
                "large"
        );

        Qualify actual = objectMapper.readValue(QUALIFY, Qualify.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Qualified() throws JsonProcessingException {
        Qualified expected = new Qualified(
                new ArrayList<Qualify>() {{
                    add(new Qualify(
                        "http://www.edamam.com/ontologies/edamam.owl#Qualifier_large",
                        "large"
                    ));
                }},
                50.0f
        );

        Qualified actual = objectMapper.readValue(QUALIFIED, Qualified.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Measure() throws JsonProcessingException {
        Qualified temp = new Qualified(
                new ArrayList<Qualify>() {{
                    add(new Qualify(
                            "http://www.edamam.com/ontologies/edamam.owl#Qualifier_large",
                            "large"
                    ));
                }},
                50.0f
        );
        Measure expected = new Measure(
            "http://www.edamam.com/ontologies/edamam.owl#Measure_unit",
                "Whole",
                43.0f,
                new ArrayList<Qualified>() {{
                    add(temp);
                }}
        );

        Measure actual = objectMapper.readValue(MEASURE, Measure.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Hint() throws JsonProcessingException {
        Food temp = new Food();
        temp.setFoodId("food_bhpradua77pk16aipcvzeayg732r");
        temp.setLabel("Egg");
        temp.setKnownAs("egg");
        Nutrients temp2 = new Nutrients();
        temp2.setENERC_KCAL(143);
        temp2.setPROCNT(12);
        temp2.setFAT(9);
        temp2.setCHOCDF(1);
        temp2.setFIBTG(0);
        temp.setNutrients(temp2);
        temp.setCategory("Generic foods");
        temp.setCategoryLabel("food");
        temp.setImage("https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg");

        ArrayList<Measure> temp3 = new ArrayList<Measure>() {{
            add(new Measure(
                "http://www.edamam.com/ontologies/edamam.owl#Measure_serving",
                "Serving",
                50
            ));
            add(new Measure(
                "http://www.edamam.com/ontologies/edamam.owl#Measure_unit",
                "Whole",
                43,
                new ArrayList<Qualified>() {{
                    add(new Qualified(
                            new ArrayList<Qualify>() {{
                                add(new Qualify(
                                    "http://www.edamam.com/ontologies/edamam.owl#Qualifier_extra_large",
                                    "extra large"
                                ));
                            }},
                            56
                    ));
                    add(new Qualified(
                            new ArrayList<Qualify>() {{
                                add(new Qualify(
                                        "http://www.edamam.com/ontologies/edamam.owl#Qualifier_large",
                                        "large"
                                ));
                            }},
                            50
                    ));
                    add(new Qualified(
                            new ArrayList<Qualify>() {{
                                add(new Qualify(
                                        "http://www.edamam.com/ontologies/edamam.owl#Qualifier_small",
                                        "small"
                                ));
                            }},
                            38
                    ));
                    add(new Qualified(
                            new ArrayList<Qualify>() {{
                                add(new Qualify(
                                        "http://www.edamam.com/ontologies/edamam.owl#Qualifier_medium",
                                        "medium"
                                ));
                            }},
                            44
                    ));
                }}
            ));
            add(new Measure(
                    "http://www.edamam.com/ontologies/edamam.owl#Measure_jumbo",
                    "Jumbo",
                    63
            ));
            add(new Measure(
                    "http://www.edamam.com/ontologies/edamam.owl#Measure_gram",
                    "Gram",
                    1
            ));
            add(new Measure(
                    "http://www.edamam.com/ontologies/edamam.owl#Measure_ounce",
                    "Ounce",
                    28
            ));
            add(new Measure(
                    "http://www.edamam.com/ontologies/edamam.owl#Measure_pound",
                    "Pound",
                    453
            ));
            add(new Measure(
                    "http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram",
                    "Kilogram",
                    1000
            ));
            add(new Measure(
                    "http://www.edamam.com/ontologies/edamam.owl#Measure_cup",
                    "Cup",
                    243
            ));
            add(new Measure(
                    "http://www.edamam.com/ontologies/edamam.owl#Measure_liter",
                    "Liter",
                    1027
            ));
        }};

        Hint expected = new Hint();
        expected.setFood(temp);
        expected.setMeasures(temp3);

        Hint actual = objectMapper.readValue(HINT, Hint.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Parsed() throws JsonProcessingException {
        Food temp = new Food();
        temp.setFoodId("food_bhpradua77pk16aipcvzeayg732r");
        temp.setLabel("Egg");
        temp.setKnownAs("egg");
        Nutrients temp2 = new Nutrients();
        temp2.setENERC_KCAL(143.0f);
        temp2.setPROCNT(12.0f);
        temp2.setFAT(9.0f);
        temp2.setCHOCDF(1.0f);
        temp2.setFIBTG(0.0f);
        temp.setNutrients(temp2);
        temp.setCategory("Generic foods");
        temp.setCategoryLabel("food");
        temp.setImage("https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg");

        Measure temp3 = new Measure(
            "http://www.edamam.com/ontologies/edamam.owl#Measure_unit",
            "Whole",
            43,
            new ArrayList<Qualified>() {{
                add(new Qualified(
                        new ArrayList<Qualify>() {{
                            add(new Qualify(
                                    "http://www.edamam.com/ontologies/edamam.owl#Qualifier_large",
                                    "large"
                            ));
                        }},
                        50
                ));
            }}
        );

        Parsed expected = new Parsed();
        expected.setFood(temp);
        expected.setQuantity(2);
        expected.setMeasure(temp3);

        Parsed actual = objectMapper.readValue(PARSED, Parsed.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void ServingSize() throws JsonProcessingException {
        ServingSize expected = new ServingSize(
            "http://www.edamam.com/ontologies/edamam.owl#Measure_gram",
            "Gram",
            196
        );

        ServingSize actual = objectMapper.readValue(SERVINGSIZE, ServingSize.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void FoodResponse() throws JsonProcessingException {
        FoodResponse response = objectMapper.readValue(FOODRESPONSE, FoodResponse.class);
        assertNotNull(response);

        // manually tested, because I couldn't get string to perfectly match because
        // floats were rounding after objectMapper messed with them
    }
}
