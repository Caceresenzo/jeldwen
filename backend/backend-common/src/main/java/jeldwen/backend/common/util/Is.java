package jeldwen.backend.common.util;

public class Is {
	
	public static boolean aString(Class<?> clazz) {
		return test(clazz, String.class);
	}
	
	public static boolean aBoolean(Class<?> clazz) {
		return test(clazz, Boolean.TYPE, Boolean.class);
	}
	
	public static boolean aByte(Class<?> clazz) {
		return test(clazz, Byte.TYPE, Byte.class);
	}
	
	public static boolean aShort(Class<?> clazz) {
		return test(clazz, Short.TYPE, Short.class);
	}
	
	public static boolean anInteger(Class<?> clazz) {
		return test(clazz, Integer.TYPE, Integer.class);
	}
	
	public static boolean aLong(Class<?> clazz) {
		return test(clazz, Long.TYPE, Long.class);
	}
	
	public static boolean aFloat(Class<?> clazz) {
		return test(clazz, Float.TYPE, Float.class);
	}
	
	public static boolean aDouble(Class<?> clazz) {
		return test(clazz, Double.TYPE, Double.class);
	}
	
	public static boolean aPrimitive(Class<?> clazz) {
		return test(clazz, Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE);
	}
	
	private static boolean test(Class<?> to, Class<?>... against) {
		for (Class<?> clazz : against) {
			if (clazz.equals(to)) {
				return true;
			}
		}
		
		return false;
	}
	
}