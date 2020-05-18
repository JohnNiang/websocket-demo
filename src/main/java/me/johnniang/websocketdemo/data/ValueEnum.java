package me.johnniang.websocketdemo.data;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Value enum interface.
 *
 * @param <T> value type
 * @author johnniang
 */
public interface ValueEnum<T extends Serializable> {

    /**
     * 枚举数据库存储值
     */
    T getValue();

    /**
     * Converts value to corresponding enum.
     *
     * @param enumType enum type
     * @param value    database value
     * @param <V>      value generic
     * @param <E>      enum generic
     * @return corresponding enum
     */
    static <V extends Serializable, E extends ValueEnum<V>> E valueToEnum(Class<E> enumType, V value) {
        Assert.notNull(enumType, "enum type must not be null");
        Assert.notNull(value, "value must not be null");
        Assert.isTrue(enumType.isEnum(), "type must be an enum type");

        return Stream.of(enumType.getEnumConstants())
            .filter(item -> item.getValue().equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown enum value: " + value));
    }

}
