package me.johnniang.websocketdemo.data;

import org.springframework.lang.NonNull;

/**
 * @description:
 * @author: ycy
 * @date: 2020/3/103:02
 */
public enum OrderBizType implements DescriptionEnum {

    /**
     * Less-than truck load
     */
    LTL(1, "零担", "L"),

    /**
     * Local city
     */
    LOCAL_CITY(2, "同城", "T"),

    /**
     * Full truck load
     */
    FTL(3, "整车", "Z"),

    /**
     * House moving
     */
    HOUSE_MOVING(4, "搬家", "T");

    private final int value;

    private final String description;

    /**
     * 业务代码。
     */
    private final String code;

    OrderBizType(int value, String description, String code) {
        this.value = value;
        this.description = description;
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 获取业务线代码。
     *
     * @return 业务线代码
     */
    @NonNull
    public String getCode() {
        return code;
    }
}
