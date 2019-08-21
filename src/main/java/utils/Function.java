package utils;



import java.util.ArrayList;
import java.util.List;

/*
 * This is a simple, general function that has 
 * a list of Object arguments
 * and an Object type result
 */

/**
 * The Class Function.
 */
public  class Function {
	
	/** The result. */
	protected  Object result;
	
	/** The arg. */
	protected  List<Object> arg;
	
	/** The no arg. */
	protected  int no_arg=0;
	
	/**
	 * Instantiates a new function.
	 */
	public Function(){}
	
	/**
	 * Instantiates a new function.
	 *
	 * @param args the args
	 */
	public Function(Object... args){
		arg= new ArrayList<Object>();
		if (args.length > 0) 
			setArg(args);

	}
	
	/**
	 * Compute.
	 *
	 * @return the object
	 */
	public Object compute(){
		//pre: the function is well defined : no_arg > 0
		//post: result = f(args) => in general
		//particular base case =>identity function
		result = arg.get(0);
		return result;
	}
	
	/**
	 * Sets the arg.
	 *
	 * @param args the new arg
	 */
	public void setArg(Object ... args){
			no_arg= args.length;
			if (arg!=null) arg.clear();
			//if (arg.size()>0) System.out.println("CLEAR");
			else arg = new ArrayList<Object>();
			
			for (Object o: args)
				arg.add(o);
	}
	
	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public  Object getResult(){
		return result;
	}
	
	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(Object result) {
		System.out.println("set the result "+result);
		this.result = result;
	}
	
	/**
	 * Copy.
	 *
	 * @return the function
	 */
	public Function copy(){
		return this;
	}

}
