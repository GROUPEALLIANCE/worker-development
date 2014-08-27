package com.itelis.worker.dev.template.designer;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.itelis.worker.dev.template.service.DynamicColumnDataSource;
import com.itelis.worker.dev.template.service.DynamicReportBuilder;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRValidationFault;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
/**
 * 
 * @author echokodjeu
 *
 */
public class DynamicJasper {
    protected JasperPrint jp;
    protected JasperReport jr;
    protected Map params = new HashMap();
    private static String inputjrxml = "Report.jrxml";
    private static String outputjasper = "Report.jasper";
    private static String pdffile = "Report.pdf";
    
/**
 * 
 * Dynamic columns Report
 * 
 * @param columnHeaders
 * @param rows
 * @throws JRException
 */
	
	public void runReport(List<String> columnHeaders, List<List<String>> rows) throws JRException {
		 
		InputStream is = getClass().getResourceAsStream("Test.jrxml");
		       JasperDesign jasperReportDesign = JRXmlLoader.load(is);
		 
		DynamicReportBuilder reportBuilder = new DynamicReportBuilder(jasperReportDesign, columnHeaders.size());
		        reportBuilder.addDynamicColumns();
		 
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperReportDesign);
		 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("REPORT_TITLE", "Sample Dynamic Columns Report");
		DynamicColumnDataSource pdfDataSource = new DynamicColumnDataSource(columnHeaders, rows);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, pdfDataSource);
		 
		JasperExportManager.exportReportToPdfFile(jasperPrint, "Test.pdf");
   }



    public static void main(String args[]) throws Exception{

        String inputTimeSubreport = "TimeSeriesChartSubReport.jrxml";
        String outputTimeSubreport = "TimeSeriesChartSubReport.jasper";

        String inputBarSubreport = "BarCharSubReport.jrxml";
        String outputBarSubreport = "BarCharSubReport.jasper";

        JasperCompileManager.compileReportToFile(inputTimeSubreport, outputTimeSubreport);
        JasperCompileManager.compileReportToFile(inputBarSubreport, outputBarSubreport);
 
    	JasperDesignForTemplate templace = new JasperDesignForTemplate();
        JasperDesign design = templace.design();
        Collection<JRValidationFault> faults = JasperCompileManager.verifyDesign(design);
        JasperCompileManager.compileReportToFile(design, outputjasper);

        HashMap<String, Object> params = new HashMap<String, Object>();
        
    
        EventData data = new EventData();
  	
        JRBeanCollectionDataSource beanList = new JRBeanCollectionDataSource(data.getEventData());
        JasperPrint jasperPrint = JasperFillManager.fillReport(outputjasper, params, beanList);
        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdffile));
    }
}