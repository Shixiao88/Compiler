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
	.globl	void_class
	.globl	class_objTab
	.globl	Object_dispTab
	.globl	IO_dispTab
	.globl	Int_dispTab
	.globl	Bool_dispTab
	.globl	String_dispTab
	.globl	Main_dispTab
_int_tag:
	.word	2
_bool_tag:
	.word	3
_string_tag:
	.word	4
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
str_const13:
	.word	4
	.word	5
	.word	String_dispTab
	.word	int_const1
	.byte	0	
	.align	2
	.word	-1
str_const12:
	.word	4
	.word	6
	.word	String_dispTab
	.word	int_const2
	.ascii	"Main"
	.byte	0	
	.align	2
	.word	-1
str_const11:
	.word	4
	.word	6
	.word	String_dispTab
	.word	int_const3
	.ascii	"String"
	.byte	0	
	.align	2
	.word	-1
str_const10:
	.word	4
	.word	6
	.word	String_dispTab
	.word	int_const2
	.ascii	"Bool"
	.byte	0	
	.align	2
	.word	-1
str_const9:
	.word	4
	.word	5
	.word	String_dispTab
	.word	int_const4
	.ascii	"Int"
	.byte	0	
	.align	2
	.word	-1
str_const8:
	.word	4
	.word	5
	.word	String_dispTab
	.word	int_const5
	.ascii	"IO"
	.byte	0	
	.align	2
	.word	-1
str_const7:
	.word	4
	.word	6
	.word	String_dispTab
	.word	int_const3
	.ascii	"Object"
	.byte	0	
	.align	2
	.word	-1
str_const6:
	.word	4
	.word	7
	.word	String_dispTab
	.word	int_const6
	.ascii	"_prim_slot"
	.byte	0	
	.align	2
	.word	-1
str_const5:
	.word	4
	.word	7
	.word	String_dispTab
	.word	int_const7
	.ascii	"SELF_TYPE"
	.byte	0	
	.align	2
	.word	-1
str_const4:
	.word	4
	.word	7
	.word	String_dispTab
	.word	int_const7
	.ascii	"_no_class"
	.byte	0	
	.align	2
	.word	-1
str_const3:
	.word	4
	.word	8
	.word	String_dispTab
	.word	int_const8
	.ascii	"<basic class>"
	.byte	0	
	.align	2
	.word	-1
str_const2:
	.word	4
	.word	6
	.word	String_dispTab
	.word	int_const2
	.ascii	"stop"
	.byte	0	
	.align	2
	.word	-1
str_const1:
	.word	4
	.word	8
	.word	String_dispTab
	.word	int_const9
	.ascii	"Enter a number>"
	.byte	0	
	.align	2
	.word	-1
str_const0:
	.word	4
	.word	7
	.word	String_dispTab
	.word	int_const6
	.ascii	"example.cl"
	.byte	0	
	.align	2
	.word	-1
int_const9:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	15
	.word	-1
int_const8:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	13
	.word	-1
int_const7:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	9
	.word	-1
int_const6:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	10
	.word	-1
int_const5:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	2
	.word	-1
int_const4:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	3
	.word	-1
int_const3:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	6
	.word	-1
int_const2:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	4
	.word	-1
int_const1:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	0
	.word	-1
int_const0:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	1
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
	.word	-1
Object_protObj:
	.word	0
	.word	3
	.word	Object_dispTab
	.word	-1
IO_protObj:
	.word	1
	.word	3
	.word	IO_dispTab
	.word	-1
Int_protObj:
	.word	2
	.word	4
	.word	Int_dispTab
	.word	0
	.word	-1
Bool_protObj:
	.word	6
	.word	4
	.word	Bool_dispTab
	.word	0
	.word	-1
String_protObj:
	.word	4
	.word	5
	.word	String_dispTab
	.word	int_const1
	.word	0
	.word	-1
Main_protObj:
	.word	6
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
	.word	str_const7
	.word	str_const8
	.word	str_const9
	.word	str_const10
	.word	str_const11
	.word	str_const12
# finish emit class name table


# start emit dispatch table
Object_dispTab:
	.word	Object_init
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
IO_dispTab:
	.word	IO_init
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	IO.out_string
	.word	IO.out_int
	.word	IO.in_string
	.word	IO.in_int
Int_dispTab:
	.word	Int_init
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
Bool_dispTab:
	.word	Bool_init
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
String_dispTab:
	.word	String_init
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	String.length
	.word	String.concat
	.word	String.substr
Main_dispTab:
	.word	Main_init
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	IO.out_string
	.word	IO.out_int
	.word	IO.in_string
	.word	IO.in_int
	.word	Main.prompt
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
	.globl	Object.abort
	.globl	Object.type_name
	.globl	Object.copy
	.globl	IO.out_string
	.globl	IO.out_int
	.globl	IO.in_string
	.globl	IO.in_int
	.globl	String.length
	.globl	String.concat
	.globl	String.substr
	.globl	Main.prompt
	.globl	Main.main
