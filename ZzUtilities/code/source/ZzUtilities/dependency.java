package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.ArrayUtil;
import com.wm.util.List;
import com.wm.app.b2b.server.ns.NSDependencyManager;
import com.wm.lang.flow.FlowElement;
import com.wm.lang.flow.FlowElementSimple;
import com.wm.lang.flow.FlowPathInfo;
import com.wm.lang.flow.FlowRoot;
import com.wm.lang.flow.FlowSequence;
import com.wm.lang.ns.DependencyManager;
import com.wm.lang.ns.NSName;
import com.wm.lang.ns.NSNode;
import com.wm.lang.ns.Namespace;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import com.softwareag.g11n.util.ArrayUtils;
import com.wm.app.b2b.server.BaseService;
import com.wm.app.b2b.server.FlowSvcImpl;
// --- <<IS-END-IMPORTS>> ---

public final class dependency

{
	// ---( internal utility methods )---

	final static dependency _instance = new dependency();

	static dependency _newInstance() { return new dependency(); }

	static dependency _cast(Object o) { return (dependency)o; }

	// ---( server methods )---




	public static final void deleteNodeByPath (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(deleteNodeByPath)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [i] field:0:required nodePath
		// [o] field:0:required isDeleted
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	serviceName = IDataUtil.getString( pipelineCursor, "serviceName" );
			String	nodePath = IDataUtil.getString( pipelineCursor, "nodePath" );
		pipelineCursor.destroy();
		
		 com.wm.app.b2b.server.ns.Namespace ns = com.wm.app.b2b.server.ns.Namespace.current();
		 NSNode node = ns.getNode(NSName.create(serviceName));
		 DependencyManager dm = NSDependencyManager.current();
		 
		 
		 IData path = dm.getInvalidPaths(node);
			IDataUtil.put( pipelineCursor, "invalidPath", path );
		 try {
			dm.deleteReference(node);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		   FlowSvcImpl flowSvc = null;
			FlowRoot flowRoot = null;
			
			
			
			
			com.wm.app.b2b.server.ns.Namespace nss = com.wm.app.b2b.server.ns.Namespace.current();
			
			IDataUtil.put( pipelineCursor, "namespace " , nss);
			
			com.wm.util.List list = new com.wm.util.List();
			
			com.wm.app.b2b.server.ns.Namespace.current();
			BaseService bs = com.wm.app.b2b.server.ns.Namespace.getService(NSName.create(serviceName));
			flowSvc =  (FlowSvcImpl) com.wm.app.b2b.server.ns.Namespace.getService(NSName.create(serviceName));
			flowRoot = flowSvc.getFlowRoot();
			FlowElement[] arrayOfFlowElement = flowRoot.getNodes();
			FlowPathInfo flowPath =(FlowPathInfo) FlowPathInfo.current();
			
			
			
			//FlowElement newElement = arrayOfFlowElement[0].getNodeAt(0);
			
			
			
			for(int i=0 ; i < arrayOfFlowElement.length;i++)
			{
				
				
				
				//IDataUtil.put( pipelineCursor, "flowPath at " + i, flowPath.createPath(arrayOfFlowElement[i]));
				if(arrayOfFlowElement[i].getDisplayType().equalsIgnoreCase("SEQUENCE"))
				{
				
					//IDataUtil.put( pipelineCursor, "flowPath at " + i, arrayOfFlowElement[i].getNodeAt(0));
				}
				
				
				
				
			}
			
			//IDataUtil.put( pipelineCursor, "flowPath at " , newElement);
			
			//FlowSequence flowSeq = (FlowSequence) arrayOfFlowElement[1].getNodeAt(0);
		//	flowSeq.getParent();
			
			//IDataUtil.put( pipelineCursor, "flowPath parent ", flowSeq.getParent());
			//IDataUtil.put( pipelineCursor, "flowPath seq ", flowPath.createPath(flowSeq));
			
			//flowSvc.setFlowRoot(flowRoot);
			
			//FlowElement[] newFlowElement = arrayOfFlowElement[1].getNodes();
			
			
			
			/*for(int j=0;j< newFlowElement.length ; j++)
			{
				
				IDataUtil.put( pipelineCursor, "flowPath new at " + j, flowPath.createPath(newFlowElement[j]));
			}
			*/
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "isDeleted", "isDeleted" );
		pipelineCursor_1.destroy();
		
		
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void removeUnresolvedRefServices (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeUnresolvedRefServices)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [i] field:0:required referenceServiceName
		// [o] field:1:required removedReferences
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	serviceName = IDataUtil.getString( pipelineCursor, "serviceName" );
		String	referenceServiceName = IDataUtil.getString( pipelineCursor, "referenceServiceName" );
		//pipelineCursor.destroy();
		
		FlowSvcImpl flowSvc = null;
		FlowRoot flowRoot = null;
		
		
		com.wm.app.b2b.server.ns.Namespace.current();
		
		
		flowSvc =  (FlowSvcImpl) com.wm.app.b2b.server.ns.Namespace.getService(NSName.create(serviceName));
		flowRoot = flowSvc.getFlowRoot();
		FlowElement[] arrayOfFlowElement = flowRoot.getNodes();
		
		IDataUtil.put( pipelineCursor, "arrayOfFlowElement", arrayOfFlowElement );
		
		IDataUtil.put( pipelineCursor, "flow root path ", flowRoot.getNodeCount());
		
		for(int i=0 ; i < arrayOfFlowElement.length;i++)
		{
			
			FlowRoot flowElementRoot =  (FlowRoot) arrayOfFlowElement[i].getFlowRoot();
			
			String key =  "array at "+i;
			
			//IDataUtil.put( pipelineCursor, key, arrayOfFlowElement[i].getPath());
			
			IDataUtil.put( pipelineCursor, key, arrayOfFlowElement[i].getID());
			
		}
		 
		
		
		
		
		
		
		
		
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		String[]	removedReferences = new String[1];
		removedReferences[0] = "removedReferences";
		IDataUtil.put( pipelineCursor_1, "removedReferences", removedReferences );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void removeUnresolvedRefServices_1 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeUnresolvedRefServices_1)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [i] field:0:required referenceServiceName
		// [o] field:1:required removedReferences
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	serviceName = IDataUtil.getString( pipelineCursor, "serviceName" );
		
