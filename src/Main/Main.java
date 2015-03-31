/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import XmlReader.ReadXmlFiles;
import XmlWriter.WriteXMLFile;
import Utils.*;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class Main {

    public static void main(String[] args) throws Exception {
        WriteXMLFile writer = new WriteXMLFile(Utils.getListOfFiles());
        ReadXmlFiles reader = new ReadXmlFiles(Utils.getListOfXMLFiles());
        writer.beginCreation();
        reader.startAnalysis();
        List<String> allTheDocs = reader.getAllTheDocs();
        Analyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET); //CharArraySet is used to show the stop words too!
        Tokenizer tokenizer = new Tokenizer();
        for (String s : allTheDocs) {
            tokenizer.createTokens(analyzer, s);
        }
        for (KeyValuePair pair : tokenizer.getTokensFrequencySortedList()) {
            System.out.println(pair.getKey() + "\t\t" + pair.getValue());
        }
    }
}