# finish emit global text

# start all emit init of prototypes

# start emit init for class: Object
Object_init:
	jr	$ra	
# finish emit init -- no need -- for class: Object


# start emit init for class: IO
IO_init:
	jr	$ra	
# finish emit init -- no need -- for class: IO


# start emit init for class: Int
Int_init:
	jr	$ra	
# finish emit init -- no need -- for class: Int


# start emit init for class: Bool
Bool_init:
	jr	$ra	
# finish emit init -- no need -- for class: Bool


# start emit init for class: String
String_init:
	jr	$ra	
# finish emit init -- no need -- for class: String


# start emit init for class: Main
Main_init:
	sw	$fp 0($sp)
	addiu	$sp $sp -4
	move	$fp $sp
	addiu	$fp $fp 4
	sw	$ra 0($sp)
	addiu	$sp $sp -4
	move	$s0 $a0
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	la	$a0 IO_protObj
	jal	Object.copy
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	la	$a0 Object_protObj
	jal	Object.copy
	move	$s0 $a0
	lw	$a0 4($sp)
	addiu	$sp $sp 4
	move	$s0 $a0
	lw	$a0 4($sp)
	addiu	$sp $sp 4
	move	$s0 $a0
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	addiu	$sp $sp 8
	jr	$ra	
# finish emit init for class: Main

# finish all emit init of prototypes


# start emit methods
Main.prompt:

	sw	$fp 0($sp)
	addiu	$sp $sp -4
	move	$fp $sp
	addiu	$fp $fp 4
	sw	$ra 0($sp)
	addiu	$sp $sp -4
# start emmit the Block expr under class: Main
# start emmit the Dispatch expr: out_string under class: Main
# start emmit the string const expr under class: Main
	la	$a0 str_const1
# start emmit the string const expr under class: Main
	sw	$a0 0($sp)
	addiu	$sp $sp -4
# start emmit the object expr under class: Main
	move	$a0 $s0
# finish emmit the object expr under class: Main
	bne	$a0 $zero label4
	la	$a0 str_const0
	li	$t1 1
	jal	_dispatch_abort
label4:
	move	$a0 $s0
	la	$t1 Main_dispTab
	lw	$t1 16($t1)
	jalr	$t1
# finish emmit the Dispatch expr: out_string under class: Main
# start emmit the Dispatch expr: in_string under class: Main
# start emmit the object expr under class: Main
	move	$a0 $s0
# finish emmit the object expr under class: Main
	bne	$a0 $zero label5
	la	$a0 str_const0
	li	$t1 1
	jal	_dispatch_abort
label5:
	move	$a0 $s0
	la	$t1 Main_dispTab
	lw	$t1 24($t1)
	jalr	$t1
# finish emmit the Dispatch expr: in_string under class: Main
# finish emmit the Block expr under class: Main
	move	$sp $fp
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	addiu	$sp $sp 0
	jr	$ra	
Main.main:

	sw	$fp 0($sp)
	addiu	$sp $sp -4
	move	$fp $sp
	addiu	$fp $fp 4
	sw	$ra 0($sp)
	addiu	$sp $sp -4
# start emmit the Loop expr under class: Main
# start emmit the bool const expr under class: Main
	la	$a0 bool_const1
# finish emmit the bool const expr under class: Main
	la	$t1 bool_const1
	bne	$a0 $t1 label7
# start emmit the Let expr under class: Main
# start emmit the Dispatch expr: prompt under class: Main
# start emmit the object expr under class: Main
	move	$a0 $s0
# finish emmit the object expr under class: Main
	bne	$a0 $zero label8
	la	$a0 str_const0
	li	$t1 1
	jal	_dispatch_abort
label8:
	move	$a0 $s0
	la	$t1 Main_dispTab
	lw	$t1 32($t1)
	jalr	$t1
# finish emmit the Dispatch expr: prompt under class: Main
	sw	$a0 0($sp)
	addiu	$sp $sp -4
# start emmit the Cond expr under class: Main
# start emmit the Equal expr under class: Main
# start emmit the object expr under class: Main
	lw	$a0 -8($fp)
# finish emmit the object expr under class: Main
	sw	$a0 0($sp)
	addiu	$sp $sp -4
# start emmit the string const expr under class: Main
	la	$a0 str_const2
# start emmit the string const expr under class: Main
	move	$t2 $a0
	lw	$t1 4($sp)
	addiu	$sp $sp 4
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	jal	equality_test
	lw	$t1 4($sp)
	addiu	$sp $sp 4
	bne	$a0 $t1 label9
	la	$a0 bool_const1
	j	 label10
