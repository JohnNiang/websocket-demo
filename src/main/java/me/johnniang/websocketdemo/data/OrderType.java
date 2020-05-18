package me.johnniang.websocketdemo.data;

/**
 * @description:
 * @author: ycy
 * @date: 2020/3/102:52
 */
public enum OrderType implements DescriptionEnum {

    NORMAL_ORDER(0, "正常订单"),
    SUPPLEMENTARY_ORDER(1, "补价订单"),
    VEHICLE_STANDARD_ORDER(2, "整车标准化订单"),
    VEHICLE_CONSULT_ORDER(3, "整车询价订单");

    OrderType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    private final int value;

    private final String description;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
