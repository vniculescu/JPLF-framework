package Power.functions;

import java.io.Serializable;
import java.util.function.BinaryOperator;

import Power.PowerFunction;
import Power.PowerList;
import types.IPowerList;


/**
 * The Class L_Reduce.
 *
 * @param <T> the generic type
 */
public class L_Reduce<T> extends PowerFunction<T> implements Serializable{

	/** The op. */
	protected BinaryOperator<T> op;
	
	/** The left result. */
	protected T right_result, left_result;
	
	/**
	 * Instantiates a new l reduce.
	 *
	 * @param op the op
	 * @param power_arg the power arg
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public L_Reduce(BinaryOperator<?> op, IPowerList<?> power_arg) throws IllegalArgumentException {
		super(power_arg);
		this.op = (BinaryOperator<T>)op;
	}
	
	/**
	 * Combine.
	 *
	 * @param left the left
	 * @param right the right
	 * @return the t
	 */
	@Override
	public  T combine(Object left, Object right){
		T value = op.apply((T)left, (T)right);
		return value;
	}
	
	/**
	 * Basic case.
	 *
	 * @return the object
	 */
	public Object basic_case(){
		PowerList s =  (PowerList) super.basic_case();
		if (s.getValue() instanceof types.IBasicList)
		{	types.IBasicList<T> list = (types.IBasicList<T>)s.getValue() ;
			basic.functions.L_Reduce<T> rf = 
			                   new basic.functions.L_Reduce<T>(op, list );
			Object result = rf.compute();
			s.setValue(result);
		}
		return s.getValue();
	}
	
	/**
	 * Creates the left function.
	 *
	 * @return the l reduce
	 */
	@Override
	public L_Reduce<T> create_left_function(){
		return new L_Reduce<T>(op, left_power_arg.get(0));
	}
	
	/**
	 * Creates the right function.
	 *
	 * @return the l reduce
	 */
	@Override
	public L_Reduce<T> create_right_function(){
		return new L_Reduce<T>(op, right_power_arg.get(0));
	}
}


