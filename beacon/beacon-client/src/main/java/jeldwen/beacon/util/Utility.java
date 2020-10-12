package jeldwen.beacon.util;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

public class Utility {

	public static <T, R> boolean contains(Collection<T> collection, R object, Function<T, R> getter) {
		for (T t : collection) {
			if (Objects.equals(getter.apply(t), object)) {
				return true;
			}
		}
		
		return false;
	}
	
}