package com.alfainmo.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.alfainmo.beans.BeanObject;
import com.alfainmo.extra.AlfaException;

public class ConvertUtils {

    private static final int BUFFER = 2048;

    /**
     * @param obj       int
     * @param zipOutput ZipOutputStream
     * @throws IOException
     */
    private static void serializarZip(Object obj, ZipOutputStream zipOutput) throws IOException {

        ZipEntry entry = new ZipEntry(UUID.randomUUID().toString());
        zipOutput.putNextEntry(entry);
        try {
            ObjectOutputStream out = new ObjectOutputStream(zipOutput);
            out.writeObject(obj);
            out.flush();
        } finally {
            zipOutput.closeEntry();
        }
    }

    /**
     * @param archivo File
     * @param pathDst String
     * @throws AlfaException
     */
    public static void descomprimirZip(File archivo, String pathDst) throws AlfaException {
        try {
            try (ZipInputStream zipInput = new ZipInputStream(new FileInputStream(archivo))) {
                byte[] bytes = new byte[BUFFER];
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
            throw new AlfaException("Archivo \"" + archivo.getAbsolutePath() + "\" no encontrado.", ex);
        } catch (IOException ex) {
            throw new AlfaException("Problema al descomprimir archivo.", ex);
        }
    }

    /**
     * @param cadena
     * @param clave
     * @return
     */
    public static String desencriptarCadena(String cadena, byte clave) {
        if (cadena == null) {
            return null;
        }
        byte[] bytes = Base64Utils.decode(cadena);

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ clave);
        }
        return new String(bytes);
    }

    /**
     * @param <T>
     * @param xmls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends BeanObject> Collection<T> deserializarXml(String xmls) {
        if (xmls == null) {
            return null;
        }
        Collection<T> result = new ArrayList<>();

        for (String xml : xmls.split("<;>")) {
            if (!xml.startsWith("<?xml")) {
                continue;
            }
            XMLDecoder xmlDecoder = new XMLDecoder(new ByteArrayInputStream(xml.getBytes()));
            result.add((T) xmlDecoder.readObject());
        }

        return result;
    }

    /**
     * @param info String
     * @return Collection
     * @throws AlfaException
     */
    @SuppressWarnings("rawtypes")
    public static Collection<Collection> deserializarZip(String info) throws AlfaException {
        Collection<Collection> result = new ArrayList<>();

        byte[] bytes = Base64Utils.decode(info);
        ByteArrayInputStream bytesInput = new ByteArrayInputStream(bytes);
        ZipInputStream zipInput = new ZipInputStream(bytesInput);
        try {
            ZipEntry zipEntry = zipInput.getNextEntry();
            while (zipEntry != null) {
                result.add((Collection) (new ObjectInputStream(zipInput)).readObject());
                zipEntry = zipInput.getNextEntry();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new AlfaException(e.getMessage());
        }
        return result;
    }

    /**
     * @param cadena
     * @param clave
     * @return
     */
    public static String encriptarCadena(String cadena, byte clave) {
        if (cadena == null) {
            return null;
        }
        byte[] bytes = new byte[cadena.length()];

        for (int i = 0; i < cadena.length(); i++) {
            bytes[i] = (byte) (cadena.charAt(i) ^ clave);
        }
        return Base64Utils.encodeBytes(bytes);
    }

    /**
     * @param archivos
     * @param nombreZip
     * @return
     * @throws AlfaException
     */
    public static String generarZip(List<String> archivos, String nombreZip) throws AlfaException {
        File archivoZip = new File(nombreZip);
        if (archivoZip.exists()) {
            archivoZip.delete();
        }
        byte[] data = new byte[BUFFER];
        try {
            FileOutputStream dest = new FileOutputStream(archivoZip);
            try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest))) {
                out.setLevel(9);
                for (String archivo : archivos) {
                    String nombreArchivo = (new File(archivo)).getName();
                    out.putNextEntry(new ZipEntry(nombreArchivo));
                    try (FileInputStream input = new FileInputStream(archivo)) {
                        int count;
                        while ((count = input.read(data, 0, BUFFER)) != -1) {
                            out.write(data, 0, count);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
        return nombreZip;
    }

    /**
     * @param bean
     * @return
     */
    public static String serializarXml(BeanObject bean) {
        if (bean == null) {
            return null;
        }
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        try (XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(ba))) {
            xmlEncoder.writeObject(bean);
        }
        return ba.toString();
    }

    /**
     * @param <T>
     * @param beans
     * @param maxLong
     * @return
     */
    public static <T extends BeanObject> String serializarXml(Collection<T> beans, int maxLong) {

        if (beans == null) {
            return null;
        }
        String xml, xmls = "";
        for (BeanObject bean : beans) {
            xml = serializarXml(bean);
            if ((xml.length() + xmls.length()) > maxLong) {
                break;
            }
            xmls += xml + "<;>";
        }
        return xmls;
    }

    /**
     * @param objects Collection
     * @return String
     * @throws AlfaException
     */
    @SuppressWarnings("rawtypes")
    public static String serializarZip(Collection<Object> objects) throws AlfaException {
        if (objects == null) {
            return null;
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ZipOutputStream zipOutput = new ZipOutputStream(bytes);
        try {
            try {
                Iterator iObjects = objects.iterator();
                // ESCA-JAVA0254:
                while (iObjects.hasNext()) {
                    serializarZip(iObjects.next(), zipOutput);
                }
            } finally {
                zipOutput.close();
            }
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
        return Base64Utils.encodeBytes(bytes.toByteArray());
    }

    private ConvertUtils() {
    }

}
