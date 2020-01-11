package com.lph.initializr.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lph.initializr.model.Dependency;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2020/1/8
 */
public class DependencyHelper {

    private static Map<String, List<Dependency>> dependencyMap = new HashMap<>();

    static {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("api.json");
            String str = IOUtils.toString(url, "UTF-8");
            JSONArray dependenciesValues = JSON.parseObject(str).getJSONObject("dependencies").getJSONArray("values");
            for (int i = 0; i < dependenciesValues.size(); i++) {
                JSONArray groupValues = dependenciesValues.getJSONObject(i).getJSONArray("values");
                for (int j = 0; j < groupValues.size(); j++) {
                    String id = groupValues.getJSONObject(j).getString("id");
                    JSONArray dependencies = groupValues.getJSONObject(j).getJSONArray("dependencies");
                    List<Dependency> dependencyList = new ArrayList<>();
                    for (int k = 0; k < dependencies.size(); k++) {
                        JSONObject dependency = dependencies.getJSONObject(k);
                        Dependency build = Dependency.builder().groupId(dependency.getString("groupId"))
                                .artifactId(dependency.getString("artifactId"))
                                .version(dependency.getString("version"))
                                .type(dependency.getString("type"))
                                .scope(dependency.getString("scope"))
                                .build();
                        dependencyList.add(build);
                    }
                    dependencyMap.put(id, dependencyList);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Dependency> getDependency(String id){
        return dependencyMap.get(id);
    }
}
