package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.sun.mail.imap.protocol.Namespaces.Namespace;
import com.wm.app.b2b.server.ServerAPI;
// --- <<IS-END-IMPORTS>> ---

public final class REST

{
	// ---( internal utility methods )---

	final static REST _instance = new REST();

	static REST _newInstance() { return new REST(); }

	static REST _cast(Object o) { return (REST)o; }

	// ---( server methods )---




	public static final void getRESTURL (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRESTURL)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [o] field:0:required restURL
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	serviceName = IDataUtil.getString( pipelineCursor, "serviceName" );
		pipelineCursor.destroy();
		
		String hostName = ServerAPI.getServerName(); 
		serviceName =  serviceName.replace(".", "/");
		serviceName = serviceName.substring(0, serviceName.indexOf(":"));
		String restURL =  "http://" + hostName + "/rest" + "/" + serviceName;
		 
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "restURL", restURL );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}
}

