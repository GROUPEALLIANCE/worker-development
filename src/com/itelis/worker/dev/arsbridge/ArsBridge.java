package com.itelis.worker.dev.arsbridge;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.remedy.arsys.api.ARException;
import com.remedy.arsys.api.ARServerUser;
import com.remedy.arsys.api.AccessNameID;
import com.remedy.arsys.api.EntryFactory;
import com.remedy.arsys.api.Field;
import com.remedy.arsys.api.NameID;
import com.remedy.arsys.api.Timestamp;
import com.remedy.arsys.api.FieldListCriteria;
import com.remedy.arsys.api.FieldType;
import com.remedy.arsys.api.FieldFactory;
import com.remedy.arsys.api.FieldCriteria;
import com.remedy.arsys.api.FieldID;
import com.remedy.arsys.api.FieldKey;
import com.remedy.arsys.api.Constants;
import com.remedy.arsys.api.Entry;
import com.remedy.arsys.api.EntryID;
import com.remedy.arsys.api.EntryKey;
import com.remedy.arsys.api.EntryItem;
import com.remedy.arsys.api.EntryCriteria;
import com.remedy.arsys.api.Value;
import com.remedy.arsys.api.Proxy;
import com.remedy.arsys.api.UserInfo;
import com.remedy.arsys.api.UserLicenseInfo;
import com.remedy.arsys.api.Util;




/**
 * 
 * 
 * Connection a la base
 * ARS
 * @author echokodjeu
 *
 */
public class ArsBridge {
	public static ARServerUser ctx; 
	public static FieldListCriteria   lsCriteria;
	public static FieldCriteria  fdcriteria;
	public static EntryCriteria  eycriteria;
	public static Proxy arsProxy;
	public static FieldID[] tbField;
	public static FieldKey[] tbFieldKy;


	public ArsBridge(){
		//Instanciate my ARServer Object
		//Setup my authntification  credentials
		NameID formName=new NameID("ITELIS:DO");
		NameID name=new NameID("Java");
		NameID pwd=new NameID("itelis");
		lsCriteria=new FieldListCriteria(formName, new Timestamp(0), 0);
		ctx=new ARServerUser("Ruby","logeek",
				Locale.getDefault().toString(),
				"ARPRODIC");
		//ctx.setPort(64935);
		//Verify that the authentification information provided is valid
		connection();
	}

	public static void main(String[] args) throws ARException, IOException{

		//Instanciate my ARServer Object
		//Setup my authntification  credentials
		NameID formName=new NameID("ITELIS:DO");
		
		lsCriteria=new FieldListCriteria(formName, new Timestamp(0), 0);
		ctx=new ARServerUser("Ruby","logeek",
				Locale.getDefault().toString(),
				"ARPRODIC");

		//Verify that the authentification information provided is valid
		connection();

		//Output some keys information abourt the connected AR Server
		//showARSystemDetails();

		//Output information about license
		licenseMonitor();

		//look up field name
		//Agent=lookupFieldName("ITELIS:DO",new Long(536870932));
		//System.out.println(Agent);

		//Value fval=new Value("test9999");
		//createEntryItems("ITELIS:DEMAT",new Long(536870913),fval);
		//HashMap<Long,Value> mapping=new HashMap<Long,Value>();
		//mapping.put(new Long(536870913), fval);
        //mapping.put(new Long(536871067), fval);
		//createHashEntryItems("ITELIS:DEMAT",mapping);
		
		//System.out.println(getEntryItems("ITELIS:PARTENAIRES", new Long(536870914), "000000000022374"));
				
		//Log out from AR Server when completed
		deconnection();

	}

	public static void connection(){
		try {
			ctx.login();
			//ctx.verifyUser();
		}
		catch(ARException e){
			System.out.println(e.getMessage());
		}

	}
	public static void deconnection() throws ARException{
		ctx.logout();
	}

	/**
	 * 
	 * @param form
	 * @param fid
	 * @return
	 */
	public static String lookupFieldName(String form,Long fid){  
		String fname = "notfound";  
		Field[] fieldList = null;
		if((fieldList==null)||(!form.equalsIgnoreCase(form))) {  
			fieldList = getFormFields(form);  
		}  
		for (int i = 0; i < fieldList.length; i++) {  
			Long lookupFid = new Long(fieldList[i].getFieldID().toString());  
			if(lookupFid.equals(fid)) {  
				fname = fieldList[i].getName().toString();  
				break;  
			}  
		}  
		return fname;  
	}

