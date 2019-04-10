package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.JournalLogger;
import com.wm.app.b2b.server.FlowManager;
import com.wm.app.b2b.server.FlowSvcImpl;
import com.wm.app.b2b.server.BaseService;
import com.wm.app.b2b.server.ServiceSetupException;
import com.wm.app.b2b.server.ns.NSNodeUtil;
import com.wm.app.b2b.server.ns.Namespace;
import java.util.ArrayList;
import java.util.Vector;
import com.softwareag.util.IDataMap;
import com.wm.lang.flow.FlowElement;
import com.wm.lang.flow.FlowInvoke;
import com.wm.lang.flow.FlowMap;
import com.wm.lang.flow.FlowMapInvoke;
import com.wm.lang.flow.FlowMapSet;
import com.wm.lang.flow.FlowRoot;
import com.wm.lang.flow.FlowSequence;
import com.wm.lang.flow.IDataWmPathProcessor;
import com.wm.lang.ns.NSName;
import com.wm.lang.ns.NSNode;
import com.wm.lang.ns.NSPackage;
import com.wm.lang.ns.WmPathItem;
// --- <<IS-END-IMPORTS>> ---

public final class TMF

{
	// ---( internal utility methods )---

	final static TMF _instance = new TMF();

	static TMF _newInstance() { return new TMF(); }

	static TMF _cast(Object o) { return (TMF)o; }

	// ---( server methods )---




