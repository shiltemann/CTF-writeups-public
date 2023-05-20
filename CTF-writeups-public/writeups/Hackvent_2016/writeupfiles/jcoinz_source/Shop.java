/*
 * Decompiled with CFR 0_118.
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Shop {
    public static int transactionTax = 2;

    public static void sendToAdmin(Account acc) {
        if (acc.payCoins(1337)) {
            String secretMessage = IO.getUserInput("XML Message: ").nextLine();
            DocumentBuilderFactory xmlDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlDocumentBuilder = null;
            Document xmlDocument = null;
            try {
                xmlDocumentBuilder = xmlDocumentBuilderFactory.newDocumentBuilder();
            }
            catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                xmlDocument = xmlDocumentBuilder.parse(new ByteArrayInputStream(secretMessage.getBytes()));
            }
            catch (SAXException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            TransformerFactory xmlTransformerFactory = TransformerFactory.newInstance();
            Transformer xmlTransformer = null;
            try {
                xmlTransformer = xmlTransformerFactory.newTransformer();
            }
            catch (TransformerConfigurationException e1) {
                e1.printStackTrace();
            }
            xmlTransformer.setOutputProperty("omit-xml-declaration", "yes");
            StringWriter xmlWriter = new StringWriter();
            try {
                xmlTransformer.transform(new DOMSource(xmlDocument), new StreamResult(xmlWriter));
            }
            catch (TransformerException e) {
                e.printStackTrace();
            }
            IO.printStatus("+", "Your secret xml message: " + xmlWriter.getBuffer().toString() + "\n\n");
        }
    }

    public static void sendToCharity(Account acc) {
        if (acc.payCoins(IO.getUserInput("Amount of jcoinz to send: ").nextInt())) {
            IO.printStatus("+", "Thank you very much!\n\n");
        }
    }
}

