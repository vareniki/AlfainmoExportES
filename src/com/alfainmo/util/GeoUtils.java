package com.alfainmo.util;

import com.alfainmo.beans.GeoPoint;
import com.alfainmo.extra.AlfaException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author dmonje
 */
public class GeoUtils {

    private final static String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";

    /**
     * @param address
     * @return
     * @throws AlfaException
     */
    public static GeoPoint getGeoPointFromAddress(String address) throws AlfaException {

        GeoPoint geoPoint = new GeoPoint();

        // prepare a URL to the geocoder
        URL url;
        try {
            url = new URL(new StringBuilder().append(GEOCODER_REQUEST_PREFIX_FOR_XML).append("?address=").append(URLEncoder.encode(address, "UTF-8")).append("&sensor=false").toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Document geocoderResultDocument = null;
            try {
                conn.connect();
                InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());

                geocoderResultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(geocoderResultInputSource);
            } finally {
                conn.disconnect();
            }

            XPath xpath = XPathFactory.newInstance().newXPath();

            NodeList resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/geometry/location/*", geocoderResultDocument, XPathConstants.NODESET);
            for (int i = 0; i < resultNodeList.getLength(); ++i) {
                Node node = resultNodeList.item(i);
                if ("lat".equals(node.getNodeName())) {
                    geoPoint.setLat(Float.parseFloat(node.getTextContent()));
                }
                if ("lng".equals(node.getNodeName())) {
                    geoPoint.setLon(Float.parseFloat(node.getTextContent()));
                }
            }

            resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/address_component[type/text() = 'administrative_area_level_1']/country[short_name/text() = 'ES']/*", geocoderResultDocument, XPathConstants.NODESET);
            for (int i = 0; i < resultNodeList.getLength(); ++i) {
                Node node = resultNodeList.item(i);
                if ("lat".equals(node.getNodeName())) {
                    geoPoint.setLat(Float.parseFloat(node.getTextContent()));
                }
                if ("lng".equals(node.getNodeName())) {
                    geoPoint.setLon(Float.parseFloat(node.getTextContent()));
                }
            }

        } catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
            throw new AlfaException(e);
        }


        return geoPoint;
    }

}
