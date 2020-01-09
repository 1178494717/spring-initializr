package com.lph.initializr.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2020/1/8
 */
public class VersionHelper {
    private static Map<String, String> versionMap = new HashMap<>();

    static {
        versionMap.put("2.2.2.RELEASE", "Hoxton.SR1");
    }

    public static String getCloudVersion(String bootVersion){
        return versionMap.get(bootVersion);
    }
}
