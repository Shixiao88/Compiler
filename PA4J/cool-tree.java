// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////


import java.util.*;
import java.io.PrintStream;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();
    //public abstract SymbolTable addIdInSymTable(SymbolTable nameScopeTable);
}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    //public abstract SymbolTable addIdInSymTable(SymbolTable nameScopeTable);
    //public abstract AbstractSymbol getType();
    //public abstract void traverse(typeEnvironment tenv, SymbolTable nameScopeTable);
    public abstract void checkType(ClassTable cltable);
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
    public abstract AbstractSymbol getName();
    //public abstract SymbolTable addIdInSymTable(SymbolTable nameScopeTable);
    public abstract AbstractSymbol checkType(ClassTable classTable, ClassTable.context context);
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
    //public abstract SymbolTable addIdInSymTable(SymbolTable nameScopeTable);
    public abstract AbstractSymbol checkType(ClassTable classTable, ClassTable.context context);
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
    //public abstract SymbolTable addIdInSymTable(SymbolTable nameScopeTable);
    public abstract AbstractSymbol checkType(ClassTable classTable, ClassTable.context context);
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
    //public abstract SymbolTable addIdInSymTable(SymbolTable nameScopeTable);
    public abstract AbstractSymbol checkType(ClassTable classTable, ClassTable.context context);
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


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            // sm: changed 'n + 1' to 'n + 2' to match changes elsewhere
	    ((Class_)e.nextElement()).dump_with_types(out, n + 2);
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

        /* three traverse process:
            1. traverse to create the class tree structure
            2. traverse to add all the features, record method and attribute
            (above two is done in ClassTable.class)
            3. check type and set type
        * */
        ClassTable classTable = new ClassTable(classes);

        /* some semantic analysis code may go here */
        /* my implementation starts */

        /* if class has error, no need to recover, directly throw the error and
        *  return
        *  */
         if (classTable.errors()) {
            System.err.println("Compilation halted due to static semantic errors.");
            System.exit(1);
        }
        checkType(classTable);
        /* finish my implementation */
        if (classTable.errors()) {
            System.err.println("Compilation halted due to static semantic errors.");
            System.exit(1);
        }
    }

    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
	        Class_ cl = (Class_)e.nextElement();
	        nameScopeTable.enterScope();
	        cl.addIdInSymTable(nameScopeTable);
	        nameScopeTable.exitScope();
        }
        return nameScopeTable;
    }*/

    /*public void traverse(typeEnvironment tenv, SymbolTable nameScopeTable) {
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            // each class has its own name scope
            Class_ cl = (Class_) e.nextElement();
            cl.traverse(tenv, nameScopeTable);
        }
    }*/

    public void checkType(ClassTable classTable) {
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            // each class has its own name scope
            class_c cl = (class_c) e.nextElement();
            cl.checkType(classTable);
        }
    }
}

/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    /** Creates "class_c" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      * @param a2 initial value for parent
      * @param a3 initial value for features
      * @param a4 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name),
                copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public AbstractSymbol getFilename() { return filename; }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParentName()   { return parent; }

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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.enterScope();
        nameScopeTable.addId(name, this);
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	        Feature f = (Feature)e.nextElement();
	        f.addIdInSymTable(nameScopeTable);
        }
        nameScopeTable.exitScope();
        return nameScopeTable;
    }


    public AbstractSymbol getType() {return name;}*/

    /*public void traverse(typeEnvironment tenv, SymbolTable nameScopeTable) {
        tenv.setCurrentClass(this);
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
            Feature f = (Feature) e.nextElement();
            f.traverse(tenv, nameScopeTable);
        }
    }*/

    public void checkType(ClassTable classTable) {
        if (classTable.isIllegalID(name)) {
            classTable.semantError(this).println("class have illegal words: such as 'self', 'SELF_TYPE'.");
        }
        ClassTable.context context = classTable.getContextByClass(this);

        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
            Feature f = (Feature) e.nextElement();
            // each method has its own name scoping
            // but attribute share scope with the current class (but I can enter and use lookup whatsoever?)
            if (f instanceof method) {
                f.checkType(classTable, context);
            } else {
                f.checkType(classTable, context);
            }
        }
    }


    // override equals for menv hashmap.get()
    @Override
    public boolean equals (Object that) {
        if (that == null) {
            return false;
        } else if (this == that) {
            return true;
        } else if (!(that instanceof class_c)) {
            return false;
        } else {
            class_c that_c = (class_c)that;
            return name.equals(that_c.name);
        }
    }
}

