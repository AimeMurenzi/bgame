/**
 * @Author: Aimé
 * @Date:   2021-04-12 05:09:32
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 13:44:02
 */
package domain;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status; 
public class Util {
    private static String defaultPersistedFolder="bgame-data";
    public static String generateLocation(String fileName){
       Path defaultPersistedFolderPath= Paths.get(System.getProperty("user.dir"),defaultPersistedFolder,fileName);  
       File file=new File(defaultPersistedFolderPath.toString()) ;
       if( !file.getParentFile().exists())
       file.getParentFile().mkdirs();
        
    //    String finalString=defaultPersistedFolderPath.toString();
    //    File file=new File(System.getProperty("user.dir"), finalString);
    //    file.getParentFile().mkdirs();
        return defaultPersistedFolderPath.toString();
    }
    public static long generateID(){
        final long MIN_VALUE=1000000000000000000l; 
        long random = (long) (Math.random() * (Long.MAX_VALUE - MIN_VALUE) + MIN_VALUE); 
        return random;        
    } 
    
    /**
     *
     * @return String of 11 random you tube like characters using base 36 without
     *         capital letters
     */
    public static String generateYouTubeStyleNameID() {
        // <editor-fold defaultstate="collapsed" desc="base36-11characters">
        // windows doesn't care for upper and lower case
        // also i dont want to add unicode symbols
        // so that narrows the names down
        // stored in db sqlite uses utf-8 which uses 1-4 bytes per character in ascii
        // if you go more than ascii it adds more bytes
        // i want to get 11 character id so every id = 4 * 11 = 44 bytes
        // possible ids = 36 pow(11)= 1,3162×10¹⁷
        // </editor-fold>
        String base = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder finalString2= new  StringBuilder(); 
        int min = 0, max = 36;
        for (int i = 0; i < 11; i++) {
            int random = (int) ((Math.random() * (max - min)) + min);
            finalString2.append(base.charAt(random)); 
        }
        return finalString2.toString();
    }
    
    public static void throwBadRequest(String message){
        throw new WebApplicationException(message,Status.BAD_REQUEST.getStatusCode()); 
    }
}