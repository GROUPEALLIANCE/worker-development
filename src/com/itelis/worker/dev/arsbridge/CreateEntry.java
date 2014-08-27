package com.itelis.worker.dev.arsbridge;

/***	create_entry.java

(Very)Basic working demonstration code of how to use the 
AR System Java API to create and save a form entry record.

Please review, many variables below need to be changed for your 
specific environment.

Tom Jamate, tomas <AT> oit.umass.edu  Feb 17, 2003.		

To compile, change for your environment:
export set LD_LIBRARY_PATH=..../api/lib/

javac -verbose -classpath .:\
.../api/lib/arapi50.jar:\
.../api/lib/jlicapi50.jar:\
.../api/lib/arutil50.jar:\
/.../api/lib create_entry.java


LICENSE INFO
Copyright (c) 2003 tomas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without
limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom
the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
IN THE SOFTWARE.

 ***/

import java.io.*;
import java.util.*;
import com.remedy.arsys.api.*;

class CreateEntry
{
	public static void main (String args [])
	{
		//Change for your environment.
		String server_name = "enter rem srv name here";
		String pswd = "change to your pwd";
		String uname = "Demo";

		//Name of form/schema to change.
		String schema_string = "enter schema to change";


		//Value to set for short desc field of new entry. Change to suit.  
		String short_desc_string = " howdy...";

		int tcpport_int = 0;
		int rpcnum_int = 0;


		//************ START ARS LOGIN AND CONTEXT SESSION VAR CHECK *****

		// Setup and init connect vars.
		ARServerUser context = null;

		// From login vars, setup context for login info.
		context= new  ARServerUser(uname,pswd,"",server_name);

		// Set ARS TCPIP server port number:
		try {
			Util.ARSetServerPort( context, 
					new NameID( server_name), tcpport_int, rpcnum_int );
		} catch( ARException e ) {
			System.out.println("<BR> AR ServerPort Error. "); 
		}

		// Check and verify user login:
		try {
			// If User Pass/Acct name is wrong, this gens an error.
			context.login();
			System.out.println( " Login successful.");

		} catch( ARException e ) {

			System.out.println( "Login Error");

			//This return end all futher processing and exits...
			System.exit(1); 
		}
		//************ END ARS LOGIN AND CONTEXT SESSION VAR CHECK *****



		//************ START Setup fields and form object to save/store as new entry.

		// Create Entry, form record object. 
		Entry entry=null;
		try {


			//Create/instantiate a new entry factory object.

			EntryFactory entryMan = EntryFactory.getFactory( );
			entry=(Entry)entryMan.newInstance( );
			entry.setContext(context);


			//Create schema name object.
			NameID Schema_name =  new SchemaKey( schema_string );

			entry.setSchemaID(Schema_name);

			// Need to know which required fields you need to submit, and field ids.
			// For the purposes of this demo, we'll use the following:
			// submitter = 2
			// status = 7 
			// short desc = 8
			// 

			// create entryItems, array of field values to create entry record.	
			EntryItem[] s_entryList = null;

			// Create a new array for number of fields for entryitems storage. 
			s_entryList = new EntryItem[3];   

			//Sets for num. of fields to change for entrylist loop
			int numItems = 0;


			// ********* BEGIN Set status field.
			// status = 7

			FieldID fieldId = new FieldID( 7 );

			//You MUST pass corrent datatype for field
			Value val =  new Value( 2 );               

			s_entryList[numItems]= new EntryItem(fieldId,val);

			// Increment field value counter.
			numItems = numItems + 1;

			// *********** END Set status field.


			// ********* BEGIN Set submitter field.
			// submitter = 2

			fieldId = new FieldID(2);

			//You MUST pass corrent datatype for field
			val =  new Value( "bonzo" );               

			s_entryList[numItems]= new EntryItem(fieldId,val);

			// Increment field value counter.
			numItems = numItems + 1;

			// *********** END Set submitter field.


			// ********* BEGIN Set short desc field.
			// short desc = 8

			fieldId = new FieldID( 8 );

			//You MUST pass corrent datatype for field
			val =  new Value( short_desc_string );               

			s_entryList[numItems]= new EntryItem(fieldId,val);

			// Increment field value counter.
			numItems = numItems + 1;

			// *********** END Set short desc field.


			// Store/save Create entry

			if ( s_entryList != null )
			{
				entry.setEntryItems( s_entryList );
				entry.create( );
				System.out.println(" post create... " );

			} else {
				System.out.println(" s_entrylist null... " );

			}

			// Was there an error ?? Check status message.
			StatusInfo[] statusList = context.getLastStatus( );

			if( statusList == null || statusList.length == 0)
			{
				System.out.println("Thank You. Ticket Save Complete." );
				System.out.println("\n New Ticket number is: "
						+ entry.getEntryID( ).toString( ) );

			} else {
				System.out.println(" AR status length = " +  statusList.length );
				System.out.println( "Get error message: " + statusList[0].getMessageText() );
			}

			// Release objs 
			entryMan.releaseInstance(entryMan);

		}
		catch( ARException e )
		{
			System.out.println("Error ARException... ");
			String	 message = e.toString( );

			if( message != null && message.length( ) > 0 )
			{
				System.out.println("<BR>" +  message );
			}
			else
			{
				System.out.println("<BR>AR ERROR, but no message... " );
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("<BR>ERROR, Array out of bounds <BR>" + e.getMessage() );
		} catch(Exception e )
		{
			System.out.println("<BR>Gen. Exception ERROR: " + e.getMessage() );
		}
		// **** END TRY..CATCH

		//logout.
		context.logout();
		System.out.println("Program end." ); 

	} 
}
//******************** END MAIN