package Power;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import basic.MyArrayList;
import types.IPowerList;
import utils.Function;



/**
 * The Class PowerFunction.
 *
 * @param <T> the generic type
 */
public class PowerFunction<T> extends Function implements Serializable //Comparable<PowerFunction<T>>
{//this is a function that has at least a powerlist argument
	
	/** The power arg. */
//protected 
	protected List<IPowerList<T>> power_arg = new ArrayList<IPowerList<T>>();
	
	/** The no power arg. */
	private int no_power_arg ;
	
	/** The left power arg. */
	protected List<IPowerList<T>> left_power_arg;
	
	/** The left arg. */
	protected List<Object> left_arg;
	
	/** The right power arg. */
	protected List<IPowerList<T>> right_power_arg;
	
	/** The right arg. */
	protected List<Object> right_arg;
	
/**
 * Instantiates a new power function.
 *
 * @param f the f
 */
////////////////////////////////////////////////////////////////////////////
	public PowerFunction(PowerFunction<T> f){
		power_arg = new ArrayList<IPowerList<T>>();
		no_power_arg = f.get_no_power_arg();
		arg = new ArrayList<Object>();
		this.no_arg = f.no_arg;
//		for(int i=0;i<no_arg;i++)
//			arg.add(f.arg.get(i));
		for(int i=0;i<get_no_power_arg();i++)
			power_arg.add(f.power_arg.get(i));
	}
	
	/**
	 * Instantiates a new power function.
	 */
	public PowerFunction(){
		
	}
	
	/**
	 * Instantiates a new power function.
	 *
	 * @param args the args
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public PowerFunction(Object ... args) throws IllegalArgumentException{
	// if no object arguments then arg will be set to null
		setArg(args);
	}

/**
 * Instantiates a new power function.
 *
 * @param power_arg the power arg
 * @param arg the arg
 * @throws IllegalArgumentException the illegal argument exception
 */
////////////////////////////////////////////////////////////////////////////
	public PowerFunction(List<PowerList<T>> power_arg, List<Object> arg) 
				throws IllegalArgumentException{
		setArg(power_arg, arg);

	}

/**
 * Sets the arg.
 *
 * @param args the new arg
 * @throws IllegalArgumentException the illegal argument exception
 */
////////////////////////////////////////////////////////////////////////////
	public void setArg(Object ...args) throws IllegalArgumentException{
		no_power_arg = 0;
		no_arg = 0;
//		this.power_arg= new ArrayList<IPowerList<T>>();
//		this.arg= new ArrayList<Object>();
		for(Object o: args){
			if (o instanceof IPowerList){
				power_arg.add((IPowerList<T>)o);
				no_power_arg = no_power_arg + 1;
			 }else{
				arg.add(o);
				no_arg ++ ;
			}
		}
		if (no_power_arg == 0)
			throw new IllegalArgumentException("no powerlist argument"); //in this case we don't have a PowerListFunction
		if (no_arg == 0)
			arg=null;
		
//		result = compute();
		
	}

/**
 * Sets the arg.
 *
 * @param power_arg the power arg
 * @param arg the arg
 * @throws IllegalArgumentException the illegal argument exception
 */
////////////////////////////////////////////////////////////////////////////
	public void setArg(List<IPowerList<T>> power_arg, List<Object> arg) throws IllegalArgumentException{
		if (power_arg == null ) throw new IllegalArgumentException();
			if (power_arg.size()==0) throw new IllegalArgumentException();
		this.power_arg = power_arg;
		this.no_power_arg = power_arg.size();
		this.arg = arg;
		if (arg!=null ) this.no_arg = arg.size();
	}
	
