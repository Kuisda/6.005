// Generated from D:/javaProject/lab-6.005/ps3/src/expressivo/parser/Expression.g4 by ANTLR 4.13.1
package expressivo.parser;

// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(ExpressionParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#calculate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalculate(ExpressionParser.CalculateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#sum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSum(ExpressionParser.SumContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive(ExpressionParser.PrimitiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#multiply}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiply(ExpressionParser.MultiplyContext ctx);
}