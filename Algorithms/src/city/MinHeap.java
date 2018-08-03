/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package city;

/**
 *
 * @author Mavis Beacon
 */
public class MinHeap {
    public static int SIZE;

    public static void main(String args[]){
        HuffmanNode [] array=new HuffmanNode[10];
        int [] numbers={16,14,10,8,7,9,3,2,4,1};
        for(int x=0;x<array.length;x++){
            array[x]=new HuffmanNode(numbers[x]);
        }

        for(int x=0;x<array.length;x++)
            System.out.print(array[x].freq+" ");
        System.out.println();
        
        array=MinHeap.HEAP_SORT(array);

        for(int x=0;x<array.length;x++)
            System.out.print(array[x].freq+" ");
        System.out.println();
    }

    public static void outputArray(HuffmanNode [] A){
        for(int x=0;x<A.length;x++)
            System.out.print(A[x].c+"="+A[x].freq+" ");
        System.out.println();
    }
    public static void outputArray(int [] A){
        for(int x=0;x<A.length;x++)
            System.out.print(A[x]+" ");
        System.out.println();
    }

    //public static void setHeap(int [] A){
       // MinHeap.A=A;
   // }

   // public static int[] getHeap(){
        //return A;
   // }

    public static int PARENT(int i){
        return i/2;
    }

    public static int LEFT(int i){
        return 2*i;
    }

    public static int RIGHT(int i){
        return 2*i+1;
    }

    public static HuffmanNode[] HEAP_SORT(HuffmanNode [] A){
        A=BUILD_MIN_HEAP(A);

       System.out.println("After Build HEap");
          for(int x=0;x<A.length;x++)
            System.out.print(A[x].freq+" ");
        System.out.println();


        System.out.println("Size:"+SIZE+" A.length="+A.length);
        for(int i=A.length-1;i>0;i--){
            HuffmanNode temp=A[0];//new HuffmanNode(A[0].freq);
            A[0]=A[i];//new HuffmanNode(A[i].freq);
            A[i]=temp;//new HuffmanNode(temp.freq);
            SIZE=SIZE-1;

            MIN_HEAPIFY(A,0);
        }
        return A;
    }//end HS

    public static HuffmanNode [] BUILD_MIN_HEAP(HuffmanNode [] A){
        //setHeap(A);
        SIZE=A.length;
        for(int i=((A.length-1)/2);i>=0;i--){
            MIN_HEAPIFY(A,i);
        }
        return A;
    }
    public static void MIN_HEAPIFY(HuffmanNode [] A,int i){
        int l=LEFT(i);
        int r=RIGHT(i);
        int smallest;
        if(l<SIZE && A[l].freq<A[i].freq)
            smallest=l;
        else
            smallest=i;
        if(r<SIZE && A[r].freq<A[smallest].freq)
            smallest=r;
        //System.out.println("smallest:"+smallest+" i:"+i);
        if(smallest!=i){
            HuffmanNode temp=A[i];
            A[i]=A[smallest];
            A[smallest]=temp;
            MIN_HEAPIFY(A,smallest);
        }

    }//end MH

    public static int HEAP_MINIMUM(HuffmanNode [] A){
        return A[0].freq;
    }//HM

    public static int HEAP_EXTRACT_MIN(HuffmanNode [] A){
       if(SIZE<1){
           System.out.println("Heap Underflow");
           return -1;
       }
       int min=A[0].freq;
       A[0]=A[SIZE-1];
       SIZE=SIZE-1;
       MIN_HEAPIFY(A,0);
       return min; 
    }//end HEM

    public static void HEAP_DECREASE_KEY(HuffmanNode [] A,int i,HuffmanNode key){
        if(key.freq<A[i].freq){
            System.out.println("New Key is smaller than current");
            return;
        }
        A[i].freq=key.freq;
        HuffmanNode temp;
        //System.out.println(PARENT(i)+" i="+i);
        while(i>1 && A[PARENT(i)].freq>A[i].freq){
            temp=A[i];
            A[i]=A[PARENT(i)];
            A[PARENT(i)]=temp;
            i=PARENT(i);
        }
    }//HIK

    public static void MIN_HEAP_INSERT(HuffmanNode [] A,HuffmanNode key){
        SIZE=SIZE+1;
        A[SIZE-1]=new HuffmanNode(-10000000);
        HEAP_DECREASE_KEY(A,SIZE-1,key);

    }//end MHI


    ///////////////////////////////////////////////////////////////////////////
    //////////////////////BEGIN INT HEAP///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    public static int [] BUILD_MIN_HEAP(int [] A){
        //setHeap(A);
        SIZE=A.length;
        for(int i=((A.length-1)/2);i>=0;i--){
            MIN_HEAPIFY(A,i);
        }
        return A;
    }
    public static void MIN_HEAPIFY(int [] A,int i){
        int l=LEFT(i);
        int r=RIGHT(i);
        int smallest;
        if(l<SIZE && A[l]<A[i])
            smallest=l;
        else
            smallest=i;
        if(r<SIZE && A[r]<A[smallest])
            smallest=r;
        System.out.println("smallest:"+smallest+" i:"+i);
        if(smallest!=i){
            int temp=A[i];
            A[i]=A[smallest];
            A[smallest]=temp;
            MIN_HEAPIFY(A,smallest);
        }
    }//end MH

    public static int HEAP_MINIMUM(int [] A){
        return A[0];
    }//HM

    public static int HEAP_EXTRACT_MIN(int [] A){
       if(SIZE<1)
           System.out.println("Heap Underflow");
       int min=A[0];
       A[0]=A[SIZE];
       SIZE=SIZE-1;
       MIN_HEAPIFY(A,0);
       return min;
    }//end HEM

    public static void HEAP_DECREASE_KEY(int [] A,int i,int key){
        if(key<A[i]){
            System.out.println("New Key is smaller than current");
            return;
        }
        A[i]=key;
        int temp;
        while(i>1 && A[PARENT(i)]>A[i]){
            temp=A[i];
            A[i]=A[PARENT(i)];
            A[PARENT(i)]=temp;
            i=PARENT(i);
        }
    }//HIK

    public static void MIN_HEAP_INSERT(int [] A, char key){
        SIZE=SIZE+1;
        A[SIZE]=-10000000;
        HEAP_DECREASE_KEY(A,SIZE,key);

    }//end MHI




}//end MinHeap
