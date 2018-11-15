package ZzUtilities;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.DecimalFormat;
import java.text.NumberFormat;
// --- <<IS-END-IMPORTS>> ---

public final class number

{
	// ---( internal utility methods )---

	final static number _instance = new number();

	static number _newInstance() { return new number(); }

	static number _cast(Object o) { return (number)o; }

	// ---( server methods )---




	public static final void convertNumberToExponentialForm (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convertNumberToExponentialForm)>> ---
		// @sigtype java 3.5
		// [i] field:0:required num
		// [o] field:0:required expNum
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	num = IDataUtil.getString( pipelineCursor, "num" );
		pipelineCursor.destroy();	
		
		Double doubleNumber = Double.parseDouble(num);
		
		
		 
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put(pipelineCursor_1, "expNum", doubleNumber);
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}
}

