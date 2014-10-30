package com.viniciuscardoso.arch.vraptor.utility;

import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import com.viniciuscardoso.arch.vraptor.exception.UtilityException;
import org.hamcrest.Matchers;

import java.io.*;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static ch.lambdaj.Lambda.selectFirst;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 16:01
 */
public class IOUtils {

    private static ResourceBundle bundle = null;

    private static ResourceBundle getResource(String resourceFile) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(resourceFile);
        }
        return bundle;
    }

    public static String getResourceValue(String key, String resourceFile) {
        try {
            return getResource(resourceFile).getString(key);
        } catch (UtilityException e) {
            throw new UtilityException("Não foi possível recuperar o valor. [" + key + "]", e);
        }
    }

    public static String getResourceKeyByValue(String value, String resourceFile) {
        try {
            String retorno = null;
            List<String> keyList = Collections.list(getResource(resourceFile).getKeys());
            for(String key : keyList) {
                if(getResourceValue(key, resourceFile).equals(value)) {
                    retorno = key;
                }
            }
            if(retorno != null) {
                return retorno;
            } else {
                throw new UtilityException("Não foi possível recuperar a chave. [" + value + "]");
            }
        } catch (UtilityException e) {
            throw e;
        }
    }

    public static void createDir(String path) {
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        } catch (UtilityException e) {
            throw new UtilityException("Não foi possível criar o diretório.", e);
        }
    }

    public static String getFullPath(String resourceKey, String resourceFile) throws Exception {
        return getResourceValue(resourceKey, resourceFile);
    }

    public static String getUniqueFileName(UploadedFile file) {
        String finalFileName = UUID.randomUUID().toString();
        String filename = file.getFileName();
        int start = filename.lastIndexOf("");
        String extension = filename.substring(start);
        return finalFileName + extension;
    }

    public static void writeFile(String filePath, String fileName, UploadedFile uploadedFile) throws FileNotFoundException, SocketException, IOException {
        FileOutputStream fileToWrite = null;
        try {
            fileToWrite = new FileOutputStream(new File(filePath, fileName));
            org.apache.commons.io.IOUtils.copyLarge(uploadedFile.getFile(), fileToWrite);
            uploadedFile.getFile().close();

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado!");
        } catch (SocketException e) {
            throw new SocketException("Falha no envio do arquivo!");
        } catch (IOException e) {
            throw new IOException("Não foi possível enviar o arquivo!");
        } finally {
            if (fileToWrite != null) fileToWrite.close();
        }
    }

    public static boolean deleteFile(File file) {
        try {
            return file.delete();
        } catch (UtilityException e) {
            throw new UtilityException("Não foi possível excluir o arquivo.", e);
        }
    }

    public static boolean deleteDir(File dir) {
        try {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    deleteDir(new File(dir, children[i]));
                }
            }

            // agora o diretorio esta vazio, delete ele!
            return dir.delete();
        } catch (UtilityException e) {
            throw new UtilityException("Não foi possível apagar o diretório.",e);
        }
    }

    public static void copyFiles(File originalFile, File destinationFile) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(originalFile);
        FileOutputStream fos = new FileOutputStream(destinationFile);

        FileChannel inCh = fis.getChannel();
        FileChannel outCh = fos.getChannel();

        outCh.transferFrom(inCh, 0, inCh.size());

        fis.close();
        fos.close();
        inCh.close();
        outCh.close();
    }

    public static String writeUploadToDisk(UploadedFile uplFile, String resourceKey, String resourceFile, String oldFileName) throws Exception {
        String fileDir;
        String newFilename = IOUtils.getUniqueFileName(uplFile);
        fileDir = IOUtils.getFullPath(resourceKey, resourceFile);
        if (oldFileName != null) {
            File oldFile = new File(fileDir, oldFileName);
            IOUtils.createDir(fileDir);
            IOUtils.writeFile(fileDir, newFilename, uplFile);
            IOUtils.deleteFile(oldFile);
        } else {
            IOUtils.createDir(fileDir);
            IOUtils.writeFile(fileDir, newFilename, uplFile);
        }
        return newFilename;
    }

    public static Download downloadFile(String downloadFileName, String storedFileName, String resourceKey, String resourceBundle, String mimeType) throws Exception {
        try {
            String fileDir = IOUtils.getFullPath(resourceKey,resourceBundle);
            File arq = new File(fileDir, storedFileName);
            return new FileDownload(arq, mimeType, ConvertUtils.convertStringToSafeFilename(downloadFileName));
        } catch (Exception e) {
            throw e;
        }
    }
}
