package org.sat.api.utils;

public class UUID {
    static SnowFlakeUtils SnowFlakeUtils = new SnowFlakeUtils(1, 1, 1);

    public static String getUUID() {

        return String.valueOf(SnowFlakeUtils.nextId());
    }
}
