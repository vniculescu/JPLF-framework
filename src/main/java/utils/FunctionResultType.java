package utils;

import P.PResultFunction;
import Power.PowerResultFunction;

/**
 * The Enum FunctionType.
 */
public enum FunctionResultType {

	/** The maP. */
	LIST {
		@Override
		boolean hasValueResult() {
			return false;
		}
	},

	/** The reduce. */
	VALUE {
		@Override
		boolean hasValueResult() {
			return true;
		}
	};

	/**
	 * Checks for value result.
	 *
	 * @return true, if successful
	 */
	abstract boolean hasValueResult();

	public static FunctionResultType valueOf(Function function) {
		if (function instanceof PResultFunction || function instanceof PowerResultFunction) {
			return LIST;
		}
		return VALUE;
	}
}
