package gx.qlexp.core;

/**
 * @author shenping
 * 2018-12-12
 */
public interface ExpContext<K, V> {
    V get(Object k);
    V put(K k, V v);
    void clear();
}
