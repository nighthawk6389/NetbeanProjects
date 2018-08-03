package algorithms;
import java.util.*;


public class Sort {

	public static void main(String args []){
            Sort sort=new Sort();
            /**
			
			List <Integer> set=new ArrayList<Integer>();
			while(set.size()<10000){
				set.add((int)(Math.random()*200));
			}
             
                        
                        int [] array=new int[100];
                        for(int x=0;x<array.length;x++){
                            array[x]=(int)(Math.random()*100+1);
                            System.out.print(array[x]+" ");
                        }
             */
            int [] array={3,0,2,0,0,2,2,5,2,3,0,1,0};
              System.out.println();
                        int [] s=sort.bucketSort(array, 6);
                        System.out.println("S is ");
                        for(int x:s)
                            System.out.print(x+" ");
                         System.out.println();
	}
	public Sort(){
	
	}
	public Object[] insertSort(Object [] array){
		long time=System.currentTimeMillis();
		int y=(Integer)array[0];
		int j=0;
		for(int x=1;x<array.length;x++){
			y=(Integer)array[x];
			j=x-1;
			while(j>=0 && y<(Integer)array[j] ){
				array[j+1]=(Integer)array[j];
				j--;
			}//end while
			array[j+1]=y;
		}//end for
		long timeEnd=System.currentTimeMillis();
		long diff=timeEnd-time;
		System.out.print("Sorted Array is: ");
		for(Object i:array)
			System.out.print(i+" ");
		System.out.println();
		System.out.println("It took "+diff+" milliseconds");
		return array;
	}
	public Object[] insertSort(Set <Integer> set){
		long time=System.currentTimeMillis();
                Integer[] t=new Integer[10];
		Integer [] array=set.toArray(t);
		int y=array[0];
		int j=0;
		for(int x=1;x<array.length;x++){
			y=array[x];
			j=x-1;
			while(j>=0 && y<array[j] ){
				array[j+1]=array[j];
				j--;
			}//end while
			array[j+1]=y;
		}//end for
		long timeEnd=System.currentTimeMillis();
		long diff=timeEnd-time;
		System.out.print("Sorted Array is: ");
		for(Object i:array)
			System.out.print(i+" ");
		System.out.println();
		System.out.println("It took "+diff+" milliseconds");
		return array;
	}
	public List insertSort(List list){
		long time=System.currentTimeMillis();
		Object [] array=list.toArray();
		int y=(Integer)array[0];
		int j=0;
		for(int x=1;x<array.length;x++){
			y=(Integer)array[x];
			j=x-1;
			while(j>=0 && y<(Integer)array[j] ){
				array[j+1]=(Integer)array[j];
				j--;
			}//end while
			array[j+1]=y;
		}//end for
		long timeEnd=System.currentTimeMillis();
		long diff=timeEnd-time;
		System.out.print("Sorted Array is: ");
                List newArray=new ArrayList();
		for(Object i:array){
			System.out.print(i+" ");
                        newArray.add(i);
                }
		System.out.println();
		System.out.println("It took "+diff+" milliseconds");
		return newArray;
	}

        public int [] quickSort(int [] array, int left, int right){
            quickSortActual(array,left,right);
            return array;
        }

        private void quickSortActual(int [] array, int left, int right){
            if(right>left){
            int pivot=(int)(Math.random()*(right-left)+left);
             
             pivot=partition(array,left,right,pivot);
             
             quickSort(array,left,pivot-1);
             quickSort(array,pivot+1,right);
             }//end if
             System.out.println();
             
        }//end quickSort
        
        private int partition(int [] array, int left,int right,int pivotIndex){
            
             int pivotNumber=array[pivotIndex];
            //moving pivot to right and right to pivot
            int temp=array[pivotIndex];
            array[pivotIndex]=array[right];
            array[right]=temp;
           
            //moving all numbers less then pivot in front of pivot
            int store=left;
            for(int x=left;x<right;x++){
                if(array[x]<=pivotNumber){
                    temp=array[x];
                    array[x]=array[store];
                    array[store]=temp;
                    store++;
                }//end if
            }//end for
            
            temp=array[right];
            array[right]=array[store];
            array[store]=temp;
            
            return store;
        }//end partition

        public int [] countingSort(int [] array, int k){
            int [] buckets=new int[k];

            for(int x=0;x<array.length;x++){
                buckets[array[x]]++;
            }//end for

            int count=0;
            for(int x=0;x<k;x++){
                while(buckets[x]-- > 0)
                    array[count++]=x;
            }//end for

            return array;
        }//end countingSort

        public int [] bucketSort(int [] array, int k){
            System.out.println("array: "+array.length+" k: "+k);

            List [] bucket=new List[k];
            for(int x=0;x<array.length;x++){
                int hash=array[x]/(k/2);
                if(bucket[hash]==null)
                    bucket[hash]=new ArrayList();
                bucket[hash].add(array[x]);
            }//end for

            for(List l:bucket)
                System.out.println(l);

            int count=0;
            for(int x=0;x<k;x++){
                if(bucket[x]!=null){
                    bucket[x]=this.insertSort(bucket[x]);
                    for(int y=0;y<bucket[x].size();y++)
                        array[count++]=(Integer)(bucket[x].get(y));
                }//end if
            }//end for
            return array;
        }//end bucketSort   
}
