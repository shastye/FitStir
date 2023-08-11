package com.fitstir.fitstirapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.ui.health.edamamapi.Link;
import com.fitstir.fitstirapp.ui.health.edamamapi.Next;
import com.fitstir.fitstirapp.ui.health.edamamapi.Self;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Image;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Images;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Recipe;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.RecipeResponse;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.CuisineType;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.DietOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.DishType;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.HealthOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.MealType;

import org.junit.Test;

import java.util.ArrayList;

public class EdamamAPIRecipesV2ResponseTests {
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private String SELF = "{" +
            "\"href\":\"https://api.edamam.com/api/recipes/v2/ed04ad9d33c494f13f6406d53a6de34e?type=public&app_id=456d7882&app_key=772e2aed7f585b9eeacffbfaacf59de6\"," +
            "\"title\":\"Self\"" +
            "}";

    private String LINK = "{" +
            "\"self\":" + SELF +
            "}";

    private String IMAGE = "{" +
            "\"url\":\"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-s.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEG8aCXVzLWVhc3QtMSJHMEUCIQD%2BDHzvDC%2F7PpnxUzt3iq%2BrvoxBqaG8CixH3UJg5wiNxgIgAJlGm9QJObJpcTCpblR9KS1dXEzbElg5xPFoRBmFupMquQUIGBAAGgwxODcwMTcxNTA5ODYiDLKpElRbghaMbn3BXiqWBXNknkAkIVvwsopTYW%2BSNTZdaIFG5tZSDVMXjwIDxUwnUunh6pSiQXMG7c8aGaB3OwIb%2FScudM6OSA6Q0b1DjcXdn8xF0WpZwqcyVxEHv%2BgWnmaIYG6rcTalsiKQNLKIEoQ42BKsQtwwHwo3SEVGdTiQY12MDUdhQDqzdLwTQe2PlrdYjZY1aXjxYTqQOLHxpwQvQztVMRRTis%2FSTf3%2FirtEvKkDfUUO4vfoDeO3QfLGk9VE8jdyKIXv8nllgKctAx%2Fhj12XImiQsiYKf%2Fy2ZJpvtM5alyTQ8qyE8GIXgBMO62POHh7YrPnA62N5pH%2FQw366oS0McXQ4W%2F5iPxLMMyHS5%2F0g0iFk9BcbqjhY1Korbk%2BD9y3Kx%2BrGyenMNiztsBDJauLDu1cIBA0%2FaD4RiA%2BwZBO6TIUxj5PXfBrz4SPqJWka7rNLWnd03JRDF%2BqGvNCqlsRQKjSZMbmpQGAQbhzLa2Wc%2BNOwhRkkY43TvSfLwYyP2lVyFGkZF4ZjG1dtRWxgVoSzcjw%2BEGS8de3zTaSZZTwcQBduJ%2BSBbNyDE7g3H1AbzFfNUcECRoLXd9U1RjCS0MauQyJYi%2B2psEcJ6VsSEhB08NOeB33wSg2OPp2fQAL0xhXNimgw5pkWVBJhd5ZVTp9g%2BWjqd0TcfkQDiLmIrH4%2F1175D28Wh6TjUDPKfnofWBvegibDWNjGuEIL%2BoFPyefk3FAJQH5m%2BNSJKzKVOtQgeU07tsg0IhLuWAuo0x4E3wdzGVxLcwu3fxrbagGcTNd0FkhNLZOg4QMNVT%2BVTWPMFHmeJzI24Al1TA5o7ccFlVphEFL%2BtOJS8%2FYUelS2LLvHHSOS3LE9x92N39ohkN2cDYwszMWPcsRh5cPz1TStsdxJMOaAr6YGOrEBfNmL%2BDcjR1%2Bpkc8b9vjVAU0UkOUXvwZx%2BBQycd9L6lgzAZvN2yXE5%2BAiQBH6L%2BaBT1el0dsmt5zNsB963Dc9am32yTcWEF4FVMPJm0jDKYe3x4ZzAh1hCYPdQhOPZUFMb2hFNPsBp1Q03R232jvFSGKjsTbCskVJHMhDJKEzDhE2FyW%2FWjIVGCg8f3CiBbjdmTgJkM4v%2FSmxi7JmHdEfBOaeMbuOvv2ZZkQxBBUB13kx&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230803T161106Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFJ3IJQ34V%2F20230803%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=9aff17194f9ba40d32f7fb63b8a5a3a1f77de0b13b74309bf5352ceeb4b9cc56\"," +
            "\"width\":100," +
            "\"height\":100" +
            "}";
    