/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
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


    public AbstractSymbol getName() {return name;}


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.enterScope();
        nameScopeTable.addId(name, this);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	        Formal f = (Formal)e.nextElement();
	        f.addIdInSymTable(nameScopeTable);
        }
        expr.addIdInSymTable(nameScopeTable);
        nameScopeTable.exitScope();
        return nameScopeTable;
    }*/


    /*public void traverse(typeEnvironment tenv, SymbolTable nameScopeTable) {
        ArrayList<AbstractSymbol> methodPrints = new ArrayList<>();
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	        formalc f = (formalc) e.nextElement();
	        AbstractSymbol ftype = f.type_decl;
	        methodPrints.add(methodPrints.size(), ftype);
        }
        methodPrints.add(methodPrints.size(), return_type);
        tenv.setPrintMethodUnderClass(methodPrints, this, tenv.getCurrentClass());
    }*/

    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        if (classTable.isIllegalID(name)) {
            classTable.semantError(context.getCurrentClass()).println(
                    "method have illegal words: such as 'self', 'SELF_TYPE'.");
        }
        // check all formals, if there is duplication, raise error
        Set<AbstractSymbol> recordedFormals = new HashSet<>();
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
            formalc f = (formalc) e.nextElement();
            if (recordedFormals.contains(f.name)) {
                classTable.semantError(context.getCurrentClass()).println(
                        "duplication formal: " + f.name
                );
            } else if (f.name.equals(TreeConstants.self)) {
                classTable.semantError(context.getCurrentClass()).println(
                        "can not use self as params identifier."
                );
            } else if (f.type_decl.equals(TreeConstants.SELF_TYPE)) {
                classTable.semantError(context.getCurrentClass()).println(
                        "can not use SELF_TYPE as params type."
                );
            } else {
                recordedFormals.add(f.name);
            }
        }

        // check if there is override method from ancestor class.
        // if there is, check if the formal's number and types are same
        ArrayList<AbstractSymbol> acestorMethodPrints = context.checkcAcestorMethod(
                context.getCurrentClass().name, name);
        if (acestorMethodPrints != null) {
            if (acestorMethodPrints.size() != recordedFormals.size() + 1) {
                classTable.semantError(context.getCurrentClass()).println(
                        "Override method must have same number of params");
            }
            int i = 0;
            for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
                formalc f = (formalc) e.nextElement();
                if (!(f.type_decl.equals(acestorMethodPrints.get(i)))) {
                    classTable.semantError(context.getCurrentClass()).println(
                            "Override method must hav same types: error "
                                    + f.type_decl.str + " instead of " + acestorMethodPrints.get(i).str
                    );
                }
                i++;
            }
        }

        // check all formals, add in methodPrint to form: M (C, f ) = (T 1 , . . . , T n , T 0 )
        ArrayList<AbstractSymbol> methodPrints = context.getMethodUnderClass(context.getCurrentClass().name, name);
        // add class type, params type in O env to evaluate expressions type T0
        // OC [SELF TYPE C /self][T 1 /x 1 ] . . . [T n /x n ], M, C |- e : T0
        context.addId(TreeConstants.self, (context.getCurrentClass().name));
        //tenv.addO(TreeConstants.self, (tenv.getCurrentClass()).getName());
        for (int index = 0; index < methodPrints.size() - 1; index ++) {
            AbstractSymbol paramType = methodPrints.get(index);
            context.addId(((formalc)formals.getNth(index)).name, paramType);
        }
        AbstractSymbol type = expr.checkType(classTable, context);
        // recover the O environment
        context.delAllIds();


        // if bottom up analysis type is subtype of return_type

        // SELF_TYPE difference between declared and actual return type
        // if declared is SELF_TYPE, then return type must be SELF_TYPE (because there might be inheritance, so
        //     actual return type cannot be a fixed type)
        // if declare is not SELF_TYPE, return type can be SELF_TYPE

        if (return_type.equals(TreeConstants.SELF_TYPE)) {
            if (!(type.equals(TreeConstants.SELF_TYPE))) {
                classTable.semantError(context.getCurrentClass()).println(
                        type.str + " does not corresponding to the required return type SELF_TYPE"
                );
            }
        } else if (type.equals(TreeConstants.SELF_TYPE)) {
            type = context.getCurrentClass().name;
        }

        if (classTable.isSubTree(return_type, type)) {
            return type;
        } else {
            // if not, error and set return type to be Object Class
            String s = "METHOD " + name.str + " type error on return type: ";
            s += type.str + " ==> " + return_type.str;
            classTable.semantError(context.getCurrentClass()).println(s);
            return TreeConstants.Object_;
        }
    }


    // my override method for mtenv hashmap.get()
    @Override
    public boolean equals(Object that) {
        if (that == null) { return false; }
        else if (this == that) { return true; }
        else if (!(that instanceof method)) { return false; }
        else {
            method thatm = (method)that;
            return this.name.equals(thatm.name);
        }
    }
}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;

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

    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.enterScope();
        nameScopeTable.addId(name, this);
        if (init != null) {
            init.addIdInSymTable(nameScopeTable);
        }
        nameScopeTable.exitScope();
        this.nameScoping = nameScopeTable;
        return nameScopeTable;
    }*/


    /*public void traverse(typeEnvironment tenv, SymbolTable nameScopeTable) {
        nameScopeTable.addId(name, type_decl);
    }*/

    public AbstractSymbol getName() {
        return name;
    }

    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        if (classTable.isIllegalID(name)) {
            classTable.semantError(context.getCurrentClass()).println("" +
                    "attribute have illegal words: such as 'self', 'SELF_TYPE'.");
        }
        AbstractSymbol actualType;
        if (!(init instanceof no_expr)) {
            AbstractSymbol type = init.checkType(classTable, context);
            if (type.equals(TreeConstants.SELF_TYPE)) {
                type = context.getCurrentClass().name;
            }
            if (classTable.isSubTree(type_decl, type)) {
                actualType = type;
            } else {
                String s = "ATTR type error on param type: ";
                s += type.str + " ==> " + type_decl;
                classTable.typeCheckError(s);
                actualType = TreeConstants.Object_;
            }
        // attr no init
        } else {
            actualType = type_decl;
        }
        context.setAttribute(name, actualType);
        return actualType;
    }
}


