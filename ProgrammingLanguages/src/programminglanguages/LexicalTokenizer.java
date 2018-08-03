/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programminglanguages;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Mavis Beacon
 */
public class LexicalTokenizer {

    //Class Variables
    int charClass;
    char lexeme[];
    Character nextChar;
    int lexLen;
    private int nextToken;
    private boolean isString;
    private boolean isComment;
    private boolean isChar;
    private int pushbackToken;
    private final int PUSHBACK_EMPTY=-100;
    BufferedReader br;

    //Char Class
    final int EOF=-1;
    final int LETTER=300;
    final int DIGIT=301;
    final int STRING=302;
    final int COMMENT=303;
    final int UNKNOWN=309;

    final int INT_LIT=310;
    final int IDENT=311;
    final int DOUBLEOR=323;
    final int DOUBLEAND=324;
    final int NOTEQUAL=341;
    final int BOOLEQUAL=327;
    final int LESSTHANEQ=328;
    final int GREATERTHANEQ=331;
    final int RESERVED=336;
    final int CHARACTER=337;
    final int DOUBLELEFTARROW=339;
    final int DOUBLERIGHTARROW=340;

    //Reserved Words
    final int IF=401;
    final int ELSE=402;
    final int WHILE=403;
    final int CIN=404;
    final int COUT=405;
    final int INT=406;
    final int CHAR=407;
    final int LOE=408;
    final int LOV=409;

        /*
    //Table of Symbols
    final int ASSIGN_OP=312;
    final int ADD_OP=313;
    final int SUB_OP=314;
    final int MULT_OP=315;
    final int DIV_OP=316;
    final int LEFT_PAREN=317;
    final int RIGHT_PAREN=318;
    final int RIGHT_BRACK=319;
    final int LEFT_BRACK=320;
    final int COMMA=321;
    final int SEMICOLON=322;
    final int SINGLEOR=325;
    final int SINGLEAND=326;
    final int LESSTHAN=329;
    final int GREATERTHAN=330;
    final int REMAINDER=332;
    final int UNARYNOT=333;
    final int BITNOT=334;
    final int UNARYMINUS=335; //HOW TO REPRESENT DIFFERENT THAN MINUS?????
    final int SINGLEQUOTE=338;
     **/

    public LexicalTokenizer(String filename){
        try {
            br =new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LexicalTokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }

        isString=false;
        isComment=false;
        isChar=false;
        lexeme=new char[100];
        lexLen=0;
        pushbackToken=-100;
    }

    public static void main(String args []){
        LexicalTokenizer l;
        if(args.length>0)
            l=new LexicalTokenizer(args[0]);
        else
            l=new LexicalTokenizer("test4.txt");
        System.out.println("Hello");
        l.breakdown();
    }

    public void breakdown(){

        getChar();
        do{
            lex();
            System.out.println("NextToken: "+displayLexeme()+" "+nextToken);
        }while(nextToken!=EOF);

    }

    protected void getChar(){
        try {
            if ( (nextChar = (char) br.read()) == -1) {
                System.out.println("End of file");
                charClass = EOF;
                return;
            }
        } catch (IOException ex) {
            charClass=EOF;
            System.out.println("IO exception in getChar()");
        }

        if(Character.isLetter(nextChar) || nextChar=='$' || nextChar=='_')
            charClass=LETTER;
        else if(Character.isDigit(nextChar))
            charClass=DIGIT;
        else if(nextChar==65535)
            charClass=EOF;
        else{
            charClass=UNKNOWN;
        }

    }//end getChar

    public Token lex(){
        if(pushbackToken!=PUSHBACK_EMPTY){
            nextToken=pushbackToken;
            pushbackToken=PUSHBACK_EMPTY;
            //System.out.println("Next Token is: "+nextToken);
            return new Token(nextToken,displayLexeme());
        }

        lexLen=0;
        getNonBlank();
        switch(charClass){

            case LETTER:
                while(charClass==LETTER || charClass==DIGIT){
                    addChar();
                    getChar();
                }//end While
                if(isReserverdWord()){
                    //NextToken set in isReservedWord
                    break;
                }
                else
                    nextToken=IDENT;
                break;

            case DIGIT:
                while(charClass==DIGIT){
                    addChar();
                    getChar();
                }//end while
                nextToken=INT_LIT;
                break;

            case UNKNOWN:
                addChar();
                getChar();
                while( (isOperatorPossible() && lexLen<=2) || (isCharPossible() && lexLen<=3) || isStringPossible() || isCommentPossible() ){
                    addChar();
                    getChar();
                }
                if(lexLen==1)
                    nextToken=(int)lexeme[0];
                else if(lexLen==2 && (!isString && !isComment && !isChar))
                    lookupString();
                else if(isChar){
                    isChar=false;
                    nextToken=CHARACTER;
                }
                else if(isString){
                    isString=false;
                    nextToken=STRING;
                }
                else if(isComment){
                    isComment=false;
                    nextToken=COMMENT;
                }
                else{
                    System.out.println("Lexeme Not Found");
                    nextToken=EOF;
                }

                break;

            case EOF:
                System.out.println("EOF in Lex");
                nextToken=EOF;
                break;

        }//end switch

        //System.out.println("Next Token is: "+nextToken+" Lexeme is:"+displayLexeme());
        return new Token(nextToken,displayLexeme());

    }//end lex