	/**
	 * Sets the power arg.
	 *
	 * @param power_arg the new power arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setPowerArg(List<IPowerList<T>> power_arg) throws IllegalArgumentException{
		if (power_arg == null ) throw new IllegalArgumentException();
			if (power_arg.size()==0) throw new IllegalArgumentException();
		this.power_arg = power_arg;
		this.no_power_arg = power_arg.size();
	}
	
	/**
	 * Gets the power arg.
	 *
	 * @return the power arg
	 */
	public List<IPowerList<T>> getPowerArg( ) {
		return power_arg;
	}
	
/**
 * Test basic case.
 *
 * @return true, if successful
 */
////////////////////////////////////////////////////////////////////////////
	public  boolean test_basic_case(){
		/*
		 * even if there are more than one powerlist args
		 * it is enough to very the first for being a
		 * Singleton
		 */
		if(power_arg.get(0).isSingleton())
			return true;
		return false;
	}
	
/**
 * Basic case.
 *
 * @return the object
 */
////////////////////////////////////////////////////////////////////////////	
	public Object basic_case(){
		/*
		 * being a general 'abstract' function - 
		 * for the base case we just return the fist argument
		 * this function will be overridden in the subclasses
		 */
	//	System.out.println("in PowerFunction basic case ");
		IPowerList<T> arg0 = power_arg.get(0);
		return arg0;
	}

/**
 * Split arg.
 */
////////////////////////////////////////////////////////////////////////////	
	public void split_arg(){
	// power_arg is nonsingleton
	//left_power_arg/right_power_arg and left_arg/right_arg are computed 
	left_power_arg = new ArrayList<IPowerList<T>>(power_arg);
	right_power_arg = new ArrayList<IPowerList<T>>(power_arg);

		for (int i=0; i<power_arg.size(); i++){
			left_power_arg.set(i,power_arg.get(i).getLeft());
			right_power_arg.set(i,power_arg.get(i).getRight());
		}	
	}

/**
 * Combine.
 *
 * @param first the first
 * @param second the second
 * @return the object
 */
////////////////////////////////////////////////////////////////////////////	
	public Object combine(Object first, Object second){
		/*
		 * this would be overriden in subclasses
		 */
		return first;
	}
////////////////////////////////////////////////////////////////////////////
	
	/**
 * Creates the left function.
 *
 * @return the power function
 */
//Factory methods
	public PowerFunction<T> create_left_function(){
		return new PowerFunction<T>(left_power_arg, left_arg);
	}
	
	/**
	 * Creates the right function.
	 *
	 * @return the power function
	 */
	public PowerFunction<T> create_right_function(){
		return new PowerFunction<T>(right_power_arg, right_arg);
	}

/**
 * Compute.
 *
 * @return the object
 */
/////////////////////////////////////////////////////////////////////////		
	public  Object compute()
	{//template method
		if (test_basic_case())
			result = basic_case();
		else{
			split_arg();
			PowerFunction<T> left = create_left_function();
			PowerFunction<T> right = create_right_function();
			Object res_left = left.compute();
			Object res_right = right.compute();
			result = combine(res_left, res_right);
		}
		return result;
	}

/**
 * Args normalization.
 */
/////////////////////////////////////////////////////////////////////////	
	public void argsNormalization() {
		/*
		 * all powerlist arguments are normalized (start=0 and incr=1)
		 */
		for (int i=0; i<power_arg.size(); i++){
			power_arg.get(i).normalization();
		}	
	}
	
	/**
	 * Args nullization.
	 */
	public void argsNullization() {
		/*
		 * all powerlist arguments are set to null list
		 */
		for (int i=0; i<power_arg.size(); i++)
			power_arg.get(i).setElements(new MyArrayList<T>(1));
	}
	
/**
 * Gets the no power arg.
 *
 * @return the no power arg
 */
/////////////////////////////////////////////////////////////////////////	
	public int get_no_power_arg() {
		return no_power_arg;
	}
	
	

//	
//	public int compareTo(PowerFunction<T> pf){
//		//based on the size of the first powerlist argument
//		PowerList<T> a = (PowerList<T>)power_arg.get(0);
//		PowerList<T> pa = (PowerList<T>)pf.power_arg.get(0);
//		return -(a.getLen()-pa.getLen());		
//	}
}
