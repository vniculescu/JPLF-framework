package Power;
/*
 * a tuple of powerlist functions
 * all are applied on the same argument list
 * for each element of the tuple a separate result will be stored
 * 'power_result' is a list of powerlists
 */
import java.util.ArrayList;
import java.util.List;

import types.IPowerList;
import types.ITiePowerList;
import types.IZipPowerList;


/**
 * The Class TuplePowerResultFunction.
 *
 * @param <T> the generic type
 */
public class TuplePowerResultFunction<T> extends PowerFunction<T> {

/** The right result. */
//the recipient where the result will be computed is given in the constructor
	protected List<IPowerList<T>> power_result, left_result, right_result;

	
	/**
	 * Instantiates a new tuple power result function.
	 *
	 * @param args the args
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public TuplePowerResultFunction( Object... args) throws IllegalArgumentException {
		/*
		 * create a tuple of function with one powerlist result equal to the first argument!
		 */
		super(args);
		List<IPowerList<T>> power_result = new ArrayList<IPowerList<T>>(1);
		this.power_result= power_result;
		power_result.add(power_arg.get(0));
	}
	
	/**
	 * Instantiates a new tuple power result function.
	 *
	 * @param power_result the power result
	 * @param args the args
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public TuplePowerResultFunction(List<IPowerList<T>> power_result,  Object... args) throws IllegalArgumentException {
		super(args);
		//setArg(args);
		this.power_result= power_result;
	}

	/**
	 * Instantiates a new tuple power result function.
	 *
	 * @param power_result the power result
	 * @param power_arg the power arg
	 * @param arg the arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public TuplePowerResultFunction( List<IPowerList<T>> power_result, List<IPowerList<T>> power_arg, List<Object> arg)
			throws IllegalArgumentException {
		super(power_arg, arg);
		this.power_result= power_result;
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
		left_result = new ArrayList<IPowerList<T>>();
		right_result = new ArrayList<IPowerList<T>>();
		super.split_arg(); 
		for(IPowerList<T> l: power_result){
			left_result.add(l.getLeft());
			right_result.add(l.getRight());
		}
	}
////////////////////////////////////////////////////////////////////////////
	/**
 * Creates the left function.
 *
 * @return the tuple power result function
 */
//Factory methods
		public TuplePowerResultFunction<T> create_left_function(){
			return new TuplePowerResultFunction<T>( left_result, left_power_arg, left_arg);
		}

/**
 * Creates the right function.
 *
 * @return the tuple power result function
 */
////////////////////////////////////////////////////////////////////////////		
		public TuplePowerResultFunction<T> create_right_function(){
			return new TuplePowerResultFunction<T>(right_result, right_power_arg, right_arg);
		}
		
/**
 * Compute.
 *
 * @return the list
 */
////////////////////////////////////////////////////////////////////////////		
		public  List<IPowerList<T>> compute()
		{//template method
			if (test_basic_case()){
				power_result =  basic_case();//.toPowerList();
				}
			else{
				split_arg();
//				System.out.println("left args"+left_power_arg);
//				System.out.println("right args"+left_power_arg);
//				System.out.println("create left function");
				TuplePowerResultFunction<T> left = create_left_function();
//				System.out.println("create right function");
				TuplePowerResultFunction<T> right = create_right_function();
				int n= power_result.size();
				List<IPowerList<T>> left_res = left.compute();
				List<IPowerList<T>> right_res = right.compute();
				for(int i=0; i<n; i++){
					left_result.set(i, left_res.get(i));
					right_result.set(i,right_res.get(i));
				}
//				System.out.println("left before combine"+left_result);
//				System.out.println("right before combine"+right_result);
				result = combine(left_result, right_result);
//				System.out.println("result after combine"+right_result);
			}
			return power_result;
		}

/**
 * Basic case.
 *
 * @return the list
 */
////////////////////////////////////////////////////////////////////////////
		@Override
		public List<IPowerList<T>> basic_case(){
	//		System.out.println("in PowerResultFunction basic case "+power_arg.get(0));
			//power_result.setValue( power_arg.get(0).getValue());
			return power_result;
		}
////////////////////////////////////////////////////////////////////////////		

	/**
 * Combine.
 *
 * @param first the first
 * @param second the second
 * @return the list
 */
public List<IPowerList<T>> combine(List<Object> first, List<Object> second){
		/**we may assume that in many cases
		 * power_result=left_result *right_result
		 * but this is not always true
		 * if it is not then we have to set 
		 * left_result = first and right_result =  second and 
		 * power_result=left_result *right_result
		 */
	int n=power_result.size();
	for(int i=0; i<n; i++){
	   if (	(left_result.get(i) != first.get(i) ) || (right_result.get(i)!=second.get(i)) ){
		   System.out.println("verif left=first & right=second");
		//one of the two arguments are equal to the left/right side of power_result
			if( (left_result.get(i) == first.get(i) ) || (right_result.get(i) ==second.get(i))|| 
					(power_result.get(i) == first.get(i)) ||(power_result.get(i) == second.get(i))){
				/* this is needed to assure that 
				 * left_result, right_result
				 * are sublists of the power_result list
				 */
				((PowerList<T>) power_result.get(i)). setFrom2Lists((PowerList<T>) first.get(i),(PowerList<T>)  second.get(i));
				if ( (((PowerList<T>)left_result.get(i)).getBase()  != ((PowerList<T>)power_result.get(i)).getBase()) ||
					 (((PowerList<T>)right_result.get(i)).getBase() != ((PowerList<T>)power_result.get(i)).getBase())  )
				{
					this.split_arg();
				}
			}
			else
			{
				if (power_result.get(i) instanceof IZipPowerList){
						power_result.set(i, new DZipPowerList<T>((IPowerList<T>)first.get(i), (IPowerList<T>)second.get(i)));
						System.out.println("verif dzip");
				}
				else if (power_result.get(i) instanceof ITiePowerList){
						power_result.set(i, new DTiePowerList<T>((IPowerList<T>)first.get(i), (IPowerList<T>)second.get(i)));
						System.out.println("verif dtie");
				}
				left_result.set(i,  power_result.get(i).getLeft());
				right_result.set(i, power_result.get(i).getRight());
			}
	   	}
		}
		return power_result;
	}
////////////////////////////////////////////////////////////////////////////		

		/**
 * Sets the result.
 *
 * @param result the new result
 */
public void setResult(List<IPowerList<T>> result) {
			power_result = result;
		}
		
		/**
		 * Gets the result.
		 *
		 * @return the result
		 */
		public List<IPowerList<T>>  getResult() {
			return power_result ;
		}
}

