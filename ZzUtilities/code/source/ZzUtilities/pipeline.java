package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.FlowSvcImpl;
import com.wm.app.b2b.server.ns.Namespace;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.wm.util.JournalLogger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Iterator;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataUtil;
import com.wm.lang.flow.FlowElement;
import com.wm.lang.flow.FlowInvoke;
import com.wm.lang.flow.FlowMap;
import com.wm.lang.flow.FlowMapInvoke;
import com.wm.lang.flow.FlowMapSet;
import com.wm.lang.flow.FlowRoot;
import com.wm.lang.ns.NSNode;
// --- <<IS-END-IMPORTS>> ---

public final class pipeline

{
	// ---( internal utility methods )---

	final static pipeline _instance = new pipeline();

	static pipeline _newInstance() { return new pipeline(); }

	static pipeline _cast(Object o) { return (pipeline)o; }

	// ---( server methods )---




	public static final void documentToPipeline (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(documentToPipeline)>> ---
		// @sigtype java 3.5
		// [i] record:0:required document
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		 
			// document
			IData	document = IDataUtil.getIData( pipelineCursor, "document" );
			if ( document != null) 
			{
				IDataCursor docCursor = document.getCursor();
				while(docCursor.next()){
					
					IDataUtil.put(pipelineCursor, docCursor.getKey(), docCursor.getValue());
					
				}
				docCursor.destroy();
				
			}
		pipelineCursor.destroy();
		
		// pipeline
			
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
	
		
		
		try {
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
					
					FlowMapInvoke[]  mapInvokes = flowMap.getInvokeMaps();
					if(null != mapInvokes && mapInvokes.length > 0)
					{
						for(FlowMapInvoke mapInvoke : flowMap.getInvokeMaps())
						{
							dimensionNameExist = false;
							dimensionValueExist = false;
							isProjectCheck = false;
														
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
				}
								
				if (flowElement.getFlowType().equals("INVOKE")) 
				{					
					serviceSteps = serviceSteps + flowElement.getFlowType();
					FlowInvoke flowInvoke = (FlowInvoke) flowElement;
					
					if(flowInvoke.getService().toString().equalsIgnoreCase(tmfServiceName))
					{						
						if(flowInvoke.getInputMap().getSetMaps()!=null && flowInvoke.getInputMap().getSetMaps().length>0)
						{
							dimensionNameExist = false;
							dimensionValueExist = false;
							isProjectCheck = false;
							
							for (FlowMapSet invokeMapSet : flowInvoke.getInputMap().getSetMaps())
							{								
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
										dimensionValueExist = true;										
									}								
								}
								
								if(null!= projectName && !projectName.equalsIgnoreCase("") )
								{									
									isProjectCheck = true;									
								
									if(fieldName.equalsIgnoreCase("project"))
									{
										if(invokeMapSet.getInput().toString().equalsIgnoreCase(projectName))
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
			}
		} catch (Exception e) {
			JournalLogger.log(3, 90, 3, e.toString());
			e.printStackTrace();
		}
		
		
		return existCount;
	}
	// --- <<IS-END-SHARED>> ---
}

