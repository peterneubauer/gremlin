package com.tinkerpop.gremlin;

import com.tinkerpop.gremlin.compiler.GremlinEvaluator;
import com.tinkerpop.gremlin.compiler.GremlinLexer;
import com.tinkerpop.gremlin.compiler.GremlinParser;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 * @author Pavel A. Yaskevich
 */
public class Gremlin {

    public static void evaluate(CharStream input) throws RecognitionException {
        GremlinLexer lexer = new GremlinLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        GremlinParser parser = new GremlinParser(tokens);
        GremlinParser.program_return r = parser.program();

        CommonTree t = (CommonTree) r.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
        GremlinEvaluator walker = new GremlinEvaluator(nodes);

        walker.program();
    }

}
