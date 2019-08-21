package mpi_ct;

import Power.PowerFunction;


/**
 * The Interface I_MPI_CTOperations.
 *
 * @param <T> the generic type
 */
public interface I_MPI_CTOperations<T> 
{

	/** The compose. */
	int  COMPOSE = 1;
	
	/** The read. */
	int  READ  = 2;
	
	/** The write. */
	int  WRITE = 3;
	
	/**
	 * compute is done in three steps:
	 * read data (from files - implicitly the data are in memory)
	 * split
	 * leaf
	 * compose
	 * save data (into file - implicitly the data remain in memory).
	 *
	 * @return the object
	 */
	Object compute();

	/**
	 * Write.
	 */
	void write();

	/**
	 * Read.
	 */
	void read();

	/**
	 * Split.
	 */
	void split();

	/**
	 * Leaf.
	 *
	 * @return the object
	 */
	Object leaf();

	/**
	 * Compose.
	 *
	 * @return the object
	 */
	Object compose();

	/**
	 * Sets the power function.
	 *
	 * @param function the new power function
	 */
	void setPowerFunction(PowerFunction<T> function);

	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	PowerFunction<T> getFunction();

}