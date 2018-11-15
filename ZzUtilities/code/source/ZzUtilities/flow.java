package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.softwareag.util.IDataMap;
import com.wm.app.b2b.server.FlowSvcImpl;
import com.wm.app.b2b.server.ns.Namespace;
import com.wm.lang.flow.*;
import com.wm.lang.ns.NSNode;
import com.wm.util.JournalLogger;
// --- <<IS-END-IMPORTS>> ---

public final class flow

{
	// ---( internal utility methods )---

	final static flow _instance = new flow();

	static flow _newInstance() { return new flow(); }

	static flow _cast(Object o) { return (flow)o; }

	// ---( server methods )---




	public static final void searchFlowService (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(searchFlowService)>> ---
		// @sigtype java 3.5
		// [i] field:0:required flowServiceName
		// [i] field:0:required searchFieldName
		// [i] field:0:required searchFieldValue
		// [o] field:0:required isFound
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	flowServiceName = IDataUtil.getString( pipelineCursor, "flowServiceName" );
		String	searchFieldName = IDataUtil.getString( pipelineCursor, "searchFieldName" );
		String	searchFieldValue = IDataUtil.getString( pipelineCursor, "searchFieldValue" );
		
		FlowRoot flowRoot = null;
		String isFound = "false";
				
		int usedCount = 0;
		
		boolean  mandatoryFieldsExist = false;
		
		if(null != flowServiceName && !flowServiceName.equalsIgnoreCase("") && null != searchFieldName && !searchFieldName.equalsIgnoreCase("") && null!=searchFieldValue && !searchFieldValue.equalsIgnoreCase(""))
		{
			mandatoryFieldsExist = true;
		}
		
		if(mandatoryFieldsExist)
		{
			NSNode nsNode = Namespace.current().getNode(flowServiceName); 		
			if (nsNode instanceof FlowSvcImpl)
			{ 	
				try {		
					FlowSvcImpl flowService = (FlowSvcImpl)nsNode; 
		
					flowRoot = flowService.getFlowRoot();
		
					FlowElement fe[] = flowRoot.getNodes();
					
					if(fe!=null && fe.length>0)
					{;
						for (FlowElement flowElement : fe) 
						{ 		
							usedCount = scanInvoke(flowElement,searchFieldName,searchFieldValue,usedCount);
						}
					}
		
					IDataUtil.put(pipelineCursor, "usedCount", Integer.toString(usedCount));
		
				} 
		
				catch (Exception e) 
				{
					e.printStackTrace();
					com.wm.util.JournalLogger.log(3, 90, 3, e);
		
				}
				if(usedCount > 0){
					isFound = "true";
				}
			} 
		
			else
			{
					
			}
		
		}
				
		IDataUtil.put(pipelineCursor, "isFound", isFound);
			
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

