package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.lang.ns.*;
import com.softwareag.util.IDataMap;
import com.wm.app.b2b.server.PackageManager;
import com.wm.app.b2b.server.ServerAPI;
import java.io.ByteArrayOutputStream;
import com.wm.util.DebugMsg;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.wm.util.JournalLogger;
import java.io.File;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
// --- <<IS-END-IMPORTS>> ---

public final class service

{
	// ---( internal utility methods )---

	final static service _instance = new service();

	static service _newInstance() { return new service(); }

	static service _cast(Object o) { return (service)o; }

	// ---( server methods )---




	public static final void getServicesByPipelineDebugProperty (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getServicesByPipelineDebugProperty)>> ---
		// @sigtype java 3.5
		// [i] field:0:required pipelnDebugOption {"save","restore(override)","restore(merge)"}
		// [o] field:1:required serviceList
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	pipelnDebugOption = IDataUtil.getString( pipelineCursor, "pipelnDebugOption" );
		pipelineCursor.destroy(); 
				 
		ArrayList<String> serviceList = new ArrayList<String>();
		 
		int pipelineDebugOptionCode = 0; 
		if(pipelnDebugOption.equalsIgnoreCase(("save")))
			pipelineDebugOptionCode=2;
		else if (pipelnDebugOption.equalsIgnoreCase(("restore(override)")))
			pipelineDebugOptionCode =3;
		else if (pipelnDebugOption.equalsIgnoreCase(("restore(merge)")))
			pipelineDebugOptionCode =4;
		else pipelineDebugOptionCode =  1;
			
		
		String[] packlist = ServerAPI.getEnabledPackages();   // retrieve enabled packages
		
		for(String str : packlist)
		{
			com.wm.app.b2b.server.Package packageObj = PackageManager.getPackage(str);
			Vector<NSNode> nodeList = PackageManager.getNodes(packageObj);
			for(NSNode node : nodeList)
			{				
				NSType nstype = node.getNodeTypeObj();
				String type = nstype.toString();
				if(type.equals("service") )   // node type service are filtered
				{
					NSService nsservice  = (NSService)node;
					int pipDebugOption = nsservice.getPipelineOption(); 
					if(pipDebugOption==pipelineDebugOptionCode)  // Service pipeline debug option is matching input pipeline debug option
						serviceList.add(node.getNSName().toString()); // Added to Service List
				}
			}
		}
		
		// converting array list to string list
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put(pipelineCursor_1, "serviceList", serviceList.toArray(new String[serviceList.size()]));
		
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static String getData(Object element)
	{
		try {
			return element.toString();
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	public static final void exportExcel(IData pipeline) throws ServiceException {
	}
		
	// --- <<IS-END-SHARED>> ---
}

