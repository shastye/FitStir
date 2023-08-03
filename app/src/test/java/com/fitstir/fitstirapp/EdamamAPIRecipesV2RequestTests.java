package com.fitstir.fitstirapp;

import static org.junit.Assert.assertEquals;

import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2.EdamamAPI_RecipesV2;
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
}
