/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Objects;

/**
 *
 * @author Dimitris Dedousis <dimitris.dedousis@gmail.com>
 */
public class KeyValuePair<K, V> {

    private K key;
    private V value;

    public KeyValuePair() {
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void add(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KeyValuePair<K, V> other = (KeyValuePair<K, V>) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return true;
    }
}
