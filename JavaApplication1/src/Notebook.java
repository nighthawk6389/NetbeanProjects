
import java.util.ArrayList;
import java.util.Iterator;

public class Notebook {

    private ArrayList<String> notes;

    public static void main(String args []){
        Notebook n=new Notebook();
        n.storeMultipleNotes();
        n.removeWithString("Nobody");
        n.listNotes();

       // n.storeMultipleNotes();
        n.removeWithString("test");
        n.listNotes();

       // n.storeMultipleNotes();
        n.removeWithString("t");
        n.listNotes();
    }

    public Notebook(){
        notes= new ArrayList<String>();
    }//end notebook

    public void storeNote(String note){
        notes.add(note);
    }
    public void storeMultipleNotes(){
        notes.add("This is a test");
        notes.add("So is this");
        notes.add("Me too");
    }
    public void removeNote(int noteNumber){
        if(noteNumber<0){
            System.out.println("The index was out of bounds");
        }
        else if(noteNumber < numberOfNotes()){
            notes.remove(noteNumber);
        }
        else
            System.out.println("The index was out of bounds");
    }
    public void listNotes(){
        System.out.println("Amount of notes:"+numberOfNotes());
        int index=0;
        for(String note:notes){
            System.out.println(index+": "+note);
            index++;
        }
    }
    public int numberOfNotes(){
        return notes.size();
    }
    public void showNotes(int noteNumber){
        if(noteNumber<0){
            System.out.println("Bad");
        }
        else if(noteNumber < numberOfNotes() ){
            System.out.println(notes.get(noteNumber));
        }
        else{

        }
    }

    public void markDone(int noteNumber){
        String newString=notes.get(noteNumber)+"[Done!]";
        notes.set(noteNumber, newString);
    }

    public void sum(int a,int b){

        if(a>b){
            System.out.println("A is bigger then B");
        }
        else{
            int sum=0;
            a=a+1;
            while(a<b){
                sum=a;
                a=a+1;

            }
            System.out.println(sum);
        }
    }

    public void isPrime(int n){
        int index=2;
        boolean prime=true;
        while(index<n-1){
            if(n%index==0){
                prime=false;
            }
            else{
                index++;
            }
        }
        System.out.println("The number is prime:"+prime);
    }

    public void search(String searchString){
        int index=0;
        boolean found=false;
        while(index<notes.size() && !found){
            String note=notes.get(index);
            if(note.contains(searchString)){
                found=true;
            }
            else{
               index++;
            }
        }
        if(found){
            System.out.println("The search term was found");
        }
        else{
            System.out.println("Search term not found");
        }
    }

    public void removeWithString(String s){
        Iterator <String> it=notes.iterator();
        while(it.hasNext()){
            String note=it.next();
            if(note.contains(s))
                it.remove();
        }
    }
}
