package com.alfainmo.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.alfainmo.extra.AlfaException;

public class FileUtils {

    private static final int bytesLect = 1024;

    private FileUtils() {
    }

    public static String consultarHttp(String url) throws AlfaException {
        StringWriter sw = new StringWriter();
        try {
            HttpURLConnection conex = (HttpURLConnection) (new URL(url)).openConnection();
            conex.setConnectTimeout(ConfigUtils.getInstance().getInteger("informacionTimeout", 10000));
            conex.setReadTimeout(conex.getConnectTimeout());
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conex.getInputStream(), "ISO-8859-1"))) {
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    sw.write(inputLine);
                }
            }
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
        return sw.toString();
    }

    /**
     * @param url
     * @param archivo
     * @throws AlfaException
     */
    public static void descargar(String url, String archivo) throws AlfaException {
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.connect();
            try {
                BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
                try (FileOutputStream out = new FileOutputStream(archivo)) {
                    byte[] bdst = new byte[bytesLect];
                    int ldst = in.read(bdst, 0, bdst.length);
                    while (ldst != -1) {
                        out.write(bdst, 0, ldst);
                        ldst = in.read(bdst, 0, bdst.length);
                    }
                } finally {
                    in.close();
                }
            } finally {
                conn.disconnect();
            }

        } catch (MalformedURLException e) {
            throw new AlfaException(e);
        } catch (IOException e) {
            throw new AlfaException(e);
        }
    }

    /**
     * @param ficheroTexto String
     * @return String
     * @throws AlfaException
     */
    public static String leer(String ficheroTexto) throws AlfaException {
        String result = "";

        File file = new File(ficheroTexto);
        if (file.exists()) {
            try {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"))) {
                    String line = br.readLine();
                    while (line != null) {
                        result += line;
                        line = br.readLine();
                    }
                }
            } catch (IOException ex) {
                throw new AlfaException(ex);
            }
        }
        return result;
    }

    /**
     * @param fichero String
     * @param output  OutputStream
     * @throws AlfaException
     */
    public static void transferir(String fichero, OutputStream output) throws AlfaException {

        try {
            try (FileInputStream input = new FileInputStream(fichero)) {
                byte[] buff = new byte[1024];
                int bytesBuff = input.read(buff);
                while (bytesBuff > -1) {
                    output.write(buff, 0, bytesBuff);
                    bytesBuff = input.read(buff);
                }
                output.flush();
            }
        } catch (FileNotFoundException ex) {
            throw new AlfaException(ex);
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
    }

    /**
     * @param ficheroZip
     * @param pathDst
     * @throws AlfaException
     */
    public static void descomprimir(File ficheroZip, String pathDst) throws AlfaException {
        try {
            try (ZipInputStream zipInput = new ZipInputStream(new FileInputStream(ficheroZip))) {
                byte[] bytes = new byte[1024];
                ZipEntry zipEntry = zipInput.getNextEntry();
                while (zipEntry != null) {
                    try (FileOutputStream fo = new FileOutputStream(pathDst + File.separator + zipEntry.getName())) {
                        int leido = zipInput.read(bytes);
                        while (leido > -1) {
                            fo.write(bytes, 0, leido);
                            leido = zipInput.read(bytes);
                        }
                    }
                    zipEntry = zipInput.getNextEntry();
                }
            }
        } catch (FileNotFoundException ex) {
            throw new AlfaException("Archivo \"" + ficheroZip.getAbsolutePath() + "\" no encontrado.", ex);
        } catch (IOException ex) {
            throw new AlfaException("Problema al descomprimir archivo.", ex);
        }
    }
}
