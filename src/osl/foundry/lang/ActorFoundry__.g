lexer grammar ActorFoundry;
options {
  language=Java;

}

@members {
  protected boolean enumIsKeyword = true;
  protected boolean assertIsKeyword = true;
}
@header {
  package osl.foundry.lang;
}

T__25 : 'package' ;
T__26 : ';' ;
T__27 : 'import' ;
T__28 : 'static' ;
T__29 : '.' ;
T__30 : '*' ;
T__31 : 'actor' ;
T__32 : '<' ;
T__33 : ',' ;
T__34 : '>' ;
T__35 : 'extends' ;
T__36 : '&' ;
T__37 : '{' ;
T__38 : '}' ;
T__39 : 'void' ;
T__40 : 'message' ;
T__41 : '[' ;
T__42 : ']' ;
T__43 : 'throws' ;
T__44 : '=' ;
T__45 : 'final' ;
T__46 : 'transient' ;
T__47 : 'volatile' ;
T__48 : 'strictfp' ;
T__49 : 'boolean' ;
T__50 : 'char' ;
T__51 : 'byte' ;
T__52 : 'short' ;
T__53 : 'int' ;
T__54 : 'long' ;
T__55 : 'float' ;
T__56 : 'double' ;
T__57 : '?' ;
T__58 : 'super' ;
T__59 : '(' ;
T__60 : ')' ;
T__61 : '...' ;
T__62 : 'this' ;
T__63 : 'null' ;
T__64 : 'true' ;
T__65 : 'false' ;
T__66 : '@' ;
T__67 : 'default' ;
T__68 : ':' ;
T__69 : 'if' ;
T__70 : 'else' ;
T__71 : 'for' ;
T__72 : 'while' ;
T__73 : 'do' ;
T__74 : 'try' ;
T__75 : 'finally' ;
T__76 : 'switch' ;
T__77 : 'return' ;
T__78 : 'throw' ;
T__79 : 'break' ;
T__80 : 'continue' ;
T__81 : 'catch' ;
T__82 : 'case' ;
T__83 : '+=' ;
T__84 : '-=' ;
T__85 : '*=' ;
T__86 : '/=' ;
T__87 : '&=' ;
T__88 : '|=' ;
T__89 : '^=' ;
T__90 : '%=' ;
T__91 : '||' ;
T__92 : '&&' ;
T__93 : '|' ;
T__94 : '^' ;
T__95 : '==' ;
T__96 : '!=' ;
T__97 : 'instanceof' ;
T__98 : '+' ;
T__99 : '-' ;
T__100 : '/' ;
T__101 : '%' ;
T__102 : '++' ;
T__103 : '--' ;
T__104 : '~' ;
T__105 : '!' ;
T__106 : 'class' ;
T__107 : '<-' ;
T__108 : '<-&' ;
T__109 : '<->' ;
T__110 : '<->&' ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 697
HexLiteral : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 699
DecimalLiteral : ('0' | '1'..'9' '0'..'9'*) IntegerTypeSuffix? ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 701
OctalLiteral : '0' ('0'..'7')+ IntegerTypeSuffix? ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 703
fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 706
fragment
IntegerTypeSuffix : ('l'|'L') ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 709
FloatingPointLiteral
    :   ('0'..'9')+ '.' ('0'..'9')* Exponent? FloatTypeSuffix?
    |   '.' ('0'..'9')+ Exponent? FloatTypeSuffix?
    |   ('0'..'9')+ Exponent FloatTypeSuffix?
    |   ('0'..'9')+ FloatTypeSuffix
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 716
fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 719
fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 722
CharacterLiteral
    :   '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 726
StringLiteral
    :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 730
fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 737
fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 744
fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 749
ENUM:   'enum' {if (!enumIsKeyword) $type=Identifier;}
    ;
    
// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 752
ASSERT
    :   'assert' {if (!assertIsKeyword) $type=Identifier;}
    ;
    
// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 756
Identifier 
    :   Letter (Letter|JavaIDDigit)*
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 760
/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
Letter
    :  '\u0024' |
       '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 780
fragment
JavaIDDigit
    :  '\u0030'..'\u0039' |
       '\u0660'..'\u0669' |
       '\u06f0'..'\u06f9' |
       '\u0966'..'\u096f' |
       '\u09e6'..'\u09ef' |
       '\u0a66'..'\u0a6f' |
       '\u0ae6'..'\u0aef' |
       '\u0b66'..'\u0b6f' |
       '\u0be7'..'\u0bef' |
       '\u0c66'..'\u0c6f' |
       '\u0ce6'..'\u0cef' |
       '\u0d66'..'\u0d6f' |
       '\u0e50'..'\u0e59' |
       '\u0ed0'..'\u0ed9' |
       '\u1040'..'\u1049'
   ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 799
WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 802
COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

// $ANTLR src "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g" 806
LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;