    private String IMAGES = "{" +
            "          \"THUMBNAIL\":" + IMAGE + "," +
            "          \"SMALL\":{" +
            "            \"url\":\"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-m.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEG8aCXVzLWVhc3QtMSJHMEUCIQD%2BDHzvDC%2F7PpnxUzt3iq%2BrvoxBqaG8CixH3UJg5wiNxgIgAJlGm9QJObJpcTCpblR9KS1dXEzbElg5xPFoRBmFupMquQUIGBAAGgwxODcwMTcxNTA5ODYiDLKpElRbghaMbn3BXiqWBXNknkAkIVvwsopTYW%2BSNTZdaIFG5tZSDVMXjwIDxUwnUunh6pSiQXMG7c8aGaB3OwIb%2FScudM6OSA6Q0b1DjcXdn8xF0WpZwqcyVxEHv%2BgWnmaIYG6rcTalsiKQNLKIEoQ42BKsQtwwHwo3SEVGdTiQY12MDUdhQDqzdLwTQe2PlrdYjZY1aXjxYTqQOLHxpwQvQztVMRRTis%2FSTf3%2FirtEvKkDfUUO4vfoDeO3QfLGk9VE8jdyKIXv8nllgKctAx%2Fhj12XImiQsiYKf%2Fy2ZJpvtM5alyTQ8qyE8GIXgBMO62POHh7YrPnA62N5pH%2FQw366oS0McXQ4W%2F5iPxLMMyHS5%2F0g0iFk9BcbqjhY1Korbk%2BD9y3Kx%2BrGyenMNiztsBDJauLDu1cIBA0%2FaD4RiA%2BwZBO6TIUxj5PXfBrz4SPqJWka7rNLWnd03JRDF%2BqGvNCqlsRQKjSZMbmpQGAQbhzLa2Wc%2BNOwhRkkY43TvSfLwYyP2lVyFGkZF4ZjG1dtRWxgVoSzcjw%2BEGS8de3zTaSZZTwcQBduJ%2BSBbNyDE7g3H1AbzFfNUcECRoLXd9U1RjCS0MauQyJYi%2B2psEcJ6VsSEhB08NOeB33wSg2OPp2fQAL0xhXNimgw5pkWVBJhd5ZVTp9g%2BWjqd0TcfkQDiLmIrH4%2F1175D28Wh6TjUDPKfnofWBvegibDWNjGuEIL%2BoFPyefk3FAJQH5m%2BNSJKzKVOtQgeU07tsg0IhLuWAuo0x4E3wdzGVxLcwu3fxrbagGcTNd0FkhNLZOg4QMNVT%2BVTWPMFHmeJzI24Al1TA5o7ccFlVphEFL%2BtOJS8%2FYUelS2LLvHHSOS3LE9x92N39ohkN2cDYwszMWPcsRh5cPz1TStsdxJMOaAr6YGOrEBfNmL%2BDcjR1%2Bpkc8b9vjVAU0UkOUXvwZx%2BBQycd9L6lgzAZvN2yXE5%2BAiQBH6L%2BaBT1el0dsmt5zNsB963Dc9am32yTcWEF4FVMPJm0jDKYe3x4ZzAh1hCYPdQhOPZUFMb2hFNPsBp1Q03R232jvFSGKjsTbCskVJHMhDJKEzDhE2FyW%2FWjIVGCg8f3CiBbjdmTgJkM4v%2FSmxi7JmHdEfBOaeMbuOvv2ZZkQxBBUB13kx&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230803T161106Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFJ3IJQ34V%2F20230803%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=24409bd5a737dcf1754695f8e771c950c844e97772534447e1d94c1d444a5787\"," +
            "            \"width\":200," +
            "            \"height\":200" +
            "          }," +
            "          \"REGULAR\":{" +
            "            \"url\":\"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEG8aCXVzLWVhc3QtMSJHMEUCIQD%2BDHzvDC%2F7PpnxUzt3iq%2BrvoxBqaG8CixH3UJg5wiNxgIgAJlGm9QJObJpcTCpblR9KS1dXEzbElg5xPFoRBmFupMquQUIGBAAGgwxODcwMTcxNTA5ODYiDLKpElRbghaMbn3BXiqWBXNknkAkIVvwsopTYW%2BSNTZdaIFG5tZSDVMXjwIDxUwnUunh6pSiQXMG7c8aGaB3OwIb%2FScudM6OSA6Q0b1DjcXdn8xF0WpZwqcyVxEHv%2BgWnmaIYG6rcTalsiKQNLKIEoQ42BKsQtwwHwo3SEVGdTiQY12MDUdhQDqzdLwTQe2PlrdYjZY1aXjxYTqQOLHxpwQvQztVMRRTis%2FSTf3%2FirtEvKkDfUUO4vfoDeO3QfLGk9VE8jdyKIXv8nllgKctAx%2Fhj12XImiQsiYKf%2Fy2ZJpvtM5alyTQ8qyE8GIXgBMO62POHh7YrPnA62N5pH%2FQw366oS0McXQ4W%2F5iPxLMMyHS5%2F0g0iFk9BcbqjhY1Korbk%2BD9y3Kx%2BrGyenMNiztsBDJauLDu1cIBA0%2FaD4RiA%2BwZBO6TIUxj5PXfBrz4SPqJWka7rNLWnd03JRDF%2BqGvNCqlsRQKjSZMbmpQGAQbhzLa2Wc%2BNOwhRkkY43TvSfLwYyP2lVyFGkZF4ZjG1dtRWxgVoSzcjw%2BEGS8de3zTaSZZTwcQBduJ%2BSBbNyDE7g3H1AbzFfNUcECRoLXd9U1RjCS0MauQyJYi%2B2psEcJ6VsSEhB08NOeB33wSg2OPp2fQAL0xhXNimgw5pkWVBJhd5ZVTp9g%2BWjqd0TcfkQDiLmIrH4%2F1175D28Wh6TjUDPKfnofWBvegibDWNjGuEIL%2BoFPyefk3FAJQH5m%2BNSJKzKVOtQgeU07tsg0IhLuWAuo0x4E3wdzGVxLcwu3fxrbagGcTNd0FkhNLZOg4QMNVT%2BVTWPMFHmeJzI24Al1TA5o7ccFlVphEFL%2BtOJS8%2FYUelS2LLvHHSOS3LE9x92N39ohkN2cDYwszMWPcsRh5cPz1TStsdxJMOaAr6YGOrEBfNmL%2BDcjR1%2Bpkc8b9vjVAU0UkOUXvwZx%2BBQycd9L6lgzAZvN2yXE5%2BAiQBH6L%2BaBT1el0dsmt5zNsB963Dc9am32yTcWEF4FVMPJm0jDKYe3x4ZzAh1hCYPdQhOPZUFMb2hFNPsBp1Q03R232jvFSGKjsTbCskVJHMhDJKEzDhE2FyW%2FWjIVGCg8f3CiBbjdmTgJkM4v%2FSmxi7JmHdEfBOaeMbuOvv2ZZkQxBBUB13kx&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230803T161106Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFJ3IJQ34V%2F20230803%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=60d9b0991a9a5b009a471ae396140c122cbbbfc6653160cf98dae1f382e578b2\"," +
            "            \"width\":300," +
            "            \"height\":300" +
            "          }" +
            "        }";

