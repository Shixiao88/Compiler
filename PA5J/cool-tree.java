// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////


import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();
    public abstract void cgen(PrintStream s);

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol getName();
    public abstract AbstractSymbol getParent();
    public abstract AbstractSymbol getFilename();
    public abstract Features getFeatures();

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;                                 
    public AbstractSymbol get_type() { return type; }           
    public Expression set_type(AbstractSymbol s) { type = s; return this; } 
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }
    public abstract void code(PrintStream s, ClassContext context);

}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'program'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class program extends Program {
    public Classes classes;
    /** Creates "program" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for classes
      */
    public program(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new program(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "program\n");
        classes.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
	    ((Class_)e.nextElement()).dump_with_types(out, n + 1);
        }
    }
    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
	<p>
        Your checker should do the following two things:
	<ol>
	<li>Check that the program is semantically correct
	<li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
	</ol>
	<p>
	You are free to first do (1) and make sure you catch all semantic
    	errors. Part (2) can be done in a second stage when you want
	to test the complete compiler.
    */
    public void semant() {
	/* ClassTable constructor may do some semantic analysis */
	ClassTable classTable = new ClassTable(classes);
	
	/* some semantic analysis code may go here */

	if (classTable.errors()) {
	    System.err.println("Compilation halted due to static semantic errors.");
	    System.exit(1);
	}
    }
    /** This method is the entry point to the code generator.  All of the work
      * of the code generator takes place within CgenClassTable constructor.
      * @param s the output stream 
      * @see CgenClassTable
      * */
    public void cgen(PrintStream s) 
    {
        // spim wants comments to start with '#'
        s.print("# start of generated code\n");

	CgenClassTable codegen_classtable = new CgenClassTable(classes, s);

	s.print("\n# end of generated code\n");
    }

}