label9:
	la	$a0 bool_const0
label10:
# finish emmit the Equal expr under class: Main
	la	$t1 bool_const1
	bne	$a0 $t1 label11
# start emmit the Dispatch expr: abort under class: Main
# start emmit the object expr under class: Main
	move	$a0 $s0
# finish emmit the object expr under class: Main
	bne	$a0 $zero label13
	la	$a0 str_const0
	li	$t1 1
	jal	_dispatch_abort
label13:
	move	$a0 $s0
	la	$t1 Main_dispTab
	lw	$t1 4($t1)
	jalr	$t1
# finish emmit the Dispatch expr: abort under class: Main
	j	 label12
label11:
# start emmit the Block expr under class: Main
# start emmit the Dispatch expr: out_string under class: Main
# start emmit the object expr under class: Main
	lw	$a0 -8($fp)
# finish emmit the object expr under class: Main
	sw	$a0 0($sp)
	addiu	$sp $sp -4
# start emmit the object expr under class: Main
	move	$a0 $s0
# finish emmit the object expr under class: Main
	bne	$a0 $zero label14
	la	$a0 str_const0
	li	$t1 1
	jal	_dispatch_abort
label14:
	move	$a0 $s0
	la	$t1 Main_dispTab
	lw	$t1 16($t1)
	jalr	$t1
# finish emmit the Dispatch expr: out_string under class: Main
# finish emmit the Block expr under class: Main
label12:
# finish emmit the Cond expr under class: Main
# finish emmit the Let expr under class: Main
label6:
# start emmit the bool const expr under class: Main
	la	$a0 bool_const1
# finish emmit the bool const expr under class: Main
	la	$t1 bool_const1
	bne	$a0 $t1 label7
# start emmit the Let expr under class: Main
# start emmit the Dispatch expr: prompt under class: Main
# start emmit the object expr under class: Main
	move	$a0 $s0
# finish emmit the object expr under class: Main
	bne	$a0 $zero label15
	la	$a0 str_const0
	li	$t1 1
	jal	_dispatch_abort
label15:
	move	$a0 $s0
	la	$t1 Main_dispTab
	lw	$t1 32($t1)
	jalr	$t1
# finish emmit the Dispatch expr: prompt under class: Main
	sw	$a0 0($sp)
	addiu	$sp $sp -4
# start emmit the Cond expr under class: Main
# start emmit the Equal expr under class: Main
# start emmit the object expr under class: Main
	lw	$a0 -8($fp)
# finish emmit the object expr under class: Main
	sw	$a0 0($sp)
	addiu	$sp $sp -4
# start emmit the string const expr under class: Main
	la	$a0 str_const2
# start emmit the string const expr under class: Main
	move	$t2 $a0
	lw	$t1 4($sp)
	addiu	$sp $sp 4
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	jal	equality_test
	lw	$t1 4($sp)
	addiu	$sp $sp 4
	bne	$a0 $t1 label16
	la	$a0 bool_const1
	j	 label17
label16:
	la	$a0 bool_const0
label17:
# finish emmit the Equal expr under class: Main
	la	$t1 bool_const1
	bne	$a0 $t1 label18
# start emmit the Dispatch expr: abort under class: Main
# start emmit the object expr under class: Main
	move	$a0 $s0
# finish emmit the object expr under class: Main
	bne	$a0 $zero label20
	la	$a0 str_const0
	li	$t1 1
	jal	_dispatch_abort
label20:
	move	$a0 $s0
	la	$t1 Main_dispTab
	lw	$t1 4($t1)
	jalr	$t1
# finish emmit the Dispatch expr: abort under class: Main
	j	 label19
label18:
# start emmit the Block expr under class: Main
# start emmit the Dispatch expr: out_string under class: Main
# start emmit the object expr under class: Main
	lw	$a0 -8($fp)
# finish emmit the object expr under class: Main
	sw	$a0 0($sp)
	addiu	$sp $sp -4
# start emmit the object expr under class: Main
	move	$a0 $s0
# finish emmit the object expr under class: Main
	bne	$a0 $zero label21
	la	$a0 str_const0
	li	$t1 1
	jal	_dispatch_abort
label21:
	move	$a0 $s0
	la	$t1 Main_dispTab
	lw	$t1 16($t1)
	jalr	$t1
# finish emmit the Dispatch expr: out_string under class: Main
# finish emmit the Block expr under class: Main
label19:
# finish emmit the Cond expr under class: Main
# finish emmit the Let expr under class: Main
	j	 label6
label7:
# finish emmit the Loop expr under class: Main
	move	$sp $fp
	lw	$ra -4($fp)
	lw	$fp 0($fp)
	addiu	$sp $sp 0
	jr	$ra	
# finish emit methods


# end of generated code
