package mpi_ct;

import Power.PowerFunction;
import mpi.MPI;


/**
 * The Class MPI_CTOperations.
 *
 * @param <T> the generic type
 */
public class MPI_CTOperations<T> implements I_MPI_CTOperations<T> {
	
	/** The nr proces. */
	protected int nrProces;
	
	/** The nr procese. */
	protected int nrProcese;
	
	/** The function. */
	protected PowerFunction<T> function;
	
	/**
	 * Instantiates a new MP I CT operations.
	 *
	 * @param function the function
	 */
	//////////////////////////////////////////////////////////////////////////////////////////
	public MPI_CTOperations(PowerFunction<T> function) {
		nrProces = MPI.COMM_WORLD.Rank();
		nrProcese = MPI.COMM_WORLD.Size();
		this.function = function;
	}
	
	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	//////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public PowerFunction<T> getFunction(){
		return function;
	}

/**
 * Compute.
 *
 * @return the object
 */
//////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Object compute(){
		Object result;
		read();
		split();
		result = leaf();
//		System.out.println(nrProces +" after leaf "+ function.getResult());
		result = compose();
//		System.out.println(nrProces +" after compose "+ function.getResult());
		write();
		return function.getResult();
	}
	
	/**
	 * Write.
	 */
	@Override
	public void write() {
	}
	
	/**
	 * Read.
	 */
	@Override
	public void read() {
	}
	
	/**
	 * Split.
	 */
	@Override
	public void split() {
	}
	
	/**
	 * Leaf.
	 *
	 * @return the object
	 */
	@Override
	public Object leaf() {
		return null;
	}
	
	/**
	 * Compose.
	 *
	 * @return the object
	 */
	@Override
	public Object compose() {
		return null;
	}

	/**
	 * Sets the power function.
	 *
	 * @param function the new power function
	 */
	@Override
	public void setPowerFunction(PowerFunction<T> function) {
		this.function = function;
	}
	
}//~end of the class