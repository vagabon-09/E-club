package com.championclub_balirmath.com.Model;

public class NoteModel {
    String userId, noteTitle, noteContent;
    long time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public NoteModel(String userId, String noteTitle, String noteContent, long time) {
        this.userId = userId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.time = time;
    }

    public NoteModel() {
    }

}
