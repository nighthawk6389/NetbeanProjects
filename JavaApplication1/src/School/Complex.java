package School;
/*
 * Complex.java
 *
 * Created on September 22, 2009, 6:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.util.Scanner;

/**
 *
 * @author elkobi
 */
public class Complex{
    private int real;
    private int fake;
    
    public static void main(String args []){
        Complex one=new Complex(5,7);
        Complex two=new Complex(0,8);
        Complex three=new Complex("5 + 11i");
        System.out.println(one.add(two));
        System.out.println(one.subtract(two));
        System.out.println(one.multiply(two));
        System.out.println(three);
    }
    /** Creates a new instance of Complex */
    public Complex() {
        real=0;
        fake=0;
    }
    public Complex(int real,int fake){
        this.real=real;
        this.fake=fake;
    }
    public Complex(String string){
        Scanner s= new Scanner(string);
        String type="";
        this.real=0;
        if(s.hasNextInt()){
            this.real=s.nextInt();
            type=s.next();
        }
        String all=s.next();
        System.out.println(all.indexOf('i')+" "+all);
        String number=all.substring(0,all.indexOf('i'));
        fake=Integer.parseInt(number);
        
        if(type=="-")
            fake=-1*fake;
        
    }
    public int getReal(){
        return this.real;
    }
    public int getFake(){
        return this.fake;
    }
    public Complex add(Complex num2){
        int sumReal=this.real+num2.getReal();
        int sumFake=this.fake+num2.getFake();
        
        return new Complex(sumReal,sumFake);
    }
    public Complex subtract(Complex num2){
        int sumReal=this.real-num2.getReal();
        int sumFake=this.fake-num2.getFake();
        
        return new Complex(sumReal,sumFake);
    }
    public Complex multiply(Complex num2){
        int product1=this.real*num2.getReal();
        int product2=this.real*num2.getFake();
        int product3=this.fake*num2.getReal();
        int product4=-1*this.fake*num2.getFake();
        
        int sumFirst=product1+product4;
        int sumSecond=product2+product3;
        return new Complex(sumFirst,sumSecond);
    }
    public Complex multiply(int num){
        int product1=this.real*num;
        int product2=this.fake*num;
        return new Complex(product1,product2);
    }
    public String toString(){
        if(this.fake<0)
            return this.real+""+this.fake+"i";
        else 
            return this.real+"+"+this.fake+"i";
    }
    
    
}
