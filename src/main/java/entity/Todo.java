package entity;

import java.io.Serializable;
import java.util.Date;


public class Todo implements Serializable {


    private static long serialVersionUID = 123L;

    Date date = new Date();
    private int id;
    private String name;
    public String nowTime;
    private boolean inProgress;

    public Todo() {
    }

    public Todo(int id, String task, String date, boolean b) {
        this.id = id;
        this.name = task;
      //  this.nowTime = String.format("%tA %<td %<tB %<tr", date);
        this.nowTime = date;
        this.inProgress = b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public boolean setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
        return inProgress;
    }
}