    private String RECIPE = "{\n" +
            "        \"uri\": \"http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e\",\n" +
            "        \"label\": \"Baked Chicken Parm\",\n" +
            "        \"image\": \"https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEG8aCXVzLWVhc3QtMSJHMEUCIQD%2BDHzvDC%2F7PpnxUzt3iq%2BrvoxBqaG8CixH3UJg5wiNxgIgAJlGm9QJObJpcTCpblR9KS1dXEzbElg5xPFoRBmFupMquQUIGBAAGgwxODcwMTcxNTA5ODYiDLKpElRbghaMbn3BXiqWBXNknkAkIVvwsopTYW%2BSNTZdaIFG5tZSDVMXjwIDxUwnUunh6pSiQXMG7c8aGaB3OwIb%2FScudM6OSA6Q0b1DjcXdn8xF0WpZwqcyVxEHv%2BgWnmaIYG6rcTalsiKQNLKIEoQ42BKsQtwwHwo3SEVGdTiQY12MDUdhQDqzdLwTQe2PlrdYjZY1aXjxYTqQOLHxpwQvQztVMRRTis%2FSTf3%2FirtEvKkDfUUO4vfoDeO3QfLGk9VE8jdyKIXv8nllgKctAx%2Fhj12XImiQsiYKf%2Fy2ZJpvtM5alyTQ8qyE8GIXgBMO62POHh7YrPnA62N5pH%2FQw366oS0McXQ4W%2F5iPxLMMyHS5%2F0g0iFk9BcbqjhY1Korbk%2BD9y3Kx%2BrGyenMNiztsBDJauLDu1cIBA0%2FaD4RiA%2BwZBO6TIUxj5PXfBrz4SPqJWka7rNLWnd03JRDF%2BqGvNCqlsRQKjSZMbmpQGAQbhzLa2Wc%2BNOwhRkkY43TvSfLwYyP2lVyFGkZF4ZjG1dtRWxgVoSzcjw%2BEGS8de3zTaSZZTwcQBduJ%2BSBbNyDE7g3H1AbzFfNUcECRoLXd9U1RjCS0MauQyJYi%2B2psEcJ6VsSEhB08NOeB33wSg2OPp2fQAL0xhXNimgw5pkWVBJhd5ZVTp9g%2BWjqd0TcfkQDiLmIrH4%2F1175D28Wh6TjUDPKfnofWBvegibDWNjGuEIL%2BoFPyefk3FAJQH5m%2BNSJKzKVOtQgeU07tsg0IhLuWAuo0x4E3wdzGVxLcwu3fxrbagGcTNd0FkhNLZOg4QMNVT%2BVTWPMFHmeJzI24Al1TA5o7ccFlVphEFL%2BtOJS8%2FYUelS2LLvHHSOS3LE9x92N39ohkN2cDYwszMWPcsRh5cPz1TStsdxJMOaAr6YGOrEBfNmL%2BDcjR1%2Bpkc8b9vjVAU0UkOUXvwZx%2BBQycd9L6lgzAZvN2yXE5%2BAiQBH6L%2BaBT1el0dsmt5zNsB963Dc9am32yTcWEF4FVMPJm0jDKYe3x4ZzAh1hCYPdQhOPZUFMb2hFNPsBp1Q03R232jvFSGKjsTbCskVJHMhDJKEzDhE2FyW%2FWjIVGCg8f3CiBbjdmTgJkM4v%2FSmxi7JmHdEfBOaeMbuOvv2ZZkQxBBUB13kx&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230803T161106Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=ASIASXCYXIIFJ3IJQ34V%2F20230803%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=fefb8008fbe3a23374a621afb6732f1c632e584f34af41dda17d08f94d6183b1\",\n" +
            "        \"images\":" + IMAGES + "," +
            "        \"source\": \"Food52\",\n" +
            "        \"url\": \"https://food52.com/recipes/17016-baked-chicken-parm\",\n" +
            "        \"shareAs\": \"http://www.edamam.com/recipe/baked-chicken-parm-ed04ad9d33c494f13f6406d53a6de34e/chicken+parm\",\n" +
            "        \"yield\": 4,\n" +
            "        \"dietLabels\": [\n" +
            "          \"high-fiber\",\n" +
            "          \"low-fat\"\n" +
            "        ],\n" +
            "        \"healthLabels\": [\n" +
            "          \"peanut-free\",\n" +
            "          \"tree-nut-free\",\n" +
            "          \"soy-free\",\n" +
            "          \"fish-free\",\n" +
            "          \"shellfish-free\",\n" +
            "          \"pork-free\",\n" +
            "          \"red-meat-free\",\n" +
            "          \"crustacean-free\",\n" +
            "          \"celery-free\",\n" +
            "          \"mustard-free\",\n" +
            "          \"sesame-free\",\n" +
            "          \"lupine-free\",\n" +
            "          \"mollusk-free\",\n" +
            "          \"alcohol-free\",\n" +
            "          \"sulfite-free\"\n" +
            "        ],\n" +
            "        \"cautions\": [\n" +
            "          \"Sulfites\"\n" +
            "        ],\n" +
            "        \"ingredientLines\": [\n" +
            "          \"1 tablespoon oil\",\n" +
            "          \"1 1/2 cup panko\",\n" +
            "          \"1/2 cup grated parmesan cheese\",\n" +
            "          \"1/4 teaspoon salt\",\n" +
            "          \"1/4 teaspoon ground black pepper\",\n" +
            "          \"1/8 teaspoon garlic powder\",\n" +
            "          \"1 teaspoon dried italian seasoning\",\n" +
            "          \"2 large eggs\",\n" +
            "          \"2 large boneless, skinless chicken breast halves\",\n" +
            "          \"1/2 cup grated sharp cheddar cheese or grated mozzarella cheese\",\n" +
            "          \"2 cups purchased marinara sauce\",\n" +
            "          \"4 servings angel hair pasta, prepared according to package instructions\"\n" +
            "        ],\n" +
            "        \"ingredients\": [\n" +
            "          {\n" +
            "            \"text\": \"1 tablespoon oil\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"tablespoon\",\n" +
            "            \"food\": \"oil\",\n" +
            "            \"weight\": 14,\n" +
            "            \"foodCategory\": \"Oils\",\n" +
            "            \"foodId\": \"food_bk9p9aaavhvoq4bqsnprobpsiuxs\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/07e/07e106ab3536d57428e5c46d009038f8.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 1/2 cup panko\",\n" +
            "            \"quantity\": 1.5,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"panko\",\n" +
            "            \"weight\": 90,\n" +
            "            \"foodCategory\": \"grains\",\n" +
            "            \"foodId\": \"food_a9tnk2lbj0xkckbytqnx1ajhpqbp\",\n" +
            "            \"image\": null\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1/2 cup grated parmesan cheese\",\n" +
            "            \"quantity\": 0.5,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"parmesan cheese\",\n" +
            "            \"weight\": 74.353125,\n" +
            "            \"foodCategory\": \"Cheese\",\n" +
            "            \"foodId\": \"food_a104ppxa06d3emb272fkcab3cugd\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/f58/f588658627c59d5041e4664119829aa9.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1/4 teaspoon salt\",\n" +
            "            \"quantity\": 0.25,\n" +
            "            \"measure\": \"teaspoon\",\n" +
            "            \"food\": \"salt\",\n" +
            "            \"weight\": 1.5,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_btxz81db72hwbra2pncvebzzzum9\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/694/6943ea510918c6025795e8dc6e6eaaeb.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1/4 teaspoon ground black pepper\",\n" +
            "            \"quantity\": 0.25,\n" +
            "            \"measure\": \"teaspoon\",\n" +
            "            \"food\": \"black pepper\",\n" +
            "            \"weight\": 0.575,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_b6ywzluaaxv02wad7s1r9ag4py89\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/c6e/c6e5c3bd8d3bc15175d9766971a4d1b2.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1/8 teaspoon garlic powder\",\n" +
            "            \"quantity\": 0.125,\n" +
            "            \"measure\": \"teaspoon\",\n" +
            "            \"food\": \"garlic powder\",\n" +
            "            \"weight\": 0.3875,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_boq94r1a036492bdup9u1beyph0l\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/5c3/5c3db1d5a1a16b1f0a74796f74dd5985.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1 teaspoon dried italian seasoning\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"measure\": \"teaspoon\",\n" +
            "            \"food\": \"italian seasoning\",\n" +
            "            \"weight\": 1,\n" +
            "            \"foodCategory\": \"Condiments and sauces\",\n" +
            "            \"foodId\": \"food_avsq22zadwyb5cb5sl1byaa4mbo8\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/89b/89b37a04e46e052671d73addcb84aa51.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"2 large eggs\",\n" +
            "            \"quantity\": 2,\n" +
            "            \"measure\": \"<unit>\",\n" +
            "            \"food\": \"eggs\",\n" +
            "            \"weight\": 100,\n" +
            "            \"foodCategory\": \"Eggs\",\n" +
            "            \"foodId\": \"food_bhpradua77pk16aipcvzeayg732r\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/a7e/a7ec7c337cb47c6550b3b118e357f077.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"2 large boneless, skinless chicken breast halves\",\n" +
            "            \"quantity\": 2,\n" +
            "            \"measure\": \"half\",\n" +
            "            \"food\": \"boneless, skinless chicken breast\",\n" +
            "            \"weight\": 217.5,\n" +
            "            \"foodCategory\": \"Poultry\",\n" +
            "            \"foodId\": \"food_bdrxu94aj3x2djbpur8dhagfhkcn\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/da5/da510379d3650787338ca16fb69f4c94.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"1/2 cup grated sharp cheddar cheese or grated mozzarella cheese\",\n" +
            "            \"quantity\": 0.5,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"cheddar cheese\",\n" +
            "            \"weight\": 66,\n" +
            "            \"foodCategory\": \"Cheese\",\n" +
            "            \"foodId\": \"food_bhppgmha1u27voagb8eptbp9g376\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/bcd/bcd94dde1fcde1475b5bf0540f821c5d.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"2 cups purchased marinara sauce\",\n" +
            "            \"quantity\": 2,\n" +
            "            \"measure\": \"cup\",\n" +
            "            \"food\": \"marinara sauce\",\n" +
            "            \"weight\": 528,\n" +
            "            \"foodCategory\": \"canned soup\",\n" +
            "            \"foodId\": \"food_a7hv5mybkkrs3ub78yhtxafs67bu\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/08b/08b3bb49c006689a458a8b9c4a4e0057.jpg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"text\": \"4 servings angel hair pasta, prepared according to package instructions\",\n" +
            "            \"quantity\": 4,\n" +
            "            \"measure\": \"serving\",\n" +
            "            \"food\": \"angel hair pasta\",\n" +
            "            \"weight\": 1200,\n" +
            "            \"foodCategory\": \"grains\",\n" +
            "            \"foodId\": \"food_a8hs60uayl5icia1qe8qoba1kwp8\",\n" +
            "            \"image\": \"https://www.edamam.com/food-img/296/296ff2b02ef3822928c3c923e22c7d19.jpg\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"calories\": 6121.690125,\n" +
            "        \"totalCO2Emissions\": 9159.680641885001,\n" +
            "        \"co2EmissionsClass\": \"F\",\n" +
            "        \"totalWeight\": 2293.315625,\n" +
            "        \"totalTime\": 116,\n" +
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
            "            \"quantity\": 6121.690125,\n" +
            "            \"unit\": \"kcal\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 99.37145500000001,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 33.374484875,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FATRN\": {\n" +
            "            \"label\": \"Trans\",\n" +
            "            \"quantity\": 0.145485,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAMS\": {\n" +
            "            \"label\": \"Monounsaturated\",\n" +
            "            \"quantity\": 29.444164875000002,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FAPU\": {\n" +
            "            \"label\": \"Polyunsaturated\",\n" +
            "            \"quantity\": 18.15075753125,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 1004.897483125,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOCDF.net\": {\n" +
            "            \"label\": \"Carbohydrates (net)\",\n" +
            "            \"quantity\": 953.563133125,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 51.33435000000001,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"SUGAR\": {\n" +
            "            \"label\": \"Sugars\",\n" +
            "            \"quantity\": 61.6435846875,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 275.85434375,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          \"CHOLE\": {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"quantity\": 657.235125,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 4639.179375,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 1996.2252500000002,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 857.972,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 5369.133625,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 25.023921875000003,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 25.2738396875,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 3929.6284375,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITA_RAE\": {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"quantity\": 721.64121875,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 11.06465,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 1.50674434375,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 2.41895875,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 62.352076468750006,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 4.67615334375,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate equivalent (total)\",\n" +
            "            \"quantity\": 377.25959375,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"FOLFD\": {\n" +
            "            \"label\": \"Folate (food)\",\n" +
            "            \"quantity\": 377.25959375,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"FOLAC\": {\n" +
            "            \"label\": \"Folic acid\",\n" +
            "            \"quantity\": 0,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 2.9649875000000003,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"VITD\": {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"quantity\": 2.767765625,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 19.425553125,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 105.76655312500002,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          \"WATER\": {\n" +
            "            \"label\": \"Water\",\n" +
            "            \"quantity\": 866.6162062500002,\n" +
            "            \"unit\": \"g\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"totalDaily\": {\n" +
            "          \"ENERC_KCAL\": {\n" +
            "            \"label\": \"Energy\",\n" +
            "            \"quantity\": 306.08450625,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FAT\": {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"quantity\": 152.87916153846155,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FASAT\": {\n" +
            "            \"label\": \"Saturated\",\n" +
            "            \"quantity\": 166.87242437499998,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOCDF\": {\n" +
            "            \"label\": \"Carbs\",\n" +
            "            \"quantity\": 334.96582770833334,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FIBTG\": {\n" +
            "            \"label\": \"Fiber\",\n" +
            "            \"quantity\": 205.3374,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"PROCNT\": {\n" +
            "            \"label\": \"Protein\",\n" +
            "            \"quantity\": 551.7086875,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CHOLE\": {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"quantity\": 219.078375,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NA\": {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"quantity\": 193.299140625,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"CA\": {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"quantity\": 199.62252500000002,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"MG\": {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"quantity\": 204.27904761904762,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"K\": {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"quantity\": 114.23688563829788,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FE\": {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"quantity\": 139.02178819444444,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"ZN\": {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"quantity\": 229.76217897727273,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"P\": {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"quantity\": 561.3754910714285,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITA_RAE\": {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"quantity\": 80.18235763888889,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITC\": {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"quantity\": 12.294055555555557,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"THIA\": {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"quantity\": 125.56202864583334,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"RIBF\": {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"quantity\": 186.07375,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"NIA\": {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"quantity\": 389.70047792968757,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB6A\": {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"quantity\": 359.70410336538464,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"FOLDFE\": {\n" +
            "            \"label\": \"Folate equivalent (total)\",\n" +
            "            \"quantity\": 94.31489843750002,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITB12\": {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"quantity\": 123.54114583333335,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITD\": {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"quantity\": 18.451770833333335,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"TOCPHA\": {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"quantity\": 129.5036875,\n" +
            "            \"unit\": \"%\"\n" +
            "          },\n" +
            "          \"VITK1\": {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"quantity\": 88.13879427083334,\n" +
            "            \"unit\": \"%\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"digest\": [\n" +
            "          {\n" +
            "            \"label\": \"Fat\",\n" +
            "            \"tag\": \"FAT\",\n" +
            "            \"schemaOrgTag\": \"fatContent\",\n" +
            "            \"total\": 99.37145500000001,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 152.87916153846155,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Saturated\",\n" +
            "                \"tag\": \"FASAT\",\n" +
            "                \"schemaOrgTag\": \"saturatedFatContent\",\n" +
            "                \"total\": 33.374484875,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 166.87242437499998,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Trans\",\n" +
            "                \"tag\": \"FATRN\",\n" +
            "                \"schemaOrgTag\": \"transFatContent\",\n" +
            "                \"total\": 0.145485,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Monounsaturated\",\n" +
            "                \"tag\": \"FAMS\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 29.444164875000002,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Polyunsaturated\",\n" +
            "                \"tag\": \"FAPU\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 18.15075753125,\n" +
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
            "            \"total\": 1004.897483125,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 334.96582770833334,\n" +
            "            \"unit\": \"g\",\n" +
            "            \"sub\": [\n" +
            "              {\n" +
            "                \"label\": \"Carbs (net)\",\n" +
            "                \"tag\": \"CHOCDF.net\",\n" +
            "                \"schemaOrgTag\": null,\n" +
            "                \"total\": 953.563133125,\n" +
            "                \"hasRDI\": false,\n" +
            "                \"daily\": 0,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Fiber\",\n" +
            "                \"tag\": \"FIBTG\",\n" +
            "                \"schemaOrgTag\": \"fiberContent\",\n" +
            "                \"total\": 51.33435000000001,\n" +
            "                \"hasRDI\": true,\n" +
            "                \"daily\": 205.3374,\n" +
            "                \"unit\": \"g\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"label\": \"Sugars\",\n" +
            "                \"tag\": \"SUGAR\",\n" +
            "                \"schemaOrgTag\": \"sugarContent\",\n" +
            "                \"total\": 61.6435846875,\n" +
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
            "            \"total\": 275.85434375,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 551.7086875,\n" +
            "            \"unit\": \"g\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Cholesterol\",\n" +
            "            \"tag\": \"CHOLE\",\n" +
            "            \"schemaOrgTag\": \"cholesterolContent\",\n" +
            "            \"total\": 657.235125,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 219.078375,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Sodium\",\n" +
            "            \"tag\": \"NA\",\n" +
            "            \"schemaOrgTag\": \"sodiumContent\",\n" +
            "            \"total\": 4639.179375,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 193.299140625,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Calcium\",\n" +
            "            \"tag\": \"CA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1996.2252500000002,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 199.62252500000002,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Magnesium\",\n" +
            "            \"tag\": \"MG\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 857.972,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 204.27904761904762,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Potassium\",\n" +
            "            \"tag\": \"K\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 5369.133625,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 114.23688563829788,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Iron\",\n" +
            "            \"tag\": \"FE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 25.023921875000003,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 139.02178819444444,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Zinc\",\n" +
            "            \"tag\": \"ZN\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 25.2738396875,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 229.76217897727273,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Phosphorus\",\n" +
            "            \"tag\": \"P\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 3929.6284375,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 561.3754910714285,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin A\",\n" +
            "            \"tag\": \"VITA_RAE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 721.64121875,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 80.18235763888889,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin C\",\n" +
            "            \"tag\": \"VITC\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 11.06465,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 12.294055555555557,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Thiamin (B1)\",\n" +
            "            \"tag\": \"THIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 1.50674434375,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 125.56202864583334,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Riboflavin (B2)\",\n" +
            "            \"tag\": \"RIBF\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 2.41895875,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 186.07375,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Niacin (B3)\",\n" +
            "            \"tag\": \"NIA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 62.352076468750006,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 389.70047792968757,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B6\",\n" +
            "            \"tag\": \"VITB6A\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 4.67615334375,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 359.70410336538464,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folate equivalent (total)\",\n" +
            "            \"tag\": \"FOLDFE\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 377.25959375,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 94.31489843750002,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folate (food)\",\n" +
            "            \"tag\": \"FOLFD\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 377.25959375,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Folic acid\",\n" +
            "            \"tag\": \"FOLAC\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 0,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin B12\",\n" +
            "            \"tag\": \"VITB12\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 2.9649875000000003,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 123.54114583333335,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin D\",\n" +
            "            \"tag\": \"VITD\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 2.767765625,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 18.451770833333335,\n" +
            "            \"unit\": \"µg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin E\",\n" +
            "            \"tag\": \"TOCPHA\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 19.425553125,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 129.5036875,\n" +
            "            \"unit\": \"mg\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Vitamin K\",\n" +
            "            \"tag\": \"VITK1\",\n" +
            "            \"schemaOrgTag\": null,\n" +
            "            \"total\": 105.76655312500002,\n" +
            "            \"hasRDI\": true,\n" +
            "            \"daily\": 88.13879427083334,\n" +
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
            "            \"total\": 866.6162062500002,\n" +
            "            \"hasRDI\": false,\n" +
            "            \"daily\": 0,\n" +
            "            \"unit\": \"g\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }";

