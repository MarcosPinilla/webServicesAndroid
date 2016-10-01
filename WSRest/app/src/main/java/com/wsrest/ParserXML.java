package com.wsrest;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Marcos Pinilla on 30-09-2016.
 */
public class ParserXML {
    public String Parsear(String docXML) throws XmlPullParserException,
            IOException {

        String texto = "";
        //
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(docXML));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                Log.d("setlocation", "Comienza Documento");
            } else if (eventType == XmlPullParser.START_TAG) {
                Log.d("setlocation", "Comienza tag" + xpp.getName());
            } else if (eventType == XmlPullParser.END_TAG) {
                Log.d("setlocation", "Termina tag" + xpp.getName());
            } else if (eventType == XmlPullParser.TEXT) {
                texto = xpp.getText();
                Log.d("setlocation", "Texto" + xpp.getText());

            }
            eventType = xpp.next();

        }

        return texto;
    }
}
