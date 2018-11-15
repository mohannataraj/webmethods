package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.ArrayList;
import java.util.List;
import com.softwareag.util.IDataMap;
import com.wm.app.b2b.server.FlowSvcImpl;
import com.wm.app.b2b.server.ns.NSDependencyManager;
import com.wm.app.b2b.server.ns.Namespace;
import com.wm.lang.flow.*;
import com.wm.lang.ns.DependencyManager;
import com.wm.lang.ns.NSNode;
import com.wm.util.JournalLogger;
// --- <<IS-END-IMPORTS>> ---

public final class ns

{
	// ---( internal utility methods )---

	final static ns _instance = new ns();

	static ns _newInstance() { return new ns(); }

	static ns _cast(Object o) { return (ns)o; }

	// ---( server methods )---




	public static final void getDependents (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getDependents)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [o] field:1:required dependentList
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		String	serviceNSname = IDataUtil.getString( pipelineCursor, "serviceName" );
		String dependentService = "";
		List<String> stringList = new ArrayList<String>();
		pipelineCursor.destroy(); 				
		NSNode nsNodeObj = Namespace.current().getNode(serviceNSname);
		DependencyManager dm = NSDependencyManager.current();
		IData dependent = dm.getDependent(nsNodeObj, null);
		
		
		if ( dependent != null)
		{
			IDataCursor dependentCursor = dependent.getCursor();
			IData[] referencedBy = IDataUtil.getIDataArray(dependentCursor,"referencedBy");
			for ( int i = 0; i < referencedBy.length; i++ ) 
			{
				IDataCursor referencedByCursor = referencedBy[i].getCursor(); 
				referencedByCursor.first(); 						
				dependentService = IDataUtil.getString( referencedByCursor, "name" );
				int index =  dependentService.indexOf("/");
				stringList.add(dependentService.substring(index+1));
				referencedByCursor.destroy();
			}		 
		}
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();				
		IDataUtil.put( pipelineCursor_1, "dependentList",(String[]) stringList.toArray(new String[stringList.size()])); 
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getNode (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getNode)>> ---
		// @sigtype java 3.5
		// [i] field:0:required name
		// [o] record:0:required node
		// pipeline
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();		
		String nodeName = IDataUtil.getNonEmptyString(pipelineCursor, "name");
		
		if(nodeName != null){ 
			NSNode nsNode = Namespace.current().getNode(nodeName); 
			IData node = nsNode.getAsData();
			
			IDataUtil.put( pipelineCursor, "node", node );
			pipelineCursor.destroy();
		}
		
		
		
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static int scanInvoke(FlowElement flowElement, String searchFieldName, String searchFieldValue, int count) 
	 {
		String serviceSteps="";
		boolean searchFieldNameExist = false;
		boolean searchFieldValueExist = false;
		int existCount = count;
			
		
		try {
			if (flowElement.getFlowType().equals("SEQUENCE") || flowElement.getFlowType().equals("BRANCH") || flowElement.getFlowType().equals("LOOP")|| flowElement.getFlowType().equals("RETRY")) 
			{
				if(flowElement.getNodes()!=null && flowElement.getNodes().length>0)
				{
					for (FlowElement flowElement2 : flowElement.getNodes())
					{
						serviceSteps = serviceSteps + flowElement2.getFlowType();
						existCount = scanInvoke(flowElement2, searchFieldName,searchFieldValue, existCount);
					
					}
				}
			} 
			else 
			{			
				if (flowElement.getFlowType().equals("MAP")) 
				{						
					serviceSteps = serviceSteps + flowElement.getFlowType();
					FlowMap flowMap = (FlowMap) flowElement;
					
					FlowMapInvoke[]  mapInvokes = flowMap.getInvokeMaps();
					if(null != mapInvokes && mapInvokes.length > 0)
					{
						for(FlowMapInvoke mapInvoke : flowMap.getInvokeMaps())
						{
							searchFieldNameExist = false;
							searchFieldValueExist = false;																			
														
								for(FlowMapSet  mapSet : mapInvoke.getInputMap().getSetMaps())
								{	
									String fieldName = mapSet.getField().substring(mapSet.getField().lastIndexOf("/")+1);
									fieldName = fieldName.substring(0,fieldName.indexOf(";"));
																	
									if(fieldName.equalsIgnoreCase(searchFieldName))
									{
										if(mapSet.getInput().toString().equals(searchFieldValue))
										{											
											searchFieldValueExist =true;
											existCount++;
										}									
									}						
								}						
						}
					}
					
					FlowMapSet[] mapSets = flowMap.getSetMaps();
					if(null != mapSets && mapSets.length > 0){
						
						for(FlowMapSet mapSet : mapSets){
							String fieldName = mapSet.getField().substring(mapSet.getField().lastIndexOf("/")+1);
							fieldName = fieldName.substring(0,fieldName.indexOf(";"));
							
							if(fieldName.equalsIgnoreCase(searchFieldName))
							{
								if(mapSet.getInput().toString().equals(searchFieldValue))
								{											
									searchFieldValueExist =true;
									existCount++;
								}									
							}	
						}
						
						
					}
				}
								
				if (flowElement.getFlowType().equals("INVOKE")) 
				{					
						serviceSteps = serviceSteps + flowElement.getFlowType();
						FlowInvoke flowInvoke = (FlowInvoke) flowElement;
											
						if(flowInvoke.getInputMap().getSetMaps()!=null && flowInvoke.getInputMap().getSetMaps().length>0)
						{
							searchFieldNameExist = false;
							searchFieldValueExist = false;							
							
							for (FlowMapSet invokeMapSet : flowInvoke.getInputMap().getSetMaps())
							{								
								String fieldName = invokeMapSet.getField().substring(invokeMapSet.getField().lastIndexOf("/")+1);
								fieldName = fieldName.substring(0,fieldName.indexOf(";"));
																
								if(fieldName.equalsIgnoreCase(searchFieldName))
								{
									if(invokeMapSet.getInput().toString().equals(searchFieldValue))
									{										
										searchFieldValueExist =true;		
										existCount++;
									}								
								}																		
							}							
						}
						
						if(flowInvoke.getOutputMap().getSetMaps()!=null && flowInvoke.getOutputMap().getSetMaps().length >0){
							
							searchFieldNameExist = false;
							searchFieldValueExist = false;							
							
							for (FlowMapSet invokeOutputMapSet : flowInvoke.getOutputMap().getSetMaps())
							{								
								String fieldName = invokeOutputMapSet.getField().substring(invokeOutputMapSet.getField().lastIndexOf("/")+1);
								fieldName = fieldName.substring(0,fieldName.indexOf(";"));
															
								if(fieldName.equalsIgnoreCase(searchFieldName))
								{
									if(invokeOutputMapSet.getInput().toString().equals(searchFieldValue))
									{										
										searchFieldValueExist =true;	
										existCount++;
									}								
								}																		
							}
							
						}		
										
				}						
			}
		} catch (Exception e) {
			JournalLogger.log(3, 90, 3, e.toString());
			e.printStackTrace();
		}
		
		
		return existCount;
	}
	// --- <<IS-END-SHARED>> ---
}

