package com.example.Entity;

public class Todo {
    private Long id;
    private String title;
    private boolean completed;

    // constructor
    public Todo() {
    }

    // constructor
    public Todo(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    // getter setter method
    public Long getId() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    // get and set title of task
    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public boolean iscompleted() {
        return completed;
    }

    public void setcompleted(boolean completed) {
        this.completed = completed;
    }

}
