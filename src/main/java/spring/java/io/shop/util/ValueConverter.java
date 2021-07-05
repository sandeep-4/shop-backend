package spring.java.io.shop.util;

public class ValueConverter {

	public static int convertStringtoint(String strVal, int defaultVal) {
		try {
			return Integer.parseInt(strVal);

		} catch (Exception e) {
			return defaultVal;
		}
	}

	public static boolean converSytingToBoolean(String strVal, boolean defaultVal) {
		try {
			if (strVal.equalsIgnoreCase("true") || strVal.equals(1)) {
				return true;
			} else if (strVal.equalsIgnoreCase("false") || strVal.equals(0)) {
				return false;
			}
		} catch (Exception e) {

		}
		return defaultVal;
	}

	public static double convertStringToDouble(String strVal, double defaultVal) {
		try {
			return Double.parseDouble(strVal);
		} catch (Exception e) {
			return defaultVal;
		}
	}
}
