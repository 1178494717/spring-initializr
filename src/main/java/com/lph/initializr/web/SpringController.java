package com.lph.initializr.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lph.initializr.model.SpringRequest;
import com.lph.initializr.service.SpringService;
import com.lph.initializr.util.ZipHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

/**
 * @date 2020/1/8
 */
@RestController
@Slf4j
public class SpringController {

    @Autowired
    private FreeMarkerConfigurer configurer;

    @Autowired
    private SpringService springService;

    @GetMapping(value = "/spring-initializr")
    public ResponseEntity<JSONObject> mavenProject() {
        try {
            URL apiJsonUrl = Thread.currentThread().getContextClassLoader().getResource("api.json");
            String apiJsonStr = IOUtils.toString(apiJsonUrl, "UTF-8");
            JSONObject jsonObject = JSON.parseObject(apiJsonStr);
            return ResponseEntity.ok(jsonObject);
        } catch (IOException e) {
            log.error("", e);
            return null;
        }
    }

    @GetMapping(value = "/spring-initializr/starter.zip")
    public ResponseEntity<byte[]> mavenProject(SpringRequest springRequest) throws IOException {
        log.info("SpringRequest -> {}", springRequest);
        Configuration configuration = configurer.getConfiguration();
        String rootPath = System.getProperty("user.dir") + File.separator + "tmp";
        FileUtils.deleteDirectory(new File(rootPath));
        process(configuration, rootPath, springRequest);
        ZipHelper.zip(rootPath, rootPath + "/" + springRequest.getArtifactId() + ".zip");
        byte[] bytes = FileUtils.readFileToByteArray(new File(rootPath + "/" + springRequest.getArtifactId() + ".zip"));
        ResponseEntity<byte[]> body = ResponseEntity.ok().header("Content-Type", "application/zip")
                .header("Content-Disposition", "attachment; filename=" + springRequest.getArtifactId() + ".zip")
                .body(bytes);
        FileUtils.deleteDirectory(new File(rootPath));
        return body;
    }

    @GetMapping(value = "/spring-initializr/pom.xml")
    public ResponseEntity<byte[]> mavenBuild(SpringRequest springRequest) throws IOException {
        log.info("SpringRequest -> {}", springRequest);
        Configuration configuration = configurer.getConfiguration();
        String rootPath = System.getProperty("user.dir") + File.separator + "tmp";
        FileUtils.deleteDirectory(new File(rootPath));

        String pomOutputPath = rootPath + "/pom.xml";
        processOne(configuration, "pom.ftl", pomOutputPath, springRequest);
        byte[] bytes = FileUtils.readFileToByteArray(new File(pomOutputPath));
        ResponseEntity<byte[]> body = ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=pom.xml")
                .body(bytes);
        FileUtils.deleteDirectory(new File(rootPath));
        return body;
    }

    private void process(Configuration configuration, String rootPath, SpringRequest springRequest) {
        String appOutputPath = rootPath + "/src/main/java/"
                + springRequest.getPackageName().replaceAll("\\.", "/")
                + "/App.java";
        processOne(configuration, "App.ftl", appOutputPath, springRequest);

        String propertiesOutputPath = rootPath + "/src/main/resources/application.properties";
        processOne(configuration, "application.ftl", propertiesOutputPath, springRequest);

        String pomOutputPath = rootPath + "/pom.xml";
        processOne(configuration, "pom.ftl", pomOutputPath, springRequest);
    }

    private void processOne(Configuration configuration, String freemarkerPath, String outputPath, SpringRequest springRequest) {
        FileWriter fileWriter = null;
        try {
            File outputFile = new File(outputPath);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            Template template = configuration.getTemplate(freemarkerPath);
            fileWriter = new FileWriter(outputFile);
            template.process(springRequest, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if(fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }
}
