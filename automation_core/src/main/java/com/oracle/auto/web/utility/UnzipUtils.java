package com.oracle.auto.web.utility;

import junit.framework.Assert;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by vemurl on 8/31/2016.
 */
public class UnzipUtils {

    private static int individualZipFileLength=PropertyConfiger.instance().getEnvData("zip.file.size",1024);

    public static UnzipUtils getInstance() {
        return new UnzipUtils();
    }

    public int unzip(String zipFilePath, String destDirectory, String... ignoreFile) throws  IOException{
        int numberOfFiles=0;
        ZipFile zipFile = new ZipFile(new File(zipFilePath));
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(destDirectory,  entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else if(!(entry.getName().equals(ignoreFile[0]))){
                    numberOfFiles++;
                    entryDestination.getParentFile().mkdirs();
                    InputStream in = zipFile.getInputStream(entry);
                    OutputStream out = new FileOutputStream(entryDestination);
                    IOUtils.copy(in, out);
                    IOUtils.closeQuietly(in);
                    out.close();
                    Assert.assertTrue("length of file "+ entryDestination.getName() + " is less than 1KB",entryDestination.length()>individualZipFileLength);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            zipFile.close();
        }
        return numberOfFiles;
    }


}