    private String HIT = "{" +
            "\"recipe\":" + RECIPE + "," +
            "\"_links\":" + LINK +
            "}";

    private String RESPONSE = "{\n" +
            "  \"from\": 1,\n" +
            "  \"to\": 20,\n" +
            "  \"count\": 179,\n" +
            "  \"_links\": {\n" +
            "    \"next\": {\n" +
            "      \"href\": \"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYlB6GgACQmNFBmEaZlBzAwsVX3dIAWZFMAYiUAZSFmJECmUbYVciVgcBFjRFUWVBalciBhFqX3cWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=456d7882\",\n" +
            "      \"title\": \"Next page\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"hits\": [\n" +
            "    " + HIT + ",\n" +
            "    " + HIT + ",\n" +
            "    " + HIT + "\n" +
            "  ]\n" +
            "}";

    private String LINK2 = "{\n" +
            "    \"next\": {\n" +
            "      \"href\": \"https://api.edamam.com/api/recipes/v2?q=chicken%20parm&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYlB6GgACQmNFBmEaZlBzAwsVX3dIAWZFMAYiUAZSFmJECmUbYVciVgcBFjRFUWVBalciBhFqX3cWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=456d7882\",\n" +
            "      \"title\": \"Next page\"\n" +
            "    }" +
            "}";

