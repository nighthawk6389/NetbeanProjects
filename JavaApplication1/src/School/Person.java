
package School;


public class Person {
    
    String name;
    int age;

   public Person(String myName, int myAge){
        name=myName;
        age=myAge;
    }

    Person newPerson(String myName, int myAge){
        int error=0;
        if(myName.length()<3)
            error=1;
        else if(myName.length()>15)
            error=1;
        else if(age<0)
            error=1;
        else if(age>120)
            error=1;

        Person myPerson=null;
        if(error==0)
            myPerson=new Person(myName,myAge);

        return myPerson;
    }

      double discountedCost(double cost){
        double discount;
        if(cost<100)
            discount=0;
        else if(cost<250)
            discount=.10;
        else if(cost<500)
            discount=.15;
        else
            discount=.20;

        double discounted=cost-(cost*discount);

        return discounted;
    }

    double discountedCost2(double cost){
        double discount=0;
        if(cost<100)
            discount=0;
        if(cost>=100 && cost<250)
            discount=.10;
        if(cost>=250 && cost<500)
            discount=.15;
        if(cost>=500)
            discount=.20;

        double discounted=cost-(cost*discount);

        return discounted;
    }

    double quadratic(double x){
        double myQuad=(3*(x*x))-(8*x)+4;

        System.out.println("At x="+x+" the values is "+myQuad);
        return myQuad;
    }



}
