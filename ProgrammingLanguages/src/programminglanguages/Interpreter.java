/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package programminglanguages;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Mavis Beacon
 */
public class Interpreter {

    int error;
    String errorMessage;
    LexicalTokenizer lt;
    SymbolTable table=new SymbolTable();
    private final static String tempName="temp";

    public Interpreter(LexicalTokenizer lt){
        this.lt=lt;
    }//end interpreter

   public static void main(String args []){
        RecursiveDescent rd=new RecursiveDescent("test4.txt");
        Term t=rd.program();
        t.outputTerm();
        Interpreter i=new Interpreter(rd.tokenizer);
        i.run(t);
        System.out.println("\nError:"+rd.error);
    }
    

    public void run(Term term){
        breakdownTerm(term);

        outputTable();
        return;
    }//end run
    
    private SymbolTable breakdownTerm(Term term){
        //print("Enter Breakdown");
        if(term instanceof SimpleTerm){
            return getSimpleTerm((SimpleTerm)term);
        }
        if(term instanceof ComplexTerm)
            return getComplexTerm((ComplexTerm)term);
        Iterator it=term.list.iterator();
        //if(term.list.size()>1)
        //    print("size greater than 1");
        Term t;
        SymbolTable symTable=null;
        while(it.hasNext()){
            t=(Term)it.next();
            symTable=breakdownTerm(t);
        }
       // if(symTable!=null)
       //     print("Returning from breakdown: "+symTable.getFirstSymbol().toString());
       // else
       //     print("Returning from breakdown null");
        return symTable;
    }

    private SymbolTable getComplexTerm(ComplexTerm ct){
        //print("Enter getComplexTerm. "+ct.operator);
        int op=ct.operator;
        if(op==lt.INT)
            declareVariable(ct,lt.INT_LIT);
        else if(op==lt.CHAR)
            declareVariable(ct,lt.CHAR);
        else if(op=='=')
            assign(ct);
        else if(op==lt.IF)
            ifStmt(ct);
        else if(op==lt.WHILE)
            whileStmt(ct);
        else if(op==lt.COUT)
            cout(ct);
        else if(op==lt.CIN)
            cin(ct);
        else if(op==lt.LOE)
            return listOfExpressions(ct);
        else if(op==lt.LOV)
            return listOfVariables(ct);
        else if((op>32 && op<128) || (op>322 && op<350))
            return exp(ct);

        //print("Exit getComplexTerm "+ct.operator);
        return null;
    }

    private SymbolTable getSimpleTerm(SimpleTerm st){
        //print("Enter getSimpleTerm");
        Symbol s;
        Symbol s1=table.getSymbolWithName(st.last);
        String value=null;
        int type=lt.IDENT;
        if(s1!=null){
            value=s1.getValue();
            type=s1.getType();
        }
        if(st.type==lt.INT_LIT)
            s=new Symbol(lt.INT_LIT,Interpreter.tempName,st.last);
        else if(st.type==lt.IDENT)
            s=new Symbol(type,st.last,value);
        else {
            outputError("SimpleTerm Else!!!");
            s=null;
        }

        //print("SimpleTerm: "+s.toString());
        return new SymbolTable(s);
    }

    private SymbolTable assign(ComplexTerm ct){
       // print("Enter Assign");
        if(ct.list.size()>2){
            outputError("Assign: More than 2 terms in assign");
            return null;
        }
        Iterator it1=ct.list.iterator();
        SymbolTable expValueTable=breakdownTerm(((Term)it1.next()));
        Symbol expValueSymbol=expValueTable.getFirstSymbol();
        String expValue=expValueSymbol.getValue();

        SymbolTable variablesTable=breakdownTerm((Term)it1.next());
        Iterator it2=variablesTable.getTableIterator();
        String name;
        Symbol sym;
        while(it2.hasNext()){
            name=((Symbol)it2.next()).getName();
            sym=table.getSymbolWithName(name);
            if(expValueSymbol.getType()!=sym.getType()){
                outputError("Assign variable("+sym.getType()+") and value("+expValueSymbol.getType()+") not of same type. "+
                        sym.getName()+" "+expValueSymbol.getValue());
                return null;
            }
            sym.setValue(expValue);
            //print("Assign: "+sym.toString());
        }
       // print("Exit assign");
        return null;
    }

    private SymbolTable declareVariable(ComplexTerm ct,int type){
       // print("Enter Declare");
        Iterator it=ct.list.iterator();
        Symbol sym;
        while(it.hasNext()){
            sym=breakdownTerm((Term)it.next()).getFirstSymbol();
            table.addToTable(new Symbol(type,sym.getName(),null));
        }//end while
       // print("Exit Declare");
        return null;
    }//end declareVariable

