/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;
import java.lang.System;
import java.util.Collections;
import java.util.ArrayList;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
    /* nothing special to do in the initial state */
    break;
    /* If necessary, add code for other states here, e.g:
       case COMMENT:
       ...
       break;
    */    
    case COMMENT:
        return new Symbol(TokenConstants.ERROR, "EOF in comment");
    case LINECOMMENT:
        return new Symbol(TokenConstants.ERROR, "EOF in comment");
    case STRINGTEXT:
        return new Symbol(TokenConstants.ERROR, "EOF in string constant");
    }
    return new Symbol(TokenConstants.EOF);   
%eofval}

%{
    private int comment_count = 0;
    private int str_index = 0;
    private int digit_index = 0;
    private int id_index = 0;
    private int MAX_STR_SIZE = 1024;
    private String intBuffer = "";
    private String commentBuffer = "";
    private boolean hasSlash = false;
%}

%class CoolLexer
%cup


%state STRINGTEXT
%state COMMENT
%state LINECOMMENT

ALPHA=[a-zA-Z]
DIGIT=[0-9]
BLANK=[\ \t\b\f]

%%
<YYINITIAL> \n	  { curr_lineno++;			      }
<YYINITIAL> "<-"  { return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL> ","   { return new Symbol(TokenConstants.COMMA);  }
<YYINITIAL> ":"   { return new Symbol(TokenConstants.COLON);  }
<YYINITIAL> ";"   { return new Symbol(TokenConstants.SEMI);   }
<YYINITIAL> "("   { return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL> ")"   { return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL> "{"   { return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL> "}"   { return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL> "."   { return new Symbol(TokenConstants.DOT);    }
<YYINITIAL> "=>"  { return new Symbol(TokenConstants.DARROW); }
<YYINITIAL> "+"   { return new Symbol(TokenConstants.PLUS);   }
<YYINITIAL> "-"   { return new Symbol(TokenConstants.MINUS);  }
<YYINITIAL> "*"   { return new Symbol(TokenConstants.MULT);   }
<YYINITIAL> "/"   { return new Symbol(TokenConstants.DIV);    }
<YYINITIAL> "="   { return new Symbol(TokenConstants.EQ);     }
<YYINITIAL> "~"   { return new Symbol(TokenConstants.NEG);    }
<YYINITIAL> "<"   { return new Symbol(TokenConstants.LT);     }
<YYINITIAL> "<="  { return new Symbol(TokenConstants.LE);     }
<YYINITIAL> "@"   { return new Symbol(TokenConstants.AT);     }
<YYINITIAL> "*)"  { return new Symbol(TokenConstants.ERROR, "Unmatched *)"); }
<YYINITIAL> t[Rr][Uu][Ee]                    { return new Symbol(TokenConstants.BOOL_CONST, true); }
<YYINITIAL> f[Aa][Ll][Ss][Ee]                { return new Symbol(TokenConstants.BOOL_CONST, false); }
<YYINITIAL> [cC][lL][aA][sS][sS]             { return new Symbol(TokenConstants.CLASS); }
<YYINITIAL> [eE][lL][sS][eE]                 { return new Symbol(TokenConstants.ELSE); }
<YYINITIAL> [fF][iI]                         { return new Symbol(TokenConstants.FI); }
<YYINITIAL> [iI][fF]                         { return new Symbol(TokenConstants.IF); }
<YYINITIAL> [iI][nN]                         { return new Symbol(TokenConstants.IN); }
<YYINITIAL> [iI][nN][hH][eE][rR][iI][tT][sS] { return new Symbol(TokenConstants.INHERITS); }
<YYINITIAL> [iI][sS][vV][oO][iI][dD]         { return new Symbol(TokenConstants.ISVOID); }
<YYINITIAL> [lL][eE][tT]                     { return new Symbol(TokenConstants.LET); }
<YYINITIAL> [lL][oO][oO][pP]                 { return new Symbol(TokenConstants.LOOP); }
<YYINITIAL> [pP][oO][oO][lL]                 { return new Symbol(TokenConstants.POOL); }
<YYINITIAL> [tT][hH][eE][nN]                 { return new Symbol(TokenConstants.THEN); }
<YYINITIAL> [wW][hH][iI][lL][eE]             { return new Symbol(TokenConstants.WHILE); }
<YYINITIAL> [cC][aA][sS][eE]                 { return new Symbol(TokenConstants.CASE); }
<YYINITIAL> [eE][sS][aA][cC]                 { return new Symbol(TokenConstants.ESAC); }
<YYINITIAL> [nN][eE][wW]                     { return new Symbol(TokenConstants.NEW); }
<YYINITIAL> [oO][fF]                         { return new Symbol(TokenConstants.OF); }
<YYINITIAL> [nN][oO][tT]                     { return new Symbol(TokenConstants.NOT); }
<YYINITIAL> {BLANK}+                          { /* Get rid of whitespace */ }
<YYINITIAL> {DIGIT}+        { IntSymbol intSym = new IntSymbol(yytext(), yytext().length(), digit_index); 
                            digit_index ++;
                            return new Symbol(TokenConstants.INT_CONST, intSym); }

<YYINITIAL> [A-Z]({ALPHA}|{DIGIT}|_)*    { IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
<YYINITIAL> [a-z]({ALPHA}|{DIGIT}|_)*    { IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
<YYINITIAL> \"              { yybegin(STRINGTEXT); string_buf = new StringBuffer();   }
<STRINGTEXT> \n		    { curr_lineno ++;
			      if (hasSlash) {
				  string_buf.append('\n');
				  hasSlash = false;
			      } else {
				    yybegin(YYINITIAL);
				    return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
                                }  } 
<STRINGTEXT> \"		     { if (hasSlash)  {
				  string_buf.append("\"");	
				  hasSlash = false;
			       } else {
				   if (string_buf.length() >= MAX_STR_CONST) {
				       return new Symbol(TokenConstants.ERROR, "String content too long." );
			     	   } else {
				       yybegin(YYINITIAL);
				       StringSymbol strSym = new StringSymbol(string_buf.toString(), string_buf.length(), str_index);
				       str_index ++;
				   //System.out.println("string finished: " + stringBuffer);
//        				   System.out.println("string length: " + stringBuffer.length());
                           	       return new Symbol(TokenConstants.STR_CONST, strSym);
			     	   } }	}
<STRINGTEXT> [^\n\"]*        { StringBuilder s = new StringBuilder(yytext());
			     //check if yytext() has backslash, if so, turn the flag on
			      ArrayList<Integer> slashIndexes = new ArrayList(); 
			      for (int i = 0; i < s.length(); i ++) {
				  if (s.charAt(i) == '\\') {
				      slashIndexes.add(i);
				  } else if (s.charAt(i) == '\0') {
				      return new Symbol(TokenConstants.ERROR, "String contains null");   	     
				  }
			      }
			      boolean isScaped = false;
			      int i = -1;
			      for (int index : slashIndexes) {
			//	System.out.println("slash index is :"  + index);System.out.println("i is :"  + i);

				// index > i garantee the situation like //n
				  if (index + 1 < s.length() && index > i) {
				      i = index + 1;
				      char followSlash = s.charAt(i);
				      switch (followSlash) {
					  case '\\':
					      s.setCharAt(i, '\\');
					      isScaped = true;
					      break;
					  case 'b':
					      s.setCharAt(i, '\b');
					      isScaped = true;
					      break;
					  case '0':
					      s.setCharAt(i, '0');
					      isScaped = true;
					      break;
					  case 'f':
					      s.setCharAt(i, '\f');
					      isScaped = true;
					      break;
					  case 't':
					      s.setCharAt(i, '\t');
					      isScaped = true;
					      break;	
					  case '"':
					     s.setCharAt(i,  '"');
					     isScaped = true;
					     break;
					  case 'n':
					     s.setCharAt(i, '\n');
					     isScaped = true;
					     break;
					}
				    } else if (index == s.length() - 1 && index > i) {
					hasSlash = true;
				    } else if (index == s.length() - 1 && index <= i) {
					hasSlash = false;
				    } 
				}
				Collections.reverse(slashIndexes);
//				System.out.println("string test " + "\\" );
				for (int index : slashIndexes) {
				    s.deleteCharAt(index);
			    	}
				string_buf.append(s.toString());
			    } 
<YYINITIAL> "--"            { yybegin(LINECOMMENT); }
<LINECOMMENT> \n            { yybegin(YYINITIAL);
//                              System.out.println("inline comemnt finished:" + commentBuffer);
			      curr_lineno ++; }
<YYINITIAL> "(*"            { yybegin(COMMENT); 
                              comment_count = 1;
                              commentBuffer = ""; }
<COMMENT> "(*"              { comment_count ++; }
<COMMENT> \n                { curr_lineno ++; }
<COMMENT> "*)"              { if (comment_count == 1) {
                                    yybegin(YYINITIAL);
//				    System.out.println("block comemnt finished:" + commentBuffer);
                               } else {
                               comment_count --;
                               }}
<COMMENT,LINECOMMENT>.      { }
.                           { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
//                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext());
                                  return new Symbol(TokenConstants.ERROR, yytext()); }