	public static final void searchFlowServiceForTMFSettings (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(searchFlowServiceForTMFSettings)>> ---
		// @sigtype java 3.5
		// [i] field:0:required flowServiceName
		// [i] field:0:required dimensionName
		// [i] field:0:required dimensionValue
		// [i] field:0:required tmfServiceName
		// [i] field:0:required projectName
		// [o] field:0:required isExist
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	flowServiceName = IDataUtil.getString( pipelineCursor, "flowServiceName" );
		String	dimensionName = IDataUtil.getString( pipelineCursor, "dimensionName" );
		String	dimensionValue = IDataUtil.getString( pipelineCursor, "dimensionValue" );
		String	tmfServiceName = IDataUtil.getString( pipelineCursor, "tmfServiceName" );
		String	projectName = IDataUtil.getString( pipelineCursor, "projectName" );
		
		pipelineCursor.destroy();
		
		int usedCount = 0;		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		
		if(null != dimensionName && !dimensionName.equalsIgnoreCase("") && null != dimensionValue && !dimensionValue.equalsIgnoreCase(""))
		{
			NSNode nsNode = Namespace.current().getNode(flowServiceName); 		
			if (nsNode instanceof FlowSvcImpl)
			{ 
				try {		
					FlowSvcImpl flowService = (FlowSvcImpl)nsNode; 
					FlowRoot flowRoot = flowService.getFlowRoot();
					FlowElement fe[] = flowRoot.getNodes();
				
					if(fe!=null && fe.length>0)
					{
						for (FlowElement flowElement : fe) 
						{ 						
							com.wm.util.JournalLogger.log(3, 90, 3, "in first for");
							usedCount = scanInvoke(flowElement,projectName,dimensionName,dimensionValue,tmfServiceName,usedCount);
							com.wm.util.JournalLogger.log(3, 90, 3, "inside flow elemt last step");
						}
					}
				
					IDataUtil.put(pipelineCursor_1, "existCount", Integer.toString(usedCount));
		
				} 
		
				catch (Exception e) 
				{
					e.printStackTrace();
					com.wm.util.JournalLogger.log(3, 90, 3, e);
				}
			} 
		
			else
			{
				IDataUtil.put(pipelineCursor_1, "error", flowServiceName + "is not a valid service");
			}
		
		}
		else
		{
			IDataUtil.put(pipelineCursor_1, "error", "Dimension Name and Dimenison value are null");
		}
		
		
		
		
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	
	
	public static int scanInvoke(FlowElement flowElement, String projectName, String dimensionName, String dimensionValue, String tmfServiceName, int count) 
	 {
		String serviceSteps="";
		boolean dimensionNameExist = false;
		boolean dimensionValueExist = false;
		boolean projectNameExist = false;
		boolean isProjectCheck = false;
		int existCount = count;
	
		
		if (flowElement.getFlowType().equals("SEQUENCE") || flowElement.getFlowType().equals("BRANCH") || flowElement.getFlowType().equals("LOOP")|| flowElement.getFlowType().equals("RETRY")) 
		{
			if(flowElement.getNodes()!=null && flowElement.getNodes().length>0)
			{
				for (FlowElement flowElement2 : flowElement.getNodes())
				{
					serviceSteps = serviceSteps + flowElement2.getFlowType();
					existCount = scanInvoke(flowElement2, projectName,dimensionName, dimensionValue,tmfServiceName, existCount);
				
				}
			}
		} 
		else 
		{			
			if (flowElement.getFlowType().equals("MAP")) 
							
			{					
				serviceSteps = serviceSteps + flowElement.getFlowType();
				FlowMap flowMap = (FlowMap) flowElement;
				
				for(FlowMapInvoke mapInvoke : flowMap.getInvokeMaps())
				{
					
					if(mapInvoke.getService().toString().equalsIgnoreCase(tmfServiceName))
					{
						
						
						for(FlowMapSet  mapSet : mapInvoke.getInputMap().getSetMaps())
						{	
							
							String fieldName = mapSet.getField().substring(mapSet.getField().lastIndexOf("/")+1);
							fieldName = fieldName.substring(0,fieldName.indexOf(";"));
							if(fieldName.equalsIgnoreCase("dimension_name"))
							{
								if(mapSet.getInput().toString().equalsIgnoreCase(dimensionName))
								{
									dimensionNameExist =true;
								}
							
							}
							if(fieldName.equalsIgnoreCase("dimension_value"))
							{
								if(mapSet.getInput().toString().equalsIgnoreCase(dimensionValue))
								{
									dimensionValueExist =true;
								}
							
							}
							
							if(null!= projectName && !projectName.equalsIgnoreCase("") )
							{
								isProjectCheck = true;
								if(fieldName.equalsIgnoreCase("project"))
								{
									if(mapSet.getInput().toString().equalsIgnoreCase(projectName))
									{
										projectNameExist = true;
									}
								
								}
							}
						}
						
						if(!isProjectCheck)
						{
							if(dimensionNameExist && dimensionValueExist)
							{
								existCount++;
							}
						}
						else if(isProjectCheck){
							
							if(dimensionNameExist && dimensionValueExist && projectNameExist)
							{
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
				//JournalLogger.log(3, 90, 3, flowInvoke.getService().toString());
				//JournalLogger.log(3, 90, 3, flowInvoke.getInputMap());
				if(flowInvoke.getService().toString().equalsIgnoreCase(tmfServiceName))
				{
					if(flowInvoke.getInputMap().getSetMaps()!=null && flowInvoke.getInputMap().getSetMaps().length>0)
					{
						for (FlowMapSet invokeMapSet : flowInvoke.getInputMap().getSetMaps())
						{						
							com.wm.util.JournalLogger.log(3, 90, 3, "inside flow map invoke 1" + invokeMapSet.getDisplayType());
							String fieldName = invokeMapSet.getField().substring(invokeMapSet.getField().lastIndexOf("/")+1);
							fieldName = fieldName.substring(0,fieldName.indexOf(";"));
							if(fieldName.equalsIgnoreCase("dimension_name"))
							{
								if(invokeMapSet.getInput().toString().equalsIgnoreCase(dimensionName))
								{
									dimensionNameExist =true;
								}
							
							}
							if(fieldName.equalsIgnoreCase("dimension_value"))
							{
								if(invokeMapSet.getInput().toString().equalsIgnoreCase(dimensionValue))
								{
									dimensionValueExist =true;
								}
							
							}
						}
						
						if(dimensionNameExist && dimensionValueExist)
						{
							existCount++;
						}
					}
				}
				
			}						
		}
		
		
		return existCount;
	}
	// --- <<IS-END-SHARED>> ---
}

