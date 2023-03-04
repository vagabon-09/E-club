package com.championclub_balirmath.com.Model;

public class NoteModel {
    String userId, noteTitle, noteContent;

    public NoteModel(String userId, String noteTitle, String noteContent) {
        this.userId = userId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public NoteModel() {
    }

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

    public class myViewHolder {
    }
}
