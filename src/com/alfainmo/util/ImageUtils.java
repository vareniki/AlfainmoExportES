package com.alfainmo.util;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.AttributedString;
import javax.imageio.ImageIO;

import com.alfainmo.extra.AlfaException;

public class ImageUtils {

    private ImageUtils() {
    }

    /**
     * @param graphics
     * @param w0
     * @param h0
     * @param texto
     */
    private static void annadeMarcaAgua(Graphics graphics, int w0, int h0, String texto) {
        // Create an alpha composite of 50%
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setComposite(alpha);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int ratio;
        if (w0 < 320) {
            ratio = 30;
        } else {
            ratio = 20;
        }

        int tamFuente = (w0 * ratio) / 720;
        g2d.setFont(new Font("Arial", Font.BOLD, tamFuente));

        AttributedString as = new AttributedString(texto);
        as.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);
        as.addAttribute(TextAttribute.FONT, new Font("Arial", Font.BOLD, tamFuente));
        //as.addAttribute(TextAttribute.BACKGROUND, Color.GRAY);

        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(texto, g2d);

        g2d.drawString(as.getIterator(), (w0 - (int) rect.getWidth()) / 2, (h0 - (int) rect.getHeight()) / 2);
    }

    /**
     * @param input
     * @param output
     * @param w0
     * @param maxH
     * @param marcaAgua
     * @throws AlfaException
     */
    public static void redimensionar(InputStream input, OutputStream output,
                                     int w0, int maxH, String marcaAgua) throws AlfaException {

        // El tamaño de la imagen pasa a ser ancho*alto
        Image imagenFnt = null;
        try {
            imagenFnt = ImageIO.read(input);
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }

        if (imagenFnt == null) {
            return;
        }

        int w = imagenFnt.getWidth(null);
        int h = imagenFnt.getHeight(null);

        int h0;
        if (w0 != 0) {
            h0 = h * w0 / w;
        } else {
            w0 = w;
            h0 = h;
        }

        int finalHeight = h0;
        if (maxH > 0 && maxH < finalHeight) {
            finalHeight = maxH;
        }
        BufferedImage imagenDst = new BufferedImage(w0, finalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = imagenDst.getGraphics();
        graphics.drawImage(imagenFnt, 0, 0, w0, h0, null);
        if (marcaAgua != null) {
            annadeMarcaAgua(graphics, w0, h0, marcaAgua);
        }

        try {
            ImageIO.write(imagenDst, "jpeg", output);
        } catch (IOException e) {
            throw new AlfaException(e);
        }

    }

    /**
     * @param archivo
     * @param output
     * @param w
     * @param maxH
     * @param marcaAgua
     * @throws AlfaException
     */
    public static void redimensionar(String archivo, OutputStream output, int w, int maxH, String marcaAgua)
            throws AlfaException {
        try {
            redimensionar(new FileInputStream(archivo), output, w, maxH, marcaAgua);
        } catch (FileNotFoundException ex) {
            throw new AlfaException(ex);
        }
    }
}
