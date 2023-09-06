package com.fitstir.fitstirapp.ui.health.diary;

import java.util.ArrayList;
import java.util.Date;

public class DiaryEntry {
    private Date date;
    private String emoji;
    private String comment;



    public DiaryEntry() {
        date = null;
        emoji = null;
        comment = null;
    }
    public DiaryEntry(Date date, String emoji, String comment) {
        this.date = date;
        this.emoji = emoji;
        this.comment = comment;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}