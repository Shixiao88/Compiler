/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;
import java.lang.System;
import java.util.Collections;
import java.util.ArrayList;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

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

    private int comment_count = 0;
    private int str_index = 0;
    private int digit_index = 0;
    private int id_index = 0;
    private int MAX_STR_SIZE = 1024;
    private String intBuffer = "";
    private String commentBuffer = "";
    private boolean hasSlash = false;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int COMMENT = 2;
	private final int LINECOMMENT = 3;
	private final int STRINGTEXT = 1;
	private final int yy_state_dtrans[] = {
		0,
		50,
		58,
		80
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NOT_ACCEPT,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"60:8,38:2,1,60,38,59,60:18,38,60,58,60:5,7,8,15,14,4,3,11,16,39:10,5,6,2,12" +
",13,60,18,40,41,42,43,44,28,41,45,46,41:2,47,41,48,49,50,41,51,52,32,53,54," +
"55,41:3,60:4,57,60,24,56,27,35,22,23,56,31,29,56:2,25,56,30,34,36,56,20,26," +
"19,21,33,37,56:3,9,60,10,17,60,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,166,
"0,1:2,2,3,1:3,4,1:4,5,1:2,6,1:3,7,8,9,10,1:7,11:2,12,11:16,13,1:7,14,15,16," +
"17:2,18,17:14,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,3" +
"9,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,11,6" +
"3,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,8" +
"8,89,90,91,92,93,94,95,11,17,96,97,98,99,100,101,102,103")[0];

	private int yy_nxt[][] = unpackFromString(104,61,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,156:2,158,59,156,114,156" +
",160,21,81,116,156,60,156,84,156,162,164,22,23,157:2,159,157,161,157,82,115" +
",117,85,163,157:4,165,156,14,24,-1,14,-1:64,25,-1:8,26,-1:51,27,-1:72,28,-1" +
":58,29,-1:55,30,-1:71,156,118,156:10,120,156:6,-1,122,156:5,120,156:5,118,1" +
"56:5,122,-1:22,157:10,61,157:8,-1,157:7,61,157:11,-1:41,22,-1:61,23,-1:40,1" +
"56:19,-1,122,156:17,122,-1:22,156:12,148,156:6,-1,122,156:5,148,156:11,122," +
"-1:3,1,51,78:56,52,78:2,1,53,54:5,79,54:7,83,54:43,-1,54,-1:19,156:5,128,15" +
"6:4,31,156:8,-1,122,128,156:5,31,156:10,122,-1:22,157:12,119,157:6,-1,157:6" +
",119,157:12,-1:22,157:19,-1,157:19,-1:22,157:12,141,157:6,-1,157:6,141,157:" +
"12,-1:5,78:56,-1,78:2,-1:15,55,-1:45,1,57,54:57,-1,54,-1:19,156:4,32,156:2," +
"136,156,32,156,33,156:7,-1,122,156:8,33,156:3,136,156:4,122,-1:22,157:4,62," +
"157:2,129,157,62,157,63,157:7,-1,157:9,63,157:3,129,157:5,-1:11,56,-1:71,15" +
"6:4,34,156:4,34,156:9,-1,122,156:17,122,-1:22,157:4,64,157:4,64,157:9,-1,15" +
"7:19,-1:22,35,156:12,35,156:5,-1,122,156:17,122,-1:22,65,157:12,65,157:5,-1" +
",157:19,-1:22,156:18,36,-1,122,156:15,36,156,122,-1:22,157:18,66,-1,157:16," +
"66,157:2,-1:22,37,156:12,37,156:5,-1,122,156:17,122,-1:22,67,157:12,67,157:" +
"5,-1,157:19,-1:22,156:3,38,156:15,-1,122,156:4,38,156:12,122,-1:22,157:11,6" +
"8,157:7,-1,157:9,68,157:9,-1:22,156:11,39,156:7,-1,122,156:8,39,156:8,122,-" +
"1:22,157:3,72,157:15,-1,157:5,72,157:13,-1:22,156:3,40,156:15,-1,122,156:4," +
"40,156:12,122,-1:22,157:3,69,157:15,-1,157:5,69,157:13,-1:22,156:8,41,156:1" +
"0,-1,122,156:2,41,156:14,122,-1:22,157:8,70,157:10,-1,157:3,70,157:15,-1:22" +
",156:17,42,156,-1,122,156:10,42,156:6,122,-1:22,157:17,71,157,-1,157:11,71," +
"157:7,-1:22,156:3,43,156:15,-1,122,156:4,43,156:12,122,-1:22,157:6,73,157:1" +
"2,-1,157:8,73,157:10,-1:22,156:6,44,156:12,-1,122,156:7,44,156:9,122,-1:22," +
"157:7,74,157:11,-1,157:13,74,157:5,-1:22,156:3,45,156:15,-1,122,156:4,45,15" +
"6:12,122,-1:22,157:3,75,157:15,-1,157:5,75,157:13,-1:22,156:7,46,156:11,-1," +
"122,156:12,46,156:4,122,-1:22,157:16,76,157:2,-1,157:4,76,157:14,-1:22,156:" +
"3,47,156:15,-1,122,156:4,47,156:12,122,-1:22,157:7,77,157:11,-1,157:13,77,1" +
"57:5,-1:22,156:16,48,156:2,-1,122,156:3,48,156:13,122,-1:22,156:7,49,156:11" +
",-1,122,156:12,49,156:4,122,-1:22,156:3,86,156:11,130,156:3,-1,122,156:4,86" +
",156:4,130,156:7,122,-1:22,157:3,87,157:11,131,157:3,-1,157:5,87,157:4,131," +
"157:8,-1:22,156:3,88,156:11,90,156:3,-1,122,156:4,88,156:4,90,156:7,122,-1:" +
"22,157:3,89,157:11,91,157:3,-1,157:5,89,157:4,91,157:8,-1:22,156:2,92,156:1" +
"6,-1,122,156:13,92,156:3,122,-1:22,157:3,93,157:15,-1,157:5,93,157:13,-1:22" +
",156:3,94,156:15,-1,122,156:4,94,156:12,122,-1:22,157:7,95,157:11,-1,157:13" +
",95,157:5,-1:22,157:5,137,157:13,-1,157,137,157:17,-1:22,156:7,96,156:11,-1" +
",122,156:12,96,156:4,122,-1:22,157:7,97,157:11,-1,157:13,97,157:5,-1:22,156" +
":5,98,156:13,-1,122,98,156:16,122,-1:22,157:5,99,157:13,-1,157,99,157:17,-1" +
":22,156:6,142,156:12,-1,122,156:7,142,156:9,122,-1:22,157:14,139,157:4,-1,1" +
"57:15,139,157:3,-1:22,156:15,100,156:3,-1,122,156:9,100,156:7,122,-1:22,157" +
":15,101,157:3,-1,157:10,101,157:8,-1:22,156:7,102,156:11,-1,122,156:12,102," +
"156:4,122,-1:22,157:15,103,157:3,-1,157:10,103,157:8,-1:22,156:5,144,156:13" +
",-1,122,144,156:16,122,-1:22,157:10,143,157:8,-1,157:7,143,157:11,-1:22,156" +
":14,146,156:4,-1,122,156:14,146,156:2,122,-1:22,157:7,105,157:11,-1,157:13," +
"105,157:5,-1:22,156:15,104,156:3,-1,122,156:9,104,156:7,122,-1:22,157:15,14" +
"5,157:3,-1,157:10,145,157:8,-1:22,156:10,150,156:8,-1,122,156:6,150,156:10," +
"122,-1:22,157:3,147,157:15,-1,157:5,147,157:13,-1:22,156:7,106,156:11,-1,12" +
"2,156:12,106,156:4,122,-1:22,157:6,107,157:12,-1,157:8,107,157:10,-1:22,156" +
":7,108,156:11,-1,122,156:12,108,156:4,122,-1:22,157:10,109,157:8,-1,157:7,1" +
"09,157:11,-1:22,156:15,152,156:3,-1,122,156:9,152,156:7,122,-1:22,157,149,1" +
"57:17,-1,157:12,149,157:6,-1:22,156:3,153,156:15,-1,122,156:4,153,156:12,12" +
"2,-1:22,157:10,151,157:8,-1,157:7,151,157:11,-1:22,156:6,110,156:12,-1,122," +
"156:7,110,156:9,122,-1:22,111,157:12,111,157:5,-1,157:19,-1:22,156:10,112,1" +
"56:8,-1,122,156:6,112,156:10,122,-1:22,156,154,156:17,-1,122,156:11,154,156" +
":5,122,-1:22,156:10,155,156:8,-1,122,156:6,155,156:10,122,-1:22,113,156:12," +
"113,156:5,-1,122,156:17,122,-1:22,156:6,124,126,156:11,-1,122,156:7,124,156" +
":4,126,156:4,122,-1:22,157:5,121,123,157:12,-1,157,121,157:6,123,157:10,-1:" +
"22,156:5,132,134,156:12,-1,122,132,156:6,134,156:9,122,-1:22,157:6,125,127," +
"157:11,-1,157:8,125,157:4,127,157:5,-1:22,156:15,138,156:3,-1,122,156:9,138" +
",156:7,122,-1:22,157:15,133,157:3,-1,157:10,133,157:8,-1:22,156:12,140,156:" +
"6,-1,122,156:5,140,156:11,122,-1:22,157:12,135,157:6,-1,157:6,135,157:12,-1" +
":3");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ curr_lineno++;			      }
					case -3:
						break;
					case 3:
						{ return new Symbol(TokenConstants.LT);     }
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.MINUS);  }
					case -5:
						break;
					case 5:
						{ return new Symbol(TokenConstants.COMMA);  }
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.COLON);  }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.SEMI);   }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.DOT);    }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.EQ);     }
					case -14:
						break;
					case 14:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
