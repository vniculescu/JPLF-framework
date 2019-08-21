package Power;

import java.util.List;

import types.IPowerList;
import types.ITiePowerList;
import types.IZipPowerList;


/**
 * The Class PowerResultFunction.
 *
 * @param <T> the generic type
 */
public class PowerResultFunction<T> extends PowerFunction<T> {

/** The right result. */
//the recipient where the result will be computed is given in the constructor
	protected IPowerList<T> power_result, left_result, right_result;

	
	/**
	 * Instantiates a new power result function.
	 *
	 * @param power_result the power result
	 * @param args the args
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PowerResultFunction(IPowerList<T> power_result,  Object... args) throws IllegalArgumentException {
		super(args);
		//setArg(args);
		this.power_result = power_result;
	}

	/**
	 * Instantiates a new power result function.
	 *
	 * @param power_result the power result
	 * @param power_arg the power arg
	 * @param arg the arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PowerResultFunction( IPowerList<T> power_result, List<IPowerList<T>> power_arg, List<Object> arg)
			throws IllegalArgumentException {
		super(power_arg, arg);
		//setArg(power_arg, arg);
		this.power_result = power_result;
	}
	
//	public void setArg(IPowerList<T> power_result, Object ...args) throws IllegalArgumentException{
//		this.power_result= power_result;
//		super.setArg(args);
//	}
/**
 * Split arg.
 */
////////////////////////////////////////////////////////////////////////////
	public void split_arg(){
	// power_arg and power_result are nonsingleton lists
	//left_power_arg/right_power_arg and left_arg/right_arg are computed 
		super.split_arg(); 
		left_result  =  power_result.getLeft();
		right_result =  power_result.getRight();
	}
////////////////////////////////////////////////////////////////////////////
	/**
 * Creates the left function.
 *
 * @return the power result function
 */
//Factory methods
		public PowerResultFunction<T> create_left_function(){
			return new PowerResultFunction<T>( left_result, left_power_arg, left_arg);
		}

/**
 * Creates the right function.
 *
 * @return the power result function
 */
////////////////////////////////////////////////////////////////////////////		
		public PowerResultFunction<T> create_right_function(){
			return new PowerResultFunction<T>(right_result, right_power_arg, right_arg);
		}
		
/**
 * Compute.
 *
 * @return the i power list
 */
////////////////////////////////////////////////////////////////////////////		
		public  IPowerList<T> compute()
		{//template method
			if (test_basic_case()){
				power_result =  basic_case();//.toPowerList();
				result=power_result;
				}
			else{
				
				split_arg();
//				System.out.println("left args"+left_power_arg+"right args"+right_power_arg);
//				System.out.println("create left function");
				PowerResultFunction<T> left = create_left_function();
//				System.out.println("create right function");
				PowerResultFunction<T> right = create_right_function();
//				System.out.println("after split left = "+left+ "right="+right);
				left_result = left.compute();
				right_result = right.compute();
//				System.out.println("left before combine"+left_result);
//				System.out.println("right before combine"+right_result);
				power_result = combine(left_result, right_result);
//				System.out.println("result after combine"+right_result);
				result = power_result;
			}
			return power_result;
		}

/**
 * Basic case.
 *
 * @return the i power list
 */
////////////////////////////////////////////////////////////////////////////
		@Override
		public IPowerList<T> basic_case(){
	//		System.out.println("in PowerResultFunction basic case "+power_arg.get(0));
			power_result.setValue( power_arg.get(0).getValue());
			return power_result;
		}

/**
 * Combine.
 *
 * @param first the first
 * @param second the second
 * @return the i power list
 */
////////////////////////////////////////////////////////////////////////////		
		@Override
		public IPowerList<T> combine(Object first, Object second){
		/**we may assume that in many cases
		 * power_result=left_result *right_result
		 * but this is not always true
		 * if it is not then we have to set 
		 * left_result = first and right_result =  second and 
		 * power_result=left_result *right_result
		 */
			
	   if (	(left_result != first ) || (right_result!=second) ){
		//one of the two arguments are equal to the left/right side of power_result
			if( (left_result == first ) || (right_result ==second)|| (power_result == first) ||(power_result == second)){
				/* this is needed to assure that 
				 * left_result, right_result
				 * are sublists of the power_result list
				 */
				((PowerList<T>) power_result). setFrom2Lists((PowerList<T>) first,(PowerList<T>)  second);
				if ( (((PowerList<T>)left_result).getBase()  != ((PowerList<T>)power_result).getBase()) ||
					 (((PowerList<T>)right_result).getBase() != ((PowerList<T>)power_result).getBase())  )
				{
					this.split_arg();
				}
			}
			else
			{
				if (power_result instanceof IZipPowerList){
						power_result = new DZipPowerList<T>((IPowerList<T>)first, (IPowerList<T>)second);	
				}
				else if (power_result instanceof ITiePowerList){
						power_result = new DTiePowerList<T>((IPowerList<T>)first, (IPowerList<T>)second);
				}
				left_result = power_result.getLeft();
				right_result  = power_result.getRight();
			}
		}
	    result = power_result;
		return power_result;
	}
////////////////////////////////////////////////////////////////////////////		

		/**
 * Sets the result.
 *
 * @param result the new result
 */
public void setResult(IPowerList<T> result) {
			power_result = result;
		}
		
		/**
		 * Gets the result.
		 *
		 * @return the result
		 */
		public IPowerList<T>  getResult() {
			return power_result ;
		}


}
