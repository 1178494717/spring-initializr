package com.lph.initializr.util;

import com.lph.initializr.model.Dependency;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2020/1/8
 */
public class DependencyHelper {

    private static Map<String, Dependency> dependencyMap = new HashMap<>();

    static {

    }

    public static Dependency getDependency(String id){
        return dependencyMap.get(id);
    }
}
