package com.fitstir.fitstirapp.ui.health.diary;

import java.util.ArrayList;

public class DiaryData {
    private ArrayList<DiaryEntry> emotions;
    private Task task01;
    private Task task02;
    private Task task03;
    private Task task04;
    private Task task05;
    private Task task06;
    private Task task07;
    private Task task08;
    private Task task09;
    private Task task10;



    public DiaryData() {
        emotions = new ArrayList<>();
        task01 = null;
        task02 = null;
        task03 = null;
        task04 = null;
        task05 = null;
        task06 = null;
        task07 = null;
        task08 = null;
        task09 = null;
        task10 = null;
    }



    public ArrayList<DiaryEntry> getEmotions() {
        return emotions;
    }

    public void setEmotions(ArrayList<DiaryEntry> emotions) {
        this.emotions = emotions;
    }

    public Task getTask01() {
        return task01;
    }

    public void setTask01(Task task01) {
        this.task01 = task01;
    }

    public Task getTask02() {
        return task02;
    }

    public void setTask02(Task task02) {
        this.task02 = task02;
    }

    public Task getTask03() {
        return task03;
    }

    public void setTask03(Task task03) {
        this.task03 = task03;
    }

    public Task getTask04() {
        return task04;
    }

    public void setTask04(Task task04) {
        this.task04 = task04;
    }

    public Task getTask05() {
        return task05;
    }

    public void setTask05(Task task05) {
        this.task05 = task05;
    }

    public Task getTask06() {
        return task06;
    }

    public void setTask06(Task task06) {
        this.task06 = task06;
    }

    public Task getTask07() {
        return task07;
    }

    public void setTask07(Task task07) {
        this.task07 = task07;
    }

    public Task getTask08() {
        return task08;
    }

    public void setTask08(Task task08) {
        this.task08 = task08;
    }

    public Task getTask09() {
        return task09;
    }

    public void setTask09(Task task09) {
        this.task09 = task09;
    }

    public Task getTask10() {
        return task10;
    }

    public void setTask10(Task task10) {
        this.task10 = task10;
    }
}
