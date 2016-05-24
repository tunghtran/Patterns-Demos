
import java.util.Arrays;


public class StrategyPatternDemo {

    public static void main(String[] args) {
        int data[] = {3,6,4,6,7,8,5,6,7,5,3,3,1,2,3,4,5,6,10,11,7,8,9,12,35,76,34,23,87,5,54,13,
            4,84,23,54,81,39,53,85,19,34,56,97,13,53,76,31,65};
//        int data[] = {3,6,4,6,7,8,5,6,7,5,3};
        System.out.println(Arrays.toString(data));
        SortingContext sc = new SortingContext();
        int sortedList[] = sc.sort(data);
        System.out.println(Arrays.toString(sortedList));
    }

}

class SortingContext{
    private SortingStrategy ss;
    public int[] sort(int[] data){
        int size = data.length;
        ss = setSortStrategy(size);
        return ss.sort(data);
    }
    
    public SortingStrategy setSortStrategy(int n){
        if(n > 0 && n < 30){
            ss = new BubbleSort();
        }
        
        if(n>=30 && n < 100){
            ss = new InsertionSort();
        }
        
        if (n>=100){
            ss = new QuickSort();
        }
        
        return ss;
    }
}

interface SortingStrategy{
    public int[] sort(int[] data);
}

class BubbleSort implements SortingStrategy{
    @Override
    public int[] sort(int[] data){
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < data.length - j; i++) {                                       
                  if (data[i] > data[i + 1]) {                          
                        tmp = data[i];
                        data[i] = data[i + 1];
                        data[i + 1] = tmp;
                        swapped = true;
                  }
            }                
        }
        return data;
    }
}

class InsertionSort implements SortingStrategy{
    @Override
    public int[] sort(int[] data){
        for(int i = 1; i < data.length; i++){
            int temp = data[i];
            int j;
            for(j = i-1; j>=0 && temp < data[j]; j--){
                data[j+1] = data[j];
            }
            data[j+1] = temp;
        }
        return data;
    }
}

class QuickSort implements SortingStrategy{
    @Override
    public int[] sort(int[] data){
        return data;
    }
}