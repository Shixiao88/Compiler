
(*  example cool program testing as many aspects of the code generator
    as possible.
 *)

(*
class Main inherits IO {
   newline() : Object {
	out_string("\n")
   };

   prompt() : String {
	{
	   out_string("Enter a number>");
	   in_string();
	}
   };

   main() : Object {
     (let z : A2I <- new A2I in
	while true loop
	   (let s : String <- prompt() in
		if s = "stop" then
		    abort()
		else
		   (let i : Int <- z.a2i(s) in
			   {
			     out_int(i);
			     newline();
			   }
	               )
		fi
		)
		              pool
     )
   };
};
*)

class Main inherits IO {
prompt() : String {
	{
	   out_string("Enter a number>");
	   in_string();
	}
   };

   main() : Object {

        while true loop
	   (let s : String <- prompt() in
		if s = "stop" then
		    abort()
		else
			   {
			     out_string(s);
			   }
		fi
		)
		              pool
   };
};
