package Power.functions;

import Power.PowerList;
import Power.PowerResultFunction;
import basic.BasicList;
import types.IPowerList;


/**
 *  
 * pre: arg should be a ZipPowerList ??? what if not
 * post: arg[i-1]=arg[i], i=1..n and arg[n] = e 
 * (p#q)* = q # p*
 *
 * @param <T> the generic type
 */
public class ShiftLeft<T> extends PowerResultFunction<T> {
	
	/** The identity. */
	T identity;
	
	/**
	 * Instantiates a new shift left.
	 *
	 * @param arg the arg
	 * @param e the e
	 */
	public ShiftLeft(IPowerList<T> arg, T e ){
		super(arg, arg);	
		this.identity = e;
	}
	
	/**
	 * Compute.
	 *
	 * @return the i power list
	 */
	public  IPowerList<T> compute()
	{
		if (test_basic_case()){
			power_result =  basic_case();//.toPowerList();
			}
		else{
			super.split_arg();
			((PowerList)left_result).shiftLeft(identity);
//			BasicList<T> basiclist = (BasicList<T>)right_result;
//			basiclist.rotateLeft();
//			power_result.setFrom2Lists(left_result, right_result, 1);
//			if (power_result.getOffset()>1) power_result.rotateRight();
			((PowerList)power_result).setOffset(((PowerList)power_result).getOffset()+1);
		}
			return power_result;
	}
	
			
	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public  IPowerList<T> basic_case(){
		
		IPowerList s =  power_result;// arg.get(0);
		if (s.getValue() instanceof types.IBasicList)
		{	
			((BasicList)s.getValue()).shiftLeft(identity);
		}
		else {
			s.setValue(identity);
		}
		return s;
	}
	

}
