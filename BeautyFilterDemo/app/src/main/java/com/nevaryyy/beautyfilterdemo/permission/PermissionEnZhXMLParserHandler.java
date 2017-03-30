package com.nevaryyy.beautyfilterdemo.permission;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

/**
 * @author nevaryyy
 */
public class PermissionEnZhXMLParserHandler extends DefaultHandler {
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("about")) {
            PermissionEnZh.VERSION = attributes.getValue(0);
            PermissionEnZh.AUTHOR = attributes.getValue(1);
        }
        else if (qName.equals("permissions")) {
            PermissionEnZh.EN_ZH = new HashMap<>();
        }
        else if (qName.equals("permission")) {
            PermissionEnZh.EN_ZH.put(attributes.getValue(0), attributes.getValue(1));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }
}