/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a1 initial value for name
      * @param a2 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.addId(name, this);
        return nameScopeTable;
    }


    public AbstractSymbol getType() {return type_decl;}*/

    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        if (type_decl.equals(TreeConstants.SELF_TYPE)) {
            classTable.semantError(context.getCurrentClass()).println("parameter of method cannot have SELF TYPE");
        }
        context.addId(name, type_decl);
        return type_decl;
    }
}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression expr;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.enterScope();
        nameScopeTable.addId(name, this);
        expr.addIdInSymTable(nameScopeTable);
        nameScopeTable.exitScope();
        return nameScopeTable;
    }


    public AbstractSymbol getType() { return type_decl; }*/

    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        context.addId(name, type_decl);
        AbstractSymbol type = expr.checkType(classTable, context);
        context.delAllIds();
        return type;
    }
}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;

    protected SymbolTable nameScoping;
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


    // naming scope: exit in the same scope as parent
    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        expr.addIdInSymTable(nameScopeTable);
        this.nameScoping = nameScopeTable;
        return nameScopeTable;
    }


    public AbstractSymbol getType() {
        // return its original type;
        return ((TreeNode)nameScoping.probe(name)).getType();
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        if (name.equals(TreeConstants.self)) {
            classTable.semantError(context.getCurrentClass()).print("Cannot assign to self.\t");
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }
        AbstractSymbol originalType = (AbstractSymbol)context.lookup(name);
        AbstractSymbol type = expr.checkType(classTable, context);
        if (classTable.isSubTree(originalType, type)) {
            set_type(type);
            return type;
        } else {
            String s = "ASSIGN type error on param type: ";
            s += type.str + " ==> " + originalType + "\t";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }
    }
}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
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


    // naming scope: the same scope for all expressions.
    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.enterScope();
        expr.addIdInSymTable(nameScopeTable);
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression expr = (Expression)e.nextElement();
            expr.addIdInSymTable(nameScopeTable);
        }
        nameScopeTable.exitScope();
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
         // evaluate e0

        AbstractSymbol exType = expr.checkType(classTable, context);

        if (exType.equals(TreeConstants.SELF_TYPE)) {
            exType = context.getCurrentClass().name;
        }
        if (!(classTable.isSubTree(type_name, exType))) {
            String s = "STATIC DISPATCH type error on return type: ";
            s += exType.str + " ==> " + type_name;
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }

        ArrayList<AbstractSymbol> actualMethodPrints = new ArrayList<>();
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression formal = (Expression) e.nextElement();
            AbstractSymbol formalType = formal.checkType(classTable, context);
            if (formalType.equals(TreeConstants.SELF_TYPE)) {
                formalType = context.getCurrentClass().name;
            }
            actualMethodPrints.add(actualMethodPrints.size(), formalType);
        }
        actualMethodPrints.add(actualMethodPrints.size(), exType);

        ArrayList<AbstractSymbol> methodPrints = context.getMethodUnderClass(type_name, name);

        // error: if cannot find such method name
        if (methodPrints == null) {
            String msg = "cannot find method " + name + " under Class " + exType;
            classTable.semantError(context.getCurrentClass()).println(msg);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }
        // error: if methods numbers are not the same
        if (methodPrints.size() != actualMethodPrints.size()) {
            String msg = "method " + name + " does not have correct number of parameters, should be "
                    + actualMethodPrints.size()
                    + " instead of "
                    + methodPrints.size();
            classTable.semantError(context.getCurrentClass()).println(msg);
        }
        // for method return type, if it is SELF_TYPE(I haven't change them in setter),
        // change it to e0 type(e0 could be other classes inheritant from the current class)
        ArrayList<AbstractSymbol> localMethodPrintsCopy = new ArrayList<>();
        localMethodPrintsCopy.addAll(methodPrints);
        /*if (methodPrints.get(methodPrints.size() - 1).equals(TreeConstants.SELF_TYPE)) {
            localMethodPrintsCopy.set(methodPrints.size() - 1, exType);
        }*/

        int len = actualMethodPrints.size();

        for (int i = 0; i < len - 1; i ++) {
            if (!(classTable.isSubTree(localMethodPrintsCopy.get(i), actualMethodPrints.get(i)))) {
                  String s = "DISPATCH type error on " + i + " param type: ";
                s += localMethodPrintsCopy.get(i).str + " ==> " + actualMethodPrints.get(i).str;
                classTable.semantError(context.getCurrentClass()).println(s);
                set_type(TreeConstants.Object_);
                return TreeConstants.Object_;
            }
        }
        AbstractSymbol returnType = localMethodPrintsCopy.get(methodPrints.size() - 1);

        set_type(returnType);
        return returnType;
    }
}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
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


    // naming scope: same as static dispatch
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.enterScope();
        expr.addIdInSymTable(nameScopeTable);
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression expr = (Expression)e.nextElement();
            expr.addIdInSymTable(nameScopeTable);
        }
        nameScopeTable.exitScope();
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        /*if (Flags.semant_debug) {
            System.out.println("dispatch the current class is : " + tenv.getCurrentClass().name);
        }*/


        /*
        * there is some confusing difference when use
        * dispatch with e0 or without e0.
        * if with e0, and
        *   return type is SELF_TYPE, the actual return type will be e0's evaluation type
        * if without e0, and
        *   return type is SELF_TYPE, the actual return type will be RETURN_TYPE
        * */

        // evaluate e0, exType must set to current class to get the method print.
        AbstractSymbol exType;
        boolean isExprSelf = false;
         /*if (Flags.semant_debug) {
            System.out.println("dispatch begin evaluate expr");
        }*/
        exType = expr.checkType(classTable, context);
          /*if (Flags.semant_debug) {
            System.out.println("dispatch finish evaluate expr");
        }*/
        if (exType.equals(TreeConstants.SELF_TYPE)) {
            exType = context.getCurrentClass().getName();
            isExprSelf = true;
        }

        ArrayList<AbstractSymbol> actualMethodPrints = new ArrayList<>();
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression formal = (Expression) e.nextElement();
            AbstractSymbol formalType = formal.checkType(classTable, context);
            if (formalType.equals(TreeConstants.SELF_TYPE)) {
                formalType = context.getCurrentClass().name;
            }
            actualMethodPrints.add(actualMethodPrints.size(), formalType);
        }
        actualMethodPrints.add(actualMethodPrints.size(), exType);

        ArrayList<AbstractSymbol> methodPrints = context.getMethodUnderClass(exType, name);

        // error: if cannot find such method name
        if (methodPrints == null) {
            String msg = "cannot find method " + name + " under Class " + exType;
            classTable.semantError(context.getCurrentClass()).println(msg);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }
        // error: if methods numbers are not the same
        if (methodPrints.size() != actualMethodPrints.size()) {
            String msg = "method " + name + " does not have correct number of parameters, should be "
                    + actualMethodPrints.size()
                    + " instead of "
                    + methodPrints.size();
            classTable.semantError(context.getCurrentClass()).println(msg);
        }
        // for method return type, if it is SELF_TYPE(I haven't change them in setter),
        // change it to e0 type(e0 could be other classes inheritant from the current class)
        ArrayList<AbstractSymbol> localMethodPrintsCopy = new ArrayList<>();
        localMethodPrintsCopy.addAll(methodPrints);


        if (methodPrints.get(methodPrints.size() - 1).equals(TreeConstants.SELF_TYPE)) {
            if (!(isExprSelf)) {
                localMethodPrintsCopy.set(methodPrints.size() - 1, exType);
            }
        }

        int len = actualMethodPrints.size();

        for (int i = 0; i < len - 1; i ++) {
            if (!(classTable.isSubTree(localMethodPrintsCopy.get(i), actualMethodPrints.get(i)))) {
                  String s = "DISPATCH type error on " + i + " param type: ";
                s += localMethodPrintsCopy.get(i).str + " ==> " + actualMethodPrints.get(i).str;
                classTable.semantError(context.getCurrentClass()).println(s);
                set_type(TreeConstants.Object_);
                return TreeConstants.Object_;
            }
        }
        AbstractSymbol returnType = localMethodPrintsCopy.get(methodPrints.size() - 1);

        set_type(returnType);
        return returnType;
    }
}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
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


    // naming scope: no new scope
    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        then_exp.addIdInSymTable(nameScopeTable);
        else_exp.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol type = pred.checkType(classTable, context);
        if (!(type.equals(TreeConstants.Bool))) {
            String s = "COND type error on prediction type: ";
            s += type.str + " ==> BOOLEAN ";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        }
        AbstractSymbol tthen = then_exp.checkType(classTable, context);
        AbstractSymbol telse = else_exp.checkType(classTable, context);
        AbstractSymbol resType = classTable.findCommonAncestor(tthen, telse);
        set_type(resType);
        return resType;
    }
}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        pred.addIdInSymTable(nameScopeTable);
        body.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol type = pred.checkType(classTable, context);
        if (!(type.equals(TreeConstants.Bool))) {
            String s = "LOOP type error on prediction type: ";
            s += type.str + " ==> BOOLEAN ";
            classTable.semantError(context.getCurrentClass()).println(s);
        }
        set_type(TreeConstants.Object_);
        return TreeConstants.Object_;
    }
}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        expr.addIdInSymTable(nameScopeTable);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            Case c = (Case)e.nextElement();
            c.addIdInSymTable(nameScopeTable);
        }
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        Stack<AbstractSymbol> types = new Stack<>();
        Set<AbstractSymbol> recordedBranch = new HashSet<>();
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            branch b = (branch) e.nextElement();
            if (recordedBranch.contains(b.type_decl)) {
                classTable.semantError(context.getCurrentClass()).println(
                        "duplicate case type: " + b.type_decl
                );
            } else {
                recordedBranch.add(b.type_decl);
            }
        }
        expr.checkType(classTable, context);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            branch b = (branch) e.nextElement();
            AbstractSymbol t = b.checkType(classTable, context);
            types.push(t);
        }
        while (types.size() != 1) {
            AbstractSymbol t = classTable.findCommonAncestor(types.pop(), types.pop());
            types.push(t);
        }
        AbstractSymbol resType = types.pop();
        set_type(resType);
        return resType;
    }
}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	        Expression expr = (Expression)e.nextElement();
            expr.addIdInSymTable(nameScopeTable);
        }
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        for (Enumeration es = body.getElements(); es.hasMoreElements();) {
            Expression returnexpr = (Expression)es.nextElement();
            returnexpr.checkType(classTable, context);
        }
        AbstractSymbol returnType = ((Expression)body.getNth(body.getLength() - 1)).
                checkType(classTable, context);
        set_type(returnType);
        return returnType;
   }
}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;

    protected SymbolTable nameScoping;
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


    // naming scope: the identifier will have new scope, init and body will have the same scope with it.
    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.enterScope();
        nameScopeTable.addId(identifier, this);
        init.addIdInSymTable(nameScopeTable);
        body.addIdInSymTable(nameScopeTable);
        this.nameScoping = nameScopeTable;
        nameScopeTable.exitScope();
        return nameScopeTable;
    }


    public AbstractSymbol getType() {return type_decl;}*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol realIdType;
        if (identifier.equals(TreeConstants.self)) {
            classTable.semantError(context.getCurrentClass()).println(
                    "self cannot be combined in let expression"
            );
            realIdType = TreeConstants.Object_;
        } else {
            realIdType = type_decl;
        }

        // let init
        if (!(init instanceof no_expr)) {
            AbstractSymbol actualInitType = init.checkType(classTable, context);
            /*if (realIdType.equals(TreeConstants.SELF_TYPE)) {
                realIdType = context.getCurrentClass().name;
            }
            if (actualInitType.equals(TreeConstants.SELF_TYPE)) {
                actualInitType = context.getCurrentClass().name;
            }*/
            if (!(classTable.isSubTree(realIdType, actualInitType))) {
                String s = "LET type error on initial expression type: ";
                s += actualInitType.str + " ==> " + realIdType.str;
                classTable.semantError(context.getCurrentClass()).println(s);
                return TreeConstants.Object_;
            } else {
                context.addId(identifier, realIdType);
            }
        } else {
            context.addId(identifier, realIdType);
        }
        AbstractSymbol bodyType = body.checkType(classTable, context);
        context.delAllIds();
        set_type(bodyType);
        return bodyType;
    }
}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;

    /**
     * Creates "plus" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a1         initial value for e1
     * @param a2         initial value for e2
     */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new plus(lineNumber, (Expression) e1.copy(), (Expression) e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        e2.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        AbstractSymbol optype2 = e2.checkType(classTable, context);
        if (!(optype1.equals(TreeConstants.Int))) {
            String s = "PLUS type error on first operant type: ";
            s += optype1.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else if (!(optype2.equals(TreeConstants.Int))) {
            String s = "PLUS type error on second operant type: ";
            s += optype2.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            set_type(TreeConstants.Int);
            return TreeConstants.Int;
        }
    }
}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        e2.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/

    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        AbstractSymbol optype2 = e2.checkType(classTable, context);
        if (!(optype1.equals(TreeConstants.Int))) {
            String s = "SUB type error on first operant type: ";
            s += optype1.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else if (!(optype2.equals(TreeConstants.Int))) {
            String s = "SUB type error on second operant type: ";
            s += optype2.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            set_type(TreeConstants.Int);
            return TreeConstants.Int;
        }
    }

}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        e2.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        AbstractSymbol optype2 = e2.checkType(classTable, context);
        if (!(optype1.equals(TreeConstants.Int))) {
            String s = "MUL type error on first operant type: ";
            s += optype1.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else if (!(optype2.equals(TreeConstants.Int))) {
            String s = "MUL type error on second operant type: ";
            s += optype2.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            set_type(TreeConstants.Int);
            return TreeConstants.Int;
        }
    }
}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        e2.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/

    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        AbstractSymbol optype2 = e2.checkType(classTable, context);
        if (!(optype1.equals(TreeConstants.Int))) {
            String s = "DIV type error on first operant type: ";
            s += optype1.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else if (!(optype2.equals(TreeConstants.Int))) {
            String s = "DIV type error on second operant type: ";
            s += optype2.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            set_type(TreeConstants.Int);
            return TreeConstants.Int;
        }
    }
}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        if (!(optype1.equals(TreeConstants.Int))) {
            String s = "NEG type error on operant type: ";
            s += optype1.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            set_type(TreeConstants.Int);
            return TreeConstants.Int;
        }
    }
}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        e2.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        AbstractSymbol optype2 = e2.checkType(classTable, context);
        if (!(optype1.equals(TreeConstants.Int))) {
            String s = "LT type error on first operant type: ";
            s += optype1.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else if (!(optype2.equals(TreeConstants.Int))) {
            String s = "LT type error on second operant type: ";
            s += optype2.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            set_type(TreeConstants.Bool);
            return TreeConstants.Bool;
        }
    }
}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        e2.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        AbstractSymbol optype2 = e2.checkType(classTable, context);

        // if type1 and type2 are not of type int/string/boolean, then they can be freely compared
        // not considering types
        if (!(optype1.equals(TreeConstants.Int)) &&
                !(optype1.equals(TreeConstants.Str)) &&
                !(optype1.equals(TreeConstants.Bool)) &&
                !(optype2.equals(TreeConstants.Int)) &&
                !(optype1.equals(TreeConstants.Str)) &&
                !(optype1.equals(TreeConstants.Bool))) {
            set_type(TreeConstants.Bool);
            return TreeConstants.Bool;
        } else {
            if (optype1.equals(optype2)) {
                set_type(TreeConstants.Bool);
                return TreeConstants.Bool;
            } else {
                String s = "EQUAL params of INT/STRING/BOOLEAN should be equal type";
                classTable.semantError(context.getCurrentClass()).println(s);
                set_type(TreeConstants.Object_);
                return TreeConstants.Object_;
            }
        }
    }
}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        e2.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        AbstractSymbol optype2 = e2.checkType(classTable, context);
        if (!(optype1.equals(TreeConstants.Int))) {
            String s = "EQUAL type error on first operant type: ";
            s += optype1.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else if (!(optype2.equals(TreeConstants.Int))) {
            String s = "EQUAL type error on second operant type: ";
            s += optype2.str + " ==> INTEGER";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            set_type(TreeConstants.Bool);
            return TreeConstants.Bool;
        }
    }
}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation.

 this is "NOT <expr>" expression
 */
