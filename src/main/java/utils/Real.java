package utils;

import java.io.Serializable;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;

/**
 * The Class Real.
 */
public class Real extends Number implements FieldElement<Real>, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6215300670613417434L;

	/** The v. */
	double v;

	/**
	 * Instantiates a new real.
	 *
	 * @param v the v
	 */
	public Real(double v) {
		this.v = v;
	}

	/**
	 * Gets the from string.
	 *
	 * @param s the s
	 * @return the from string
	 */
	public static Real getFromString(String s) {
		double d = Double.parseDouble(s);
		return new Real(d);
	}

	/**
	 * Gets the adds the unity.
	 *
	 * @return the adds the unity
	 */
	public static Real getAddUnity() {
		return new Real(0);
	}

	/**
	 * Gets the mul unity.
	 *
	 * @return the mul unity
	 */
	public static Real getMulUnity() {
		return new Real(1);
	}

	/**
	 * Gets the v.
	 *
	 * @return the v
	 */
	public synchronized double getV() {
		return v;
	}

	/**
	 * Sets the v.
	 *
	 * @param v the new v
	 */
	public synchronized void setV(double v) {
		this.v = v;
	}

	/**
	 * Adds the.
	 *
	 * @param r the r
	 * @return the real
	 */
	public Real add(Real r) {
		// v+=r.v;
		// return this;
		return new Real(this.v + r.v);
	}

	/**
	 * Adds the.
	 *
	 * @param r the r
	 * @return the real
	 */
	public Real add(double r) {
		// v+=r.v;
		// return this;
		return new Real(this.v + r);
	}

	/**
	 * Divide.
	 *
	 * @param arg0 the arg 0
	 * @return the real
	 * @throws NullArgumentException   the null argument exception
	 * @throws MathArithmeticException the math arithmetic exception
	 */
	@Override
	public Real divide(Real arg0) throws NullArgumentException, MathArithmeticException {
		return new Real(this.v / arg0.v);
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	@Override
	public Field<Real> getField() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Multiply.
	 *
	 * @param arg0 the arg 0
	 * @return the real
	 */
	@Override
	public Real multiply(int arg0) {
		return new Real(this.v * arg0);
	}

	/**
	 * Multiply.
	 *
	 * @param arg0 the arg 0
	 * @return the real
	 * @throws NullArgumentException the null argument exception
	 */
	@Override
	public Real multiply(Real arg0) throws NullArgumentException {
		return new Real(this.v * arg0.v);
	}

	/**
	 * Negate.
	 *
	 * @return the real
	 */
	@Override
	public Real negate() {
		return new Real(-v);
	}

	/**
	 * Reciprocal.
	 *
	 * @return the real
	 * @throws MathArithmeticException the math arithmetic exception
	 */
	@Override
	public Real reciprocal() throws MathArithmeticException {
		return null;
	}

	/**
	 * Subtract.
	 *
	 * @param arg0 the arg 0
	 * @return the real
	 * @throws NullArgumentException the null argument exception
	 */
	@Override
	public Real subtract(Real arg0) throws NullArgumentException {
		return new Real(this.v - arg0.v);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return (new Double(v)).toString();
	}

	/**
	 * Int value.
	 *
	 * @return the int
	 */
	@Override
	public int intValue() {
		return (int) v;
	}

	/**
	 * Long value.
	 *
	 * @return the long
	 */
	@Override
	public long longValue() {
		return (long) v;
	}

	/**
	 * Float value.
	 *
	 * @return the float
	 */
	@Override
	public float floatValue() {
		return (float) v;
	}

	/**
	 * Double value.
	 *
	 * @return the double
	 */
	@Override
	public double doubleValue() {
		return v;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Real) {
			return this.v == ((Real) obj).v;
		}
		return false;
	}

}
