package com.fitstir.fitstirapp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.EdamamAPI_FoodDatabaseParser;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.FoodResponse;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.CategoryOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.HealthOptions;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import okhttp3.Headers;
import okhttp3.ResponseBody;

public class EdamamAPIFoodDatabaseParserRequestTests {
    private final String FOODRESPONSE = "{\"text\":\"2 large eggs\",\"parsed\":[{\"food\":{\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\",\"label\":\"Egg\",\"knownAs\":\"egg\",\"nutrients\":{\"ENERC_KCAL\":143.0,\"PROCNT\":12.6,\"FAT\":9.51,\"CHOCDF\":0.72,\"FIBTG\":0.0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\",\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"},\"quantity\":2.0,\"measure\":{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":43.0,\"qualified\":[{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\",\"label\":\"large\"}],\"weight\":50.0}]}}],\"hints\":[{\"food\":{\"foodId\":\"food_bhpradua77pk16aipcvzeayg732r\",\"label\":\"Egg\",\"knownAs\":\"egg\",\"nutrients\":{\"ENERC_KCAL\":143.0,\"PROCNT\":12.6,\"FAT\":9.51,\"CHOCDF\":0.72,\"FIBTG\":0.0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\",\"image\":\"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":43.0,\"qualified\":[{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_extra_large\",\"label\":\"extra large\"}],\"weight\":56.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_large\",\"label\":\"large\"}],\"weight\":50.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_small\",\"label\":\"small\"}],\"weight\":38.0},{\"qualifiers\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Qualifier_medium\",\"label\":\"medium\"}],\"weight\":44.0}]},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_jumbo\",\"label\":\"Jumbo\",\"weight\":63.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_cup\",\"label\":\"Cup\",\"weight\":243.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_liter\",\"label\":\"Liter\",\"weight\":1027.10093956848}]},{\"food\":{\"foodId\":\"food_a4vwb7xagt9toib6ax2rjao2p1dv\",\"label\":\"2 Large Eggs\",\"knownAs\":\"2 Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":184.18367346938777,\"PROCNT\":11.224489795918368,\"FAT\":6.63265306122449,\"CHOCDF\":21.93877551020408,\"FIBTG\":2.5510204081632653},\"brand\":\"Mimi's Cafe\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":196.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":196.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bv61ls3bw0eon1azwl78aavqhmsp\",\"label\":\"Ener-g Egg Replacer for 2 Large Eggs\",\"knownAs\":\"egg substitute\",\"nutrients\":{\"ENERC_KCAL\":375.0,\"PROCNT\":0.0,\"FAT\":0.0,\"CHOCDF\":100.0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\",\"weight\":4.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_replacer\",\"label\":\"Replacer\",\"weight\":4.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_teaspoon\",\"label\":\"Teaspoon\",\"weight\":2.6708333334688},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_tablespoon\",\"label\":\"Tablespoon\",\"weight\":8.01249999986453},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_cup\",\"label\":\"Cup\",\"weight\":128.2}]},{\"food\":{\"foodId\":\"food_abeyqhpa8umqssbcyh1i8btnwes8\",\"label\":\"2 Large Fresh Eggs\",\"knownAs\":\"2 Large Fresh Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0,\"FIBTG\":0.0},\"brand\":\"Perkins\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":100.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":100.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_buv4ac0an6ud3cbfs1agbbhh25u6\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"PENNY SMART\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"EGGS\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bh53an9br1otxqaorzqh8axt9gtf\",\"label\":\"Large Eggs\",\"knownAs\":\"Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"The Kroger Co.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a0a42e9bdmcsiablxszuvabooien\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Safeway Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"CONTAINS: EGGS.\",\"image\":\"https://www.edamam.com/food-img/b9c/b9c26bcf7d3597633e89160f7a0ef111.jpg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a313tv1bd8hg6nbhx3srgb8b90tx\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Meijer, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"FRESH SHELL EGGS.\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}],\"servingsPerContainer\":8.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":400.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_biyyop6b6oj2f6and9n1lbk0y7y1\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"First Light Egg Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a8p11iobmkyt3da00u40lbr94c85\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"First Light Egg Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_am5hafxaiorcacamqspnka6gcky5\",\"label\":\"Large Eggs\",\"knownAs\":\"LARGE EGGS\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Supervalu\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"image\":\"https://www.edamam.com/food-img/c7e/c7e96e053a544b8471ec5f71c2b0b2a4.jpg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":18.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":900.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a4mvulkbopk8pebmheve0at0ip76\",\"label\":\"2 Large Grade AA Eggs\",\"knownAs\":\"2 Large Grade AA Eggs\",\"nutrients\":{\"ENERC_KCAL\":164.2512077294686,\"PROCNT\":11.594202898550725,\"FAT\":12.56038647342995,\"CHOCDF\":1.932367149758454,\"FIBTG\":0.0},\"brand\":\"Perkins\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":103.5}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":103.5},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bcnm13qasb1zlpa1xg5xqah8c6tw\",\"label\":\"Kroger Large Eggs\",\"knownAs\":\"Kroger Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Kroger\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"image\":\"https://www.edamam.com/food-img/640/640b160d91f4f06549928be395451763.png\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}],\"servingsPerContainer\":12.0},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\",\"weight\":600.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a55wfyfat1oc0iap2e3f2bkerxns\",\"label\":\"Two Large Eggs\",\"knownAs\":\"Two Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.54054054054055,\"PROCNT\":12.612612612612613,\"FAT\":9.00900900900901,\"CHOCDF\":0.9009009009009009,\"FIBTG\":0.0},\"brand\":\"Mimi's Cafe\",\"category\":\"Fast foods\",\"categoryLabel\":\"meal\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":111.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":111.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_a7ts6avbb4va76azubk4bazulnr1\",\"label\":\"Extra Large Eggs\",\"knownAs\":\"Extra Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":142.85714285714286,\"PROCNT\":12.5,\"FAT\":8.928571428571429,\"CHOCDF\":0.0},\"brand\":\"Gravel Ridge Farms\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":56.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_ah17t5cbq26x6dbdyn00kbo0ma45\",\"label\":\"Sunshine, Large Eggs\",\"knownAs\":\"Sunshine, Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Sunshine\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_apwdiw9a7zp87bbtd1au7a5buy4l\",\"label\":\"12 Large Eggs\",\"knownAs\":\"12 Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0,\"FIBTG\":0.0},\"brand\":\"Supervalu, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_bln3k77b5543jnbflszbcbv0o1q3\",\"label\":\"Grade a Large Eggs\",\"knownAs\":\"Grade A Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"Bruno's Supermarkets, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Large Eggs\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_b5wh7a2baoenlrb317e3mauu19n3\",\"label\":\"Grade a Large Eggs\",\"knownAs\":\"Grade A Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":9.0,\"CHOCDF\":2.0},\"brand\":\"Bruno's Supermarkets, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"quantity\":1.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_egg\",\"label\":\"Egg\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]},{\"food\":{\"foodId\":\"food_b2t2hi7bo6byz9ar94x4jbtkcq7m\",\"label\":\"Kroger Large Eggs\",\"knownAs\":\"Kroger Large Eggs\",\"nutrients\":{\"ENERC_KCAL\":140.0,\"PROCNT\":12.0,\"FAT\":10.0,\"CHOCDF\":0.0},\"brand\":\"Kroger\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Egg\",\"image\":\"https://www.edamam.com/food-img/640/640b160d91f4f06549928be395451763.png\",\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":50.0}]},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\",\"weight\":50.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"weight\":1.0},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"weight\":28.349523125},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\",\"weight\":453.59237},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\",\"weight\":1000.0}]}],\"_links\":{\"next\":{\"title\":\"Next page\",\"href\":\"https://api.edamam.com/api/food-database/v2/parser?session=40&app_id=ab4a541a&app_key=5435c61858cc52c36783f9f36fc2f1f2&ingr=2+large+eggs&nutrition-type=cooking\"}}}";

    @Test
    public void validCodeReceived_onlyQuantityUnitIngredientAndNutritionType() {
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "", "");
        api.execute();

        int code = api.getResponseCode();

        assertEquals(200, code);
    }

    @Test
    public void validHeaderReceived_onlyQuantityUnitIngredientAndNutritionType() {
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", HealthOptions.ALCOHOL_FREE.getKey(), "", "", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", HealthOptions.CELERY_FREE.getKey(), "", "", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", HealthOptions.KETO_FRIENDLY.getKey(), "", "", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "100", "", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "10", "", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "200", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "20", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "100", "200", "");
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "", CategoryOptions.GENERIC_FOODS.getKey());
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "", CategoryOptions.FAST_FOODS.getKey());
        api.execute();

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
        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", " ", "", "", CategoryOptions.FAST_FOODS.getKey());
        api.execute();
    }

    @Test
    public void getClassObjectFromAPI() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FoodResponse expected = objectMapper.readValue(FOODRESPONSE, FoodResponse.class);

        EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser("2", "large", "eggs", "cooking", "", "", "", "");
        api.execute();

        FoodResponse actual = api.getFoodResponse();

        assertEquals(expected, actual);
    }
}
