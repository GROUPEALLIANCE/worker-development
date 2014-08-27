package com.itelis.worker.dev.template.designer;


import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.*;
/**
 * 
 * @author echokodjeu
 *
 */
public class JasperDesignForTemplate {
    public JasperDesign design() throws Exception {

        // set basic design for main report
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName("simpleReport");
        jasperDesign.setPageWidth(595);
        jasperDesign.setPageHeight(842);
        jasperDesign.setColumnWidth(270);
        jasperDesign.setColumnSpacing(15);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(30);
        jasperDesign.setBottomMargin(30);    

        //Parameters

        // field
        JRDesignField bar = new JRDesignField();
        bar.setName("bar");
        bar.setValueClass(java.util.List.class);
        jasperDesign.addField(bar);

        JRDesignField time = new JRDesignField();
        time.setName("time");
        time.setValueClass(java.util.List.class);
        jasperDesign.addField(time);

        JRDesignBand band = new JRDesignBand();

        //Group
        JRDesignGroup group = new JRDesignGroup();
        group.setName("Chart group");

        band = new JRDesignBand();
        band.setHeight(250);
        band.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignSubreport jSubreport = new JRDesignSubreport(jasperDesign); 
        jSubreport.setUsingCache(false); 
        jSubreport.setRemoveLineWhenBlank(true); 
        jSubreport.setPrintRepeatedValues(false);

        JRDesignExpression expression = new JRDesignExpression();
        expression.setText("new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($F{time})");

        jSubreport.setDataSourceExpression(expression);

        expression = new JRDesignExpression(); 
        expression.setText("\"TimeSeriesChartSubReport.jasper\"");

        jSubreport.setExpression(expression);
        band.addElement(jSubreport);

        ((JRDesignSection)group.getGroupHeaderSection()).addBand(band);
        jasperDesign.addGroup(group);

        JRDesignGroup Chartgroup = new JRDesignGroup();
        Chartgroup.setName("Chart group Chart");

        JRDesignBand chartband = new JRDesignBand();
        chartband.setHeight(250);
        chartband.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignSubreport jSubreportChart = new JRDesignSubreport(jasperDesign); 
        jSubreportChart.setUsingCache(false); 
        jSubreportChart.setRemoveLineWhenBlank(true); 

        JRDesignExpression expressionChart = new JRDesignExpression();
        expressionChart.setText("new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($F{bar})");

        jSubreportChart.setDataSourceExpression(expressionChart);

        expressionChart = new JRDesignExpression(); 
        expressionChart.setText("\"BarCharSubReport.jasper\"");

        jSubreportChart.setExpression(expressionChart);
        chartband.addElement(jSubreportChart);

        ((JRDesignSection)Chartgroup.getGroupHeaderSection()).addBand(chartband);
        jasperDesign.addGroup(Chartgroup);


        // title band
        band = new JRDesignBand();
        band.setHeight(20);
        band.setSplitType(SplitTypeEnum.STRETCH);

        JRDesignStaticText staticText = new JRDesignStaticText();
        staticText.setX(0);
        staticText.setY(0);
        staticText.setHeight(20);
        staticText.setWidth(550);
        staticText.setText("Avis de Dispense d'Avance de Frais ");
        staticText.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
        staticText.setFontSize(15);
        band.addElement(staticText);
        jasperDesign.setTitle(band);
        // end of title band

        // page header band
        band = new JRDesignBand();
        band.setHeight(50);
        band.setSplitType(SplitTypeEnum.STRETCH);
        jasperDesign.setPageHeader(band);
        // end of page header band

        // column header band
        band = new JRDesignBand();
        band.setHeight(50);
        band.setSplitType(SplitTypeEnum.STRETCH);
        jasperDesign.setColumnHeader(band);
        // end of column header band

        //detail band
        band = new JRDesignBand();
        band.setHeight(20);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(band);
        // end of detail band

        // column footer band
        band = new JRDesignBand();
        band.setHeight(20);
        jasperDesign.setColumnFooter(band);
        // end of column footer band

        // page footer band
        band = new JRDesignBand();
        band.setHeight(20);
        jasperDesign.setPageFooter(band);
        // end of page footer band

        // summary band
        band = new JRDesignBand();
        band.setHeight(20);
        jasperDesign.setSummary(band);
        // end of summary band

        return jasperDesign;
    }
}
