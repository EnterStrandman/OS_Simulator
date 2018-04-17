/* MATT STRAND
 * 4/16/2018
 *
 *
 */
package pcb;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Interface {
    String version;
    String date;
    String year;
    String month;
    String day;
    Vector fileSet = new Vector();    
    
    //Constructors
    public Interface(){
        this.version = "0.0.0";  
    }
    
    public Interface(String version){
        this.version = version;   
    }
    
    //Setters and Getters
    void setVersion(String versionNum){
        this.version = versionNum;
    }
    String getVersion(){
        return version;
    }
    
    String getYear(){
        return year;
    }
    
    void setYear(String year){
        this.year = year;
    }
    
    String getMonth(){
        return month;
    }
    
    void setMonth(String month){
        this.month = month;
    }
    String getDay(){
        return day;
    }
    
    void setDay(String day){
        this.day = day;
    }
    
    void addFile(String newFile){
        fileSet.addElement(newFile);
    }
    void printFileNames(){
        System.out.print(fileSet.toString());
    }

}