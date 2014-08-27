package com.itelis.worker.dev.template.designer;

import java.util.ArrayList;
import java.util.List;

public class EventNameList {

    public List<EventBean> getSingleDataBeanList() {
        ArrayList<EventBean> list = new ArrayList<EventBean>();

        ArrayList<TimeSeriesBean> listTime = new ArrayList<TimeSeriesBean>();
        TimeSeriesBean tbean = new TimeSeriesBean("413","1375951800");
        TimeSeriesBean tbean1 = new TimeSeriesBean("425","1375952100");
        TimeSeriesBean tbean2 = new TimeSeriesBean("396","1375952820");
        TimeSeriesBean tbean3 = new TimeSeriesBean("400","1375953540");
        TimeSeriesBean tbean4 = new TimeSeriesBean("200","1375953440");
        TimeSeriesBean tbean5 = new TimeSeriesBean("1400","1375953999");
        listTime.add(tbean);
        listTime.add(tbean1);
        listTime.add(tbean2);
        listTime.add(tbean3);
        listTime.add(tbean4);
        listTime.add(tbean5);

        list.add(generate("Flow", "100", "800", listTime));

        return list;
    }

    public List<EventBean> getDataBeanList() {
        ArrayList<EventBean> list = new ArrayList<EventBean>();

        ArrayList<TimeSeriesBean> listTime = new ArrayList<TimeSeriesBean>();
        TimeSeriesBean tbean = new TimeSeriesBean("413","1375951800");
        TimeSeriesBean tbean1 = new TimeSeriesBean("425","1375952100");
        TimeSeriesBean tbean2 = new TimeSeriesBean("396","1375952820");
        TimeSeriesBean tbean3 = new TimeSeriesBean("400","1375953540");
        TimeSeriesBean tbean4 = new TimeSeriesBean("400","1375953440");
        TimeSeriesBean tbean5 = new TimeSeriesBean("400","1375953999");
        listTime.add(tbean);
        listTime.add(tbean1);
        listTime.add(tbean2);
        listTime.add(tbean3);
        listTime.add(tbean4);
        listTime.add(tbean5);

        list.add(generate("Flow", "100", "800", null));
        list.add(generate("Non flow", "200", "50", null));
        list.add(generate("Allow", "600", "400", null));
        list.add(generate("Deny", "50", "200", null));
        list.add(generate("Block", "150", "1200", null));
        list.add(generate("Access", "10", "0", null));

        return list;
    }

    private EventBean generate(String name, String country, String pastCount, List<TimeSeriesBean> time) {
        EventBean bean = new EventBean();
        bean.setName(name);
        bean.setCount(country);
        bean.setPastcount(pastCount);
        bean.setTime(time);
        return bean;
    }


}