		FlowSvcImpl flowSvc = null;
		FlowRoot flowRoot = null;		
		
		Namespace ns = com.wm.app.b2b.server.ns.Namespace.current();
		NSNode node = ns.getNode(NSName.create(serviceName));
		
		flowSvc =  (FlowSvcImpl) com.wm.app.b2b.server.ns.Namespace.getService(NSName.create(serviceName));
		flowRoot = flowSvc.getFlowRoot();
		
		DependencyManager dm = NSDependencyManager.current();
		 
		IData unresolved = dm.getInvalidPaths(node);
		
		IDataCursor unresolvedCursor = unresolved.getCursor();
		IData[] brokenReferences = IDataUtil.getIDataArray(unresolvedCursor, "reference");
		
		ArrayList<String> brokenServerList = new ArrayList<>();
		
				
		for(IData temp : brokenReferences)
		{
			
			IDataCursor tempCursor = temp.getCursor();
			String[] pathList = IDataUtil.getStringArray(tempCursor,"path");
			
			boolean invokeStep =  false;
			
			for(String path : pathList)
			{
				String[] tokeneizePath = path.split("/");
				
				int index = 1; 
				
				int invokeIndex = ArrayUtils.getIndex(tokeneizePath, "INVOKE;0");
						
						//Arrays.asList(tokeneizePath).indexOf("INVOKE;0");
						
						
						
				
				int pathSize = tokeneizePath.length-1;
				
				if(!(pathSize >invokeIndex))
				{
					brokenServerList.add(IDataUtil.getString(tempCursor, "name"));
					break;
				}
				
										
				/*for(String singlePath : tokeneizePath)
				{
					index++;
					
					if(singlePath.equalsIgnoreCase("INVOKE;0"))
					{
						
						if(!(tokeneizePath.length > index))
						{
							invokeStep = true;
							brokenServerList.add(IDataUtil.getString(tempCursor, "name"));
							break;
						}
					}
					
				}
				
				if(invokeStep)
					break;*/
			}
		}
		
		
		FlowElement[] flowElements = flowRoot.getNodes();
		
		int nodeCount = flowRoot.getNodeCount();
		
		IDataUtil.put(pipelineCursor, "flowElements", flowElements);	
		
		IDataUtil.put(pipelineCursor, "Node count", flowRoot.getNodeCount());	
		
		for(FlowElement element : flowElements)
		{
			//element.de
			
		}
		
		for(int i =0 ; i< nodeCount; i++)
		{
			FlowElement nodeElement = flowRoot.getNodeAt(i);
			
			if(nodeElement.getDisplayType().equals("INVOKE"))
			{
				com.wm.lang.flow.FlowInvoke flowInvoke = (com.wm.lang.flow.FlowInvoke) nodeElement;
				String servinceInvoke = flowInvoke.getService().toString();
				
				
				IDataUtil.put(pipelineCursor, "nodeNSName", flowInvoke.getService());	
				if(brokenServerList.contains(servinceInvoke))
				{
					//flowRoot.deleteNodeAt(i);
					//flowInvoke.deleteAllNodes();
					//flowInvoke.deleteNode(flowInvoke);
					//nodeElement.deleteNodeAt(i);
					//flowRoot.addNode(nodeElement);
					//flowRoot.setNodeIndex(i);
					//flowRoot.deleteAllNodes();
					nodeElement.deleteAllNodes();
					
				}
				
			}
		}
		
		
		
		
		flowSvc.setFlowRoot(flowRoot);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		IDataUtil.put(pipelineCursor, "unresolved", unresolved);
		IDataUtil.put(pipelineCursor, "brokenServerList", brokenServerList);
		
		
		
		
		
		
		
				
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	public List checkElement(FlowElement node)
	{
		
		
		
		return null;			
		
		
		
		
		
				
	}
	
	
		
	// --- <<IS-END-SHARED>> ---
}

