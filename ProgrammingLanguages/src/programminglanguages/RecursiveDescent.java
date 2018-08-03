/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programminglanguages;

import java.util.ArrayList;


/** 
 *
 * @author Mavis Beacon
 */
public class RecursiveDescent {

    LexicalTokenizer tokenizer;
    int error=0;


    public RecursiveDescent(){ 
        tokenizer=new LexicalTokenizer("test4.txt");
    }

    public RecursiveDescent(String filename){
        tokenizer=new LexicalTokenizer(filename);
    }

    public static void main(String args []){
        RecursiveDescent rd=new RecursiveDescent();
        Term t=rd.program();
        t.outputTerm();
        System.out.println("\nError:"+rd.error);
    }
 
    public Term program(){
        System.out.println("Enter Program");
        ArrayList list=new ArrayList();
        Term t;
        tokenizer.getChar();
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok==tokenizer.INT || tok==tokenizer.CHAR){
            tokenizer.pushback();
            t=listOfDeclarations();
            list.add(t);
        }
        else 
            tokenizer.pushback();
        t=listOfStatements();
        list.add(t);

        System.out.println("Exit Program");
        return new Term(list);
    }//end program

    private Term listOfDeclarations(){
        System.out.println("Enter LoD");
        ArrayList list=new ArrayList();
        Term t;
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==tokenizer.INT || tok==tokenizer.CHAR){
            tokenizer.pushback();
            t=declaration();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }//end while
        tokenizer.pushback();
        System.out.println("Exit LoD");
        return new Term(list);
    }//end listOfDeclarations;

    private Term listOfStatements(){
        System.out.println("Enter LoS");
        ArrayList list=new ArrayList();
        Term t;
        Token token=tokenizer.lex();
        int tok=token.type;
        while(stillStatement(tok)){
            tokenizer.pushback();
            t=statement();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }//end while
        tokenizer.pushback();
        System.out.println("Exit LoS");
        return new Term(list);
    }//end LoS

    
    private Term declaration(){
        Token token;
        ArrayList list=new ArrayList();
        Term t;
        System.out.println("Enter Declaration");
        t=type();
        int type=((SimpleTerm)t).type;
        //list.add(t);
        t=variable();
        list.add(t);
        token=tokenizer.lex();
        int tok=token.type;
        while(tok==(int)(',')){
            System.out.println("More Variable");
            t=variable();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }//end while
        tokenizer.pushback();
        token=tokenizer.lex();
        tok=token.type;
        if(tok!=(int)(';'))
            error("Error in Delclaration. NextToken expected was ;. Actual was "+tok);
        System.out.println("Exit Declaration");
        return new ComplexTerm(type,list);
    }//end declaration

    private Term statement(){
        System.out.println("Enter Statement");
        ArrayList list=new ArrayList();
        Term t;
        Token token=tokenizer.lex();
        tokenizer.pushback();
        int tok=token.type;
        if(tok==tokenizer.CIN)
            t=cin();
        else if(tok==tokenizer.COUT)
            t=cout();
        else if(tok==tokenizer.IF)
            t=ifStmt();
        else if(tok==tokenizer.IDENT)
            t=assign(); 
        else if(tok==tokenizer.WHILE)
            t=whileStmt();
        else if(tok==(int)('{')){
            token=tokenizer.lex();
            t=listOfStatements();
            token=tokenizer.lex();
            tok=token.type;
            if(tok!=(int)('}'))
                error("Error in Statement->ListOfStatements");
        }//end else if
        else{
            error("Error in Statement");
            t=null;
        }
        
        list.add(t);
        System.out.println("Exit Statement");
        return new Term(list);
    }//end statement

    private SimpleTerm type(){
        System.out.println("Enter Type");
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=tokenizer.INT && tok!=tokenizer.CHAR)
            error("Error in Type");
        System.out.println("Exit Type");
        return new SimpleTerm(token.type,token.value);
    }//end type

    private Term cin(){
        System.out.println("Enter Cin");
        ArrayList list=new ArrayList();
        Term t;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=tokenizer.CIN)
            error("Error in Cin(1). Expecting CIN");
        token=tokenizer.lex();
        tok=token.type; 
        if(tok!=tokenizer.DOUBLERIGHTARROW)
            error("Error in Cin(2). Expecting DOUBLERIGHTARROW");
        t=listOfVariables();
        list.add(t);
        token=tokenizer.lex();
        tok=token.type;
        if(tok!=(int)(';'))
            error("Error in Cin(3). Expection semicolon");
        System.out.println("Exit Cin");
        return new ComplexTerm(tokenizer.CIN,list);
    }//end cin

    private Term cout(){
        System.out.println("Enter Cout");
        ArrayList list=new ArrayList();
        Term t;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=tokenizer.COUT)
            error("Error in COUT. Expecting COUT");
        token=tokenizer.lex();
        tok=token.type;
        if(tok!=tokenizer.DOUBLELEFTARROW)
            error("Error in COUT. Expecting DOUBLELEFTARROW");
        t=listOfExpressions();
        list.add(t);
        System.out.println("Exit Cout");
        return new ComplexTerm(tokenizer.COUT,list);
    }//end cout

    private Term ifStmt(){
        System.out.println("Enter IfStmt");
        ArrayList list=new ArrayList();
        Term t;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=tokenizer.IF){
            error("Error in ifStmt. Expecting IF");
            return null;
        }//end if
        token=tokenizer.lex();
        tok=token.type;
        if(tok!=(int)('(')){
            error("Error in ifStmt. Expecting (");
            return null;
        }//end if
        t=exp();
        list.add(t);
        token=tokenizer.lex();
        tok=token.type;
        if(tok!=(int)(')')){
            error("Error in ifStmt. Expecting )");
            return null;
        }//end if
        t=statement();
        list.add(t);

        token=tokenizer.lex();
        tok=token.type;
        if(tok==tokenizer.ELSE){
            t=statement();
            list.add(t);
        }//end if
        else
            tokenizer.pushback();

        System.out.println("Exit ifStmt");
        return new ComplexTerm(tokenizer.IF,list);
    }//end ifStmt

    private Term exp(){
        System.out.println("Entering exp");
        ArrayList list=new ArrayList();
        Term t;
        Term t1;
        t=exp1();
        list.add(t);
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==tokenizer.DOUBLEOR){
            t1=exp1();
            list.add(t1);
            t=new ComplexTerm(tokenizer.DOUBLEOR,list);
            list=new ArrayList();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }
        tokenizer.pushback();
        System.out.println("Exiting exp");
        return t;
    }//end exp

    private Term exp1(){
        System.out.println("Entering exp1");
        ArrayList list=new ArrayList();
        Term t;
        Term t1;
        t=exp2();
        list.add(t);
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==tokenizer.DOUBLEAND){
            t1=exp2();
            list.add(t1);
            t=new ComplexTerm(tokenizer.DOUBLEAND,list);
            list=new ArrayList();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }
        tokenizer.pushback();
        System.out.println("Exiting exp1");
        return t;
    }
    private Term exp2(){
        System.out.println("Entering exp2");
        ArrayList list=new ArrayList();
        Term t;
        Term t1;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok==(int)('!')){
            t=exp2();
            list.add(t);
            System.out.println("Exiting exp2");
            return new ComplexTerm('!',list);
        }
        else{
            tokenizer.pushback();
            t=exp3();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
            while(tok==(int)('|')){
                t1=exp3();
                list.add(t1);
                t=new ComplexTerm('|',list);
                list=new ArrayList();
                list.add(t);
                token=tokenizer.lex();
                tok=token.type;
            }
            tokenizer.pushback();
            System.out.println("Exiting exp2");
            return t;
        }
    }
    private Term exp3(){
       System.out.println("Entering exp3");
       ArrayList list=new ArrayList();
        Term t;
        Term t1;
        t=exp4();
        list.add(t);
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==(int)('^')){
            t1=exp4();
            list.add(t1);
            t=new ComplexTerm(token.type,list);
            list=new ArrayList();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }
        tokenizer.pushback();
        System.out.println("Exiting exp3");
        return t;
    }

   private Term exp4(){
       System.out.println("Entering exp4");
       ArrayList list=new ArrayList();
        Term t;
        Term t1;
        t=exp5();
        list.add(t);
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==(int)('&')){
            t1=exp5();
            list.add(t1);
            t=new ComplexTerm(token.type,list);
            list=new ArrayList();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }
        tokenizer.pushback();
        System.out.println("Exiting exp4");
        return t;
    }
    private Term exp5(){
       System.out.println("Entering exp5");
       ArrayList list=new ArrayList();
        Term t;
        Term t1;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok==(int)('~')){
            t=exp5();
            list.add(t);
            return new ComplexTerm(token.type,list);
        }
        else{
            tokenizer.pushback();
            t=exp6();
            token=tokenizer.lex();
            tok=token.type;
            list.add(t);
            while(tok==tokenizer.NOTEQUAL || tok==tokenizer.BOOLEQUAL){
                t1=exp6();
                list.add(t1);
                t=new ComplexTerm(token.type,list);
                list=new ArrayList();
                list.add(t);
                token=tokenizer.lex();
                tok=token.type;
            }
            tokenizer.pushback();
            System.out.println("Exiting exp5");
            return t;
        }
        
    }
    private Term exp6(){
       System.out.println("Entering exp6");
        ArrayList list=new ArrayList();
        Term t;
        Term t1;
        t=exp7();
        list.add(t);
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==tokenizer.LESSTHANEQ || tok==tokenizer.GREATERTHANEQ || tok==(int)('>') || tok==(int)('<')){
            t1=exp7();
            list.add(t1);
            t=new ComplexTerm(token.type,list);
            list=new ArrayList();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }
        tokenizer.pushback();
        System.out.println("Exiting exp6");
        return t;
    }

    private Term exp7(){
       System.out.println("Entering exp7");
       ArrayList list=new ArrayList();
        Term t;
        Term t1;
        t=exp8();
        list.add(t);
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==(int)('+') || tok==(int)('-')){
            t1=exp8();
            list.add(t1);
            t=new ComplexTerm(token.type,list);
            list=new ArrayList();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }
        tokenizer.pushback();
        System.out.println("Exiting exp7");
        return t;
    }
    private Term exp8(){
       System.out.println("Entering exp8");
       ArrayList list=new ArrayList();
        Term t;
        Term t1;
        t=exp9();
        list.add(t);
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==(int)('*')||tok==(int)('/') || tok==(int)('%')){
            t1=exp9();
            list.add(t1);
            t=new ComplexTerm(token.type,list);
            list=new ArrayList();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }
        tokenizer.pushback();
        System.out.println("Exiting exp8");
        return t;
    }
    private Term exp9(){
        System.out.println("Entering exp9");
        ArrayList list=new ArrayList();
        Term t;
        Term t1;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok=='('){
            t=exp();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
            if(tok!=(int)(')'))
                error("Error in exp9. Expecting )");
            System.out.println("Exiting exp9");
            return new Term(list);
        }//end if
        else if(tok==tokenizer.INT_LIT || tok==tokenizer.IDENT || tok==tokenizer.CHARACTER){
            tokenizer.pushback();
            t=last();
            System.out.println("Exiting exp9");
            return t;
        }
        else if(tok==(int)('-') || tok==(int)('+')){
            tokenizer.pushback();
            t=uarop();
            System.out.println("Exiting exp9");
            return t;
        }
        else{
            error("Error in exp9. Expecting either ( or <last>");
            t=null;
            System.out.println("Exiting exp9");
            return t;
        }

    }
    private Term uarop(){
        System.out.println("Enter uarop");
        ArrayList list=new ArrayList();
        Term t;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=(int)('-') && tok!=(int)('+'))
            error("Error in Last. Expecting INT,IDENT or CHARACTER");
        t=last();
        list.add(t);
        System.out.println("Exit uarop");
        return new ComplexTerm(token.type,list);
    }
    private Term last(){
        System.out.println("Enter last");
        ArrayList list=new ArrayList();
        Term t;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=tokenizer.INT_LIT && tok!=tokenizer.IDENT && tok!=tokenizer.CHARACTER)
            error("Error in Last. Expecting INT,IDENT or CHARACTER");
        System.out.println("Exit last");
        return new SimpleTerm(token.type,token.value);
    }//end last

    private Term variable(){
        System.out.println("Enter Variable");
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=tokenizer.IDENT)
            error("Error in variable. Expecting IDENT");
        System.out.println("Exit Variable");
        return new SimpleTerm(token.type,token.value);
    }//end variable

    private Term assign(){
        System.out.println("Enter Assign");
        ArrayList list=new ArrayList();
        Term listOfVariables=listOfVariables();
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=(int)('=')){
            error("Error in Assign. Expecting =. Actual was "+tok);
            return null;
        }//end if
        Term exp=exp();
        list.add(exp);
        list.add(listOfVariables);
        token=tokenizer.lex();
        tok=token.type;
        if(tok!=(int)(';'))
            error("Error in Assign. Expecting ;. Actual was "+tok);
        System.out.println("Exit Assign");
        return new ComplexTerm('=',list);
    }//end assign

    private Term whileStmt(){
        System.out.println("Enter while");
        ArrayList list=new ArrayList();
        Term t;
        Term t1;
        Token token=tokenizer.lex();
        int tok=token.type;
        if(tok!=tokenizer.WHILE){
            error("Error in WhileStmt. NextToken expected was WHILE. Actual was "+tok);
            return null;
        }
        token=tokenizer.lex();
        tok=token.type;
        if(tok!=(int)('(')){
            error("Error in WHILE. NextToken expected was (. Actual was "+tok);
            return null;
        }//end if
        t=exp();
        list.add(t);
        token=tokenizer.lex();
        tok=token.type;
        if(tok!=(int)(')')){
            error("Error in WHILE. NextToken expected was ). Actual was "+tok);
            return null;
        }
        t=statement();
        list.add(t);
        System.out.println("Exit While");
        return new ComplexTerm(tokenizer.WHILE,list);
    }//end while

    private Term listOfVariables(){
        System.out.println("Enter LoV");
        ArrayList list=new ArrayList();
        Term t; 
        Token token;
        int tok;
        t=variable();
        list.add(t);
        token=tokenizer.lex();
        tok=token.type;
        while(tok==(int)(',')){
            t=variable();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }//end while
        tokenizer.pushback();
        System.out.println("Exit LoV");
        return new ComplexTerm(tokenizer.LOV,list);
    }//end LoV

    private Term listOfExpressions(){
        System.out.println("Enter LoE");
        ArrayList list=new ArrayList();
        Term t;
        t=exp();
        list.add(t);
        Token token=tokenizer.lex();
        int tok=token.type;
        while(tok==(int)(',')){
            t=exp();
            list.add(t);
            token=tokenizer.lex();
            tok=token.type;
        }
        System.out.println("Exit LoE");
        return new ComplexTerm(tokenizer.LOE,list);
    }

    private boolean stillStatement(int tok){
        if(tok==tokenizer.CIN || tok==tokenizer.COUT || tok==tokenizer.IF
                || tok==tokenizer.IDENT || tok==tokenizer.WHILE
                || tok==(int)('{'))
            return true;
        
        return false;
    }//end nextToken
    
    private void error(String s){
        System.out.println(s);
        error++;
    }

}
