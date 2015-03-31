/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XmlReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class ReadXmlFiles {

    private static Document doc;
    private NodeList nodeList;
    private Node nope;
    private Element element;
    private List<String> allTheDocuments;
    private DocumentBuilder documentBuilder;
    private DocumentBuilderFactory factory;
    private File[] listOfXmlFiles;

    {
        allTheDocuments = new ArrayList<>();
    }

    public ReadXmlFiles(File[] listOfXmlFiles) throws ParserConfigurationException, SAXException, IOException {
        this.listOfXmlFiles = listOfXmlFiles;
        factory = DocumentBuilderFactory.newInstance();
        documentBuilder = factory.newDocumentBuilder();
    }

    public void startAnalysis() throws SAXException, IOException {
        System.out.println("Analyzing xml docs...");
        for (File xmlFile : listOfXmlFiles) {
            doc = documentBuilder.parse(xmlFile);
            analyzeXml();
        }
        System.out.println("Analyzation completed successfully!");
    }

    private void analyzeXml() {
        doc.getDocumentElement().normalize();
        doc.getDocumentElement().getNodeName();
        nodeList = doc.getElementsByTagName("Document");
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            nope = nodeList.item(temp);
            nope.getNodeName();
            if (nope.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) nope;
                element.getAttribute("id");
                allTheDocuments.add(element.getElementsByTagName("Title").item(0).getTextContent());
                allTheDocuments.add(element.getElementsByTagName("Body").item(0).getTextContent());
            }
        }
    }

    /**
     * In case you want to print the values of the xml files you must use this
     * method INSTEAD of the method that analyze the xml files. Otherwise the
     * reading of the xml files will be performed twice.
     */
    private void printAllTheXmls() {
        doc.getDocumentElement().normalize();
        System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
        nodeList = doc.getElementsByTagName("Document");
        System.out.println("----------------------------");
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            nope = nodeList.item(temp);
            System.out.println("\nCurrent Element : " + nope.getNodeName());
            if (nope.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nope;
                System.out.println("Doc id : " + eElement.getAttribute("id"));
                System.out.println("Title : " + eElement.getElementsByTagName("Title").item(0).getTextContent());
                System.out.println("Body : " + eElement.getElementsByTagName("Body").item(0).getTextContent());
            }
        }
    }

    public List<String> getAllTheDocs() {
        return allTheDocuments;
    }
}
