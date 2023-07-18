package com.example.compsciia;

public class ToDoListObject {
    private String contents;
    private boolean isFinished;

    public ToDoListObject(String contents, boolean isFinished){
        this.contents = contents;
        this.isFinished = isFinished;
    }
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
