// $ANTLR 3.1.1 /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g 2009-01-01 17:49:57

  package osl.foundry.lang;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;
import java.util.HashMap;
public class ActorFoundryParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Identifier", "FloatingPointLiteral", "CharacterLiteral", "StringLiteral", "HexLiteral", "OctalLiteral", "DecimalLiteral", "ASSERT", "HexDigit", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "EscapeSequence", "UnicodeEscape", "OctalEscape", "ENUM", "Letter", "JavaIDDigit", "WS", "COMMENT", "LINE_COMMENT", "'package'", "';'", "'import'", "'static'", "'.'", "'*'", "'actor'", "'<'", "','", "'>'", "'extends'", "'&'", "'{'", "'}'", "'void'", "'message'", "'['", "']'", "'throws'", "'='", "'final'", "'transient'", "'volatile'", "'strictfp'", "'boolean'", "'char'", "'byte'", "'short'", "'int'", "'long'", "'float'", "'double'", "'?'", "'super'", "'('", "')'", "'...'", "'this'", "'null'", "'true'", "'false'", "'@'", "'default'", "':'", "'if'", "'else'", "'for'", "'while'", "'do'", "'try'", "'finally'", "'switch'", "'return'", "'throw'", "'break'", "'continue'", "'catch'", "'case'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'||'", "'&&'", "'|'", "'^'", "'=='", "'!='", "'instanceof'", "'+'", "'-'", "'/'", "'%'", "'++'", "'--'", "'~'", "'!'", "'class'", "'<-'", "'<-&'", "'<->'", "'<->&'"
    };
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int FloatTypeSuffix=15;
    public static final int T__25=25;
    public static final int OctalLiteral=9;
    public static final int EOF=-1;
    public static final int Identifier=4;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int COMMENT=23;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=24;
    public static final int IntegerTypeSuffix=13;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int ASSERT=11;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int WS=22;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int FloatingPointLiteral=5;
    public static final int JavaIDDigit=21;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int Letter=20;
    public static final int EscapeSequence=16;
    public static final int T__73=73;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int CharacterLiteral=6;
    public static final int Exponent=14;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int HexDigit=12;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__110=110;
    public static final int T__50=50;
    public static final int T__42=42;
    public static final int HexLiteral=8;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int DecimalLiteral=10;
    public static final int StringLiteral=7;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int ENUM=19;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int UnicodeEscape=17;
    public static final int OctalEscape=18;

    // delegates
    // delegators


        public ActorFoundryParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ActorFoundryParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[318+1];
             
             
        }
        
    protected StringTemplateGroup templateLib =
      new StringTemplateGroup("ActorFoundryParserTemplates", AngleBracketTemplateLexer.class);

    public void setTemplateLib(StringTemplateGroup templateLib) {
      this.templateLib = templateLib;
    }
    public StringTemplateGroup getTemplateLib() {
      return templateLib;
    }
    /** allows convenient multi-value initialization:
     *  "new STAttrMap().put(...).put(...)"
     */
    public static class STAttrMap extends HashMap {
      public STAttrMap put(String attrName, Object value) {
        super.put(attrName, value);
        return this;
      }
      public STAttrMap put(String attrName, int value) {
        super.put(attrName, new Integer(value));
        return this;
      }
    }

    public String[] getTokenNames() { return ActorFoundryParser.tokenNames; }
    public String getGrammarFileName() { return "/home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g"; }


      String pkgQName;
      String getPackageQName() {
        return pkgQName == null ? "":pkgQName;
      }


    public static class compilationUnit_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "compilationUnit"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:61:1: compilationUnit : ( annotations ( packageDeclaration ( importDeclaration )* actorOrViewDeclaration | actorOrViewDeclaration ) | ( packageDeclaration )? ( importDeclaration )* actorOrViewDeclaration );
    public final ActorFoundryParser.compilationUnit_return compilationUnit() throws RecognitionException {
        ActorFoundryParser.compilationUnit_return retval = new ActorFoundryParser.compilationUnit_return();
        retval.start = input.LT(1);
        int compilationUnit_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:62:5: ( annotations ( packageDeclaration ( importDeclaration )* actorOrViewDeclaration | actorOrViewDeclaration ) | ( packageDeclaration )? ( importDeclaration )* actorOrViewDeclaration )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==66) ) {
                alt5=1;
            }
            else if ( (LA5_0==25||LA5_0==27||LA5_0==31) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:62:9: annotations ( packageDeclaration ( importDeclaration )* actorOrViewDeclaration | actorOrViewDeclaration )
                    {
                    pushFollow(FOLLOW_annotations_in_compilationUnit81);
                    annotations();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:63:9: ( packageDeclaration ( importDeclaration )* actorOrViewDeclaration | actorOrViewDeclaration )
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==25) ) {
                        alt2=1;
                    }
                    else if ( (LA2_0==31) ) {
                        alt2=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 0, input);

                        throw nvae;
                    }
                    switch (alt2) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:63:13: packageDeclaration ( importDeclaration )* actorOrViewDeclaration
                            {
                            pushFollow(FOLLOW_packageDeclaration_in_compilationUnit95);
                            packageDeclaration();

                            state._fsp--;
                            if (state.failed) return retval;
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:63:32: ( importDeclaration )*
                            loop1:
                            do {
                                int alt1=2;
                                int LA1_0 = input.LA(1);

                                if ( (LA1_0==27) ) {
                                    alt1=1;
                                }


                                switch (alt1) {
                            	case 1 :
                            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: importDeclaration
                            	    {
                            	    pushFollow(FOLLOW_importDeclaration_in_compilationUnit97);
                            	    importDeclaration();

                            	    state._fsp--;
                            	    if (state.failed) return retval;

                            	    }
                            	    break;

                            	default :
                            	    break loop1;
                                }
                            } while (true);

                            pushFollow(FOLLOW_actorOrViewDeclaration_in_compilationUnit100);
                            actorOrViewDeclaration();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;
                        case 2 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:64:13: actorOrViewDeclaration
                            {
                            pushFollow(FOLLOW_actorOrViewDeclaration_in_compilationUnit114);
                            actorOrViewDeclaration();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:66:9: ( packageDeclaration )? ( importDeclaration )* actorOrViewDeclaration
                    {
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:66:9: ( packageDeclaration )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==25) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: packageDeclaration
                            {
                            pushFollow(FOLLOW_packageDeclaration_in_compilationUnit134);
                            packageDeclaration();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:66:29: ( importDeclaration )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==27) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: importDeclaration
                    	    {
                    	    pushFollow(FOLLOW_importDeclaration_in_compilationUnit137);
                    	    importDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    pushFollow(FOLLOW_actorOrViewDeclaration_in_compilationUnit140);
                    actorOrViewDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, compilationUnit_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "compilationUnit"

    public static class packageDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "packageDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:69:1: packageDeclaration : 'package' qualifiedName ';' ;
    public final ActorFoundryParser.packageDeclaration_return packageDeclaration() throws RecognitionException {
        ActorFoundryParser.packageDeclaration_return retval = new ActorFoundryParser.packageDeclaration_return();
        retval.start = input.LT(1);
        int packageDeclaration_StartIndex = input.index();
        ActorFoundryParser.qualifiedName_return qualifiedName1 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:70:5: ( 'package' qualifiedName ';' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:70:9: 'package' qualifiedName ';'
            {
            match(input,25,FOLLOW_25_in_packageDeclaration159); if (state.failed) return retval;
            pushFollow(FOLLOW_qualifiedName_in_packageDeclaration161);
            qualifiedName1=qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              pkgQName=(qualifiedName1!=null?input.toString(qualifiedName1.start,qualifiedName1.stop):null);
            }
            match(input,26,FOLLOW_26_in_packageDeclaration165); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, packageDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "packageDeclaration"

    public static class importDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "importDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:74:1: importDeclaration : 'import' ( 'static' )? qualifiedName ( '.' '*' )? ';' ;
    public final ActorFoundryParser.importDeclaration_return importDeclaration() throws RecognitionException {
        ActorFoundryParser.importDeclaration_return retval = new ActorFoundryParser.importDeclaration_return();
        retval.start = input.LT(1);
        int importDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:75:5: ( 'import' ( 'static' )? qualifiedName ( '.' '*' )? ';' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:75:9: 'import' ( 'static' )? qualifiedName ( '.' '*' )? ';'
            {
            match(input,27,FOLLOW_27_in_importDeclaration189); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:75:18: ( 'static' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==28) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: 'static'
                    {
                    match(input,28,FOLLOW_28_in_importDeclaration191); if (state.failed) return retval;

                    }
                    break;

            }

            pushFollow(FOLLOW_qualifiedName_in_importDeclaration194);
            qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:75:42: ( '.' '*' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==29) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:75:43: '.' '*'
                    {
                    match(input,29,FOLLOW_29_in_importDeclaration197); if (state.failed) return retval;
                    match(input,30,FOLLOW_30_in_importDeclaration199); if (state.failed) return retval;

                    }
                    break;

            }

            match(input,26,FOLLOW_26_in_importDeclaration203); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, importDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "importDeclaration"

    public static class actorOrViewDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "actorOrViewDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:78:1: actorOrViewDeclaration : ( actorDeclaration ) ;
    public final ActorFoundryParser.actorOrViewDeclaration_return actorOrViewDeclaration() throws RecognitionException {
        ActorFoundryParser.actorOrViewDeclaration_return retval = new ActorFoundryParser.actorOrViewDeclaration_return();
        retval.start = input.LT(1);
        int actorOrViewDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:79:5: ( ( actorDeclaration ) )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:79:9: ( actorDeclaration )
            {
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:79:9: ( actorDeclaration )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:79:10: actorDeclaration
            {
            pushFollow(FOLLOW_actorDeclaration_in_actorOrViewDeclaration223);
            actorDeclaration();

            state._fsp--;
            if (state.failed) return retval;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, actorOrViewDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "actorOrViewDeclaration"

    protected static class actorDeclaration_scope {
        String actorName;
    }
    protected Stack actorDeclaration_stack = new Stack();

    public static class actorDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "actorDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:82:1: actorDeclaration : 'actor' actName= Identifier actorBody -> template(act_name=$actorDeclaration::actorNameact_body=$actorBody.text) \"public class <act_name> extends osl.manager.Actor <act_body>\";
    public final ActorFoundryParser.actorDeclaration_return actorDeclaration() throws RecognitionException {
        actorDeclaration_stack.push(new actorDeclaration_scope());
        ActorFoundryParser.actorDeclaration_return retval = new ActorFoundryParser.actorDeclaration_return();
        retval.start = input.LT(1);
        int actorDeclaration_StartIndex = input.index();
        Token actName=null;
        ActorFoundryParser.actorBody_return actorBody2 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:86:5: ( 'actor' actName= Identifier actorBody -> template(act_name=$actorDeclaration::actorNameact_body=$actorBody.text) \"public class <act_name> extends osl.manager.Actor <act_body>\")
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:86:9: 'actor' actName= Identifier actorBody
            {
            match(input,31,FOLLOW_31_in_actorDeclaration247); if (state.failed) return retval;
            actName=(Token)match(input,Identifier,FOLLOW_Identifier_in_actorDeclaration251); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              ((actorDeclaration_scope)actorDeclaration_stack.peek()).actorName =(actName!=null?actName.getText():null);
            }
            pushFollow(FOLLOW_actorBody_in_actorDeclaration255);
            actorBody2=actorBody();

            state._fsp--;
            if (state.failed) return retval;


            // TEMPLATE REWRITE
            if ( state.backtracking==0 ) {
              // 86:95: -> template(act_name=$actorDeclaration::actorNameact_body=$actorBody.text) \"public class <act_name> extends osl.manager.Actor <act_body>\"
              {
                  retval.st = new StringTemplate(templateLib, "public class <act_name> extends osl.manager.Actor <act_body>",
                new STAttrMap().put("act_name", ((actorDeclaration_scope)actorDeclaration_stack.peek()).actorName).put("act_body", (actorBody2!=null?input.toString(actorBody2.start,actorBody2.stop):null)));
              }

              ((TokenRewriteStream)input).replace(
                ((Token)retval.start).getTokenIndex(),
                input.LT(-1).getTokenIndex(),
                retval.st);
            }
            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, actorDeclaration_StartIndex); }
            actorDeclaration_stack.pop();
        }
        return retval;
    }
    // $ANTLR end "actorDeclaration"

    public static class viewDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "viewDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:92:1: viewDeclaration : ;
    public final ActorFoundryParser.viewDeclaration_return viewDeclaration() throws RecognitionException {
        ActorFoundryParser.viewDeclaration_return retval = new ActorFoundryParser.viewDeclaration_return();
        retval.start = input.LT(1);
        int viewDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:93:5: ()
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:94:5: 
            {
            }

            retval.stop = input.LT(-1);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, viewDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "viewDeclaration"

    public static class modifiers_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "modifiers"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:96:1: modifiers : ( modifier )* ;
    public final ActorFoundryParser.modifiers_return modifiers() throws RecognitionException {
        ActorFoundryParser.modifiers_return retval = new ActorFoundryParser.modifiers_return();
        retval.start = input.LT(1);
        int modifiers_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:97:5: ( ( modifier )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:97:9: ( modifier )*
            {
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:97:9: ( modifier )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>=45 && LA8_0<=48)||LA8_0==66) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: modifier
            	    {
            	    pushFollow(FOLLOW_modifier_in_modifiers328);
            	    modifier();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, modifiers_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "modifiers"

    public static class typeParameters_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "typeParameters"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:100:1: typeParameters : '<' typeParameter ( ',' typeParameter )* '>' ;
    public final ActorFoundryParser.typeParameters_return typeParameters() throws RecognitionException {
        ActorFoundryParser.typeParameters_return retval = new ActorFoundryParser.typeParameters_return();
        retval.start = input.LT(1);
        int typeParameters_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:101:5: ( '<' typeParameter ( ',' typeParameter )* '>' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:101:9: '<' typeParameter ( ',' typeParameter )* '>'
            {
            match(input,32,FOLLOW_32_in_typeParameters356); if (state.failed) return retval;
            pushFollow(FOLLOW_typeParameter_in_typeParameters358);
            typeParameter();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:101:27: ( ',' typeParameter )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==33) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:101:28: ',' typeParameter
            	    {
            	    match(input,33,FOLLOW_33_in_typeParameters361); if (state.failed) return retval;
            	    pushFollow(FOLLOW_typeParameter_in_typeParameters363);
            	    typeParameter();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match(input,34,FOLLOW_34_in_typeParameters367); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, typeParameters_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "typeParameters"

    public static class typeParameter_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "typeParameter"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:104:1: typeParameter : Identifier ( 'extends' typeBound )? ;
    public final ActorFoundryParser.typeParameter_return typeParameter() throws RecognitionException {
        ActorFoundryParser.typeParameter_return retval = new ActorFoundryParser.typeParameter_return();
        retval.start = input.LT(1);
        int typeParameter_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:105:5: ( Identifier ( 'extends' typeBound )? )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:105:9: Identifier ( 'extends' typeBound )?
            {
            match(input,Identifier,FOLLOW_Identifier_in_typeParameter386); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:105:20: ( 'extends' typeBound )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==35) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:105:21: 'extends' typeBound
                    {
                    match(input,35,FOLLOW_35_in_typeParameter389); if (state.failed) return retval;
                    pushFollow(FOLLOW_typeBound_in_typeParameter391);
                    typeBound();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, typeParameter_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "typeParameter"

    public static class typeBound_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "typeBound"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:108:1: typeBound : type ( '&' type )* ;
    public final ActorFoundryParser.typeBound_return typeBound() throws RecognitionException {
        ActorFoundryParser.typeBound_return retval = new ActorFoundryParser.typeBound_return();
        retval.start = input.LT(1);
        int typeBound_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:109:5: ( type ( '&' type )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:109:9: type ( '&' type )*
            {
            pushFollow(FOLLOW_type_in_typeBound420);
            type();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:109:14: ( '&' type )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==36) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:109:15: '&' type
            	    {
            	    match(input,36,FOLLOW_36_in_typeBound423); if (state.failed) return retval;
            	    pushFollow(FOLLOW_type_in_typeBound425);
            	    type();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, typeBound_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "typeBound"

    public static class typeList_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "typeList"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:112:1: typeList : type ( ',' type )* ;
    public final ActorFoundryParser.typeList_return typeList() throws RecognitionException {
        ActorFoundryParser.typeList_return retval = new ActorFoundryParser.typeList_return();
        retval.start = input.LT(1);
        int typeList_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:113:5: ( type ( ',' type )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:113:9: type ( ',' type )*
            {
            pushFollow(FOLLOW_type_in_typeList458);
            type();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:113:14: ( ',' type )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==33) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:113:15: ',' type
            	    {
            	    match(input,33,FOLLOW_33_in_typeList461); if (state.failed) return retval;
            	    pushFollow(FOLLOW_type_in_typeList463);
            	    type();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, typeList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "typeList"

    public static class actorBody_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "actorBody"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:116:1: actorBody : '{' ( actorBodyDeclaration )* '}' ;
    public final ActorFoundryParser.actorBody_return actorBody() throws RecognitionException {
        ActorFoundryParser.actorBody_return retval = new ActorFoundryParser.actorBody_return();
        retval.start = input.LT(1);
        int actorBody_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:117:5: ( '{' ( actorBodyDeclaration )* '}' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:117:9: '{' ( actorBodyDeclaration )* '}'
            {
            match(input,37,FOLLOW_37_in_actorBody488); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:117:13: ( actorBodyDeclaration )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==Identifier||LA13_0==26||LA13_0==32||(LA13_0>=39 && LA13_0<=40)||(LA13_0>=45 && LA13_0<=56)||LA13_0==66) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: actorBodyDeclaration
            	    {
            	    pushFollow(FOLLOW_actorBodyDeclaration_in_actorBody490);
            	    actorBodyDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match(input,38,FOLLOW_38_in_actorBody493); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, actorBody_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "actorBody"

    public static class actorBodyDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "actorBodyDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:120:1: actorBodyDeclaration : ( ';' | modifiers memberDecl );
    public final ActorFoundryParser.actorBodyDeclaration_return actorBodyDeclaration() throws RecognitionException {
        ActorFoundryParser.actorBodyDeclaration_return retval = new ActorFoundryParser.actorBodyDeclaration_return();
        retval.start = input.LT(1);
        int actorBodyDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:121:5: ( ';' | modifiers memberDecl )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==26) ) {
                alt14=1;
            }
            else if ( (LA14_0==Identifier||LA14_0==32||(LA14_0>=39 && LA14_0<=40)||(LA14_0>=45 && LA14_0<=56)||LA14_0==66) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:121:9: ';'
                    {
                    match(input,26,FOLLOW_26_in_actorBodyDeclaration512); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:122:9: modifiers memberDecl
                    {
                    pushFollow(FOLLOW_modifiers_in_actorBodyDeclaration522);
                    modifiers();

                    state._fsp--;
                    if (state.failed) return retval;
                    pushFollow(FOLLOW_memberDecl_in_actorBodyDeclaration524);
                    memberDecl();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, actorBodyDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "actorBodyDeclaration"

    public static class memberDecl_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "memberDecl"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:125:1: memberDecl : ( genericMethodOrConstructorDecl | memberDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | genericMessageDeclaration | messageDeclaration );
    public final ActorFoundryParser.memberDecl_return memberDecl() throws RecognitionException {
        ActorFoundryParser.memberDecl_return retval = new ActorFoundryParser.memberDecl_return();
        retval.start = input.LT(1);
        int memberDecl_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:126:5: ( genericMethodOrConstructorDecl | memberDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | genericMessageDeclaration | messageDeclaration )
            int alt15=6;
            switch ( input.LA(1) ) {
            case 32:
                {
                alt15=1;
                }
                break;
            case Identifier:
                {
                int LA15_2 = input.LA(2);

                if ( (LA15_2==Identifier||LA15_2==29||LA15_2==32||LA15_2==41) ) {
                    alt15=2;
                }
                else if ( (LA15_2==59) ) {
                    alt15=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 2, input);

                    throw nvae;
                }
                }
                break;
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
                {
                alt15=2;
                }
                break;
            case 39:
                {
                alt15=3;
                }
                break;
            case 40:
                {
                int LA15_5 = input.LA(2);

                if ( (LA15_5==Identifier||LA15_5==39||(LA15_5>=49 && LA15_5<=56)) ) {
                    alt15=6;
                }
                else if ( (LA15_5==32) ) {
                    alt15=5;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:126:9: genericMethodOrConstructorDecl
                    {
                    pushFollow(FOLLOW_genericMethodOrConstructorDecl_in_memberDecl547);
                    genericMethodOrConstructorDecl();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:127:9: memberDeclaration
                    {
                    pushFollow(FOLLOW_memberDeclaration_in_memberDecl557);
                    memberDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:128:9: 'void' Identifier voidMethodDeclaratorRest
                    {
                    match(input,39,FOLLOW_39_in_memberDecl567); if (state.failed) return retval;
                    match(input,Identifier,FOLLOW_Identifier_in_memberDecl569); if (state.failed) return retval;
                    pushFollow(FOLLOW_voidMethodDeclaratorRest_in_memberDecl571);
                    voidMethodDeclaratorRest();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:129:9: Identifier constructorDeclaratorRest
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_memberDecl581); if (state.failed) return retval;
                    pushFollow(FOLLOW_constructorDeclaratorRest_in_memberDecl583);
                    constructorDeclaratorRest();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:130:9: genericMessageDeclaration
                    {
                    pushFollow(FOLLOW_genericMessageDeclaration_in_memberDecl593);
                    genericMessageDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 6 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:131:9: messageDeclaration
                    {
                    pushFollow(FOLLOW_messageDeclaration_in_memberDecl603);
                    messageDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, memberDecl_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "memberDecl"

    public static class messageDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "messageDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:134:1: messageDeclaration : ( 'message' type methodDeclaration -> template(t=$type.textmd=$methodDeclaration.text) \"public <t> <md>\" | 'message' 'void' msgName= Identifier voidMethodDeclaratorRest -> template(msg_name=$msgName.textmd=$voidMethodDeclaratorRest.text) \"public void <msg_name> <md>\");
    public final ActorFoundryParser.messageDeclaration_return messageDeclaration() throws RecognitionException {
        ActorFoundryParser.messageDeclaration_return retval = new ActorFoundryParser.messageDeclaration_return();
        retval.start = input.LT(1);
        int messageDeclaration_StartIndex = input.index();
        Token msgName=null;
        ActorFoundryParser.type_return type3 = null;

        ActorFoundryParser.methodDeclaration_return methodDeclaration4 = null;

        ActorFoundryParser.voidMethodDeclaratorRest_return voidMethodDeclaratorRest5 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:135:2: ( 'message' type methodDeclaration -> template(t=$type.textmd=$methodDeclaration.text) \"public <t> <md>\" | 'message' 'void' msgName= Identifier voidMethodDeclaratorRest -> template(msg_name=$msgName.textmd=$voidMethodDeclaratorRest.text) \"public void <msg_name> <md>\")
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==40) ) {
                int LA16_1 = input.LA(2);

                if ( (LA16_1==39) ) {
                    alt16=2;
                }
                else if ( (LA16_1==Identifier||(LA16_1>=49 && LA16_1<=56)) ) {
                    alt16=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:135:4: 'message' type methodDeclaration
                    {
                    match(input,40,FOLLOW_40_in_messageDeclaration622); if (state.failed) return retval;
                    pushFollow(FOLLOW_type_in_messageDeclaration624);
                    type3=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    pushFollow(FOLLOW_methodDeclaration_in_messageDeclaration626);
                    methodDeclaration4=methodDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;


                    // TEMPLATE REWRITE
                    if ( state.backtracking==0 ) {
                      // 135:37: -> template(t=$type.textmd=$methodDeclaration.text) \"public <t> <md>\"
                      {
                          retval.st = new StringTemplate(templateLib, "public <t> <md>",
                        new STAttrMap().put("t", (type3!=null?input.toString(type3.start,type3.stop):null)).put("md", (methodDeclaration4!=null?input.toString(methodDeclaration4.start,methodDeclaration4.stop):null)));
                      }

                      ((TokenRewriteStream)input).replace(
                        ((Token)retval.start).getTokenIndex(),
                        input.LT(-1).getTokenIndex(),
                        retval.st);
                    }
                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:136:4: 'message' 'void' msgName= Identifier voidMethodDeclaratorRest
                    {
                    match(input,40,FOLLOW_40_in_messageDeclaration646); if (state.failed) return retval;
                    match(input,39,FOLLOW_39_in_messageDeclaration648); if (state.failed) return retval;
                    msgName=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageDeclaration652); if (state.failed) return retval;
                    pushFollow(FOLLOW_voidMethodDeclaratorRest_in_messageDeclaration654);
                    voidMethodDeclaratorRest5=voidMethodDeclaratorRest();

                    state._fsp--;
                    if (state.failed) return retval;


                    // TEMPLATE REWRITE
                    if ( state.backtracking==0 ) {
                      // 137:2: -> template(msg_name=$msgName.textmd=$voidMethodDeclaratorRest.text) \"public void <msg_name> <md>\"
                      {
                          retval.st = new StringTemplate(templateLib, "public void <msg_name> <md>",
                        new STAttrMap().put("msg_name", (msgName!=null?msgName.getText():null)).put("md", (voidMethodDeclaratorRest5!=null?input.toString(voidMethodDeclaratorRest5.start,voidMethodDeclaratorRest5.stop):null)));
                      }

                      ((TokenRewriteStream)input).replace(
                        ((Token)retval.start).getTokenIndex(),
                        input.LT(-1).getTokenIndex(),
                        retval.st);
                    }
                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, messageDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "messageDeclaration"

    public static class genericMessageDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "genericMessageDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:140:1: genericMessageDeclaration : 'message' typeParameters type methodDeclaration -> template(tp=$typeParameters.textt=$type.textmd=$methodDeclaration.text) \"public <tp> <t> <md>\";
    public final ActorFoundryParser.genericMessageDeclaration_return genericMessageDeclaration() throws RecognitionException {
        ActorFoundryParser.genericMessageDeclaration_return retval = new ActorFoundryParser.genericMessageDeclaration_return();
        retval.start = input.LT(1);
        int genericMessageDeclaration_StartIndex = input.index();
        ActorFoundryParser.typeParameters_return typeParameters6 = null;

        ActorFoundryParser.type_return type7 = null;

        ActorFoundryParser.methodDeclaration_return methodDeclaration8 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:141:2: ( 'message' typeParameters type methodDeclaration -> template(tp=$typeParameters.textt=$type.textmd=$methodDeclaration.text) \"public <tp> <t> <md>\")
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:141:4: 'message' typeParameters type methodDeclaration
            {
            match(input,40,FOLLOW_40_in_genericMessageDeclaration682); if (state.failed) return retval;
            pushFollow(FOLLOW_typeParameters_in_genericMessageDeclaration684);
            typeParameters6=typeParameters();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_type_in_genericMessageDeclaration686);
            type7=type();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_methodDeclaration_in_genericMessageDeclaration688);
            methodDeclaration8=methodDeclaration();

            state._fsp--;
            if (state.failed) return retval;


            // TEMPLATE REWRITE
            if ( state.backtracking==0 ) {
              // 142:2: -> template(tp=$typeParameters.textt=$type.textmd=$methodDeclaration.text) \"public <tp> <t> <md>\"
              {
                  retval.st = new StringTemplate(templateLib, "public <tp> <t> <md>",
                new STAttrMap().put("tp", (typeParameters6!=null?input.toString(typeParameters6.start,typeParameters6.stop):null)).put("t", (type7!=null?input.toString(type7.start,type7.stop):null)).put("md", (methodDeclaration8!=null?input.toString(methodDeclaration8.start,methodDeclaration8.stop):null)));
              }

              ((TokenRewriteStream)input).replace(
                ((Token)retval.start).getTokenIndex(),
                input.LT(-1).getTokenIndex(),
                retval.st);
            }
            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, genericMessageDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "genericMessageDeclaration"

    public static class memberDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "memberDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:145:1: memberDeclaration : type ( methodDeclaration | fieldDeclaration ) ;
    public final ActorFoundryParser.memberDeclaration_return memberDeclaration() throws RecognitionException {
        ActorFoundryParser.memberDeclaration_return retval = new ActorFoundryParser.memberDeclaration_return();
        retval.start = input.LT(1);
        int memberDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:146:5: ( type ( methodDeclaration | fieldDeclaration ) )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:146:9: type ( methodDeclaration | fieldDeclaration )
            {
            pushFollow(FOLLOW_type_in_memberDeclaration724);
            type();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:146:14: ( methodDeclaration | fieldDeclaration )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==Identifier) ) {
                int LA17_1 = input.LA(2);

                if ( (LA17_1==26||LA17_1==33||LA17_1==41||LA17_1==44) ) {
                    alt17=2;
                }
                else if ( (LA17_1==59) ) {
                    alt17=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:146:15: methodDeclaration
                    {
                    pushFollow(FOLLOW_methodDeclaration_in_memberDeclaration727);
                    methodDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:146:35: fieldDeclaration
                    {
                    pushFollow(FOLLOW_fieldDeclaration_in_memberDeclaration731);
                    fieldDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, memberDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "memberDeclaration"

    public static class genericMethodOrConstructorDecl_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "genericMethodOrConstructorDecl"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:149:1: genericMethodOrConstructorDecl : typeParameters genericMethodOrConstructorRest ;
    public final ActorFoundryParser.genericMethodOrConstructorDecl_return genericMethodOrConstructorDecl() throws RecognitionException {
        ActorFoundryParser.genericMethodOrConstructorDecl_return retval = new ActorFoundryParser.genericMethodOrConstructorDecl_return();
        retval.start = input.LT(1);
        int genericMethodOrConstructorDecl_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:150:5: ( typeParameters genericMethodOrConstructorRest )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:150:9: typeParameters genericMethodOrConstructorRest
            {
            pushFollow(FOLLOW_typeParameters_in_genericMethodOrConstructorDecl751);
            typeParameters();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_genericMethodOrConstructorRest_in_genericMethodOrConstructorDecl753);
            genericMethodOrConstructorRest();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, genericMethodOrConstructorDecl_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "genericMethodOrConstructorDecl"

    public static class genericMethodOrConstructorRest_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "genericMethodOrConstructorRest"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:153:1: genericMethodOrConstructorRest : ( ( type | 'void' ) Identifier methodDeclaratorRest | Identifier constructorDeclaratorRest );
    public final ActorFoundryParser.genericMethodOrConstructorRest_return genericMethodOrConstructorRest() throws RecognitionException {
        ActorFoundryParser.genericMethodOrConstructorRest_return retval = new ActorFoundryParser.genericMethodOrConstructorRest_return();
        retval.start = input.LT(1);
        int genericMethodOrConstructorRest_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:154:5: ( ( type | 'void' ) Identifier methodDeclaratorRest | Identifier constructorDeclaratorRest )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==Identifier) ) {
                int LA19_1 = input.LA(2);

                if ( (LA19_1==Identifier||LA19_1==29||LA19_1==32||LA19_1==41) ) {
                    alt19=1;
                }
                else if ( (LA19_1==59) ) {
                    alt19=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA19_0==39||(LA19_0>=49 && LA19_0<=56)) ) {
                alt19=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:154:9: ( type | 'void' ) Identifier methodDeclaratorRest
                    {
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:154:9: ( type | 'void' )
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==Identifier||(LA18_0>=49 && LA18_0<=56)) ) {
                        alt18=1;
                    }
                    else if ( (LA18_0==39) ) {
                        alt18=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 0, input);

                        throw nvae;
                    }
                    switch (alt18) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:154:10: type
                            {
                            pushFollow(FOLLOW_type_in_genericMethodOrConstructorRest777);
                            type();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;
                        case 2 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:154:17: 'void'
                            {
                            match(input,39,FOLLOW_39_in_genericMethodOrConstructorRest781); if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,Identifier,FOLLOW_Identifier_in_genericMethodOrConstructorRest784); if (state.failed) return retval;
                    pushFollow(FOLLOW_methodDeclaratorRest_in_genericMethodOrConstructorRest786);
                    methodDeclaratorRest();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:155:9: Identifier constructorDeclaratorRest
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_genericMethodOrConstructorRest796); if (state.failed) return retval;
                    pushFollow(FOLLOW_constructorDeclaratorRest_in_genericMethodOrConstructorRest798);
                    constructorDeclaratorRest();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, genericMethodOrConstructorRest_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "genericMethodOrConstructorRest"

    public static class methodDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "methodDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:158:1: methodDeclaration : Identifier methodDeclaratorRest ;
    public final ActorFoundryParser.methodDeclaration_return methodDeclaration() throws RecognitionException {
        ActorFoundryParser.methodDeclaration_return retval = new ActorFoundryParser.methodDeclaration_return();
        retval.start = input.LT(1);
        int methodDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:159:5: ( Identifier methodDeclaratorRest )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:159:9: Identifier methodDeclaratorRest
            {
            match(input,Identifier,FOLLOW_Identifier_in_methodDeclaration817); if (state.failed) return retval;
            pushFollow(FOLLOW_methodDeclaratorRest_in_methodDeclaration819);
            methodDeclaratorRest();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, methodDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "methodDeclaration"

    public static class fieldDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "fieldDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:162:1: fieldDeclaration : variableDeclarators ';' ;
    public final ActorFoundryParser.fieldDeclaration_return fieldDeclaration() throws RecognitionException {
        ActorFoundryParser.fieldDeclaration_return retval = new ActorFoundryParser.fieldDeclaration_return();
        retval.start = input.LT(1);
        int fieldDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:163:5: ( variableDeclarators ';' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:163:9: variableDeclarators ';'
            {
            pushFollow(FOLLOW_variableDeclarators_in_fieldDeclaration838);
            variableDeclarators();

            state._fsp--;
            if (state.failed) return retval;
            match(input,26,FOLLOW_26_in_fieldDeclaration840); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 21, fieldDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "fieldDeclaration"

    public static class methodDeclaratorRest_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "methodDeclaratorRest"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:166:1: methodDeclaratorRest : formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ( methodBody | ';' ) ;
    public final ActorFoundryParser.methodDeclaratorRest_return methodDeclaratorRest() throws RecognitionException {
        ActorFoundryParser.methodDeclaratorRest_return retval = new ActorFoundryParser.methodDeclaratorRest_return();
        retval.start = input.LT(1);
        int methodDeclaratorRest_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:167:5: ( formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ( methodBody | ';' ) )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:167:9: formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ( methodBody | ';' )
            {
            pushFollow(FOLLOW_formalParameters_in_methodDeclaratorRest867);
            formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:167:26: ( '[' ']' )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==41) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:167:27: '[' ']'
            	    {
            	    match(input,41,FOLLOW_41_in_methodDeclaratorRest870); if (state.failed) return retval;
            	    match(input,42,FOLLOW_42_in_methodDeclaratorRest872); if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:168:9: ( 'throws' qualifiedNameList )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==43) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:168:10: 'throws' qualifiedNameList
                    {
                    match(input,43,FOLLOW_43_in_methodDeclaratorRest885); if (state.failed) return retval;
                    pushFollow(FOLLOW_qualifiedNameList_in_methodDeclaratorRest887);
                    qualifiedNameList();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }

            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:169:9: ( methodBody | ';' )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==37) ) {
                alt22=1;
            }
            else if ( (LA22_0==26) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:169:13: methodBody
                    {
                    pushFollow(FOLLOW_methodBody_in_methodDeclaratorRest903);
                    methodBody();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:170:13: ';'
                    {
                    match(input,26,FOLLOW_26_in_methodDeclaratorRest917); if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 22, methodDeclaratorRest_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "methodDeclaratorRest"

    public static class voidMethodDeclaratorRest_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "voidMethodDeclaratorRest"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:174:1: voidMethodDeclaratorRest : formalParameters ( 'throws' qualifiedNameList )? ( methodBody | ';' ) ;
    public final ActorFoundryParser.voidMethodDeclaratorRest_return voidMethodDeclaratorRest() throws RecognitionException {
        ActorFoundryParser.voidMethodDeclaratorRest_return retval = new ActorFoundryParser.voidMethodDeclaratorRest_return();
        retval.start = input.LT(1);
        int voidMethodDeclaratorRest_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:175:5: ( formalParameters ( 'throws' qualifiedNameList )? ( methodBody | ';' ) )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:175:9: formalParameters ( 'throws' qualifiedNameList )? ( methodBody | ';' )
            {
            pushFollow(FOLLOW_formalParameters_in_voidMethodDeclaratorRest950);
            formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:175:26: ( 'throws' qualifiedNameList )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==43) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:175:27: 'throws' qualifiedNameList
                    {
                    match(input,43,FOLLOW_43_in_voidMethodDeclaratorRest953); if (state.failed) return retval;
                    pushFollow(FOLLOW_qualifiedNameList_in_voidMethodDeclaratorRest955);
                    qualifiedNameList();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }

            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:176:9: ( methodBody | ';' )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==37) ) {
                alt24=1;
            }
            else if ( (LA24_0==26) ) {
                alt24=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:176:13: methodBody
                    {
                    pushFollow(FOLLOW_methodBody_in_voidMethodDeclaratorRest971);
                    methodBody();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:177:13: ';'
                    {
                    match(input,26,FOLLOW_26_in_voidMethodDeclaratorRest985); if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 23, voidMethodDeclaratorRest_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "voidMethodDeclaratorRest"

    public static class constructorDeclaratorRest_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "constructorDeclaratorRest"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:181:1: constructorDeclaratorRest : formalParameters ( 'throws' qualifiedNameList )? constructorBody ;
    public final ActorFoundryParser.constructorDeclaratorRest_return constructorDeclaratorRest() throws RecognitionException {
        ActorFoundryParser.constructorDeclaratorRest_return retval = new ActorFoundryParser.constructorDeclaratorRest_return();
        retval.start = input.LT(1);
        int constructorDeclaratorRest_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:182:5: ( formalParameters ( 'throws' qualifiedNameList )? constructorBody )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:182:9: formalParameters ( 'throws' qualifiedNameList )? constructorBody
            {
            pushFollow(FOLLOW_formalParameters_in_constructorDeclaratorRest1022);
            formalParameters();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:182:26: ( 'throws' qualifiedNameList )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==43) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:182:27: 'throws' qualifiedNameList
                    {
                    match(input,43,FOLLOW_43_in_constructorDeclaratorRest1025); if (state.failed) return retval;
                    pushFollow(FOLLOW_qualifiedNameList_in_constructorDeclaratorRest1027);
                    qualifiedNameList();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }

            pushFollow(FOLLOW_constructorBody_in_constructorDeclaratorRest1031);
            constructorBody();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 24, constructorDeclaratorRest_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constructorDeclaratorRest"

    public static class constantDeclarator_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "constantDeclarator"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:185:1: constantDeclarator : Identifier constantDeclaratorRest ;
    public final ActorFoundryParser.constantDeclarator_return constantDeclarator() throws RecognitionException {
        ActorFoundryParser.constantDeclarator_return retval = new ActorFoundryParser.constantDeclarator_return();
        retval.start = input.LT(1);
        int constantDeclarator_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:186:5: ( Identifier constantDeclaratorRest )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:186:9: Identifier constantDeclaratorRest
            {
            match(input,Identifier,FOLLOW_Identifier_in_constantDeclarator1050); if (state.failed) return retval;
            pushFollow(FOLLOW_constantDeclaratorRest_in_constantDeclarator1052);
            constantDeclaratorRest();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 25, constantDeclarator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constantDeclarator"

    public static class variableDeclarators_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "variableDeclarators"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:189:1: variableDeclarators : variableDeclarator ( ',' variableDeclarator )* ;
    public final ActorFoundryParser.variableDeclarators_return variableDeclarators() throws RecognitionException {
        ActorFoundryParser.variableDeclarators_return retval = new ActorFoundryParser.variableDeclarators_return();
        retval.start = input.LT(1);
        int variableDeclarators_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:190:5: ( variableDeclarator ( ',' variableDeclarator )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:190:9: variableDeclarator ( ',' variableDeclarator )*
            {
            pushFollow(FOLLOW_variableDeclarator_in_variableDeclarators1075);
            variableDeclarator();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:190:28: ( ',' variableDeclarator )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==33) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:190:29: ',' variableDeclarator
            	    {
            	    match(input,33,FOLLOW_33_in_variableDeclarators1078); if (state.failed) return retval;
            	    pushFollow(FOLLOW_variableDeclarator_in_variableDeclarators1080);
            	    variableDeclarator();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 26, variableDeclarators_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "variableDeclarators"

    public static class variableDeclarator_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "variableDeclarator"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:193:1: variableDeclarator : variableDeclaratorId ( '=' variableInitializer )? ;
    public final ActorFoundryParser.variableDeclarator_return variableDeclarator() throws RecognitionException {
        ActorFoundryParser.variableDeclarator_return retval = new ActorFoundryParser.variableDeclarator_return();
        retval.start = input.LT(1);
        int variableDeclarator_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:194:5: ( variableDeclaratorId ( '=' variableInitializer )? )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:194:9: variableDeclaratorId ( '=' variableInitializer )?
            {
            pushFollow(FOLLOW_variableDeclaratorId_in_variableDeclarator1101);
            variableDeclaratorId();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:194:30: ( '=' variableInitializer )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==44) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:194:31: '=' variableInitializer
                    {
                    match(input,44,FOLLOW_44_in_variableDeclarator1104); if (state.failed) return retval;
                    pushFollow(FOLLOW_variableInitializer_in_variableDeclarator1106);
                    variableInitializer();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 27, variableDeclarator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "variableDeclarator"

    public static class constantDeclaratorsRest_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "constantDeclaratorsRest"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:197:1: constantDeclaratorsRest : constantDeclaratorRest ( ',' constantDeclarator )* ;
    public final ActorFoundryParser.constantDeclaratorsRest_return constantDeclaratorsRest() throws RecognitionException {
        ActorFoundryParser.constantDeclaratorsRest_return retval = new ActorFoundryParser.constantDeclaratorsRest_return();
        retval.start = input.LT(1);
        int constantDeclaratorsRest_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:198:5: ( constantDeclaratorRest ( ',' constantDeclarator )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:198:9: constantDeclaratorRest ( ',' constantDeclarator )*
            {
            pushFollow(FOLLOW_constantDeclaratorRest_in_constantDeclaratorsRest1131);
            constantDeclaratorRest();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:198:32: ( ',' constantDeclarator )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==33) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:198:33: ',' constantDeclarator
            	    {
            	    match(input,33,FOLLOW_33_in_constantDeclaratorsRest1134); if (state.failed) return retval;
            	    pushFollow(FOLLOW_constantDeclarator_in_constantDeclaratorsRest1136);
            	    constantDeclarator();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 28, constantDeclaratorsRest_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constantDeclaratorsRest"

    public static class constantDeclaratorRest_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "constantDeclaratorRest"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:201:1: constantDeclaratorRest : ( '[' ']' )* '=' variableInitializer ;
    public final ActorFoundryParser.constantDeclaratorRest_return constantDeclaratorRest() throws RecognitionException {
        ActorFoundryParser.constantDeclaratorRest_return retval = new ActorFoundryParser.constantDeclaratorRest_return();
        retval.start = input.LT(1);
        int constantDeclaratorRest_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:202:5: ( ( '[' ']' )* '=' variableInitializer )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:202:9: ( '[' ']' )* '=' variableInitializer
            {
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:202:9: ( '[' ']' )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==41) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:202:10: '[' ']'
            	    {
            	    match(input,41,FOLLOW_41_in_constantDeclaratorRest1158); if (state.failed) return retval;
            	    match(input,42,FOLLOW_42_in_constantDeclaratorRest1160); if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

            match(input,44,FOLLOW_44_in_constantDeclaratorRest1164); if (state.failed) return retval;
            pushFollow(FOLLOW_variableInitializer_in_constantDeclaratorRest1166);
            variableInitializer();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 29, constantDeclaratorRest_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constantDeclaratorRest"

    public static class variableDeclaratorId_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "variableDeclaratorId"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:205:1: variableDeclaratorId : Identifier ( '[' ']' )* ;
    public final ActorFoundryParser.variableDeclaratorId_return variableDeclaratorId() throws RecognitionException {
        ActorFoundryParser.variableDeclaratorId_return retval = new ActorFoundryParser.variableDeclaratorId_return();
        retval.start = input.LT(1);
        int variableDeclaratorId_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:206:5: ( Identifier ( '[' ']' )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:206:9: Identifier ( '[' ']' )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_variableDeclaratorId1189); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:206:20: ( '[' ']' )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==41) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:206:21: '[' ']'
            	    {
            	    match(input,41,FOLLOW_41_in_variableDeclaratorId1192); if (state.failed) return retval;
            	    match(input,42,FOLLOW_42_in_variableDeclaratorId1194); if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 30, variableDeclaratorId_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "variableDeclaratorId"

    public static class variableInitializer_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "variableInitializer"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:209:1: variableInitializer : ( arrayInitializer | expression );
    public final ActorFoundryParser.variableInitializer_return variableInitializer() throws RecognitionException {
        ActorFoundryParser.variableInitializer_return retval = new ActorFoundryParser.variableInitializer_return();
        retval.start = input.LT(1);
        int variableInitializer_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:210:5: ( arrayInitializer | expression )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==37) ) {
                alt31=1;
            }
            else if ( ((LA31_0>=Identifier && LA31_0<=DecimalLiteral)||LA31_0==39||(LA31_0>=49 && LA31_0<=56)||(LA31_0>=58 && LA31_0<=59)||(LA31_0>=62 && LA31_0<=65)||(LA31_0>=98 && LA31_0<=99)||(LA31_0>=102 && LA31_0<=105)) ) {
                alt31=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:210:9: arrayInitializer
                    {
                    pushFollow(FOLLOW_arrayInitializer_in_variableInitializer1215);
                    arrayInitializer();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:211:9: expression
                    {
                    pushFollow(FOLLOW_expression_in_variableInitializer1225);
                    expression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 31, variableInitializer_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "variableInitializer"

    public static class arrayInitializer_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "arrayInitializer"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:214:1: arrayInitializer : '{' ( variableInitializer ( ',' variableInitializer )* ( ',' )? )? '}' ;
    public final ActorFoundryParser.arrayInitializer_return arrayInitializer() throws RecognitionException {
        ActorFoundryParser.arrayInitializer_return retval = new ActorFoundryParser.arrayInitializer_return();
        retval.start = input.LT(1);
        int arrayInitializer_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:215:5: ( '{' ( variableInitializer ( ',' variableInitializer )* ( ',' )? )? '}' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:215:9: '{' ( variableInitializer ( ',' variableInitializer )* ( ',' )? )? '}'
            {
            match(input,37,FOLLOW_37_in_arrayInitializer1252); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:215:13: ( variableInitializer ( ',' variableInitializer )* ( ',' )? )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( ((LA34_0>=Identifier && LA34_0<=DecimalLiteral)||LA34_0==37||LA34_0==39||(LA34_0>=49 && LA34_0<=56)||(LA34_0>=58 && LA34_0<=59)||(LA34_0>=62 && LA34_0<=65)||(LA34_0>=98 && LA34_0<=99)||(LA34_0>=102 && LA34_0<=105)) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:215:14: variableInitializer ( ',' variableInitializer )* ( ',' )?
                    {
                    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1255);
                    variableInitializer();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:215:34: ( ',' variableInitializer )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( (LA32_0==33) ) {
                            int LA32_1 = input.LA(2);

                            if ( ((LA32_1>=Identifier && LA32_1<=DecimalLiteral)||LA32_1==37||LA32_1==39||(LA32_1>=49 && LA32_1<=56)||(LA32_1>=58 && LA32_1<=59)||(LA32_1>=62 && LA32_1<=65)||(LA32_1>=98 && LA32_1<=99)||(LA32_1>=102 && LA32_1<=105)) ) {
                                alt32=1;
                            }


                        }


                        switch (alt32) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:215:35: ',' variableInitializer
                    	    {
                    	    match(input,33,FOLLOW_33_in_arrayInitializer1258); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1260);
                    	    variableInitializer();

                    	    state._fsp--;
                    	    if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop32;
                        }
                    } while (true);

                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:215:61: ( ',' )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==33) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:215:62: ','
                            {
                            match(input,33,FOLLOW_33_in_arrayInitializer1265); if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;

            }

            match(input,38,FOLLOW_38_in_arrayInitializer1272); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 32, arrayInitializer_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "arrayInitializer"

    public static class modifier_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "modifier"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:218:1: modifier : ( annotation | 'final' | 'transient' | 'volatile' | 'strictfp' );
    public final ActorFoundryParser.modifier_return modifier() throws RecognitionException {
        ActorFoundryParser.modifier_return retval = new ActorFoundryParser.modifier_return();
        retval.start = input.LT(1);
        int modifier_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:219:5: ( annotation | 'final' | 'transient' | 'volatile' | 'strictfp' )
            int alt35=5;
            switch ( input.LA(1) ) {
            case 66:
                {
                alt35=1;
                }
                break;
            case 45:
                {
                alt35=2;
                }
                break;
            case 46:
                {
                alt35=3;
                }
                break;
            case 47:
                {
                alt35=4;
                }
                break;
            case 48:
                {
                alt35=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }

            switch (alt35) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:219:9: annotation
                    {
                    pushFollow(FOLLOW_annotation_in_modifier1291);
                    annotation();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:220:9: 'final'
                    {
                    match(input,45,FOLLOW_45_in_modifier1301); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:221:9: 'transient'
                    {
                    match(input,46,FOLLOW_46_in_modifier1311); if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:222:9: 'volatile'
                    {
                    match(input,47,FOLLOW_47_in_modifier1321); if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:223:9: 'strictfp'
                    {
                    match(input,48,FOLLOW_48_in_modifier1331); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 33, modifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "modifier"

    public static class packageOrTypeName_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "packageOrTypeName"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:226:1: packageOrTypeName : qualifiedName ;
    public final ActorFoundryParser.packageOrTypeName_return packageOrTypeName() throws RecognitionException {
        ActorFoundryParser.packageOrTypeName_return retval = new ActorFoundryParser.packageOrTypeName_return();
        retval.start = input.LT(1);
        int packageOrTypeName_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:227:5: ( qualifiedName )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:227:9: qualifiedName
            {
            pushFollow(FOLLOW_qualifiedName_in_packageOrTypeName1350);
            qualifiedName();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 34, packageOrTypeName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "packageOrTypeName"

    public static class enumConstantName_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "enumConstantName"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:230:1: enumConstantName : Identifier ;
    public final ActorFoundryParser.enumConstantName_return enumConstantName() throws RecognitionException {
        ActorFoundryParser.enumConstantName_return retval = new ActorFoundryParser.enumConstantName_return();
        retval.start = input.LT(1);
        int enumConstantName_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:231:5: ( Identifier )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:231:9: Identifier
            {
            match(input,Identifier,FOLLOW_Identifier_in_enumConstantName1369); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 35, enumConstantName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "enumConstantName"

    public static class typeName_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "typeName"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:234:1: typeName : qualifiedName ;
    public final ActorFoundryParser.typeName_return typeName() throws RecognitionException {
        ActorFoundryParser.typeName_return retval = new ActorFoundryParser.typeName_return();
        retval.start = input.LT(1);
        int typeName_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:235:5: ( qualifiedName )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:235:9: qualifiedName
            {
            pushFollow(FOLLOW_qualifiedName_in_typeName1388);
            qualifiedName();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 36, typeName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "typeName"

    public static class type_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "type"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:238:1: type : ( classOrInterfaceType ( '[' ']' )* | primitiveType ( '[' ']' )* );
    public final ActorFoundryParser.type_return type() throws RecognitionException {
        ActorFoundryParser.type_return retval = new ActorFoundryParser.type_return();
        retval.start = input.LT(1);
        int type_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:239:2: ( classOrInterfaceType ( '[' ']' )* | primitiveType ( '[' ']' )* )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==Identifier) ) {
                alt38=1;
            }
            else if ( ((LA38_0>=49 && LA38_0<=56)) ) {
                alt38=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:239:4: classOrInterfaceType ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_classOrInterfaceType_in_type1402);
                    classOrInterfaceType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:239:25: ( '[' ']' )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==41) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:239:26: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_type1405); if (state.failed) return retval;
                    	    match(input,42,FOLLOW_42_in_type1407); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:240:4: primitiveType ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_primitiveType_in_type1414);
                    primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:240:18: ( '[' ']' )*
                    loop37:
                    do {
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( (LA37_0==41) ) {
                            alt37=1;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:240:19: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_type1417); if (state.failed) return retval;
                    	    match(input,42,FOLLOW_42_in_type1419); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 37, type_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "type"

    public static class classOrInterfaceType_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "classOrInterfaceType"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:243:1: classOrInterfaceType : Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* ;
    public final ActorFoundryParser.classOrInterfaceType_return classOrInterfaceType() throws RecognitionException {
        ActorFoundryParser.classOrInterfaceType_return retval = new ActorFoundryParser.classOrInterfaceType_return();
        retval.start = input.LT(1);
        int classOrInterfaceType_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:244:2: ( Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:244:4: Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_classOrInterfaceType1432); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:244:15: ( typeArguments )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==32) ) {
                int LA39_1 = input.LA(2);

                if ( (LA39_1==Identifier||(LA39_1>=49 && LA39_1<=57)) ) {
                    alt39=1;
                }
            }
            switch (alt39) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: typeArguments
                    {
                    pushFollow(FOLLOW_typeArguments_in_classOrInterfaceType1434);
                    typeArguments();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }

            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:244:30: ( '.' Identifier ( typeArguments )? )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==29) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:244:31: '.' Identifier ( typeArguments )?
            	    {
            	    match(input,29,FOLLOW_29_in_classOrInterfaceType1438); if (state.failed) return retval;
            	    match(input,Identifier,FOLLOW_Identifier_in_classOrInterfaceType1440); if (state.failed) return retval;
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:244:46: ( typeArguments )?
            	    int alt40=2;
            	    int LA40_0 = input.LA(1);

            	    if ( (LA40_0==32) ) {
            	        int LA40_1 = input.LA(2);

            	        if ( (LA40_1==Identifier||(LA40_1>=49 && LA40_1<=57)) ) {
            	            alt40=1;
            	        }
            	    }
            	    switch (alt40) {
            	        case 1 :
            	            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: typeArguments
            	            {
            	            pushFollow(FOLLOW_typeArguments_in_classOrInterfaceType1442);
            	            typeArguments();

            	            state._fsp--;
            	            if (state.failed) return retval;

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 38, classOrInterfaceType_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "classOrInterfaceType"

    public static class primitiveType_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "primitiveType"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:247:1: primitiveType : ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' );
    public final ActorFoundryParser.primitiveType_return primitiveType() throws RecognitionException {
        ActorFoundryParser.primitiveType_return retval = new ActorFoundryParser.primitiveType_return();
        retval.start = input.LT(1);
        int primitiveType_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:248:5: ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:
            {
            if ( (input.LA(1)>=49 && input.LA(1)<=56) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 39, primitiveType_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "primitiveType"

    public static class variableModifier_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "variableModifier"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:258:1: variableModifier : ( 'final' | annotation );
    public final ActorFoundryParser.variableModifier_return variableModifier() throws RecognitionException {
        ActorFoundryParser.variableModifier_return retval = new ActorFoundryParser.variableModifier_return();
        retval.start = input.LT(1);
        int variableModifier_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:259:5: ( 'final' | annotation )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==45) ) {
                alt42=1;
            }
            else if ( (LA42_0==66) ) {
                alt42=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:259:9: 'final'
                    {
                    match(input,45,FOLLOW_45_in_variableModifier1551); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:260:9: annotation
                    {
                    pushFollow(FOLLOW_annotation_in_variableModifier1561);
                    annotation();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 40, variableModifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "variableModifier"

    public static class typeArguments_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "typeArguments"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:263:1: typeArguments : '<' typeArgument ( ',' typeArgument )* '>' ;
    public final ActorFoundryParser.typeArguments_return typeArguments() throws RecognitionException {
        ActorFoundryParser.typeArguments_return retval = new ActorFoundryParser.typeArguments_return();
        retval.start = input.LT(1);
        int typeArguments_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 41) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:264:5: ( '<' typeArgument ( ',' typeArgument )* '>' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:264:9: '<' typeArgument ( ',' typeArgument )* '>'
            {
            match(input,32,FOLLOW_32_in_typeArguments1580); if (state.failed) return retval;
            pushFollow(FOLLOW_typeArgument_in_typeArguments1582);
            typeArgument();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:264:26: ( ',' typeArgument )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==33) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:264:27: ',' typeArgument
            	    {
            	    match(input,33,FOLLOW_33_in_typeArguments1585); if (state.failed) return retval;
            	    pushFollow(FOLLOW_typeArgument_in_typeArguments1587);
            	    typeArgument();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            match(input,34,FOLLOW_34_in_typeArguments1591); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 41, typeArguments_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "typeArguments"

    public static class typeArgument_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "typeArgument"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:267:1: typeArgument : ( type | '?' ( ( 'extends' | 'super' ) type )? );
    public final ActorFoundryParser.typeArgument_return typeArgument() throws RecognitionException {
        ActorFoundryParser.typeArgument_return retval = new ActorFoundryParser.typeArgument_return();
        retval.start = input.LT(1);
        int typeArgument_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 42) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:268:5: ( type | '?' ( ( 'extends' | 'super' ) type )? )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==Identifier||(LA45_0>=49 && LA45_0<=56)) ) {
                alt45=1;
            }
            else if ( (LA45_0==57) ) {
                alt45=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:268:9: type
                    {
                    pushFollow(FOLLOW_type_in_typeArgument1614);
                    type();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:269:9: '?' ( ( 'extends' | 'super' ) type )?
                    {
                    match(input,57,FOLLOW_57_in_typeArgument1624); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:269:13: ( ( 'extends' | 'super' ) type )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==35||LA44_0==58) ) {
                        alt44=1;
                    }
                    switch (alt44) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:269:14: ( 'extends' | 'super' ) type
                            {
                            if ( input.LA(1)==35||input.LA(1)==58 ) {
                                input.consume();
                                state.errorRecovery=false;state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            pushFollow(FOLLOW_type_in_typeArgument1635);
                            type();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 42, typeArgument_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "typeArgument"

    public static class qualifiedNameList_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "qualifiedNameList"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:272:1: qualifiedNameList : qualifiedName ( ',' qualifiedName )* ;
    public final ActorFoundryParser.qualifiedNameList_return qualifiedNameList() throws RecognitionException {
        ActorFoundryParser.qualifiedNameList_return retval = new ActorFoundryParser.qualifiedNameList_return();
        retval.start = input.LT(1);
        int qualifiedNameList_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 43) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:273:5: ( qualifiedName ( ',' qualifiedName )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:273:9: qualifiedName ( ',' qualifiedName )*
            {
            pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList1660);
            qualifiedName();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:273:23: ( ',' qualifiedName )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==33) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:273:24: ',' qualifiedName
            	    {
            	    match(input,33,FOLLOW_33_in_qualifiedNameList1663); if (state.failed) return retval;
            	    pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList1665);
            	    qualifiedName();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 43, qualifiedNameList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "qualifiedNameList"

    public static class formalParameters_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "formalParameters"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:276:1: formalParameters : '(' ( formalParameterDecls )? ')' ;
    public final ActorFoundryParser.formalParameters_return formalParameters() throws RecognitionException {
        ActorFoundryParser.formalParameters_return retval = new ActorFoundryParser.formalParameters_return();
        retval.start = input.LT(1);
        int formalParameters_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 44) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:277:5: ( '(' ( formalParameterDecls )? ')' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:277:9: '(' ( formalParameterDecls )? ')'
            {
            match(input,59,FOLLOW_59_in_formalParameters1686); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:277:13: ( formalParameterDecls )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==Identifier||LA47_0==45||(LA47_0>=49 && LA47_0<=56)||LA47_0==66) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: formalParameterDecls
                    {
                    pushFollow(FOLLOW_formalParameterDecls_in_formalParameters1688);
                    formalParameterDecls();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }

            match(input,60,FOLLOW_60_in_formalParameters1691); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 44, formalParameters_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "formalParameters"

    public static class formalParameterDecls_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "formalParameterDecls"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:280:1: formalParameterDecls : variableModifiers type formalParameterDeclsRest ;
    public final ActorFoundryParser.formalParameterDecls_return formalParameterDecls() throws RecognitionException {
        ActorFoundryParser.formalParameterDecls_return retval = new ActorFoundryParser.formalParameterDecls_return();
        retval.start = input.LT(1);
        int formalParameterDecls_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 45) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:281:5: ( variableModifiers type formalParameterDeclsRest )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:281:9: variableModifiers type formalParameterDeclsRest
            {
            pushFollow(FOLLOW_variableModifiers_in_formalParameterDecls1714);
            variableModifiers();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_type_in_formalParameterDecls1716);
            type();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_formalParameterDeclsRest_in_formalParameterDecls1718);
            formalParameterDeclsRest();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 45, formalParameterDecls_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "formalParameterDecls"

    public static class formalParameterDeclsRest_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "formalParameterDeclsRest"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:284:1: formalParameterDeclsRest : ( variableDeclaratorId ( ',' formalParameterDecls )? | '...' variableDeclaratorId );
    public final ActorFoundryParser.formalParameterDeclsRest_return formalParameterDeclsRest() throws RecognitionException {
        ActorFoundryParser.formalParameterDeclsRest_return retval = new ActorFoundryParser.formalParameterDeclsRest_return();
        retval.start = input.LT(1);
        int formalParameterDeclsRest_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 46) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:285:5: ( variableDeclaratorId ( ',' formalParameterDecls )? | '...' variableDeclaratorId )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==Identifier) ) {
                alt49=1;
            }
            else if ( (LA49_0==61) ) {
                alt49=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:285:9: variableDeclaratorId ( ',' formalParameterDecls )?
                    {
                    pushFollow(FOLLOW_variableDeclaratorId_in_formalParameterDeclsRest1741);
                    variableDeclaratorId();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:285:30: ( ',' formalParameterDecls )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==33) ) {
                        alt48=1;
                    }
                    switch (alt48) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:285:31: ',' formalParameterDecls
                            {
                            match(input,33,FOLLOW_33_in_formalParameterDeclsRest1744); if (state.failed) return retval;
                            pushFollow(FOLLOW_formalParameterDecls_in_formalParameterDeclsRest1746);
                            formalParameterDecls();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:286:9: '...' variableDeclaratorId
                    {
                    match(input,61,FOLLOW_61_in_formalParameterDeclsRest1758); if (state.failed) return retval;
                    pushFollow(FOLLOW_variableDeclaratorId_in_formalParameterDeclsRest1760);
                    variableDeclaratorId();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 46, formalParameterDeclsRest_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "formalParameterDeclsRest"

    public static class methodBody_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "methodBody"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:289:1: methodBody : block ;
    public final ActorFoundryParser.methodBody_return methodBody() throws RecognitionException {
        ActorFoundryParser.methodBody_return retval = new ActorFoundryParser.methodBody_return();
        retval.start = input.LT(1);
        int methodBody_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 47) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:290:5: ( block )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:290:9: block
            {
            pushFollow(FOLLOW_block_in_methodBody1783);
            block();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 47, methodBody_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "methodBody"

    public static class constructorBody_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "constructorBody"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:293:1: constructorBody : '{' ( explicitConstructorInvocation )? ( blockStatement )* '}' ;
    public final ActorFoundryParser.constructorBody_return constructorBody() throws RecognitionException {
        ActorFoundryParser.constructorBody_return retval = new ActorFoundryParser.constructorBody_return();
        retval.start = input.LT(1);
        int constructorBody_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 48) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:294:5: ( '{' ( explicitConstructorInvocation )? ( blockStatement )* '}' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:294:9: '{' ( explicitConstructorInvocation )? ( blockStatement )* '}'
            {
            match(input,37,FOLLOW_37_in_constructorBody1802); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:294:13: ( explicitConstructorInvocation )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==32) ) {
                alt50=1;
            }
            else if ( ((LA50_0>=Identifier && LA50_0<=DecimalLiteral)||LA50_0==39||(LA50_0>=49 && LA50_0<=56)||(LA50_0>=58 && LA50_0<=59)||(LA50_0>=62 && LA50_0<=65)) ) {
                int LA50_3 = input.LA(2);

                if ( (synpred65_ActorFoundry()) ) {
                    alt50=1;
                }
            }
            switch (alt50) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: explicitConstructorInvocation
                    {
                    pushFollow(FOLLOW_explicitConstructorInvocation_in_constructorBody1804);
                    explicitConstructorInvocation();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }

            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:294:44: ( blockStatement )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( ((LA51_0>=Identifier && LA51_0<=ASSERT)||LA51_0==26||LA51_0==37||LA51_0==39||LA51_0==45||(LA51_0>=49 && LA51_0<=56)||(LA51_0>=58 && LA51_0<=59)||(LA51_0>=62 && LA51_0<=66)||LA51_0==69||(LA51_0>=71 && LA51_0<=74)||(LA51_0>=76 && LA51_0<=80)||(LA51_0>=98 && LA51_0<=99)||(LA51_0>=102 && LA51_0<=105)) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: blockStatement
            	    {
            	    pushFollow(FOLLOW_blockStatement_in_constructorBody1807);
            	    blockStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

            match(input,38,FOLLOW_38_in_constructorBody1810); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 48, constructorBody_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constructorBody"

    public static class explicitConstructorInvocation_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "explicitConstructorInvocation"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:297:1: explicitConstructorInvocation : ( ( nonWildcardTypeArguments )? ( 'this' | 'super' ) arguments ';' | primary '.' ( nonWildcardTypeArguments )? 'super' arguments ';' );
    public final ActorFoundryParser.explicitConstructorInvocation_return explicitConstructorInvocation() throws RecognitionException {
        ActorFoundryParser.explicitConstructorInvocation_return retval = new ActorFoundryParser.explicitConstructorInvocation_return();
        retval.start = input.LT(1);
        int explicitConstructorInvocation_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 49) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:298:5: ( ( nonWildcardTypeArguments )? ( 'this' | 'super' ) arguments ';' | primary '.' ( nonWildcardTypeArguments )? 'super' arguments ';' )
            int alt54=2;
            switch ( input.LA(1) ) {
            case Identifier:
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 39:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 59:
            case 63:
            case 64:
            case 65:
                {
                alt54=2;
                }
                break;
            case 58:
            case 62:
                {
                int LA54_2 = input.LA(2);

                if ( (synpred69_ActorFoundry()) ) {
                    alt54=1;
                }
                else if ( (true) ) {
                    alt54=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 2, input);

                    throw nvae;
                }
                }
                break;
            case 32:
                {
                alt54=1;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }

            switch (alt54) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:298:9: ( nonWildcardTypeArguments )? ( 'this' | 'super' ) arguments ';'
                    {
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:298:9: ( nonWildcardTypeArguments )?
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==32) ) {
                        alt52=1;
                    }
                    switch (alt52) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: nonWildcardTypeArguments
                            {
                            pushFollow(FOLLOW_nonWildcardTypeArguments_in_explicitConstructorInvocation1829);
                            nonWildcardTypeArguments();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    if ( input.LA(1)==58||input.LA(1)==62 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_arguments_in_explicitConstructorInvocation1840);
                    arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,26,FOLLOW_26_in_explicitConstructorInvocation1842); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:299:9: primary '.' ( nonWildcardTypeArguments )? 'super' arguments ';'
                    {
                    pushFollow(FOLLOW_primary_in_explicitConstructorInvocation1852);
                    primary();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,29,FOLLOW_29_in_explicitConstructorInvocation1854); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:299:21: ( nonWildcardTypeArguments )?
                    int alt53=2;
                    int LA53_0 = input.LA(1);

                    if ( (LA53_0==32) ) {
                        alt53=1;
                    }
                    switch (alt53) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: nonWildcardTypeArguments
                            {
                            pushFollow(FOLLOW_nonWildcardTypeArguments_in_explicitConstructorInvocation1856);
                            nonWildcardTypeArguments();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,58,FOLLOW_58_in_explicitConstructorInvocation1859); if (state.failed) return retval;
                    pushFollow(FOLLOW_arguments_in_explicitConstructorInvocation1861);
                    arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,26,FOLLOW_26_in_explicitConstructorInvocation1863); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 49, explicitConstructorInvocation_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "explicitConstructorInvocation"

    public static class qualifiedName_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "qualifiedName"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:303:1: qualifiedName : Identifier ( '.' Identifier )* ;
    public final ActorFoundryParser.qualifiedName_return qualifiedName() throws RecognitionException {
        ActorFoundryParser.qualifiedName_return retval = new ActorFoundryParser.qualifiedName_return();
        retval.start = input.LT(1);
        int qualifiedName_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:304:5: ( Identifier ( '.' Identifier )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:304:9: Identifier ( '.' Identifier )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_qualifiedName1883); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:304:20: ( '.' Identifier )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==29) ) {
                    int LA55_2 = input.LA(2);

                    if ( (LA55_2==Identifier) ) {
                        alt55=1;
                    }


                }


                switch (alt55) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:304:21: '.' Identifier
            	    {
            	    match(input,29,FOLLOW_29_in_qualifiedName1886); if (state.failed) return retval;
            	    match(input,Identifier,FOLLOW_Identifier_in_qualifiedName1888); if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 50, qualifiedName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "qualifiedName"

    public static class literal_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "literal"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:307:1: literal : ( integerLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | booleanLiteral | 'null' );
    public final ActorFoundryParser.literal_return literal() throws RecognitionException {
        ActorFoundryParser.literal_return retval = new ActorFoundryParser.literal_return();
        retval.start = input.LT(1);
        int literal_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 51) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:308:5: ( integerLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | booleanLiteral | 'null' )
            int alt56=6;
            switch ( input.LA(1) ) {
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
                {
                alt56=1;
                }
                break;
            case FloatingPointLiteral:
                {
                alt56=2;
                }
                break;
            case CharacterLiteral:
                {
                alt56=3;
                }
                break;
            case StringLiteral:
                {
                alt56=4;
                }
                break;
            case 64:
            case 65:
                {
                alt56=5;
                }
                break;
            case 63:
                {
                alt56=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }

            switch (alt56) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:308:9: integerLiteral
                    {
                    pushFollow(FOLLOW_integerLiteral_in_literal1914);
                    integerLiteral();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:309:9: FloatingPointLiteral
                    {
                    match(input,FloatingPointLiteral,FOLLOW_FloatingPointLiteral_in_literal1924); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:310:9: CharacterLiteral
                    {
                    match(input,CharacterLiteral,FOLLOW_CharacterLiteral_in_literal1934); if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:311:9: StringLiteral
                    {
                    match(input,StringLiteral,FOLLOW_StringLiteral_in_literal1944); if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:312:9: booleanLiteral
                    {
                    pushFollow(FOLLOW_booleanLiteral_in_literal1954);
                    booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 6 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:313:9: 'null'
                    {
                    match(input,63,FOLLOW_63_in_literal1964); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 51, literal_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "literal"

    public static class integerLiteral_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "integerLiteral"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:316:1: integerLiteral : ( HexLiteral | OctalLiteral | DecimalLiteral );
    public final ActorFoundryParser.integerLiteral_return integerLiteral() throws RecognitionException {
        ActorFoundryParser.integerLiteral_return retval = new ActorFoundryParser.integerLiteral_return();
        retval.start = input.LT(1);
        int integerLiteral_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:317:5: ( HexLiteral | OctalLiteral | DecimalLiteral )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:
            {
            if ( (input.LA(1)>=HexLiteral && input.LA(1)<=DecimalLiteral) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 52, integerLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "integerLiteral"

    public static class booleanLiteral_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "booleanLiteral"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:322:1: booleanLiteral : ( 'true' | 'false' );
    public final ActorFoundryParser.booleanLiteral_return booleanLiteral() throws RecognitionException {
        ActorFoundryParser.booleanLiteral_return retval = new ActorFoundryParser.booleanLiteral_return();
        retval.start = input.LT(1);
        int booleanLiteral_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 53) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:323:5: ( 'true' | 'false' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:
            {
            if ( (input.LA(1)>=64 && input.LA(1)<=65) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 53, booleanLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "booleanLiteral"

    public static class annotations_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "annotations"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:329:1: annotations : ( annotation )+ ;
    public final ActorFoundryParser.annotations_return annotations() throws RecognitionException {
        ActorFoundryParser.annotations_return retval = new ActorFoundryParser.annotations_return();
        retval.start = input.LT(1);
        int annotations_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:330:5: ( ( annotation )+ )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:330:9: ( annotation )+
            {
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:330:9: ( annotation )+
            int cnt57=0;
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==66) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: annotation
            	    {
            	    pushFollow(FOLLOW_annotation_in_annotations2053);
            	    annotation();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    if ( cnt57 >= 1 ) break loop57;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(57, input);
                        throw eee;
                }
                cnt57++;
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 54, annotations_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "annotations"

    public static class annotation_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "annotation"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:333:1: annotation : '@' annotationName ( '(' ( elementValuePairs | elementValue )? ')' )? ;
    public final ActorFoundryParser.annotation_return annotation() throws RecognitionException {
        ActorFoundryParser.annotation_return retval = new ActorFoundryParser.annotation_return();
        retval.start = input.LT(1);
        int annotation_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 55) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:334:5: ( '@' annotationName ( '(' ( elementValuePairs | elementValue )? ')' )? )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:334:9: '@' annotationName ( '(' ( elementValuePairs | elementValue )? ')' )?
            {
            match(input,66,FOLLOW_66_in_annotation2073); if (state.failed) return retval;
            pushFollow(FOLLOW_annotationName_in_annotation2075);
            annotationName();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:334:28: ( '(' ( elementValuePairs | elementValue )? ')' )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==59) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:334:30: '(' ( elementValuePairs | elementValue )? ')'
                    {
                    match(input,59,FOLLOW_59_in_annotation2079); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:334:34: ( elementValuePairs | elementValue )?
                    int alt58=3;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==Identifier) ) {
                        int LA58_1 = input.LA(2);

                        if ( (LA58_1==44) ) {
                            alt58=1;
                        }
                        else if ( ((LA58_1>=29 && LA58_1<=30)||LA58_1==32||LA58_1==34||LA58_1==36||LA58_1==41||LA58_1==57||(LA58_1>=59 && LA58_1<=60)||(LA58_1>=91 && LA58_1<=103)) ) {
                            alt58=2;
                        }
                    }
                    else if ( ((LA58_0>=FloatingPointLiteral && LA58_0<=DecimalLiteral)||LA58_0==37||LA58_0==39||(LA58_0>=49 && LA58_0<=56)||(LA58_0>=58 && LA58_0<=59)||(LA58_0>=62 && LA58_0<=66)||(LA58_0>=98 && LA58_0<=99)||(LA58_0>=102 && LA58_0<=105)) ) {
                        alt58=2;
                    }
                    switch (alt58) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:334:36: elementValuePairs
                            {
                            pushFollow(FOLLOW_elementValuePairs_in_annotation2083);
                            elementValuePairs();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;
                        case 2 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:334:56: elementValue
                            {
                            pushFollow(FOLLOW_elementValue_in_annotation2087);
                            elementValue();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,60,FOLLOW_60_in_annotation2092); if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 55, annotation_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "annotation"

    public static class annotationName_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "annotationName"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:337:1: annotationName : Identifier ( '.' Identifier )* ;
    public final ActorFoundryParser.annotationName_return annotationName() throws RecognitionException {
        ActorFoundryParser.annotationName_return retval = new ActorFoundryParser.annotationName_return();
        retval.start = input.LT(1);
        int annotationName_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:338:5: ( Identifier ( '.' Identifier )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:338:7: Identifier ( '.' Identifier )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_annotationName2116); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:338:18: ( '.' Identifier )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==29) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:338:19: '.' Identifier
            	    {
            	    match(input,29,FOLLOW_29_in_annotationName2119); if (state.failed) return retval;
            	    match(input,Identifier,FOLLOW_Identifier_in_annotationName2121); if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 56, annotationName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "annotationName"

    public static class elementValuePairs_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "elementValuePairs"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:341:1: elementValuePairs : elementValuePair ( ',' elementValuePair )* ;
    public final ActorFoundryParser.elementValuePairs_return elementValuePairs() throws RecognitionException {
        ActorFoundryParser.elementValuePairs_return retval = new ActorFoundryParser.elementValuePairs_return();
        retval.start = input.LT(1);
        int elementValuePairs_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 57) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:342:5: ( elementValuePair ( ',' elementValuePair )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:342:9: elementValuePair ( ',' elementValuePair )*
            {
            pushFollow(FOLLOW_elementValuePair_in_elementValuePairs2142);
            elementValuePair();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:342:26: ( ',' elementValuePair )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==33) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:342:27: ',' elementValuePair
            	    {
            	    match(input,33,FOLLOW_33_in_elementValuePairs2145); if (state.failed) return retval;
            	    pushFollow(FOLLOW_elementValuePair_in_elementValuePairs2147);
            	    elementValuePair();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 57, elementValuePairs_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "elementValuePairs"

    public static class elementValuePair_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "elementValuePair"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:345:1: elementValuePair : Identifier '=' elementValue ;
    public final ActorFoundryParser.elementValuePair_return elementValuePair() throws RecognitionException {
        ActorFoundryParser.elementValuePair_return retval = new ActorFoundryParser.elementValuePair_return();
        retval.start = input.LT(1);
        int elementValuePair_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 58) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:346:5: ( Identifier '=' elementValue )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:346:9: Identifier '=' elementValue
            {
            match(input,Identifier,FOLLOW_Identifier_in_elementValuePair2168); if (state.failed) return retval;
            match(input,44,FOLLOW_44_in_elementValuePair2170); if (state.failed) return retval;
            pushFollow(FOLLOW_elementValue_in_elementValuePair2172);
            elementValue();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 58, elementValuePair_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "elementValuePair"

    public static class elementValue_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "elementValue"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:349:1: elementValue : ( conditionalExpression | annotation | elementValueArrayInitializer );
    public final ActorFoundryParser.elementValue_return elementValue() throws RecognitionException {
        ActorFoundryParser.elementValue_return retval = new ActorFoundryParser.elementValue_return();
        retval.start = input.LT(1);
        int elementValue_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 59) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:350:5: ( conditionalExpression | annotation | elementValueArrayInitializer )
            int alt62=3;
            switch ( input.LA(1) ) {
            case Identifier:
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 39:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 59:
            case 62:
            case 63:
            case 64:
            case 65:
            case 98:
            case 99:
            case 102:
            case 103:
            case 104:
            case 105:
                {
                alt62=1;
                }
                break;
            case 66:
                {
                alt62=2;
                }
                break;
            case 37:
                {
                alt62=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }

            switch (alt62) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:350:9: conditionalExpression
                    {
                    pushFollow(FOLLOW_conditionalExpression_in_elementValue2195);
                    conditionalExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:351:9: annotation
                    {
                    pushFollow(FOLLOW_annotation_in_elementValue2205);
                    annotation();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:352:9: elementValueArrayInitializer
                    {
                    pushFollow(FOLLOW_elementValueArrayInitializer_in_elementValue2215);
                    elementValueArrayInitializer();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 59, elementValue_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "elementValue"

    public static class elementValueArrayInitializer_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "elementValueArrayInitializer"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:355:1: elementValueArrayInitializer : '{' ( elementValue ( ',' elementValue )* )? ( ',' )? '}' ;
    public final ActorFoundryParser.elementValueArrayInitializer_return elementValueArrayInitializer() throws RecognitionException {
        ActorFoundryParser.elementValueArrayInitializer_return retval = new ActorFoundryParser.elementValueArrayInitializer_return();
        retval.start = input.LT(1);
        int elementValueArrayInitializer_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 60) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:356:5: ( '{' ( elementValue ( ',' elementValue )* )? ( ',' )? '}' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:356:9: '{' ( elementValue ( ',' elementValue )* )? ( ',' )? '}'
            {
            match(input,37,FOLLOW_37_in_elementValueArrayInitializer2238); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:356:13: ( elementValue ( ',' elementValue )* )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( ((LA64_0>=Identifier && LA64_0<=DecimalLiteral)||LA64_0==37||LA64_0==39||(LA64_0>=49 && LA64_0<=56)||(LA64_0>=58 && LA64_0<=59)||(LA64_0>=62 && LA64_0<=66)||(LA64_0>=98 && LA64_0<=99)||(LA64_0>=102 && LA64_0<=105)) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:356:14: elementValue ( ',' elementValue )*
                    {
                    pushFollow(FOLLOW_elementValue_in_elementValueArrayInitializer2241);
                    elementValue();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:356:27: ( ',' elementValue )*
                    loop63:
                    do {
                        int alt63=2;
                        int LA63_0 = input.LA(1);

                        if ( (LA63_0==33) ) {
                            int LA63_1 = input.LA(2);

                            if ( ((LA63_1>=Identifier && LA63_1<=DecimalLiteral)||LA63_1==37||LA63_1==39||(LA63_1>=49 && LA63_1<=56)||(LA63_1>=58 && LA63_1<=59)||(LA63_1>=62 && LA63_1<=66)||(LA63_1>=98 && LA63_1<=99)||(LA63_1>=102 && LA63_1<=105)) ) {
                                alt63=1;
                            }


                        }


                        switch (alt63) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:356:28: ',' elementValue
                    	    {
                    	    match(input,33,FOLLOW_33_in_elementValueArrayInitializer2244); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_elementValue_in_elementValueArrayInitializer2246);
                    	    elementValue();

                    	    state._fsp--;
                    	    if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop63;
                        }
                    } while (true);


                    }
                    break;

            }

            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:356:49: ( ',' )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==33) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:356:50: ','
                    {
                    match(input,33,FOLLOW_33_in_elementValueArrayInitializer2253); if (state.failed) return retval;

                    }
                    break;

            }

            match(input,38,FOLLOW_38_in_elementValueArrayInitializer2257); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 60, elementValueArrayInitializer_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "elementValueArrayInitializer"

    public static class defaultValue_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "defaultValue"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:359:1: defaultValue : 'default' elementValue ;
    public final ActorFoundryParser.defaultValue_return defaultValue() throws RecognitionException {
        ActorFoundryParser.defaultValue_return retval = new ActorFoundryParser.defaultValue_return();
        retval.start = input.LT(1);
        int defaultValue_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 61) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:360:5: ( 'default' elementValue )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:360:9: 'default' elementValue
            {
            match(input,67,FOLLOW_67_in_defaultValue2284); if (state.failed) return retval;
            pushFollow(FOLLOW_elementValue_in_defaultValue2286);
            elementValue();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 61, defaultValue_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "defaultValue"

    public static class block_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "block"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:365:1: block : '{' ( blockStatement )* '}' ;
    public final ActorFoundryParser.block_return block() throws RecognitionException {
        ActorFoundryParser.block_return retval = new ActorFoundryParser.block_return();
        retval.start = input.LT(1);
        int block_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 62) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:366:5: ( '{' ( blockStatement )* '}' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:366:9: '{' ( blockStatement )* '}'
            {
            match(input,37,FOLLOW_37_in_block2307); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:366:13: ( blockStatement )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( ((LA66_0>=Identifier && LA66_0<=ASSERT)||LA66_0==26||LA66_0==37||LA66_0==39||LA66_0==45||(LA66_0>=49 && LA66_0<=56)||(LA66_0>=58 && LA66_0<=59)||(LA66_0>=62 && LA66_0<=66)||LA66_0==69||(LA66_0>=71 && LA66_0<=74)||(LA66_0>=76 && LA66_0<=80)||(LA66_0>=98 && LA66_0<=99)||(LA66_0>=102 && LA66_0<=105)) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: blockStatement
            	    {
            	    pushFollow(FOLLOW_blockStatement_in_block2309);
            	    blockStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);

            match(input,38,FOLLOW_38_in_block2312); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 62, block_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "block"

    public static class blockStatement_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "blockStatement"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:369:1: blockStatement : ( localVariableDeclarationStatement | statement );
    public final ActorFoundryParser.blockStatement_return blockStatement() throws RecognitionException {
        ActorFoundryParser.blockStatement_return retval = new ActorFoundryParser.blockStatement_return();
        retval.start = input.LT(1);
        int blockStatement_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 63) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:370:5: ( localVariableDeclarationStatement | statement )
            int alt67=2;
            switch ( input.LA(1) ) {
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case ASSERT:
            case 26:
            case 37:
            case 39:
            case 58:
            case 59:
            case 62:
            case 63:
            case 64:
            case 65:
            case 69:
            case 71:
            case 72:
            case 73:
            case 74:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 98:
            case 99:
            case 102:
            case 103:
            case 104:
            case 105:
                {
                alt67=2;
                }
                break;
            case 45:
            case 66:
                {
                alt67=1;
                }
                break;
            case Identifier:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
                {
                int LA67_3 = input.LA(2);

                if ( (synpred92_ActorFoundry()) ) {
                    alt67=1;
                }
                else if ( (true) ) {
                    alt67=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 67, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }

            switch (alt67) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:370:9: localVariableDeclarationStatement
                    {
                    pushFollow(FOLLOW_localVariableDeclarationStatement_in_blockStatement2335);
                    localVariableDeclarationStatement();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:371:9: statement
                    {
                    pushFollow(FOLLOW_statement_in_blockStatement2345);
                    statement();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 63, blockStatement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "blockStatement"

    public static class localVariableDeclarationStatement_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "localVariableDeclarationStatement"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:374:1: localVariableDeclarationStatement : localVariableDeclaration ';' ;
    public final ActorFoundryParser.localVariableDeclarationStatement_return localVariableDeclarationStatement() throws RecognitionException {
        ActorFoundryParser.localVariableDeclarationStatement_return retval = new ActorFoundryParser.localVariableDeclarationStatement_return();
        retval.start = input.LT(1);
        int localVariableDeclarationStatement_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 64) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:375:5: ( localVariableDeclaration ';' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:375:10: localVariableDeclaration ';'
            {
            pushFollow(FOLLOW_localVariableDeclaration_in_localVariableDeclarationStatement2369);
            localVariableDeclaration();

            state._fsp--;
            if (state.failed) return retval;
            match(input,26,FOLLOW_26_in_localVariableDeclarationStatement2371); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 64, localVariableDeclarationStatement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "localVariableDeclarationStatement"

    public static class localVariableDeclaration_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "localVariableDeclaration"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:378:1: localVariableDeclaration : variableModifiers type variableDeclarators ;
    public final ActorFoundryParser.localVariableDeclaration_return localVariableDeclaration() throws RecognitionException {
        ActorFoundryParser.localVariableDeclaration_return retval = new ActorFoundryParser.localVariableDeclaration_return();
        retval.start = input.LT(1);
        int localVariableDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 65) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:379:5: ( variableModifiers type variableDeclarators )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:379:9: variableModifiers type variableDeclarators
            {
            pushFollow(FOLLOW_variableModifiers_in_localVariableDeclaration2390);
            variableModifiers();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_type_in_localVariableDeclaration2392);
            type();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_variableDeclarators_in_localVariableDeclaration2394);
            variableDeclarators();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 65, localVariableDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "localVariableDeclaration"

    public static class variableModifiers_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "variableModifiers"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:382:1: variableModifiers : ( variableModifier )* ;
    public final ActorFoundryParser.variableModifiers_return variableModifiers() throws RecognitionException {
        ActorFoundryParser.variableModifiers_return retval = new ActorFoundryParser.variableModifiers_return();
        retval.start = input.LT(1);
        int variableModifiers_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 66) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:383:5: ( ( variableModifier )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:383:9: ( variableModifier )*
            {
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:383:9: ( variableModifier )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==45||LA68_0==66) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: variableModifier
            	    {
            	    pushFollow(FOLLOW_variableModifier_in_variableModifiers2417);
            	    variableModifier();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 66, variableModifiers_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "variableModifiers"

    public static class statement_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "statement"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:386:1: statement : ( block | ASSERT expression ( ':' expression )? ';' | 'if' parExpression statement ( options {k=1; } : 'else' statement )? | 'for' '(' forControl ')' statement | 'while' parExpression statement | 'do' statement 'while' parExpression ';' | 'try' block ( catches 'finally' block | catches | 'finally' block ) | 'switch' parExpression '{' switchBlockStatementGroups '}' | 'return' ( expression )? ';' | 'throw' expression ';' | 'break' ( Identifier )? ';' | 'continue' ( Identifier )? ';' | ';' | statementExpression ';' | Identifier ':' statement | messageSend ';' );
    public final ActorFoundryParser.statement_return statement() throws RecognitionException {
        ActorFoundryParser.statement_return retval = new ActorFoundryParser.statement_return();
        retval.start = input.LT(1);
        int statement_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 67) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:387:5: ( block | ASSERT expression ( ':' expression )? ';' | 'if' parExpression statement ( options {k=1; } : 'else' statement )? | 'for' '(' forControl ')' statement | 'while' parExpression statement | 'do' statement 'while' parExpression ';' | 'try' block ( catches 'finally' block | catches | 'finally' block ) | 'switch' parExpression '{' switchBlockStatementGroups '}' | 'return' ( expression )? ';' | 'throw' expression ';' | 'break' ( Identifier )? ';' | 'continue' ( Identifier )? ';' | ';' | statementExpression ';' | Identifier ':' statement | messageSend ';' )
            int alt75=16;
            alt75 = dfa75.predict(input);
            switch (alt75) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:387:7: block
                    {
                    pushFollow(FOLLOW_block_in_statement2435);
                    block();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:388:9: ASSERT expression ( ':' expression )? ';'
                    {
                    match(input,ASSERT,FOLLOW_ASSERT_in_statement2445); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_statement2447);
                    expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:388:27: ( ':' expression )?
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==68) ) {
                        alt69=1;
                    }
                    switch (alt69) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:388:28: ':' expression
                            {
                            match(input,68,FOLLOW_68_in_statement2450); if (state.failed) return retval;
                            pushFollow(FOLLOW_expression_in_statement2452);
                            expression();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,26,FOLLOW_26_in_statement2456); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:389:9: 'if' parExpression statement ( options {k=1; } : 'else' statement )?
                    {
                    match(input,69,FOLLOW_69_in_statement2466); if (state.failed) return retval;
                    pushFollow(FOLLOW_parExpression_in_statement2468);
                    parExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    pushFollow(FOLLOW_statement_in_statement2470);
                    statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:389:38: ( options {k=1; } : 'else' statement )?
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==70) ) {
                        int LA70_2 = input.LA(2);

                        if ( (synpred97_ActorFoundry()) ) {
                            alt70=1;
                        }
                    }
                    switch (alt70) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:389:54: 'else' statement
                            {
                            match(input,70,FOLLOW_70_in_statement2480); if (state.failed) return retval;
                            pushFollow(FOLLOW_statement_in_statement2482);
                            statement();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:390:9: 'for' '(' forControl ')' statement
                    {
                    match(input,71,FOLLOW_71_in_statement2494); if (state.failed) return retval;
                    match(input,59,FOLLOW_59_in_statement2496); if (state.failed) return retval;
                    pushFollow(FOLLOW_forControl_in_statement2498);
                    forControl();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,60,FOLLOW_60_in_statement2500); if (state.failed) return retval;
                    pushFollow(FOLLOW_statement_in_statement2502);
                    statement();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:391:9: 'while' parExpression statement
                    {
                    match(input,72,FOLLOW_72_in_statement2512); if (state.failed) return retval;
                    pushFollow(FOLLOW_parExpression_in_statement2514);
                    parExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    pushFollow(FOLLOW_statement_in_statement2516);
                    statement();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 6 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:392:9: 'do' statement 'while' parExpression ';'
                    {
                    match(input,73,FOLLOW_73_in_statement2526); if (state.failed) return retval;
                    pushFollow(FOLLOW_statement_in_statement2528);
                    statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,72,FOLLOW_72_in_statement2530); if (state.failed) return retval;
                    pushFollow(FOLLOW_parExpression_in_statement2532);
                    parExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,26,FOLLOW_26_in_statement2534); if (state.failed) return retval;

                    }
                    break;
                case 7 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:393:9: 'try' block ( catches 'finally' block | catches | 'finally' block )
                    {
                    match(input,74,FOLLOW_74_in_statement2544); if (state.failed) return retval;
                    pushFollow(FOLLOW_block_in_statement2546);
                    block();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:394:9: ( catches 'finally' block | catches | 'finally' block )
                    int alt71=3;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==81) ) {
                        int LA71_1 = input.LA(2);

                        if ( (synpred102_ActorFoundry()) ) {
                            alt71=1;
                        }
                        else if ( (synpred103_ActorFoundry()) ) {
                            alt71=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 71, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA71_0==75) ) {
                        alt71=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 71, 0, input);

                        throw nvae;
                    }
                    switch (alt71) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:394:11: catches 'finally' block
                            {
                            pushFollow(FOLLOW_catches_in_statement2558);
                            catches();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,75,FOLLOW_75_in_statement2560); if (state.failed) return retval;
                            pushFollow(FOLLOW_block_in_statement2562);
                            block();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;
                        case 2 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:395:11: catches
                            {
                            pushFollow(FOLLOW_catches_in_statement2574);
                            catches();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;
                        case 3 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:396:13: 'finally' block
                            {
                            match(input,75,FOLLOW_75_in_statement2588); if (state.failed) return retval;
                            pushFollow(FOLLOW_block_in_statement2590);
                            block();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;
                case 8 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:398:9: 'switch' parExpression '{' switchBlockStatementGroups '}'
                    {
                    match(input,76,FOLLOW_76_in_statement2610); if (state.failed) return retval;
                    pushFollow(FOLLOW_parExpression_in_statement2612);
                    parExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,37,FOLLOW_37_in_statement2614); if (state.failed) return retval;
                    pushFollow(FOLLOW_switchBlockStatementGroups_in_statement2616);
                    switchBlockStatementGroups();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,38,FOLLOW_38_in_statement2618); if (state.failed) return retval;

                    }
                    break;
                case 9 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:399:9: 'return' ( expression )? ';'
                    {
                    match(input,77,FOLLOW_77_in_statement2628); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:399:18: ( expression )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( ((LA72_0>=Identifier && LA72_0<=DecimalLiteral)||LA72_0==39||(LA72_0>=49 && LA72_0<=56)||(LA72_0>=58 && LA72_0<=59)||(LA72_0>=62 && LA72_0<=65)||(LA72_0>=98 && LA72_0<=99)||(LA72_0>=102 && LA72_0<=105)) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: expression
                            {
                            pushFollow(FOLLOW_expression_in_statement2630);
                            expression();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,26,FOLLOW_26_in_statement2633); if (state.failed) return retval;

                    }
                    break;
                case 10 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:400:9: 'throw' expression ';'
                    {
                    match(input,78,FOLLOW_78_in_statement2643); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_statement2645);
                    expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,26,FOLLOW_26_in_statement2647); if (state.failed) return retval;

                    }
                    break;
                case 11 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:401:9: 'break' ( Identifier )? ';'
                    {
                    match(input,79,FOLLOW_79_in_statement2657); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:401:17: ( Identifier )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==Identifier) ) {
                        alt73=1;
                    }
                    switch (alt73) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: Identifier
                            {
                            match(input,Identifier,FOLLOW_Identifier_in_statement2659); if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,26,FOLLOW_26_in_statement2662); if (state.failed) return retval;

                    }
                    break;
                case 12 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:402:9: 'continue' ( Identifier )? ';'
                    {
                    match(input,80,FOLLOW_80_in_statement2672); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:402:20: ( Identifier )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==Identifier) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: Identifier
                            {
                            match(input,Identifier,FOLLOW_Identifier_in_statement2674); if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,26,FOLLOW_26_in_statement2677); if (state.failed) return retval;

                    }
                    break;
                case 13 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:403:9: ';'
                    {
                    match(input,26,FOLLOW_26_in_statement2687); if (state.failed) return retval;

                    }
                    break;
                case 14 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:404:9: statementExpression ';'
                    {
                    pushFollow(FOLLOW_statementExpression_in_statement2698);
                    statementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,26,FOLLOW_26_in_statement2700); if (state.failed) return retval;

                    }
                    break;
                case 15 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:405:9: Identifier ':' statement
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_statement2710); if (state.failed) return retval;
                    match(input,68,FOLLOW_68_in_statement2712); if (state.failed) return retval;
                    pushFollow(FOLLOW_statement_in_statement2714);
                    statement();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 16 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:406:9: messageSend ';'
                    {
                    pushFollow(FOLLOW_messageSend_in_statement2724);
                    messageSend();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,26,FOLLOW_26_in_statement2726); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 67, statement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class catches_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "catches"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:409:1: catches : catchClause ( catchClause )* ;
    public final ActorFoundryParser.catches_return catches() throws RecognitionException {
        ActorFoundryParser.catches_return retval = new ActorFoundryParser.catches_return();
        retval.start = input.LT(1);
        int catches_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 68) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:410:5: ( catchClause ( catchClause )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:410:9: catchClause ( catchClause )*
            {
            pushFollow(FOLLOW_catchClause_in_catches2745);
            catchClause();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:410:21: ( catchClause )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==81) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:410:22: catchClause
            	    {
            	    pushFollow(FOLLOW_catchClause_in_catches2748);
            	    catchClause();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 68, catches_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "catches"

    public static class catchClause_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "catchClause"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:413:1: catchClause : 'catch' '(' formalParameter ')' block ;
    public final ActorFoundryParser.catchClause_return catchClause() throws RecognitionException {
        ActorFoundryParser.catchClause_return retval = new ActorFoundryParser.catchClause_return();
        retval.start = input.LT(1);
        int catchClause_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 69) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:414:5: ( 'catch' '(' formalParameter ')' block )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:414:9: 'catch' '(' formalParameter ')' block
            {
            match(input,81,FOLLOW_81_in_catchClause2773); if (state.failed) return retval;
            match(input,59,FOLLOW_59_in_catchClause2775); if (state.failed) return retval;
            pushFollow(FOLLOW_formalParameter_in_catchClause2777);
            formalParameter();

            state._fsp--;
            if (state.failed) return retval;
            match(input,60,FOLLOW_60_in_catchClause2779); if (state.failed) return retval;
            pushFollow(FOLLOW_block_in_catchClause2781);
            block();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 69, catchClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "catchClause"

    public static class formalParameter_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "formalParameter"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:417:1: formalParameter : variableModifiers type variableDeclaratorId ;
    public final ActorFoundryParser.formalParameter_return formalParameter() throws RecognitionException {
        ActorFoundryParser.formalParameter_return retval = new ActorFoundryParser.formalParameter_return();
        retval.start = input.LT(1);
        int formalParameter_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 70) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:418:5: ( variableModifiers type variableDeclaratorId )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:418:9: variableModifiers type variableDeclaratorId
            {
            pushFollow(FOLLOW_variableModifiers_in_formalParameter2800);
            variableModifiers();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_type_in_formalParameter2802);
            type();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_variableDeclaratorId_in_formalParameter2804);
            variableDeclaratorId();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 70, formalParameter_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "formalParameter"

    public static class switchBlockStatementGroups_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "switchBlockStatementGroups"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:421:1: switchBlockStatementGroups : ( switchBlockStatementGroup )* ;
    public final ActorFoundryParser.switchBlockStatementGroups_return switchBlockStatementGroups() throws RecognitionException {
        ActorFoundryParser.switchBlockStatementGroups_return retval = new ActorFoundryParser.switchBlockStatementGroups_return();
        retval.start = input.LT(1);
        int switchBlockStatementGroups_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 71) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:422:5: ( ( switchBlockStatementGroup )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:422:9: ( switchBlockStatementGroup )*
            {
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:422:9: ( switchBlockStatementGroup )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==67||LA77_0==82) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:422:10: switchBlockStatementGroup
            	    {
            	    pushFollow(FOLLOW_switchBlockStatementGroup_in_switchBlockStatementGroups2832);
            	    switchBlockStatementGroup();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop77;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 71, switchBlockStatementGroups_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "switchBlockStatementGroups"

    public static class switchBlockStatementGroup_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "switchBlockStatementGroup"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:429:1: switchBlockStatementGroup : ( switchLabel )+ ( blockStatement )* ;
    public final ActorFoundryParser.switchBlockStatementGroup_return switchBlockStatementGroup() throws RecognitionException {
        ActorFoundryParser.switchBlockStatementGroup_return retval = new ActorFoundryParser.switchBlockStatementGroup_return();
        retval.start = input.LT(1);
        int switchBlockStatementGroup_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 72) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:430:5: ( ( switchLabel )+ ( blockStatement )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:430:9: ( switchLabel )+ ( blockStatement )*
            {
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:430:9: ( switchLabel )+
            int cnt78=0;
            loop78:
            do {
                int alt78=2;
                int LA78_0 = input.LA(1);

                if ( (LA78_0==82) ) {
                    int LA78_2 = input.LA(2);

                    if ( (synpred118_ActorFoundry()) ) {
                        alt78=1;
                    }


                }
                else if ( (LA78_0==67) ) {
                    int LA78_3 = input.LA(2);

                    if ( (synpred118_ActorFoundry()) ) {
                        alt78=1;
                    }


                }


                switch (alt78) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: switchLabel
            	    {
            	    pushFollow(FOLLOW_switchLabel_in_switchBlockStatementGroup2859);
            	    switchLabel();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    if ( cnt78 >= 1 ) break loop78;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(78, input);
                        throw eee;
                }
                cnt78++;
            } while (true);

            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:430:22: ( blockStatement )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( ((LA79_0>=Identifier && LA79_0<=ASSERT)||LA79_0==26||LA79_0==37||LA79_0==39||LA79_0==45||(LA79_0>=49 && LA79_0<=56)||(LA79_0>=58 && LA79_0<=59)||(LA79_0>=62 && LA79_0<=66)||LA79_0==69||(LA79_0>=71 && LA79_0<=74)||(LA79_0>=76 && LA79_0<=80)||(LA79_0>=98 && LA79_0<=99)||(LA79_0>=102 && LA79_0<=105)) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: blockStatement
            	    {
            	    pushFollow(FOLLOW_blockStatement_in_switchBlockStatementGroup2862);
            	    blockStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop79;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 72, switchBlockStatementGroup_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "switchBlockStatementGroup"

    public static class switchLabel_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "switchLabel"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:433:1: switchLabel : ( 'case' constantExpression ':' | 'case' enumConstantName ':' | 'default' ':' );
    public final ActorFoundryParser.switchLabel_return switchLabel() throws RecognitionException {
        ActorFoundryParser.switchLabel_return retval = new ActorFoundryParser.switchLabel_return();
        retval.start = input.LT(1);
        int switchLabel_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 73) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:434:5: ( 'case' constantExpression ':' | 'case' enumConstantName ':' | 'default' ':' )
            int alt80=3;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==82) ) {
                int LA80_1 = input.LA(2);

                if ( ((LA80_1>=FloatingPointLiteral && LA80_1<=DecimalLiteral)||LA80_1==39||(LA80_1>=49 && LA80_1<=56)||(LA80_1>=58 && LA80_1<=59)||(LA80_1>=62 && LA80_1<=65)||(LA80_1>=98 && LA80_1<=99)||(LA80_1>=102 && LA80_1<=105)) ) {
                    alt80=1;
                }
                else if ( (LA80_1==Identifier) ) {
                    int LA80_4 = input.LA(3);

                    if ( (LA80_4==68) ) {
                        int LA80_5 = input.LA(4);

                        if ( (synpred120_ActorFoundry()) ) {
                            alt80=1;
                        }
                        else if ( (synpred121_ActorFoundry()) ) {
                            alt80=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 80, 5, input);

                            throw nvae;
                        }
                    }
                    else if ( ((LA80_4>=29 && LA80_4<=30)||LA80_4==32||LA80_4==34||LA80_4==36||LA80_4==41||LA80_4==44||LA80_4==57||LA80_4==59||(LA80_4>=83 && LA80_4<=103)) ) {
                        alt80=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 80, 4, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 80, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA80_0==67) ) {
                alt80=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }
            switch (alt80) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:434:9: 'case' constantExpression ':'
                    {
                    match(input,82,FOLLOW_82_in_switchLabel2886); if (state.failed) return retval;
                    pushFollow(FOLLOW_constantExpression_in_switchLabel2888);
                    constantExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,68,FOLLOW_68_in_switchLabel2890); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:435:9: 'case' enumConstantName ':'
                    {
                    match(input,82,FOLLOW_82_in_switchLabel2900); if (state.failed) return retval;
                    pushFollow(FOLLOW_enumConstantName_in_switchLabel2902);
                    enumConstantName();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,68,FOLLOW_68_in_switchLabel2904); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:436:9: 'default' ':'
                    {
                    match(input,67,FOLLOW_67_in_switchLabel2914); if (state.failed) return retval;
                    match(input,68,FOLLOW_68_in_switchLabel2916); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 73, switchLabel_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "switchLabel"

    public static class forControl_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "forControl"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:439:1: forControl options {k=3; } : ( enhancedForControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );
    public final ActorFoundryParser.forControl_return forControl() throws RecognitionException {
        ActorFoundryParser.forControl_return retval = new ActorFoundryParser.forControl_return();
        retval.start = input.LT(1);
        int forControl_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 74) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:441:5: ( enhancedForControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? )
            int alt84=2;
            alt84 = dfa84.predict(input);
            switch (alt84) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:441:9: enhancedForControl
                    {
                    pushFollow(FOLLOW_enhancedForControl_in_forControl2947);
                    enhancedForControl();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:442:9: ( forInit )? ';' ( expression )? ';' ( forUpdate )?
                    {
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:442:9: ( forInit )?
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( ((LA81_0>=Identifier && LA81_0<=DecimalLiteral)||LA81_0==39||LA81_0==45||(LA81_0>=49 && LA81_0<=56)||(LA81_0>=58 && LA81_0<=59)||(LA81_0>=62 && LA81_0<=66)||(LA81_0>=98 && LA81_0<=99)||(LA81_0>=102 && LA81_0<=105)) ) {
                        alt81=1;
                    }
                    switch (alt81) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: forInit
                            {
                            pushFollow(FOLLOW_forInit_in_forControl2957);
                            forInit();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,26,FOLLOW_26_in_forControl2960); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:442:22: ( expression )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( ((LA82_0>=Identifier && LA82_0<=DecimalLiteral)||LA82_0==39||(LA82_0>=49 && LA82_0<=56)||(LA82_0>=58 && LA82_0<=59)||(LA82_0>=62 && LA82_0<=65)||(LA82_0>=98 && LA82_0<=99)||(LA82_0>=102 && LA82_0<=105)) ) {
                        alt82=1;
                    }
                    switch (alt82) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: expression
                            {
                            pushFollow(FOLLOW_expression_in_forControl2962);
                            expression();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,26,FOLLOW_26_in_forControl2965); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:442:38: ( forUpdate )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( ((LA83_0>=Identifier && LA83_0<=DecimalLiteral)||LA83_0==39||(LA83_0>=49 && LA83_0<=56)||(LA83_0>=58 && LA83_0<=59)||(LA83_0>=62 && LA83_0<=65)||(LA83_0>=98 && LA83_0<=99)||(LA83_0>=102 && LA83_0<=105)) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: forUpdate
                            {
                            pushFollow(FOLLOW_forUpdate_in_forControl2967);
                            forUpdate();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 74, forControl_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "forControl"

    public static class forInit_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "forInit"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:445:1: forInit : ( localVariableDeclaration | expressionList );
    public final ActorFoundryParser.forInit_return forInit() throws RecognitionException {
        ActorFoundryParser.forInit_return retval = new ActorFoundryParser.forInit_return();
        retval.start = input.LT(1);
        int forInit_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 75) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:446:5: ( localVariableDeclaration | expressionList )
            int alt85=2;
            switch ( input.LA(1) ) {
            case 45:
            case 66:
                {
                alt85=1;
                }
                break;
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 39:
            case 58:
            case 59:
            case 62:
            case 63:
            case 64:
            case 65:
            case 98:
            case 99:
            case 102:
            case 103:
            case 104:
            case 105:
                {
                alt85=2;
                }
                break;
            case Identifier:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
                {
                int LA85_3 = input.LA(2);

                if ( (synpred126_ActorFoundry()) ) {
                    alt85=1;
                }
                else if ( (true) ) {
                    alt85=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 85, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }

            switch (alt85) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:446:9: localVariableDeclaration
                    {
                    pushFollow(FOLLOW_localVariableDeclaration_in_forInit2987);
                    localVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:447:9: expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_forInit2997);
                    expressionList();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 75, forInit_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "forInit"

    public static class enhancedForControl_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "enhancedForControl"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:450:1: enhancedForControl : variableModifiers type Identifier ':' expression ;
    public final ActorFoundryParser.enhancedForControl_return enhancedForControl() throws RecognitionException {
        ActorFoundryParser.enhancedForControl_return retval = new ActorFoundryParser.enhancedForControl_return();
        retval.start = input.LT(1);
        int enhancedForControl_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 76) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:451:5: ( variableModifiers type Identifier ':' expression )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:451:9: variableModifiers type Identifier ':' expression
            {
            pushFollow(FOLLOW_variableModifiers_in_enhancedForControl3020);
            variableModifiers();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_type_in_enhancedForControl3022);
            type();

            state._fsp--;
            if (state.failed) return retval;
            match(input,Identifier,FOLLOW_Identifier_in_enhancedForControl3024); if (state.failed) return retval;
            match(input,68,FOLLOW_68_in_enhancedForControl3026); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_enhancedForControl3028);
            expression();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 76, enhancedForControl_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "enhancedForControl"

    public static class forUpdate_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "forUpdate"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:454:1: forUpdate : expressionList ;
    public final ActorFoundryParser.forUpdate_return forUpdate() throws RecognitionException {
        ActorFoundryParser.forUpdate_return retval = new ActorFoundryParser.forUpdate_return();
        retval.start = input.LT(1);
        int forUpdate_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 77) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:455:5: ( expressionList )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:455:9: expressionList
            {
            pushFollow(FOLLOW_expressionList_in_forUpdate3047);
            expressionList();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 77, forUpdate_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "forUpdate"

    public static class parExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "parExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:460:1: parExpression : '(' expression ')' ;
    public final ActorFoundryParser.parExpression_return parExpression() throws RecognitionException {
        ActorFoundryParser.parExpression_return retval = new ActorFoundryParser.parExpression_return();
        retval.start = input.LT(1);
        int parExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 78) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:461:5: ( '(' expression ')' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:461:9: '(' expression ')'
            {
            match(input,59,FOLLOW_59_in_parExpression3068); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_parExpression3070);
            expression();

            state._fsp--;
            if (state.failed) return retval;
            match(input,60,FOLLOW_60_in_parExpression3072); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 78, parExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "parExpression"

    public static class expressionList_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "expressionList"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:464:1: expressionList : expression ( ',' expression )* ;
    public final ActorFoundryParser.expressionList_return expressionList() throws RecognitionException {
        ActorFoundryParser.expressionList_return retval = new ActorFoundryParser.expressionList_return();
        retval.start = input.LT(1);
        int expressionList_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 79) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:465:5: ( expression ( ',' expression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:465:9: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressionList3095);
            expression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:465:20: ( ',' expression )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==33) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:465:21: ',' expression
            	    {
            	    match(input,33,FOLLOW_33_in_expressionList3098); if (state.failed) return retval;
            	    pushFollow(FOLLOW_expression_in_expressionList3100);
            	    expression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop86;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 79, expressionList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expressionList"

    public static class statementExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "statementExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:468:1: statementExpression : expression ;
    public final ActorFoundryParser.statementExpression_return statementExpression() throws RecognitionException {
        ActorFoundryParser.statementExpression_return retval = new ActorFoundryParser.statementExpression_return();
        retval.start = input.LT(1);
        int statementExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 80) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:469:5: ( expression )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:469:9: expression
            {
            pushFollow(FOLLOW_expression_in_statementExpression3121);
            expression();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 80, statementExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "statementExpression"

    public static class constantExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "constantExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:472:1: constantExpression : expression ;
    public final ActorFoundryParser.constantExpression_return constantExpression() throws RecognitionException {
        ActorFoundryParser.constantExpression_return retval = new ActorFoundryParser.constantExpression_return();
        retval.start = input.LT(1);
        int constantExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 81) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:473:5: ( expression )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:473:9: expression
            {
            pushFollow(FOLLOW_expression_in_constantExpression3144);
            expression();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 81, constantExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constantExpression"

    public static class expression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "expression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:476:1: expression : conditionalExpression ( assignmentOperator expression )? ;
    public final ActorFoundryParser.expression_return expression() throws RecognitionException {
        ActorFoundryParser.expression_return retval = new ActorFoundryParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 82) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:477:5: ( conditionalExpression ( assignmentOperator expression )? )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:477:9: conditionalExpression ( assignmentOperator expression )?
            {
            pushFollow(FOLLOW_conditionalExpression_in_expression3167);
            conditionalExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:477:31: ( assignmentOperator expression )?
            int alt87=2;
            alt87 = dfa87.predict(input);
            switch (alt87) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:477:32: assignmentOperator expression
                    {
                    pushFollow(FOLLOW_assignmentOperator_in_expression3170);
                    assignmentOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_expression3172);
                    expression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 82, expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class assignmentOperator_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "assignmentOperator"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:480:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | ( '<' '<' '=' )=>t1= '<' t2= '<' t3= '=' {...}? | ( '>' '>' '>' '=' )=>t1= '>' t2= '>' t3= '>' t4= '=' {...}? | ( '>' '>' '=' )=>t1= '>' t2= '>' t3= '=' {...}?);
    public final ActorFoundryParser.assignmentOperator_return assignmentOperator() throws RecognitionException {
        ActorFoundryParser.assignmentOperator_return retval = new ActorFoundryParser.assignmentOperator_return();
        retval.start = input.LT(1);
        int assignmentOperator_StartIndex = input.index();
        Token t1=null;
        Token t2=null;
        Token t3=null;
        Token t4=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 83) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:481:5: ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | ( '<' '<' '=' )=>t1= '<' t2= '<' t3= '=' {...}? | ( '>' '>' '>' '=' )=>t1= '>' t2= '>' t3= '>' t4= '=' {...}? | ( '>' '>' '=' )=>t1= '>' t2= '>' t3= '=' {...}?)
            int alt88=12;
            alt88 = dfa88.predict(input);
            switch (alt88) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:481:9: '='
                    {
                    match(input,44,FOLLOW_44_in_assignmentOperator3197); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:482:9: '+='
                    {
                    match(input,83,FOLLOW_83_in_assignmentOperator3207); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:483:9: '-='
                    {
                    match(input,84,FOLLOW_84_in_assignmentOperator3217); if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:484:9: '*='
                    {
                    match(input,85,FOLLOW_85_in_assignmentOperator3227); if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:485:9: '/='
                    {
                    match(input,86,FOLLOW_86_in_assignmentOperator3237); if (state.failed) return retval;

                    }
                    break;
                case 6 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:486:9: '&='
                    {
                    match(input,87,FOLLOW_87_in_assignmentOperator3247); if (state.failed) return retval;

                    }
                    break;
                case 7 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:487:9: '|='
                    {
                    match(input,88,FOLLOW_88_in_assignmentOperator3257); if (state.failed) return retval;

                    }
                    break;
                case 8 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:488:9: '^='
                    {
                    match(input,89,FOLLOW_89_in_assignmentOperator3267); if (state.failed) return retval;

                    }
                    break;
                case 9 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:489:9: '%='
                    {
                    match(input,90,FOLLOW_90_in_assignmentOperator3277); if (state.failed) return retval;

                    }
                    break;
                case 10 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:490:9: ( '<' '<' '=' )=>t1= '<' t2= '<' t3= '=' {...}?
                    {
                    t1=(Token)match(input,32,FOLLOW_32_in_assignmentOperator3298); if (state.failed) return retval;
                    t2=(Token)match(input,32,FOLLOW_32_in_assignmentOperator3302); if (state.failed) return retval;
                    t3=(Token)match(input,44,FOLLOW_44_in_assignmentOperator3306); if (state.failed) return retval;
                    if ( !(( t1.getLine() == t2.getLine() &&
                              t1.getCharPositionInLine() + 1 == t2.getCharPositionInLine() && 
                              t2.getLine() == t3.getLine() && 
                              t2.getCharPositionInLine() + 1 == t3.getCharPositionInLine() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "assignmentOperator", " $t1.getLine() == $t2.getLine() &&\n          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() && \n          $t2.getLine() == $t3.getLine() && \n          $t2.getCharPositionInLine() + 1 == $t3.getCharPositionInLine() ");
                    }

                    }
                    break;
                case 11 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:495:9: ( '>' '>' '>' '=' )=>t1= '>' t2= '>' t3= '>' t4= '=' {...}?
                    {
                    t1=(Token)match(input,34,FOLLOW_34_in_assignmentOperator3340); if (state.failed) return retval;
                    t2=(Token)match(input,34,FOLLOW_34_in_assignmentOperator3344); if (state.failed) return retval;
                    t3=(Token)match(input,34,FOLLOW_34_in_assignmentOperator3348); if (state.failed) return retval;
                    t4=(Token)match(input,44,FOLLOW_44_in_assignmentOperator3352); if (state.failed) return retval;
                    if ( !(( t1.getLine() == t2.getLine() && 
                              t1.getCharPositionInLine() + 1 == t2.getCharPositionInLine() &&
                              t2.getLine() == t3.getLine() && 
                              t2.getCharPositionInLine() + 1 == t3.getCharPositionInLine() &&
                              t3.getLine() == t4.getLine() && 
                              t3.getCharPositionInLine() + 1 == t4.getCharPositionInLine() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "assignmentOperator", " $t1.getLine() == $t2.getLine() && \n          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() &&\n          $t2.getLine() == $t3.getLine() && \n          $t2.getCharPositionInLine() + 1 == $t3.getCharPositionInLine() &&\n          $t3.getLine() == $t4.getLine() && \n          $t3.getCharPositionInLine() + 1 == $t4.getCharPositionInLine() ");
                    }

                    }
                    break;
                case 12 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:502:9: ( '>' '>' '=' )=>t1= '>' t2= '>' t3= '=' {...}?
                    {
                    t1=(Token)match(input,34,FOLLOW_34_in_assignmentOperator3383); if (state.failed) return retval;
                    t2=(Token)match(input,34,FOLLOW_34_in_assignmentOperator3387); if (state.failed) return retval;
                    t3=(Token)match(input,44,FOLLOW_44_in_assignmentOperator3391); if (state.failed) return retval;
                    if ( !(( t1.getLine() == t2.getLine() && 
                              t1.getCharPositionInLine() + 1 == t2.getCharPositionInLine() && 
                              t2.getLine() == t3.getLine() && 
                              t2.getCharPositionInLine() + 1 == t3.getCharPositionInLine() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "assignmentOperator", " $t1.getLine() == $t2.getLine() && \n          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() && \n          $t2.getLine() == $t3.getLine() && \n          $t2.getCharPositionInLine() + 1 == $t3.getCharPositionInLine() ");
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 83, assignmentOperator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "assignmentOperator"

    public static class conditionalExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "conditionalExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:509:1: conditionalExpression : conditionalOrExpression ( '?' expression ':' expression )? ;
    public final ActorFoundryParser.conditionalExpression_return conditionalExpression() throws RecognitionException {
        ActorFoundryParser.conditionalExpression_return retval = new ActorFoundryParser.conditionalExpression_return();
        retval.start = input.LT(1);
        int conditionalExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 84) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:510:5: ( conditionalOrExpression ( '?' expression ':' expression )? )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:510:9: conditionalOrExpression ( '?' expression ':' expression )?
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalExpression3420);
            conditionalOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:510:33: ( '?' expression ':' expression )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==57) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:510:35: '?' expression ':' expression
                    {
                    match(input,57,FOLLOW_57_in_conditionalExpression3424); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_conditionalExpression3426);
                    expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,68,FOLLOW_68_in_conditionalExpression3428); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_conditionalExpression3430);
                    expression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 84, conditionalExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "conditionalExpression"

    public static class conditionalOrExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "conditionalOrExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:513:1: conditionalOrExpression : conditionalAndExpression ( '||' conditionalAndExpression )* ;
    public final ActorFoundryParser.conditionalOrExpression_return conditionalOrExpression() throws RecognitionException {
        ActorFoundryParser.conditionalOrExpression_return retval = new ActorFoundryParser.conditionalOrExpression_return();
        retval.start = input.LT(1);
        int conditionalOrExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 85) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:514:5: ( conditionalAndExpression ( '||' conditionalAndExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:514:9: conditionalAndExpression ( '||' conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression3452);
            conditionalAndExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:514:34: ( '||' conditionalAndExpression )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==91) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:514:36: '||' conditionalAndExpression
            	    {
            	    match(input,91,FOLLOW_91_in_conditionalOrExpression3456); if (state.failed) return retval;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression3458);
            	    conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 85, conditionalOrExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "conditionalOrExpression"

    public static class conditionalAndExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "conditionalAndExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:517:1: conditionalAndExpression : inclusiveOrExpression ( '&&' inclusiveOrExpression )* ;
    public final ActorFoundryParser.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException {
        ActorFoundryParser.conditionalAndExpression_return retval = new ActorFoundryParser.conditionalAndExpression_return();
        retval.start = input.LT(1);
        int conditionalAndExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 86) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:518:5: ( inclusiveOrExpression ( '&&' inclusiveOrExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:518:9: inclusiveOrExpression ( '&&' inclusiveOrExpression )*
            {
            pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression3480);
            inclusiveOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:518:31: ( '&&' inclusiveOrExpression )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==92) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:518:33: '&&' inclusiveOrExpression
            	    {
            	    match(input,92,FOLLOW_92_in_conditionalAndExpression3484); if (state.failed) return retval;
            	    pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression3486);
            	    inclusiveOrExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 86, conditionalAndExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "conditionalAndExpression"

    public static class inclusiveOrExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "inclusiveOrExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:521:1: inclusiveOrExpression : exclusiveOrExpression ( '|' exclusiveOrExpression )* ;
    public final ActorFoundryParser.inclusiveOrExpression_return inclusiveOrExpression() throws RecognitionException {
        ActorFoundryParser.inclusiveOrExpression_return retval = new ActorFoundryParser.inclusiveOrExpression_return();
        retval.start = input.LT(1);
        int inclusiveOrExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 87) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:522:5: ( exclusiveOrExpression ( '|' exclusiveOrExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:522:9: exclusiveOrExpression ( '|' exclusiveOrExpression )*
            {
            pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression3508);
            exclusiveOrExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:522:31: ( '|' exclusiveOrExpression )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==93) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:522:33: '|' exclusiveOrExpression
            	    {
            	    match(input,93,FOLLOW_93_in_inclusiveOrExpression3512); if (state.failed) return retval;
            	    pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression3514);
            	    exclusiveOrExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop92;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 87, inclusiveOrExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "inclusiveOrExpression"

    public static class exclusiveOrExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "exclusiveOrExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:525:1: exclusiveOrExpression : andExpression ( '^' andExpression )* ;
    public final ActorFoundryParser.exclusiveOrExpression_return exclusiveOrExpression() throws RecognitionException {
        ActorFoundryParser.exclusiveOrExpression_return retval = new ActorFoundryParser.exclusiveOrExpression_return();
        retval.start = input.LT(1);
        int exclusiveOrExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 88) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:526:5: ( andExpression ( '^' andExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:526:9: andExpression ( '^' andExpression )*
            {
            pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression3536);
            andExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:526:23: ( '^' andExpression )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( (LA93_0==94) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:526:25: '^' andExpression
            	    {
            	    match(input,94,FOLLOW_94_in_exclusiveOrExpression3540); if (state.failed) return retval;
            	    pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression3542);
            	    andExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 88, exclusiveOrExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "exclusiveOrExpression"

    public static class andExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "andExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:529:1: andExpression : equalityExpression ( '&' equalityExpression )* ;
    public final ActorFoundryParser.andExpression_return andExpression() throws RecognitionException {
        ActorFoundryParser.andExpression_return retval = new ActorFoundryParser.andExpression_return();
        retval.start = input.LT(1);
        int andExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 89) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:530:5: ( equalityExpression ( '&' equalityExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:530:9: equalityExpression ( '&' equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_andExpression3564);
            equalityExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:530:28: ( '&' equalityExpression )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==36) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:530:30: '&' equalityExpression
            	    {
            	    match(input,36,FOLLOW_36_in_andExpression3568); if (state.failed) return retval;
            	    pushFollow(FOLLOW_equalityExpression_in_andExpression3570);
            	    equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 89, andExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "andExpression"

    public static class equalityExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "equalityExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:533:1: equalityExpression : instanceOfExpression ( ( '==' | '!=' ) instanceOfExpression )* ;
    public final ActorFoundryParser.equalityExpression_return equalityExpression() throws RecognitionException {
        ActorFoundryParser.equalityExpression_return retval = new ActorFoundryParser.equalityExpression_return();
        retval.start = input.LT(1);
        int equalityExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 90) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:534:5: ( instanceOfExpression ( ( '==' | '!=' ) instanceOfExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:534:9: instanceOfExpression ( ( '==' | '!=' ) instanceOfExpression )*
            {
            pushFollow(FOLLOW_instanceOfExpression_in_equalityExpression3592);
            instanceOfExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:534:30: ( ( '==' | '!=' ) instanceOfExpression )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( ((LA95_0>=95 && LA95_0<=96)) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:534:32: ( '==' | '!=' ) instanceOfExpression
            	    {
            	    if ( (input.LA(1)>=95 && input.LA(1)<=96) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_instanceOfExpression_in_equalityExpression3604);
            	    instanceOfExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop95;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 90, equalityExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "equalityExpression"

    public static class instanceOfExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "instanceOfExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:537:1: instanceOfExpression : relationalExpression ( 'instanceof' type )? ;
    public final ActorFoundryParser.instanceOfExpression_return instanceOfExpression() throws RecognitionException {
        ActorFoundryParser.instanceOfExpression_return retval = new ActorFoundryParser.instanceOfExpression_return();
        retval.start = input.LT(1);
        int instanceOfExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 91) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:538:5: ( relationalExpression ( 'instanceof' type )? )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:538:9: relationalExpression ( 'instanceof' type )?
            {
            pushFollow(FOLLOW_relationalExpression_in_instanceOfExpression3626);
            relationalExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:538:30: ( 'instanceof' type )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==97) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:538:31: 'instanceof' type
                    {
                    match(input,97,FOLLOW_97_in_instanceOfExpression3629); if (state.failed) return retval;
                    pushFollow(FOLLOW_type_in_instanceOfExpression3631);
                    type();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 91, instanceOfExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "instanceOfExpression"

    public static class relationalExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "relationalExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:541:1: relationalExpression : shiftExpression ( relationalOp shiftExpression )* ;
    public final ActorFoundryParser.relationalExpression_return relationalExpression() throws RecognitionException {
        ActorFoundryParser.relationalExpression_return retval = new ActorFoundryParser.relationalExpression_return();
        retval.start = input.LT(1);
        int relationalExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 92) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:542:5: ( shiftExpression ( relationalOp shiftExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:542:9: shiftExpression ( relationalOp shiftExpression )*
            {
            pushFollow(FOLLOW_shiftExpression_in_relationalExpression3652);
            shiftExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:542:25: ( relationalOp shiftExpression )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==32) ) {
                    int LA97_2 = input.LA(2);

                    if ( ((LA97_2>=Identifier && LA97_2<=DecimalLiteral)||LA97_2==39||LA97_2==44||(LA97_2>=49 && LA97_2<=56)||(LA97_2>=58 && LA97_2<=59)||(LA97_2>=62 && LA97_2<=65)||(LA97_2>=98 && LA97_2<=99)||(LA97_2>=102 && LA97_2<=105)) ) {
                        alt97=1;
                    }


                }
                else if ( (LA97_0==34) ) {
                    int LA97_3 = input.LA(2);

                    if ( ((LA97_3>=Identifier && LA97_3<=DecimalLiteral)||LA97_3==39||LA97_3==44||(LA97_3>=49 && LA97_3<=56)||(LA97_3>=58 && LA97_3<=59)||(LA97_3>=62 && LA97_3<=65)||(LA97_3>=98 && LA97_3<=99)||(LA97_3>=102 && LA97_3<=105)) ) {
                        alt97=1;
                    }


                }


                switch (alt97) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:542:27: relationalOp shiftExpression
            	    {
            	    pushFollow(FOLLOW_relationalOp_in_relationalExpression3656);
            	    relationalOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    pushFollow(FOLLOW_shiftExpression_in_relationalExpression3658);
            	    shiftExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop97;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 92, relationalExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "relationalExpression"

    public static class relationalOp_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "relationalOp"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:545:1: relationalOp : ( ( '<' '=' )=>t1= '<' t2= '=' {...}? | ( '>' '=' )=>t1= '>' t2= '=' {...}? | '<' | '>' );
    public final ActorFoundryParser.relationalOp_return relationalOp() throws RecognitionException {
        ActorFoundryParser.relationalOp_return retval = new ActorFoundryParser.relationalOp_return();
        retval.start = input.LT(1);
        int relationalOp_StartIndex = input.index();
        Token t1=null;
        Token t2=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 93) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:546:5: ( ( '<' '=' )=>t1= '<' t2= '=' {...}? | ( '>' '=' )=>t1= '>' t2= '=' {...}? | '<' | '>' )
            int alt98=4;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==32) ) {
                int LA98_1 = input.LA(2);

                if ( (LA98_1==44) && (synpred151_ActorFoundry())) {
                    alt98=1;
                }
                else if ( ((LA98_1>=Identifier && LA98_1<=DecimalLiteral)||LA98_1==39||(LA98_1>=49 && LA98_1<=56)||(LA98_1>=58 && LA98_1<=59)||(LA98_1>=62 && LA98_1<=65)||(LA98_1>=98 && LA98_1<=99)||(LA98_1>=102 && LA98_1<=105)) ) {
                    alt98=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 98, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA98_0==34) ) {
                int LA98_2 = input.LA(2);

                if ( (LA98_2==44) && (synpred152_ActorFoundry())) {
                    alt98=2;
                }
                else if ( ((LA98_2>=Identifier && LA98_2<=DecimalLiteral)||LA98_2==39||(LA98_2>=49 && LA98_2<=56)||(LA98_2>=58 && LA98_2<=59)||(LA98_2>=62 && LA98_2<=65)||(LA98_2>=98 && LA98_2<=99)||(LA98_2>=102 && LA98_2<=105)) ) {
                    alt98=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 98, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }
            switch (alt98) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:546:9: ( '<' '=' )=>t1= '<' t2= '=' {...}?
                    {
                    t1=(Token)match(input,32,FOLLOW_32_in_relationalOp3693); if (state.failed) return retval;
                    t2=(Token)match(input,44,FOLLOW_44_in_relationalOp3697); if (state.failed) return retval;
                    if ( !(( t1.getLine() == t2.getLine() && 
                              t1.getCharPositionInLine() + 1 == t2.getCharPositionInLine() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "relationalOp", " $t1.getLine() == $t2.getLine() && \n          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() ");
                    }

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:549:9: ( '>' '=' )=>t1= '>' t2= '=' {...}?
                    {
                    t1=(Token)match(input,34,FOLLOW_34_in_relationalOp3727); if (state.failed) return retval;
                    t2=(Token)match(input,44,FOLLOW_44_in_relationalOp3731); if (state.failed) return retval;
                    if ( !(( t1.getLine() == t2.getLine() && 
                              t1.getCharPositionInLine() + 1 == t2.getCharPositionInLine() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "relationalOp", " $t1.getLine() == $t2.getLine() && \n          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() ");
                    }

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:552:9: '<'
                    {
                    match(input,32,FOLLOW_32_in_relationalOp3752); if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:553:9: '>'
                    {
                    match(input,34,FOLLOW_34_in_relationalOp3763); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 93, relationalOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "relationalOp"

    public static class shiftExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "shiftExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:556:1: shiftExpression : additiveExpression ( shiftOp additiveExpression )* ;
    public final ActorFoundryParser.shiftExpression_return shiftExpression() throws RecognitionException {
        ActorFoundryParser.shiftExpression_return retval = new ActorFoundryParser.shiftExpression_return();
        retval.start = input.LT(1);
        int shiftExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 94) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:557:5: ( additiveExpression ( shiftOp additiveExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:557:9: additiveExpression ( shiftOp additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_shiftExpression3783);
            additiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:557:28: ( shiftOp additiveExpression )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( (LA99_0==32) ) {
                    int LA99_1 = input.LA(2);

                    if ( (LA99_1==32) ) {
                        int LA99_4 = input.LA(3);

                        if ( ((LA99_4>=Identifier && LA99_4<=DecimalLiteral)||LA99_4==39||(LA99_4>=49 && LA99_4<=56)||(LA99_4>=58 && LA99_4<=59)||(LA99_4>=62 && LA99_4<=65)||(LA99_4>=98 && LA99_4<=99)||(LA99_4>=102 && LA99_4<=105)) ) {
                            alt99=1;
                        }


                    }


                }
                else if ( (LA99_0==34) ) {
                    int LA99_2 = input.LA(2);

                    if ( (LA99_2==34) ) {
                        int LA99_5 = input.LA(3);

                        if ( (LA99_5==34) ) {
                            int LA99_7 = input.LA(4);

                            if ( ((LA99_7>=Identifier && LA99_7<=DecimalLiteral)||LA99_7==39||(LA99_7>=49 && LA99_7<=56)||(LA99_7>=58 && LA99_7<=59)||(LA99_7>=62 && LA99_7<=65)||(LA99_7>=98 && LA99_7<=99)||(LA99_7>=102 && LA99_7<=105)) ) {
                                alt99=1;
                            }


                        }
                        else if ( ((LA99_5>=Identifier && LA99_5<=DecimalLiteral)||LA99_5==39||(LA99_5>=49 && LA99_5<=56)||(LA99_5>=58 && LA99_5<=59)||(LA99_5>=62 && LA99_5<=65)||(LA99_5>=98 && LA99_5<=99)||(LA99_5>=102 && LA99_5<=105)) ) {
                            alt99=1;
                        }


                    }


                }


                switch (alt99) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:557:30: shiftOp additiveExpression
            	    {
            	    pushFollow(FOLLOW_shiftOp_in_shiftExpression3787);
            	    shiftOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    pushFollow(FOLLOW_additiveExpression_in_shiftExpression3789);
            	    additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop99;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 94, shiftExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "shiftExpression"

    public static class shiftOp_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "shiftOp"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:560:1: shiftOp : ( ( '<' '<' )=>t1= '<' t2= '<' {...}? | ( '>' '>' '>' )=>t1= '>' t2= '>' t3= '>' {...}? | ( '>' '>' )=>t1= '>' t2= '>' {...}?);
    public final ActorFoundryParser.shiftOp_return shiftOp() throws RecognitionException {
        ActorFoundryParser.shiftOp_return retval = new ActorFoundryParser.shiftOp_return();
        retval.start = input.LT(1);
        int shiftOp_StartIndex = input.index();
        Token t1=null;
        Token t2=null;
        Token t3=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 95) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:561:5: ( ( '<' '<' )=>t1= '<' t2= '<' {...}? | ( '>' '>' '>' )=>t1= '>' t2= '>' t3= '>' {...}? | ( '>' '>' )=>t1= '>' t2= '>' {...}?)
            int alt100=3;
            alt100 = dfa100.predict(input);
            switch (alt100) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:561:9: ( '<' '<' )=>t1= '<' t2= '<' {...}?
                    {
                    t1=(Token)match(input,32,FOLLOW_32_in_shiftOp3820); if (state.failed) return retval;
                    t2=(Token)match(input,32,FOLLOW_32_in_shiftOp3824); if (state.failed) return retval;
                    if ( !(( t1.getLine() == t2.getLine() && 
                              t1.getCharPositionInLine() + 1 == t2.getCharPositionInLine() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "shiftOp", " $t1.getLine() == $t2.getLine() && \n          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() ");
                    }

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:564:9: ( '>' '>' '>' )=>t1= '>' t2= '>' t3= '>' {...}?
                    {
                    t1=(Token)match(input,34,FOLLOW_34_in_shiftOp3856); if (state.failed) return retval;
                    t2=(Token)match(input,34,FOLLOW_34_in_shiftOp3860); if (state.failed) return retval;
                    t3=(Token)match(input,34,FOLLOW_34_in_shiftOp3864); if (state.failed) return retval;
                    if ( !(( t1.getLine() == t2.getLine() && 
                              t1.getCharPositionInLine() + 1 == t2.getCharPositionInLine() &&
                              t2.getLine() == t3.getLine() && 
                              t2.getCharPositionInLine() + 1 == t3.getCharPositionInLine() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "shiftOp", " $t1.getLine() == $t2.getLine() && \n          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() &&\n          $t2.getLine() == $t3.getLine() && \n          $t2.getCharPositionInLine() + 1 == $t3.getCharPositionInLine() ");
                    }

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:569:9: ( '>' '>' )=>t1= '>' t2= '>' {...}?
                    {
                    t1=(Token)match(input,34,FOLLOW_34_in_shiftOp3894); if (state.failed) return retval;
                    t2=(Token)match(input,34,FOLLOW_34_in_shiftOp3898); if (state.failed) return retval;
                    if ( !(( t1.getLine() == t2.getLine() && 
                              t1.getCharPositionInLine() + 1 == t2.getCharPositionInLine() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "shiftOp", " $t1.getLine() == $t2.getLine() && \n          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() ");
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 95, shiftOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "shiftOp"

    public static class additiveExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "additiveExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:575:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
    public final ActorFoundryParser.additiveExpression_return additiveExpression() throws RecognitionException {
        ActorFoundryParser.additiveExpression_return retval = new ActorFoundryParser.additiveExpression_return();
        retval.start = input.LT(1);
        int additiveExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 96) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:576:5: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:576:9: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3928);
            multiplicativeExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:576:34: ( ( '+' | '-' ) multiplicativeExpression )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( ((LA101_0>=98 && LA101_0<=99)) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:576:36: ( '+' | '-' ) multiplicativeExpression
            	    {
            	    if ( (input.LA(1)>=98 && input.LA(1)<=99) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3940);
            	    multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop101;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 96, additiveExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "additiveExpression"

    public static class multiplicativeExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "multiplicativeExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:579:1: multiplicativeExpression : unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* ;
    public final ActorFoundryParser.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        ActorFoundryParser.multiplicativeExpression_return retval = new ActorFoundryParser.multiplicativeExpression_return();
        retval.start = input.LT(1);
        int multiplicativeExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 97) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:580:5: ( unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:580:9: unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3962);
            unaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:580:25: ( ( '*' | '/' | '%' ) unaryExpression )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( (LA102_0==30||(LA102_0>=100 && LA102_0<=101)) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:580:27: ( '*' | '/' | '%' ) unaryExpression
            	    {
            	    if ( input.LA(1)==30||(input.LA(1)>=100 && input.LA(1)<=101) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3980);
            	    unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop102;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 97, multiplicativeExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiplicativeExpression"

    public static class unaryExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "unaryExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:583:1: unaryExpression : ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus );
    public final ActorFoundryParser.unaryExpression_return unaryExpression() throws RecognitionException {
        ActorFoundryParser.unaryExpression_return retval = new ActorFoundryParser.unaryExpression_return();
        retval.start = input.LT(1);
        int unaryExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 98) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:584:5: ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus )
            int alt103=5;
            switch ( input.LA(1) ) {
            case 98:
                {
                alt103=1;
                }
                break;
            case 99:
                {
                alt103=2;
                }
                break;
            case 102:
                {
                alt103=3;
                }
                break;
            case 103:
                {
                alt103=4;
                }
                break;
            case Identifier:
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 39:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 59:
            case 62:
            case 63:
            case 64:
            case 65:
            case 104:
            case 105:
                {
                alt103=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }

            switch (alt103) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:584:9: '+' unaryExpression
                    {
                    match(input,98,FOLLOW_98_in_unaryExpression4006); if (state.failed) return retval;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression4008);
                    unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:585:9: '-' unaryExpression
                    {
                    match(input,99,FOLLOW_99_in_unaryExpression4018); if (state.failed) return retval;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression4020);
                    unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:586:9: '++' unaryExpression
                    {
                    match(input,102,FOLLOW_102_in_unaryExpression4030); if (state.failed) return retval;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression4032);
                    unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:587:9: '--' unaryExpression
                    {
                    match(input,103,FOLLOW_103_in_unaryExpression4042); if (state.failed) return retval;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression4044);
                    unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:588:9: unaryExpressionNotPlusMinus
                    {
                    pushFollow(FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression4054);
                    unaryExpressionNotPlusMinus();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 98, unaryExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unaryExpression"

    public static class unaryExpressionNotPlusMinus_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "unaryExpressionNotPlusMinus"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:591:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );
    public final ActorFoundryParser.unaryExpressionNotPlusMinus_return unaryExpressionNotPlusMinus() throws RecognitionException {
        ActorFoundryParser.unaryExpressionNotPlusMinus_return retval = new ActorFoundryParser.unaryExpressionNotPlusMinus_return();
        retval.start = input.LT(1);
        int unaryExpressionNotPlusMinus_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 99) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:592:5: ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? )
            int alt106=4;
            switch ( input.LA(1) ) {
            case 59:
                {
                int LA106_1 = input.LA(2);

                if ( (synpred169_ActorFoundry()) ) {
                    alt106=3;
                }
                else if ( (true) ) {
                    alt106=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 106, 1, input);

                    throw nvae;
                }
                }
                break;
            case Identifier:
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 39:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 62:
            case 63:
            case 64:
            case 65:
                {
                alt106=4;
                }
                break;
            case 104:
                {
                alt106=1;
                }
                break;
            case 105:
                {
                alt106=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 106, 0, input);

                throw nvae;
            }

            switch (alt106) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:592:9: '~' unaryExpression
                    {
                    match(input,104,FOLLOW_104_in_unaryExpressionNotPlusMinus4073); if (state.failed) return retval;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus4075);
                    unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:593:9: '!' unaryExpression
                    {
                    match(input,105,FOLLOW_105_in_unaryExpressionNotPlusMinus4085); if (state.failed) return retval;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus4087);
                    unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:594:9: castExpression
                    {
                    pushFollow(FOLLOW_castExpression_in_unaryExpressionNotPlusMinus4097);
                    castExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:595:9: primary ( selector )* ( '++' | '--' )?
                    {
                    pushFollow(FOLLOW_primary_in_unaryExpressionNotPlusMinus4107);
                    primary();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:595:17: ( selector )*
                    loop104:
                    do {
                        int alt104=2;
                        int LA104_0 = input.LA(1);

                        if ( (LA104_0==29||LA104_0==41) ) {
                            alt104=1;
                        }


                        switch (alt104) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: selector
                    	    {
                    	    pushFollow(FOLLOW_selector_in_unaryExpressionNotPlusMinus4109);
                    	    selector();

                    	    state._fsp--;
                    	    if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop104;
                        }
                    } while (true);

                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:595:27: ( '++' | '--' )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( ((LA105_0>=102 && LA105_0<=103)) ) {
                        alt105=1;
                    }
                    switch (alt105) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:
                            {
                            if ( (input.LA(1)>=102 && input.LA(1)<=103) ) {
                                input.consume();
                                state.errorRecovery=false;state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 99, unaryExpressionNotPlusMinus_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unaryExpressionNotPlusMinus"

    public static class castExpression_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "castExpression"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:598:1: castExpression : ( '(' primitiveType ')' unaryExpression | '(' ( type | expression ) ')' unaryExpressionNotPlusMinus );
    public final ActorFoundryParser.castExpression_return castExpression() throws RecognitionException {
        ActorFoundryParser.castExpression_return retval = new ActorFoundryParser.castExpression_return();
        retval.start = input.LT(1);
        int castExpression_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 100) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:599:5: ( '(' primitiveType ')' unaryExpression | '(' ( type | expression ) ')' unaryExpressionNotPlusMinus )
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==59) ) {
                int LA108_1 = input.LA(2);

                if ( (synpred173_ActorFoundry()) ) {
                    alt108=1;
                }
                else if ( (true) ) {
                    alt108=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 108, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }
            switch (alt108) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:599:8: '(' primitiveType ')' unaryExpression
                    {
                    match(input,59,FOLLOW_59_in_castExpression4135); if (state.failed) return retval;
                    pushFollow(FOLLOW_primitiveType_in_castExpression4137);
                    primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,60,FOLLOW_60_in_castExpression4139); if (state.failed) return retval;
                    pushFollow(FOLLOW_unaryExpression_in_castExpression4141);
                    unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:600:8: '(' ( type | expression ) ')' unaryExpressionNotPlusMinus
                    {
                    match(input,59,FOLLOW_59_in_castExpression4150); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:600:12: ( type | expression )
                    int alt107=2;
                    alt107 = dfa107.predict(input);
                    switch (alt107) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:600:13: type
                            {
                            pushFollow(FOLLOW_type_in_castExpression4153);
                            type();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;
                        case 2 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:600:20: expression
                            {
                            pushFollow(FOLLOW_expression_in_castExpression4157);
                            expression();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }

                    match(input,60,FOLLOW_60_in_castExpression4160); if (state.failed) return retval;
                    pushFollow(FOLLOW_unaryExpressionNotPlusMinus_in_castExpression4162);
                    unaryExpressionNotPlusMinus();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 100, castExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "castExpression"

    public static class primary_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "primary"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:603:1: primary : ( parExpression | 'this' ( '.' Identifier )* ( identifierSuffix )? | 'super' superSuffix | literal | Identifier ( '.' Identifier )* ( identifierSuffix )? | primitiveType ( '[' ']' )* '.' 'class' | 'void' '.' 'class' );
    public final ActorFoundryParser.primary_return primary() throws RecognitionException {
        ActorFoundryParser.primary_return retval = new ActorFoundryParser.primary_return();
        retval.start = input.LT(1);
        int primary_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 101) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:604:5: ( parExpression | 'this' ( '.' Identifier )* ( identifierSuffix )? | 'super' superSuffix | literal | Identifier ( '.' Identifier )* ( identifierSuffix )? | primitiveType ( '[' ']' )* '.' 'class' | 'void' '.' 'class' )
            int alt114=7;
            switch ( input.LA(1) ) {
            case 59:
                {
                alt114=1;
                }
                break;
            case 62:
                {
                alt114=2;
                }
                break;
            case 58:
                {
                alt114=3;
                }
                break;
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 63:
            case 64:
            case 65:
                {
                alt114=4;
                }
                break;
            case Identifier:
                {
                alt114=5;
                }
                break;
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
                {
                alt114=6;
                }
                break;
            case 39:
                {
                alt114=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 114, 0, input);

                throw nvae;
            }

            switch (alt114) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:604:9: parExpression
                    {
                    pushFollow(FOLLOW_parExpression_in_primary4181);
                    parExpression();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:605:9: 'this' ( '.' Identifier )* ( identifierSuffix )?
                    {
                    match(input,62,FOLLOW_62_in_primary4191); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:605:16: ( '.' Identifier )*
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( (LA109_0==29) ) {
                            int LA109_2 = input.LA(2);

                            if ( (LA109_2==Identifier) ) {
                                int LA109_3 = input.LA(3);

                                if ( (synpred176_ActorFoundry()) ) {
                                    alt109=1;
                                }


                            }


                        }


                        switch (alt109) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:605:17: '.' Identifier
                    	    {
                    	    match(input,29,FOLLOW_29_in_primary4194); if (state.failed) return retval;
                    	    match(input,Identifier,FOLLOW_Identifier_in_primary4196); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop109;
                        }
                    } while (true);

                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:605:34: ( identifierSuffix )?
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==29||LA110_0==41) ) {
                        int LA110_1 = input.LA(2);

                        if ( (synpred177_ActorFoundry()) ) {
                            alt110=1;
                        }
                    }
                    else if ( (LA110_0==59) ) {
                        alt110=1;
                    }
                    switch (alt110) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: identifierSuffix
                            {
                            pushFollow(FOLLOW_identifierSuffix_in_primary4200);
                            identifierSuffix();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:606:9: 'super' superSuffix
                    {
                    match(input,58,FOLLOW_58_in_primary4211); if (state.failed) return retval;
                    pushFollow(FOLLOW_superSuffix_in_primary4213);
                    superSuffix();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:607:9: literal
                    {
                    pushFollow(FOLLOW_literal_in_primary4223);
                    literal();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:608:9: Identifier ( '.' Identifier )* ( identifierSuffix )?
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_primary4233); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:608:20: ( '.' Identifier )*
                    loop111:
                    do {
                        int alt111=2;
                        int LA111_0 = input.LA(1);

                        if ( (LA111_0==29) ) {
                            int LA111_2 = input.LA(2);

                            if ( (LA111_2==Identifier) ) {
                                int LA111_3 = input.LA(3);

                                if ( (synpred181_ActorFoundry()) ) {
                                    alt111=1;
                                }


                            }


                        }


                        switch (alt111) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:608:21: '.' Identifier
                    	    {
                    	    match(input,29,FOLLOW_29_in_primary4236); if (state.failed) return retval;
                    	    match(input,Identifier,FOLLOW_Identifier_in_primary4238); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop111;
                        }
                    } while (true);

                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:608:38: ( identifierSuffix )?
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==29||LA112_0==41) ) {
                        int LA112_2 = input.LA(2);

                        if ( (synpred182_ActorFoundry()) ) {
                            alt112=1;
                        }
                    }
                    else if ( (LA112_0==59) ) {
                        alt112=1;
                    }
                    switch (alt112) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: identifierSuffix
                            {
                            pushFollow(FOLLOW_identifierSuffix_in_primary4242);
                            identifierSuffix();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:609:9: primitiveType ( '[' ']' )* '.' 'class'
                    {
                    pushFollow(FOLLOW_primitiveType_in_primary4253);
                    primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:609:23: ( '[' ']' )*
                    loop113:
                    do {
                        int alt113=2;
                        int LA113_0 = input.LA(1);

                        if ( (LA113_0==41) ) {
                            alt113=1;
                        }


                        switch (alt113) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:609:24: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_primary4256); if (state.failed) return retval;
                    	    match(input,42,FOLLOW_42_in_primary4258); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop113;
                        }
                    } while (true);

                    match(input,29,FOLLOW_29_in_primary4262); if (state.failed) return retval;
                    match(input,106,FOLLOW_106_in_primary4264); if (state.failed) return retval;

                    }
                    break;
                case 7 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:610:9: 'void' '.' 'class'
                    {
                    match(input,39,FOLLOW_39_in_primary4274); if (state.failed) return retval;
                    match(input,29,FOLLOW_29_in_primary4276); if (state.failed) return retval;
                    match(input,106,FOLLOW_106_in_primary4278); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 101, primary_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "primary"

    public static class identifierSuffix_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "identifierSuffix"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:613:1: identifierSuffix : ( ( '[' ']' )+ '.' 'class' | ( '[' expression ']' )+ | arguments | '.' 'class' | '.' explicitGenericInvocation | '.' 'this' | '.' 'super' arguments );
    public final ActorFoundryParser.identifierSuffix_return identifierSuffix() throws RecognitionException {
        ActorFoundryParser.identifierSuffix_return retval = new ActorFoundryParser.identifierSuffix_return();
        retval.start = input.LT(1);
        int identifierSuffix_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 102) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:614:5: ( ( '[' ']' )+ '.' 'class' | ( '[' expression ']' )+ | arguments | '.' 'class' | '.' explicitGenericInvocation | '.' 'this' | '.' 'super' arguments )
            int alt117=7;
            alt117 = dfa117.predict(input);
            switch (alt117) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:614:9: ( '[' ']' )+ '.' 'class'
                    {
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:614:9: ( '[' ']' )+
                    int cnt115=0;
                    loop115:
                    do {
                        int alt115=2;
                        int LA115_0 = input.LA(1);

                        if ( (LA115_0==41) ) {
                            alt115=1;
                        }


                        switch (alt115) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:614:10: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_identifierSuffix4298); if (state.failed) return retval;
                    	    match(input,42,FOLLOW_42_in_identifierSuffix4300); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt115 >= 1 ) break loop115;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(115, input);
                                throw eee;
                        }
                        cnt115++;
                    } while (true);

                    match(input,29,FOLLOW_29_in_identifierSuffix4304); if (state.failed) return retval;
                    match(input,106,FOLLOW_106_in_identifierSuffix4306); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:615:9: ( '[' expression ']' )+
                    {
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:615:9: ( '[' expression ']' )+
                    int cnt116=0;
                    loop116:
                    do {
                        int alt116=2;
                        int LA116_0 = input.LA(1);

                        if ( (LA116_0==41) ) {
                            int LA116_2 = input.LA(2);

                            if ( (synpred188_ActorFoundry()) ) {
                                alt116=1;
                            }


                        }


                        switch (alt116) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:615:10: '[' expression ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_identifierSuffix4317); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_expression_in_identifierSuffix4319);
                    	    expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    match(input,42,FOLLOW_42_in_identifierSuffix4321); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt116 >= 1 ) break loop116;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(116, input);
                                throw eee;
                        }
                        cnt116++;
                    } while (true);


                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:616:9: arguments
                    {
                    pushFollow(FOLLOW_arguments_in_identifierSuffix4334);
                    arguments();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:617:9: '.' 'class'
                    {
                    match(input,29,FOLLOW_29_in_identifierSuffix4344); if (state.failed) return retval;
                    match(input,106,FOLLOW_106_in_identifierSuffix4346); if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:618:9: '.' explicitGenericInvocation
                    {
                    match(input,29,FOLLOW_29_in_identifierSuffix4356); if (state.failed) return retval;
                    pushFollow(FOLLOW_explicitGenericInvocation_in_identifierSuffix4358);
                    explicitGenericInvocation();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 6 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:619:9: '.' 'this'
                    {
                    match(input,29,FOLLOW_29_in_identifierSuffix4368); if (state.failed) return retval;
                    match(input,62,FOLLOW_62_in_identifierSuffix4370); if (state.failed) return retval;

                    }
                    break;
                case 7 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:620:9: '.' 'super' arguments
                    {
                    match(input,29,FOLLOW_29_in_identifierSuffix4380); if (state.failed) return retval;
                    match(input,58,FOLLOW_58_in_identifierSuffix4382); if (state.failed) return retval;
                    pushFollow(FOLLOW_arguments_in_identifierSuffix4384);
                    arguments();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 102, identifierSuffix_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "identifierSuffix"

    public static class arrayCreatorRest_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "arrayCreatorRest"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:624:1: arrayCreatorRest : '[' ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* ) ;
    public final ActorFoundryParser.arrayCreatorRest_return arrayCreatorRest() throws RecognitionException {
        ActorFoundryParser.arrayCreatorRest_return retval = new ActorFoundryParser.arrayCreatorRest_return();
        retval.start = input.LT(1);
        int arrayCreatorRest_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 103) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:625:5: ( '[' ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* ) )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:625:9: '[' ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* )
            {
            match(input,41,FOLLOW_41_in_arrayCreatorRest4404); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:626:9: ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* )
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==42) ) {
                alt121=1;
            }
            else if ( ((LA121_0>=Identifier && LA121_0<=DecimalLiteral)||LA121_0==39||(LA121_0>=49 && LA121_0<=56)||(LA121_0>=58 && LA121_0<=59)||(LA121_0>=62 && LA121_0<=65)||(LA121_0>=98 && LA121_0<=99)||(LA121_0>=102 && LA121_0<=105)) ) {
                alt121=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 121, 0, input);

                throw nvae;
            }
            switch (alt121) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:626:13: ']' ( '[' ']' )* arrayInitializer
                    {
                    match(input,42,FOLLOW_42_in_arrayCreatorRest4418); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:626:17: ( '[' ']' )*
                    loop118:
                    do {
                        int alt118=2;
                        int LA118_0 = input.LA(1);

                        if ( (LA118_0==41) ) {
                            alt118=1;
                        }


                        switch (alt118) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:626:18: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_arrayCreatorRest4421); if (state.failed) return retval;
                    	    match(input,42,FOLLOW_42_in_arrayCreatorRest4423); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop118;
                        }
                    } while (true);

                    pushFollow(FOLLOW_arrayInitializer_in_arrayCreatorRest4427);
                    arrayInitializer();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:627:13: expression ']' ( '[' expression ']' )* ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_expression_in_arrayCreatorRest4441);
                    expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,42,FOLLOW_42_in_arrayCreatorRest4443); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:627:28: ( '[' expression ']' )*
                    loop119:
                    do {
                        int alt119=2;
                        int LA119_0 = input.LA(1);

                        if ( (LA119_0==41) ) {
                            int LA119_1 = input.LA(2);

                            if ( ((LA119_1>=Identifier && LA119_1<=DecimalLiteral)||LA119_1==39||(LA119_1>=49 && LA119_1<=56)||(LA119_1>=58 && LA119_1<=59)||(LA119_1>=62 && LA119_1<=65)||(LA119_1>=98 && LA119_1<=99)||(LA119_1>=102 && LA119_1<=105)) ) {
                                alt119=1;
                            }


                        }


                        switch (alt119) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:627:29: '[' expression ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_arrayCreatorRest4446); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_expression_in_arrayCreatorRest4448);
                    	    expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    match(input,42,FOLLOW_42_in_arrayCreatorRest4450); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop119;
                        }
                    } while (true);

                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:627:50: ( '[' ']' )*
                    loop120:
                    do {
                        int alt120=2;
                        int LA120_0 = input.LA(1);

                        if ( (LA120_0==41) ) {
                            alt120=1;
                        }


                        switch (alt120) {
                    	case 1 :
                    	    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:627:51: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_arrayCreatorRest4455); if (state.failed) return retval;
                    	    match(input,42,FOLLOW_42_in_arrayCreatorRest4457); if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop120;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 103, arrayCreatorRest_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "arrayCreatorRest"

    public static class explicitGenericInvocation_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "explicitGenericInvocation"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:631:1: explicitGenericInvocation : nonWildcardTypeArguments Identifier arguments ;
    public final ActorFoundryParser.explicitGenericInvocation_return explicitGenericInvocation() throws RecognitionException {
        ActorFoundryParser.explicitGenericInvocation_return retval = new ActorFoundryParser.explicitGenericInvocation_return();
        retval.start = input.LT(1);
        int explicitGenericInvocation_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 104) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:632:5: ( nonWildcardTypeArguments Identifier arguments )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:632:9: nonWildcardTypeArguments Identifier arguments
            {
            pushFollow(FOLLOW_nonWildcardTypeArguments_in_explicitGenericInvocation4496);
            nonWildcardTypeArguments();

            state._fsp--;
            if (state.failed) return retval;
            match(input,Identifier,FOLLOW_Identifier_in_explicitGenericInvocation4498); if (state.failed) return retval;
            pushFollow(FOLLOW_arguments_in_explicitGenericInvocation4500);
            arguments();

            state._fsp--;
            if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 104, explicitGenericInvocation_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "explicitGenericInvocation"

    public static class nonWildcardTypeArguments_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "nonWildcardTypeArguments"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:635:1: nonWildcardTypeArguments : '<' typeList '>' ;
    public final ActorFoundryParser.nonWildcardTypeArguments_return nonWildcardTypeArguments() throws RecognitionException {
        ActorFoundryParser.nonWildcardTypeArguments_return retval = new ActorFoundryParser.nonWildcardTypeArguments_return();
        retval.start = input.LT(1);
        int nonWildcardTypeArguments_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 105) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:636:5: ( '<' typeList '>' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:636:9: '<' typeList '>'
            {
            match(input,32,FOLLOW_32_in_nonWildcardTypeArguments4523); if (state.failed) return retval;
            pushFollow(FOLLOW_typeList_in_nonWildcardTypeArguments4525);
            typeList();

            state._fsp--;
            if (state.failed) return retval;
            match(input,34,FOLLOW_34_in_nonWildcardTypeArguments4527); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 105, nonWildcardTypeArguments_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "nonWildcardTypeArguments"

    public static class selector_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "selector"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:639:1: selector : ( '.' Identifier ( arguments )? | '.' 'this' | '.' 'super' superSuffix | '[' expression ']' );
    public final ActorFoundryParser.selector_return selector() throws RecognitionException {
        ActorFoundryParser.selector_return retval = new ActorFoundryParser.selector_return();
        retval.start = input.LT(1);
        int selector_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 106) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:640:5: ( '.' Identifier ( arguments )? | '.' 'this' | '.' 'super' superSuffix | '[' expression ']' )
            int alt123=4;
            int LA123_0 = input.LA(1);

            if ( (LA123_0==29) ) {
                switch ( input.LA(2) ) {
                case Identifier:
                    {
                    alt123=1;
                    }
                    break;
                case 62:
                    {
                    alt123=2;
                    }
                    break;
                case 58:
                    {
                    alt123=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 123, 1, input);

                    throw nvae;
                }

            }
            else if ( (LA123_0==41) ) {
                alt123=4;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 123, 0, input);

                throw nvae;
            }
            switch (alt123) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:640:9: '.' Identifier ( arguments )?
                    {
                    match(input,29,FOLLOW_29_in_selector4550); if (state.failed) return retval;
                    match(input,Identifier,FOLLOW_Identifier_in_selector4552); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:640:24: ( arguments )?
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( (LA122_0==59) ) {
                        alt122=1;
                    }
                    switch (alt122) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: arguments
                            {
                            pushFollow(FOLLOW_arguments_in_selector4554);
                            arguments();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:641:9: '.' 'this'
                    {
                    match(input,29,FOLLOW_29_in_selector4565); if (state.failed) return retval;
                    match(input,62,FOLLOW_62_in_selector4567); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:642:9: '.' 'super' superSuffix
                    {
                    match(input,29,FOLLOW_29_in_selector4577); if (state.failed) return retval;
                    match(input,58,FOLLOW_58_in_selector4579); if (state.failed) return retval;
                    pushFollow(FOLLOW_superSuffix_in_selector4581);
                    superSuffix();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:643:9: '[' expression ']'
                    {
                    match(input,41,FOLLOW_41_in_selector4591); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_selector4593);
                    expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,42,FOLLOW_42_in_selector4595); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 106, selector_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "selector"

    public static class superSuffix_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "superSuffix"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:646:1: superSuffix : ( arguments | '.' Identifier ( arguments )? );
    public final ActorFoundryParser.superSuffix_return superSuffix() throws RecognitionException {
        ActorFoundryParser.superSuffix_return retval = new ActorFoundryParser.superSuffix_return();
        retval.start = input.LT(1);
        int superSuffix_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 107) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:647:5: ( arguments | '.' Identifier ( arguments )? )
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==59) ) {
                alt125=1;
            }
            else if ( (LA125_0==29) ) {
                alt125=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 125, 0, input);

                throw nvae;
            }
            switch (alt125) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:647:9: arguments
                    {
                    pushFollow(FOLLOW_arguments_in_superSuffix4618);
                    arguments();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:648:9: '.' Identifier ( arguments )?
                    {
                    match(input,29,FOLLOW_29_in_superSuffix4628); if (state.failed) return retval;
                    match(input,Identifier,FOLLOW_Identifier_in_superSuffix4630); if (state.failed) return retval;
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:648:24: ( arguments )?
                    int alt124=2;
                    int LA124_0 = input.LA(1);

                    if ( (LA124_0==59) ) {
                        alt124=1;
                    }
                    switch (alt124) {
                        case 1 :
                            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: arguments
                            {
                            pushFollow(FOLLOW_arguments_in_superSuffix4632);
                            arguments();

                            state._fsp--;
                            if (state.failed) return retval;

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 107, superSuffix_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "superSuffix"

    public static class arguments_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "arguments"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:651:1: arguments : '(' ( expressionList )? ')' ;
    public final ActorFoundryParser.arguments_return arguments() throws RecognitionException {
        ActorFoundryParser.arguments_return retval = new ActorFoundryParser.arguments_return();
        retval.start = input.LT(1);
        int arguments_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 108) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:652:5: ( '(' ( expressionList )? ')' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:652:9: '(' ( expressionList )? ')'
            {
            match(input,59,FOLLOW_59_in_arguments4652); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:652:13: ( expressionList )?
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( ((LA126_0>=Identifier && LA126_0<=DecimalLiteral)||LA126_0==39||(LA126_0>=49 && LA126_0<=56)||(LA126_0>=58 && LA126_0<=59)||(LA126_0>=62 && LA126_0<=65)||(LA126_0>=98 && LA126_0<=99)||(LA126_0>=102 && LA126_0<=105)) ) {
                alt126=1;
            }
            switch (alt126) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_arguments4654);
                    expressionList();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }

            match(input,60,FOLLOW_60_in_arguments4657); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 108, arguments_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "arguments"

    protected static class messageSend_scope {
        String actorName;
        String messageName;
        String arguments;
    }
    protected Stack messageSend_stack = new Stack();

    public static class messageSend_return extends ParserRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "messageSend"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:655:1: messageSend : (act= Identifier '<-' msg= Identifier messageArgs -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"send(<actor_name>, \"<msg_name>\"<args>)\" | act= Identifier '<-&' msg= Identifier messageArgs -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"sendByRef(<actor_name>, \"<msg_name>\"<args>)\" | act= Identifier '<->' msg= Identifier messageArgs -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"call(<actor_name>, \"<msg_name>\"<args>)\" | act= Identifier '<->&' msg= Identifier messageArgs -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"callByRef(<actor_name>, \"<msg_name>\"<args>)\");
    public final ActorFoundryParser.messageSend_return messageSend() throws RecognitionException {
        messageSend_stack.push(new messageSend_scope());
        ActorFoundryParser.messageSend_return retval = new ActorFoundryParser.messageSend_return();
        retval.start = input.LT(1);
        int messageSend_StartIndex = input.index();
        Token act=null;
        Token msg=null;
        ActorFoundryParser.messageArgs_return messageArgs9 = null;

        ActorFoundryParser.messageArgs_return messageArgs10 = null;

        ActorFoundryParser.messageArgs_return messageArgs11 = null;

        ActorFoundryParser.messageArgs_return messageArgs12 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 109) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:661:5: (act= Identifier '<-' msg= Identifier messageArgs -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"send(<actor_name>, \"<msg_name>\"<args>)\" | act= Identifier '<-&' msg= Identifier messageArgs -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"sendByRef(<actor_name>, \"<msg_name>\"<args>)\" | act= Identifier '<->' msg= Identifier messageArgs -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"call(<actor_name>, \"<msg_name>\"<args>)\" | act= Identifier '<->&' msg= Identifier messageArgs -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"callByRef(<actor_name>, \"<msg_name>\"<args>)\")
            int alt127=4;
            int LA127_0 = input.LA(1);

            if ( (LA127_0==Identifier) ) {
                switch ( input.LA(2) ) {
                case 108:
                    {
                    alt127=2;
                    }
                    break;
                case 107:
                    {
                    alt127=1;
                    }
                    break;
                case 109:
                    {
                    alt127=3;
                    }
                    break;
                case 110:
                    {
                    alt127=4;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 127, 1, input);

                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 127, 0, input);

                throw nvae;
            }
            switch (alt127) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:661:9: act= Identifier '<-' msg= Identifier messageArgs
                    {
                    act=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageSend4682); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).actorName =(act!=null?act.getText():null);
                    }
                    match(input,107,FOLLOW_107_in_messageSend4686); if (state.failed) return retval;
                    msg=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageSend4699); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).messageName =(msg!=null?msg.getText():null);
                    }
                    pushFollow(FOLLOW_messageArgs_in_messageSend4712);
                    messageArgs9=messageArgs();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).arguments =(messageArgs9!=null?messageArgs9.args:null);
                    }


                    // TEMPLATE REWRITE
                    if ( state.backtracking==0 ) {
                      // 663:66: -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"send(<actor_name>, \"<msg_name>\"<args>)\"
                      {
                          retval.st = new StringTemplate(templateLib, "send(<actor_name>, \"<msg_name>\"<args>)",
                        new STAttrMap().put("actor_name", ((messageSend_scope)messageSend_stack.peek()).actorName).put("msg_name", ((messageSend_scope)messageSend_stack.peek()).messageName).put("args", ((messageSend_scope)messageSend_stack.peek()).arguments));
                      }

                      ((TokenRewriteStream)input).replace(
                        ((Token)retval.start).getTokenIndex(),
                        input.LT(-1).getTokenIndex(),
                        retval.st);
                    }
                    }
                    break;
                case 2 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:666:9: act= Identifier '<-&' msg= Identifier messageArgs
                    {
                    act=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageSend4765); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).actorName =(act!=null?act.getText():null);
                    }
                    match(input,108,FOLLOW_108_in_messageSend4769); if (state.failed) return retval;
                    msg=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageSend4782); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).messageName =(msg!=null?msg.getText():null);
                    }
                    pushFollow(FOLLOW_messageArgs_in_messageSend4795);
                    messageArgs10=messageArgs();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).arguments =(messageArgs10!=null?messageArgs10.args:null);
                    }


                    // TEMPLATE REWRITE
                    if ( state.backtracking==0 ) {
                      // 668:66: -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"sendByRef(<actor_name>, \"<msg_name>\"<args>)\"
                      {
                          retval.st = new StringTemplate(templateLib, "sendByRef(<actor_name>, \"<msg_name>\"<args>)",
                        new STAttrMap().put("actor_name", ((messageSend_scope)messageSend_stack.peek()).actorName).put("msg_name", ((messageSend_scope)messageSend_stack.peek()).messageName).put("args", ((messageSend_scope)messageSend_stack.peek()).arguments));
                      }

                      ((TokenRewriteStream)input).replace(
                        ((Token)retval.start).getTokenIndex(),
                        input.LT(-1).getTokenIndex(),
                        retval.st);
                    }
                    }
                    break;
                case 3 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:671:9: act= Identifier '<->' msg= Identifier messageArgs
                    {
                    act=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageSend4848); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).actorName =(act!=null?act.getText():null);
                    }
                    match(input,109,FOLLOW_109_in_messageSend4852); if (state.failed) return retval;
                    msg=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageSend4865); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).messageName =(msg!=null?msg.getText():null);
                    }
                    pushFollow(FOLLOW_messageArgs_in_messageSend4878);
                    messageArgs11=messageArgs();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).arguments =(messageArgs11!=null?messageArgs11.args:null);
                    }


                    // TEMPLATE REWRITE
                    if ( state.backtracking==0 ) {
                      // 673:66: -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"call(<actor_name>, \"<msg_name>\"<args>)\"
                      {
                          retval.st = new StringTemplate(templateLib, "call(<actor_name>, \"<msg_name>\"<args>)",
                        new STAttrMap().put("actor_name", ((messageSend_scope)messageSend_stack.peek()).actorName).put("msg_name", ((messageSend_scope)messageSend_stack.peek()).messageName).put("args", ((messageSend_scope)messageSend_stack.peek()).arguments));
                      }

                      ((TokenRewriteStream)input).replace(
                        ((Token)retval.start).getTokenIndex(),
                        input.LT(-1).getTokenIndex(),
                        retval.st);
                    }
                    }
                    break;
                case 4 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:676:9: act= Identifier '<->&' msg= Identifier messageArgs
                    {
                    act=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageSend4931); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).actorName =(act!=null?act.getText():null);
                    }
                    match(input,110,FOLLOW_110_in_messageSend4935); if (state.failed) return retval;
                    msg=(Token)match(input,Identifier,FOLLOW_Identifier_in_messageSend4948); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).messageName =(msg!=null?msg.getText():null);
                    }
                    pushFollow(FOLLOW_messageArgs_in_messageSend4961);
                    messageArgs12=messageArgs();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                      ((messageSend_scope)messageSend_stack.peek()).arguments =(messageArgs12!=null?messageArgs12.args:null);
                    }


                    // TEMPLATE REWRITE
                    if ( state.backtracking==0 ) {
                      // 678:66: -> template(actor_name=$messageSend::actorNamemsg_name=$messageSend::messageNameargs=$messageSend::arguments) \"callByRef(<actor_name>, \"<msg_name>\"<args>)\"
                      {
                          retval.st = new StringTemplate(templateLib, "callByRef(<actor_name>, \"<msg_name>\"<args>)",
                        new STAttrMap().put("actor_name", ((messageSend_scope)messageSend_stack.peek()).actorName).put("msg_name", ((messageSend_scope)messageSend_stack.peek()).messageName).put("args", ((messageSend_scope)messageSend_stack.peek()).arguments));
                      }

                      ((TokenRewriteStream)input).replace(
                        ((Token)retval.start).getTokenIndex(),
                        input.LT(-1).getTokenIndex(),
                        retval.st);
                    }
                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 109, messageSend_StartIndex); }
            messageSend_stack.pop();
        }
        return retval;
    }
    // $ANTLR end "messageSend"

    public static class messageArgs_return extends ParserRuleReturnScope {
        public String args;
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };

    // $ANTLR start "messageArgs"
    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:683:1: messageArgs returns [String args] : '(' ( expressionList )? ')' ;
    public final ActorFoundryParser.messageArgs_return messageArgs() throws RecognitionException {
        ActorFoundryParser.messageArgs_return retval = new ActorFoundryParser.messageArgs_return();
        retval.start = input.LT(1);
        int messageArgs_StartIndex = input.index();
        ActorFoundryParser.expressionList_return expressionList13 = null;



          retval.args = "";

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 110) ) { return retval; }
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:687:5: ( '(' ( expressionList )? ')' )
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:688:9: '(' ( expressionList )? ')'
            {
            match(input,59,FOLLOW_59_in_messageArgs5036); if (state.failed) return retval;
            // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:688:13: ( expressionList )?
            int alt128=2;
            int LA128_0 = input.LA(1);

            if ( ((LA128_0>=Identifier && LA128_0<=DecimalLiteral)||LA128_0==39||(LA128_0>=49 && LA128_0<=56)||(LA128_0>=58 && LA128_0<=59)||(LA128_0>=62 && LA128_0<=65)||(LA128_0>=98 && LA128_0<=99)||(LA128_0>=102 && LA128_0<=105)) ) {
                alt128=1;
            }
            switch (alt128) {
                case 1 :
                    // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_messageArgs5038);
                    expressionList13=expressionList();

                    state._fsp--;
                    if (state.failed) return retval;

                    }
                    break;

            }

            match(input,60,FOLLOW_60_in_messageArgs5041); if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                        if ((expressionList13!=null?input.toString(expressionList13.start,expressionList13.stop):null) != null) {
                          retval.args = ", "+(expressionList13!=null?input.toString(expressionList13.start,expressionList13.stop):null);
                        }
                      
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 110, messageArgs_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "messageArgs"

    // $ANTLR start synpred65_ActorFoundry
    public final void synpred65_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:294:13: ( explicitConstructorInvocation )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:294:13: explicitConstructorInvocation
        {
        pushFollow(FOLLOW_explicitConstructorInvocation_in_synpred65_ActorFoundry1804);
        explicitConstructorInvocation();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred65_ActorFoundry

    // $ANTLR start synpred69_ActorFoundry
    public final void synpred69_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:298:9: ( ( nonWildcardTypeArguments )? ( 'this' | 'super' ) arguments ';' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:298:9: ( nonWildcardTypeArguments )? ( 'this' | 'super' ) arguments ';'
        {
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:298:9: ( nonWildcardTypeArguments )?
        int alt138=2;
        int LA138_0 = input.LA(1);

        if ( (LA138_0==32) ) {
            alt138=1;
        }
        switch (alt138) {
            case 1 :
                // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:0:0: nonWildcardTypeArguments
                {
                pushFollow(FOLLOW_nonWildcardTypeArguments_in_synpred69_ActorFoundry1829);
                nonWildcardTypeArguments();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        if ( input.LA(1)==58||input.LA(1)==62 ) {
            input.consume();
            state.errorRecovery=false;state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }

        pushFollow(FOLLOW_arguments_in_synpred69_ActorFoundry1840);
        arguments();

        state._fsp--;
        if (state.failed) return ;
        match(input,26,FOLLOW_26_in_synpred69_ActorFoundry1842); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred69_ActorFoundry

    // $ANTLR start synpred92_ActorFoundry
    public final void synpred92_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:370:9: ( localVariableDeclarationStatement )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:370:9: localVariableDeclarationStatement
        {
        pushFollow(FOLLOW_localVariableDeclarationStatement_in_synpred92_ActorFoundry2335);
        localVariableDeclarationStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred92_ActorFoundry

    // $ANTLR start synpred97_ActorFoundry
    public final void synpred97_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:389:54: ( 'else' statement )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:389:54: 'else' statement
        {
        match(input,70,FOLLOW_70_in_synpred97_ActorFoundry2480); if (state.failed) return ;
        pushFollow(FOLLOW_statement_in_synpred97_ActorFoundry2482);
        statement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred97_ActorFoundry

    // $ANTLR start synpred102_ActorFoundry
    public final void synpred102_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:394:11: ( catches 'finally' block )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:394:11: catches 'finally' block
        {
        pushFollow(FOLLOW_catches_in_synpred102_ActorFoundry2558);
        catches();

        state._fsp--;
        if (state.failed) return ;
        match(input,75,FOLLOW_75_in_synpred102_ActorFoundry2560); if (state.failed) return ;
        pushFollow(FOLLOW_block_in_synpred102_ActorFoundry2562);
        block();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred102_ActorFoundry

    // $ANTLR start synpred103_ActorFoundry
    public final void synpred103_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:395:11: ( catches )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:395:11: catches
        {
        pushFollow(FOLLOW_catches_in_synpred103_ActorFoundry2574);
        catches();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred103_ActorFoundry

    // $ANTLR start synpred118_ActorFoundry
    public final void synpred118_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:430:9: ( switchLabel )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:430:9: switchLabel
        {
        pushFollow(FOLLOW_switchLabel_in_synpred118_ActorFoundry2859);
        switchLabel();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred118_ActorFoundry

    // $ANTLR start synpred120_ActorFoundry
    public final void synpred120_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:434:9: ( 'case' constantExpression ':' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:434:9: 'case' constantExpression ':'
        {
        match(input,82,FOLLOW_82_in_synpred120_ActorFoundry2886); if (state.failed) return ;
        pushFollow(FOLLOW_constantExpression_in_synpred120_ActorFoundry2888);
        constantExpression();

        state._fsp--;
        if (state.failed) return ;
        match(input,68,FOLLOW_68_in_synpred120_ActorFoundry2890); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred120_ActorFoundry

    // $ANTLR start synpred121_ActorFoundry
    public final void synpred121_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:435:9: ( 'case' enumConstantName ':' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:435:9: 'case' enumConstantName ':'
        {
        match(input,82,FOLLOW_82_in_synpred121_ActorFoundry2900); if (state.failed) return ;
        pushFollow(FOLLOW_enumConstantName_in_synpred121_ActorFoundry2902);
        enumConstantName();

        state._fsp--;
        if (state.failed) return ;
        match(input,68,FOLLOW_68_in_synpred121_ActorFoundry2904); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred121_ActorFoundry

    // $ANTLR start synpred122_ActorFoundry
    public final void synpred122_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:441:9: ( enhancedForControl )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:441:9: enhancedForControl
        {
        pushFollow(FOLLOW_enhancedForControl_in_synpred122_ActorFoundry2947);
        enhancedForControl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred122_ActorFoundry

    // $ANTLR start synpred126_ActorFoundry
    public final void synpred126_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:446:9: ( localVariableDeclaration )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:446:9: localVariableDeclaration
        {
        pushFollow(FOLLOW_localVariableDeclaration_in_synpred126_ActorFoundry2987);
        localVariableDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred126_ActorFoundry

    // $ANTLR start synpred128_ActorFoundry
    public final void synpred128_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:477:32: ( assignmentOperator expression )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:477:32: assignmentOperator expression
        {
        pushFollow(FOLLOW_assignmentOperator_in_synpred128_ActorFoundry3170);
        assignmentOperator();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred128_ActorFoundry3172);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred128_ActorFoundry

    // $ANTLR start synpred138_ActorFoundry
    public final void synpred138_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:490:9: ( '<' '<' '=' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:490:10: '<' '<' '='
        {
        match(input,32,FOLLOW_32_in_synpred138_ActorFoundry3288); if (state.failed) return ;
        match(input,32,FOLLOW_32_in_synpred138_ActorFoundry3290); if (state.failed) return ;
        match(input,44,FOLLOW_44_in_synpred138_ActorFoundry3292); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred138_ActorFoundry

    // $ANTLR start synpred139_ActorFoundry
    public final void synpred139_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:495:9: ( '>' '>' '>' '=' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:495:10: '>' '>' '>' '='
        {
        match(input,34,FOLLOW_34_in_synpred139_ActorFoundry3328); if (state.failed) return ;
        match(input,34,FOLLOW_34_in_synpred139_ActorFoundry3330); if (state.failed) return ;
        match(input,34,FOLLOW_34_in_synpred139_ActorFoundry3332); if (state.failed) return ;
        match(input,44,FOLLOW_44_in_synpred139_ActorFoundry3334); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred139_ActorFoundry

    // $ANTLR start synpred140_ActorFoundry
    public final void synpred140_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:502:9: ( '>' '>' '=' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:502:10: '>' '>' '='
        {
        match(input,34,FOLLOW_34_in_synpred140_ActorFoundry3373); if (state.failed) return ;
        match(input,34,FOLLOW_34_in_synpred140_ActorFoundry3375); if (state.failed) return ;
        match(input,44,FOLLOW_44_in_synpred140_ActorFoundry3377); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred140_ActorFoundry

    // $ANTLR start synpred151_ActorFoundry
    public final void synpred151_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:546:9: ( '<' '=' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:546:10: '<' '='
        {
        match(input,32,FOLLOW_32_in_synpred151_ActorFoundry3685); if (state.failed) return ;
        match(input,44,FOLLOW_44_in_synpred151_ActorFoundry3687); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred151_ActorFoundry

    // $ANTLR start synpred152_ActorFoundry
    public final void synpred152_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:549:9: ( '>' '=' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:549:10: '>' '='
        {
        match(input,34,FOLLOW_34_in_synpred152_ActorFoundry3719); if (state.failed) return ;
        match(input,44,FOLLOW_44_in_synpred152_ActorFoundry3721); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred152_ActorFoundry

    // $ANTLR start synpred155_ActorFoundry
    public final void synpred155_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:561:9: ( '<' '<' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:561:10: '<' '<'
        {
        match(input,32,FOLLOW_32_in_synpred155_ActorFoundry3812); if (state.failed) return ;
        match(input,32,FOLLOW_32_in_synpred155_ActorFoundry3814); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred155_ActorFoundry

    // $ANTLR start synpred156_ActorFoundry
    public final void synpred156_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:564:9: ( '>' '>' '>' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:564:10: '>' '>' '>'
        {
        match(input,34,FOLLOW_34_in_synpred156_ActorFoundry3846); if (state.failed) return ;
        match(input,34,FOLLOW_34_in_synpred156_ActorFoundry3848); if (state.failed) return ;
        match(input,34,FOLLOW_34_in_synpred156_ActorFoundry3850); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred156_ActorFoundry

    // $ANTLR start synpred157_ActorFoundry
    public final void synpred157_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:569:9: ( '>' '>' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:569:10: '>' '>'
        {
        match(input,34,FOLLOW_34_in_synpred157_ActorFoundry3886); if (state.failed) return ;
        match(input,34,FOLLOW_34_in_synpred157_ActorFoundry3888); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred157_ActorFoundry

    // $ANTLR start synpred169_ActorFoundry
    public final void synpred169_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:594:9: ( castExpression )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:594:9: castExpression
        {
        pushFollow(FOLLOW_castExpression_in_synpred169_ActorFoundry4097);
        castExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred169_ActorFoundry

    // $ANTLR start synpred173_ActorFoundry
    public final void synpred173_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:599:8: ( '(' primitiveType ')' unaryExpression )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:599:8: '(' primitiveType ')' unaryExpression
        {
        match(input,59,FOLLOW_59_in_synpred173_ActorFoundry4135); if (state.failed) return ;
        pushFollow(FOLLOW_primitiveType_in_synpred173_ActorFoundry4137);
        primitiveType();

        state._fsp--;
        if (state.failed) return ;
        match(input,60,FOLLOW_60_in_synpred173_ActorFoundry4139); if (state.failed) return ;
        pushFollow(FOLLOW_unaryExpression_in_synpred173_ActorFoundry4141);
        unaryExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred173_ActorFoundry

    // $ANTLR start synpred174_ActorFoundry
    public final void synpred174_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:600:13: ( type )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:600:13: type
        {
        pushFollow(FOLLOW_type_in_synpred174_ActorFoundry4153);
        type();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred174_ActorFoundry

    // $ANTLR start synpred176_ActorFoundry
    public final void synpred176_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:605:17: ( '.' Identifier )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:605:17: '.' Identifier
        {
        match(input,29,FOLLOW_29_in_synpred176_ActorFoundry4194); if (state.failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred176_ActorFoundry4196); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred176_ActorFoundry

    // $ANTLR start synpred177_ActorFoundry
    public final void synpred177_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:605:34: ( identifierSuffix )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:605:34: identifierSuffix
        {
        pushFollow(FOLLOW_identifierSuffix_in_synpred177_ActorFoundry4200);
        identifierSuffix();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred177_ActorFoundry

    // $ANTLR start synpred181_ActorFoundry
    public final void synpred181_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:608:21: ( '.' Identifier )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:608:21: '.' Identifier
        {
        match(input,29,FOLLOW_29_in_synpred181_ActorFoundry4236); if (state.failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred181_ActorFoundry4238); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred181_ActorFoundry

    // $ANTLR start synpred182_ActorFoundry
    public final void synpred182_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:608:38: ( identifierSuffix )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:608:38: identifierSuffix
        {
        pushFollow(FOLLOW_identifierSuffix_in_synpred182_ActorFoundry4242);
        identifierSuffix();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred182_ActorFoundry

    // $ANTLR start synpred188_ActorFoundry
    public final void synpred188_ActorFoundry_fragment() throws RecognitionException {   
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:615:10: ( '[' expression ']' )
        // /home/amshali/src/workspace/foundry-local/src/osl/foundry/lang/ActorFoundry.g:615:10: '[' expression ']'
        {
        match(input,41,FOLLOW_41_in_synpred188_ActorFoundry4317); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred188_ActorFoundry4319);
        expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,42,FOLLOW_42_in_synpred188_ActorFoundry4321); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred188_ActorFoundry

    // Delegated rules

    public final boolean synpred103_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred103_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred174_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred174_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred155_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred155_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred121_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred121_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred120_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred120_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred126_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred126_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred140_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred140_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred188_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred188_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred157_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred157_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred97_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred97_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred65_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred65_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred92_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred92_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred138_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred138_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred102_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred102_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred128_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred128_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred118_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred118_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred181_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred181_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred122_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred122_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred182_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred182_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred139_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred139_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred169_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred169_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred176_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred176_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred69_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred69_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred156_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred156_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred177_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred177_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred151_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred151_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred152_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred152_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred173_ActorFoundry() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred173_ActorFoundry_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA75 dfa75 = new DFA75(this);
    protected DFA84 dfa84 = new DFA84(this);
    protected DFA87 dfa87 = new DFA87(this);
    protected DFA88 dfa88 = new DFA88(this);
    protected DFA100 dfa100 = new DFA100(this);
    protected DFA107 dfa107 = new DFA107(this);
    protected DFA117 dfa117 = new DFA117(this);
    static final String DFA75_eotS =
        "\22\uffff";
    static final String DFA75_eofS =
        "\22\uffff";
    static final String DFA75_minS =
        "\1\4\16\uffff\1\32\2\uffff";
    static final String DFA75_maxS =
        "\1\151\16\uffff\1\156\2\uffff";
    static final String DFA75_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\uffff\1\17\1\20";
    static final String DFA75_specialS =
        "\22\uffff}>";
    static final String[] DFA75_transitionS = {
            "\1\17\6\16\1\2\16\uffff\1\15\12\uffff\1\1\1\uffff\1\16\11\uffff"+
            "\10\16\1\uffff\2\16\2\uffff\4\16\3\uffff\1\3\1\uffff\1\4\1\5"+
            "\1\6\1\7\1\uffff\1\10\1\11\1\12\1\13\1\14\21\uffff\2\16\2\uffff"+
            "\4\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\16\2\uffff\2\16\1\uffff\1\16\1\uffff\1\16\1\uffff\1\16\4"+
            "\uffff\1\16\2\uffff\1\16\14\uffff\1\16\1\uffff\1\16\10\uffff"+
            "\1\20\16\uffff\25\16\3\uffff\4\21",
            "",
            ""
    };

    static final short[] DFA75_eot = DFA.unpackEncodedString(DFA75_eotS);
    static final short[] DFA75_eof = DFA.unpackEncodedString(DFA75_eofS);
    static final char[] DFA75_min = DFA.unpackEncodedStringToUnsignedChars(DFA75_minS);
    static final char[] DFA75_max = DFA.unpackEncodedStringToUnsignedChars(DFA75_maxS);
    static final short[] DFA75_accept = DFA.unpackEncodedString(DFA75_acceptS);
    static final short[] DFA75_special = DFA.unpackEncodedString(DFA75_specialS);
    static final short[][] DFA75_transition;

    static {
        int numStates = DFA75_transitionS.length;
        DFA75_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA75_transition[i] = DFA.unpackEncodedString(DFA75_transitionS[i]);
        }
    }

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = DFA75_eot;
            this.eof = DFA75_eof;
            this.min = DFA75_min;
            this.max = DFA75_max;
            this.accept = DFA75_accept;
            this.special = DFA75_special;
            this.transition = DFA75_transition;
        }
        public String getDescription() {
            return "386:1: statement : ( block | ASSERT expression ( ':' expression )? ';' | 'if' parExpression statement ( options {k=1; } : 'else' statement )? | 'for' '(' forControl ')' statement | 'while' parExpression statement | 'do' statement 'while' parExpression ';' | 'try' block ( catches 'finally' block | catches | 'finally' block ) | 'switch' parExpression '{' switchBlockStatementGroups '}' | 'return' ( expression )? ';' | 'throw' expression ';' | 'break' ( Identifier )? ';' | 'continue' ( Identifier )? ';' | ';' | statementExpression ';' | Identifier ':' statement | messageSend ';' );";
        }
    }
    static final String DFA84_eotS =
        "\u0083\uffff";
    static final String DFA84_eofS =
        "\u0083\uffff";
    static final String DFA84_minS =
        "\5\4\21\uffff\7\4\4\uffff\1\4\24\uffff\1\32\1\52\1\32\1\uffff\22"+
        "\0\4\uffff\1\0\43\uffff\2\0\1\uffff\1\0\5\uffff\1\0\5\uffff";
    static final String DFA84_maxS =
        "\1\151\1\102\1\4\1\147\1\51\21\uffff\2\51\1\102\1\4\1\102\1\152"+
        "\1\151\4\uffff\1\151\24\uffff\1\104\1\52\1\104\1\uffff\22\0\4\uffff"+
        "\1\0\43\uffff\2\0\1\uffff\1\0\5\uffff\1\0\5\uffff";
    static final String DFA84_acceptS =
        "\5\uffff\1\2\162\uffff\1\1\12\uffff";
    static final String DFA84_specialS =
        "\72\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\4\uffff\1\22\43\uffff\1\23\1\24\1\uffff"+
        "\1\25\5\uffff\1\26\5\uffff}>";
    static final String[] DFA84_transitionS = {
            "\1\3\6\5\17\uffff\1\5\14\uffff\1\5\5\uffff\1\1\3\uffff\10\4"+
            "\1\uffff\2\5\2\uffff\4\5\1\2\37\uffff\2\5\2\uffff\4\5",
            "\1\26\50\uffff\1\30\3\uffff\10\27\11\uffff\1\31",
            "\1\32",
            "\1\66\25\uffff\1\5\2\uffff\1\33\1\5\1\uffff\1\41\2\5\1\uffff"+
            "\1\5\4\uffff\1\34\2\uffff\1\5\14\uffff\1\5\1\uffff\1\5\27\uffff"+
            "\25\5",
            "\1\70\30\uffff\1\5\13\uffff\1\67",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\75\30\uffff\1\73\2\uffff\1\72\10\uffff\1\74",
            "\1\77\44\uffff\1\76",
            "\1\100\50\uffff\1\102\3\uffff\10\101\11\uffff\1\103",
            "\1\104",
            "\1\107\30\uffff\1\105\17\uffff\1\111\3\uffff\10\110\2\uffff"+
            "\1\106\6\uffff\1\112",
            "\1\113\33\uffff\1\5\31\uffff\1\5\3\uffff\1\5\53\uffff\1\5",
            "\7\5\34\uffff\1\5\2\uffff\1\120\6\uffff\10\5\1\uffff\2\5\2"+
            "\uffff\4\5\40\uffff\2\5\2\uffff\4\5",
            "",
            "",
            "",
            "",
            "\1\164\6\5\25\uffff\1\5\6\uffff\1\5\4\uffff\1\5\4\uffff\10"+
            "\165\1\167\2\5\2\uffff\4\5\40\uffff\2\5\2\uffff\4\5",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\5\6\uffff\1\5\7\uffff\1\5\2\uffff\1\5\27\uffff\1\170",
            "\1\175",
            "\1\5\6\uffff\1\5\7\uffff\1\5\2\uffff\1\5\27\uffff\1\170",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA84_eot = DFA.unpackEncodedString(DFA84_eotS);
    static final short[] DFA84_eof = DFA.unpackEncodedString(DFA84_eofS);
    static final char[] DFA84_min = DFA.unpackEncodedStringToUnsignedChars(DFA84_minS);
    static final char[] DFA84_max = DFA.unpackEncodedStringToUnsignedChars(DFA84_maxS);
    static final short[] DFA84_accept = DFA.unpackEncodedString(DFA84_acceptS);
    static final short[] DFA84_special = DFA.unpackEncodedString(DFA84_specialS);
    static final short[][] DFA84_transition;

    static {
        int numStates = DFA84_transitionS.length;
        DFA84_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA84_transition[i] = DFA.unpackEncodedString(DFA84_transitionS[i]);
        }
    }

    class DFA84 extends DFA {

        public DFA84(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 84;
            this.eot = DFA84_eot;
            this.eof = DFA84_eof;
            this.min = DFA84_min;
            this.max = DFA84_max;
            this.accept = DFA84_accept;
            this.special = DFA84_special;
            this.transition = DFA84_transition;
        }
        public String getDescription() {
            return "439:1: forControl options {k=3; } : ( enhancedForControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA84_58 = input.LA(1);

                         
                        int index84_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_58);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA84_59 = input.LA(1);

                         
                        int index84_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_59);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA84_60 = input.LA(1);

                         
                        int index84_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_60);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA84_61 = input.LA(1);

                         
                        int index84_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_61);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA84_62 = input.LA(1);

                         
                        int index84_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_62);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA84_63 = input.LA(1);

                         
                        int index84_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_63);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA84_64 = input.LA(1);

                         
                        int index84_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_64);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA84_65 = input.LA(1);

                         
                        int index84_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_65);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA84_66 = input.LA(1);

                         
                        int index84_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_66);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA84_67 = input.LA(1);

                         
                        int index84_67 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_67);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA84_68 = input.LA(1);

                         
                        int index84_68 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_68);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA84_69 = input.LA(1);

                         
                        int index84_69 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_69);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA84_70 = input.LA(1);

                         
                        int index84_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_70);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA84_71 = input.LA(1);

                         
                        int index84_71 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_71);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA84_72 = input.LA(1);

                         
                        int index84_72 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_72);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA84_73 = input.LA(1);

                         
                        int index84_73 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_73);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA84_74 = input.LA(1);

                         
                        int index84_74 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_74);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA84_75 = input.LA(1);

                         
                        int index84_75 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_75);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA84_80 = input.LA(1);

                         
                        int index84_80 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_80);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA84_116 = input.LA(1);

                         
                        int index84_116 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_116);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA84_117 = input.LA(1);

                         
                        int index84_117 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_117);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA84_119 = input.LA(1);

                         
                        int index84_119 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_119);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA84_125 = input.LA(1);

                         
                        int index84_125 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred122_ActorFoundry()) ) {s = 120;}

                        else if ( (true) ) {s = 5;}

                         
                        input.seek(index84_125);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 84, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA87_eotS =
        "\16\uffff";
    static final String DFA87_eofS =
        "\1\14\15\uffff";
    static final String DFA87_minS =
        "\1\32\13\0\2\uffff";
    static final String DFA87_maxS =
        "\1\132\13\0\2\uffff";
    static final String DFA87_acceptS =
        "\14\uffff\1\2\1\1";
    static final String DFA87_specialS =
        "\1\uffff\1\5\1\2\1\12\1\7\1\1\1\4\1\6\1\11\1\0\1\3\1\10\2\uffff}>";
    static final String[] DFA87_transitionS = {
            "\1\14\5\uffff\1\12\1\14\1\13\3\uffff\1\14\3\uffff\1\14\1\uffff"+
            "\1\1\17\uffff\1\14\7\uffff\1\14\16\uffff\1\2\1\3\1\4\1\5\1\6"+
            "\1\7\1\10\1\11",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA87_eot = DFA.unpackEncodedString(DFA87_eotS);
    static final short[] DFA87_eof = DFA.unpackEncodedString(DFA87_eofS);
    static final char[] DFA87_min = DFA.unpackEncodedStringToUnsignedChars(DFA87_minS);
    static final char[] DFA87_max = DFA.unpackEncodedStringToUnsignedChars(DFA87_maxS);
    static final short[] DFA87_accept = DFA.unpackEncodedString(DFA87_acceptS);
    static final short[] DFA87_special = DFA.unpackEncodedString(DFA87_specialS);
    static final short[][] DFA87_transition;

    static {
        int numStates = DFA87_transitionS.length;
        DFA87_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA87_transition[i] = DFA.unpackEncodedString(DFA87_transitionS[i]);
        }
    }

    class DFA87 extends DFA {

        public DFA87(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 87;
            this.eot = DFA87_eot;
            this.eof = DFA87_eof;
            this.min = DFA87_min;
            this.max = DFA87_max;
            this.accept = DFA87_accept;
            this.special = DFA87_special;
            this.transition = DFA87_transition;
        }
        public String getDescription() {
            return "477:31: ( assignmentOperator expression )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA87_9 = input.LA(1);

                         
                        int index87_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA87_5 = input.LA(1);

                         
                        int index87_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_5);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA87_2 = input.LA(1);

                         
                        int index87_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA87_10 = input.LA(1);

                         
                        int index87_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_10);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA87_6 = input.LA(1);

                         
                        int index87_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA87_1 = input.LA(1);

                         
                        int index87_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_1);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA87_7 = input.LA(1);

                         
                        int index87_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA87_4 = input.LA(1);

                         
                        int index87_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_4);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA87_11 = input.LA(1);

                         
                        int index87_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA87_8 = input.LA(1);

                         
                        int index87_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_8);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA87_3 = input.LA(1);

                         
                        int index87_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred128_ActorFoundry()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index87_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 87, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA88_eotS =
        "\17\uffff";
    static final String DFA88_eofS =
        "\17\uffff";
    static final String DFA88_minS =
        "\1\40\12\uffff\2\42\2\uffff";
    static final String DFA88_maxS =
        "\1\132\12\uffff\1\42\1\54\2\uffff";
    static final String DFA88_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\2\uffff\1\13"+
        "\1\14";
    static final String DFA88_specialS =
        "\1\1\13\uffff\1\0\2\uffff}>";
    static final String[] DFA88_transitionS = {
            "\1\12\1\uffff\1\13\11\uffff\1\1\46\uffff\1\2\1\3\1\4\1\5\1\6"+
            "\1\7\1\10\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14",
            "\1\15\11\uffff\1\16",
            "",
            ""
    };

    static final short[] DFA88_eot = DFA.unpackEncodedString(DFA88_eotS);
    static final short[] DFA88_eof = DFA.unpackEncodedString(DFA88_eofS);
    static final char[] DFA88_min = DFA.unpackEncodedStringToUnsignedChars(DFA88_minS);
    static final char[] DFA88_max = DFA.unpackEncodedStringToUnsignedChars(DFA88_maxS);
    static final short[] DFA88_accept = DFA.unpackEncodedString(DFA88_acceptS);
    static final short[] DFA88_special = DFA.unpackEncodedString(DFA88_specialS);
    static final short[][] DFA88_transition;

    static {
        int numStates = DFA88_transitionS.length;
        DFA88_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA88_transition[i] = DFA.unpackEncodedString(DFA88_transitionS[i]);
        }
    }

    class DFA88 extends DFA {

        public DFA88(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 88;
            this.eot = DFA88_eot;
            this.eof = DFA88_eof;
            this.min = DFA88_min;
            this.max = DFA88_max;
            this.accept = DFA88_accept;
            this.special = DFA88_special;
            this.transition = DFA88_transition;
        }
        public String getDescription() {
            return "480:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | ( '<' '<' '=' )=>t1= '<' t2= '<' t3= '=' {...}? | ( '>' '>' '>' '=' )=>t1= '>' t2= '>' t3= '>' t4= '=' {...}? | ( '>' '>' '=' )=>t1= '>' t2= '>' t3= '=' {...}?);";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA88_12 = input.LA(1);

                         
                        int index88_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA88_12==34) && (synpred139_ActorFoundry())) {s = 13;}

                        else if ( (LA88_12==44) && (synpred140_ActorFoundry())) {s = 14;}

                         
                        input.seek(index88_12);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA88_0 = input.LA(1);

                         
                        int index88_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA88_0==44) ) {s = 1;}

                        else if ( (LA88_0==83) ) {s = 2;}

                        else if ( (LA88_0==84) ) {s = 3;}

                        else if ( (LA88_0==85) ) {s = 4;}

                        else if ( (LA88_0==86) ) {s = 5;}

                        else if ( (LA88_0==87) ) {s = 6;}

                        else if ( (LA88_0==88) ) {s = 7;}

                        else if ( (LA88_0==89) ) {s = 8;}

                        else if ( (LA88_0==90) ) {s = 9;}

                        else if ( (LA88_0==32) && (synpred138_ActorFoundry())) {s = 10;}

                        else if ( (LA88_0==34) ) {s = 11;}

                         
                        input.seek(index88_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 88, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA100_eotS =
        "\27\uffff";
    static final String DFA100_eofS =
        "\27\uffff";
    static final String DFA100_minS =
        "\1\40\1\uffff\1\42\1\4\23\uffff";
    static final String DFA100_maxS =
        "\1\42\1\uffff\1\42\1\151\23\uffff";
    static final String DFA100_acceptS =
        "\1\uffff\1\1\2\uffff\1\2\22\3";
    static final String DFA100_specialS =
        "\1\1\2\uffff\1\0\23\uffff}>";
    static final String[] DFA100_transitionS = {
            "\1\1\1\uffff\1\2",
            "",
            "\1\3",
            "\1\24\1\17\1\20\1\21\3\16\27\uffff\1\4\4\uffff\1\26\11\uffff"+
            "\10\25\1\uffff\1\15\1\13\2\uffff\1\14\1\23\2\22\40\uffff\1\5"+
            "\1\6\2\uffff\1\7\1\10\1\11\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA100_eot = DFA.unpackEncodedString(DFA100_eotS);
    static final short[] DFA100_eof = DFA.unpackEncodedString(DFA100_eofS);
    static final char[] DFA100_min = DFA.unpackEncodedStringToUnsignedChars(DFA100_minS);
    static final char[] DFA100_max = DFA.unpackEncodedStringToUnsignedChars(DFA100_maxS);
    static final short[] DFA100_accept = DFA.unpackEncodedString(DFA100_acceptS);
    static final short[] DFA100_special = DFA.unpackEncodedString(DFA100_specialS);
    static final short[][] DFA100_transition;

    static {
        int numStates = DFA100_transitionS.length;
        DFA100_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA100_transition[i] = DFA.unpackEncodedString(DFA100_transitionS[i]);
        }
    }

    class DFA100 extends DFA {

        public DFA100(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 100;
            this.eot = DFA100_eot;
            this.eof = DFA100_eof;
            this.min = DFA100_min;
            this.max = DFA100_max;
            this.accept = DFA100_accept;
            this.special = DFA100_special;
            this.transition = DFA100_transition;
        }
        public String getDescription() {
            return "560:1: shiftOp : ( ( '<' '<' )=>t1= '<' t2= '<' {...}? | ( '>' '>' '>' )=>t1= '>' t2= '>' t3= '>' {...}? | ( '>' '>' )=>t1= '>' t2= '>' {...}?);";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA100_3 = input.LA(1);

                         
                        int index100_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA100_3==34) && (synpred156_ActorFoundry())) {s = 4;}

                        else if ( (LA100_3==98) && (synpred157_ActorFoundry())) {s = 5;}

                        else if ( (LA100_3==99) && (synpred157_ActorFoundry())) {s = 6;}

                        else if ( (LA100_3==102) && (synpred157_ActorFoundry())) {s = 7;}

                        else if ( (LA100_3==103) && (synpred157_ActorFoundry())) {s = 8;}

                        else if ( (LA100_3==104) && (synpred157_ActorFoundry())) {s = 9;}

                        else if ( (LA100_3==105) && (synpred157_ActorFoundry())) {s = 10;}

                        else if ( (LA100_3==59) && (synpred157_ActorFoundry())) {s = 11;}

                        else if ( (LA100_3==62) && (synpred157_ActorFoundry())) {s = 12;}

                        else if ( (LA100_3==58) && (synpred157_ActorFoundry())) {s = 13;}

                        else if ( ((LA100_3>=HexLiteral && LA100_3<=DecimalLiteral)) && (synpred157_ActorFoundry())) {s = 14;}

                        else if ( (LA100_3==FloatingPointLiteral) && (synpred157_ActorFoundry())) {s = 15;}

                        else if ( (LA100_3==CharacterLiteral) && (synpred157_ActorFoundry())) {s = 16;}

                        else if ( (LA100_3==StringLiteral) && (synpred157_ActorFoundry())) {s = 17;}

                        else if ( ((LA100_3>=64 && LA100_3<=65)) && (synpred157_ActorFoundry())) {s = 18;}

                        else if ( (LA100_3==63) && (synpred157_ActorFoundry())) {s = 19;}

                        else if ( (LA100_3==Identifier) && (synpred157_ActorFoundry())) {s = 20;}

                        else if ( ((LA100_3>=49 && LA100_3<=56)) && (synpred157_ActorFoundry())) {s = 21;}

                        else if ( (LA100_3==39) && (synpred157_ActorFoundry())) {s = 22;}

                         
                        input.seek(index100_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA100_0 = input.LA(1);

                         
                        int index100_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA100_0==32) && (synpred155_ActorFoundry())) {s = 1;}

                        else if ( (LA100_0==34) ) {s = 2;}

                         
                        input.seek(index100_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 100, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA107_eotS =
        "\7\uffff";
    static final String DFA107_eofS =
        "\7\uffff";
    static final String DFA107_minS =
        "\1\4\1\0\1\35\2\uffff\1\52\1\35";
    static final String DFA107_maxS =
        "\1\151\1\0\1\74\2\uffff\1\52\1\74";
    static final String DFA107_acceptS =
        "\3\uffff\1\2\1\1\2\uffff";
    static final String DFA107_specialS =
        "\1\uffff\1\0\5\uffff}>";
    static final String[] DFA107_transitionS = {
            "\1\1\6\3\34\uffff\1\3\11\uffff\10\2\1\uffff\2\3\2\uffff\4\3"+
            "\40\uffff\2\3\2\uffff\4\3",
            "\1\uffff",
            "\1\3\13\uffff\1\5\22\uffff\1\4",
            "",
            "",
            "\1\6",
            "\1\3\13\uffff\1\5\22\uffff\1\4"
    };

    static final short[] DFA107_eot = DFA.unpackEncodedString(DFA107_eotS);
    static final short[] DFA107_eof = DFA.unpackEncodedString(DFA107_eofS);
    static final char[] DFA107_min = DFA.unpackEncodedStringToUnsignedChars(DFA107_minS);
    static final char[] DFA107_max = DFA.unpackEncodedStringToUnsignedChars(DFA107_maxS);
    static final short[] DFA107_accept = DFA.unpackEncodedString(DFA107_acceptS);
    static final short[] DFA107_special = DFA.unpackEncodedString(DFA107_specialS);
    static final short[][] DFA107_transition;

    static {
        int numStates = DFA107_transitionS.length;
        DFA107_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA107_transition[i] = DFA.unpackEncodedString(DFA107_transitionS[i]);
        }
    }

    class DFA107 extends DFA {

        public DFA107(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 107;
            this.eot = DFA107_eot;
            this.eof = DFA107_eof;
            this.min = DFA107_min;
            this.max = DFA107_max;
            this.accept = DFA107_accept;
            this.special = DFA107_special;
            this.transition = DFA107_transition;
        }
        public String getDescription() {
            return "600:12: ( type | expression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA107_1 = input.LA(1);

                         
                        int index107_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred174_ActorFoundry()) ) {s = 4;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index107_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 107, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA117_eotS =
        "\12\uffff";
    static final String DFA117_eofS =
        "\12\uffff";
    static final String DFA117_minS =
        "\1\35\1\4\1\uffff\1\40\6\uffff";
    static final String DFA117_maxS =
        "\1\73\1\151\1\uffff\1\152\6\uffff";
    static final String DFA117_acceptS =
        "\2\uffff\1\3\1\uffff\1\1\1\2\1\4\1\6\1\7\1\5";
    static final String DFA117_specialS =
        "\12\uffff}>";
    static final String[] DFA117_transitionS = {
            "\1\3\13\uffff\1\1\21\uffff\1\2",
            "\7\5\34\uffff\1\5\2\uffff\1\4\6\uffff\10\5\1\uffff\2\5\2\uffff"+
            "\4\5\40\uffff\2\5\2\uffff\4\5",
            "",
            "\1\11\31\uffff\1\10\3\uffff\1\7\53\uffff\1\6",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA117_eot = DFA.unpackEncodedString(DFA117_eotS);
    static final short[] DFA117_eof = DFA.unpackEncodedString(DFA117_eofS);
    static final char[] DFA117_min = DFA.unpackEncodedStringToUnsignedChars(DFA117_minS);
    static final char[] DFA117_max = DFA.unpackEncodedStringToUnsignedChars(DFA117_maxS);
    static final short[] DFA117_accept = DFA.unpackEncodedString(DFA117_acceptS);
    static final short[] DFA117_special = DFA.unpackEncodedString(DFA117_specialS);
    static final short[][] DFA117_transition;

    static {
        int numStates = DFA117_transitionS.length;
        DFA117_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA117_transition[i] = DFA.unpackEncodedString(DFA117_transitionS[i]);
        }
    }

    class DFA117 extends DFA {

        public DFA117(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 117;
            this.eot = DFA117_eot;
            this.eof = DFA117_eof;
            this.min = DFA117_min;
            this.max = DFA117_max;
            this.accept = DFA117_accept;
            this.special = DFA117_special;
            this.transition = DFA117_transition;
        }
        public String getDescription() {
            return "613:1: identifierSuffix : ( ( '[' ']' )+ '.' 'class' | ( '[' expression ']' )+ | arguments | '.' 'class' | '.' explicitGenericInvocation | '.' 'this' | '.' 'super' arguments );";
        }
    }
 

    public static final BitSet FOLLOW_annotations_in_compilationUnit81 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_packageDeclaration_in_compilationUnit95 = new BitSet(new long[]{0x000000008A000000L});
    public static final BitSet FOLLOW_importDeclaration_in_compilationUnit97 = new BitSet(new long[]{0x000000008A000000L});
    public static final BitSet FOLLOW_actorOrViewDeclaration_in_compilationUnit100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorOrViewDeclaration_in_compilationUnit114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_packageDeclaration_in_compilationUnit134 = new BitSet(new long[]{0x000000008A000000L});
    public static final BitSet FOLLOW_importDeclaration_in_compilationUnit137 = new BitSet(new long[]{0x000000008A000000L});
    public static final BitSet FOLLOW_actorOrViewDeclaration_in_compilationUnit140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_packageDeclaration159 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedName_in_packageDeclaration161 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_packageDeclaration165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_importDeclaration189 = new BitSet(new long[]{0x0000000010000010L});
    public static final BitSet FOLLOW_28_in_importDeclaration191 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedName_in_importDeclaration194 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_29_in_importDeclaration197 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_importDeclaration199 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_importDeclaration203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorOrViewDeclaration223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_actorDeclaration247 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_actorDeclaration251 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_actorBody_in_actorDeclaration255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_modifier_in_modifiers328 = new BitSet(new long[]{0x0001E00000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_32_in_typeParameters356 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_typeParameter_in_typeParameters358 = new BitSet(new long[]{0x0000000600000000L});
    public static final BitSet FOLLOW_33_in_typeParameters361 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_typeParameter_in_typeParameters363 = new BitSet(new long[]{0x0000000600000000L});
    public static final BitSet FOLLOW_34_in_typeParameters367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_typeParameter386 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_typeParameter389 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_typeBound_in_typeParameter391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeBound420 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_36_in_typeBound423 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_typeBound425 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_type_in_typeList458 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_typeList461 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_typeList463 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_37_in_actorBody488 = new BitSet(new long[]{0x0001E04004000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_actorBodyDeclaration_in_actorBody490 = new BitSet(new long[]{0x0001E04004000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_38_in_actorBody493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_actorBodyDeclaration512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_modifiers_in_actorBodyDeclaration522 = new BitSet(new long[]{0x01FE018100000010L});
    public static final BitSet FOLLOW_memberDecl_in_actorBodyDeclaration524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericMethodOrConstructorDecl_in_memberDecl547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_memberDeclaration_in_memberDecl557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_memberDecl567 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_memberDecl569 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_voidMethodDeclaratorRest_in_memberDecl571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_memberDecl581 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_constructorDeclaratorRest_in_memberDecl583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericMessageDeclaration_in_memberDecl593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_messageDeclaration_in_memberDecl603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_messageDeclaration622 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_messageDeclaration624 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_methodDeclaration_in_messageDeclaration626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_messageDeclaration646 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_messageDeclaration648 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_messageDeclaration652 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_voidMethodDeclaratorRest_in_messageDeclaration654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_genericMessageDeclaration682 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_typeParameters_in_genericMessageDeclaration684 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_genericMessageDeclaration686 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_methodDeclaration_in_genericMessageDeclaration688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_memberDeclaration724 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_methodDeclaration_in_memberDeclaration727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fieldDeclaration_in_memberDeclaration731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeParameters_in_genericMethodOrConstructorDecl751 = new BitSet(new long[]{0x01FE008000000010L});
    public static final BitSet FOLLOW_genericMethodOrConstructorRest_in_genericMethodOrConstructorDecl753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_genericMethodOrConstructorRest777 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_39_in_genericMethodOrConstructorRest781 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_genericMethodOrConstructorRest784 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_methodDeclaratorRest_in_genericMethodOrConstructorRest786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_genericMethodOrConstructorRest796 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_constructorDeclaratorRest_in_genericMethodOrConstructorRest798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_methodDeclaration817 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_methodDeclaratorRest_in_methodDeclaration819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclarators_in_fieldDeclaration838 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_fieldDeclaration840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_methodDeclaratorRest867 = new BitSet(new long[]{0x00000A2004000000L});
    public static final BitSet FOLLOW_41_in_methodDeclaratorRest870 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_methodDeclaratorRest872 = new BitSet(new long[]{0x00000A2004000000L});
    public static final BitSet FOLLOW_43_in_methodDeclaratorRest885 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedNameList_in_methodDeclaratorRest887 = new BitSet(new long[]{0x0000002004000000L});
    public static final BitSet FOLLOW_methodBody_in_methodDeclaratorRest903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_methodDeclaratorRest917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_voidMethodDeclaratorRest950 = new BitSet(new long[]{0x0000082004000000L});
    public static final BitSet FOLLOW_43_in_voidMethodDeclaratorRest953 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedNameList_in_voidMethodDeclaratorRest955 = new BitSet(new long[]{0x0000002004000000L});
    public static final BitSet FOLLOW_methodBody_in_voidMethodDeclaratorRest971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_voidMethodDeclaratorRest985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_constructorDeclaratorRest1022 = new BitSet(new long[]{0x0000082000000000L});
    public static final BitSet FOLLOW_43_in_constructorDeclaratorRest1025 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedNameList_in_constructorDeclaratorRest1027 = new BitSet(new long[]{0x0000082000000000L});
    public static final BitSet FOLLOW_constructorBody_in_constructorDeclaratorRest1031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_constantDeclarator1050 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_constantDeclaratorRest_in_constantDeclarator1052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclarator_in_variableDeclarators1075 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_variableDeclarators1078 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclarator_in_variableDeclarators1080 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_variableDeclaratorId_in_variableDeclarator1101 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_44_in_variableDeclarator1104 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_variableInitializer_in_variableDeclarator1106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constantDeclaratorRest_in_constantDeclaratorsRest1131 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_constantDeclaratorsRest1134 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_constantDeclarator_in_constantDeclaratorsRest1136 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_41_in_constantDeclaratorRest1158 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_constantDeclaratorRest1160 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_44_in_constantDeclaratorRest1164 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_variableInitializer_in_constantDeclaratorRest1166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclaratorId1189 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_variableDeclaratorId1192 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_variableDeclaratorId1194 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_arrayInitializer_in_variableInitializer1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_variableInitializer1225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_arrayInitializer1252 = new BitSet(new long[]{0xCDFE00E0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1255 = new BitSet(new long[]{0x0000004200000000L});
    public static final BitSet FOLLOW_33_in_arrayInitializer1258 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1260 = new BitSet(new long[]{0x0000004200000000L});
    public static final BitSet FOLLOW_33_in_arrayInitializer1265 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_arrayInitializer1272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_modifier1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_modifier1301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_modifier1311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_modifier1321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_modifier1331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_packageOrTypeName1350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_enumConstantName1369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_typeName1388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classOrInterfaceType_in_type1402 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_type1405 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_type1407 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_primitiveType_in_type1414 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_type1417 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_type1419 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_Identifier_in_classOrInterfaceType1432 = new BitSet(new long[]{0x0000000120000002L});
    public static final BitSet FOLLOW_typeArguments_in_classOrInterfaceType1434 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_29_in_classOrInterfaceType1438 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_classOrInterfaceType1440 = new BitSet(new long[]{0x0000000120000002L});
    public static final BitSet FOLLOW_typeArguments_in_classOrInterfaceType1442 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_set_in_primitiveType0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_variableModifier1551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_variableModifier1561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_typeArguments1580 = new BitSet(new long[]{0x03FE000000000010L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1582 = new BitSet(new long[]{0x0000000600000000L});
    public static final BitSet FOLLOW_33_in_typeArguments1585 = new BitSet(new long[]{0x03FE000000000010L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1587 = new BitSet(new long[]{0x0000000600000000L});
    public static final BitSet FOLLOW_34_in_typeArguments1591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeArgument1614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_typeArgument1624 = new BitSet(new long[]{0x0400000800000002L});
    public static final BitSet FOLLOW_set_in_typeArgument1627 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_typeArgument1635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList1660 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_qualifiedNameList1663 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList1665 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_59_in_formalParameters1686 = new BitSet(new long[]{0x11FE200000000010L,0x0000000000000004L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameters1688 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_formalParameters1691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifiers_in_formalParameterDecls1714 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_formalParameterDecls1716 = new BitSet(new long[]{0x2000000000000010L});
    public static final BitSet FOLLOW_formalParameterDeclsRest_in_formalParameterDecls1718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclaratorId_in_formalParameterDeclsRest1741 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_formalParameterDeclsRest1744 = new BitSet(new long[]{0x01FE200000000010L,0x0000000000000004L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameterDeclsRest1746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_formalParameterDeclsRest1758 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaratorId_in_formalParameterDeclsRest1760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_methodBody1783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_constructorBody1802 = new BitSet(new long[]{0xCDFE20E104000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_explicitConstructorInvocation_in_constructorBody1804 = new BitSet(new long[]{0xCDFE20E004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_blockStatement_in_constructorBody1807 = new BitSet(new long[]{0xCDFE20E004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_38_in_constructorBody1810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_explicitConstructorInvocation1829 = new BitSet(new long[]{0x4400000000000000L});
    public static final BitSet FOLLOW_set_in_explicitConstructorInvocation1832 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_arguments_in_explicitConstructorInvocation1840 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_explicitConstructorInvocation1842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_in_explicitConstructorInvocation1852 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_explicitConstructorInvocation1854 = new BitSet(new long[]{0x0400000100000000L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_explicitConstructorInvocation1856 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_explicitConstructorInvocation1859 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_arguments_in_explicitConstructorInvocation1861 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_explicitConstructorInvocation1863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_qualifiedName1883 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_29_in_qualifiedName1886 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_qualifiedName1888 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_integerLiteral_in_literal1914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FloatingPointLiteral_in_literal1924 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CharacterLiteral_in_literal1934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_literal1944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanLiteral_in_literal1954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_literal1964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_integerLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_booleanLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_annotations2053 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_annotation2073 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_annotationName_in_annotation2075 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_annotation2079 = new BitSet(new long[]{0xDDFE00A0000007F0L,0x000003CC00000007L});
    public static final BitSet FOLLOW_elementValuePairs_in_annotation2083 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_elementValue_in_annotation2087 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_annotation2092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_annotationName2116 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_29_in_annotationName2119 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_annotationName2121 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_elementValuePair_in_elementValuePairs2142 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_elementValuePairs2145 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elementValuePair_in_elementValuePairs2147 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_Identifier_in_elementValuePair2168 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_elementValuePair2170 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000007L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair2172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_elementValue2195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_elementValue2205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_elementValueArrayInitializer_in_elementValue2215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_elementValueArrayInitializer2238 = new BitSet(new long[]{0xCDFE00E2000007F0L,0x000003CC00000007L});
    public static final BitSet FOLLOW_elementValue_in_elementValueArrayInitializer2241 = new BitSet(new long[]{0x0000004200000000L});
    public static final BitSet FOLLOW_33_in_elementValueArrayInitializer2244 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000007L});
    public static final BitSet FOLLOW_elementValue_in_elementValueArrayInitializer2246 = new BitSet(new long[]{0x0000004200000000L});
    public static final BitSet FOLLOW_33_in_elementValueArrayInitializer2253 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_elementValueArrayInitializer2257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_defaultValue2284 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000007L});
    public static final BitSet FOLLOW_elementValue_in_defaultValue2286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_block2307 = new BitSet(new long[]{0xCDFE20E004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_blockStatement_in_block2309 = new BitSet(new long[]{0xCDFE20E004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_38_in_block2312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclarationStatement_in_blockStatement2335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_blockStatement2345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_localVariableDeclarationStatement2369 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_localVariableDeclarationStatement2371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifiers_in_localVariableDeclaration2390 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_localVariableDeclaration2392 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclarators_in_localVariableDeclaration2394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifier_in_variableModifiers2417 = new BitSet(new long[]{0x0000200000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_block_in_statement2435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASSERT_in_statement2445 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_statement2447 = new BitSet(new long[]{0x0000000004000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_statement2450 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_statement2452 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_statement2456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_statement2466 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_parExpression_in_statement2468 = new BitSet(new long[]{0xCDFE20A004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_statement_in_statement2470 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_statement2480 = new BitSet(new long[]{0xCDFE20A004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_statement_in_statement2482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_statement2494 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_statement2496 = new BitSet(new long[]{0xCDFE20A0040007F0L,0x000003CC00000007L});
    public static final BitSet FOLLOW_forControl_in_statement2498 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_statement2500 = new BitSet(new long[]{0xCDFE20A004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_statement_in_statement2502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_statement2512 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_parExpression_in_statement2514 = new BitSet(new long[]{0xCDFE20A004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_statement_in_statement2516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_statement2526 = new BitSet(new long[]{0xCDFE20A004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_statement_in_statement2528 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_statement2530 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_parExpression_in_statement2532 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_statement2534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_statement2544 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_statement2546 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020800L});
    public static final BitSet FOLLOW_catches_in_statement2558 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_statement2560 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_statement2562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_catches_in_statement2574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_statement2588 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_statement2590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_statement2610 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_parExpression_in_statement2612 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_statement2614 = new BitSet(new long[]{0x0000004000000000L,0x0000000000040008L});
    public static final BitSet FOLLOW_switchBlockStatementGroups_in_statement2616 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_statement2618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_statement2628 = new BitSet(new long[]{0xCDFE00A0040007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_statement2630 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_statement2633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_statement2643 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_statement2645 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_statement2647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_statement2657 = new BitSet(new long[]{0x0000000004000010L});
    public static final BitSet FOLLOW_Identifier_in_statement2659 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_statement2662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_statement2672 = new BitSet(new long[]{0x0000000004000010L});
    public static final BitSet FOLLOW_Identifier_in_statement2674 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_statement2677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_statement2687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementExpression_in_statement2698 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_statement2700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_statement2710 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_statement2712 = new BitSet(new long[]{0xCDFE20A004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_statement_in_statement2714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_messageSend_in_statement2724 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_statement2726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_catchClause_in_catches2745 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_catchClause_in_catches2748 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_catchClause2773 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_catchClause2775 = new BitSet(new long[]{0x01FE200000000010L,0x0000000000000004L});
    public static final BitSet FOLLOW_formalParameter_in_catchClause2777 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_catchClause2779 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_catchClause2781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifiers_in_formalParameter2800 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_formalParameter2802 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaratorId_in_formalParameter2804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_switchBlockStatementGroup_in_switchBlockStatementGroups2832 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040008L});
    public static final BitSet FOLLOW_switchLabel_in_switchBlockStatementGroup2859 = new BitSet(new long[]{0xCDFE20A004000FF2L,0x000003CC0005F7AFL});
    public static final BitSet FOLLOW_blockStatement_in_switchBlockStatementGroup2862 = new BitSet(new long[]{0xCDFE20A004000FF2L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_82_in_switchLabel2886 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_constantExpression_in_switchLabel2888 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_switchLabel2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_switchLabel2900 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_enumConstantName_in_switchLabel2902 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_switchLabel2904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_switchLabel2914 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_switchLabel2916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enhancedForControl_in_forControl2947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forInit_in_forControl2957 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_forControl2960 = new BitSet(new long[]{0xCDFE00A0040007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_forControl2962 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_forControl2965 = new BitSet(new long[]{0xCDFE20A0000007F2L,0x000003CC00000007L});
    public static final BitSet FOLLOW_forUpdate_in_forControl2967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_forInit2987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionList_in_forInit2997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifiers_in_enhancedForControl3020 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_enhancedForControl3022 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_enhancedForControl3024 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_enhancedForControl3026 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_enhancedForControl3028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionList_in_forUpdate3047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_parExpression3068 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_parExpression3070 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_parExpression3072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionList3095 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_expressionList3098 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_expressionList3100 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_expression_in_statementExpression3121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_constantExpression3144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_expression3167 = new BitSet(new long[]{0x0000100500000002L,0x0000000007F80000L});
    public static final BitSet FOLLOW_assignmentOperator_in_expression3170 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_expression3172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_assignmentOperator3197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_assignmentOperator3207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_assignmentOperator3217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_assignmentOperator3227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_assignmentOperator3237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_assignmentOperator3247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_assignmentOperator3257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_assignmentOperator3267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_assignmentOperator3277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_assignmentOperator3298 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_assignmentOperator3302 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_assignmentOperator3306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_assignmentOperator3340 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_assignmentOperator3344 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_assignmentOperator3348 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_assignmentOperator3352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_assignmentOperator3383 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_assignmentOperator3387 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_assignmentOperator3391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalExpression3420 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_conditionalExpression3424 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_conditionalExpression3426 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_conditionalExpression3428 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_conditionalExpression3430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression3452 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_conditionalOrExpression3456 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression3458 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression3480 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_conditionalAndExpression3484 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression3486 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression3508 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_inclusiveOrExpression3512 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression3514 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression3536 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_exclusiveOrExpression3540 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression3542 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression3564 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_36_in_andExpression3568 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression3570 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_instanceOfExpression_in_equalityExpression3592 = new BitSet(new long[]{0x0000000000000002L,0x0000000180000000L});
    public static final BitSet FOLLOW_set_in_equalityExpression3596 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_instanceOfExpression_in_equalityExpression3604 = new BitSet(new long[]{0x0000000000000002L,0x0000000180000000L});
    public static final BitSet FOLLOW_relationalExpression_in_instanceOfExpression3626 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_instanceOfExpression3629 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_type_in_instanceOfExpression3631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression3652 = new BitSet(new long[]{0x0000000500000002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpression3656 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression3658 = new BitSet(new long[]{0x0000000500000002L});
    public static final BitSet FOLLOW_32_in_relationalOp3693 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_relationalOp3697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_relationalOp3727 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_relationalOp3731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_relationalOp3752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_relationalOp3763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression3783 = new BitSet(new long[]{0x0000000500000002L});
    public static final BitSet FOLLOW_shiftOp_in_shiftExpression3787 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression3789 = new BitSet(new long[]{0x0000000500000002L});
    public static final BitSet FOLLOW_32_in_shiftOp3820 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_shiftOp3824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_shiftOp3856 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_shiftOp3860 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_shiftOp3864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_shiftOp3894 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_shiftOp3898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3928 = new BitSet(new long[]{0x0000000000000002L,0x0000000C00000000L});
    public static final BitSet FOLLOW_set_in_additiveExpression3932 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3940 = new BitSet(new long[]{0x0000000000000002L,0x0000000C00000000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3962 = new BitSet(new long[]{0x0000000040000002L,0x0000003000000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3966 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3980 = new BitSet(new long[]{0x0000000040000002L,0x0000003000000000L});
    public static final BitSet FOLLOW_98_in_unaryExpression4006 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression4008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_unaryExpression4018 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression4020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_unaryExpression4030 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression4032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_unaryExpression4042 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression4044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression4054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_unaryExpressionNotPlusMinus4073 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus4075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_unaryExpressionNotPlusMinus4085 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus4087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_unaryExpressionNotPlusMinus4097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_in_unaryExpressionNotPlusMinus4107 = new BitSet(new long[]{0x0000020020000002L,0x000000C000000000L});
    public static final BitSet FOLLOW_selector_in_unaryExpressionNotPlusMinus4109 = new BitSet(new long[]{0x0000020020000002L,0x000000C000000000L});
    public static final BitSet FOLLOW_set_in_unaryExpressionNotPlusMinus4112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_castExpression4135 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_primitiveType_in_castExpression4137 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_castExpression4139 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_castExpression4141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_castExpression4150 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_type_in_castExpression4153 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_expression_in_castExpression4157 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_castExpression4160 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpressionNotPlusMinus_in_castExpression4162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parExpression_in_primary4181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_primary4191 = new BitSet(new long[]{0x0800020020000002L});
    public static final BitSet FOLLOW_29_in_primary4194 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_primary4196 = new BitSet(new long[]{0x0800020020000002L});
    public static final BitSet FOLLOW_identifierSuffix_in_primary4200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_primary4211 = new BitSet(new long[]{0x0800000020000000L});
    public static final BitSet FOLLOW_superSuffix_in_primary4213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_primary4223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_primary4233 = new BitSet(new long[]{0x0800020020000002L});
    public static final BitSet FOLLOW_29_in_primary4236 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_primary4238 = new BitSet(new long[]{0x0800020020000002L});
    public static final BitSet FOLLOW_identifierSuffix_in_primary4242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_primary4253 = new BitSet(new long[]{0x0000020020000000L});
    public static final BitSet FOLLOW_41_in_primary4256 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_primary4258 = new BitSet(new long[]{0x0000020020000000L});
    public static final BitSet FOLLOW_29_in_primary4262 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_primary4264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_primary4274 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_primary4276 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_primary4278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_identifierSuffix4298 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_identifierSuffix4300 = new BitSet(new long[]{0x0000020020000000L});
    public static final BitSet FOLLOW_29_in_identifierSuffix4304 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_identifierSuffix4306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_identifierSuffix4317 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_identifierSuffix4319 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_identifierSuffix4321 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_arguments_in_identifierSuffix4334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_identifierSuffix4344 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_identifierSuffix4346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_identifierSuffix4356 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_explicitGenericInvocation_in_identifierSuffix4358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_identifierSuffix4368 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_identifierSuffix4370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_identifierSuffix4380 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_identifierSuffix4382 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_arguments_in_identifierSuffix4384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_arrayCreatorRest4404 = new BitSet(new long[]{0xCDFE04A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest4418 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_41_in_arrayCreatorRest4421 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest4423 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_arrayInitializer_in_arrayCreatorRest4427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_arrayCreatorRest4441 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest4443 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_arrayCreatorRest4446 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_arrayCreatorRest4448 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest4450 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_arrayCreatorRest4455 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest4457 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_explicitGenericInvocation4496 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_explicitGenericInvocation4498 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_arguments_in_explicitGenericInvocation4500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_nonWildcardTypeArguments4523 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_typeList_in_nonWildcardTypeArguments4525 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_nonWildcardTypeArguments4527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_selector4550 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_selector4552 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_arguments_in_selector4554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_selector4565 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_selector4567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_selector4577 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_selector4579 = new BitSet(new long[]{0x0800000020000000L});
    public static final BitSet FOLLOW_superSuffix_in_selector4581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_selector4591 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_selector4593 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_selector4595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_superSuffix4618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_superSuffix4628 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_superSuffix4630 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_arguments_in_superSuffix4632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_arguments4652 = new BitSet(new long[]{0xDDFE20A0000007F0L,0x000003CC00000007L});
    public static final BitSet FOLLOW_expressionList_in_arguments4654 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_arguments4657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_messageSend4682 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_107_in_messageSend4686 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_messageSend4699 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_messageArgs_in_messageSend4712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_messageSend4765 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_messageSend4769 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_messageSend4782 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_messageArgs_in_messageSend4795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_messageSend4848 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_messageSend4852 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_messageSend4865 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_messageArgs_in_messageSend4878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_messageSend4931 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_messageSend4935 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_messageSend4948 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_messageArgs_in_messageSend4961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_messageArgs5036 = new BitSet(new long[]{0xDDFE20A0000007F0L,0x000003CC00000007L});
    public static final BitSet FOLLOW_expressionList_in_messageArgs5038 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_messageArgs5041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_explicitConstructorInvocation_in_synpred65_ActorFoundry1804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_synpred69_ActorFoundry1829 = new BitSet(new long[]{0x4400000000000000L});
    public static final BitSet FOLLOW_set_in_synpred69_ActorFoundry1832 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_arguments_in_synpred69_ActorFoundry1840 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_synpred69_ActorFoundry1842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclarationStatement_in_synpred92_ActorFoundry2335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_synpred97_ActorFoundry2480 = new BitSet(new long[]{0xCDFE20A004000FF0L,0x000003CC0001F7A7L});
    public static final BitSet FOLLOW_statement_in_synpred97_ActorFoundry2482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_catches_in_synpred102_ActorFoundry2558 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_synpred102_ActorFoundry2560 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_synpred102_ActorFoundry2562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_catches_in_synpred103_ActorFoundry2574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_switchLabel_in_synpred118_ActorFoundry2859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_synpred120_ActorFoundry2886 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_constantExpression_in_synpred120_ActorFoundry2888 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_synpred120_ActorFoundry2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_synpred121_ActorFoundry2900 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_enumConstantName_in_synpred121_ActorFoundry2902 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_synpred121_ActorFoundry2904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enhancedForControl_in_synpred122_ActorFoundry2947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_synpred126_ActorFoundry2987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentOperator_in_synpred128_ActorFoundry3170 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_synpred128_ActorFoundry3172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_synpred138_ActorFoundry3288 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_synpred138_ActorFoundry3290 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_synpred138_ActorFoundry3292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_synpred139_ActorFoundry3328 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_synpred139_ActorFoundry3330 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_synpred139_ActorFoundry3332 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_synpred139_ActorFoundry3334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_synpred140_ActorFoundry3373 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_synpred140_ActorFoundry3375 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_synpred140_ActorFoundry3377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_synpred151_ActorFoundry3685 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_synpred151_ActorFoundry3687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_synpred152_ActorFoundry3719 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_synpred152_ActorFoundry3721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_synpred155_ActorFoundry3812 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_synpred155_ActorFoundry3814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_synpred156_ActorFoundry3846 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_synpred156_ActorFoundry3848 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_synpred156_ActorFoundry3850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_synpred157_ActorFoundry3886 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_synpred157_ActorFoundry3888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_synpred169_ActorFoundry4097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_synpred173_ActorFoundry4135 = new BitSet(new long[]{0x01FE000000000010L});
    public static final BitSet FOLLOW_primitiveType_in_synpred173_ActorFoundry4137 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_synpred173_ActorFoundry4139 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_unaryExpression_in_synpred173_ActorFoundry4141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred174_ActorFoundry4153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_synpred176_ActorFoundry4194 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_synpred176_ActorFoundry4196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierSuffix_in_synpred177_ActorFoundry4200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_synpred181_ActorFoundry4236 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_synpred181_ActorFoundry4238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierSuffix_in_synpred182_ActorFoundry4242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_synpred188_ActorFoundry4317 = new BitSet(new long[]{0xCDFE00A0000007F0L,0x000003CC00000003L});
    public static final BitSet FOLLOW_expression_in_synpred188_ActorFoundry4319 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_synpred188_ActorFoundry4321 = new BitSet(new long[]{0x0000000000000002L});

}