    @Test
    public void Self() throws JsonProcessingException {
        Self expected = new Self();
        expected.setTitle("Self");
        expected.setHref("https://api.edamam.com/api/recipes/v2/ed04ad9d33c494f13f6406d53a6de34e?type=public&app_id=456d7882&app_key=772e2aed7f585b9eeacffbfaacf59de6");

        Self actual = objectMapper.readValue(SELF, Self.class);

        assertEquals(expected, actual);
    }

    @Test
    public void Link() throws JsonProcessingException {
        Link expected = new Link();
        Self temp = new Self();
        temp.setTitle("Self");
        temp.setHref("https://api.edamam.com/api/recipes/v2/ed04ad9d33c494f13f6406d53a6de34e?type=public&app_id=456d7882&app_key=772e2aed7f585b9eeacffbfaacf59de6");
        expected.setSelf(temp);

        Link actual = objectMapper.readValue(LINK, Link.class);

        assertEquals(expected, actual);
    }

    @Test
    public void Link2() throws JsonProcessingException {
        Link expected = new Link();
        Next temp = new Next();
        temp.setTitle("Next page");
        temp.setHref("https://api.edamam.com/api/recipes/v2?q=chicken%20parm&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYlB6GgACQmNFBmEaZlBzAwsVX3dIAWZFMAYiUAZSFmJECmUbYVciVgcBFjRFUWVBalciBhFqX3cWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=456d7882");
        expected.setNext(temp);

        Link actual = objectMapper.readValue(LINK2, Link.class);

        assertEquals(expected, actual);
    }

