package com.tinkerpop.gremlin.compiler.functions.g.number;

import com.tinkerpop.gremlin.compiler.Atom;
import com.tinkerpop.gremlin.compiler.functions.AbstractFunction;
import com.tinkerpop.gremlin.compiler.operations.Operation;

import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class FloatFunction extends AbstractFunction<Float> {

    private static final String FUNCTION_NAME = "float";

    public Atom<Float> compute(final List<Operation> parameters) throws RuntimeException {
        if (parameters.size() == 1) {
            Object object = parameters.get(0).compute().getValue();
            return new Atom<Float>(Float.valueOf(object.toString()));
        } else {
            throw new RuntimeException(this.createUnsupportedArgumentMessage());
        }
    }

    public String getFunctionName() {
        return FUNCTION_NAME;
    }
}