class comp extends Expression {
    protected Expression e1;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/

    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol optype1 = e1.checkType(classTable, context);
        if (!(optype1.equals(TreeConstants.Bool))) {
            String s = "COMP type error on operant type: ";
            s += optype1.str + " ==> BOOLEAN";
            classTable.semantError(context.getCurrentClass()).println(s);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            set_type(TreeConstants.Bool);
            return TreeConstants.Bool;
        }
    }
}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.addId(TreeConstants.Int, token);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        set_type(TreeConstants.Int);
        return TreeConstants.Int;
    }
}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
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


    // boolean no need to be added
    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        set_type(TreeConstants.Bool);
        return TreeConstants.Bool;
    }
}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.addId(token, this);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        set_type(TreeConstants.Str);
        return TreeConstants.Str;
    }
}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;

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

    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        Class_ c = (Class_)nameScopeTable.lookup(type_name);
        if (c != null) {
            nameScopeTable.addId(type_name, c);
        }
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        AbstractSymbol realType;
        if (type_name.equals(TreeConstants.SELF_TYPE)) {
            realType = TreeConstants.SELF_TYPE;
        } else {
            realType = type_name;
        }
        set_type(realType);
        return realType;
    }
}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        e1.addIdInSymTable(nameScopeTable);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        e1.checkType(classTable, context);
        set_type(TreeConstants.Bool);
        return TreeConstants.Bool;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        set_type(TreeConstants.No_type);
        return TreeConstants.No_type;
    }
}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
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


    /*public SymbolTable addIdInSymTable(SymbolTable nameScopeTable) {
        nameScopeTable.addId(name, this);
        return nameScopeTable;
    }*/


    public AbstractSymbol checkType(ClassTable classTable, ClassTable.context context) {
        // for self keyword, if in current nameScope there is no self, return current class name
        // if in current nameScope there is self, return SELF_TYPE

        if (name.equals(TreeConstants.self)) {
            set_type(TreeConstants.SELF_TYPE);
            return TreeConstants.SELF_TYPE;

            /*if (currentNameScopeSelfBinding == null) {
                set_type(context.getCurrentClass().name);
                return context.getCurrentClass().name;
            } else {
                set_type(currentNameScopeSelfBinding);
                return currentNameScopeSelfBinding;
            }*/
        }
        AbstractSymbol resType = context.lookup(name);
        if (resType == null) {
            class_c c = context.getCurrentClass();
            classTable.semantError(c.getFilename(), c)
                    .println("undefined identifier: " + name.str);
            set_type(TreeConstants.Object_);
            return TreeConstants.Object_;
        } else {
            /*if (Flags.semant_debug) {
                if (name.equalString("self", 4)) {
                    System.out.println("should reach here + " +
                            classTable.getContextByClass(context.getCurrentClass()).toString());
                }
            }*/
            set_type(resType);
            return resType;
        }
    }
}

