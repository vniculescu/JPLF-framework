package Power.functions;

import Power.PowerList;
import Power.PowerResultFunction;
import basic.BasicList;
import types.IPowerList;


/**
 *  
 * pre: arg should be a ZipPowerList ??? what if not
 * post: arg[i+1]=arg[i], i=0..n-1 and arg[0] = e 
 * (p#q)* = q* # p
 *
 * @param <T> the generic type
 */

public class ShiftRight<T> extends PowerResultFunction<T> {
	
	/** The identity. */
	T identity;
	
	/**
	 * Instantiates a new shift right.
	 *
	 * @param arg the arg
	 * @param e the e
	 */
	public ShiftRight(IPowerList<T> arg, T e ){
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
			this.split_arg();
			((PowerList)right_result).shiftRight((PowerList)identity);
			//power_result = new ZipPowerList<T>(right_result, left_result, left_result.getLen());
			((PowerList)power_result).setOffset(((PowerList)power_result).getOffset()-1);
		}
		//PROBLEM : offset=? primul element este din fostul right_result??? offset= left_result.len
		return power_result;
	}
	
			
	/**
	 * Basic case.
	 *
	 * @return the i power list
	 */
	@Override
	public  IPowerList<T> basic_case(){
		
		IPowerList s =  power_arg.get(0);
		if (s.getValue() instanceof types.IBasicList)
		{	
			((BasicList)s.getValue()).shiftRight(identity);
		}
		else {
			s.setValue(identity);
		}
		return s;
	}
	

}
