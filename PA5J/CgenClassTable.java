/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file




import java.io.PrintStream;
import java.util.*;

/** This class is used for representing the inheritance tree during code
    generation. You will need to fill in some of its methods and
    potentially extend it in other useful ways. */
class CgenClassTable extends SymbolTable {

    /**
     * All classes in the program, represented as CgenNode
     */
    private Vector nds;

    /**
     * This is the stream to which assembly instructions are output
     */
    private PrintStream str;

    private int stringclasstag;
    private int intclasstag;
    private int boolclasstag;

    /* start my implementation */
    private IntSymbol zeroSymbol;
    private StringSymbol emptyStrSymbol;

    final static String[] dMs = {
        "Object.copy",
        "Object.abort",
        "Object.type_name",
        "IO.out_string",
        "IO.out_int",
        "IO.in_string",
        "IO.in_int",
        "String.length",
        "String.concat",
        "String.substr",
        "equality_test",
        "dispatch_abort",
        "case_abort",
        "case_abort2"
    };
    private Vector<String> definedMethods = new Vector<>(Arrays.asList(dMs));


    private int labelindex;
    private int classtag;
    private  Map<CgenNode, Integer> clToTag;
    protected Map<String, Integer> nameToLabelIndex;
    private Map<CgenNode, Vector<methodInheri>> classToMethodTable;
    private Map<CgenNode, Vector<attr>> classToAttrsTable;
    ClassContext context;
    /* finish my implementation */


    // The following methods emit code for constants and global
    // declarations.

