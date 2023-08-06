package com.fitstir.fitstirapp;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2.EdamamAPI_RecipesV2;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2.RecipeResponse;
import com.fitstir.fitstirapp.ui.utility.enums.CuisineType;
import com.fitstir.fitstirapp.ui.utility.enums.DietOptions;
import com.fitstir.fitstirapp.ui.utility.enums.DishType;
import com.fitstir.fitstirapp.ui.utility.enums.HealthOptions;
import com.fitstir.fitstirapp.ui.utility.enums.MealType;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.ResponseBody;

public class EdamamAPIRecipesV2RequestTests {
    private String RECIPERESPONSE = "{\n" +
            "  \"from\": 1,\n" +
            "  \"to\": 1,\n" +
            "  \"count\": 1,\n" +
            "  \"_links\": {},\n" +
            "  \"hits\": [\n" +
            "    {\n" +
            "      \"recipe\": {\n" +
            "        \"uri\": \"http://www.edamam.com/ontologies/edamam.owl#recipe_ecb55f6c6f2f63a94939f1956002abe7\",\n" +
            "        \"label\": \"Garlicky Chicken Parm\",\n" +
            "        \"image\": \"https://edamam-product-images.s3.amazonaws.com/web-img/fc1/fc1f3345bcf24f45dca506360581a821.jpeg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHEaCXVzLWVhc3QtMSJHMEUCIQCorejAO3XNtZBeNH9U6Ru73G3D1AmCk0Z3P1SNZLcitAIgPyR0QJYKCWd7T5RDJBseWcoWDBZ%2BwPD1uqW086JZU4kquQUIGhAAGgwxODcwMTcxNTA5ODYiDCsDr3rQfZ2L%2FDJn4yqWBfYb5Obni5F6KvAxlRIO3yTPEn9o%2FUqO4advijihRjt4%2Fe9km7hAMohoiULQ3pdN6n%2B5UjrhSB3D3SzFC1MEXquGeDk9%2FlzyieSnhim6e0UmerSyIf6q1E8TOFTBtBJmR%2BBaCrmeLpPsYKKxy5DfX9AxGH350h%2BkJ9kkT0I716CXkobtDa5MPd%2BZdUnsc8tWZ5cAN7riWYy3nWT86zREby%2B%2B9%2FdVVY%2FG9JLeCT1%2FSjWt84VKyCFokx%2Fh%2Bi%2Fz8CvYeAr87zCf21GeyZ4UuIBQr8hDnb8IbQcZXQVA1hkCyNvUet2QzDo3aQk%2F6Gr4E86kXxkE0tmKEhH7P%2BbbJGKS1rSSGmEKv2rl%2B6OPXJT1c90wdQ2VMvBYziC6kUdqMyffWWLmmvusTb9gz6RKPxTBtpvOE2cNvr52iuLg49uYuwQER0XBpdLMZ7gelp8jWB937GlV69RnWuxKJl7mJagJaGvtdOS9EVUiDlc%2BxyBaHuTVznH1%2Bl4J9tgwyAwQrPLDQL5RkyOpHJDqhEiTJow8kLUE82mDEgM1SeYuy2LpdsWG%2FxvOTKwU93Jbc2VHlu9F15qb%2BtFWC4%2Fa86tA7tmHLXYIPgt0UcY2rlifAQ1LP5%2FlCST4jcvzA1z02Y5QxT48qUWL3TZLVhDnjxc%2FqZI7XSHg7BtGnSUSDluZRTc9ErAH4ivwKJswQLZYlHI1vsmDiaZx3lJjkke1IYH4tp58ngJ%2B2b7MTTi8DkuTKsv5dC%2BNzr2v4ZXobYhtK1W0O2SYguVCYnElyOEJBXECeG9wauf5yEuP6HiMjBpTj54HdwoJr3bDE7UGUu3rxw1%2FlXqgBmCWP1YOaIsh8S3DJxpqxE%2FjrzhYYluFbtmMRzm4wi2p3CVLoO9YMOW4r6YGOrEBJdlcUZEFhSuOGOmZ3Z1t5YeDViyJ87RRT2PTBlsECJBJuU4clOFJmKc9fD2Y%2BxqdqCYNoXqHMqLmdM1jfVLS9zCfTN5oowYLPJIEL9GLiioBiaHesaU%2BSMHU%2B0GyieaVP9N62ghgi9LATWyEFEzlkN3lyHhuhu7IAIGAYfhAPOZ9LXBxfAyx4lJvErr%2F6ANgeMNwxldTpv%2F%2BPLknprOIluRKZ7WhZduJAKIikjaKEqcM&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230803T181714Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFOYIXFP4S%2F20230803%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=e1860b66492f940e35c119bd23b30714ae6298a572f61dab9aee2be21421783d\",\n" +
            "        \"images\": {\n" +
            "          \"THUMBNAIL\": {\n" +
            "            \"url\": \"https://edamam-product-images.s3.amazonaws.com/web-img/fc1/fc1f3345bcf24f45dca506360581a821-s.jpeg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHEaCXVzLWVhc3QtMSJHMEUCIQCorejAO3XNtZBeNH9U6Ru73G3D1AmCk0Z3P1SNZLcitAIgPyR0QJYKCWd7T5RDJBseWcoWDBZ%2BwPD1uqW086JZU4kquQUIGhAAGgwxODcwMTcxNTA5ODYiDCsDr3rQfZ2L%2FDJn4yqWBfYb5Obni5F6KvAxlRIO3yTPEn9o%2FUqO4advijihRjt4%2Fe9km7hAMohoiULQ3pdN6n%2B5UjrhSB3D3SzFC1MEXquGeDk9%2FlzyieSnhim6e0UmerSyIf6q1E8TOFTBtBJmR%2BBaCrmeLpPsYKKxy5DfX9AxGH350h%2BkJ9kkT0I716CXkobtDa5MPd%2BZdUnsc8tWZ5cAN7riWYy3nWT86zREby%2B%2B9%2FdVVY%2FG9JLeCT1%2FSjWt84VKyCFokx%2Fh%2Bi%2Fz8CvYeAr87zCf21GeyZ4UuIBQr8hDnb8IbQcZXQVA1hkCyNvUet2QzDo3aQk%2F6Gr4E86kXxkE0tmKEhH7P%2BbbJGKS1rSSGmEKv2rl%2B6OPXJT1c90wdQ2VMvBYziC6kUdqMyffWWLmmvusTb9gz6RKPxTBtpvOE2cNvr52iuLg49uYuwQER0XBpdLMZ7gelp8jWB937GlV69RnWuxKJl7mJagJaGvtdOS9EVUiDlc%2BxyBaHuTVznH1%2Bl4J9tgwyAwQrPLDQL5RkyOpHJDqhEiTJow8kLUE82mDEgM1SeYuy2LpdsWG%2FxvOTKwU93Jbc2VHlu9F15qb%2BtFWC4%2Fa86tA7tmHLXYIPgt0UcY2rlifAQ1LP5%2FlCST4jcvzA1z02Y5QxT48qUWL3TZLVhDnjxc%2FqZI7XSHg7BtGnSUSDluZRTc9ErAH4ivwKJswQLZYlHI1vsmDiaZx3lJjkke1IYH4tp58ngJ%2B2b7MTTi8DkuTKsv5dC%2BNzr2v4ZXobYhtK1W0O2SYguVCYnElyOEJBXECeG9wauf5yEuP6HiMjBpTj54HdwoJr3bDE7UGUu3rxw1%2FlXqgBmCWP1YOaIsh8S3DJxpqxE%2FjrzhYYluFbtmMRzm4wi2p3CVLoO9YMOW4r6YGOrEBJdlcUZEFhSuOGOmZ3Z1t5YeDViyJ87RRT2PTBlsECJBJuU4clOFJmKc9fD2Y%2BxqdqCYNoXqHMqLmdM1jfVLS9zCfTN5oowYLPJIEL9GLiioBiaHesaU%2BSMHU%2B0GyieaVP9N62ghgi9LATWyEFEzlkN3lyHhuhu7IAIGAYfhAPOZ9LXBxfAyx4lJvErr%2F6ANgeMNwxldTpv%2F%2BPLknprOIluRKZ7WhZduJAKIikjaKEqcM&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230803T181714Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFOYIXFP4S%2F20230803%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=3d9b99ff1649908511b5e0d2bff3344438b4d842edb35bdd48efaeff724f57b0\",\n" +
            "            \"width\": 100,\n" +
            "            \"height\": 100\n" +
            "          },\n" +
            "          \"SMALL\": {\n" +
            "            \"url\": \"https://edamam-product-images.s3.amazonaws.com/web-img/fc1/fc1f3345bcf24f45dca506360581a821-m.jpeg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHEaCXVzLWVhc3QtMSJHMEUCIQCorejAO3XNtZBeNH9U6Ru73G3D1AmCk0Z3P1SNZLcitAIgPyR0QJYKCWd7T5RDJBseWcoWDBZ%2BwPD1uqW086JZU4kquQUIGhAAGgwxODcwMTcxNTA5ODYiDCsDr3rQfZ2L%2FDJn4yqWBfYb5Obni5F6KvAxlRIO3yTPEn9o%2FUqO4advijihRjt4%2Fe9km7hAMohoiULQ3pdN6n%2B5UjrhSB3D3SzFC1MEXquGeDk9%2FlzyieSnhim6e0UmerSyIf6q1E8TOFTBtBJmR%2BBaCrmeLpPsYKKxy5DfX9AxGH350h%2BkJ9kkT0I716CXkobtDa5MPd%2BZdUnsc8tWZ5cAN7riWYy3nWT86zREby%2B%2B9%2FdVVY%2FG9JLeCT1%2FSjWt84VKyCFokx%2Fh%2Bi%2Fz8CvYeAr87zCf21GeyZ4UuIBQr8hDnb8IbQcZXQVA1hkCyNvUet2QzDo3aQk%2F6Gr4E86kXxkE0tmKEhH7P%2BbbJGKS1rSSGmEKv2rl%2B6OPXJT1c90wdQ2VMvBYziC6kUdqMyffWWLmmvusTb9gz6RKPxTBtpvOE2cNvr52iuLg49uYuwQER0XBpdLMZ7gelp8jWB937GlV69RnWuxKJl7mJagJaGvtdOS9EVUiDlc%2BxyBaHuTVznH1%2Bl4J9tgwyAwQrPLDQL5RkyOpHJDqhEiTJow8kLUE82mDEgM1SeYuy2LpdsWG%2FxvOTKwU93Jbc2VHlu9F15qb%2BtFWC4%2Fa86tA7tmHLXYIPgt0UcY2rlifAQ1LP5%2FlCST4jcvzA1z02Y5QxT48qUWL3TZLVhDnjxc%2FqZI7XSHg7BtGnSUSDluZRTc9ErAH4ivwKJswQLZYlHI1vsmDiaZx3lJjkke1IYH4tp58ngJ%2B2b7MTTi8DkuTKsv5dC%2BNzr2v4ZXobYhtK1W0O2SYguVCYnElyOEJBXECeG9wauf5yEuP6HiMjBpTj54HdwoJr3bDE7UGUu3rxw1%2FlXqgBmCWP1YOaIsh8S3DJxpqxE%2FjrzhYYluFbtmMRzm4wi2p3CVLoO9YMOW4r6YGOrEBJdlcUZEFhSuOGOmZ3Z1t5YeDViyJ87RRT2PTBlsECJBJuU4clOFJmKc9fD2Y%2BxqdqCYNoXqHMqLmdM1jfVLS9zCfTN5oowYLPJIEL9GLiioBiaHesaU%2BSMHU%2B0GyieaVP9N62ghgi9LATWyEFEzlkN3lyHhuhu7IAIGAYfhAPOZ9LXBxfAyx4lJvErr%2F6ANgeMNwxldTpv%2F%2BPLknprOIluRKZ7WhZduJAKIikjaKEqcM&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230803T181714Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFOYIXFP4S%2F20230803%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=232d970454e2fd53d3db52b2baebde6d5f60cf096a0a438f811a64903d2f3948\",\n" +
            "            \"width\": 200,\n" +
            "            \"height\": 200\n" +
            "          },\n" +
            "          \"REGULAR\": {\n" +
            "            \"url\": \"https://edamam-product-images.s3.amazonaws.com/web-img/fc1/fc1f3345bcf24f45dca506360581a821.jpeg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHEaCXVzLWVhc3QtMSJHMEUCIQCorejAO3XNtZBeNH9U6Ru73G3D1AmCk0Z3P1SNZLcitAIgPyR0QJYKCWd7T5RDJBseWcoWDBZ%2BwPD1uqW086JZU4kquQUIGhAAGgwxODcwMTcxNTA5ODYiDCsDr3rQfZ2L%2FDJn4yqWBfYb5Obni5F6KvAxlRIO3yTPEn9o%2FUqO4advijihRjt4%2Fe9km7hAMohoiULQ3pdN6n%2B5UjrhSB3D3SzFC1MEXquGeDk9%2FlzyieSnhim6e0UmerSyIf6q1E8TOFTBtBJmR%2BBaCrmeLpPsYKKxy5DfX9AxGH350h%2BkJ9kkT0I716CXkobtDa5MPd%2BZdUnsc8tWZ5cAN7riWYy3nWT86zREby%2B%2B9%2FdVVY%2FG9JLeCT1%2FSjWt84VKyCFokx%2Fh%2Bi%2Fz8CvYeAr87zCf21GeyZ4UuIBQr8hDnb8IbQcZXQVA1hkCyNvUet2QzDo3aQk%2F6Gr4E86kXxkE0tmKEhH7P%2BbbJGKS1rSSGmEKv2rl%2B6OPXJT1c90wdQ2VMvBYziC6kUdqMyffWWLmmvusTb9gz6RKPxTBtpvOE2cNvr52iuLg49uYuwQER0XBpdLMZ7gelp8jWB937GlV69RnWuxKJl7mJagJaGvtdOS9EVUiDlc%2BxyBaHuTVznH1%2Bl4J9tgwyAwQrPLDQL5RkyOpHJDqhEiTJow8kLUE82mDEgM1SeYuy2LpdsWG%2FxvOTKwU93Jbc2VHlu9F15qb%2BtFWC4%2Fa86tA7tmHLXYIPgt0UcY2rlifAQ1LP5%2FlCST4jcvzA1z02Y5QxT48qUWL3TZLVhDnjxc%2FqZI7XSHg7BtGnSUSDluZRTc9ErAH4ivwKJswQLZYlHI1vsmDiaZx3lJjkke1IYH4tp58ngJ%2B2b7MTTi8DkuTKsv5dC%2BNzr2v4ZXobYhtK1W0O2SYguVCYnElyOEJBXECeG9wauf5yEuP6HiMjBpTj54HdwoJr3bDE7UGUu3rxw1%2FlXqgBmCWP1YOaIsh8S3DJxpqxE%2FjrzhYYluFbtmMRzm4wi2p3CVLoO9YMOW4r6YGOrEBJdlcUZEFhSuOGOmZ3Z1t5YeDViyJ87RRT2PTBlsECJBJuU4clOFJmKc9fD2Y%2BxqdqCYNoXqHMqLmdM1jfVLS9zCfTN5oowYLPJIEL9GLiioBiaHesaU%2BSMHU%2B0GyieaVP9N62ghgi9LATWyEFEzlkN3lyHhuhu7IAIGAYfhAPOZ9LXBxfAyx4lJvErr%2F6ANgeMNwxldTpv%2F%2BPLknprOIluRKZ7WhZduJAKIikjaKEqcM&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230803T181714Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFOYIXFP4S%2F20230803%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=e1860b66492f940e35c119bd23b30714ae6298a572f61dab9aee2be21421783d\",\n" +
            "            \"width\": 300,\n" +
            "            \"height\": 300\n" +
            "          }\n" +
            "        },\n" +
            "        \"source\": \"Food Network\",\n" +
            "        \"url\": \"https://www.foodnetwork.com/recipes/nancy-fuller/garlicky-chicken-parm-3612404\",\n" +
            "        \"shareAs\": \"http://www.edamam.com/recipe/garlicky-chicken-parm-ecb55f6c6f2f63a94939f1956002abe7/chicken+parm\",\n" +
            "        \"yield\": 8,\n" +
            "        \"dietLabels\": [\n" +
            "          \"Low-Carb\"\n" +
            "        ],\n" +
            "        \"healthLabels\": [\n" +
            "          \"Peanut-Free\",\n" +
            "          \"Tree-Nut-Free\",\n" +
            "          \"Soy-Free\",\n" +
            "          \"Fish-Free\",\n" +
            "          \"Shellfish-Free\",\n" +
            "          \"Pork-Free\",\n" +
            "          \"Red-Meat-Free\",\n" +
            "          \"Crustacean-Free\",\n" +
            "          \"Mustard-Free\",\n" +
            "          \"Sesame-Free\",\n" +
            "          \"Lupine-Free\",\n" +
            "          \"Mollusk-Free\",\n" +
            "          \"Alcohol-Free\",\n" +
            "          \"Sulfite-Free\"\n" +
            "        ],\n" +
            "        \"cautions\": [\n" +
            "          \"Sulfites\"\n" +
            "        ],\n" +
            "        \"ingredientLines\": [\n" +
            "          \"1/4 cup extra-virgin olive oil\",\n" +
            "          \"1 small onion, chopped\",\n" +
            "          \"1 clove garlic, chopped\",\n" +
            "          \"1 carrot, peeled and finely chopped\",\n" +
            "          \"1 stalk celery, chopped\",\n" +
            "          \"Sea salt and freshly ground pepper\",\n" +
            "          \"1 32-ounce can crushed tomatoes\",\n" +
            "          \"1 teaspoon dried oregano\",\n" +
            "          \"8 skinless, boneless chicken breasts (6 to 8 ounces each)\",\n" +
            "          \"Kosher salt and freshly ground pepper\",\n" +
            "          \"1 cup all-purpose flour\",\n" +
            "          \"3 large eggs\",\n" +
            "          \"3/4 cup milk\",\n" +
            "          \"7 cloves garlic, minced\",\n" +
            "          \"3 cups panko breadcrumbs\",\n" +
            "          \"2 teaspoons garlic salt\",\n" +
            "          \"Vegetable oil, for frying\",\n" +
            "          \"1/4 cup grated parmesan cheese\",\n" +
            "          \"1 1/2 cups shredded mozzarella cheese\"\n" +
            "        ],\n" +
            "        \"ingredients\": [\n" +
            "          {\n" +
            "            \"text\": \"1/4 cup extra-virgin olive oil\",\n" +
            "            \"quantity\": 0.25,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"extra-virgin olive oil\",\n" +
            "            \"weight\": 54,\n" +
            "            \"foodCategory\": \"Oils\",\n" +
            "            \"foodId\": \"food_b1d1icuad3iktrbqby0hiagafaz7\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/4d6/4d651eaa8a353647746290c7a9b29d84.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 small onion, chopped\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"<unit>\",\n" +
            "            \"food\": \"onion\",\n" +
            "            \"weight\": 70,\n" +
            "            \"foodCategory\": \"vegetables\",\n" +
            "            \"foodId\": \"food_bmrvi4ob4binw9a5m7l07amlfcoy\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/205/205e6bf2399b85d34741892ef91cc603.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 clove garlic, chopped\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"clove\",\n" +
            "            \"food\": \"garlic\",\n" +
            "            \"weight\": 3,\n" +
            "            \"foodCategory\": \"vegetables\",\n" +
            "            \"foodId\": \"food_avtcmx6bgjv1jvay6s6stan8dnyp\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/6ee/6ee142951f48aaf94f4312409f8d133d.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 carrot, peeled and finely chopped\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"<unit>\",\n" +
            "            \"food\": \"carrot\",\n" +
            "            \"weight\": 61,\n" +
            "            \"foodCategory\": \"vegetables\",\n" +
            "            \"foodId\": \"food_ai215e5b85pdh5ajd4aafa3w2zm8\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/121/121e33fce0bb9546ed7d060b6c114e29.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 stalk celery, chopped\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"stalk\",\n" +
            "            \"food\": \"celery\",\n" +
            "            \"weight\": 40,\n" +
            "            \"foodCategory\": \"vegetables\",\n" +
            "            \"foodId\": \"food_bffeoksbyyur8ja4da73ub2xs57g\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/d91/d91d2aed1c36d8fad54c4d7dc58f5a18.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"Sea salt and freshly ground pepper\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"Sea salt\",\n" +
            "            \"weight\": 22.895682585,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_a1vgrj1bs8rd1majvmd9ubz8ttkg\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/694/6943ea510918c6025795e8dc6e6eaaeb.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"Sea salt and freshly ground pepper\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"pepper\",\n" +
            "            \"weight\": 11.4478412925,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_b6ywzluaaxv02wad7s1r9ag4py89\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/c6e/c6e5c3bd8d3bc15175d9766971a4d1b2.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 32-ounce can crushed tomatoes\",\n" +
            "            \"quantity\": 32,\n" +
            "            \"measure\": \"ounce\",\n" +
            "            \"food\": \"crushed tomatoes\",\n" +
            "            \"weight\": 907.18474,\n" +
            "            \"foodCategory\": \"canned vegetables\",\n" +
            "            \"foodId\": \"food_aqqtb83adjyq8ybf51yo8bsjetdh\",\n" +
            "            \"image\": null\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 teaspoon dried oregano\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"teaspoon\",\n" +
            "            \"food\": \"dried oregano\",\n" +
            "            \"weight\": 1,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_bkkw6v3bdf0sqiazmzyuiax7i8jr\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/1b0/1b0eaffb1c261606e0d82fed8e9747a7.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"8 skinless, boneless chicken breasts (6 to 8 ounces each)\",\n" +
            "            \"quantity\": 56,\n" +
            "            \"measure\": \"ounce\",\n" +
            "            \"food\": \"chicken breasts\",\n" +
            "            \"weight\": 1587.5732950000001,\n" +
            "            \"foodCategory\": \"Poultry\",\n" +
            "            \"foodId\": \"food_bdrxu94aj3x2djbpur8dhagfhkcn\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/da5/da510379d3650787338ca16fb69f4c94.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"Kosher salt and freshly ground pepper\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"Kosher salt\",\n" +
            "            \"weight\": 22.895682585,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_a1vgrj1bs8rd1majvmd9ubz8ttkg\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/694/6943ea510918c6025795e8dc6e6eaaeb.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"Kosher salt and freshly ground pepper\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"pepper\",\n" +
            "            \"weight\": 11.4478412925,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_b6ywzluaaxv02wad7s1r9ag4py89\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/c6e/c6e5c3bd8d3bc15175d9766971a4d1b2.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 cup all-purpose flour\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"all-purpose flour\",\n" +
            "            \"weight\": 125,\n" +
            "            \"foodCategory\": \"grains\",\n" +
            "            \"foodId\": \"food_ar3x97tbq9o9p6b6gzwj0am0c81l\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/368/368077bbcab62f862a8c766a56ea5dd1.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"3 large eggs\",\n" +
            "            \"quantity\": 3,\n" +
            "            \"measure\": \"<unit>\",\n" +
            "            \"food\": \"eggs\",\n" +
            "            \"weight\": 150,\n" +
            "            \"foodCategory\": \"Eggs\",\n" +
            "            \"foodId\": \"food_bhpradua77pk16aipcvzeayg732r\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"3/4 cup milk\",\n" +
            "            \"quantity\": 0.75,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"milk\",\n" +
            "            \"weight\": 183,\n" +
            "            \"foodCategory\": \"Milk\",\n" +
            "            \"foodId\": \"food_b49rs1kaw0jktabzkg2vvanvvsis\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/7c9/7c9962acf83654a8d98ea6a2ade93735.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"7 cloves garlic, minced\",\n" +
            "            \"quantity\": 7,\n" +
            "            \"measure\": \"clove\",\n" +
            "            \"food\": \"garlic\",\n" +
            "            \"weight\": 21,\n" +
            "            \"foodCategory\": \"vegetables\",\n" +
            "            \"foodId\": \"food_avtcmx6bgjv1jvay6s6stan8dnyp\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/6ee/6ee142951f48aaf94f4312409f8d133d.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"3 cups panko breadcrumbs\",\n" +
            "            \"quantity\": 3,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"panko breadcrumbs\",\n" +
            "            \"weight\": 180,\n" +
            "            \"foodCategory\": \"grains\",\n" +
            "            \"foodId\": \"food_a9tnk2lbj0xkckbytqnx1ajhpqbp\",\n" +
            "            \"image\": null\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"2 teaspoons garlic salt\",\n" +
            "            \"quantity\": 2,\n" +
            "            \"measure\": \"teaspoon\",\n" +
            "            \"food\": \"garlic salt\",\n" +
            "            \"weight\": 6.2,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_boq94r1a036492bdup9u1beyph0l\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/5c3/5c3db1d5a1a16b1f0a74796f74dd5985.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"Vegetable oil, for frying\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"measure\": null,\n" +
            "            \"food\": \"Vegetable oil\",\n" +
            "            \"weight\": 51.896880526,\n" +
            "            \"foodCategory\": \"Oils\",\n" +
            "            \"foodId\": \"food_bt1mzi2ah2sfg8bv7no1qai83w8s\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/6e5/6e51a63a6300a8ea1b4c4cc68dfaba33.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1/4 cup grated parmesan cheese\",\n" +
            "            \"quantity\": 0.25,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"parmesan cheese\",\n" +
            "            \"weight\": 37.1765625,\n" +
            "            \"foodCategory\": \"Cheese\",\n" +
            "            \"foodId\": \"food_a104ppxa06d3emb272fkcab3cugd\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/f58/f588658627c59d5041e4664119829aa9.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 1/2 cups shredded mozzarella cheese\",\n" +
            "            \"quantity\": 1.5,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"mozzarella cheese\",\n" +
            "            \"weight\": 389.8125,\n" +
            "            \"foodCategory\": \"Cheese\",\n" +
            "            \"foodId\": \"food_acjhpy7bkl7a9qboztipaa2i9e4m\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/92d/92d92d4a4dfe8c025cea407c9ce764c3.jpg\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"calories\": 10723.64398569648,\n" +
            "        \"totalCO2Emissions\": 30281.789454382375,\n" +
            "        \"co2EmissionsClass\": \"G\",\n" +
            "        \"totalWeight\": 4423.780810762119,\n" +
            "        \"totalTime\": 135,\n" +
            "        \"cuisineType\": [\n" +
            "          \"mediterranean\"\n" +
            "        ],\n" +
            "        \"mealType\": [\n" +
            "          \"lunch/dinner\"\n" +
            "        ],\n" +
            "        \"dishType\": [\n" +
            "          \"main course\"\n" +
            "        ],\n" +
            "        \"totalNutrients\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 10723.64398569648,\n" +
            "            \"unit\": \"kcal\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 806.3540342665209,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 129.20075321319658,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FATRN\": {\n" +
            "            \"label\": \"Trans\",\n" +
            "            \"quantity\": 4.575274714765877,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAMS\": {\n" +
            "            \"label\": \"Monounsaturated\",\n" +
            "            \"quantity\": 501.95651247259315,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAPU\": {\n" +
            "            \"label\": \"Polyunsaturated\",\n" +
            "            \"quantity\": 119.08328615561405,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 349.5616584629,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOCDF.net\": {\n" +
            "            \"label\": \"Carbohydrates (net)\",\n" +
            "            \"quantity\": 312.37254070889503,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 37.189117754005,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"SUGAR\": {\n" +
            "            \"label\": \"Sugars\",\n" +
            "            \"quantity\": 66.76162139729401,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 530.87008147484,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOLE\": {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"quantity\": 2107.44169285,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 10263.361572048201,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 3942.4649608348786,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 859.4248294092388,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 9759.31791709161,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 33.307798002336774,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 28.43462170440037,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 6272.8762573843005,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITA_RAE\": {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"quantity\": 1936.91986162295,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 101.06539608,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 3.5573336488668006,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 6.228644963103,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 172.925825163844,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 15.562097390647352,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate equivalent (total)\",\n" +
            "            \"quantity\": 787.18923816445,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"FOLFD\": {\n" +
            "            \"label\": \"Folate (food)\",\n" +
            "            \"quantity\": 459.68923816445,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"FOLAC\": {\n" +
            "            \"label\": \"Folic acid\",\n" +
            "            \"quantity\": 192.5,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 8.7841539195,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITD\": {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"quantity\": 7.5139453125,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 157.16540603822247,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 156.5938247219,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"WATER\": {\n" +
            "            \"label\": \"Water\",\n" +
            "            \"quantity\": 2646.298887455903,\n" +
            "            \"unit\": \"g\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"totalDaily\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 536.182199284824,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 1240.5446681023398,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 646.003766065983,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 116.52055282096667,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 148.75647101602,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 1061.74016294968,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOLE\": {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"quantity\": 702.4805642833334,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 427.6400655020084,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 394.2464960834879,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 204.6249593831521,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 207.64506206577894,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 185.0433222352043,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 258.49656094909426,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 896.1251796263286,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITA_RAE\": {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"quantity\": 215.21331795810556,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 112.29488453333332,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 296.44447073890007,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 479.12653562330763,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 1080.786407274025,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 1197.084414665181,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate equivalent (total)\",\n" +
            "            \"quantity\": 196.79730954111247,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 366.0064133125,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITD\": {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"quantity\": 50.09296875,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 1047.7693735881498,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 130.49485393491668,\n" +
            "            \"unit\": \"%\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"digest\": [\n" +
            "          {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"tag\": \"FAT\",\n" +
            "            \"schemaOrgTag\": \"fatContent\",\n" +
            "            \"total\": 806.3540342665209,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 1240.5446681023398,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Saturated\",\n" +
            "                \"tag\": \"FASAT\",\n" +
            "                \"schemaOrgTag\": \"saturatedFatContent\",\n" +
            "                \"total\": 129.20075321319658,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 646.003766065983,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Trans\",\n" +
            "                \"tag\": \"FATRN\",\n" +
            "                \"schemaOrgTag\": \"transFatContent\",\n" +
            "                \"total\": 4.575274714765877,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Monounsaturated\",\n" +
            "                \"tag\": \"FAMS\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 501.95651247259315,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Polyunsaturated\",\n" +
            "                \"tag\": \"FAPU\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 119.08328615561405,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"tag\": \"CHOCDF\",\n" +
            "            \"schemaOrgTag\": \"carbohydrateContent\",\n" +
            "            \"total\": 349.5616584629,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 116.52055282096667,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Carbs (net)\",\n" +
            "                \"tag\": \"CHOCDF.net\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 312.37254070889503,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Fiber\",\n" +
            "                \"tag\": \"FIBTG\",\n" +
            "                \"schemaOrgTag\": \"fiberContent\",\n" +
            "                \"total\": 37.189117754005,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 148.75647101602,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Sugars\",\n" +
            "                \"tag\": \"SUGAR\",\n" +
            "                \"schemaOrgTag\": \"sugarContent\",\n" +
            "                \"total\": 66.76162139729401,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Sugars, added\",\n" +
            "                \"tag\": \"SUGAR.added\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 0,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"tag\": \"PROCNT\",\n" +
            "            \"schemaOrgTag\": \"proteinContent\",\n" +
            "            \"total\": 530.87008147484,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 1061.74016294968,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"tag\": \"CHOLE\",\n" +
            "            \"schemaOrgTag\": \"cholesterolContent\",\n" +
            "            \"total\": 2107.44169285,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 702.4805642833334,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"tag\": \"NA\",\n" +
            "            \"schemaOrgTag\": \"sodiumContent\",\n" +
            "            \"total\": 10263.361572048201,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 427.6400655020084,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"tag\": \"CA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 3942.4649608348786,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 394.2464960834879,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"tag\": \"MG\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 859.4248294092388,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 204.6249593831521,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"tag\": \"K\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 9759.31791709161,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 207.64506206577894,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"tag\": \"FE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 33.307798002336774,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 185.0433222352043,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"tag\": \"ZN\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 28.43462170440037,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 258.49656094909426,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"tag\": \"P\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 6272.8762573843005,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 896.1251796263286,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"tag\": \"VITA_RAE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1936.91986162295,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 215.21331795810556,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"tag\": \"VITC\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 101.06539608,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 112.29488453333332,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"tag\": \"THIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 3.5573336488668006,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 296.44447073890007,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"tag\": \"RIBF\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 6.228644963103,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 479.12653562330763,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"tag\": \"NIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 172.925825163844,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 1080.786407274025,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"tag\": \"VITB6A\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 15.562097390647352,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 1197.084414665181,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folate equivalent (total)\",\n" +
            "            \"tag\": \"FOLDFE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 787.18923816445,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 196.79730954111247,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folate (food)\",\n" +
            "            \"tag\": \"FOLFD\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 459.68923816445,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folic acid\",\n" +
            "            \"tag\": \"FOLAC\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 192.5,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"tag\": \"VITB12\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 8.7841539195,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 366.0064133125,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"tag\": \"VITD\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 7.5139453125,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 50.09296875,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"tag\": \"TOCPHA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 157.16540603822247,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 1047.7693735881498,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"tag\": \"VITK1\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 156.5938247219,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 130.49485393491668,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Sugar alcohols\",\n" +
            "            \"tag\": \"Sugar.alcohol\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Water\",\n" +
            "            \"tag\": \"WATER\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 2646.298887455903,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"g\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"_links\": {\n" +
            "        \"self\": {\n" +
            "          \"href\": \"https://api.edamam.com/api/recipes/v2/ecb55f6c6f2f63a94939f1956002abe7?type=public&app_id=456d7882&app_key=772e2aed7f585b9eeacffbfaacf59de6\",\n" +
            "          \"title\": \"Self\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void validCodeRecieved_onlyToSearchFor() {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "", "", "", "");
        api.execute();

