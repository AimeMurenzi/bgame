/**
 * @Author: Aimé
 * @Date:   2021-04-07 20:22:12
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-08 01:03:47
 */
package domain;

import java.util.LinkedList;
import java.util.Queue;

public class IDGenerator {
    private long counter;
    private final String saveName = "bgame.idGen.txt";
    private IDGenerator(){
        this.counter=open(saveName);
    }
    public static long increment(){
        ++getInstance().counter;
        getInstance().save(getInstance().counter, getInstance().saveName);
        return getInstance().counter;
    }
    private boolean saving = false; 
    private Queue<Boolean> saveQueue = new LinkedList<>();

    private void saveComplete() {
        // if not saving and there is still something on queue
        if (!saving && saveQueue.poll() != null){
            saveQueue.clear();
            save(this.counter,this.saveName);
        }
            
    }
    private void save(long counter,String saveName) {
        if(!saving){
            new Thread(
                ()->{
                    saving=true;
                    BGameSettings.save(saveName, Long.toString(counter)); 
                    saving=false;
                    saveComplete();
                }
            ).start();
        }else{
            saveQueue.add(false);
        }
       
    }
    private long open(String saveName) {
        String counterString=BGameSettings.open(saveName);
        long count=0;
        try {
             count=Long.parseLong(counterString);
        } catch (NumberFormatException nfe) {             
        }
       
        return count;
    }
    private static class HoldInstance {
        private static final IDGenerator INSTANCE = new IDGenerator();
    }

    public static IDGenerator getInstance() {
        return HoldInstance.INSTANCE;
    }

}
