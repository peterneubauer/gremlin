package com.tinkerpop.gremlin.compiler.functions.g.number;

import com.tinkerpop.gremlin.compiler.Atom;
import com.tinkerpop.gremlin.compiler.functions.AbstractFunction;
import com.tinkerpop.gremlin.compiler.operations.Operation;

import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class LongFunction extends AbstractFunction<Long> {

    private static final String FUNCTION_NAME = "long";

    public Atom<Long> compute(final List<Operation> parameters) throws RuntimeException {
        if (parameters.size() == 1) {
            Object object = parameters.get(0).compute().getValue();
            Double temp = Double.valueOf(object.toString());
            return new Atom<Long>(temp.longValue());
        } else {
            throw new RuntimeException(this.createUnsupportedArgumentMessage());
        }
    }

    public String getFunctionName() {
        return FUNCTION_NAME;
    }
}