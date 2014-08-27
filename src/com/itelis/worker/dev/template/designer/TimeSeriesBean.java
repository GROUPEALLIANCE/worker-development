package com.itelis.worker.dev.template.designer;

public class TimeSeriesBean {

    private String count;
    private String timeStamp;

    public TimeSeriesBean(String count, String timeStamp) {
        this.count = count;
        this.timeStamp = timeStamp;
    }
    public TimeSeriesBean() {

    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }



}