/** Defines AST constructor 'class_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_ extends Class_ {
    public AbstractSymbol name;
    public AbstractSymbol parent;
    public Features features;
    public AbstractSymbol filename;
    /** Creates "class_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      * @param a2 initial value for parent
      * @param a3 initial value for features
      * @param a4 initial value for filename
      */
    public class_(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	    ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    public AbstractSymbol getFilename() { return filename; }
    public Features getFeatures()       { return features; }
}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    public AbstractSymbol name;
    public Formals formals;
    public AbstractSymbol return_type;
    public Expression expr;
    /** Creates "method" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      * @param a2 initial value for formals
      * @param a3 initial value for return_type
      * @param a4 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	    ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
        expr.dump_with_types(out, n + 2);
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        if (!(that instanceof method)) return false;
        else {
            method mthat = (method)that;
            return name.equals(mthat.name);
        }
    }

}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    public Expression init;
    /** Creates "attr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      * @param a2 initial value for type_decl
      * @param a3 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        if (!(that instanceof attr)) return false;
        else {
            attr athat = (attr) that;
            return name.equals(athat.name);
        }
    }
}


/** Defines AST constructor 'formal'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formal extends Formal {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    /** Creates "formal" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      * @param a2 initial value for type_decl
      */
    public formal(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formal(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formal\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    public Expression expr;
    /** Creates "branch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      * @param a2 initial value for type_decl
      * @param a3 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	expr.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    public AbstractSymbol name;
    public Expression expr;
    /** Creates "assign" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      * @param a2 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
        expr.dump_with_types(out, n + 2);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * value in $a0 previously will be overwritten
      * will store the name -> value pair in stack
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Assign expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;
        // the evaluate value is in ACC
        expr.code(s, context);
        /* if the name is not in the current scope:
           1. it might be attribute. 2. it might be a temp variable
           check order should follow the above */
        if (!(context.exitInCurrentScope(name))) {
            int offset = table.checkAttrOffset(context.getSo(), name);
            /* if it is attribute, set the attribute's value */
            if (offset != -1) {
                // sw a0, offset * 4(s0)
                int offsetInHeader = offset + CgenSupport.DEFAULT_OBJFIELDS;
                CgenSupport.emitStore(CgenSupport.ACC, offsetInHeader, CgenSupport.SELF, s);
                // currently i decide not push it to the stack.
                // context.appendInEnv(name);
            /* this is not an attribute, it is a temp, push it to stack */
            } else {
                // push $a0
                CgenSupport.emitPush(CgenSupport.ACC, s);
                context.appendInEnv(name);
            }
        } else {
        /* if the name is in current scope:
           1. it might be local var. 2. it might be param(this case the Address offset is positive).
           because the memory stack is top large, bottom small, and I have made the context's offset
           perform the same as stack */
            int offset = (int)context.probeInEnv(name);
            // sw a0, negative offset($fp)
            CgenSupport.emitStore(CgenSupport.ACC, offset, CgenSupport.FP, s);
        }
        CgenSupport.emitComment("finish emmit the Assign expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    public Expression expr;
    public AbstractSymbol type_name;
    public AbstractSymbol name;
    public Expressions actual;
    /** Creates "static_dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for expr
      * @param a2 initial value for type_name
      * @param a3 initial value for name
      * @param a4 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
        expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the static dispatch expr: " + name.str +
                " under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;
        // static type, this should be run under static type
        AbstractSymbol exprType = type_name;
        CgenNode currentStaticNode = (CgenNode) table.lookup(exprType);

        context.enterScopeCopyPrevious();
        //context.enterScope();
        for (Enumeration e = actual.getElements(); e.hasMoreElements(); ) {
            Expression param = (Expression) e.nextElement();
            param.code(s, context);
            // evaluate all arguments, push them on stack
            // push $a0
            CgenSupport.emitPush(CgenSupport.ACC, s);
        }
        // after expr evaluation, $a0 is a ptr to a class layout
        // whatever $a0 is pointed to is the dynamic type, also set $s0 in the new environment
        expr.code(s, context);

        // if expr result in void ($zero), then abort
        int currentLabelIndex = table.getLabelindex();
        int noVoidLabelIndex = currentLabelIndex ++;
        table.setLabelindex(currentLabelIndex);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, noVoidLabelIndex, s);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.FILENAME, s);
        CgenSupport.emitLoadImm(CgenSupport.T1, 1, s);
        CgenSupport.emitJal("_dispatch_abort", s);
        CgenSupport.emitLabelDef(noVoidLabelIndex, s);

        // mw $s0, $a0
        CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, s);
        context.setSo(currentStaticNode);

         /* I can now only set $t1 to dispatch table and check the method offset and then jump and link to it
         * (cannot change $a0, because it will possibly used in the method (like what i found in type_name())
         * because some inheritante method are not use current class's name */
        // use a0 to guide finding the method, because static type maybe not the same as runtime type(I guess)
        int methodOffset = table.checkMethodOffset(currentStaticNode, name);
        if (methodOffset != -1) {
            // set $t1 to be the start of the method
            // lw $t1 , methodOffset($t1)
            // jal $t1
            CgenSupport.emitLoadAddress(CgenSupport.T1, exprType.str + CgenSupport.DISPTAB_SUFFIX, s);
            CgenSupport.emitLoad(CgenSupport.T1, methodOffset, CgenSupport.T1, s);
            CgenSupport.emitJalr(CgenSupport.T1, s);
        }
        CgenSupport.emitComment("finish emmit the static dispatch expr: " + name.str +
                " under class: " + context.getSo().name, s);
        context.exitScope();
    }
}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    public Expression expr;
    public AbstractSymbol name;
    public Expressions actual;
    /** Creates "dispatch" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for expr
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
        expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Dispatch expr: " + name.str +
                " under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;
        context.enterScopeCopyPrevious();
        //context.enterScope();
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression param = (Expression)e.nextElement();
            param.code(s, context);
            // evaluate all arguments, add them on stack
            // push $a0
            CgenSupport.emitPush(CgenSupport.ACC, s);
        }
        // after expr evaluation, $a0 is a ptr to a class layout
        expr.code(s, context);

        // if expr result in void ($zero), then abort
        int currentLabelIndex = table.getLabelindex();
        int noVoidLabelIndex = currentLabelIndex ++;
        table.setLabelindex(currentLabelIndex);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, noVoidLabelIndex, s);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.FILENAME, s);
        CgenSupport.emitLoadImm(CgenSupport.T1, 1, s);
        CgenSupport.emitJal("_dispatch_abort", s);
        CgenSupport.emitLabelDef(noVoidLabelIndex, s);

        AbstractSymbol exprType = expr.get_type();
        if (exprType.equals(TreeConstants.SELF_TYPE)) {
            // static type
            exprType = context.getSo().name;
            // mw $a0, $s0
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
        } else {
            // whatever $a0 is pointed to is the dynamic type, also set $s0 in the new environment
            // mw $s0, $a0
            CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, s);
        }
        CgenNode staticNode = (CgenNode)table.lookup(exprType);
        context.setSo(staticNode);
        /* i can now only set $t1 to dispatch table and check the method offset and then jump and link to it
         * (cannot change $a0, because it will possibly used in the method (like what i found in type_name())
         * because some inheritante method are not use current class's name */
        // use a0 to guide finding the method, because static type maybe not the same as runtime type(I guess)
        int methodOffset = table.checkMethodOffset(staticNode, name);
        if (methodOffset != -1) {
            // set $t1 to be the start of the method
            // lw $t1 , methodOffset($t1)
            // jal $t1
            CgenSupport.emitLoadAddress(CgenSupport.T1, exprType.str + CgenSupport.DISPTAB_SUFFIX, s);
            CgenSupport.emitLoad(CgenSupport.T1, methodOffset, CgenSupport.T1, s);
            CgenSupport.emitJalr(CgenSupport.T1, s);
        }
        CgenSupport.emitComment("finish emmit the Dispatch expr: " + name.str +
                " under class: " + context.getSo().name, s);
        context.exitScope();
    }
}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    public Expression pred;
    public Expression then_exp;
    public Expression else_exp;
    /** Creates "cond" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for pred
      * @param a2 initial value for then_exp
      * @param a3 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
        pred.dump_with_types(out, n + 2);
        then_exp.dump_with_types(out, n + 2);
        else_exp.dump_with_types(out, n + 2);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Cond expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;
        pred.code(s, context);

        int currentLabelIndex = table.getLabelindex();
        //int trueBranchLabel = currentLabelIndex ++;
        int falseBranchLabel = currentLabelIndex ++;
        int finishLabel = currentLabelIndex ++;
        table.setLabelindex(currentLabelIndex);

        // because BEQ must compare two registers, I load 1 into $t1
        CgenSupport.emitLoadBool(CgenSupport.T1, BoolConst.truebool, s);
        // two branch jump definition is not necessary, just put here for clarity
        // previously I think have two labels are ok, now I see that should only have one
        // because it flow linearly
        //CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.T1, trueBranchLabel, s);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.T1, falseBranchLabel, s);
        //CgenSupport.emitLabelDef(trueBranchLabel, s);
        then_exp.code(s, context);
        CgenSupport.emitJump(finishLabel, s);
        CgenSupport.emitLabelDef(falseBranchLabel, s);
        else_exp.code(s,context);
        CgenSupport.emitLabelDef(finishLabel, s);
        CgenSupport.emitComment("finish emmit the Cond expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    public Expression pred;
    public Expression body;
    /** Creates "loop" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for pred
      * @param a2 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
        pred.dump_with_types(out, n + 2);
        body.dump_with_types(out, n + 2);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {

        CgenSupport.emitComment("start emmit the Loop expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;
        int currentLabelIndex = table.getLabelindex();
        int loopLabelIndex = currentLabelIndex ++;
        int finishLabelIndex =  currentLabelIndex ++;
        table.setLabelindex(currentLabelIndex);
        CgenSupport.emitLabelDef(loopLabelIndex, s);

        pred.code(s, context);
        CgenSupport.emitLoadBool(CgenSupport.T1, BoolConst.truebool, s);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.T1, finishLabelIndex, s);
        body.code(s, context);

        CgenSupport.emitJump(loopLabelIndex, s);

        CgenSupport.emitLabelDef(finishLabelIndex, s);

        CgenSupport.emitComment("finish emmit the Loop expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    public Expression expr;
    public Cases cases;
    /** Creates "typcase" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for expr
      * @param a2 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
        expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	    ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_type(out, n);
    }


    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {

        CgenSupport.emitComment("finish emmit the Typcase expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;
        expr.code(s, context);

        // if expr result in void ($zero), then abort
        int currentLabelIndex = table.getLabelindex();
        int noVoidLabelIndex = currentLabelIndex ++;
        int matchLabelIndex = currentLabelIndex ++;
        table.setLabelindex(currentLabelIndex);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, noVoidLabelIndex, s);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.FILENAME, s);
        CgenSupport.emitLoadImm(CgenSupport.T1, 1, s);
        CgenSupport.emitJal("_case_abort2", s);
        CgenSupport.emitLabelDef(noVoidLabelIndex, s);

        AbstractSymbol exprType = expr.get_type();
        CgenNode exprNode = (CgenNode)table.lookup(exprType);
        Vector<AbstractSymbol> ndsNameInBranches = new Vector<>();
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            branch b = (branch)e.nextElement();
            ndsNameInBranches.add(b.type_decl);
        }
        int index = table.findClosestAncestor(ndsNameInBranches, exprNode);
        if (index == -1) {
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
            CgenSupport.emitJal("_case_abort", s);
        } else {
            CgenSupport.emitJump(matchLabelIndex, s);
        }
        branch b = (branch)cases.getNth(index);

        // jump directly to the specific branch and evaluate
        CgenSupport.emitLabelDef(matchLabelIndex, s);
        b.expr.code(s, context);

        CgenSupport.emitComment("finish emmit the Typecase expr under class: " + context.getSo().name, s);
    }


}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    public Expressions body;
    /** Creates "block" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {

        CgenSupport.emitComment("start emmit the Block expr under class: " + context.getSo().name, s);
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
            ((Expression)e.nextElement()).code(s, context);
        }
        CgenSupport.emitComment("finish emmit the Block expr under class: " + context.getSo().name, s);
    }


}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    public AbstractSymbol identifier;
    public AbstractSymbol type_decl;
    public Expression init;
    public Expression body;
    /** Creates "let" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for identifier
      * @param a2 initial value for type_decl
      * @param a3 initial value for init
      * @param a4 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
        dump_AbstractSymbol(out, n + 2, identifier);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump_with_types(out, n + 2);
        body.dump_with_types(out, n + 2);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Let expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;
        init.code(s, context);
        // enter a special scope that maintain all the values in previous scope.
        context.enterScopeCopyPrevious();
        // check if the identifier hides any attribute, local var, etc.
        if (Flags.cgen_debug) {
                 System.out.println("let debug: identifier is " + identifier.str + " under class ");
                System.out.println("let debug: currentScope is " + context.env.toString());
            }
        if (!(context.exitInCurrentScope(identifier))) {
            int offset = table.checkAttrOffset(context.getSo(), identifier);
            /* if it is attribute, set the attribute's value */
            if (offset != -1) {
                // sw a0, offset * 4(s0)
                int offsetInHeader = offset + CgenSupport.DEFAULT_OBJFIELDS;
                CgenSupport.emitStore(CgenSupport.ACC, offsetInHeader, CgenSupport.SELF, s);
                // currently i decide not push it to the stack.
                // context.appendInEnv(name);
            /* this is not an attribute, it is a temp, push it to stack */
            } else {
                // push $a0
                CgenSupport.emitPush(CgenSupport.ACC, s);
                context.appendInEnv(identifier);
            }
        } else {
            int offset = (int)context.probeInEnv(identifier);
            // sw a0, negative offset($fp)
            CgenSupport.emitStore(CgenSupport.ACC, offset, CgenSupport.FP, s);
        }
        body.code(s, context);
        context.exitScope();
        CgenSupport.emitComment("finish emmit the Let expr under class: " + context.getSo().name, s);
    }


}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "plus" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      * @param a2 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }


    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Plus expr under class: " + context.getSo().name, s);
        e1.code(s, context);
        // push $a0 int value
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.ACC, s);
        e2.code(s, context);
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        // pop $t1 int value
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitAdd(CgenSupport.T1, CgenSupport.T1, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.T1, s);
        // create new int object and set the value in it
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int" + CgenSupport.PROTOBJ_SUFFIX, s);
        CgenSupport.emitJal("Object.copy", s);
        CgenSupport.emitJal("Int" + CgenSupport.CLASSINIT_SUFFIX, s);
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitStore(CgenSupport.T1, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitComment("finish emmit the Plus expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "sub" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      * @param a2 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Minus expr under class: " + context.getSo().name, s);
        e1.code(s, context);
        // push $a0 int value
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.ACC, s);
        e2.code(s, context);
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        // pop $t1 int value
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitSub(CgenSupport.T1, CgenSupport.T1, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.T1, s);
        // create new int object and set the value in it
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int" + CgenSupport.PROTOBJ_SUFFIX, s);
        CgenSupport.emitJal("Object.copy", s);
        CgenSupport.emitJal("Int" + CgenSupport.CLASSINIT_SUFFIX, s);
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitStore(CgenSupport.T1, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);

        CgenSupport.emitComment("finish emmit the Minus expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "mul" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      * @param a2 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }


    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Mul expr under class: " + context.getSo().name, s);
        e1.code(s, context);
        // push $a0 int value
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.ACC, s);
        e2.code(s, context);
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        // pop $t1 int value
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitMul(CgenSupport.T1, CgenSupport.T1, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.T1, s);
        // create new int object and set the value in it
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int" + CgenSupport.PROTOBJ_SUFFIX, s);
        CgenSupport.emitJal("Object.copy", s);
        CgenSupport.emitJal("Int" + CgenSupport.CLASSINIT_SUFFIX, s);
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitStore(CgenSupport.T1, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);

        CgenSupport.emitComment("finish emmit the Mul expr under class: " + context.getSo().name, s);
    }

}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "divide" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      * @param a2 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Div expr under class: " + context.getSo().name, s);
        e1.code(s, context);
        // push $a0 int value
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.ACC, s);
        e2.code(s, context);
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        // pop $t1 int value
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitDiv(CgenSupport.T1, CgenSupport.T1, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.T1, s);
        // create new int object and set the value in it
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int" + CgenSupport.PROTOBJ_SUFFIX, s);
        CgenSupport.emitJal("Object.copy", s);
        CgenSupport.emitJal("Int" + CgenSupport.CLASSINIT_SUFFIX, s);
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitStore(CgenSupport.T1, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitComment("finish emmit the Div expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    public Expression e1;
    /** Creates "neg" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Neg expr under class: " + context.getSo().name, s);
         e1.code(s, context);
        // push $a0 int value
        CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitNeg(CgenSupport.T1, CgenSupport.T1, s);
        CgenSupport.emitPush(CgenSupport.T1, s);

        // create new int object and set the value in it
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int" + CgenSupport.PROTOBJ_SUFFIX, s);
        CgenSupport.emitJal("Object.copy", s);
        CgenSupport.emitJal("Int" + CgenSupport.CLASSINIT_SUFFIX, s);
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitStore(CgenSupport.T1, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);

        CgenSupport.emitComment("finish emmit the Neg expr under class: " + context.getSo().name, s);
    }

}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "lt" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      * @param a2 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }


    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Less expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;

        e1.code(s, context);
        // push $a0 int value
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.ACC, s);
        e2.code(s, context);
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        // pop $t1 int value
        CgenSupport.emitPop(CgenSupport.T1, s);

        /* now $t1 is value of first, $a0 is value of second
        *  compare them, if less than, jump to loadBool $a0, true
        *  else, continue to loadBool $a0, false, and then jump to the end
        *  */
        int currentLabelIdnex = table.getLabelindex();
        int trueLabelIndex = currentLabelIdnex ++;
        int finishLabelIndex = currentLabelIdnex ++;
        table.setLabelindex(currentLabelIdnex);
        CgenSupport.emitBlt(CgenSupport.T1, CgenSupport.ACC, trueLabelIndex, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
        CgenSupport.emitJump(finishLabelIndex, s);

        CgenSupport.emitLabelDef(trueLabelIndex, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);

        CgenSupport.emitLabelDef(finishLabelIndex, s);

        CgenSupport.emitComment("finish emmit the Less expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "eq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      * @param a2 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        /* equal will use the trap_handler builtin process, equality_test */
        CgenSupport.emitComment("start emmit the Equal expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;

        e1.code(s, context);
        // push $a0 object
        CgenSupport.emitPush(CgenSupport.ACC, s);
        e2.code(s, context);
        CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, s);
        // pop $t1 int value
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitPush(CgenSupport.ACC, s);
        CgenSupport.emitJal("equality_test", s);

        int currentLabelIndex = table.getLabelindex();
        int falseLabelIndex = currentLabelIndex ++;
        int finishLabelIndex = currentLabelIndex ++;
        table.setLabelindex(currentLabelIndex);

        // $t1 take $a0 initial value, i.e. true
        CgenSupport.emitPop(CgenSupport.T1, s);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.T1, falseLabelIndex, s);

        CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
        CgenSupport.emitJump(finishLabelIndex, s);

        CgenSupport.emitLabelDef(falseLabelIndex, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
        CgenSupport.emitLabelDef(finishLabelIndex, s);

        CgenSupport.emitComment("finish emmit the Equal expr under class: " + context.getSo().name, s);
    }

}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "leq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      * @param a2 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the Less & Equal expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;

        e1.code(s, context);
        // push $a0 int value
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        CgenSupport.emitPush(CgenSupport.ACC, s);
        e2.code(s, context);
        CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.INTCONTOFFSET, CgenSupport.ACC, s);
        // pop $t1 int value
        CgenSupport.emitPop(CgenSupport.T1, s);

        /* now $t1 is value of first, $a0 is value of second
        *  compare them, if less than, jump to loadBool $a0, true
        *  else, continue to loadBool $a0, false, and then jump to the end
        *  */
        int currentLabelIdnex = table.getLabelindex();
        int trueLabelIndex = currentLabelIdnex ++;
        int finishLabelIndex = currentLabelIdnex ++;
        table.setLabelindex(currentLabelIdnex);
        CgenSupport.emitBleq(CgenSupport.T1, CgenSupport.ACC, trueLabelIndex, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
        CgenSupport.emitJump(finishLabelIndex, s);

        CgenSupport.emitLabelDef(trueLabelIndex, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);

        CgenSupport.emitLabelDef(finishLabelIndex, s);
        CgenSupport.emitComment("finish emmit the Less & Equal expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    public Expression e1;
    /** Creates "comp" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("finish emmit the Not expr under class: " + context.getSo().name, s);
        CgenClassTable table = context.getSo().table;
        e1.code(s, context);

        int currentLabelIdnex = table.getLabelindex();
        int trueLabelIndex = currentLabelIdnex ++;
        int finishLabelIndex = currentLabelIdnex ++;
        table.setLabelindex(currentLabelIdnex);
        CgenSupport.emitLoadBool(CgenSupport.T1, BoolConst.truebool, s);
        CgenSupport.emitLoadBool(CgenSupport.T2, BoolConst.falsebool, s);

        CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.T1, trueLabelIndex, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
        CgenSupport.emitJump(finishLabelIndex, s);

        CgenSupport.emitLabelDef(trueLabelIndex, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);

        CgenSupport.emitLabelDef(finishLabelIndex, s);
        CgenSupport.emitComment("finish emmit the Not expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    public AbstractSymbol token;
    /** Creates "int_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
        dump_AbstractSymbol(out, n + 2, token);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the int const expr under class: " + context.getSo().name, s);
        CgenSupport.emitLoadInt(CgenSupport.ACC,
                                (IntSymbol)AbstractTable.inttable.lookup(token.getString()), s);
        CgenSupport.emitComment("finish emmit the int const expr under class: " + context.getSo().name, s);
    }

}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    public Boolean val;
    /** Creates "bool_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
        dump_Boolean(out, n + 2, val);
        dump_type(out, n);
    }


    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the bool const expr under class: " + context.getSo().name, s);
        if (val) {
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
        } else {
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
        }
        CgenSupport.emitComment("finish emmit the bool const expr under class: " + context.getSo().name, s);
    }

}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    public AbstractSymbol token;
    /** Creates "string_const" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, token.getString());
        out.println("\"");
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the string const expr under class: " + context.getSo().name, s);
        CgenSupport.emitLoadString(CgenSupport.ACC,
            (StringSymbol)AbstractTable.stringtable.lookup(token.getString()), s);
        //s.print(CgenSupport.ALIGN);
        CgenSupport.emitComment("start emmit the string const expr under class: " + context.getSo().name, s);
    }

}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    public AbstractSymbol type_name;
    /** Creates "new_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_type(out, n);
    }


    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * this will modify the a0, and not store back, the initilized class's ptr is stored in $a0
      * */
    public void code(PrintStream s, ClassContext context) {
        if (Flags.cgen_debug) {
            System.out.println("new debug, new stuff is: " + type_name.str);
        }
        CgenSupport.emitComment("start emmit the new_ expr under class: " + context.getSo().name, s);
        AbstractSymbol newCl;
        if (type_name.equals(TreeConstants.SELF_TYPE)) {
            newCl = context.getSo().name;
        } else {
            newCl = type_name;
        }
        CgenClassTable table = context.getSo().table;
        CgenNode newNode = (CgenNode)table.lookup(newCl);
        // lw $a0, class prototype label
        CgenSupport.emitLoadAddress(CgenSupport.ACC, newCl.str + CgenSupport.PROTOBJ_SUFFIX, s);
        // ja object.copy
        CgenSupport.emitJal("Object.copy", s);
        // ja object_init
        CgenSupport.emitJal(newCl.str + CgenSupport.CLASSINIT_SUFFIX, s);
        CgenSupport.emitComment("finish emmit the new_ expr under class: " + context.getSo().name, s);
    }
}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    public Expression e1;
    /** Creates "isvoid" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the isvoid expr under class: " + context.getSo().name, s);
        e1.code(s, context);
        if (e1.get_type().equals(TreeConstants.Void)) {
            s.print(CgenSupport.LA + CgenSupport.ACC + " ");
            BoolConst.truebool.codeRef(s);
            s.println("");
        } else {
            s.print(CgenSupport.LA + CgenSupport.ACC + " ");
            BoolConst.falsebool.codeRef(s);
            s.println("");
        }
        CgenSupport.emitComment("finish emmit the isvoid expr under class: " + context.getSo().name, s);
    }


}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the no_expr expr under class: " + context.getSo().name, s);
        // lw $a0, 0
        CgenSupport.emitLoadImm(CgenSupport.ACC, 0, s);
        CgenSupport.emitComment("finish emmit the no_expr expr under class: " + context.getSo().name, s);
    }


}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    public AbstractSymbol name;
    /** Creates "object" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
        dump_AbstractSymbol(out, n + 2, name);
        dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * this will modify the a0, and not store back
      * */
    public void code(PrintStream s, ClassContext context) {
        CgenSupport.emitComment("start emmit the object expr under class: " + context.getSo().name, s);
        // self
        if (name.equals(TreeConstants.self)) {
            // mw a0, s0
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
        }
        /* if the name is not in the current scope:
           1. it might be attribute. 2. it is undefined, this should be an error
           check order should follow the above */
        else if (!(context.exitInCurrentScope(name))) {
             /* if it is attribute, return the attribute's value */
            int attrOffset = context.getSo().table.checkAttrOffset(context.getSo(), name);
            // found the attributes offset
            if (attrOffset != -1) {
                // lw a0, (offset + 3) * 4(self object)
                int offsetInHeader = attrOffset + CgenSupport.DEFAULT_OBJFIELDS;
                CgenSupport.emitLoad(CgenSupport.ACC, offsetInHeader, CgenSupport.SELF, s);
            } else {
                // undefined, this is error
            }

        } else {
        /* if the name is in current scope:
        *   1. it might be local var. 2. it might be param(this case the Address offset is negative).
        *   because the memory stack is top large, bottom small, so the offset in context and memory need swap
        *   update the stack with the value in $a0 onto stack, and store into context */
        /* frames on the stack will be increased only when invoking new function call
        *  the expressions like let.. will be added to currently frames' local value,
        *  but it will create new scope in ClassContext's symbol table, so if it is reachable
        *  is decided by the symbol table, actually they are all in (or more accurately saying surround
        *  the $fp) and the offset is given by the ClassContext */
            int offset= (int)context.probeInEnv(name);
            CgenSupport.emitLoad(CgenSupport.ACC, offset, CgenSupport.FP, s);
        }
        CgenSupport.emitComment("finish emmit the object expr under class: " + context.getSo().name, s);

    }


}