    /**
     * Emits code to start the .data segment and to
     * declare the global names.
     */
    private void codeGlobalData() {
        // The following global names must be defined first.

        CgenSupport.emitComment("start emit global data", str);
        str.print("\t.data\n" + CgenSupport.ALIGN);
        str.println(CgenSupport.GLOBAL + CgenSupport.CLASSNAMETAB);
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitProtObjRef(TreeConstants.Main, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitProtObjRef(TreeConstants.Int, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitProtObjRef(TreeConstants.Str, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        BoolConst.falsebool.codeRef(str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        BoolConst.truebool.codeRef(str);
        str.println("");
        str.println(CgenSupport.GLOBAL + CgenSupport.INTTAG);
        str.println(CgenSupport.GLOBAL + CgenSupport.BOOLTAG);
        str.println(CgenSupport.GLOBAL + CgenSupport.STRINGTAG);

        /* start my implementation */
        // add global labels
        str.println(CgenSupport.GLOBAL + CgenSupport.VOIDCLASS);
        str.println(CgenSupport.GLOBAL + CgenSupport.CLASSOBJTAB);
        for (Enumeration e = nds.elements(); e.hasMoreElements();) {
            CgenNode node = (CgenNode)e.nextElement();
            str.print(CgenSupport.GLOBAL);
            CgenSupport.emitDispTableRef(node.name, str);
            str.println("");
        }

        /* finish my implementation*/

        // We also need to know the tag of the Int, String, and Bool classes
        // during code generation.

        str.println(CgenSupport.INTTAG + CgenSupport.LABEL
                + CgenSupport.WORD + intclasstag);
        str.println(CgenSupport.BOOLTAG + CgenSupport.LABEL
                + CgenSupport.WORD + boolclasstag);
        str.println(CgenSupport.STRINGTAG + CgenSupport.LABEL
                + CgenSupport.WORD + stringclasstag);

        CgenSupport.emitComment("finish emit global data", str);
    }

    /**
     * Emits code to start the .text segment and to
     * declare the global names.
     */
    private void codeGlobalText() {
        CgenSupport.emitComment("start emit global text", str);
        str.println(CgenSupport.GLOBAL + CgenSupport.HEAP_START);
        str.print(CgenSupport.HEAP_START + CgenSupport.LABEL);
        str.println(CgenSupport.WORD + 0);
        str.println("\t.text");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitInitRef(TreeConstants.Main, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitInitRef(TreeConstants.Int, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitInitRef(TreeConstants.Str, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitInitRef(TreeConstants.Bool, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitMethodRef(TreeConstants.Main, TreeConstants.main_meth, str);
        str.println("");

        /* start my implementation */
        for (Enumeration e = nds.elements(); e.hasMoreElements();) {
            CgenNode n = (CgenNode)e.nextElement();
            Vector<methodInheri> methods = classToMethodTable.get(n);
            for (methodInheri md : methods) {
                if (md.source.equals(n.name)) {
                    str.print(CgenSupport.GLOBAL);
                    CgenSupport.emitMethodRef(md.source, md.m.name, str);
                    str.println("");
                }
            }
        }
        /* finish my implementation */

        CgenSupport.emitComment("finish emit global text", str);
    }

    /**
     * Emits code definitions for boolean constants.
     */
    private void codeBools(int classtag) {
        BoolConst.falsebool.codeDef(classtag, str);
        BoolConst.truebool.codeDef(classtag, str);
    }

    /**
     * Generates GC choice constants (pointers to GC functions)
     */
    private void codeSelectGc() {
        CgenSupport.emitComment("start emit CG choice constants", str);
        str.println(CgenSupport.GLOBAL + "_MemMgr_INITIALIZER");
        str.println("_MemMgr_INITIALIZER:");
        str.println(CgenSupport.WORD
                + CgenSupport.gcInitNames[Flags.cgen_Memmgr]);

        str.println(CgenSupport.GLOBAL + "_MemMgr_COLLECTOR");
        str.println("_MemMgr_COLLECTOR:");
        str.println(CgenSupport.WORD
                + CgenSupport.gcCollectNames[Flags.cgen_Memmgr]);

        str.println(CgenSupport.GLOBAL + "_MemMgr_TEST");
        str.println("_MemMgr_TEST:");
        str.println(CgenSupport.WORD
                + ((Flags.cgen_Memmgr_Test == Flags.GC_TEST) ? "1" : "0"));
        CgenSupport.emitComment("finish emit CG choice constants", str);
    }

    /**
     * Emits code to reserve space for and initialize all of the
     * constants.  Class names should have been added to the string
     * table (in the supplied code, it is done during the construction
     * of the inheritance graph), and code for emitting string constants
     * as a side effect adds the string's length to the integer table.
     * The constants are emmitted by running through the stringtable and
     * inttable and producing code for each entry.
     */
    private void codeConstants() {
        CgenSupport.emitComment("start emit constants", str);
        // Add constants that are required by the code generator.
        AbstractTable.stringtable.addString("");
        AbstractTable.inttable.addString("0");

        AbstractTable.stringtable.codeStringTable(stringclasstag, str);
        AbstractTable.inttable.codeStringTable(intclasstag, str);
        codeBools(boolclasstag);

        codeVoidClass();

        CgenSupport.emitComment("finish emit constants", str);
    }


    /**
     * Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     */
    private void installBasicClasses() {
        AbstractSymbol filename
                = AbstractTable.stringtable.addString("<basic class>");

        // A few special class names are installed in the lookup table
        // but not the class list.  Thus, these classes exist, but are
        // not part of the inheritance hierarchy.  No_class serves as
        // the parent of Object and the other special classes.
        // SELF_TYPE is the self class; it cannot be redefined or
        // inherited.  prim_slot is a class known to the code generator.

        addId(TreeConstants.No_class,
                new CgenNode(new class_(0,
                        TreeConstants.No_class,
                        TreeConstants.No_class,
                        new Features(0),
                        filename),
                        CgenNode.Basic, this));

        addId(TreeConstants.SELF_TYPE,
                new CgenNode(new class_(0,
                        TreeConstants.SELF_TYPE,
                        TreeConstants.No_class,
                        new Features(0),
                        filename),
                        CgenNode.Basic, this));

        addId(TreeConstants.prim_slot,
                new CgenNode(new class_(0,
                        TreeConstants.prim_slot,
                        TreeConstants.No_class,
                        new Features(0),
                        filename),
                        CgenNode.Basic, this));

        class_ Void_class =
                new class_(0,
                        TreeConstants.Void,
                        TreeConstants.No_class,
                        new Features(0),
                        filename
                        );

        // The Object class has no parent class. Its methods are
        //        cool_abort() : Object    aborts the program
        //        type_name() : Str        returns a string representation
        //                                 of class name
        //        copy() : SELF_TYPE       returns a copy of the object

        class_ Object_class =
                new class_(0,
                        TreeConstants.Object_,
                        TreeConstants.No_class,
                        new Features(0)
                                .appendElement(new method(0,
                                        TreeConstants.cool_abort,
                                        new Formals(0),
                                        TreeConstants.Object_,
                                        new no_expr(0)))
                                .appendElement(new method(0,
                                        TreeConstants.type_name,
                                        new Formals(0),
                                        TreeConstants.Str,
                                        new no_expr(0)))
                                .appendElement(new method(0,
                                        TreeConstants.copy,
                                        new Formals(0),
                                        TreeConstants.SELF_TYPE,
                                        new no_expr(0))),
                        filename);

        installClass(new CgenNode(Object_class, CgenNode.Basic, this));

        // The IO class inherits from Object. Its methods are
        //        out_string(Str) : SELF_TYPE  writes a string to the output
        //        out_int(Int) : SELF_TYPE      "    an int    "  "     "
        //        in_string() : Str            reads a string from the input
        //        in_int() : Int                "   an int     "  "     "

        class_ IO_class =
                new class_(0,
                        TreeConstants.IO,
                        TreeConstants.Object_,
                        new Features(0)
                                .appendElement(new method(0,
                                        TreeConstants.out_string,
                                        new Formals(0)
                                                .appendElement(new formal(0,
                                                        TreeConstants.arg,
                                                        TreeConstants.Str)),
                                        TreeConstants.SELF_TYPE,
                                        new no_expr(0)))
                                .appendElement(new method(0,
                                        TreeConstants.out_int,
                                        new Formals(0)
                                                .appendElement(new formal(0,
                                                        TreeConstants.arg,
                                                        TreeConstants.Int)),
                                        TreeConstants.SELF_TYPE,
                                        new no_expr(0)))
                                .appendElement(new method(0,
                                        TreeConstants.in_string,
                                        new Formals(0),
                                        TreeConstants.Str,
                                        new no_expr(0)))
                                .appendElement(new method(0,
                                        TreeConstants.in_int,
                                        new Formals(0),
                                        TreeConstants.Int,
                                        new no_expr(0))),
                        filename);

        installClass(new CgenNode(IO_class, CgenNode.Basic, this));

        // The Int class has no methods and only a single attribute, the
        // "val" for the integer.

        class_ Int_class =
                new class_(0,
                        TreeConstants.Int,
                        TreeConstants.Object_,
                        new Features(0)
                                .appendElement(new attr(0,
                                        TreeConstants.val,
                                        TreeConstants.prim_slot,
                                        new no_expr(0))),
                        filename);

        installClass(new CgenNode(Int_class, CgenNode.Basic, this));

        // Bool also has only the "val" slot.
        class_ Bool_class =
                new class_(0,
                        TreeConstants.Bool,
                        TreeConstants.Object_,
                        new Features(0)
                                .appendElement(new attr(0,
                                        TreeConstants.val,
                                        TreeConstants.prim_slot,
                                        new no_expr(0))),
                        filename);

        installClass(new CgenNode(Bool_class, CgenNode.Basic, this));

        // The class Str has a number of slots and operations:
        //       val                              the length of the string
        //       str_field                        the string itself
        //       length() : Int                   returns length of the string
        //       concat(arg: Str) : Str           performs string concatenation
        //       substr(arg: Int, arg2: Int): Str substring selection

        class_ Str_class =
                new class_(0,
                        TreeConstants.Str,
                        TreeConstants.Object_,
                        new Features(0)
                                .appendElement(new attr(0,
                                        TreeConstants.val,
                                        TreeConstants.Int,
                                        new no_expr(0)))
                                .appendElement(new attr(0,
                                        TreeConstants.str_field,
                                        TreeConstants.prim_slot,
                                        new no_expr(0)))
                                .appendElement(new method(0,
                                        TreeConstants.length,
                                        new Formals(0),
                                        TreeConstants.Int,
                                        new no_expr(0)))
                                .appendElement(new method(0,
                                        TreeConstants.concat,
                                        new Formals(0)
                                                .appendElement(new formal(0,
                                                        TreeConstants.arg,
                                                        TreeConstants.Str)),
                                        TreeConstants.Str,
                                        new no_expr(0)))
                                .appendElement(new method(0,
                                        TreeConstants.substr,
                                        new Formals(0)
                                                .appendElement(new formal(0,
                                                        TreeConstants.arg,
                                                        TreeConstants.Int))
                                                .appendElement(new formal(0,
                                                        TreeConstants.arg2,
                                                        TreeConstants.Int)),
                                        TreeConstants.Str,
                                        new no_expr(0))),
                        filename);

        installClass(new CgenNode(Str_class, CgenNode.Basic, this));
    }

    // The following creates an inheritance graph from
    // a list of classes.  The graph is implemented as
    // a tree of `CgenNode', and class names are placed
    // in the base class symbol table.

    private void installClass(CgenNode nd) {
        if (nd.name.equals(TreeConstants.Object_)) {
            clToTag.put(nd, 0);
        } else if (nd.name.equals(TreeConstants.IO)) {
            clToTag.put(nd, 1);
        } else if (nd.name.equals(TreeConstants.Int)) {
            clToTag.put(nd, 2);
        } else if (nd.name.equals(TreeConstants.Bool)) {
            clToTag.put(nd, 3);
        } else if (nd.name.equals(TreeConstants.Str)) {
            clToTag.put(nd, 4);
        }
        AbstractSymbol name = nd.getName();
        if (probe(name) != null) return;
        nds.addElement(nd);
        addId(name, nd);
    }

    private void installClasses(Classes cs) {
        for (Enumeration e = cs.getElements(); e.hasMoreElements(); ) {
            installClass(new CgenNode((Class_) e.nextElement(),
                    CgenNode.NotBasic, this));
        }
    }

    private void buildInheritanceTree() {
        for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
            setRelations((CgenNode) e.nextElement());
        }
    }

    private void setRelations(CgenNode nd) {
        CgenNode parent = (CgenNode) probe(nd.getParent());
        nd.setParentNd(parent);
        parent.addChild(nd);
    }

    /**
     * Constructs a new class table and invokes the code generator
     */
    public CgenClassTable(Classes cls, PrintStream str) {
        nds = new Vector();

        this.str = str;


        /* class tag:
        *   void_class : no class
        nds:
        *   Object_class: 0
        *   IO_class: 1
        *   Int_class: 2
        *   Bool_class: 3
        *   Str_class: 4
        *   ----------------------- above are basic classes, the index are fixed
        *   other classes : 5 and above.
        * */
        clToTag = new HashMap<>();

        stringclasstag = 4 /* Change to your String class tag here */;
        intclasstag = 2 /* Change to your Int class tag here */;
        boolclasstag = 3 /* Change to your Bool class tag here */;


        enterScope();
        if (Flags.cgen_debug) System.out.println("Building CgenClassTable");

        installBasicClasses();
        installClasses(cls);
        buildInheritanceTree();

        code();

        exitScope();
    }

    /**
     * This method is the meat of the code generator.  It is to be
     * filled in programming assignment 5
     */
    public void code() {
        if (Flags.cgen_debug) System.out.println("coding global data");
        codeGlobalData();

        if (Flags.cgen_debug) System.out.println("choosing gc");
        codeSelectGc();

        if (Flags.cgen_debug) System.out.println("coding constants");
        codeConstants();



        //                 Add your code to emit
        //                   - prototype objects
        labelindex = 4;
        classtag = 6;
        nameToLabelIndex = new HashMap<>();
        classToMethodTable = new HashMap<>();
        classToAttrsTable = new HashMap<>();
        codePrototypeObjs();
        codeClassObjTab();

        //                   - class_nameTab
        codeClass_nameTab();
        //                   - dispatch tables
        codeDispatchTab();

        if (Flags.cgen_debug) System.out.println("coding global text");
        codeGlobalText();

        //                 Add your code to emit
        //                   - object initializer

        this.context = new ClassContext();
        codeinit();
        //                   - the class methods
        codeMethods();
        //                   - etc...
    }

    /**
     * Gets the root of the inheritance tree
     */
    public CgenNode root() {
        return (CgenNode) probe(TreeConstants.Object_);
    }

    /* Start my implementation */

    /** method to find all ancestors of node, node itself does not included
     *  @param node the node that need to find his ancestors.
     *  @param includeSelf flag set to true if the returned stack include itself, otherwise set to false
     *  @return stack of ancestors, stack bottom is the closest, top is the farest */
    private Stack<CgenNode> findAllAncestors(CgenNode node, boolean includeSelf) {

        Stack<CgenNode> ancestor = new Stack<>();
        if (includeSelf) {
            ancestor.push(node);
        }
        if (node.name.equals(TreeConstants.Object_)) {
            return ancestor;
        }
        CgenNode ances = node.getParentNd();

        ancestor.push(ances);
        while (!(ances.name.equals(TreeConstants.Object_))) {
            ances = ances.getParentNd();
            ancestor.push(ances);
        }
        return ancestor;
    }

    /** methods to find from a list of ancestors, the closest ancestor of a node
     *  @return the closest ancestor's index in Vector, if there is no common ancestor, return -1*/
    int findClosestAncestor(Vector<AbstractSymbol> ndsNameToCheck, CgenNode node) {
        Vector<CgenNode> ndsToCheck = new Vector<>();
        for (AbstractSymbol name : ndsNameToCheck) {
            ndsToCheck.add((CgenNode)lookup(name));
        }
        Stack<CgenNode> ancestors = findAllAncestors(node, true);
        for (int i = 0; i < ancestors.size(); i += 1) {
            if (ndsToCheck.contains(ancestors.get(i))) {
                return i;
            }
        }
        return -1;
    }


    /** iterate all classes to first find out how many attributes they have */
    private void iterateCls() {
        // first iterate to check their attributes and methods in their own scope
        for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
            CgenNode node = (CgenNode) e.nextElement();
            Vector<methodInheri> nodeDispatchTable = new Vector<>();
            Vector<attr> nodeAttributeTable = new Vector<>();
            for (Enumeration e1 = node.features.getElements(); e1.hasMoreElements();) {
                Feature f = (Feature) e1.nextElement();
                if (f instanceof method) {
                    method m = (method)f;
                    nodeDispatchTable.add(new methodInheri(m, node.name));
                } else {
                    attr a = (attr)f;
                    nodeAttributeTable.add(a);
                }
            }
            classToMethodTable.put(node, nodeDispatchTable);
            classToAttrsTable.put(node, nodeAttributeTable);
        }
        // second iteration to add all parents' methods and attributes, and map them to the two maps.
        for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
            CgenNode node = (CgenNode) e.nextElement();
            fillFullMethods(node);
            fillFullAttrs(node);
        }

    }

     /** helper function of iterateCls:
     *  iterate all nodes, create its full methods vector(including all parents' methods.
     *  associate CgenNode with vector; */
    private void fillFullMethods(CgenNode node) {
        if (node.name.equals(TreeConstants.Object_)) {
            return;
        }
        CgenNode parent = node.getParentNd();
        Vector<methodInheri> parentDispatchTable = classToMethodTable.get(parent);
        Vector<methodInheri> nodeOwnDispatchTable = classToMethodTable.get(node);
        Vector<methodInheri> currentNodeDispatchTable = new Vector<>();
        // add all its parent's methods
        for (int index = 0; index < parentDispatchTable.size(); index += 1) {
            currentNodeDispatchTable.add(index, parentDispatchTable.get(index));
        }
        // append on the end of the method that does not exist in their parent's
        for (methodInheri m : nodeOwnDispatchTable) {
            int replace = currentNodeDispatchTable.indexOf(m);
            if (replace >= 0) {
                currentNodeDispatchTable.set(replace, m);
            } else {
                currentNodeDispatchTable.add(m);
            }
        }
        classToMethodTable.put(node, currentNodeDispatchTable);
    }


     /** helper function of iterateCls:
     *  iterate all nodes, create its full methods vector(including all parents' methods.
     *  associate CgenNode with vector; */
     private void fillFullAttrs(CgenNode node) {
         if (node.name.equals(TreeConstants.Object_)) {
             return;
         }
         CgenNode parent = node.getParentNd();
         Vector<attr> parentAttrs = classToAttrsTable.get(parent);
         Vector<attr> nodeAttrs = classToAttrsTable.get(node);
         Vector<attr> currentNodeAttrs = new Vector<>();
         for (attr a : parentAttrs) {
             currentNodeAttrs.add(currentNodeAttrs.size(), a);
         }
         for (attr a : nodeAttrs) {
             currentNodeAttrs.add(currentNodeAttrs.size(), a);
         }
         classToAttrsTable.put(node, currentNodeAttrs);
     }


    /** to create the void_class, this will be on heap, and never copied,
     *  all vars that are void in cool actually point to this address
     *  the label is laid in .data area */
    private void codeVoidClass() {
        str.println(CgenSupport.VOIDCLASS + CgenSupport.LABEL);
        str.println(CgenSupport.WORD + "-1");
        str.println(CgenSupport.WORD + CgenSupport.DEFAULT_OBJFIELDS);
    }

    /** for every classes(Iterate through the tree), I will create a prototype objects
     *  there is a question, the Bool, Int, String has already built the prototype, do I
     *  need to skip it? */
    private void codePrototypeObjs() {
        str.println("");
        CgenSupport.emitComment("start emit prototypes", str);
        iterateCls();
        for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
            CgenNode node = (CgenNode) e.nextElement();
            if (node.name.equals(TreeConstants.Int)) {
                zeroSymbol = (IntSymbol)AbstractTable.inttable.addInt(0);
                zeroSymbol.codeProtDef(intclasstag, str);
            } else if (node.name.equals(TreeConstants.Str)) {
                emptyStrSymbol = (StringSymbol)AbstractTable.stringtable.addString("");
                emptyStrSymbol.codeProjDef(stringclasstag, str);
            } else if (node.name.equals(TreeConstants.Bool)) {
                BoolConst.falsebool.codeProtDef(classtag, str);
            } else {
                emitPrototypeObjs(node);
            }
        }
        CgenSupport.emitComment("finish emit prototypes", str);
        str.println("");
    }

    /** the method that emit prototype objects' layout*/
    private void emitPrototypeObjs(CgenNode node) {
        // the Garbage Collector Tag
        str.println(CgenSupport.WORD + "-1");
        CgenSupport.emitProtObjRef(node.name, str);
        str.print(CgenSupport.LABEL);
        // create Class Tag Number, emit it to SPIM
        // and map the Class Tag Number to Class Name
        int index;
        if (clToTag.containsKey(node)) {
            index = clToTag.get(node);
        } else {
            index = classtag ++;
            clToTag.put(node, index);
        }
        nameToLabelIndex.put(node.name.str, labelindex);
        str.println(CgenSupport.WORD + index);
        // class size
        int attrNum = classToAttrsTable.get(node).size();
        str.println(CgenSupport.WORD + (CgenSupport.DEFAULT_OBJFIELDS + attrNum));
        // emit the dispatch table label
        str.print(CgenSupport.WORD);
        CgenSupport.emitDispTableRef(node.name, str);
        str.println("");
        // emit all attributes
        createAttributes(node);
    }

    /** helper function of emitPrototypeOjbs
     *  create the attributes, associated it with CgenNode, and print by PrintStream */
    private void createAttributes(CgenNode node) {
        Vector<attr> nodeAttrs = classToAttrsTable.get(node);
        // set default value to attributes: string to "", bool and int to 0, other to void
        for (attr a : nodeAttrs) {
            if (a.type_decl.equals(TreeConstants.Int)) {
                str.print(CgenSupport.WORD);
                zeroSymbol.codeRef(str);
                str.println("");
            } else if (a.type_decl.equals(TreeConstants.Str)) {
                str.print(CgenSupport.WORD);
                emptyStrSymbol.codeRef(str);
                str.println("");
            } else if (a.type_decl.equals(TreeConstants.Bool)) {
                str.print(CgenSupport.WORD);
                BoolConst.falsebool.codeRef(str);
                str.println("");
            } else {
                str.print(CgenSupport.WORD);
                // set attributes to void class
                str.print(CgenSupport.VOIDCLASS);
                str.println("");
            }
        }
    }

    /** function to create the class name table */
    private void codeClass_nameTab() {
        str.println("");
        CgenSupport.emitComment("start emit class name table", str);
        str.print(CgenSupport.CLASSNAMETAB + CgenSupport.LABEL);
        for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
            CgenNode node = (CgenNode) e.nextElement();

            /* this is a confusing part for me: the class name part is actually
            *  AbstractSymbol taken from idTable, but when building class name table,
            *  actually require AbstractSymbol's index from stringTable, which are not
            *  the same indexes as from idTable.
            *  so I need to first lookup the string from stringTable and fetch the
            *  corresponding return value's index.*/
            AbstractSymbol nameAsString = AbstractTable.stringtable.lookup(node.name.str);

            str.print(CgenSupport.WORD);

            str.println(CgenSupport.STRCONST_PREFIX + nameAsString.index);
        }
        CgenSupport.emitComment("finish emit class name table", str);
        str.println("");
    }

    /** function to create object table, it will have the same offset as class name table */
     private void codeClassObjTab() {
        str.println("");
        CgenSupport.emitComment("start emit object table", str);
        str.print(CgenSupport.CLASSOBJTAB + CgenSupport.LABEL);
        for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
            CgenNode node = (CgenNode) e.nextElement();
            str.print(CgenSupport.WORD);
            CgenSupport.emitProtObjRef(node.name, str);
            str.println("");
        }
        CgenSupport.emitComment("finish emit object table", str);
        str.println("");
    }

    /** function to create dispatch table, it will have same offset as class name and object table */
    private void codeDispatchTab() {
        str.println("");
        CgenSupport.emitComment("start emit dispatch table", str);
        for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
            CgenNode node = (CgenNode) e.nextElement();
            str.print(node.name.str + CgenSupport.DISPTAB_SUFFIX + CgenSupport.LABEL);
            // init method is implicitly, not in the classToMethod vector
            str.print(CgenSupport.WORD);
            CgenSupport.emitInitRef(node.name, str);
            str.println("");
            for (methodInheri method : classToMethodTable.get(node)) {
                str.println(CgenSupport.WORD + method.source + CgenSupport.METHOD_SEP + method.m.name);
            }
        }
        CgenSupport.emitComment("finish emit dispatch table", str);
        str.println("");
    }


    /** function to initialize all classes prototype */
    private void codeinit() {
        str.println("");
        CgenSupport.emitComment("start all emit init of prototypes", str);
        for (Enumeration e = nds.elements(); e.hasMoreElements();) {
            codeinit((CgenNode)e.nextElement());
        }
        CgenSupport.emitComment("finish all emit init of prototypes", str);
        str.println("");
    }

    /** function emit code for ClassName_init function call.
     *  the new copied object suppose to be in $a0
     *  find all ancestors, do copy, push copied onto stack, the last one, also store in $a0 for later excution
     *  suppose there is Main(target to init) inherits A inherits IO inherits Object
     *  after do all copy, the stack will be
     *        ...
     *      --------------------
     *         Main copy ptr
     *      --------------------
     *         A copy ptr
     *      --------------------
     *         B copy ptr
     *      --------------------
     *         unused   <---- $fp
     *
     *     and $a0 store Object copy ptr
     *
     *     when code Init for each one of them, I can garantee that the first one is Object which will
     *     not be evaluate, why I don't store the Objcted copy ptr on stack and pointed by $fp,
     *     because when I evaluate B, I need to look for its parent(i.e. Object) but it has no attributes to
     *     look for, so no bother to store it or not.
     *
     *  */
    void codeinit(CgenNode initNode) {
        str.println("");
        CgenSupport.emitComment("start emit init for class: " + initNode.name.str, str);
        str.print(initNode.name.str + CgenSupport.CLASSINIT_SUFFIX + CgenSupport.LABEL);

        if (//initNode.name.equals(TreeConstants.Object_)) {
            initNode.basic()) {
            CgenSupport.emitReturn(str);
            CgenSupport.emitComment("finish emit init -- no need -- for class: " + initNode.name.str, str);
            str.println("");
            return;
        }

        /* this is the initial work for every method, add the frame pointer and return address on stack */
        // push $fp, the frame pointer, and set $fp to point to this address
        CgenSupport.emitPush(CgenSupport.FP, str);
        CgenSupport.emitMove(CgenSupport.FP, CgenSupport.SP, str);
        CgenSupport.emitAddiu(CgenSupport.FP, CgenSupport.FP, CgenSupport.WORD_SIZE, str);
        // once called JAl, MIPS automatically store the next instruction into $ra, so no need to store previously
        // push $ra, the return address
        CgenSupport.emitPush(CgenSupport.RA, str);

        // move $s0, $a0
        CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, str);

        Stack<CgenNode> ancestors = findAllAncestors(initNode, false);
        int numAncestors = ancestors.size();
        // push $a0 on stack
        CgenSupport.emitPush(CgenSupport.ACC, str);
        // iterate all ancestor, call copy, and store the pointers onto stack, except the last one, which store in $a0
        for (int i = 0; i < numAncestors - 1; i += 1) {
            CgenNode ances = ancestors.get(i);
            // la $a0, ances_prototype label
            // call obj.copy
            CgenSupport.emitLoadAddress(CgenSupport.ACC, ances.name.str + CgenSupport.PROTOBJ_SUFFIX, str);
            CgenSupport.emitJal("Object.copy", str);
            CgenSupport.emitPush(CgenSupport.ACC, str);
        }

        CgenNode ances = ancestors.get(numAncestors - 1);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, ances.name.str + CgenSupport.PROTOBJ_SUFFIX, str);
        CgenSupport.emitJal("Object.copy", str);