	public static void createEntryItems(String form,Long fid, Value val) throws ARException{


		Field[] fieldList = null;
		FieldID fieldId=new FieldID();
		Entry entry = (Entry) EntryFactory.getFactory().newInstance();
		entry.setContext(ctx);
		entry.setSchemaID(new NameID(form));

		// create entryItems, array of field values to create entry record.
		EntryItem[] s_entryList = null;

		// Create a new array for number of fields for entryitems storage.
		s_entryList = new EntryItem[4];

		if((fieldList==null)||(!form.equalsIgnoreCase(form))) {
			fieldList = getFormFields(form);

		}
		for (int i = 0; i < fieldList.length; i++) {
			Long lookupFid = new Long(fieldList[i].getFieldID().toString());
			if(lookupFid.equals(fid)) {
				fieldId = fieldList[i].getFieldID();
				break;
			}
		}


		s_entryList[3]= new EntryItem(fieldId,val);

		if ( s_entryList != null )
		{
			entry.setEntryItems( s_entryList );
			entry.create( );
			entry.store();
		}


	}
	public static void createHashEntryItems(String form,HashMap<Long,Value> map) throws ARException{

		HashMap<Long,Value> mapping=new HashMap<Long,Value>();
                mapping.putAll(map);
		Field[] fieldList = null;
		FieldID fieldId=new FieldID();
		Entry entry = (Entry) EntryFactory.getFactory().newInstance();
		entry.setContext(ctx);
		entry.setSchemaID(new NameID(form));

		// create entryItems, array of field values to create entry record.
		EntryItem[] s_entryList = null;

		// Create a new array for number of fields for entryitems storage.
		s_entryList = new EntryItem[25];

		if((fieldList==null)||(!form.equalsIgnoreCase(form))) {
			fieldList = getFormFields(form);

		}
		int j=0;
		for(Long fid:mapping.keySet()){
			j++;
		for (int i = 0; i < fieldList.length; i++) {
			Long lookupFid = new Long(fieldList[i].getFieldID().toString());
			if(lookupFid.equals(fid)) {
				fieldId = fieldList[i].getFieldID();
				break;
			}
		}
		
		s_entryList[j]= new EntryItem(fieldId,mapping.get(fid));
		}
		if ( s_entryList != null )
		{
			entry.setEntryItems( s_entryList );
			entry.create( );
			entry.store();
		}


	}
	/**
	 * 
	 * @param form
	 * @param fid
	 * @param val
	 * @param numero
	 * @throws ARException
	 */
	public static  void setEntryItems(String form,Long fid,Value val,String numero) throws ARException{  


		Field[] fieldList = null;
		FieldID fieldId=new FieldID();
		Entry entry = (Entry) EntryFactory.getFactory().newInstance();
		entry.setContext(ctx);
		entry.setSchemaID(new NameID(form));

		// create entryItems, array of field values to create entry record.
		EntryItem[] s_entryList = null;

		// Create a new array for number of fields for entryitems storage.
		s_entryList = new EntryItem[3];

		if((fieldList==null)||(!form.equalsIgnoreCase(form))) {
			fieldList = getFormFields(form);

		}
		for (int i = 0; i < fieldList.length; i++) {
			Long lookupFid = new Long(fieldList[i].getFieldID().toString());
			if(lookupFid.equals(fid)) {
				fieldId = fieldList[i].getFieldID();
				break;
			}
		}

		//Sets for num. of fields to change for entrylist loop:000000000000001

		EntryKey entryKey = new EntryKey(new NameID(form), new EntryID(numero));

		entry = EntryFactory.findByKey(ctx, entryKey, null);



		s_entryList[2]= new EntryItem(fieldId,val);

		if ( s_entryList != null )
		{
			entry.setEntryItems( s_entryList );
			//entry.create( );
			entry.store();
		}

	}

