package jeldwen.backend.common.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MoreCollectors {
	
	public static <T, K, U> Collector<T, ?, Map<K, U>> toLinkedMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
		return Collectors.toMap(keyMapper, valueMapper, (u, v) -> {
			throw new IllegalStateException(String.format("Duplicate key %s", u));
		}, LinkedHashMap::new);
	}
	
	public static Function<List<String>, String> joiningLastDelimiter(String delimiter, String lastDelimiter) {
		return (list) -> {
			int last = list.size() - 1;
			
			if (last < 1) {
				return String.join(delimiter, list);
			}
			
			return String.join(lastDelimiter, String.join(delimiter, list.subList(0, last)), list.get(last));
		};
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		
		return (t) -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
}