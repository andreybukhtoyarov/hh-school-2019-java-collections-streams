package common;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Содержит перегруженный метод, проверяющий ссылку на коллекцию или словарь на налл.
 * @author Andrey Bukhtoyarov (andreymedoed@gmail.com).
 * @version %Id%.
 * @since 0.1.
 */
public class Util {

    public static <T> Collection<T> emptyIfNull(Collection<T> collection) {
        return collection != null ? collection : Collections.emptyList();
    }

    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> collection) {
        return collection != null ? collection : Collections.emptyMap();
    }
}