        int code = api.getResponseCode();

        assertEquals(200, code);
    }

    @Test
    public void validBodyReceived_onlyToSearchFor() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "", "", "", "");
        api.execute();

        ResponseBody body = api.getResponseBody();
        byte[] bytes = body.bytes();
        String actual = new String(bytes, StandardCharsets.UTF_8);

        String expected =
            "{" +
                "\"from\":1," +
                "\"to\":20," +
                "\"count\":179," +
                "\"_links\":" +
                "{" +
                    "\"next\":" +
                    "{" +
                        "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYlB6GgACQmNFBmEaZlBzAwsVX3dIAWZFMAYiUAZSFmJECmUbYVciVgcBFjRFUWVBalciBhFqX3cWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=456d7882&beta=false\"," +
                        "\"title\":\"Next page\"" +
                    "}" +
                "}," +
                "\"hits\":" +
                "[" +
                    "{" +
                        "\"recipe\":" +
                        "{" +
                            "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e\"," +
                            "\"label\":\"Baked Chicken Parm\"," +
                            "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/";

        assertEquals(expected, actual.substring(0, expected.length()));
    }

    @Test
    public void validStringReceivedFromStringMethod_onlyToSearchFor() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "", "", "", "");
        api.execute();

        String actual = api.getResponseAsString();
        String expected =
                "{" +
                        "\"from\":1," +
                        "\"to\":20," +
                        "\"count\":179," +
                        "\"_links\":" +
                        "{" +
                        "\"next\":" +
                        "{" +
                        "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYlB6GgACQmNFBmEaZlBzAwsVX3dIAWZFMAYiUAZSFmJECmUbYVciVgcBFjRFUWVBalciBhFqX3cWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=456d7882&beta=false\"," +
                        "\"title\":\"Next page\"" +
                        "}" +
                        "}," +
                        "\"hits\":" +
                        "[" +
                        "{" +
                        "\"recipe\":" +
                        "{" +
                        "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e\"," +
                        "\"label\":\"Baked Chicken Parm\"," +
                        "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/";

        assertEquals(expected, actual.substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_numIngredientsMin5() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "5",
                "", "", "", "", "", "",
                "", "", "", "");
        api.execute();

        String expected =
            "{" +
                "\"from\":1," +
                "\"to\":20," +
                "\"count\":174," +
                "\"_links\":" +
                "{" +
                    "\"next\":" +
                    "{" +
                        "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&ingr=5%2B&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYlB7GgsFS2xGBGQWYlZ1AwIPUXlSB2dCMVN2BVdWEGYTVmoXa1d2VwFWSzRJV2IWa1Z3VwcVLnlSVSBMPkd5BgMbUSYRVTdgMgksRlpSAAcRXTVGcV84SU4%3D&type=public&app_id=456d7882&beta=false\"," +
                        "\"title\":\"Next page\"" +
                    "}" +
                "}," +
                    "\"hits\":" +
                    "[" +
                        "{" +
                            "\"recipe\":" +
                            "{" +
                                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e\"," +
                                "\"label\":\"Baked Chicken Parm\"," +
                                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_numIngredientsMax7() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "7", "", "", "", "", "",
                "", "", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":20," +
                "\"count\":41," +
                "\"_links\":{" +
                "\"next\":{" +
                "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&ingr=7&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=456d7882&beta=false\"," +
                "\"title\":\"Next page\"" +
                "}" +
                "}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_aeb5fe0082957c9226b40de1e9025d73\"," +
                "\"label\":\"Chicken Parm-asaurus\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/757/";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_numIngredientsMin5Max7() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "5",
                "7", "", "", "", "", "",
                "", "", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":20," +
                "\"count\":36," +
                "\"_links\":{" +
                "\"next\":{" +
                "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&ingr=5-7&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=456d7882&beta=false\"," +
                "\"title\":\"Next page\"" +
                "}" +
                "}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_aeb5fe0082957c9226b40de1e9025d73\"," +
                "\"label\":\"Chicken Parm-asaurus\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/757/";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_dietHighProtein() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", DietOptions.HIGH_PROTEIN.getKey(), "", "", "", "",
                "", "", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":6," +
                "\"count\":6," +
                "\"_links\":{}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_983fa931bdc4b1f34892636367f07393\"," +
                "\"label\":\"Chicken Parm Panini\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/b55/";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_healthVegan() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", HealthOptions.VEGAN.getKey(), "", "", "",
                "", "", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":2," +
                "\"count\":2," +
                "\"_links\":{}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_c808cb623073b83e65872b5987e7d024\"," +
                "\"label\":\"Marinara Sauce\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/cc5/";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_cuisineChinese() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", CuisineType.CHINESE.getKey(), "", "",
                "", "", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":1," +
                "\"count\":1," +
                "\"_links\":{}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_5ad196caa55d1e35e67c2cffeb564b92\"," +
                "\"label\":\"Chicken Parmesan Wontons\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/fe6/fe6dde68e76eb138bbfe9db147fd2105.jpg";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_mealSnack() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", MealType.SNACK.getKey(), "",
                "", "", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":1," +
                "\"count\":1," +
                "\"_links\":{}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_c808cb623073b83e65872b5987e7d024\"," +
                "\"label\":\"Marinara Sauce\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/cc5/cc594c96e8f3f1dd0686fa09711048fc.jpg";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_dishSideDish() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", DishType.SIDE_DISH.getKey(),
                "", "", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":0," +
                "\"count\":0," +
                "\"_links\":{}," +
                "\"hits\":[]" +
                "}";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_minCalories500() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "500", "", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":20," +
                "\"count\":83," +
                "\"_links\":{" +
                "\"next\":{" +
                "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYl1tBwIPRm1DAWoRZ1JzAwMCUXlSB2UWY10iAVYPQGcUVmFHYQFxVgBUQWERUDMTZQB1UAMVLnlSVSBMPkd5BgMbUSYRVTdgMgksRlpSAAcRXTVGcV84SU4%3D&calories=500%2B&type=public&app_id=456d7882&beta=false\"," +
                "\"title\":\"Next page\"" +
                "}" +
                "}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e\"," +
                "\"label\":\"Baked Chicken Parm\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_maxCalories700() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "", "700", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":20," +
                "\"count\":133," +
                "\"_links\":{" +
                "\"next\":{" +
                "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYld2GgUES2ZIBGMXZlx6AgMOUXlSUmJFNlYlAQsEQzdBUjRHZwRyUgYAETRJV2sWalElBVIVLnlSVSBMPkd5BgMbUSYRVTdgMgksRlpSAAcRXTVGcV84SU4%3D&calories=700&type=public&app_id=456d7882&beta=false\"," +
                "\"title\":\"Next page\"" +
                "}" +
                "}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_3361e31c23021a0e26919cade8f90679\"," +
                "\"label\":\"Skillet Chicken Parm\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/b33/b33953d4e85dbf3f536cc95d763fb141.jpg";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_minCalories500maxCalories700() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "500", "700", "", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":20," +
                "\"count\":37," +
                "\"_links\":{" +
                "\"next\":{" +
                "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&calories=500-700&type=public&app_id=456d7882&beta=false\"," +
                "\"title\":\"Next page\"" +
                "}" +
                "}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_3361e31c23021a0e26919cade8f90679\"," +
                "\"label\":\"Skillet Chicken Parm\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/b33/b33953d4e85dbf3f536cc95d763fb141.jpg";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_minTime120() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "", "", "120", "");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":4," +
                "\"count\":4," +
                "\"_links\":{}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_ecb55f6c6f2f63a94939f1956002abe7\"," +
                "\"label\":\"Garlicky Chicken Parm\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/fc1/fc1f3345bcf24f45dca506360581a821.jpeg";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_maxTime150() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "", "", "", "150");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":20," +
                "\"count\":176," +
                "\"_links\":{" +
                "\"next\":{" +
                "\"href\":\"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&random=false&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYlB0GgsER2BCBmMXZVF7AAAAUXlSAmAWNwEhBgtRFWRCC2obMFMhDFBTEDNBVjcUNQd7UAoVLnlSVSBMPkd5BgMbUSYRVTdgMgksRlpSAAcRXTVGcV84SU4%3D&time=150&type=public&app_id=456d7882&beta=false\"," +
                "\"title\":\"Next page\"" +
                "}" +
                "}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e\"," +
                "\"label\":\"Baked Chicken Parm\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test
    public void validResponses_onlyToSearchFor_minTime120maxTime150() throws IOException {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "", "", "120", "150");
        api.execute();

        String expected = "{" +
                "\"from\":1," +
                "\"to\":1," +
                "\"count\":1," +
                "\"_links\":{}," +
                "\"hits\":[" +
                "{" +
                "\"recipe\":{" +
                "\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_ecb55f6c6f2f63a94939f1956002abe7\"," +
                "\"label\":\"Garlicky Chicken Parm\"," +
                "\"image\":\"https://edamam-product-images.s3.amazonaws.com/web-img/fc1/fc1f3345bcf24f45dca506360581a821.jpeg";

        assertEquals(200, api.getResponseCode());
        assertEquals(expected, api.getResponseAsString().substring(0, expected.length()));
    }

    @Test(expected = RuntimeException.class)
    public void RuntimeException_spaceForField() {
        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", " ", "", "", "", "",
                "", "", "120", "150");
        api.execute();
    }

    @Test
    public void getClassObjectFromAPI() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        RecipeResponse expected = objectMapper.readValue(RECIPERESPONSE, RecipeResponse.class);

        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2("chicken parm", "",
                "", "", "", "", "", "",
                "", "", "120", "150");api.execute();
        api.execute();

        RecipeResponse actual = api.getRecipeResponse();

        assertEquals(expected, actual);
    }
}
