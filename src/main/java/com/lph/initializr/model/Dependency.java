package com.lph.initializr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2020/1/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dependency {

    private  String groupId;
    private  String artifactId;
    private  String version;
    private  String scope;
    private  String type;

}
