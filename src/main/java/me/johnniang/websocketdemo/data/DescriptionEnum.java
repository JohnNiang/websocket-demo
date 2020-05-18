package me.johnniang.websocketdemo.data;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Description enum interface.
 *
 * @author johnniang
 */
public interface DescriptionEnum extends ValueEnum<Integer> {

    /**
     * Get enum description.
     *
     * @return enum description
     */
    String getDescription();


    /**
     * Get enum from description.
     *
     * @param desc      description could be blank
     * @param typeClass enum type class must not be null
     * @param <T>       enum type
     * @return corresponding enum or null
     */
    @Nullable
    static <T extends Enum<?>> T from(@Nullable String desc, @NonNull Class<T> typeClass) {
        Assert.notNull(typeClass, "Type class must not be null");
        Assert.isTrue(!typeClass.isAssignableFrom(DescriptionEnum.class),
            "Enum type must implement " + DescriptionEnum.class.getSimpleName());

        // get all enums
        T[] types = typeClass.getEnumConstants();
        // foreach all types
        for (T type : types) {
            // convert to description enum
            DescriptionEnum descriptionEnum = (DescriptionEnum) type;
            if (Objects.equals(desc, descriptionEnum.getDescription())) {
                // compare
                return type;
            }
        }
        return null;
    }
}
