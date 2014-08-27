package com.itelis.worker.dev.experteofeed;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.jasperreports.engine.JRDataSource;


/**
 * 
 * @author echokodjeu
 *
 */

public class JRXMLDataSource implements JRDataSource{

	 
    private FieldNode rootFieldNode = null; 
    private String rowPath = "/"; 
    private FieldNode actualPath = null; 
 
    public JRXMLDataSource(String uri, String rowPath) { 
        super(); 
        this.rowPath = rowPath; 
        try { 
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
            Document doc = db.parse(uri); 
            build(doc); 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
    } 
 
    public JRXMLDataSource(java.io.File file, String rowPath) { 
        super(); 
        this.rowPath = rowPath; 
        try { 
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
            Document doc = db.parse(file); 
            build(doc); 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
    } 
 
    public JRXMLDataSource(java.io.InputStream is, String rowPath) { 
        super(); 
        this.rowPath = rowPath; 
        try { 
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
            Document doc = db.parse(is); 
            build(doc); 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
    } 
 
    public JRXMLDataSource(FieldNode rootFieldNode, String rowPath) { 
        super(); 
        this.rowPath = rowPath; 
        this.rootFieldNode = rootFieldNode; 
    } 
 
    private void build(org.w3c.dom.Document doc) throws Exception { 
        Element element = doc.getDocumentElement(); 
        rootFieldNode = createNode(element, null); 
    } 
 
    private FieldNode createNode(Node element, FieldNode parent) throws Exception { 
        FieldNode cn = new FieldNode(element.getNodeName()); 
        NamedNodeMap attributes = element.getAttributes(); 
        for (int i = 0; i < attributes.getLength(); ++i) { 
            Node node = attributes.item(i); 
            cn.getAttributes().setProperty(node.getNodeName(), node.getNodeValue()); 
        } 
        NodeList nl = element.getChildNodes(); 
        for (int i = 0; i < nl.getLength(); ++i) { 
            Node node = nl.item(i); 
            if (node.getNodeType() == Node.ELEMENT_NODE) { 
                createNode(node, cn); 
            } else  if (node.getNodeType() == Node.CDATA_SECTION_NODE || node.getNodeType() == Node.TEXT_NODE) { 
                cn.setValue(node.getNodeValue()); 
            } 
        } 
        if (parent != null) { 
            parent.getChildren().add(cn); 
        } else  { 
            parent = cn; 
        } 
        return parent; 
    } 
 
    public Object getFieldValue(net.sf.jasperreports.engine.JRField jRField) throws net.sf.jasperreports.engine.JRException { 
        String path = jRField.getDescription(); 
        Object val = getPathValue(rootFieldNode, path); 
        { 
            return val; 
        } 
    } 
 
 //   public booleanboolean next() throws net.sf.jasperreports.engine.JRException { 
        public boolean next() throws net.sf.jasperreports.engine.JRException { 
        return rootFieldNode.next(this.rowPath); 
    } 
 
    public String getActualPath() { 
        String childPath = this.rowPath; 
        childPath = rowPath.substring(rootFieldNode.getName().length() + 1); 
        return rootFieldNode.getName() + "::" + rootFieldNode.getChildsPath(rowPath); 
    } 
 
    public static void main(String[] argv) { 
        //JRXMLDataSource ds = new JRXMLDataSource("C:\\\\test_ireport.xml", "/Sites"); 
        JRXMLDataSource ds = new JRXMLDataSource("Test_Flux_Retour_dentaire.xml", "/DOSSIER/BENEF"); 
       try { 
           System.out.println("starting"); 
         while (ds.next()) { 
            System.out.println(ds.getActualPath()); 
           } 
           System.out.println("finishing"); 
      } catch (Exception ex) { 
          ex.printStackTrace(); 
        } 
    } 
 
    private Object getPathValue(FieldNode startingNode, String path) { 
        String tag = ""; 
        if (path == null) return startingNode.getValue(); 
        if (path.startsWith("/")) path = path.substring(1); 
        String sub_path = ""; 
        if (path.indexOf("+") >= 0) { 
            sub_path = path.substring(path.indexOf("+") + 1); 
            path = path.substring(0, path.indexOf("+") + 1); 
        } 
        if (path.indexOf("/") < 0) { 
            if (path.indexOf("@") >= 0) { 
                tag = path.substring(path.indexOf("@") + 1); 
                return startingNode.getAttribute(tag); 
            } else  if (path.indexOf("*") >= 0) { 
                String childName = path.substring(path.indexOf("*") + 1); 
                FieldNode fn = new FieldNode(startingNode.getName()); 
                fn.setAttributes(startingNode.getAttributes()); 
                fn.setChildren(startingNode.getChilddren(childName)); 
                return new JRXMLDataSource(fn, "/" + startingNode.getName() + "/" + childName); 
            } else  if (path.indexOf("+") >= 0) { 
                String childToTake = sub_path; 
                childToTake = getNextNodeName(sub_path); 
                return getSubPathValue(startingNode.getChild(childToTake), sub_path); 
            } else  { 
                return startingNode.getValue(); 
            } 
        } 
        path = path.substring(path.indexOf("/") + 1); 
        path += sub_path; 
        return getPathValue(startingNode.getNextChild(), path); 
    } 
 
    private Object getSubPathValue(FieldNode startingNode, String path) { 
        String tag = ""; 
        if (path == null) return startingNode.getValue() + "[" + path + "]"; 
        if (path.startsWith("/")) path = path.substring(1); 
        if (path.indexOf("/") < 0) { 
            if (path.indexOf("@") >= 0) { 
                tag = path.substring(path.indexOf("@") + 1); 
                return startingNode.getAttribute(tag); 
            } else  if (path.indexOf("*") >= 0) { 
                String childName = path.substring(path.indexOf("*") + 1); 
                FieldNode fn = new FieldNode(startingNode.getName()); 
                fn.setAttributes(startingNode.getAttributes()); 
                fn.setChildren(startingNode.getChilddren(childName)); 
                return new JRXMLDataSource(fn, "/" + startingNode.getName() + "/" + childName); 
            } else  { 
                return startingNode.getValue(); 
            } 
        } else  { 
            path = path.substring(path.indexOf("/") + 1); 
            String childToTake = path; 
            childToTake = getNextNodeName(path); 
            return getSubPathValue(startingNode.getChild(childToTake), path); 
        } 
    } 
 
    private static String getNextNodeName(String path) { 
        if (path == null || path.length() == 0) return ""; 
        if (path.startsWith("/")) path = path.substring(1); 
        String childToTake = path; 
        if (path.indexOf("/") >= 0) { 
            childToTake = path.substring(0, path.indexOf("/")); 
        } 
        if (childToTake.indexOf("@") >= 0) { 
            childToTake = childToTake.substring(0, path.indexOf("@")); 
        } 
        if (childToTake.indexOf("*") >= 0) { 
            childToTake = childToTake.substring(0, path.indexOf("*")); 
        } 
        return childToTake; 
    } 


}
