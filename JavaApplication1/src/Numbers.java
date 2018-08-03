import java.util.*;
import javax.swing.JOptionPane;
public class Numbers{
    static String names[];
    static String tens[];
    static String big[];
    
    
    public static void main(String args []){
        System.out.println(Numbers.displayNumber("one million eighty thousand eighty three"));
    }
    
    public Numbers(){
    }
    
    public Numbers(int number){
       
    }
    public static void setArrays(){
        names=new String[20];
        names[0]=""; 
        names[1]="one"; 
        names[2]="two"; 
        names[3]="three"; 
        names[4]="four"; 
        names[5]="five"; 
        names[6]="six";
        names[7]="seven";
        names[8]="eight";
        names[9]="nine";
        names[10]="ten";
        names[11]="eleven";
        names[12]="twelve";
        names[13]="thirteen";
        names[14]="fourteen";
        names[15]="fifteen";
        names[16]="sixteen";
        names[17]="seventeen";
        names[18]="eighteen";
        names[19]="nineteen";
        
         tens=new String[10];
        tens[0]="";
        tens[1]="ten";
        tens[2]="twenty";
        tens[3]="thirty";
        tens[4]="forty";
        tens[5]="fifty";
        tens[6]="sixty";
        tens[7]="seventy";
        tens[8]="eighty";
        tens[9]="ninety";
        
         big=new String[5];
        big[0]="hundred";
        big[1]="thousand"; 
        big[2]="million"; 
        big[3]="billion"; 
        big[4]="trillion"; 
    }
    public static  String displayWord(int number){
        setArrays();
        Integer numString=new Integer(number);
        String string=numString.toString();
        int size=string.length();
        String temp="";
        for(int x=0;x<size;x++){
            temp+=number%10;
            number=number/10;
           // System.out.println("Reversing: Temp= "+temp+" Number="+number);
        }
        int tempNumber=Integer.parseInt(temp);
        int array[]=new int[size];
        for(int x=0;x<size;x++){
            array[x]=tempNumber%10;
            tempNumber=tempNumber/10;
            //System.out.println("Array["+x+"]= "+array[x]);
        }
      
 
        String word="";
        size=array.length;
        int numsAfter;
        boolean suffix=false;
       //System.out.println("Size= "+size);
        for(int x=0;x<size;x++){
            //System.out.println("In For");
            numsAfter=(size-x)%3;
            if(numsAfter==0 && array[x]!=0){
                word+=" "+names[array[x]];
                word+=" "+big[0];
               //System.out.println("If: numsAfter==0 "+word);
            }
            else if(numsAfter==1){
                word+=" "+names[array[x]];
                suffix=true;
               // System.out.println("Elseif: numsafter==1 "+word);
            }
            else if(numsAfter==2){
                //System.out.println("elseif: numsafter==2 "+word);
                if(array[x]==1){
                    x++;
                    word+=" "+names[10+array[x]];
                    suffix=true;
                   //System.out.println("elseif: numsafter==2 teens "+word);
                }
                else{
                    word+=" "+tens[array[x]];
                   // System.out.println("elseif: numsafter==2 regular "+word);
                }
            }
            else{
                //System.out.println("No Remainder good "+numsAfter+" "+word);
            }
            if(suffix && (size-x)>3){
               // System.out.println(array[x]+" "+array[x-1]+" "+array[x-2]);
                if(array[x]!=0 || array[x-1]!=0 || array[x-2]!=0){
                    word+=" "+big[(size-x)/3];
                    suffix=false;
                    //System.out.println("suffix "+word);
                }
                
            }
        }
        //System.out.println("Word: "+word);
        return word;
    }//end displayWord
    
    public static double displayNumber(String number){
        setArrays();
        
        System.out.println("number= "+number); 
        
        Scanner s=new Scanner(number);
        List l=new ArrayList();
        String reverse="";
        while(s.hasNext())
            l.add(s.next());
        
        ListIterator li=l.listIterator(l.size());
        while(li.hasPrevious())
            reverse+=" "+li.previous();
        
        System.out.println("After Scanner= "+reverse);
        
        s=new Scanner(reverse);
        String word;
        int num;
        int index=0;
        boolean found=false;
        int [] array=new int[2];
        String single="";
        while(s.hasNext()){
        single=s.next();
        System.out.println("single= "+single);
            //looking for singles
        for(int x=0;x<names.length;x++){
            System.out.println("In names[x]="+names[x]+" single="+single);
            if(names[x].equals(single)){
                if(x>9){
                    array[index]=1;
                    array[index]=x-10;
                }
                else {
                array[index]=x;
                index++;
                System.out.println("Found at= "+x+". Array[index] is "+array[index-1]+". The index is "+index);
                found=true;
                break;
                }
            }
        }
        if(found){
            found=false;
            continue;
        }
            //looking through tens
        for(int x=0;x<tens.length;x++){
            if(tens[x].equals(single)){
                if(index%3==0){
                    array[index]=0;
                    array[++index]=x;
                    index++;
                    System.out.println("In tens with NO zero Found at= "+x+" which is "+tens[x]+". The index is "+index);
                }
                else{
                    array[index]=x;
                    index++;
                    System.out.println("In tens WITH zero Found at= "+x+" which is "+tens[x]+". The index is "+index);
                }
                
                found=true;
                break;
            }
        }
        
        if(found){
            found=false;
            continue;
        }
        
         for(int x=0;x<big.length;x++){
            System.out.println("In big");
            if(big[x].equals(single)){
                int size=(x+1)*3;
                 if(size>array.length){
                      int [] temp=new int[size];
                      temp=array.clone();
                      array=new int[size];
                      for(int y=0;y<temp.length;y++)
                          array[y]=temp[y];
                    }   
                System.out.println("In BIG    Found at= "+x+" which is "+big[x]+". The index is "+index+" Array length is "+array.length);
                if((index+2)%3==0 && x==0)
                    index++;
                else if((index+3)%3==0 && x==0)
                    index+=2;
                else if(x!=0)
                    index=x*3;
                System.out.println("Index is now "+index);
                break;
            }
         }
        }//end while
        
        System.out.println();
        String theNumber="";
        boolean bool=false;
        for(int x=array.length-1;x>=0;x--){
            if(array[x]==0)
                if(bool==false)
                    continue;
            bool=true;
            theNumber+=array[x]+"";
            System.out.print(array[x]+"");
        }
        System.out.println();
        return new Double(Double.parseDouble(theNumber));
    }//end displayNumber
    
}
