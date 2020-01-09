<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>${bootVersion}</version>
    </parent>
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <name>${name}</name>
    <description>${description}</description>

    <properties>
        <java.version>${javaVersion}</java.version>
        <#if cloudVersion??>
        <spring-cloud.version>${cloudVersion}</spring-cloud.version>
        </#if>
    </properties>

    <dependencies>
        <#list dependencyList as dependency>
            <dependency>
                <groupId>${dependency.groupId}</groupId>
                <artifactId>${dependency.artifactId}</artifactId>
                <#if dependency.version??>
                    <version>${dependency.version}</version>
                </#if>
                <#if dependency.scope??>
                    <version>${dependency.scope}</version>
                </#if>
            </dependency>
        </#list>
    </dependencies>

    <#if cloudVersion??>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${r'$'}{spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    </#if>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
