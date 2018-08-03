/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

/**
 *
 * @author Mavis Beacon
 */
public class MedianSort {

          /**
	public void medianSort(List<Integer>set,int left,int right){
		if(left<right){
			System.out.println("In medianSort left is less "+left+" "+right);
		int indexMean=getMeanIndex(set,right,left);
		System.out.println("IndexMean= "+indexMean);
		int meanValue=set.get(indexMean);
		System.out.println("Meanvalue= "+meanValue);
		int setMiddle=left+(int)Math.ceil((right-left-1)/2);
		System.out.println("Middle is: "+setMiddle);
		int temp=set.get(setMiddle);
		set.set(setMiddle,meanValue);
		set.set(indexMean, temp);
		System.out.println("After exhanged mid and mean= "+set);
		//////
		int kIndex;
		for(int x=left;x<setMiddle;x++){
			if(set.get(x)>meanValue){
				kIndex=findIndexOfLessThanMean(set.get(x),meanValue,set,right,setMiddle);
				if(kIndex==-1)
					break;
				System.out.println("In Loop Kindex"+kIndex);
				temp=set.get(kIndex);
				set.set(kIndex,set.get(x));
				set.set(x,temp);
			}
		}
		System.out.println("After kIndex= "+set);
		System.out.println("Going to median sort Left "+left+" setMiddle "+setMiddle+" right "+right);
		medianSort(set,left,setMiddle-1);
		medianSort(set,setMiddle+1,right);
		}
	}
	private int getMeanIndex(List<Integer> set,int right,int left){
		int sum=0;
		for(int x=left;x<right;x++)
			sum+=set.get(x);
		int mean=(sum)/(right-left);
		System.out.println("mean is "+mean);
		int indexMean;
		if(set.indexOf(mean)!=-1)
			indexMean=set.indexOf(mean);
		else
		{
			indexMean=findClosestNumber(mean,set,right,left);
		}
		return indexMean;
	}
	public int findClosestNumber(int mean, List <Integer> set,int right,int left){
		//System.out.println("In findClosestNumber");
		int closest=-1;
		int index=-1;
		for(int x=left;x<right;x++){
			int diff=Math.abs(set.get(x)-mean);
			//System.out.println("("+set.get(x)+") -("+mean+")="+diff);
			if(closest==-1 || diff<closest){
				//System.out.println("Changing closet old Close was "+closest+" New diff "+diff);
				closest=diff;
				index=x;
			}
		}
		return index;
	}//end find closestNumber

	public int findIndexOfLessThanMean(int valueToCheck,int meanValue,List<Integer>set,int right,int left){
		for(int x=left;x<right;x++){
			//System.out.println("in FIOLTM  get(x)= "+set.get(x)+" meanValue "+meanValue);
			if(set.get(x)<meanValue && set.get(x)!=-1){
				//System.out.println("Returning x"+x);
				return x;
			}

		}
		return -1;
	}//end findIndexOfLessThanMean

         **/
}
