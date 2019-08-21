package utils;

import java.io.Serializable;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

/**
 * The Class Matrix.
 */
public class Matrix implements FieldElement<Matrix>, Serializable {

	/** The dim. */
	public static int DIM = 100;

	/** The n. */
	private int n;

	/** The v. */
	private double v[][];

	/**
	 * Instantiates a new matrix.
	 *
	 * @param n   the n
	 * @param val the val
	 */
	public Matrix(int n, double val) {
		this.v = new double[n][n];
		this.n = n;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				v[i][j] = val;
	}

	/**
	 * Instantiates a new matrix.
	 *
	 * @param n    the n
	 * @param val  the val
	 * @param zero the zero
	 */
	public Matrix(int n, double val, double zero) {
		this.v = new double[n][n];
		this.n = n;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (i != 0 && j != 0)
					v[i][j] = val;
				else
					v[i][j] = zero;
			}
	}

	/**
	 * Instantiates a new matrix.
	 *
	 * @param n the n
	 */
	public Matrix(int n) {

		this.v = new double[n][n];
		this.n = n;
	}

//		public Matrix (double val){
//			this.v=new double [DIM][DIM];
//			this.n=DIM;
//			for(int i=0; i<n;i++)
//				for(int j=0; j<n;j++)
//					v[i][j] = val;
	/**
	 * Adds the.
	 *
	 * @param r the r
	 * @return the matrix
	 */
//		}
	@Override
	public Matrix add(Matrix r) {
		Matrix result = new Matrix(n);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				result.v[i][j] = v[i][j] + r.v[i][j];
		return result;
	}

	/**
	 * Adds the.
	 *
	 * @param r the r
	 * @return the matrix
	 */
	public Matrix add(int r) {
		Matrix result = new Matrix(n);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				result.v[i][j] = v[i][j] + r;
		return result;
	}

	/**
	 * Divide.
	 *
	 * @param arg0 the arg 0
	 * @return the matrix
	 * @throws NullArgumentException   the null argument exception
	 * @throws MathArithmeticException the math arithmetic exception
	 */
	public Matrix divide(Matrix arg0) throws NullArgumentException, MathArithmeticException {
		return new Matrix(n);
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	@Override
	public Field<Matrix> getField() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Multiply.
	 *
	 * @param arg0 the arg 0
	 * @return the matrix
	 */
	@Override
	public Matrix multiply(int arg0) {
		Matrix result = new Matrix(n);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				result.v[i][j] = v[i][j] * arg0;
		return result;
	}

	/**
	 * Multiply.
	 *
	 * @param arg0 the arg 0
	 * @return the matrix
	 * @throws NullArgumentException the null argument exception
	 */
	@Override
	public Matrix multiply(Matrix arg0) throws NullArgumentException {
		Matrix result = new Matrix(n);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < n; k++)
					result.v[i][j] += v[i][k] * arg0.v[k][j];
		return result;
	}

	/**
	 * Negate.
	 *
	 * @return the matrix
	 */
	@Override
	public Matrix negate() {
		Matrix result = new Matrix(n);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				result.v[i][j] = -v[i][j];
		return result;
	}

	/**
	 * Reciprocal.
	 *
	 * @return the matrix
	 * @throws MathArithmeticException the math arithmetic exception
	 */
	@Override
	public Matrix reciprocal() throws MathArithmeticException {
		return null;
	}

	/**
	 * Subtract.
	 *
	 * @param arg0 the arg 0
	 * @return the matrix
	 * @throws NullArgumentException the null argument exception
	 */
	@Override
	public Matrix subtract(Matrix arg0) throws NullArgumentException {
		Matrix result = new Matrix(n);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				result.v[i][j] = v[i][j] - arg0.v[i][j];
		return result;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				s.append(new Double(v[i][j]).toString()).append(" ");
			s.append("\n");
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Matrix) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++)
					if (v[i][j] != ((Matrix) obj).v[i][j]) {
						return false;
					}
			}
			return true;
		}
		return false;
	}

}
