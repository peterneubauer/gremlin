package com.tinkerpop.gremlin.compiler.functions.g.string;

import com.tinkerpop.gremlin.BaseTest;
import com.tinkerpop.gremlin.compiler.Atom;
import com.tinkerpop.gremlin.compiler.functions.Function;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class StartsWithFunctionTest extends BaseTest {

    public void testStartsWith() {
        Function<Boolean> function = new StartsWithFunction();
        this.stopWatch();
        Atom<Boolean> atom = function.compute(createUnaryArgs("marko", "ma"));
        printPerformance(function.getFunctionName() + " function", 1, "starts with check", this.stopWatch());
        assertTrue(atom.getValue());
        this.stopWatch();
        atom = function.compute(createUnaryArgs("marko", "pa"));
        printPerformance(function.getFunctionName() + " function", 1, "starts with check", this.stopWatch());
        assertFalse(atom.getValue());
    }
}