        // evaluate the ancestor's init first, pop stored ptr onto $a0, repeately for
        while (ancestors.size() != 0) {
            codeInitAlong(ancestors.pop());
            CgenSupport.emitPop(CgenSupport.ACC, str);
        }
        codeInitAlong(initNode);

        // get ready to return: store current frame's return address in $ra, set $fp to point to caller's frame pointer
        // restore $ra: dw $ra, -4 ($fp)
        // lw $fp, ($fp)
        CgenSupport.emitLoad(CgenSupport.RA, -1, CgenSupport.FP, str);
        CgenSupport.emitLoad(CgenSupport.FP, 0, CgenSupport.FP, str);
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, 2 * CgenSupport.WORD_SIZE, str);
        CgenSupport.emitReturn(str);
        CgenSupport.emitComment("finish emit init for class: " + initNode.name.str, str);
        str.println("");

    }

    private void codeInitAlong(CgenNode node) {
        // move $s0, $a0
        CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, str);
         if (node.name.equals(TreeConstants.Object_) ||
                node.name.equals(TreeConstants.IO)) {
            // do nothing because there are no attributes
         } else {
             context.enterScope();
             context.setSo(node);
             Vector<attr> nodeAttrs = classToAttrsTable.get(node);
             /*
             *
             * I have made a big mistake here
             * I thought that the attribute's offset is the same as the stack
             * that lower is smaller, BUT this is store in the heap, not stack
             * the heap is growing towards big address.
             * so the offset IS NOT negate!!
             *
             *
             * */
             for (int i = 0; i < nodeAttrs.size(); i += 1) {
                 attr a = nodeAttrs.get(i);
                 // if attribute has no initial expr, check for their ancestor,
                 // if also not setted, then set the default
                 if (a.init instanceof no_expr) {
                     CgenNode parent = node.getParentNd();
                     if (hasAttribute(parent, a)) {
                         // la $a0, $ptr, because although it pops out, the parent ptr is still store in it
                         CgenSupport.emitLoad(CgenSupport.ACC,0, CgenSupport.SP, str);
                         // lw a0, (i+3) * 4($a0)
                         CgenSupport.emitLoad(CgenSupport.ACC, i + 3, CgenSupport.ACC, str);
                         // sw a0, (i+3) * 4($s0)
                         CgenSupport.emitStore(CgenSupport.ACC, i + 3, CgenSupport.SELF, str);
                         // if it is not ancestor's attribute and does not have init expr, set to 0
                     } else {
                         // i have already create the default attributes in prototype object, so do nothing
                     }
                 } else {
                     a.init.code(str, context);
                     // sw a0, (i+3) * 4($s0), store whatever evaluated in $a0 back to the attribute slot
                     CgenSupport.emitStore(CgenSupport.ACC, i + 3, CgenSupport.SELF, str);
                 }
             }
             context.exitScope();
         }
    }


    /* initialize all the ancestors of class that need to be initialized
     *  ( I do not consider that the ancestors have already initialized)
     *  from Object to the most close ancestor, first initialize the class, and then evaluate all attributes
     *  if attributes has init body, evaluate body,
     *  if not has, first check for its parent's attribute, if has, store the same attr at same offset
     *                                                      if not, set default value (0) */
    /*private void codeAncestorInit(CgenNode node) {
        str.println("");
        CgenSupport.emitComment("start emit init for class: " + node.name.str, str);
        // la a0, ObjectPrototype label
        CgenSupport.emitLoadAddress(CgenSupport.ACC, node.name.str + CgenSupport.PROTOBJ_SUFFIX, str);
        // call Object.copy on ancestor, return in a0;
        CgenSupport.emitJal("Object.copy", str);
        if (node.name.equals(TreeConstants.Object_) ||
                node.name.equals(TreeConstants.IO)) {
            // do nothing because there are no attributes
            // only copy $a0 to $s0 because need to push it to stack
            CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, str);
        } else {
            context.enterScope();
            context.setSo(node);
            // move s0, a0
            CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, str);
            Vector<attr> nodeAttrs = classToAttrsTable.get(node);
            for (int i = 0; i < nodeAttrs.size(); i += 1) {
                attr a = nodeAttrs.get(i);
                // if attribute has no initial expr, check for their ancestor,
                // if also not setted, then set the default
                if (a.init instanceof no_expr) {
                    if (hasAttribute(node.getParentNd(), a)) {
                        // the parent of this node in in stack $sp - 4($sp) points to next unused block)
                        // move $t1, $sp
                        // addiu $t1, $t1, 4, so that $t1 points to the last block of $sp
                        // lw $t2, t1, so that $t2 points to the  parent
                        CgenSupport.emitMove(CgenSupport.T1, CgenSupport.SP, str);
                        CgenSupport.emitAddiu(CgenSupport.T1, CgenSupport.T1, 4, str);
                        CgenSupport.emitLoad(CgenSupport.T2, 0, CgenSupport.T1, str);
                        // lw a0, - (i+2) * 4($t2)
                        CgenSupport.emitLoad(CgenSupport.ACC, -(i + 2), CgenSupport.T2, str);
                        // sw a0, - (i+2) * 4($s0)
                        CgenSupport.emitStore(CgenSupport.ACC, -(i + 2), CgenSupport.SELF, str);
                        // if it is not ancestor's attribute and does not have init expr, set to 0
                    } else {
                        // i have already create the default attributes in prototype object, so do nothing
                    }
                } else {
                    a.init.code(str, context);
                    // sw a0, - (i+2) * 4($s0), store whatever evaluated in $a0 back to the attribute slot
                    CgenSupport.emitStore(CgenSupport.ACC,-(i + 2), CgenSupport.SELF, str);
                }
            }
            context.exitScope();
        }
    }*/

    /** helper function to attributes initializer */
    private boolean hasAttribute (CgenNode node, attr a) {
        Vector<attr> att = classToAttrsTable.get(node);
        return att.contains(a);
    }

    /** function to put all methods to .text area */
    private void codeMethods() {
        str.println("");
        CgenSupport.emitComment("start emit methods", str);
        for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
            CgenNode node = (CgenNode) e.nextElement();
            context.enterScope();
            context.setSo(node);
            codeMethod(node);
            context.exitScope();
        }
        CgenSupport.emitComment("finish emit methods", str);
        str.println("");
    }

    /** evaluate all method, and set them to be pointed by the dispatch table
     *  will modify a0 */
    private void codeMethod(CgenNode node) {
        if (node.basic()) {
            return;
        }
        Vector<methodInheri> nodemethods = classToMethodTable.get(node);

        for (methodInheri method : nodemethods) {
            method m = method.m;
            if (!(method.source.equals(node.name))) {
                continue;
            }
            String fullname = method.source + CgenSupport.METHOD_SEP + m.name.str;
            if (definedMethods.contains(fullname)) {
                continue;
            }
            context.enterScope();
            str.println(fullname + CgenSupport.LABEL);

            /* this is the initial work for every method, add the frame pointer and return address on stack */
            // push $fp, the frame pointer, and set $fp to point to this address
            CgenSupport.emitPush(CgenSupport.FP, str);
            CgenSupport.emitMove(CgenSupport.FP, CgenSupport.SP, str);
            CgenSupport.emitAddiu(CgenSupport.FP, CgenSupport.FP, CgenSupport.WORD_SIZE, str);
            // once called JAl, MIPS automatically store the next instruction into $ra, so no need to store previously
            // push $ra, the return address
            CgenSupport.emitPush(CgenSupport.RA, str);

            /* I put the arguments in stack
                arg1
              --------
                arg2
              --------
                ...
              --------
                argN
              --------
              frame pointer
              ----------
              return address
              ----------
                 self
              ----------
              local values
                 ...
              ----------
                temps
                 ...

             and the return value is always stored in $a0
             before return, load $fp the value that $fp currently points to (is the caller's $fp)
             and jump to the value of $ar
            */
            int totalSize = m.formals.getLength();
            int offset = totalSize;
            // form the implementation(X, m) = (x 1 , x 2 , . . . , x n , e body )
            // add in all formals
            for (Enumeration e = m.formals.getElements(); e.hasMoreElements();) {
                formal fm = (formal)e.nextElement();
                context.addInEnv(fm.name, offset);
                offset -= 1;
            }
            // evaluate expr body under the context that enter into a new scope
            m.expr.code(str, context);
            // get ready to return: store current frame's return address in $ra, set $fp to point to caller's frame pointer
            // restore $ra: dw $ra, -4 ($fp)
            // lw $fp, ($fp)
            CgenSupport.emitMove(CgenSupport.SP, CgenSupport.FP, str);
            CgenSupport.emitLoad(CgenSupport.RA, -1, CgenSupport.FP, str);
            CgenSupport.emitLoad(CgenSupport.FP, 0, CgenSupport.FP, str);
            // pop out the $ra, $fp and arguments
            CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, totalSize * CgenSupport.WORD_SIZE, str);
            CgenSupport.emitReturn(str);
        }

    }

    Vector<attr> getAttributes(CgenNode node) {
        return classToAttrsTable.get(node);
    }

    /** find the offset of one attribute in the class name,
     *  if does not exist, return -1 */
    int checkAttrOffset(CgenNode node, AbstractSymbol name) {
        Vector<attr> nodeAttrs = classToAttrsTable.get(node);
        int i = -1;
        for (int index = 0; index < nodeAttrs.size(); index += 1) {
            if (nodeAttrs.get(index).name.equals(name)) {
                i = index;
                break;
            }
        }
        return i;
    }

     /** find the offset of one method in the class name,
     *  if does not exist, return -1 */
    int checkMethodOffset(CgenNode node, AbstractSymbol name) {
        Vector<methodInheri> nodeMethods = classToMethodTable.get(node);
        int i = -1;
        for (int index = 0; index < nodeMethods.size(); index += 1) {
            if (nodeMethods.get(index).m.name.equals(name)) {
                // in dispatch table, the first method is init, but it is not include in classToMethodTable
                i = index + 1;
                break;
            }
        }
        return i;
    }

    int getLabelindex() {
        return labelindex;
    }

    void setLabelindex(int labelindex) {
        this.labelindex = labelindex;
    }

    private class methodInheri {
        private method m;
        private AbstractSymbol source;

        methodInheri(method m, AbstractSymbol s) {
            this.m = m;
            this.source = s;
        }

        @Override
        public boolean equals (Object that) {
            if (that == null) return false;
            if (this == that) return true;
            if (!(that instanceof methodInheri)) return false;
            else {
                methodInheri mthat = (methodInheri)that;
                return m.equals(mthat.m);
            }
        }
    }

}
			  
    
