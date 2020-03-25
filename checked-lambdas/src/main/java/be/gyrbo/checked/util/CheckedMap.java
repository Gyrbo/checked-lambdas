package be.gyrbo.checked.util;

import java.util.Map;

import be.gyrbo.checked.function.CheckedBiConsumer;
import be.gyrbo.checked.function.CheckedBiFunction;
import be.gyrbo.checked.function.CheckedFunction;

public class CheckedMap<K, V> {
	private final Map<K, V> delegate;

	private CheckedMap(Map<K, V> delegate) {
		this.delegate = delegate;
	}
	
	public static <K, V> CheckedMap<K, V> of(Map<K, V> delegate) {
		return new CheckedMap<K, V>(delegate);
	}

	public <EX extends Exception> void forEach(CheckedBiConsumer<? super K, ? super V, EX> action) throws EX {
		delegate.forEach(action.sneakyThrow());
	}

	public <EX extends Exception> void replaceAll(CheckedBiFunction<? super K, ? super V, ? extends V, EX> function) throws EX {
		delegate.replaceAll(function.sneakyThrow());
	}

	public <EX extends Exception> V computeIfAbsent(K key,
			CheckedFunction<? super K, ? extends V, EX> mappingFunction) throws EX {
		return delegate.computeIfAbsent(key, mappingFunction.sneakyThrow());
	}

	public <EX extends Exception> V computeIfPresent(K key,
			CheckedBiFunction<? super K, ? super V, ? extends V, EX> remappingFunction) throws EX {
		return delegate.computeIfPresent(key, remappingFunction.sneakyThrow());
	}

	public <EX extends Exception> V compute(K key,
			CheckedBiFunction<? super K, ? super V, ? extends V, EX> remappingFunction) throws EX {
		return delegate.compute(key, remappingFunction.sneakyThrow());
	}

	public <EX extends Exception> V merge(K key, V value,
			CheckedBiFunction<? super V, ? super V, ? extends V, EX> remappingFunction) throws EX {
		return delegate.merge(key, value, remappingFunction.sneakyThrow());
	}
	
	
}
