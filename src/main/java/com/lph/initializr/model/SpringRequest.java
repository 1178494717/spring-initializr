package com.lph.initializr.model;

import com.lph.initializr.util.DependencyHelper;
import com.lph.initializr.util.VersionHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2020/1/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpringRequest {
    private List<String> dependencies = new ArrayList<>();

    private String name;

    private String type;

    private String description;

    private String groupId;

    private String artifactId;

    private String version;

    private String bootVersion;

    private String packaging;

    private String applicationName;

    private String language;

    private String packageName;

    private String javaVersion;

    private String cloudVersion;

    private List<Dependency> dependencyList = new ArrayList<>();

    // The base directory to create in the archive - no baseDir by default
    private String baseDir;

    public String getPackageName() {
        if (StringUtils.hasText(this.packageName)) {
            return this.packageName;
        }
        if (StringUtils.hasText(this.groupId) && StringUtils.hasText(this.artifactId)) {
            return getGroupId() + "." + getArtifactId();
        }
        return null;
    }

    public List<Dependency> getDependencyList() {
        for (String id: dependencies){
            Dependency dependency = DependencyHelper.getDependency(id);
            dependencyList.add(dependency);
            if(id.contains("cloud") && StringUtils.hasText(cloudVersion)){
                this.setCloudVersion(VersionHelper.getCloudVersion(this.getBootVersion()));
            }
        }
        return dependencyList;
    }
}
