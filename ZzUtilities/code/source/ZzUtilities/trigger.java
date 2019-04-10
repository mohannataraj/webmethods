package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.ArrayList;
import com.wm.lang.ns.NSName;
import com.wm.app.b2b.server.dispatcher.trigger.*;
// --- <<IS-END-IMPORTS>> ---

public final class trigger

{
	// ---( internal utility methods )---

	final static trigger _instance = new trigger();

	static trigger _newInstance() { return new trigger(); }

	static trigger _cast(Object o) { return (trigger)o; }

	// ---( server methods )---




	public static final void getTriggerFilter (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getTriggerFilter)>> ---
		// @sigtype java 3.5
		// [i] field:0:required triggerName
		// [o] record:1:required triggerFilters
		// [o] - field:0:required name
		// [o] - field:0:required service
		// [o] - field:0:required joinType
		// [o] - record:1:required documentTypeFilterPairs
		// [o] -- field:0:required documentType
		// [o] -- field:0:required filterSource
		// [o] -- object:0:required umFilter
		// [o] - field:0:required documentTypesString
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	triggerName = IDataUtil.getString( pipelineCursor, "triggerName" );
		pipelineCursor.destroy();
		
		IData[] triggerConditions = null;		
		
		TriggerManagementUtil tmu = new TriggerManagementUtil(triggerName);		
		triggerConditions = tmu.getConditionsAsData();			
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		
		IDataUtil.put( pipelineCursor_1, "triggerFilters", triggerConditions );
		pipelineCursor_1.destroy();
		
		
		
			
		// --- <<IS-END>> ---

                
	}
}