    @Test
    public void Image() throws JsonProcessingException {
        Image expected = new Image();
        expected.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-s.jpg");
        expected.setWidth(100);
        expected.setHeight(100);

        Image actual = objectMapper.readValue(IMAGE, Image.class);

        assertEquals(expected, actual);
    }

    @Test
    public void Images() throws JsonProcessingException {
        Images expected = new Images();
        Image t = new Image();
        t.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-s.jpg");
        t.setWidth(100);
        t.setHeight(100);
        expected.setTHUMBNAIL(t);
        Image s = new Image();
        s.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-m.jpg");
        s.setWidth(200);
        s.setHeight(200);
        expected.setSMALL(s);
        Image r = new Image();
        r.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg");
        r.setWidth(300);
        r.setHeight(300);
        expected.setREGULAR(r);

        Images actual = objectMapper.readValue(IMAGES, Images.class);
    }

    @Test
    public void Recipe() throws JsonProcessingException {
        Images tImages = new Images();
        Image t = new Image();
        t.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-s.jpg?");
        t.setWidth(100);
        t.setHeight(100);
        tImages.setTHUMBNAIL(t);
        Image s = new Image();
        s.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-m.jpg?");
        s.setWidth(200);
        s.setHeight(200);
        tImages.setSMALL(s);
        Image r = new Image();
        r.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg?");
        r.setWidth(300);
        r.setHeight(300);
        tImages.setREGULAR(r);

        Recipe expected = new Recipe();
        expected.setUri("http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e");
        expected.setLabel("Baked Chicken Parm");
        expected.setImage("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg?");
        expected.setImages(tImages);
        expected.setSource("Food52");
        expected.setUrl("https://food52.com/recipes/17016-baked-chicken-parm");
        expected.setYield(4);
        expected.setDietLabels(new ArrayList<String>() {{
            add(DietOptions.HIGH_FIBER.getKey());
            add(DietOptions.LOW_FAT.getKey());
        }});
        expected.setHealthLabels(new ArrayList<String>() {{
            add(HealthOptions.PEANUT_FREE.getKey());
            add(HealthOptions.TREE_NUT_FREE.getKey());
            add(HealthOptions.SOY_FREE.getKey());
            add(HealthOptions.FISH_FREE.getKey());
            add(HealthOptions.SHELLFISH_FREE.getKey());
            add(HealthOptions.PORK_FREE.getKey());
            add(HealthOptions.RED_MEAT_FREE.getKey());
            add(HealthOptions.CRUSTACEAN_FREE.getKey());
            add(HealthOptions.CELERY_FREE.getKey());
            add(HealthOptions.MUSTARD_FREE.getKey());
            add(HealthOptions.SESAME_FREE.getKey());
            add(HealthOptions.LUPINE_FREE.getKey());
            add("mollusk-free");
            add(HealthOptions.ALCOHOL_FREE.getKey());
            add("sulfite-free");
        }});
        expected.setIngredientLines(new ArrayList<String>() {{
            add("1 tablespoon oil");
            add("1 1/2 cup panko");
            add("1/2 cup grated parmesan cheese");
            add("1/4 teaspoon salt");
            add("1/4 teaspoon ground black pepper");
            add("1/8 teaspoon garlic powder");
            add("1 teaspoon dried italian seasoning");
            add("2 large eggs");
            add("2 large boneless, skinless chicken breast halves");
            add("1/2 cup grated sharp cheddar cheese or grated mozzarella cheese");
            add("2 cups purchased marinara sauce");
            add("4 servings angel hair pasta, prepared according to package instructions");
        }});
        expected.setCalories(6121.690125f);
        expected.setTotalTime(116);
        expected.setCuisineType(new ArrayList<String>() {{
            add(CuisineType.MEDITERRANEAN.getKey());
        }});
        expected.setMealType(new ArrayList<String>() {{
            add(MealType.DINNER.getKey());
        }});
        expected.setDishType(new ArrayList<String>() {{
            add(DishType.MAIN_COURSE.getKey());
        }});

        Recipe actual = objectMapper.readValue(RECIPE, Recipe.class);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void Hit() throws JsonProcessingException {
        Images tImages = new Images();
        Image t = new Image();
        t.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-s.jpg?");
        t.setWidth(100);
        t.setHeight(100);
        tImages.setTHUMBNAIL(t);
        Image s = new Image();
        s.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-m.jpg?");
        s.setWidth(200);
        s.setHeight(200);
        tImages.setSMALL(s);
        Image r = new Image();
        r.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg?");
        r.setWidth(300);
        r.setHeight(300);
        tImages.setREGULAR(r);

        Recipe tRecipe = new Recipe();
        tRecipe.setUri("http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e");
        tRecipe.setLabel("Baked Chicken Parm");
        tRecipe.setImage("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg?");
        tRecipe.setImages(tImages);
        tRecipe.setSource("Food52");
        tRecipe.setUrl("https://food52.com/recipes/17016-baked-chicken-parm");
        tRecipe.setYield(4);
        tRecipe.setDietLabels(new ArrayList<String>() {{
            add(DietOptions.HIGH_FIBER.getKey());
            add(DietOptions.LOW_FAT.getKey());
        }});
        tRecipe.setHealthLabels(new ArrayList<String>() {{
            add(HealthOptions.PEANUT_FREE.getKey());
            add(HealthOptions.TREE_NUT_FREE.getKey());
            add(HealthOptions.SOY_FREE.getKey());
            add(HealthOptions.FISH_FREE.getKey());
            add(HealthOptions.SHELLFISH_FREE.getKey());
            add(HealthOptions.PORK_FREE.getKey());
            add(HealthOptions.RED_MEAT_FREE.getKey());
            add(HealthOptions.CRUSTACEAN_FREE.getKey());
            add(HealthOptions.CELERY_FREE.getKey());
            add(HealthOptions.MUSTARD_FREE.getKey());
            add(HealthOptions.SESAME_FREE.getKey());
            add(HealthOptions.LUPINE_FREE.getKey());
            add("mollusk-free");
            add(HealthOptions.ALCOHOL_FREE.getKey());
            add("sulfite-free");
        }});
        tRecipe.setIngredientLines(new ArrayList<String>() {{
            add("1 tablespoon oil");
            add("1 1/2 cup panko");
            add("1/2 cup grated parmesan cheese");
            add("1/4 teaspoon salt");
            add("1/4 teaspoon ground black pepper");
            add("1/8 teaspoon garlic powder");
            add("1 teaspoon dried italian seasoning");
            add("2 large eggs");
            add("2 large boneless, skinless chicken breast halves");
            add("1/2 cup grated sharp cheddar cheese or grated mozzarella cheese");
            add("2 cups purchased marinara sauce");
            add("4 servings angel hair pasta, prepared according to package instructions");
        }});
        tRecipe.setCalories(6121.690125f);
        tRecipe.setTotalTime(116);
        tRecipe.setCuisineType(new ArrayList<String>() {{
            add(CuisineType.MEDITERRANEAN.getKey());
        }});
        tRecipe.setMealType(new ArrayList<String>() {{
            add(MealType.DINNER.getKey());
        }});
        tRecipe.setDishType(new ArrayList<String>() {{
            add(DishType.MAIN_COURSE.getKey());
        }});

        Link tLink = new Link();
        Self tSelf = new Self();
        tSelf.setTitle("Self");
        tSelf.setHref("https://api.edamam.com/api/recipes/v2/ed04ad9d33c494f13f6406d53a6de34e?type=public&app_id=456d7882&app_key=772e2aed7f585b9eeacffbfaacf59de6");
        tLink.setSelf(tSelf);

        Hit expected = new Hit();
        expected.setRecipe(tRecipe);
        expected.set_links(tLink);

        Hit actual = objectMapper.readValue(HIT, Hit.class);

        assertEquals(expected, actual);
    }

    @Test
    public void RecipeResponse() throws JsonProcessingException {
        Images tImages = new Images();
        Image t = new Image();
        t.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-s.jpg?");
        t.setWidth(100);
        t.setHeight(100);
        tImages.setTHUMBNAIL(t);
        Image s = new Image();
        s.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b-m.jpg?");
        s.setWidth(200);
        s.setHeight(200);
        tImages.setSMALL(s);
        Image r = new Image();
        r.setUrl("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg?");
        r.setWidth(300);
        r.setHeight(300);
        tImages.setREGULAR(r);

        Recipe tRecipe = new Recipe();
        tRecipe.setUri("http://www.edamam.com/ontologies/edamam.owl#recipe_ed04ad9d33c494f13f6406d53a6de34e");
        tRecipe.setLabel("Baked Chicken Parm");
        tRecipe.setImage("https://edamam-product-images.s3.amazonaws.com/web-img/6f7/6f7456d7de07c54c41e28053f7ec338b.jpg?");
        tRecipe.setImages(tImages);
        tRecipe.setSource("Food52");
        tRecipe.setUrl("https://food52.com/recipes/17016-baked-chicken-parm");
        tRecipe.setYield(4);
        tRecipe.setDietLabels(new ArrayList<String>() {{
            add(DietOptions.HIGH_FIBER.getKey());
            add(DietOptions.LOW_FAT.getKey());
        }});
        tRecipe.setHealthLabels(new ArrayList<String>() {{
            add(HealthOptions.PEANUT_FREE.getKey());
            add(HealthOptions.TREE_NUT_FREE.getKey());
            add(HealthOptions.SOY_FREE.getKey());
            add(HealthOptions.FISH_FREE.getKey());
            add(HealthOptions.SHELLFISH_FREE.getKey());
            add(HealthOptions.PORK_FREE.getKey());
            add(HealthOptions.RED_MEAT_FREE.getKey());
            add(HealthOptions.CRUSTACEAN_FREE.getKey());
            add(HealthOptions.CELERY_FREE.getKey());
            add(HealthOptions.MUSTARD_FREE.getKey());
            add(HealthOptions.SESAME_FREE.getKey());
            add(HealthOptions.LUPINE_FREE.getKey());
            add("mollusk-free");
            add(HealthOptions.ALCOHOL_FREE.getKey());
            add("sulfite-free");
        }});
        tRecipe.setIngredientLines(new ArrayList<String>() {{
            add("1 tablespoon oil");
            add("1 1/2 cup panko");
            add("1/2 cup grated parmesan cheese");
            add("1/4 teaspoon salt");
            add("1/4 teaspoon ground black pepper");
            add("1/8 teaspoon garlic powder");
            add("1 teaspoon dried italian seasoning");
            add("2 large eggs");
            add("2 large boneless, skinless chicken breast halves");
            add("1/2 cup grated sharp cheddar cheese or grated mozzarella cheese");
            add("2 cups purchased marinara sauce");
            add("4 servings angel hair pasta, prepared according to package instructions");
        }});
        tRecipe.setCalories(6121.690125f);
        tRecipe.setTotalTime(116);
        tRecipe.setCuisineType(new ArrayList<String>() {{
            add(CuisineType.MEDITERRANEAN.getKey());
        }});
        tRecipe.setMealType(new ArrayList<String>() {{
            add(MealType.DINNER.getKey());
        }});
        tRecipe.setDishType(new ArrayList<String>() {{
            add(DishType.MAIN_COURSE.getKey());
        }});

        Link tLink = new Link();
        Self tSelf = new Self();
        tSelf.setTitle("Self");
        tSelf.setHref("https://api.edamam.com/api/recipes/v2/ed04ad9d33c494f13f6406d53a6de34e?type=public&app_id=456d7882&app_key=772e2aed7f585b9eeacffbfaacf59de6");
        tLink.setSelf(tSelf);

        Link tLink2 = new Link();
        Next tNext = new Next();
        tNext.setTitle("Next page");
        tNext.setHref("https://api.edamam.com/api/recipes/v2?q=chicken%20parm&app_key=772e2aed7f585b9eeacffbfaacf59de6&_cont=CHcVQBtNNQphDmgVQ3tAEX4BYlB6GgACQmNFBmEaZlBzAwsVX3dIAWZFMAYiUAZSFmJECmUbYVciVgcBFjRFUWVBalciBhFqX3cWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=456d7882");
        tLink2.setNext(tNext);

        Hit tHit = new Hit();
        tHit.setRecipe(tRecipe);
        tHit.set_links(tLink);

        RecipeResponse expected = new RecipeResponse();
        expected.setFrom(1);
        expected.setTo(20);
        expected.setCount(179);
        expected.set_links(tLink2);
        expected.setHits(new ArrayList<Hit>() {{
            add(tHit);
            add(tHit);
            add(tHit);
        }});

        RecipeResponse actual = objectMapper.readValue(RESPONSE, RecipeResponse.class);

        assertEquals(expected, actual);
    }
}
