/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XmlWriter;

import Utils.Utils;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Dimitris Dedousis <dedousis@aueb.gr>
 */
public class WriteXMLFile {

    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private TransformerFactory transformerFactory;
    private Transformer transformer;
    private DOMSource source;
    private StreamResult result;
    private Element rootElement;
    private Element document;
    private int fileCounter = 0;
    private File[] filesToBeRead;

    public WriteXMLFile(File[] filesToBeRead) {
        this.filesToBeRead = filesToBeRead;
    }

    public void beginCreation() throws Exception {
        System.out.println("Starting the XML file creation...");
        for (File a : filesToBeRead) {
            fileCounter++;
            docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setValidating(true);
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            String fileToString = Utils.fileToString(a);
            splitDocsInsideFiles(fileToString.toLowerCase(), a.getName());
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(doc);
            result = new StreamResult(new File(Utils.XML_FILES_DIR + "\\lisa" + fileCounter + ".xml"));
            transformer.transform(source, result);
        }
        System.out.println("Xml files created!!");
    }

    private void splitDocsInsideFiles(String fileString, String fileName) {
        writeRootTag(fileName);
        String[] docSplit = fileString.split("\\*+\\s+"); //Splits the file on the * character
        for (String docs : docSplit) {
            String[] everyDocInFileSplited = docs.split("\\s\\r\\n"); //Splits every document in double line breaks.
            for (String splitedDocs : everyDocInFileSplited) {
                if (splitedDocs.startsWith("document ")) {
                    String[] insideTitleSplit = splitedDocs.split("\\r\\n");//insideTitleSplit holds the document tag and the title tag
                    writeDocTag(insideTitleSplit[0]);
                    String title = "";
                    for (int i = 1; i <= insideTitleSplit.length - 1; i++) {
                        title += insideTitleSplit[i];
                    }
                    writeTitleTag(title);
                } else {
                    writeBodyTag(splitedDocs);
                }
            }
        }
    }

    private void writeRootTag(String fileName) {
        rootElement = doc.createElement(fileName);
        doc.appendChild(rootElement);
    }

    private void writeDocTag(String docID) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(docID);
        int n = 0;
        while (m.find()) {
            n = Integer.parseInt(m.group());
        }
        document = doc.createElement("Document");
        rootElement.appendChild(document);
        Attr attr = doc.createAttribute("id");
        attr.setValue(String.valueOf(n));
        document.setAttributeNode(attr);
    }

    private void writeTitleTag(String title) {
        Element docTitle = doc.createElement("Title");
        docTitle.appendChild(doc.createTextNode(title));
        document.appendChild(docTitle);
    }

    private void writeBodyTag(String body) {
        Element docBody = doc.createElement("Body");
        docBody.appendChild(doc.createTextNode(body));
        document.appendChild(docBody);
    }
}