    private SymbolTable exp(ComplexTerm ct){
       // print("Enter exp");
        int op=ct.operator;
        Symbol sym=null;
        switch(op){
            case '+':sym=disambiguePlus(ct).getFirstSymbol();;break;
            case '-':sym=disambigueMinus(ct).getFirstSymbol();break;
            case '*':sym=multiply(ct).getFirstSymbol();break;
            case '/':sym=divide(ct).getFirstSymbol();break;
            case '%':sym=modula(ct).getFirstSymbol();break;
            case '|':sym=singleOr(ct).getFirstSymbol();break;
            case '&':sym=singleAnd(ct).getFirstSymbol();break;
            case '!':sym=booleanNot(ct).getFirstSymbol();break;
            case '^':sym=bitwiseOr(ct).getFirstSymbol();break;
            case '~':sym=bitwiseNot(ct).getFirstSymbol();break;
            case '>':sym=relativeOperators(ct,'>').getFirstSymbol();break;
            case '<':sym=relativeOperators(ct,'<').getFirstSymbol();break;
        }
        if(op==lt.DOUBLEOR)
            sym=doubleOr(ct).getFirstSymbol();
        else if(op==lt.DOUBLEAND)
            sym=doubleAnd(ct).getFirstSymbol();
        else if(op==lt.BOOLEQUAL)
            sym=booleanEqual(ct).getFirstSymbol();
        else if(op==lt.NOTEQUAL)
            sym=booleanNotEqual(ct).getFirstSymbol();
        else if(op==lt.GREATERTHANEQ)
            sym=relativeOperators(ct,lt.GREATERTHANEQ).getFirstSymbol();
        else if(op==lt.LESSTHANEQ)
            sym=relativeOperators(ct,lt.LESSTHANEQ).getFirstSymbol();
        //print("Exit exp "+sym.toString());
        return new SymbolTable(sym);
    }

    private SymbolTable disambigueMinus(ComplexTerm ct){
        if(ct.list.size()==1)
            return unaryMinus(ct);
        return subtract(ct);
    }

    private SymbolTable disambiguePlus(ComplexTerm ct){
        if(ct.list.size()==1)
            return unaryPlus(ct);
        return add(ct);
    }

    private SymbolTable unaryMinus(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        return new SymbolTable(new Symbol(one.getType(),Interpreter.tempName,"-"+one.getValue()));
    }
    private SymbolTable unaryPlus(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        return new SymbolTable(new Symbol(one.getType(),Interpreter.tempName,"-"+one.getValue()));
    }

    private SymbolTable add(ComplexTerm ct){
        if(ct.list.isEmpty())
            return null;
        return actualCalculation(ct,'+');
    }

    private SymbolTable subtract(ComplexTerm ct){
        if(ct.list.isEmpty())
            return null;
        return actualCalculation(ct,'-');
    }
    private SymbolTable multiply(ComplexTerm ct){
        if(ct.list.isEmpty())
            return null;
        return actualCalculation(ct,'*');
    }
    private SymbolTable divide(ComplexTerm ct){
        if(ct.list.isEmpty())
            return null;
        return actualCalculation(ct,'/');
    }
    private SymbolTable modula(ComplexTerm ct){
        if(ct.list.isEmpty())
            return null;
        return actualCalculation(ct,'%');
    }

    private SymbolTable actualCalculation(ComplexTerm ct,int type){
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        print("ActaulCalc: "+one.toString());
        int currentValue=Integer.parseInt(one.getValue());
        while(it.hasNext()){
            one=breakdownTerm((Term)it.next()).getFirstSymbol();
            if(type=='+')
                currentValue+=Integer.parseInt(one.getValue());
            else if(type=='-')
                currentValue-=Integer.parseInt(one.getValue());
            else if(type=='*')
                currentValue*=Integer.parseInt(one.getValue());
            else if(type=='/')
                currentValue/=Integer.parseInt(one.getValue());
            else if(type=='%')
                currentValue%=Integer.parseInt(one.getValue());
        }
        //print("ActualCalc: "+currentValue+".");
        return new SymbolTable(new Symbol(one.getType(),Interpreter.tempName,currentValue+""));
    }

