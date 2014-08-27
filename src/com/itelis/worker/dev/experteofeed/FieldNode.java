package com.itelis.worker.dev.experteofeed;


/**
 * 
 * @author echokodjeu
 *
 */
public class FieldNode {
	 
	private java.util.Properties attributes; 
    private java.util.Vector children; 
    private String name = ""; 
    private String value = null; 
    private int childIndex = 0; 
    //private boolean consumed = false; 
    private boolean consumed = false; 
 
    public FieldNode(String name) { 
    	super(); 
        attributes = new java.util.Properties(); 
        children = new java.util.Vector(); 
        this.name = name; 
    } 
 
    public java.util.Properties getAttributes() { 
        return attributes; 
    } 
 
    public void setAttributes(java.util.Properties attributes) { 
        this.attributes = attributes; 
    } 
 
    public java.lang.String getName() { 
        return name; 
    } 
 
    public void setName(java.lang.String name) { 
        this.name = name; 
    } 
 
    public java.lang.String getValue() { 
        return value; 
    } 
 
    public java.lang.String getValue(String def) { 
        if (value == null || value.length() == 0) return def; 
        return value; 
    } 
 
  //  public booleanboolean getValueAsBoolean() {    
    public boolean getValueAsBoolean() { 
        if (value != null && value.trim().equalsIgnoreCase("true")) return true; 
        return false; 
    } 
 
    public int getValueAsInteger() { 
        try { 
            return Integer.parseInt(value + ""); 
        } catch (Exception ex) { 
            return 0; 
        } 
    } 
 
    public void setValue(java.lang.String value) { 
        this.value = value; 
    } 
 
    //public FieldNode getChild(java.lang.String childName, booleanboolean noNull) {      
    public FieldNode getChild(java.lang.String childName, boolean noNull) { 
        java.util.Enumeration enum_children = children.elements(); 
        while (enum_children.hasMoreElements()) { 
            FieldNode cn = (FieldNode)enum_children.nextElement(); 
            if (cn.getName().equals(childName)) return cn; 
        } 
        if (noNull) { 
            FieldNode cn = new FieldNode(childName); 
            this.children.add(cn); 
            return cn; 
        } 
        return null; 
    } 
 
    public java.util.Vector getChilddren(java.lang.String childName) { 
        java.util.Vector result = new java.util.Vector(); 
        java.util.Enumeration enum_children = children.elements(); 
        while (enum_children.hasMoreElements()) { 
            FieldNode cn = (FieldNode)enum_children.nextElement(); 
            if (cn.getName().equals(childName)) { 
                result.add(cn); 
            } 
        } 
        return result; 
    } 
 
    public FieldNode getChild(java.lang.String childName) { 
        return getChild(childName, true); 
    } 
 
    public FieldNode getNextChild() { 
        if (this.getChildren().size() == 0) { 
            return new FieldNode("Unnamed"); 
        } 
        if (childIndex < 0) { 
            childIndex = 0; 
        } 
        return (FieldNode)this.getChildren().elementAt(childIndex); 
    } 
 
    public java.lang.String getAttribute(String attrName) { 
        return attributes.getProperty(attrName); 
    } 
 
    public java.lang.String getAttribute(String attrName, String def) { 
        return attributes.getProperty(attrName, def); 
    } 
 
   // public booleanboolean getAttributeAsBoolean(String attrName) {  
    public boolean getAttributeAsBoolean(String attrName) { 
        String val = attributes.getProperty(attrName); 
        if (val != null && val.trim().equalsIgnoreCase("true")) return true; 
        return false; 
    } 
 
    public int getAttributeAsInteger(String attrName) { 
        try { 
            String val = attributes.getProperty(attrName); 
            return Integer.parseInt(val + ""); 
        } catch (Exception ex) { 
            return 0; 
        } 
    } 
 
    public java.util.Vector getChildren() { 
        return children; 
    } 
 
    public void setChildren(java.util.Vector children) { 
        this.children = children; 
    } 
 
   // public booleanboolean next(String xmlPath) {  
    public boolean next(String xmlPath) { 
        if (xmlPath.startsWith("/")) { 
            xmlPath = xmlPath.substring(1); 
        } 
        if (xmlPath.indexOf("/") < 0) { 
            if (!consumed) { 
                return consumed = true; 
            } else  return false; 
        } 
        xmlPath = xmlPath.substring(xmlPath.indexOf("/")); 
        if (childIndex == 0 && getChildren().size() == 0) { 
            if (!consumed) { 
                return consumed = true; 
            } else  return false; 
        } 
        if (!getNextChild().next(xmlPath)) { 
            childIndex++; 
            if (childIndex < getChildren().size()) return getNextChild().next(xmlPath); 
        } else  { 
            return true; 
        } 
        return false; 
    } 
 
    public String getChildsPath(String xmlPath) { 
        if (xmlPath.indexOf("/") == xmlPath.lastIndexOf("/")) { 
            return ""; 
        } 
        xmlPath = xmlPath.substring(xmlPath.indexOf("/") + 1); 
        return this.getNextChild().getName() + "(" + childIndex + ")::" + this.getNextChild().getChildsPath(xmlPath); 
    } 

}
