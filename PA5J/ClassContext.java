/*  Edit by Xiao on
 *  2018/01/21
 *  this is the wrapped context of so, E:
 *  E is another wrapped class of (int offset, string ptrName)
 * */



public class ClassContext {
    private AbstractSymbol s;
    SymbolTable env;

    ClassContext() {
        s = TreeConstants.self;
        env = new SymbolTable();
        env.enterScope();
    }

    void setSo(CgenNode cl) {
        env.addId(this.s, cl);
    }

    CgenNode getSo() {
        return (CgenNode)env.probe(s);
    }

    /* enter to a new scope with the same self pointer */
    public void enterScope() {
        Object oself = env.probe(s);

        env.enterScope();
        if (oself != null) {
            env.addId(s, oself);
        }
        // offset to current $fp is one (in middle is the $ra)
        env.addId(TreeConstants.mxExOffset, -2);
    }

    /* enter to a new scope with all value in the previous values stays the same */
    public void enterScopeCopyPrevious() {
        env.enterScopeCopyPrevious();
    }


    public void exitScope() {
        env.exitScope();
    }

    void addInEnv(AbstractSymbol sym, int offset) {
        // always maintain the offset to record the smallest (because is negative) offset in this frame.
        // if recorded most extended offset is smaller than the address, just append this symbol to the env.
        // this maybe buggie because add in a existed symbol with more extended address

        int mostExtendedOffset = (int)env.probe(TreeConstants.mxExOffset);
        if (mostExtendedOffset > offset) {
            appendInEnv(sym);
        } else {
            this.env.addId(sym, offset);
        }
    }

    /** this only occur when add the $a0 value to the stack */
    int appendInEnv(AbstractSymbol sym) {
        int envOffset = (int)env.probe(TreeConstants.mxExOffset);
        int addrOffset = envOffset;
        envOffset -= 1;
        env.addId(TreeConstants.mxExOffset, envOffset);
        env.addId(sym, addrOffset);
        return addrOffset;
    }

    int getAddrInEnv(AbstractSymbol sym) {
        return (int) this.env.lookup(sym);
    }

    boolean exitInCurrentScope(AbstractSymbol sym) {
        if (env.probe(sym) == null) {
            return false;
        }
        return true;
    }

    Object probeInEnv(AbstractSymbol sym) {
        return env.probe(sym);
    }

    boolean exitInNonLocalScope(AbstractSymbol sym) {
        if (lookupInEnv(sym) == null) {
            return false;
        }
        return true;
    }

    Object lookupInEnv(AbstractSymbol sym) {
        return env.lookup(sym);
    }


}

    /** this is the look up for variables, once have Assign/Let etc.
     *  store them in the env such as E(x) = l, and the l is represented as
     *  an offset and a fixed ptr($fp) */
    /*class Address {
        /* offset will perform same like in stack, to avoid misleading
        *  so local and temps will have negative offset, params will have
        *  positive offset */
        /*private int offset;
        private String ptr;

        /** if address is not fixed */
        /*Address() {
            ptr = "not fix";
        }

        Address(int offset, String ptr) {
            this.offset = offset;
            this.ptr = ptr;
        }

        void setPtr(String ptr) {
            this.ptr = ptr;
        }

        void setOffset(int offset) {
            this.offset = offset;
        }

        String getPtr() {
            return this.ptr;
        }

        int getOffset() {
            return this.offset;
        }

        boolean isAddrValid() {
            return ptr.equals(CgenSupport.FP);
        }
    }*/
