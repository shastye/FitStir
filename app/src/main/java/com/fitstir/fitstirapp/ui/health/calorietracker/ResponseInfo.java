package com.fitstir.fitstirapp.ui.health.calorietracker;

import com.fitstir.fitstirapp.ui.health.edamamapi.ISearchResult;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Hint;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;

import java.util.Calendar;
import java.util.Objects;

public class ResponseInfo {

    private String resultID;
    private Calendar date;
    private String mealType;
    private ISearchResult item;
    private int quantity;


    public ResponseInfo() {
        this.date = Calendar.getInstance();
        this.mealType = "";
        this.item = new Parsed();
        this.quantity = 0;
        this.resultID = "";
    }
    public ResponseInfo(Calendar date) {
        this.date = date;
        this.mealType = "";
        this.item = new Parsed();
        this.quantity = 0;
        this.resultID = "";
    }
    public ResponseInfo(Calendar date, String mealType, ISearchResult item, int quantity) {
        this.date = date;
        this.mealType = mealType;
        this.item = item;
        this.quantity = quantity;

        String id = "";
        if (item instanceof Parsed) {
            id = ((Parsed) item).getFood().getFoodId();
            int index = id.indexOf("food_");
            if (index != -1) {
                index += 5;
                id = id.substring(index);
            }
        } else if (item instanceof Hint) {
            id = ((Hint) item).getFood().getFoodId();
            int index = id.indexOf("food_");
            if (index != -1) {
                index += 5;
                id = id.substring(index);
            }
        } else if (item instanceof Hit) {
            id = ((Hit) item).getRecipe().getUri();
            int index = id.indexOf("recipe_");
            if (index != -1) {
                index += 7;
                id = id.substring(index);
            }
        }

        String collID = id + "&&&&&";
        collID += date.get(Calendar.YEAR);
        collID += "-";
        collID += date.get(Calendar.MONTH);
        collID += "-";
        collID += date.get(Calendar.DATE);
        collID += "*";
        collID += date.get(Calendar.HOUR_OF_DAY);
        collID += "-";
        collID += date.get(Calendar.MINUTE);
        collID += "-";
        collID += date.get(Calendar.SECOND);
        collID += "-";
        collID += date.get(Calendar.MILLISECOND);
        this.resultID = collID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof ResponseInfo)) {
            return false;
        } else {
            ResponseInfo that = (ResponseInfo) o;

            boolean b1 = isDate(that.date);

            int thisIndex = resultID.indexOf("&&&&&");
            String thisID = resultID.substring(0, thisIndex);
            int thatIndex = that.resultID.indexOf("&&&&&");
            String thatID = that.resultID.substring(0, thatIndex);
            boolean b2 = Objects.equals(thisID, thatID);

            boolean b3 = Objects.equals(item, that.item);
            boolean b4 = Objects.equals(mealType, that.mealType);

            return b1 && b2 && b3 && b4;
        }
    }

    public Calendar getDate() {
        return date;
    }
    public void setDate(Calendar date) {
        this.date = date;
    }
    public String getMealType() {
        return mealType;
    }
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    public ISearchResult getItem() {
        return item;
    }
    public void setItem(ISearchResult item) {
        this.item = item;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getResultID() {
        return resultID;
    }
    public void setResultID(String resultID) {
        this.resultID = resultID;
    }



    public boolean isDate(Calendar date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date.getTime());

        Calendar c2 = Calendar.getInstance();
        c2.setTime(this.date.getTime());

        int d1 = c1.get(Calendar.DAY_OF_YEAR);
        int d2 = c2.get(Calendar.DAY_OF_YEAR);

        return d1 == d2;
    }
}
