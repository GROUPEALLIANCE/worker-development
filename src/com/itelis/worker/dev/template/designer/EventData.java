package com.itelis.worker.dev.template.designer;

import java.util.ArrayList;
import java.util.List;

public class EventData {
    private List<EventBean> bar;

    private List<TimeSeriesBean> time;

    public List<EventBean> getBar() {
        return bar;
    }

    public void setBar(List<EventBean> bar) {
        this.bar = bar;
    }

    public List<TimeSeriesBean> getTime() {
        return time;
    }

    public void setTime(List<TimeSeriesBean> time) {
        this.time = time;
    }

    public List<EventData> getEventData(){
        ArrayList<EventData> dataArr = new ArrayList<EventData>();

        EventData data = new EventData();
        EventNameList dataList = new EventNameList();

        data.setBar(dataList.getDataBeanList());
        data.setTime(dataList.getSingleDataBeanList().get(0).getTime());

        dataArr.add(data);
        return dataArr;
    }
}