    private void addChar(){
        if(lexLen<100){
            lexeme[lexLen]=nextChar;
            lexLen++;
            lexeme[lexLen]=0;
        }//end if
        else{ 
            System.out.println("Lexeme too long");
            charClass=EOF;
        }//end else
    }//end addChar

    public void pushback(){
        pushbackToken=nextToken;
        System.out.println("Pushback");
    }

    private void getNonBlank(){
        while(Character.isWhitespace(nextChar)){
            getChar();
        }//end while
    }//end getNonBlank

   private String displayLexeme(){

        String s="";
        for(int i=0;i<lexLen;i++){
            s=s+lexeme[i];
        }//end for

        return s;

    }//end DisplayLexeme

    private boolean isReserverdWord(){
        
        String s="";
        for(int i=0;i<lexLen;i++)
            s=s+lexeme[i];

        if(lookupReserved(s))
            return true;
        return false;

    }//end isReservedWord;

    private boolean isOperatorPossible(){
        char c = lexeme[0];

        if(c=='|' && nextChar=='|')
            return true;
        if(c=='&' && nextChar=='&')
            return true;
        if(c=='=' && nextChar=='=')
            return true;
        if(c=='!' && nextChar=='=')
            return true;
        if(c=='<' && nextChar=='=')
            return true;
        if(c=='>' && nextChar=='=')
            return true;
        if(c=='<' && nextChar=='<')
            return true;
        if(c=='>' && nextChar=='>')
            return true;

        return false;
    }//end isOperatorPossible

    private boolean isCharPossible(){
        char c1=lexeme[0];
        char c2=lexeme[1];
        char c3=lexeme[lexLen-1];

        if(c1=='\''&& c2!='\\' && c3=='\'' && lexLen==3){
            isChar=true;
            return false;
        }
        if(c1=='\''&& c2=='\\' && (lexeme[2]=='\'' || lexeme[2]=='\\') && c3=='\'' && (lexLen==4)){
            isChar=true;
            return false;
        }
        if(c1=='\'' && c2=='\\' && (nextChar!='\'' && nextChar!='\\'))
            return false;
        if(c1=='\''){
            return true;
        }

        return false;
    }

    char c='\\';
    private boolean isStringPossible(){
        char c1=lexeme[0];
        char c2=lexeme[lexLen-1];

        boolean isTrue=false;
        if(c1=='\"'){
            isTrue=true;
            isString=true;
        }
        if(c1=='\"' && c2=='\"' && (lexLen-1)!=0)
            isTrue=false;

        return isTrue;
    }

    private boolean isCommentPossible(){
        char c1=lexeme[0];
        char c2=lexeme[1];
        char c3=lexeme[0];
        char c4=lexeme[lexLen-1];;

        if(lexLen>1){
           c3=lexeme[lexLen-2];
        }

        //Multi-line Comment
        boolean isTrue=false;
        if(c1=='/' && (c2=='*' || nextChar=='*')){
            isTrue=true;
            isComment=true;
            if(c3=='*' && c4=='/' && (lexLen-1)!=0)
               isTrue=false;
        }
        //Single Line Comment--Double Slash
        else if(c1=='/' && (c2=='/' || nextChar=='/')){
            isTrue=true;
            isComment=true;
            if(nextChar=='\n')
                isTrue=false;
        }

        return isTrue;
    }
    
    private boolean lookupReserved(String res){

        if(res.equals("if"))
            nextToken=IF;
        else if(res.equals("else"))
           nextToken=ELSE;
        else if(res.equals("while"))
            nextToken=WHILE;
        else if(res.equals("cin"))
            nextToken=CIN;
        else if(res.equals("cout"))
            nextToken=COUT;
        else if(res.equals("int"))
            nextToken=INT;
        else if(res.equals("char"))
            nextToken=CHAR;
        else
            return false;

        return true;

    }//end lookupReserved

    private int lookupString(){
        String s="";
        for(int i=0;i<lexLen;i++){
            s=s+lexeme[i];
        }//end for

        if(s.equals("||")){
            nextToken=DOUBLEOR;
        }
        else if(s.equals("&&")){
            nextToken=DOUBLEAND;
        }
        else if(s.equals("!=")){
            nextToken=NOTEQUAL;
        }
        else if(s.equals("==")){
            nextToken=BOOLEQUAL;
        }
        else if(s.equals("<=")){
            nextToken=LESSTHANEQ;
        }
        else if(s.equals(">=")){
            nextToken=GREATERTHANEQ;
        }
        else if(s.equals(">>")){
            nextToken=DOUBLERIGHTARROW;
        }
        else if(s.equals("<<")){
            nextToken=DOUBLELEFTARROW;
        }
        else{
            System.out.println("LookupString not found. String was "+s+" -- EOF");
            nextToken=EOF;
        }
        return nextToken;
    }//end lookupString
 
}//end class
