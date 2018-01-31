# start of generated code
# start emit global data
	.data
	.align	2
	.globl	class_nameTab
	.globl	Main_protObj
	.globl	Int_protObj
	.globl	String_protObj
	.globl	bool_const0
	.globl	bool_const1
	.globl	_int_tag
	.globl	_bool_tag
	.globl	_string_tag
_int_tag:
	.word	2
_bool_tag:
	.word	3
_string_tag:
	.word	1
# finish emit global data
# start emit CG choice constants
	.globl	_MemMgr_INITIALIZER
_MemMgr_INITIALIZER:
	.word	_NoGC_Init
	.globl	_MemMgr_COLLECTOR
_MemMgr_COLLECTOR:
	.word	_NoGC_Collect
	.globl	_MemMgr_TEST
_MemMgr_TEST:
	.word	0
# finish emit CG choice constants
# start emit constants
	.word	-1
str_const12:
	.word	1
	.word	5
	.word	String_dispTab
	.word	int_const0
	.byte	0	
	.align	2
	.word	-1
str_const11:
	.word	1
	.word	6
	.word	String_dispTab
	.word	int_const1
	.ascii	"Main"
	.byte	0	
	.align	2
	.word	-1
str_const10:
	.word	1
	.word	6
	.word	String_dispTab
	.word	int_const2
	.ascii	"String"
	.byte	0	
	.align	2
	.word	-1
str_const9:
	.word	1
	.word	6
	.word	String_dispTab
	.word	int_const1
	.ascii	"Bool"
	.byte	0	
	.align	2
	.word	-1
str_const8:
	.word	1
	.word	5
	.word	String_dispTab
	.word	int_const3
	.ascii	"Int"
	.byte	0	
	.align	2
	.word	-1
str_const7:
	.word	1
	.word	5
	.word	String_dispTab
	.word	int_const4
	.ascii	"IO"
	.byte	0	
	.align	2
	.word	-1
str_const6:
	.word	1
	.word	6
	.word	String_dispTab
	.word	int_const2
	.ascii	"Object"
	.byte	0	
	.align	2
	.word	-1
str_const5:
	.word	1
	.word	7
	.word	String_dispTab
	.word	int_const5
	.ascii	"_prim_slot"
	.byte	0	
	.align	2
	.word	-1
str_const4:
	.word	1
	.word	7
	.word	String_dispTab
	.word	int_const6
	.ascii	"SELF_TYPE"
	.byte	0	
	.align	2
	.word	-1
str_const3:
	.word	1
	.word	7
	.word	String_dispTab
	.word	int_const6
	.ascii	"_no_class"
	.byte	0	
	.align	2
	.word	-1
str_const2:
	.word	1
	.word	8
	.word	String_dispTab
	.word	int_const7
	.ascii	"<basic class>"
	.byte	0	
	.align	2
	.word	-1
str_const1:
	.word	1
	.word	8
	.word	String_dispTab
	.word	int_const8
	.ascii	"Hello, World.\n"
	.byte	0	
	.align	2
	.word	-1
str_const0:
	.word	1
	.word	8
	.word	String_dispTab
	.word	int_const8
	.ascii	"hello_world.cl"
	.byte	0	
	.align	2
	.word	-1
int_const8:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	14
	.word	-1
int_const7:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	13
	.word	-1
int_const6:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	9
	.word	-1
int_const5:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	10
	.word	-1
int_const4:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	2
	.word	-1
int_const3:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	3
	.word	-1
int_const2:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	6
	.word	-1
int_const1:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	4
	.word	-1
int_const0:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	0
	.word	-1
bool_const0:
	.word	3
	.word	4
	.word	Bool_dispTab
	.word	0
	.word	-1
bool_const1:
	.word	3
	.word	4
	.word	Bool_dispTab
	.word	1
void_class:

	.word	-1
	.word	3
# finish emit constants

# start emit prototypes
Object_protObj:
	.word	-1
	.word	4
	.word	3
	.word	Object_dispTab
IO_protObj:
	.word	-1
	.word	5
	.word	3
	.word	IO_dispTab
Int_protObj:
	.word	-1
	.word	6
	.word	4
	.word	Int_dispTab
	.word	void_class
Bool_protObj:
	.word	-1
	.word	7
	.word	4
	.word	Bool_dispTab
	.word	void_class
String_protObj:
	.word	-1
	.word	8
	.word	5
	.word	String_dispTab
	.word	0
	.word	void_class
Main_protObj:
	.word	-1
	.word	9
	.word	3
	.word	Main_dispTab
# finish emit prototypes


# start emit object table
class_objTab:
	.word	Object_protObj
	.word	IO_protObj
	.word	Int_protObj
	.word	Bool_protObj
	.word	String_protObj
	.word	Main_protObj
# finish emit object table


# start emit class name table
class_nameTab:
	.ascii	"Object"
	.byte	0	
	.align	2
	.ascii	"IO"
	.byte	0	
	.align	2
	.ascii	"Int"
	.byte	0	
	.align	2
	.ascii	"Bool"
	.byte	0	
	.align	2
	.ascii	"String"
	.byte	0	
	.align	2
	.ascii	"Main"
	.byte	0	
	.align	2
# finish emit class name table


# start emit dispatch table
Object_dispTab:
	.word	Object_init
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
IO_dispTab:
	.word	IO_init
	.word	IO.abort
	.word	IO.type_name
	.word	IO.copy
	.word	IO.out_string
	.word	IO.out_int
	.word	IO.in_string
	.word	IO.in_int
