package com.viniciuscardoso.arch.vraptor.utility;

import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import com.viniciuscardoso.arch.vraptor.exception.UtilityException;

import java.io.*;
import java.net.SocketException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        } catch (SecurityException e) {
            throw new UtilityException("Não foi possível criar o diretório.", e);
        }
    }

    public static String getFullPath(String resourceKey, String resourceFile) throws Exception {
        return getResourceValue(resourceKey, resourceFile);
    }

    public static String getUniqueFileName(UploadedFile file) {
        String finalFileName = UUID.randomUUID().toString();
        String filename = file.getFileName();
        int start = filename.lastIndexOf(".");
        String extension = filename.substring(start);
        return finalFileName + extension;
    }

    public static String getUniqueFileName(String extension) {
        String finalFileName = UUID.randomUUID().toString();
        return finalFileName + extension;
    }

    public static void writeFile(String filePath, String fileName, UploadedFile uploadedFile) throws IOException {
        writeFile(filePath, fileName, uploadedFile, true);
    }

    public static void writeFile(String filePath, String fileName, InputStream inputStream) throws IOException {
        writeFile(filePath, fileName, inputStream, true);
    }

    public static void writeFile(String filePath, String fileName, UploadedFile uploadedFile, boolean loosePermissions) throws IOException {
        FileOutputStream fosToWrite = null;
        try {
            File file = new File(filePath, fileName);

            if (loosePermissions) {
                file.setReadable(true);
                file.setWritable(true);
                file.setExecutable(true);
            }

            fosToWrite = new FileOutputStream(file);
            org.apache.commons.io.IOUtils.copyLarge(uploadedFile.getFile(), fosToWrite);
            uploadedFile.getFile().close();

        } catch (FileNotFoundException e) {
            throw new UtilityException("Arquivo não encontrado!", e);
        } catch (SocketException e) {
            throw new UtilityException("Falha no envio do arquivo!", e);
        } catch (IOException e) {
            throw new UtilityException("Não foi possível enviar o arquivo!", e);
        } finally {
            if (fosToWrite != null) fosToWrite.close();
        }
    }

    public static void writeFile(String filePath, String fileName, InputStream inputStream, boolean loosePermissions) throws IOException {
        FileOutputStream fosToWrite = null;
        try {
            File file = new File(filePath, fileName);

            if (loosePermissions) {
                file.setReadable(true);
                file.setWritable(true);
                file.setExecutable(true);
            }

            fosToWrite = new FileOutputStream(file);
            org.apache.commons.io.IOUtils.copyLarge(inputStream, fosToWrite);
            inputStream.close();

        } catch (FileNotFoundException e) {
            throw new UtilityException("Arquivo não encontrado!", e);
        } catch (SocketException e) {
            throw new UtilityException("Falha no envio do arquivo!", e);
        } catch (IOException e) {
            throw new UtilityException("Não foi possível enviar o arquivo!", e);
        } finally {
            if (fosToWrite != null) fosToWrite.close();
        }
    }

    public static boolean deleteFile(File file) {
        try {
            return file.delete();
        } catch (SecurityException e) {
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
        } catch (SecurityException e) {
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

    public static void copyFiles(InputStream is, FileOutputStream fos) throws IOException {
        ReadableByteChannel inCh = Channels.newChannel(is);
        FileChannel outCh = fos.getChannel();

        outCh.transferFrom(inCh, 0, Integer.MAX_VALUE);

        is.close();
        fos.close();
        inCh.close();
        outCh.close();
    }

    public static String writeInputStreamToDisk(InputStream inputStream, String extension, String fileDir, String oldFileName) throws Exception {
        String newFilename = IOUtils.getUniqueFileName(extension);
        File oldFile = null;
        if (oldFileName != null) {
            oldFile = new File(fileDir, oldFileName);
        }
            IOUtils.createDir(fileDir);
            IOUtils.writeFile(fileDir, newFilename, inputStream);

        if (oldFile != null) {
            IOUtils.deleteFile(oldFile);
        }
        return newFilename;
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

    public static String writeUploadToDisk(UploadedFile uplFile, String fileDir, String oldFileName) throws Exception {
        if (uplFile == null) return oldFileName; //quando não há alteração vinda do form.

        String newFilename = IOUtils.getUniqueFileName(uplFile);
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

    public static Download downloadFile(String downloadFileName, String storedFileName, String fileDir, String mimeType) throws Exception {
        try {
            File arq = new File(fileDir, storedFileName);
            return new FileDownload(arq, mimeType, ConvertUtils.convertStringToSafeFilename(downloadFileName));
        } catch (Exception e) {
            throw e;
        }
    }

    public static void packCurrentDirectoryContents(String directoryPath, ZipOutputStream zos, String raiz) throws IOException {
        // Iterate through the directory elements
        for (String dirElement : new File(directoryPath).list()) {

            // Construct each element full path
            String dirElementPath = directoryPath + "/" + dirElement;

            // For directories - go down the directory tree recursively
            if (new File(dirElementPath).isDirectory()) packCurrentDirectoryContents(dirElementPath, zos, raiz);
            // For files add the a ZIP entry
            // THIS IS IMPORTANT: a ZIP entry needs to be a relative path to the file
            // so we cut off the path to the directory that is being packed.
            int tamRaiz = raiz.length();
            if (new File(dirElementPath).isDirectory()) {
                ZipEntry ze = new ZipEntry(dirElementPath.substring(tamRaiz + 1, dirElementPath.length()) + "/");
                zos.putNextEntry(ze);
            } else {
                ZipEntry ze = new ZipEntry(dirElementPath.substring(tamRaiz + 1, dirElementPath.length()));
                zos.putNextEntry(ze);

                // Open input stream to packed file
                FileInputStream fis = new FileInputStream(dirElementPath);

                byte[] buf = new byte[1024];
//                byte[] buf = new byte[8192];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }

                // Close the stream
                fis.close();
            }

        }
    }
}
