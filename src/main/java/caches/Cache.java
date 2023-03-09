package caches;

public interface Cache<T> {
    public T get(final String key);
    public void put(final String key, final T value);
}