Int_dispTab:
	.word	Int_init
	.word	Int.abort
	.word	Int.type_name
	.word	Int.copy
Bool_dispTab:
	.word	Bool_init
	.word	Bool.abort
	.word	Bool.type_name
	.word	Bool.copy
String_dispTab:
	.word	String_init
	.word	String.abort
	.word	String.type_name
	.word	String.copy
	.word	String.length
	.word	String.concat
	.word	String.substr
Main_dispTab:
	.word	Main_init
	.word	Main.abort
	.word	Main.type_name
	.word	Main.copy
	.word	Main.out_string
	.word	Main.out_int
	.word	Main.in_string
	.word	Main.in_int
	.word	Main.main
# finish emit dispatch table

# start emit global text
	.globl	heap_start
heap_start:
	.word	0
	.text
	.globl	Main_init
	.globl	Int_init
	.globl	String_init
	.globl	Bool_init
	.globl	Main.main
# finish emit global text

# start all emit init of prototypes

# start emit init for class: Object
Object_init:
	move	$s0 $a0
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	move	$s0 $a0
# finish emit init for class: Object


# start emit init for class: IO
IO_init:
	move	$s0 $a0
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	move	$s0 $a0
	lw	$a0 0($sp)
	addiu	$sp $sp 4
	move	$s0 $a0
# finish emit init for class: IO


# start emit init for class: Int
Int_init:
	move	$s0 $a0
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	move	$s0 $a0
	lw	$a0 0($sp)
	addiu	$sp $sp 4
	move	$s0 $a0
# finish emit init for class: Int


# start emit init for class: Bool
Bool_init:
	move	$s0 $a0
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	move	$s0 $a0
	lw	$a0 0($sp)
	addiu	$sp $sp 4
	move	$s0 $a0
# finish emit init for class: Bool


# start emit init for class: String
String_init:
	move	$s0 $a0
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	move	$s0 $a0
	lw	$a0 0($sp)
	addiu	$sp $sp 4
	move	$s0 $a0
# finish emit init for class: String


# start emit init for class: Main
Main_init:
	move	$s0 $a0
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	la	$a0 IO_protObj
	jal	Object.copy
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	move	$s0 $a0
	lw	$a0 0($sp)
	addiu	$sp $sp 4
	move	$s0 $a0
	lw	$a0 0($sp)
	addiu	$sp $sp 4
	move	$s0 $a0
# finish emit init for class: Main

# finish all emit init of prototypes


# start emit methods
	la	$s0 Object_protObj
Object.abort:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Object_dispTab
	sw	$a0 0($t1)
Object.type_name:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Object_dispTab
	sw	$a0 4($t1)
Object.copy:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Object_dispTab
	sw	$a0 8($t1)
	la	$s0 IO_protObj
IO.abort:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 IO_dispTab
	sw	$a0 0($t1)
IO.type_name:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 IO_dispTab
	sw	$a0 4($t1)
IO.copy:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 IO_dispTab
	sw	$a0 8($t1)
IO.out_string:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 IO_dispTab
	sw	$a0 12($t1)
IO.out_int:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 IO_dispTab
	sw	$a0 16($t1)
IO.in_string:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 IO_dispTab
	sw	$a0 20($t1)
IO.in_int:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 IO_dispTab
	sw	$a0 24($t1)
	la	$s0 Int_protObj
Int.abort:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Int_dispTab
	sw	$a0 0($t1)
Int.type_name:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Int_dispTab
	sw	$a0 4($t1)
Int.copy:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Int_dispTab
	sw	$a0 8($t1)
	la	$s0 Bool_protObj
Bool.abort:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Bool_dispTab
	sw	$a0 0($t1)
Bool.type_name:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Bool_dispTab
	sw	$a0 4($t1)
Bool.copy:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Bool_dispTab
	sw	$a0 8($t1)
	la	$s0 String_protObj
String.abort:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 String_dispTab
	sw	$a0 0($t1)
String.type_name:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 String_dispTab
	sw	$a0 4($t1)
String.copy:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 String_dispTab
	sw	$a0 8($t1)
String.length:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 String_dispTab
	sw	$a0 12($t1)
String.concat:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 String_dispTab
	sw	$a0 16($t1)
String.substr:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 String_dispTab
	sw	$a0 20($t1)
	la	$s0 Main_protObj
Main.abort:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Main_dispTab
	sw	$a0 0($t1)
Main.type_name:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Main_dispTab
	sw	$a0 4($t1)
Main.copy:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Main_dispTab
	sw	$a0 8($t1)
Main.out_string:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Main_dispTab
	sw	$a0 12($t1)
Main.out_int:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Main_dispTab
	sw	$a0 16($t1)
Main.in_string:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Main_dispTab
	sw	$a0 20($t1)
Main.in_int:

	li	$a0 0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Main_dispTab
	sw	$a0 24($t1)
Main.main:

	la	$a0 str_const1
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	lw	$a0 0($s0)
	lw	$s0 0($a0)
	lw	$a0 12($a0)
	jalr	$a0
	sw	$fp 0($sp)
	addiu	$sp $sp -4
	sw	$ra 0($sp)
	addiu	$sp $sp -4
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	jr	$ra	
	la	$t1 Main_dispTab
	sw	$a0 28($t1)
# finish emit methods


# end of generated code
