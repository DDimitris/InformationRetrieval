/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author Dimitris Dedousis <dedousis@aueb.gr>
 */
public class Utils {

    public static final String FILES_DIR = "C:\\Users\\Dimitris\\Desktop\\lisa";
    public static final String XML_FILES_DIR = "C:\\Users\\Dimitris\\Desktop\\lisaXML";

    public static String fileToString(File path) throws IOException {
        return new String(Files.readAllBytes(path.toPath()));
    }

    public static File[] getListOfFiles() {
        File f = new File(FILES_DIR);
        return f.listFiles();
    }

    public static File[] getListOfXMLFiles() {
        File f = new File(XML_FILES_DIR);
        return f.listFiles();
    }
}
