package mpi_ct;


/**
 * The Class MPI_DecoCTOperations.
 *
 * @param <T> the generic type
 */
public class MPI_DecoCTOperations<T>  extends MPI_CTOperations<T>{
	
	/** The ct. */
	protected I_MPI_CTOperations<T> ct;
	
	/**
	 * Instantiates a new MP I deco CT operations.
	 *
	 * @param ct the ct
	 */
	public MPI_DecoCTOperations (I_MPI_CTOperations<T> ct){
		super(ct.getFunction());
		this.ct = ct;
	}
	
	/**
	 * Write.
	 */
	@Override
	public void write() {
		ct.write();
	}
	
	/**
	 * Read.
	 */
	@Override
	public void read() {
		ct.read();
	}
	
	/**
	 * Split.
	 */
	@Override
	public void split() {
		ct.split();
	}
	
	/**
	 * Leaf.
	 *
	 * @return the object
	 */
	@Override
	public Object leaf() {
		ct.leaf();
		return function.getResult();
	}
	
	/**
	 * Compose.
	 *
	 * @return the object
	 */
	@Override
	public Object compose() {
		ct.compose();
		return function.getResult();
	}

}