/* I have refactor the structure, see ClassTable.class for explaination

// the environment class for type checking
class typeEnvironment {

    private Stack<symTypePair> oEnv;
    private class_c cEnv;
    private HashMap<methodClassPair, ArrayList<AbstractSymbol>> mEnv;
    private final List<AbstractSymbol> keywords = Arrays.asList(
       TreeConstants.self, TreeConstants.SELF_TYPE
            );

    /*
     * cool has reserved key words that cannot be used as Id, method name, attribute name, class name
     * */
/*
    protected boolean isIllegalID(AbstractSymbol id) {
        return keywords.contains(id);
    }

    private class symTypePair {
        private AbstractSymbol id;
        private AbstractSymbol type;

        symTypePair(AbstractSymbol id, AbstractSymbol type) {
            this.id = id;
            this.type = type;
        }*/
/*
        boolean equalId (nodeTypePair that) {
            return this.id.equals(that.id);
        }

        boolean equalId (AbstractSymbol myid) {
            return this.id.equals(myid);
        }

        boolean equalType (nodeTypePair that) {
            return this.type.equals(that.type);
        }
        */
/*
    }

    private class methodClassPair {
        private Feature m;
        private class_c cl;
        methodClassPair(Feature m, class_c cl) {
           this.m = m;
           this.cl = cl;
       }

       @Override
       public boolean equals (Object that) {
           if (that == null) {
               return false;
           } else if (this == that) {
               return true;
           } else if (!(that instanceof methodClassPair)) {
               return false;
           } else {
               methodClassPair mcThat = (methodClassPair) that;
               return (m.equals(mcThat.m) && cl.equals(mcThat.cl));
           }
       }
    }

    typeEnvironment() {
        oEnv = new Stack<>();
        mEnv = new HashMap<>();
    }

/*
    // O[id] = T
    AbstractSymbol getTypeById(AbstractSymbol id) {
        int len = oEnv.size();
        for (int i = len - 1; i >= 0; i --) {
            if (oEnv.get(i).equalId(id)) {
                return oEnv.get(i).type;
            }
        }
        return null;
    }
*/
    // O[T0/x] for operations {addO, delO}
    // looks like it is the same as name scope table
    /*void addO (AbstractSymbol id, AbstractSymbol type) {
        symTypePair p = new symTypePair(id, type);
        oEnv.push(p);
    }

    void delO (int num) {
        if (num > oEnv.size()) {
            Utilities.fatalError("cannot pop more than the environment has");
        }
        for (int i = 0; i < num; i ++) {
            oEnv.pop();
        }
    }

    void setCurrentClass (class_c cl) {
        cEnv = cl;
    }

    class_c getCurrentClass () {
        return cEnv;
    }

    private AbstractSymbol getMethodName(methodClassPair pair) {
        return pair.m.getName();
    }


    // M(C, f ) = (T 1 , . . . , T n , T 0 )
    ArrayList<AbstractSymbol> getPrintMethodUnderClass (Feature m, class_c cl) {
        ArrayList<AbstractSymbol> types = mEnv.get(new methodClassPair(m, cl));
        return types;
    }


    ArrayList<AbstractSymbol> getPrintMethodUnderClass(
            AbstractSymbol featureName, AbstractSymbol clName, ClassTable clsTable) {
        ArrayList<AbstractSymbol> types;
        class_c cl = clsTable.getClassByName(clName);

        if (cl != null) {
            while (!(clName.equals(TreeConstants.No_class))) {
                types = getPrintMethodUnderClass(featureName, clName);
                if (types != null) {
                    return types;
                } else {
                    clName = cl.getParentName();
                    cl = clsTable.getClassByName(clName);
                }
            }

        }
        return null;
    }

    private ArrayList<AbstractSymbol> getPrintMethodUnderClass(AbstractSymbol featureName, AbstractSymbol clName){
        // iterate the map and found the matching feature name and class name
        for (Map.Entry<methodClassPair, ArrayList<AbstractSymbol>> entry : mEnv.entrySet()) {
            if (entry.getKey().m.getName().equals(featureName) &&
                    ((class_c) entry.getKey().cl).getName().equals(clName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    // add the types foot print of method under class;
    void setPrintMethodUnderClass (ArrayList<AbstractSymbol> types, Feature m, class_c cl) {
        mEnv.put(new methodClassPair(m, cl), types);
    }

    // printable method for O env, for debug
    String showOenv () {
        String res = "";
        for (int i = oEnv.size() - 1; i >= 0; i --) {
            symTypePair p = oEnv.get(i);
            res += "Tree node is : ";
            res += p.id.str;
            res += " , its type is : ";
            res += p.type.str;
            res += "\n--------------------------------\n";
        }
        return res;
    }

    // printable method for M env, for debug
    String showMenv () {
        String res = "";
        for (Map.Entry<methodClassPair, ArrayList<AbstractSymbol>> entry : mEnv.entrySet()) {
            String clstr = ((class_c)(entry.getKey().cl)).getName().str;
            String mstr = entry.getKey().m.getName().str;
            res += "method : ";
            res += mstr;
            res += " under class ";
            res += clstr;
            ArrayList<AbstractSymbol> args = entry.getValue();
            res += " { ";
            for (AbstractSymbol s : args) {
                res += s.str;
                res += " ; ";
            }
            res += " } \n";
        }
        return res;
    }
    */


