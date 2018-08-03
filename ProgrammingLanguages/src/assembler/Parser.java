/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mavis Beacon
 */
public class Parser {
    
    BufferedReader br;
    SymbolTable table;
    String nextLine="";
    private int nextRam=16;
    public static final int A_COMMAND=100;
    public static final int C_COMMAND=101;
    public static final int L_COMMAND=102;

    public Parser(String filename){
        try {
            br =new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        table=this.constructSymbolTable();

        try {
            br =new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }

    public static void main2(String args []){
        Parser p=new Parser("assembly.txt");
        System.out.println(p.table);
    }

    public static void main(String args []){
        Parser p=new Parser("assembly.txt");
        String nextLine;
        String withSpaces;
        int y;
        while((nextLine=p.parseNextLine()) != null){
            withSpaces="";
            y=0;
            for(int x=4;x<=nextLine.length();x=x+4){
                withSpaces+=nextLine.substring(y, x)+" ";
                y=x;
            }
            System.out.println("NextLine: "+withSpaces);
        }
    }

    /**
     * Constructs a SymbolTable of labels and their associated line number
     * from this objects current InputStream.
     *
     * @return A map of label names and the line number they appear on
     */
    public SymbolTable constructSymbolTable(){
        SymbolTable tableTemp=new SymbolTable();
        int lineNumber=0;
        while(advance()){
            int type=commandType();
            if(type==Parser.A_COMMAND || type==Parser.C_COMMAND)
                lineNumber++;
            if(type==Parser.L_COMMAND){
                String s=this.symbol();
                tableTemp.addEntry(s, (lineNumber+1)+"");
            }
        }//end while
        return tableTemp;
    }

    public String parseNextLine(){
        if(!advance())
            return null;
        int type=commandType();
        String binary="";
        if(type==Parser.A_COMMAND || type==Parser.L_COMMAND){
            String s;
            s=this.symbol();
            if(s==null)
                return " ";
            String tempBinary=Integer.toBinaryString(Integer.parseInt(s));
            for(int x=16;x>tempBinary.length();x--)
                binary=binary+"0";
            binary+=tempBinary;
            //System.out.println("Parse1: "+binary);
            return binary;
        }

        if(type==Parser.C_COMMAND){
            String dest=this.dest();
            String comp=this.comp();
            String jump=this.jump();
            binary="111"+Code.comp(comp)+Code.dest(dest)+Code.jump(jump);
            //System.out.println("Parse2: "+binary);
            return binary;
        }

        System.out.println("Parse returning null");
        return null;

    }

    public boolean advance(){
        try {
            nextLine = br.readLine();
        } catch (IOException ex) {
            System.out.println("IO Exception is hasmorecommands");
        }

        if(nextLine==null)
            return false;
        return true;
    }

    public int commandType(){
        nextLine=nextLine.trim();
        boolean aCommand=nextLine.startsWith("@");
        if(aCommand)
            return Parser.A_COMMAND;

        boolean lCommand=nextLine.startsWith("(");
        if(lCommand)
            return Parser.L_COMMAND;

        boolean cCommand1=nextLine.contains("=");
        boolean cCommand2=nextLine.contains(";");
        if(cCommand1 || cCommand2)
            return Parser.C_COMMAND;

        return -1; //no matches
    }

    public String symbol(){
        nextLine=nextLine.trim();
        String symbol;
        if(nextLine.startsWith("@")){
            symbol=nextLine.substring(1);
            if(Character.isLetter(symbol.charAt(0)))
                symbol=checkAndMaintainVariable(symbol);
            //System.out.println("Symbol: "+symbol);
            return symbol;
        }

        if(nextLine.startsWith("(")){
            symbol=nextLine.substring(1, nextLine.length()-1);
            //System.out.println("Symbol: "+symbol);
            return null;
        }

        System.out.println("Symbol: null");
        return null;
    }

    public String dest(){
        int pos=nextLine.indexOf("=");
        String dest="";
        if(pos!=-1){
            dest=nextLine.substring(0, pos);
            //System.out.println("Dest: "+dest);
            return dest;
        }

        pos=nextLine.indexOf(";");
        dest="";
        if(pos!=-1){
            dest=nextLine.substring(0, pos);
            if(Character.isDigit(dest.charAt(0)))
                return null;
           // System.out.println("Dest: "+dest);
            return dest;
        }

        System.out.println("Dest: null");
        return null;
    }//end dest

    public String comp(){
        int posEq=nextLine.indexOf("=");
        posEq++; 
        int posSemCol=nextLine.indexOf(";");
        if(posSemCol==-1)
            posSemCol=nextLine.length();

        String comp=nextLine.substring(posEq,posSemCol);
        //System.out.println("Comp: "+comp);
        return comp;
    }//end comp

    public String jump(){
        int posSemCol=nextLine.indexOf(";");
        if(posSemCol==-1){
            //System.out.println("Jump: null");
            return null;
        }
        String jump=nextLine.substring(posSemCol+1);
        //System.out.println("Jump: "+jump);
        return jump;
    }

    private String checkAndMaintainVariable(String variable){
        if(table.contains(variable))
            return table.getAddress(variable);
        table.addEntry(variable, ""+nextRam);
        return ""+nextRam++;
    }

}//end parser