//                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext());
                                  return new Symbol(TokenConstants.ERROR, yytext()); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.PLUS);   }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.MULT);   }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.DIV);    }
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.NEG);    }
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.AT);     }
					case -20:
						break;
					case 20:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -21:
						break;
					case 21:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -22:
						break;
					case 22:
						{ /* Get rid of whitespace */ }
					case -23:
						break;
					case 23:
						{ IntSymbol intSym = new IntSymbol(yytext(), yytext().length(), digit_index); 
                            digit_index ++;
                            return new Symbol(TokenConstants.INT_CONST, intSym); }
					case -24:
						break;
					case 24:
						{ yybegin(STRINGTEXT); string_buf = new StringBuffer();   }
					case -25:
						break;
					case 25:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.LE);     }
					case -27:
						break;
					case 27:
						{ yybegin(LINECOMMENT); }
					case -28:
						break;
					case 28:
						{ yybegin(COMMENT); 
                              comment_count = 1;
                              commentBuffer = ""; }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.DARROW); }
					case -30:
						break;
					case 30:
						{ return new Symbol(TokenConstants.ERROR, "Unmatched *)"); }
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.FI); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.IF); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.IN); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.OF); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.LET); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.NEW); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.NOT); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.BOOL_CONST, true); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.THEN); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.ELSE); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.ESAC); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.LOOP); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.CASE); }
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.POOL); }
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.BOOL_CONST, false); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.CLASS); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.WHILE); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -50:
						break;
					case 50:
						{ StringBuilder s = new StringBuilder(yytext());
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
					case -51:
						break;
					case 51:
						{ curr_lineno ++;
			      if (hasSlash) {
				  string_buf.append('\n');
				  hasSlash = false;
			      } else {
				    yybegin(YYINITIAL);
				    return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
                                }  }
					case -52:
						break;
					case 52:
						{ if (hasSlash)  {
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
					case -53:
						break;
					case 53:
						{ curr_lineno ++; }
					case -54:
						break;
					case 54:
						{ }
					case -55:
						break;
					case 55:
						{ comment_count ++; }
					case -56:
						break;
					case 56:
						{ if (comment_count == 1) {
                                    yybegin(YYINITIAL);
//				    System.out.println("block comemnt finished:" + commentBuffer);
                               } else {
                               comment_count --;
                               }}
					case -57:
						break;
					case 57:
						{ yybegin(YYINITIAL);
//                              System.out.println("inline comemnt finished:" + commentBuffer);
			      curr_lineno ++; }
					case -58:
						break;
					case 59:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -59:
						break;
					case 60:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -60:
						break;
					case 61:
						{ return new Symbol(TokenConstants.FI); }
					case -61:
						break;
					case 62:
						{ return new Symbol(TokenConstants.IF); }
					case -62:
						break;
					case 63:
						{ return new Symbol(TokenConstants.IN); }
					case -63:
						break;
					case 64:
						{ return new Symbol(TokenConstants.OF); }
					case -64:
						break;
					case 65:
						{ return new Symbol(TokenConstants.LET); }
					case -65:
						break;
					case 66:
						{ return new Symbol(TokenConstants.NEW); }
					case -66:
						break;
					case 67:
						{ return new Symbol(TokenConstants.NOT); }
					case -67:
						break;
					case 68:
						{ return new Symbol(TokenConstants.THEN); }
					case -68:
						break;
					case 69:
						{ return new Symbol(TokenConstants.ELSE); }
					case -69:
						break;
					case 70:
						{ return new Symbol(TokenConstants.ESAC); }
					case -70:
						break;
					case 71:
						{ return new Symbol(TokenConstants.LOOP); }
					case -71:
						break;
					case 72:
						{ return new Symbol(TokenConstants.CASE); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.POOL); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.CLASS); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.WHILE); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -77:
						break;
					case 78:
						{ StringBuilder s = new StringBuilder(yytext());
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
					case -78:
						break;
					case 79:
						{ }
					case -79:
						break;
					case 81:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -80:
						break;
					case 82:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -81:
						break;
					case 83:
						{ }
					case -82:
						break;
					case 84:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -83:
						break;
					case 85:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -84:
						break;
					case 86:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -85:
						break;
					case 87:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -86:
						break;
					case 88:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -87:
						break;
					case 89:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -88:
						break;
					case 90:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -89:
						break;
					case 91:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -90:
						break;
					case 92:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -91:
						break;
					case 93:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -92:
						break;
					case 94:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -93:
						break;
					case 95:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -94:
						break;
					case 96:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -95:
						break;
					case 97:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -96:
						break;
					case 98:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -97:
						break;
					case 99:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -98:
						break;
					case 100:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -99:
						break;
					case 101:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -100:
						break;
					case 102:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -101:
						break;
					case 103:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -102:
						break;
					case 104:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -103:
						break;
					case 105:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -104:
						break;
					case 106:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -105:
						break;
					case 107:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -106:
						break;
					case 108:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -107:
						break;
					case 109:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -108:
						break;
					case 110:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -109:
						break;
					case 111:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -110:
						break;
					case 112:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -111:
						break;
					case 113:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -112:
						break;
					case 114:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -113:
						break;
					case 115:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -114:
						break;
					case 116:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -115:
						break;
					case 117:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -116:
						break;
					case 118:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -117:
						break;
					case 119:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -118:
						break;
					case 120:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -119:
						break;
					case 121:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -120:
						break;
					case 122:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -121:
						break;
					case 123:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -122:
						break;
					case 124:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -123:
						break;
					case 125:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -124:
						break;
					case 126:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -125:
						break;
					case 127:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -126:
						break;
					case 128:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -127:
						break;
					case 129:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -128:
						break;
					case 130:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -129:
						break;
					case 131:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -130:
						break;
					case 132:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -131:
						break;
					case 133:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -132:
						break;
					case 134:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -133:
						break;
					case 135:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -134:
						break;
					case 136:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -135:
						break;
					case 137:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -136:
						break;
					case 138:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -137:
						break;
					case 139:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -138:
						break;
					case 140:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -139:
						break;
					case 141:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -140:
						break;
					case 142:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -141:
						break;
					case 143:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -142:
						break;
					case 144:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -143:
						break;
					case 145:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -144:
						break;
					case 146:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -145:
						break;
					case 147:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -146:
						break;
					case 148:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -147:
						break;
					case 149:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -148:
						break;
					case 150:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -149:
						break;
					case 151:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -150:
						break;
					case 152:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -151:
						break;
					case 153:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -152:
						break;
					case 154:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -153:
						break;
					case 155:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -154:
						break;
					case 156:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -155:
						break;
					case 157:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -156:
						break;
					case 158:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -157:
						break;
					case 159:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -158:
						break;
					case 160:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -159:
						break;
					case 161:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -160:
						break;
					case 162:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -161:
						break;
					case 163:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -162:
						break;
					case 164:
						{ IdSymbol objIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.OBJECTID, objIdSym);   }
					case -163:
						break;
					case 165:
						{ IdSymbol typeIdSym = new IdSymbol(yytext(), yytext().length(), id_index);
                            id_index ++;
                            return new Symbol(TokenConstants.TYPEID, typeIdSym); }
					case -164:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
