
import java.io.PrintStream;
import java.util.*;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;

/* start my implementation */
	private AbstractSymbol filename;
    private class_c root;
    private List<class_c> classesRecord;
    // parent -> [children] relationship.
    private Map<class_c, LinkedList<class_c>> graph;
    private Map<AbstractSymbol, class_c> symToClass_c;
    // for praph search data structure
	private Map<class_c, class_c> edgeFrom;
	private Map<class_c, Boolean> marked;
	// for cycle detection
	private Map<class_c, Boolean> onStack;
	private Stack<class_c> cycle;
	// for class_c ==> class context mapping
	private Map<class_c, context> clToContext;
	// check for illegal names
	private final List<AbstractSymbol> keywords = Arrays.asList(
       TreeConstants.self, TreeConstants.SELF_TYPE
            );

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");

	/* start my implementation */
	this.filename = filename;
	LinkedList<class_c> objChildren = new LinkedList<>();
	/* finish my implementation */	
	
	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class = 
	    new class_c(0, 
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

  	/* start my implementation */
  	this.root = Object_class;
	//symToClass_c.put(TreeConstants.Object_, Object_class);
    classesRecord.add(Object_class);

	/* finsih my implementation */

	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class = 
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
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

	/* start my implementation */
	//objChildren.add(IO_class);
	//symToClass_c.put(TreeConstants.IO, IO_class);
	classesRecord.add(IO_class);
	/* finsih my implementation */

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class = 
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	/* start my implementation */
	//objChildren.add(Int_class);
	//symToClass_c.put(TreeConstants.Int, Int_class);
	classesRecord.add(Int_class);
	/* finsih my implementation */

	// Bool also has only the "val" slot.
	class_c Bool_class = 
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	/* start my implementation */
	//objChildren.add(Bool_class);
	//symToClass_c.put(TreeConstants.Bool, Bool_class);
	classesRecord.add(Bool_class);
	/* finsih my implementation */

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
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
						  .appendElement(new formalc(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);
	/* start my implementation */
	//objChildren.add(Str_class);
	//symToClass_c.put(TreeConstants.Str, Str_class);
	classesRecord.add(Str_class);

	/* finsih my implementation */

	/* Do somethind with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */

	/* start my implementation */
	root = Object_class;
	graph.put(Object_class, objChildren);
	/* finsih my implementation */

    }
	
	// install basic classes, and check for all inheritance.
    public ClassTable(Classes cls) {
        semantErrors = 0;
        errorStream = System.err;

        /* fill this in */
        /* start my implementation */
        /*if (Flags.semant_debug) {
        	System.out.println("debug info for initial classes");
        	for (Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
				class_c cl = (class_c) e.nextElement();
				System.out.print(cl.name);
				System.out.print(" , ");
				System.out.print("parent is : ");
                System.out.print(cl.parent + "\n");

			}}*/

        this.graph = new HashMap<>();
        this.symToClass_c = new HashMap<>();
        this.classesRecord = new ArrayList<>();
        this.clToContext = new HashMap<>();
        installBasicClasses();
		formGraphAndCheck(cls);
		formBasicContext();
		/*if (Flags.semant_debug) {
			for (Map.Entry<class_c, context> entry : clToContext.entrySet()) {
				System.out.println("context debug: " + entry.getValue().toString());
			}
        }*/
    }

    /**
	 * step 1: form the class tree and check for validaty
	 * */
    private void formGraphAndCheck(Classes cls) {
    	// test 1: if there is any violation for base classes;
        // if there is any class that have not been defined;
        // if there is any class that Object root do not has path to;

		// check if exist class Main
		boolean hasMain = false;
        for (Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
			// check if there is redefine of base class
			class_c cl = (class_c) e.nextElement();
			if (cl.name.equals(TreeConstants.IO)
					|| cl.name.equals(TreeConstants.Int)
					|| cl.name.equals(TreeConstants.Str)
					|| cl.name.equals(TreeConstants.Bool)) {
				semantError(cl).println(" cannot redefine " +
						"from Int/String/Boolean");
				return;
			}
			// check if there is inheritance of string, boolean base class
			AbstractSymbol clParent = cl.getParentName();
			if (clParent.equals(TreeConstants.Int)
					|| clParent.equals(TreeConstants.Str)
					|| clParent.equals(TreeConstants.Bool)
					|| clParent.equals(TreeConstants.SELF_TYPE)) {
				semantError(cl).println("class " + cl.name + " cannot inherite " +
						"from Int/String/Boolean/SELF_TYPE.");
				return;
			}

			// check if class Main is defined
			if (cl.name.equals(TreeConstants.Main)) {
				hasMain = true;
			}
		}
		if (!hasMain) {
        	semantError().println("Class Main is not defined.");
        	return;
		}

    	// first iterate all the classes store in the list and then create the class map
        for (Enumeration e = cls.getElements(); e.hasMoreElements(); ) {
			class_c cl = (class_c) e.nextElement();
			if (classesRecord.contains(cl)) {
				semantError(cl).println("redefine the defined class: " + cl.name);
			} else {
				classesRecord.add(cl);
			}
		}
		for (class_c cl : classesRecord) {
			symToClass_c.put(cl.name, cl);
		}
        // form the graphic with the class list_node derived from Program entry
        for (class_c cl : classesRecord) {
            AbstractSymbol parent = cl.getParentName();
            class_c parentNode = symToClass_c.get(parent);

            LinkedList<class_c> siblings = graph.get(parentNode);
            if (siblings == null) {
                siblings = new LinkedList<>();
            }
            if (!siblings.contains(cl)) {
                siblings.add(cl);
            }
            graph.put(parentNode, siblings);
        }

	/*if (Flags.semant_debug) {
    	for (Map.Entry<class_c, LinkedList<class_c>> enty : graph.entrySet()) {
			System.out.println("parent is : " + enty.getKey().name);
			LinkedList<class_c> v = enty.getValue();
			String s = "[ ";
			for (class_c n : v) {
				s += n.name;
				s += ",";
			}
			s += " ]";
			System.out.println(s);
			System.out.println("");
		}
	}*/

       	for (class_c cl : classesRecord) {
       		// cannot use directly the Classes passed from semant, because there might be checking for
			// redefined class
            // check if all classes derive from Object (or saying any parent are not clarified)
            if (!hasPathTo(root, cl)) {
                semantError(cl).println("class " + cl.name +
				" cannot derived from Object");
                return;
  	    }
    }

        // test 2: if it has circle.
        onStack = new HashMap<>();
		edgeFrom = new HashMap<>();
        marked = new HashMap<>();
        for (Map.Entry<AbstractSymbol, class_c> entry : symToClass_c.entrySet()) {
        	/*if (Flags.semant_debug) {
        		System.out.println("checkcycle debug: ");
        		System.out.println(entry.getKey().str);
			}*/
            marked.put(entry.getValue(), false);
            edgeFrom.put(entry.getValue(), null);
            onStack.put(entry.getValue(), false);
        }
        checkCycle(root);
	}

    /**
	 * step2: from the root Object, iterate all class and form the prilimary method and attribute and self
	 *        into context, after that mantain a copy
	 * */

    private void formBasicContext() {
    	context cx = new context();
    	formBasicContext(root, cx);
	}

	private void formBasicContext(class_c cl, context cx) {
        cx.cxEnterScope();
        cx.formMethodAttrSelf(cl);
        clToContext.put(cl, cx.copy());
        cx.idEnterScope();
        for (class_c clchild : adjs(cl)) {
        	formBasicContext(clchild, cx);
		}
		cx.cxExitScope();
	}

	protected class_c getClassByName(AbstractSymbol clname) {
    	return symToClass_c.get(clname);
	}

	protected context getContextByClass(class_c cl) {
    	return clToContext.get(cl);
	}

	protected boolean isIllegalID(AbstractSymbol id) {
        return keywords.contains(id);
    }

	protected void DFS() {
    	clear();
        dfs(root);
	}

	private void clear() {
    	marked = new HashMap<>();
        edgeFrom = new HashMap<>();
        onStack = new HashMap<>();
        cycle = null;
        for (Map.Entry<AbstractSymbol, class_c> entry : symToClass_c.entrySet()) {
            marked.put(entry.getValue(), false);
            edgeFrom.put(entry.getValue(), null);
            onStack.put(entry.getValue(), false);
        }
	}

    private boolean hasPathTo(class_c src, class_c des) {
    	if (src.equals(des)) {
    		return true;
		} else {
    		clear();
    		dfs(src);
			return marked.get(des);
		}
    }

    private Stack<class_c> pathTo(class_c from, class_c to) {
		clear();
		dfs(from);
		Stack<class_c> path = new Stack<>();
		path.push(to);
		for (class_c x = to; !(x.equals(from)); x = edgeFrom.get(x)) {
			path.push(x);
		}
		return path;
	}



	private void dfs(class_c node) {
    	onStack.put(node, true);
    	marked.put(node, true);
    	for (class_c child : adjs(node)) {
			if (cycle != null){
				return;
			} else if (marked.get(child).equals(false)) {
				edgeFrom.put(child, node);
				dfs(child);
			} else if (onStack.get(child).equals(true)) {
				cycle = new Stack<>();
				for (class_c n = node; !(n.equals(node)); n = edgeFrom.get(node)) {
					cycle.push(n);
				}
				cycle.push(child);
				cycle.push(node);
			}
		}
		onStack.put(node, false);
	}

	private Iterable<class_c> adjs(class_c node) {
    	LinkedList<class_c> res = graph.get(node);
    	if (res != null) {
    		return res;
		} else {
    		return new LinkedList<>();
		}
	}

	private boolean hasCycle() { return cycle!= null; }

    private boolean checkCycle(class_c node) {
    	/*if (Flags.semant_debug) {
    		System.out.println("marked map debug info : ");
    		System.out.println(marked.toString());
		}*/
		return hasCycle();
    }


    public boolean isSubTree (AbstractSymbol parent, AbstractSymbol child) {
    	if (parent.equals(TreeConstants.SELF_TYPE) && child.equals(TreeConstants.SELF_TYPE)) {
    		return true;
		}
    	class_c p = symToClass_c.get(parent);
    	class_c c = symToClass_c.get(child);
		if (p == null || c == null) {
			//semantError().println(parent.str + " does not declared.");
			return false;
		} else {

    		return (p.equals(c) || hasPathTo(p, c));
		}
	}

	// find the nearest common ancestor of two nodes;
	public AbstractSymbol findCommonAncestor(AbstractSymbol a1, AbstractSymbol a2) {
    	/*if (a1.equals(a2)) {
    		return a1;
		}*/
		class_c t1 = symToClass_c.get(a1);
    	class_c t2 = symToClass_c.get(a2);
    	class_c t = root;
    	if (t1 != null && t2 != null) {
			Stack<class_c> p1 = pathTo(root, t1);
			Stack<class_c> p2 = pathTo(root, t2);
			for (int i = 0; i < p1.size(); i ++) {
				if (p2.contains(p1.get(i))) {
					t = p1.get(i);
					break;
				}
			}
			for (Map.Entry<AbstractSymbol, class_c> enty : symToClass_c.entrySet()) {
				if (enty.getValue().equals(t)) {
					return enty.getKey();
				}
			}
		}
		return TreeConstants.Object_;
	}


	public void typeCheckError(String s) {
    	/*if (node != null) {
    		errorStream.print("Type check error: \n");
    		errorStream.print(filename + ":" + node.getLineNumber() + ": ");
		} else {*/
    		semantErrors ++;
    		errorStream.println(s);
    		//((Expression)node).dump_with_types(errorStream, 0);

    		//node.dump(errorStream, 1);
	}


	public void checkError(String errorMsg) {
    	semantErrors ++;
    	errorStream.print(errorMsg);
	}
/* finsih my implementation */

    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
        errorStream.print(filename + ":" + t.getLineNumber() + ": ");
	return semantError();
    }


	/***
	 * I have change the structure of context:
	 * Use to:
	 *   have an environment of O, M and Self in cool-tree.class
	 *   but it has problem of iterate only once, because of the SymbolTable structure.
	 *   so if a cool programme use an id before it declared, I have no choice but to declare error
	 *
	 * Now to:
	 *   have an context of class in ClassTable.class
	 *   context include :
	 *   	- method prints table
	 *      - attribute table
	 *      - id table
	 *      - current class
	 *
	 *   1. iterate through all classes to build the class tree
	 *   2. iterate the tree and build context, make context copy and map to each tree
	 *   	(it could directly a node with class name, and context, but I have already build
	 *       the tree and its method, so I will build a Map mapping class and context
	 *       the context should be copy)
	 *   3. go to semant entry to iterate through semantics.
	 */

	protected class context {
		private SymbolTable methodSymTable;
		private SymbolTable attrSymTable;
		private SymbolTable idSymTable;
		private class_c currentClass;

		public context() {
			methodSymTable = new SymbolTable();
			attrSymTable = new SymbolTable();
			idSymTable = new SymbolTable();
		}


		public void formMethodAttrSelf(class_c cl) {
			for (Enumeration e = cl.features.getElements(); e.hasMoreElements();) {
				Feature f = (Feature) e.nextElement();
				if (f instanceof method) {
					method m = (method)f;
					ArrayList<AbstractSymbol> methodPrints = new ArrayList<>();
					for (Enumeration fl = m.formals.getElements(); fl.hasMoreElements();) {
	        			formalc formal = (formalc) fl.nextElement();
                        AbstractSymbol ftype = formal.type_decl;
                        methodPrints.add(methodPrints.size(), ftype);
                    }
                    methodPrints.add(methodPrints.size(), m.return_type);
					methodSymTable.addId(m.name,methodPrints);
				} else {
					// attribute can not override by inheritance.
					attr a = (attr)f;

					if (attrSymTable.lookup(a.name) != null) {
						semantError(cl).println("attribute override: " + a.name + " under class " + cl.name);
					}
					attrSymTable.addId(a.name, a.type_decl);
				}
			}
			this.currentClass = cl;
		}

		// addId and delId is after delegate context to every class, so the changes
		// must be local, change only on the context copy
		protected void addId (AbstractSymbol id, AbstractSymbol type) {
			idEnterScope();
			clToContext.get(currentClass).idSymTable.addId(id, type);
		}

		protected void delAllIds () {
			idExitScope();
		}

		protected void setAttribute(AbstractSymbol name, AbstractSymbol type) {
			attrSymTable.addId(name, type);
		}

		// in one scope, first look up id symbol table,
		// if in current id table scope does not have, then look up attributes

		// changed with experiment:
		// when attribute has initial expr, add into id symbol table, lookup only look in id symbol table

		// look up should always go to copy symbol tables!!
		protected AbstractSymbol lookup(AbstractSymbol id) {
			AbstractSymbol type = (AbstractSymbol)(clToContext.get(currentClass).idSymTable.lookup(id));
			if (type == null) {
				type = (AbstractSymbol)(clToContext.get(currentClass).attrSymTable.lookup(id));
			}
			return type;
		}

		protected void cxEnterScope() {
			methodSymTable.enterScope();
			attrSymTable.enterScope();
		}

		protected void cxExitScope() {
			methodSymTable.exitScope();
			attrSymTable.exitScope();
		}

		protected void idEnterScope() {
			clToContext.get(currentClass).idSymTable.enterScope();
		}

		protected void idExitScope() {
			clToContext.get(currentClass).idSymTable.exitScope();
		}

		protected class_c getCurrentClass() {
			return this.currentClass;
		}

		// every time when search for method, or attribute, or id, must check for the copy, NOT the
		// current symbol tables!!
		protected ArrayList<AbstractSymbol> getMethodUnderClass (AbstractSymbol className, AbstractSymbol methodname) {
			Object res = clToContext.get(symToClass_c.get(className)).methodSymTable.lookup(methodname);
			return (ArrayList)res;
		}

		protected ArrayList<AbstractSymbol> checkcAcestorMethod (AbstractSymbol className, AbstractSymbol methodname) {
			class_c cl = symToClass_c.get(className);
			class_c parent = symToClass_c.get(cl.parent);

			while (!(parent.equals(root))) {
				if (getMethodUnderClass(parent.name, methodname)!= null) {
					return getMethodUnderClass(parent.name, methodname);
				}
				parent = symToClass_c.get(parent.parent);
			}
			return null;
		}

		protected AbstractSymbol IDprobe (AbstractSymbol name) {
			return (AbstractSymbol)(clToContext.get(currentClass).idSymTable.probe(name));
		}

		private context copy() {
			context cxCp = new context();
			cxCp.methodSymTable = methodSymTable.copy();
			cxCp.attrSymTable = attrSymTable.copy();
			cxCp.idSymTable = idSymTable.copy();
			cxCp.currentClass = currentClass;
			return cxCp;
		}


		@Override
		public String toString() {
			String s = "Under class_c " + this.currentClass.name + ":\n";
			s += "  method prints are: " + methodSymTable.toString() + "\n";
			s += "  attributes are: " + attrSymTable.toString() + "\n";
			s += "  ids are: " + idSymTable.toString() + "\n";
			s += "finish";
			return s;
		}
	}

	/** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
	semantErrors++;
	return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
        return semantErrors != 0;
    }

    private void showEdgeFrom() {
    	DFS();
        System.out.println("pathTo debug: ");
        System.out.println("edgefrom's size is " + edgeFrom.size());
        for (Map.Entry<class_c, class_c> entry : edgeFrom.entrySet()) {
            if (entry.getValue() != null) {
                System.out.println("[ " + entry.getKey().name + " <-- " + entry.getValue().name + " ]");
            } else {
                System.out.println("[ " + entry.getKey().name + " <-- null ]");
            }
        }
    }
}
			  

