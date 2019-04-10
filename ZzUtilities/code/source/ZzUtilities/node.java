package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.*;
import com.wm.app.b2b.server.Package;
import com.wm.app.b2b.server.PackageManager;
import com.wm.lang.ns.*;
import java.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class node

{
	// ---( internal utility methods )---

	final static node _instance = new node();

	static node _newInstance() { return new node(); }

	static node _cast(Object o) { return (node)o; }

	// ---( server methods )---




	public static final void getNodesByType (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getNodesByType)>> ---
		// @sigtype java 3.5
		// [i] field:1:required nodeType
		// [o] record:1:required nodeList
		// [o] - field:0:required nodeName
		// [o] - field:0:required nodeType
		IDataCursor pipelineCursor = pipeline.getCursor();
		List<String> nodeList = Arrays.asList(IDataUtil.getStringArray(pipelineCursor, "nodeType"));
		pipelineCursor.destroy();
		 
		ArrayList<IData> nodeList1 = new ArrayList<IData>();
						
		String[] packlist = ServerAPI.getEnabledPackages();
		
		for(String str : packlist)
		{
			if(!str.startsWith("Wm")){
				Package packageObj = PackageManager.getPackage(str);
				Vector<NSNode> NSNodeList = PackageManager.getNodes(packageObj);
				for(NSNode NSNode : NSNodeList)
				{				
					NSType nstype = NSNode.getNodeTypeObj();
					String type = nstype.toString();
					if(nodeList.contains(type))
					{
						IData temp = IDataFactory.create();
						IDataCursor tempCursor = temp.getCursor();
						IDataUtil.put(tempCursor, "nodeName", NSNode.getNSName().toString());
						IDataUtil.put(tempCursor,"nodeType",type);
						nodeList1.add(temp);
						
					}
				} 
			}
			
		}
		
		IDataCursor pipelineCurosr_1 = pipeline.getCursor();
		IDataUtil.put(pipelineCurosr_1, "nodeList", nodeList1.toArray(new IData[nodeList1.size()]));
		
		
		
			
		// --- <<IS-END>> ---

                
	}
}

