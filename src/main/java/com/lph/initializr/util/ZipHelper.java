package com.lph.initializr.util;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.File;

/**
 * @date 2020/1/8
 */
public class ZipHelper {

    public static void zip(String srcFilePath, String distFileName) throws ZipException {
        zip(new File(srcFilePath), distFileName);
    }

    public static void zip(File srcFile, String distFileName) throws ZipException {
        ZipFile zipFile = new ZipFile(distFileName);

        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
        zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);

        File[] files = srcFile.listFiles();
        for(File file : files){
            if(file.isDirectory()){
                zipFile.addFolder(file,zipParameters);
            } else {
                zipFile.addFile(file, zipParameters);
            }
        }
    }

    public static void unzip(String srcFileName, String distFilePath) throws ZipException {
        ZipFile zipFile = new ZipFile(srcFileName);
        zipFile.extractAll(distFilePath);
    }

}
