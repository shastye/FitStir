package com.fitstir.fitstirapp.ui.health.diary;

import java.util.ArrayList;

public class DiaryData {
    private ArrayList<DiaryEntry> emotions;
    private ArrayList<Task> tasks;



    public DiaryData() {
        emotions = new ArrayList<>();
        tasks = new ArrayList<>();
    }



    public ArrayList<DiaryEntry> getEmotions() {
        return emotions;
    }

    public void setEmotions(ArrayList<DiaryEntry> emotions) {
        this.emotions = emotions;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

}