    private SymbolTable relativeOperators(ComplexTerm ct,int type){
        if(ct.list.size()>2){
            outputError("RelativeOperators: More than two terms");
            return null;
        }
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue1=Integer.parseInt(one.getValue());
        Symbol two=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue2=Integer.parseInt(two.getValue());
        int result;
        if(type=='>')
            result=(currentValue1>currentValue2)?1:0;
        else if(type=='<')
            result=(currentValue1<currentValue2)?1:0;
        else if(type==lt.GREATERTHANEQ)
            result=(currentValue1>=currentValue2)?1:0;
        else if(type==lt.LESSTHANEQ)
            result=(currentValue1<=currentValue2)?1:0;
        else
            result=0;
        return new SymbolTable(new Symbol(one.getType(),Interpreter.tempName,result+""));
    }

    private SymbolTable doubleOr(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue=Integer.parseInt(one.getValue());
        if(currentValue!=0)
            return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,currentValue+""));
         one=breakdownTerm((Term)it.next()).getFirstSymbol();
         currentValue=Integer.parseInt(one.getValue());
         if(currentValue!=0)
             return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,currentValue+""));
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,"0"));
    }

    private SymbolTable singleOr(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue=Integer.parseInt(one.getValue());
        int isTrue=0;
        if(currentValue!=0)
            isTrue=1;
        while(it.hasNext()){
            one=breakdownTerm((Term)it.next()).getFirstSymbol();
            currentValue=Integer.parseInt(one.getValue());
            if(currentValue!=0)
                isTrue=1;
        }
        int s=0;
        if(isTrue!=0)
            s=1;
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,s+""));
    }

    private SymbolTable singleAnd(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue=Integer.parseInt(one.getValue());
        int isTrue=1;
        if(currentValue==0)
            isTrue=0;
        while(it.hasNext()){
            one=breakdownTerm((Term)it.next()).getFirstSymbol();
            currentValue=Integer.parseInt(one.getValue());
            if(currentValue==0)
                isTrue=0;
        }
        int s=0;
        if(isTrue!=0)
            s=1;
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,s+""));
    }

    private SymbolTable doubleAnd(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue=Integer.parseInt(one.getValue());
        if(currentValue==0)
            return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,"0"));
        while(it.hasNext()){
            one=breakdownTerm((Term)it.next()).getFirstSymbol();
            currentValue=Integer.parseInt(one.getValue());
            if(currentValue==0)
                return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,"0"));
        }
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,"1"));
    }

    private SymbolTable booleanNot(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        if(ct.list.size()>1){
            outputError("BooleanNot: More than one term");
            return null;
        }
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue=Integer.parseInt(one.getValue());
        if(currentValue==0)
            currentValue=1;
        else
            currentValue=0;
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,currentValue+""));
    }

    private SymbolTable booleanEqual(ComplexTerm ct){
        if(ct.list.size()>2){
            outputError("Boolean Equal: More than two terms");
            return null;
        }
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue1=Integer.parseInt(one.getValue());
        Symbol two=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue2=Integer.parseInt(two.getValue());
        int result=0;
        if(currentValue1==currentValue2){
            print("Equal: "+currentValue1+" "+currentValue2);
            result=1;
        }
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,result+""));
    }

    private SymbolTable booleanNotEqual(ComplexTerm ct){
        if(ct.list.size()>2){
            outputError("Boolean Equal: More than two terms");
            return null;
        }
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue1=Integer.parseInt(one.getValue());
        Symbol two=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue2=Integer.parseInt(two.getValue());
        int result=0;
        if(currentValue1!=currentValue2)
            result=1;
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,result+""));
    }

    private SymbolTable bitwiseOr(ComplexTerm ct){
        if(ct.list.size()>2){
            outputError("BitwiseOR: More than two terms");
            return null;
        }
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue=Integer.parseInt(one.getValue());
        String s1=Integer.toBinaryString(currentValue);

        Symbol two=breakdownTerm((Term)it.next()).getFirstSymbol();
        currentValue=Integer.parseInt(two.getValue());
        String s2=Integer.toBinaryString(currentValue);

        int shorter=(s1.length()<s2.length())?s1.length():s2.length();
        String result="";
        for(int x=0;x<shorter;x++){
            if(s1.charAt(x)=='1' || s2.charAt(x)=='1')
                result+=1;
            else
                result+=0;
        }//end for
        int intResult=Integer.parseInt(result, 2);
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,intResult+""));
    }

    private SymbolTable bitwiseNot(ComplexTerm ct){
        if(ct.list.size()>1){
            outputError("BitwiseNOT: More than one term");
            return null;
        }
        Iterator it=ct.list.iterator();
        Symbol one=breakdownTerm((Term)it.next()).getFirstSymbol();
        int currentValue=Integer.parseInt(one.getValue());
        String s1=Integer.toBinaryString(currentValue);
        String result="";
        for(int x=0;x<s1.length();x++){
            if(s1.charAt(x)=='0')
                result+="1";
            else
                result+="0";
        }//end for
        int intResult=Integer.parseInt(result, 2);
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,intResult+""));
    }

    private SymbolTable ifStmt(ComplexTerm ct){
        if(ct.list.size()>3){
            outputError("ifStmt: More than three terms");
            return null;
        }
        Iterator it=ct.list.iterator();
        Symbol exp=breakdownTerm((Term)it.next()).getFirstSymbol();
        int expValue=Integer.parseInt(exp.getValue());
        //System.out.println("ifStmt Exp value: "+expValue);
        if(expValue!=0){
           // System.out.println("ifStmt Exp true");
            Symbol stmts=breakdownTerm((Term)it.next()).getFirstSymbol();
            //int stmtsValue=Integer.parseInt(stmts.getValue());
            return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,"0"));
        }
        if(ct.list.get(2)!=null){
            //System.out.println("ifStmt else");
            Symbol stmts=breakdownTerm(ct.list.get(2)).getFirstSymbol();
            //int stmtsValue=Integer.parseInt(stmts.getValue());
            return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,"0"));
        }
        return null;
    }

    private SymbolTable whileStmt(ComplexTerm ct){
       // print("Enter while");
        if(ct.list.size()>3){
            outputError("ifStmt: More than three terms");
            return null;
        }
        Iterator it=ct.list.iterator();
        Term expTerm=(Term)it.next();
        Symbol exp=breakdownTerm(expTerm).getFirstSymbol();
        int expValue=Integer.parseInt(exp.getValue());
        SymbolTable stmtsTable;
        Term stmtsTerm=(Term)it.next();
        while(expValue==1){
           // print("in while loop");
            stmtsTable=breakdownTerm(stmtsTerm);
            exp=breakdownTerm(expTerm).getFirstSymbol();
            expValue=Integer.parseInt(exp.getValue());
            print("while: "+expValue);
        }
        //print("Exit while");
        return new SymbolTable(new Symbol(lt.INT_LIT,Interpreter.tempName,"0"));
    }

    private SymbolTable listOfVariables(ComplexTerm ct){
        //print("Enter LOV");
        Iterator it=ct.list.iterator();
        SymbolTable variablesTable=new SymbolTable();
        Symbol sym;
        while(it.hasNext()){
            sym=breakdownTerm((Term)it.next()).getFirstSymbol();
            variablesTable.addToTable(sym);
        }
        //print("Exit LOV");
        return variablesTable;
    }

    private SymbolTable listOfExpressions(ComplexTerm ct){
        //print("Enter LOE");
        Iterator it=ct.list.iterator();
        SymbolTable variablesTable=new SymbolTable();
        Symbol sym;
        while(it.hasNext()){
            sym=breakdownTerm((Term)it.next()).getFirstSymbol();
            variablesTable.addToTable(sym);
        }
        //print("Exit LOE");
        return variablesTable;
    }
    private SymbolTable cin(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        SymbolTable symTable=breakdownTerm((Term)it.next());
        it=symTable.getTableIterator();
        Symbol userSymbol;
        String name;
        Symbol sym;
        while(it.hasNext()){
            userSymbol=(Symbol)it.next();
            String userValueString=JOptionPane.showInputDialog("Input value for "+userSymbol.getName());
            userSymbol.setValue(userValueString);
            name=userSymbol.getName();
            sym=table.getSymbolWithName(name);
            if(userSymbol.getType()!=sym.getType()){
                outputError("Assign variable("+sym.getType()+") and value("+userSymbol.getType()+") not of same type. "+
                        sym.getName()+" "+userSymbol.getValue());
                return null;
            }
            sym.setValue(userSymbol.getValue());
            System.out.println("Cin: "+userSymbol.toString());
        }
        return null;
    }
    private SymbolTable cout(ComplexTerm ct){
        Iterator it=ct.list.iterator();
        SymbolTable symTable=breakdownTerm((Term)it.next());
        it=symTable.getTableIterator();
        Symbol one;
        String name;
        int currentValue;
        while(it.hasNext()){
            one=(Symbol)it.next();
            name=one.getName()+"=";
            if(name.equals("temp="))
                name="";
            currentValue=Integer.parseInt(one.getValue());
            JOptionPane.showMessageDialog(null,name+" "+currentValue+"");
            System.out.println("Cout: "+currentValue);
        }
        return null;
    }

    private void outputError(String errorMessage){
        this.errorMessage=errorMessage;
        System.out.println(errorMessage);
    }

    private void outputTable(){
        System.out.println(table);
    }

    private void print(String s){
        System.out.println(s);
    }

}
