package com.alfainmo.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

public class FmtUtils {

    protected static NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
    protected static DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
    protected static DateFormat dtf2 = DateFormat.getDateInstance(DateFormat.LONG);
    protected static DateFormat dtf3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static String filterNonUtf8Chars(String s) {

        if (s == null) return null;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();i++){
            if (Character.isHighSurrogate(s.charAt(i))) {
                continue;

            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public static String fmtDireccion(
            String nombreVia, int numeroVia, String escalera, String piso, String puerta) {

        if (nombreVia == null) {
            return "";
        }
        StringBuilder result = new StringBuilder(nombreVia);
        if (numeroVia > 0) {
            result.append(", ").append(numeroVia);
        }
        result.append(". ");
        if (escalera != null && !escalera.equals("")) {
            result.append("Esc. ").append(escalera).append(". ");
        }
        if (piso != null && !piso.equals("") && !piso.equals("0")) {
            result.append(piso).append(" ");
            if (puerta != null) {
                result.append(puerta);
            }
        }

        return result.toString().trim();
    }

    public static String fmtFechaH(java.util.Date fecha) {
        return dtf3.format(fecha);
    }

    public static String fmtFechaL(java.util.Date fecha) {
        return dtf2.format(fecha);
    }

    public static String fmtFechaS(java.util.Date fecha) {
        return dtf.format(fecha);
    }

    public static String fmtMoneda(double valor) {
        if (valor == 0) {
            return "";
        } else {
            return nf.format(valor) + " &euro;";
        }
    }

    public static String fmtMoneda(long valor) {
        if (valor == 0) {
            return "";
        } else {
            return nf.format(valor) + " &euro;";
        }
    }

    public static String fmtNumero(double valor) {
        if (valor == 0) {
            return "";
        } else {
            return nf.format(valor);
        }
    }

    public static String fmtNumero(int valor, int digitos) {
        String scantidad = "" + valor;
        while (scantidad.length() < digitos) {
            scantidad = "0" + scantidad;
        }
        return scantidad;
    }

    public static String fmtNumero(long valor) {
        if (valor == 0) {
            return "";
        } else {
            return nf.format(valor);
        }
    }

    public static String nullToEmpty(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    /**
     * @param str
     * @return
     */
    public static String sinTildes(String str) {
        char[][] chars = new char[][]{{'·', 'a'}, {'È', 'e'}, {'Ì', 'i'}, {'Û', 'o'}, {'˙', 'u'}, {'Ò', 'n'}, {'¸', 'u'}};
        for (char[] element : chars) {
            str = str.replace(element[0], element[1]);
        }
        return str;
    }

    /**
     * @param valor
     * @return
     */
    public static String fmtStringUC(String valor) {
        if (valor != null) {
            valor = valor.toLowerCase();
            StringBuilder result = new StringBuilder("");
            StringTokenizer st = new StringTokenizer(valor);
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                result.append(token.substring(0, 1).toUpperCase()).append(token.substring(1)).append(" ");
            }
            valor = result.toString().trim();
        }
        return (valor != null) ? valor : "";
    }

    /**
     * @param valor
     * @param longMax
     * @return
     */
    public static String fmtStringUC(String valor, int longMax) {
        if (valor != null) {
            if (valor.length() > longMax) {
                valor = valor.substring(0, longMax) + "...";
            }
            valor = fmtStringUC(valor);
        }
        return (valor != null) ? valor : "";
    }

    /**
     * @param value
     * @return
     */
    public static String fmtStringUTF8(String value) {
        String result;
        try {
            String str = new String(value.getBytes(), "ISO-8859-1");
            result = new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            result = value;
        }
        return result;
    }

    /**
     * @param txt
     * @param maxCount
     * @return
     */
    public static String limitarTexto(String txt, int maxCount) {
        if (txt == null) {
            txt = "";
        }
        txt = txt.replaceAll("\r|\n", "<br />");
        if (txt.length() >= maxCount - 3) {
            txt = txt.substring(0, maxCount - 3) + "...";
        }

        return txt;
    }

    /**
     * @param valor
     * @param longMax
     * @return
     */
    public static String truncar(String valor, int longMax) {
        if (valor != null) {
            if (longMax > valor.length()) {
                longMax = valor.length();
            }
            return valor.substring(0, longMax);
        } else {
            return "";
        }
    }

    public static String notNull(String val) {
        return (val != null) ? val : "";
    }

    public static String htmlToPlainText(String html) {
        if (html == null) {
            return "";
        }
        html = html.replaceAll("\\<.*?>", "");
        html = html.replaceAll("\\<.*?/>", "");
        return html;

    }

    public static String convertCharset(String txt) {
        if (txt == null) {
            return "";
        }
        Charset charset = Charset.forName("ISO-8859-15");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        String s = null;
        try {
            ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(txt));
            CharBuffer cbuf = decoder.decode(bbuf);
            s = cbuf.toString();
        } catch (CharacterCodingException e) {
        } catch (NullPointerException e) {
            s = "";
        }
        return s;
    }

    private FmtUtils() {
    }
}
