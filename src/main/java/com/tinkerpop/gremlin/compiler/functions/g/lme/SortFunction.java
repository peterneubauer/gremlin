package com.tinkerpop.gremlin.compiler.functions.g.lme;

import com.tinkerpop.gremlin.compiler.Atom;
import com.tinkerpop.gremlin.compiler.functions.AbstractFunction;
import com.tinkerpop.gremlin.compiler.functions.FunctionHelper;
import com.tinkerpop.gremlin.compiler.operations.Operation;

import java.util.*;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SortFunction extends AbstractFunction<Object> {

    private static final String FUNCTION_NAME = "sort";
    private static final String VALUE = "value";
    private static final String KEY = "key";
    private static final String KEY_VALUE_ERROR = "Must specify whether to sort by key or by value";

    public Atom<Object> compute(final List<Operation> parameters) throws RuntimeException {
        if (parameters.size() == 2) {
            // sort iterable
            Iterable iterable = (Iterable) parameters.get(0).compute().getValue();
            Boolean reverse = (Boolean) parameters.get(1).compute().getValue();
            List sortedList = new ArrayList();
            FunctionHelper.fillCollection(iterable, sortedList);
            Collections.sort(sortedList);
            if (reverse)
                Collections.reverse(sortedList);
            return new Atom<Object>(sortedList);
        } else if (parameters.size() == 3) {
            // sort map
            Map map = (Map) parameters.get(0).compute().getValue();
            String keyOrValue = (String) parameters.get(1).compute().getValue();
            Boolean reverse = (Boolean) parameters.get(2).compute().getValue();
            if (keyOrValue.equals(VALUE)) {
                return new Atom<Object>(sortByValue(map, reverse));
            } else if (keyOrValue.equals(KEY)) {
                return new Atom<Object>(sortByKey(map, reverse));
            } else {
                throw new RuntimeException(KEY_VALUE_ERROR);
            }

        } else {
            throw new RuntimeException(this.createUnsupportedArgumentMessage());
        }
    }

    public String getFunctionName() {
        return FUNCTION_NAME;
    }

    private static Map sortByValue(final Map map, final boolean reverse) {
        List mapKeys = new ArrayList(map.keySet());
        List mapValues = new ArrayList(map.values());
        Collections.sort(mapValues);

        if (reverse)
            Collections.reverse(mapValues);

        LinkedHashMap sortedMap = new LinkedHashMap();
        HashMap oldMap = new HashMap(map);

        Iterator ittyValue = mapValues.iterator();
        while (ittyValue.hasNext()) {
            Object value = ittyValue.next();
            Iterator ittyKey = mapKeys.iterator();
            while (ittyKey.hasNext()) {
                Object key = ittyKey.next();
                String comp1 = map.get(key).toString();
                String comp2 = value.toString();
                if (comp1.equals(comp2)) {
                    map.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put(key, value);
                    break;
                }
            }
        }

        map.putAll(oldMap);
        return sortedMap;
    }

    private static Map sortByKey(Map map, final boolean reverse) {
        map = new TreeMap(map);
        if (reverse)
            map = ((TreeMap) map).descendingMap();
        return map;
    }
}
