package Power.functions;

import Power.PowerList;
import Power.PowerResultFunction;
import types.IPowerList;


/**
 * The Class CopyOperator.
 *
 * @param <T> the generic type
 */
public class CopyOperator<T> extends PowerResultFunction<T> {
	
	/** The off. */
	int off;
	
	/**
	 * Instantiates a new copy operator.
	 *
	 * @param src the src
	 * @param dest the dest
	 */
	public CopyOperator(PowerList<T> src, PowerList<T> dest){
		super(dest, src); 
		off=dest.getOffset();
	}
	
	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public  IPowerList<T> basic_case(){
		IPowerList s = power_result;
		
		if (s.getValue() instanceof types.IBasicList)
		{	
			types.IBasicList list = (types.IBasicList)power_arg.get(0).getValue() ;
			list.copyElems((types.IBasicList)s.getValue());
		}
		else{
			power_result.setValue(power_arg.get(0).getValue());
		}
		
		return power_result; //power_result should be identical to list argument
	}
	

	
	/**
	 * Combine.
	 *
	 * @param first the first
	 * @param second the second
	 * @return the i power list
	 */
	public IPowerList<T> combine(Object first, Object second){
		super.combine(first, second);
		((PowerList<T>)power_result).setOffset(off);
		return power_result;
	}
	
	/**
	 * Creates the left function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_left_function(){
		return new CopyOperator<T>((PowerList<T>)power_arg.get(0).getLeft(), (PowerList<T>)left_result);
	}
	
	/**
	 * Creates the right function.
	 *
	 * @return the power result function
	 */
	@Override
	public PowerResultFunction<T> create_right_function(){
		return new CopyOperator<T>((PowerList<T>)power_arg.get(0).getRight(), (PowerList<T>)right_result);
	}

}
