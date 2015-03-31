/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class Tokenizer {

    private Map<String, Integer> tokensFrequency;
    private List<KeyValuePair> frequencyList;

    {
        tokensFrequency = new HashMap<>();
        frequencyList = new ArrayList<>();
    }

    public Tokenizer() {
    }

    public void createTokens(Analyzer analyzer, String text) throws Exception {
        TokenStream ts = analyzer.tokenStream("dummyField", new StringReader(text));
        ts.reset(); // Resets this stream to the beginning. 
        while (ts.incrementToken()) {
            String token = ts.getAttribute(CharTermAttribute.class).toString();
            if (tokensFrequency.containsKey(token)) {
                tokensFrequency.put(token, tokensFrequency.get(token) + 1);
            } else {
                tokensFrequency.put(token, 1);
            }
        }
        ts.end(); // Perform end-of-stream operations
        ts.close();
    }

    public List<KeyValuePair> getTokensFrequencySortedList() {
        for (Map.Entry<String, Integer> map : tokensFrequency.entrySet()) {
            KeyValuePair<String, Integer> pair = new KeyValuePair<>();
            pair.add(map.getKey(), map.getValue());
            frequencyList.add(pair);
        }
        Collections.sort(frequencyList, new CustomComparator());
        return frequencyList;
    }

    public class CustomComparator implements Comparator<KeyValuePair> {

        @Override
        public int compare(KeyValuePair o1, KeyValuePair o2) {
            if ((Integer) o1.getValue() < (Integer) o2.getValue()) {
                return 1;
            } else if ((Integer) o1.getValue() == (Integer) o2.getValue()) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
