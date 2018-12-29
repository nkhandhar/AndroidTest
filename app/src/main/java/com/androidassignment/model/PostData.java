package com.androidassignment.model;

public class PostData {

    String created_at, title;
    boolean selected;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public PostData(String created_at, String title, boolean selected) {
        this.created_at = created_at;
        this.title = title;
        this.selected = selected;

    }
}
