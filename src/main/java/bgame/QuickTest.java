package bgame;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class QuickTest {
    /**
     * InnerQuickTest
     */
    public static class InnerQuickTest {
    
        
    }

    public static void main(String[] args) {
         Object object= new InnerQuickTest();
       HashMap map=new HashMap<>(100);
     ArrayList arrayList=new ArrayList<>();
     arrayList.remove(1);
     LinkedList  linkedList=new LinkedList<>();
     linkedList.removeLast();
     LinkedHashSet  linkedHashSet=new LinkedHashSet<>();
     
       System.out.println(map.size());

        long [] my= new long[1000000];
  System.out.println(object.getClass().getSimpleName()); 


    }
}
