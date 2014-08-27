package com.itelis.worker.dev.arsbridge;

public class FormUtils {
//	public static void getFieldIds(NameID form_name) throws ARException{
//
//		lsCriteria=new FieldListCriteria(new NameID(form_name),new Timestamp(0),FieldType.AR_DATA_FIELD);
//		arsProxy=new Proxy();
//		tbField=arsProxy.ARGetListField(ctx,lsCriteria);		
//	}
//
//	public static void getFieldKey(NameID form_name) throws ARException{
//		getFieldIds(form_name);
//		int i=0;
//		for (FieldID fieldId : tbField) {
//			i=i+1;
//			tbFieldKy[i]=new FieldKey(new NameID(form_name),new FieldID(fieldId.getValue()));
//		}
//	}
//	public static void getField(FieldKey field_key) throws ARException{
//		criteria = new FieldCriteria();
//		criteria.setRetrieveAll(true);
//		FieldFactory.findByKey(ctx, field_key, criteria);
//	}
//	public static void getFields(NameID form_name) throws ARException{
//		getFieldKey(form_name);
//		for (FieldKey field_key : tbFieldKy) {
//			getField(field_key);
//		}
//	}
//	public static void getFieldsHash(NameID form_name) throws ARException{
//		getFields(form_name);
//		Field[] field;
//		Field[] result;
//		//        for(int i = 0; i < field.length; i++){
//		//		result[field[i].getFieldID.getValue] = field[i].getName.getValue;
//		//        }
//	}
//	public static void getEntries(NameID form_name) throws ARException{
//
//		EntryListCriteria entry_list_criteria=new EntryListCriteria();
//		entry_list_criteria.setSchemaID(new NameID(form_name));
//		entry_list_criteria.setQualifier(null);
//		entry_list_criteria.setSortInfos(null);
//		entry_list_criteria.setFirstRetrieve(0);
//		entry_list_criteria.setMaxLimit(1);
//
//		int num_items=3;
//		EntryListFieldInfo[] entry_list_field_list=new EntryListFieldInfo[num_items];
//
//		String separator="";
//		int column_width=15;
//
//		entry_list_field_list[0]= new EntryListFieldInfo(new FieldID(2), column_width, separator);
//		entry_list_field_list[1]= new EntryListFieldInfo(new FieldID(7), column_width, separator);
//		entry_list_field_list[2]= new EntryListFieldInfo(new FieldID(8), column_width, separator);
//
//		EntryCriteria entry_criteria=new EntryCriteria();
//		entry_criteria.setEntryListFieldInfo(entry_list_field_list);
//
//
//		entry_list_field_list = entry_list_field_list.clone();
//
//		EntryFactory.findEntryListInfos(ctx, entry_list_criteria, entry_criteria, false, null);
//	}
//	public static void findEntrieskeys(NameID form_name, Boolean conditions){
//
//	}
//	public static void publique(NameID form_name, boolean conditions){
//
//	}
//	public static void findUniqueEntry(NameID form_name){
//
//	}
//	public static Entry getEntry(NameID form_name, EntryID entry_id){
//		Entry entry = null;
//		return entry;
//	}
//	public static void getEntryHashWithFieldsIds(NameID form_name, EntryID entry_id){
//
//	}
//	public static void getEntryHash(NameID form_name, EntryID entry_id, Boolean includesTypes){
//
//		//result[field.getFieldID.getValue] = field.getName.getValue;
//	}
//	public static EntryItem[] buildEntryItemsFromHash(int[] values_hash, Boolean force_enum_for){
//
//		FieldID field;
//		EntryItem[] entry_items = new EntryItem[values_hash.length];
//
//		return entry_items;
//
//	}
//	public static void updateEntry(NameID form_name, EntryID entry_id, int[]new_values_hash, Boolean force_enum_for) throws ARException{
//		Entry entry=getEntry(form_name,entry_id);
//		EntryItem[] updated_attributes = buildEntryItemsFromHash(new_values_hash,force_enum_for);
//		entry.setEntryItems(updated_attributes);
//		entry.store();		
//	}
//	public static EntryID create_entry(NameID form_name, int[] values_hash, Boolean force_enum_for) throws ARException{
//		EntryFactory entryFactory = EntryFactory.getFactory();
//		Entry entry = (Entry) entryFactory.newInstance();
//		EntryID entry_id=null;
//		entry.setContext(ctx);
//		entry.setSchemaID(new NameID(form_name));
//		EntryItem[] entry_items = buildEntryItemsFromHash(values_hash,force_enum_for);
//		entry.setEntryItems(entry_items);
//		entry.create();
//		return entry_id;
//	}
//	public static void delete_entry(NameID form_name, EntryID entry_id) throws ARException{
//		EntryKey entry_key=new EntryKey(new NameID(form_name),new EntryID(entry_id));
//		arsProxy.ARDeleteEntry(ctx,entry_key);
//	}

}
