package com.tinkerpop.gremlin.compiler.operations.util;

import com.tinkerpop.gremlin.compiler.Atom;
import com.tinkerpop.gremlin.compiler.GremlinEvaluator;
import com.tinkerpop.gremlin.compiler.operations.Operation;

/**
 * @author Pavel A. Yaskevich
 */
public class DeclareVariable implements Operation {

    private String name;
    private Operation valueOperation;

    public DeclareVariable(String name, Operation valueOp) {
        this.name = name;
        this.valueOperation = valueOp;
    }

    public Atom compute() {
        Atom varValue = makeAtomValue(this.name, this.valueOperation);
        GremlinEvaluator.declareVariable(this.name, varValue);

        return (GremlinEvaluator.DEBUG) ? varValue : new Atom(null);
    }

    public static void decalareWithInit(String var, Atom value) {
        GremlinEvaluator.declareVariable(var, makeAtomValue(var, value));
    }

    public static void declareEmpty(String var) {
        decalareWithInit(var, new Atom(null));
    }

    public static Atom makeAtomValue(String name, Atom value) {
        value.setPersistent(false);
        value.setVariableName(name);

        return value;
    }

    protected static Atom makeAtomValue(String name, Operation operation) {
        Atom value = operation.compute();
        return makeAtomValue(name, value);
    }

    public Type getType() {
        return Type.STATEMENT;
    }

}