	/**
	 * 
	 * @param form
	 * @param fid
	 * @param val
	 * @param numero
	 * @throws ARException
	 */
	public static  String getEntryItems(String form,Long fid,String numero) throws IOException, ARException{  

		HashMap<Long, Value> fieldValue=new HashMap<Long, Value>(); 
		Field[] fieldList = null;
		FieldID  fieldId=new FieldID();
		Entry entry = (Entry) EntryFactory.getFactory().newInstance();
		entry.setContext(ctx);
		entry.setSchemaID(new NameID(form));

		// create entryItems, array of field values to create entry record.
		EntryItem[] s_entryList = null;

		// Create a new array for number of fields for entryitems storage. 
		s_entryList = new EntryItem[3];   

		if((fieldList==null)||(!form.equalsIgnoreCase(form))) {  
			fieldList = getFormFields(form);

		} 
		//Controle
		for (int i = 0; i < fieldList.length; i++) {  
			Long lookupFid = new Long(fieldList[i].getFieldID().toString());  
			if(lookupFid.equals(fid)) {  
				fieldId = fieldList[i].getFieldID();
				break;  
			}  
		}  

		EntryKey entryKey = new EntryKey(new NameID(form), new EntryID(numero));

		entry = EntryFactory.findByKey(ctx, entryKey, null);

		s_entryList = entry.getEntryItems(); 

		//Mapping
		//          for(int i = 0;i<s_entryList.length;++i)   
		//          {   
		//            Value val = new Value();
		//            val=s_entryList[i].getValue(); 
		//            
		//            for (int j = 0; j < fieldList.length; j++) { 
		//            	
		//            	   Long lookupFid = new Long(fieldList[j].getFieldID().toString()); 
		//            	
		//                    fieldValue.put(lookupFid,val); 
		//            }
		//         }   
		fieldValue=(HashMap<Long, Value>) toEntryMap(entry);

		//Recuperation 
		String vlue="";
		for (Long fdx:fieldValue.keySet()){ 
			if((fid.equals(fdx))){
				if ((fieldValue.get(fdx)!=null)&&(fieldValue!=null))
					vlue=new String(fieldValue.get(fdx).toString());	

			}		
		}
		EntryFactory.getFactory().releaseInstance(entry);   
		return vlue;   

	}
	public static Map<Long, Value> toEntryMap(Entry entry) {
		Map<Long, Value> entryMap = new HashMap<Long, Value>();
		return toEntryMap(entry, entryMap);
	}
	public static Map<Long, Value> toEntryMap(Entry entry, Map<Long, Value> entryMap) {
		EntryItem[] items = entry.getEntryItems();
		for (EntryItem item : items) {
			entryMap.put(item.getFieldID().getValue(), item.getValue());
		}
		if (entry.getEntryID() != null) {
			entryMap.put(1L, new Value(entry.getEntryID().toString()));
		}
		return entryMap;
	}
	public static Field[] getFormFields(String form) {  
		// set the session instance to this form name  
		Field[] fieldList=null;
		FieldFactory fieldFactory = FieldFactory.getFactory();  
		Timestamp timestamp = new Timestamp(0);  
		// If you want to construct Field objects, you have to go to the   
		// factory.  It's the factory's findObjects() method we want.  
		NameID schemaName = new NameID(form);  

		// We'd like all fields  
		FieldCriteria fCrit = new FieldCriteria();  
		fCrit.setRetrieveAll(true);  
		// We'd like all types of fields  
		FieldListCriteria fListCrit = new FieldListCriteria(  
				schemaName, timestamp,   
				FieldType.AR_ALL_FIELD);  
		// This should load the array with all fields on Group  
		try {  
			fieldList = fieldFactory.findObjects(ctx,fListCrit, fCrit);  
		} catch (ARException e) {  
			e.printStackTrace();  
		}  
		return fieldList;  
	}   

	public static void showARSystemDetails() throws ARException{
		System.out.println("Connected to AR Server" + ctx.getServer()+ " Version " + ctx.getVersionStringFromServer());
		System.out.println("Listed all connected users and last accessed time");

		AccessNameID name=ctx.getUser();
		System.out.println(name);

		UserInfo users []=Util.ARGetListUser(ctx, Constants.AR_USER_LIST_CURRENT,
				(Timestamp)null);

		for (UserInfo user : users) {

			System.out.println("   " + user.getUserName() + " - "

		            + user.getLastAccessTime().toDate());

		}

	}

	public void ARExceptionHandler(ARException e, String errMessage){
		System.out.println(errMessage);
		System.out.print("Stack Trace:");
		e.printStackTrace();
	}


	public static void licenseMonitor() throws ARException{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy MM dd HH:mm"); 

		UserInfo users []=Util.ARGetListUser(ctx, Constants.AR_USER_LIST_CURRENT,
				(Timestamp)null);


		System.out.println(dateFormat.format(now)); 

		if(users!=null){
			System.out.print(" --");
			for (UserInfo thisUser : users){ 
				System.out.print(" " + thisUser.getUserName()); 
				UserLicenseInfo[] licenses = thisUser.getLicenseInfo(); 
				System.out.print(" --");
				for (UserLicenseInfo thisLicense : licenses){
					if(thisLicense.getLicenseTag()==Constants.AR_LICENSE_TAG_WRITE){
						switch(thisLicense.getCurrentLicenseType()){
						case Constants.AR_LICENSE_TYPE_NONE:
							System.out.println("READ"); break;
						case Constants.AR_LICENSE_TYPE_FLOATING: 
							System.out.println("FLOATING"); break;
						case Constants.AR_LICENSE_TYPE_FIXED: 
							System.out.println("FIXED"); break;
						case Constants.AR_LICENSE_TYPE_FIXED2: 
							System.out.println("FIXED2"); break;
						}
					}
				}
				System.out.print("--");
			} 

		}
	}
}
