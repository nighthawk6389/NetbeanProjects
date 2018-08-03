/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package job;

/**
 *
 * @author Mavis Beacon
 */
public abstract class Animal {

    private String name;
    public Animal(String nm){
        
    }

    public String getName(){
        return name;
    }
   public abstract String getAnimal();

}

class Cat extends Animal{

    public Cat(){
        super("");
    }
    @Override
    public String getAnimal() {
        return super.getName();
    }

}
