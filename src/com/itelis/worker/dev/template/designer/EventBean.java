package com.itelis.worker.dev.template.designer;
import java.util.ArrayList;
import java.util.List;

public class EventBean {
    private String field;
    private String count;
    private String pastcount;
    private List<TimeSeriesBean> time = new ArrayList<TimeSeriesBean>();

    public EventBean(){

    }

    public EventBean(String name, String count, String pastCount, List<TimeSeriesBean> time){
        this.field = name;
        this.count = count;
        this.time = time;
        this.pastcount = pastCount;
    }

    public String getField() {
        return field;
    }
    public void setName(String name) {
        this.field = name;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }

    public List<TimeSeriesBean> getTime() {
        return time;
    }

    public void setTime(List<TimeSeriesBean> time) {
        this.time = time;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPastcount() {
        return pastcount;
    }

    public void setPastcount(String pastcount) {
        this.pastcount = pastcount;
    }

}
