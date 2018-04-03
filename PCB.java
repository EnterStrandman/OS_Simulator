/*
 * Matt Strand
 * PCB
 * 2/19/2018
 * RESOURCES:
 * https://www.tutorialspoint.com/java/util/priorityqueue_iterator.htm
 * https://www.tutorialspoint.com/java/java_arrays.htm
 * 
 */
package pcb;

import java.util.*;

public class PCB {

    public static void main(String[] args) {
        //DATA STRUCTURES
        Process[] memory; //this is how the memory will be stored as processes
        LinkedList<Process> ready = new LinkedList<Process>();
        LinkedList<Process> blocked = new LinkedList<Process>();
        
        //MORE DATA FOR SET UP
        Scanner sc = new Scanner(System.in); //get user input
        System.out.print("Type the max memory you would like to use: ");
        int userMax = sc.nextInt(); //the size of memory 
        int remainingMemory = userMax;
        memory = new Process[userMax];
        boolean menu = true;

        while(menu){
            instrutionsMain();
            int userChoice = sc.nextInt();
            
            //MAIN FUNCTIONALITY BELOW
            if(userChoice == 1){
                //CREATE PROCESS
                System.out.print("Type the process id(needs to be an int):");
                int userID = sc.nextInt();
                System.out.print("Type the memory size(needs to be an int):");
                int userMemSize = sc.nextInt();
                createProcess(userID, userMemSize, remainingMemory, memory, ready, blocked);
            }else if(userChoice == 2){
                //DELETE PROCESS
                System.out.println("Type the ID of the process to be deleted:");
                int userDelete = sc.nextInt();
                if(deleteProcess(userDelete, memory, ready, blocked)){
                    System.out.println("The process has been removed.");
                } else{
                    System.out.println("The process was not found. Check your input and try again.");
                }
            }else if(userChoice == 3){
                //BLOCK
                System.out.print("Type the ID of the process to be deleted:");
                int userBlock = sc.nextInt();
                if(blockProcess(userBlock,memory,ready, blocked)){
                    System.out.println("The process has been moved to the blocked queue.");
                }else{
                    System.out.println("Failure. Check your input and try again.");
                }
            }else if(userChoice == 4){
                //UNBLOCK
                System.out.print("Type the ID of the process to be unblocked:");
                int unblock = sc.nextInt();
                if(unblockProcess(unblock, memory, ready, blocked)){
                    System.out.println("The process has been moved from the blocked queue to the ready queue:");
                }else{
                    System.out.println("Failure. Check your input and try again.");
                }
            }else if(userChoice == 5){
                //SHOW SINGLE PROCESS INFO
                System.out.print("Type the process ID to have the information displayed:");
                int showOne = sc.nextInt();
                if(showProcess(showOne, memory, ready, blocked)){
                    System.out.println();
                }else{
                    System.out.println("Failure. Check your input and try again.");
                }
            }else if(userChoice == 6){
                //SHOW ALL PROCESS INFO
                showAll( memory, ready, blocked);
            }else if(userChoice == 7){
                //SHOW READY
                showReady(ready);
            }else if(userChoice == 8){
                //SHOW BLOCKED
                showBlocked(blocked);
            }else if(userChoice == 9){
                //GENERATE RANDOM PROCESSES
                System.out.print("How many processes would you like to generate?:");
                int numRandProcesses = sc.nextInt();
                generateProcesses(numRandProcesses,userMax,memory,ready, blocked);
                System.out.println("The process generator has finished.");
            }else if(userChoice == 10){
                //EXECUTE
                execute(memory,ready,blocked);
                System.out.println("Execute has completed.");
                
            }else if(userChoice == 11){
                sc.nextLine();
                System.out.print("Are you sure you want to exit?(1(y)/2(n)):");
                int exiter = sc.nextInt();
                if(exiter == 1){
                    System.out.println("Goodbye.");
                    menu = false;
                } else{
                    System.out.println("Yay! Thanks for staying.");
                }
            }else{
                System.out.println("Invalid Input");
            }
            
        }
    }
    
    public static void instrutionsMain(){
        System.out.println("1.Create Process");
        System.out.println("2.Delete Process");
        System.out.println("3.Block Process");
        System.out.println("4.Unblock Process");
        System.out.println("5.Show Specific Process");
        System.out.println("6.Show All Information");
        System.out.println("7.Show Ready Queue");
        System.out.println("8.Show Blocked Queue");
        System.out.println("9.Generate Random Processes");
        System.out.println("10.Execute PCB Cycle");
        System.out.println("11.Exit");
        System.out.print("Type the corresponding number to the process:");
    }
    
    public static boolean createHelper(int uniqueID, int memory, int remainingMem, Process[] memSet, LinkedList<Process> ready, LinkedList<Process> blocked){
       Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
       Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained
       boolean creator = true;
        
        while(creator){
            //this checks if there is enough memory
            if(memory < remainingMem){
                //these 2 while loops and the for loop check that the id is unique
                while(itReady.hasNext()){
                    Process p = itReady.next();
                    if (p.getIdNum() == uniqueID) {
                        System.out.println("The ID is not unique. Try Again.");
                        creator = false;
                        return false;
                    }
                }
                while(itBlocked.hasNext()){
                    Process b = itBlocked.next();
                    if(b.getIdNum() == uniqueID){
                        System.out.println("The ID is not unique. Try Again.");
                        creator = false;
                        return false;
                    }
                }
                for(int i=0;i<memSet.length;i++){
                    if(memSet[i] != null){
                        creator = false;
                        if(memSet[i].getIdNum() == uniqueID){
                            System.out.println("The ID is not unique. Try Again.");
                            creator = false;
                            return false;
                        }
                    }                
                }
                return true;
            }else{
                System.out.println("There is not enough memory for this process." + remainingMem + " memory is left.");
                creator = false;
            }
        }
        return true;
            
    }
        
    public static void createProcess(int uniqueID, int memory, int remainingMem, Process[] memSet, LinkedList<Process> ready, LinkedList<Process> blocked){
        if(createHelper(uniqueID,memory,remainingMem,memSet,ready,blocked) == true){
            Process newProcess = new Process(uniqueID, 0, 0, 0, memory);
            ready.add(newProcess);
            remainingMem = remainingMem-memory;
            System.out.println("The process has been created with "+memory+" used " + "and " + remainingMem + " remaining memory.");
            System.out.println("The process was created with the unique ID " + uniqueID);
        }
    }
    
    public static boolean deleteProcess(int uniqueID, Process[] memSet, LinkedList<Process> ready, LinkedList<Process> blocked){
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained
        
        while(itReady.hasNext()){
            Process p = itReady.next();
            if (p.getIdNum() == uniqueID) {
                System.out.println("The process has been found.");
                ready.remove(p);
                return true;
            }
        }
        while(itBlocked.hasNext()){
            Process b = itBlocked.next();
            if(b.getIdNum() == uniqueID){
                System.out.println("The process has been found.");
                blocked.remove(b);
                return true;
            }
        }           
        for(int i=0;i<memSet.length;i++){
            if(memSet[i] != null){
                if(memSet[i].getIdNum() == uniqueID){
                    System.out.println("The process has been found.");
                    memSet[i] = new Process();
                    return true;
                }
            }                
        }
        return false;
    } 
    
    public static boolean blockProcess(int uniqueID, Process[] memSet, LinkedList<Process> ready, LinkedList<Process> blocked){
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained        
        
        while(itReady.hasNext()){
            Process p = itReady.next();
            if (p.getIdNum() == uniqueID) {
                System.out.println("The process has been found.");
                ready.remove(p);
                blocked.add(p);
                System.out.println("The process has been moved to the blocked queue.");
                return true;
            }
        }
        while(itBlocked.hasNext()){
            Process b = itBlocked.next();
            if(b.getIdNum() == uniqueID){
                System.out.println("The process has been found, it is already in the blocked queue.");
                return false;
            }
        }           
        for(int i=0;i<memSet.length;i++){
            if(memSet[i] != null){
                if(memSet[i].getIdNum() == uniqueID){
                    System.out.println("The process has been found.");
                    blocked.add(memSet[i]);
                    memSet[i] = new Process();
                    System.out.println("The process has been moved to the blocked queue.");
                    return true;
                }
            }                
        }
        return false;
    }
    
    public static boolean unblockProcess(int uniqueID, Process[] memSet, LinkedList<Process> ready, LinkedList<Process> blocked){
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained        
        
        while(itReady.hasNext()){
            Process p = itReady.next();
            if (p.getIdNum() == uniqueID) {
                System.out.println("The process has been found, it already exists in the ready queue.");
                return false;
            }
        }
        while(itBlocked.hasNext()){
            Process b = itBlocked.next();
            if(b.getIdNum() == uniqueID){
                System.out.println("The process has been found.");
                blocked.remove(b);
                ready.add(b);
                return true;
            }
        }           
        for(int i=0;i<memSet.length;i++){
            if(memSet[i] != null){
                if(memSet[i].getIdNum() == uniqueID){
                    System.out.println("The process has been found.");
                    ready.add(memSet[i]);
                    memSet[i] = new Process();
                    System.out.println("The process has been moved to the ready queue.");
                    return true;
                }
            }                
        }
        return false;
    }
    
    public static boolean showProcess(int uniqueID,Process[] memSet, LinkedList<Process> ready, LinkedList<Process> blocked){
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained        
        
        while(itReady.hasNext()){
            Process p = itReady.next();
            if (p.getIdNum() == uniqueID) {
                System.out.println("The process has been found in the ready queue.");
                System.out.println("ID:" + p.getIdNum());
                System.out.println("CPU Term:" + p.getCPUterm());
                System.out.println("I/O Requests:" + p.getIOrequest());
                System.out.println("Waiting Term:" + p.getWaitingTerm());
                System.out.println("Memory Usage:" + p.getMemoryUsage());
                return true;
            }        
        }
        
        while(itBlocked.hasNext()){
            Process b = itBlocked.next();
            if(b.getIdNum() == uniqueID){
                System.out.println("The process has been found in the blocked queue.");
                System.out.println("ID:" + b.getIdNum());
                System.out.println("CPU Term:" + b.getCPUterm());
                System.out.println("I/O Requests:" + b.getIOrequest());
                System.out.println("Waiting Term:" + b.getWaitingTerm());
                System.out.println("Memory Usage:" + b.getMemoryUsage());
                return true;
            }
        }           
        for(int i=0;i<memSet.length;i++){
            if(memSet[i] != null){
                if(memSet[i].getIdNum() == uniqueID){
                    System.out.println("The process has been found in memory.");
                    System.out.println("ID:" + memSet[i].getIdNum());
                    System.out.println("CPU Term:" + memSet[i].getCPUterm());
                    System.out.println("I/O Requests:" + memSet[i].getIOrequest());
                    System.out.println("Waiting Term:" + memSet[i].getWaitingTerm());
                    System.out.println("Memory Usage:" + memSet[i].getMemoryUsage());
                    return true;
                }
            }                
        }
        return false;
    }
    
    public static void showAll(Process[] memSet, LinkedList<Process> ready, LinkedList<Process> blocked){
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained        
        
        System.out.print("Ready Queue:");
        if(ready.isEmpty() == false){
            while(itReady.hasNext()){
                Process p = itReady.next();
                System.out.print("ID:" + p.getIdNum() + ",");
                System.out.print("CPU:" + p.getCPUterm()+ ",");
                System.out.print("I/O:" + p.getIOrequest()+ ",");
                System.out.print("Wait:" + p.getWaitingTerm()+ ",");
                System.out.println("Mem:" + p.getMemoryUsage()+ ";");
            }
        } else{
            System.out.println("is empty.");
        }
        System.out.println();
        System.out.print("Blocked:");
        if(blocked.isEmpty() == false){
            while(itBlocked.hasNext()){
                Process b = itBlocked.next();
                System.out.print("ID:" + b.getIdNum() + ",");
                System.out.print("CPU:" + b.getCPUterm()+ ",");
                System.out.print("I/O:" + b.getIOrequest()+ ",");
                System.out.print("Wait:" + b.getWaitingTerm()+ ",");
                System.out.println("Mem:" + b.getMemoryUsage()+ ";");
            }     
        }else{
            System.out.println("is empty.");
        }
        System.out.println();
        int nullCount = 0;
        System.out.print("Memory Set:");
        for(int i=0;i<memSet.length;i++){
            if(memSet[i] != null){
                System.out.print("ID:" + memSet[i].getIdNum()+ ",");
                System.out.print("CPU:" + memSet[i].getCPUterm()+ ",");
                System.out.print("I/O:" + memSet[i].getIOrequest()+ ",");
                System.out.print("Wait:" + memSet[i].getWaitingTerm()+ ",");
                System.out.println("Mem:" + memSet[i].getMemoryUsage()+ ";");
            } else{
                nullCount++;
            }                
        }
        if(nullCount == memSet.length){
            System.out.println("is empty.");
        }
        System.out.println();
    }
    
    public static void showBlocked(LinkedList<Process> blocked){
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained
        
        System.out.print("Blocked:");
        if(blocked.isEmpty() == false){
            while(itBlocked.hasNext()){
                Process b = itBlocked.next();
                System.out.print("ID:" + b.getIdNum() + ",");
                System.out.print("CPU:" + b.getCPUterm()+ ",");
                System.out.print("I/O:" + b.getIOrequest()+ ",");
                System.out.print("Wait:" + b.getWaitingTerm()+ ",");
                System.out.println("Mem:" + b.getMemoryUsage()+ ";");
            }     
        } else{
            System.out.println(" is empty.");
        }
    }
    //could combine showBlocked and show ready to 1 function that shows both queues depending on input
    public static void showReady(LinkedList<Process> ready){
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        
        System.out.print("Ready:");
        if(ready.isEmpty() == false){
            while(itReady.hasNext()){
                Process b = itReady.next();
                System.out.print("ID:" + b.getIdNum() + ",");
                System.out.print("CPU:" + b.getCPUterm()+ ",");
                System.out.print("I/O:" + b.getIOrequest()+ ",");
                System.out.print("Wait:" + b.getWaitingTerm()+ ",");
                System.out.println("Mem:" + b.getMemoryUsage()+ ";");
            }     
        } else{
            System.out.println(" is empty.");
        }
    }
    
    public static boolean generateProcessesHelper(int uniqueID,Process[] memSet,LinkedList<Process> ready,LinkedList<Process> blocked){
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained        
        
        while(itReady.hasNext()){
            Process p = itReady.next();
            if (p.getIdNum() == uniqueID) {
                return false;
            }
        }
        while(itBlocked.hasNext()){
            Process b = itBlocked.next();
            if(b.getIdNum() == uniqueID){
                return false;
            }
        }           
        for(int i=0;i<memSet.length;i++){
            if(memSet[i] != null){
                if(memSet[i].getIdNum() == uniqueID){
                    memSet[i] = new Process();
                    return false;
                }
            }                
        }
        return true;
    }
    
    public static void generateProcesses(int numRandProcesses,int userMax,Process[] memSet,LinkedList<Process> ready,LinkedList<Process> blocked){
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained
        int randProcessCounter = 0;
        int mem = userMax;
            while(randProcessCounter < numRandProcesses){
                if(mem != 0){
                    Random rand = new Random();
                    int randNum = rand.nextInt(numRandProcesses*10) + 1;
                    if(generateProcessesHelper(randNum,memSet,ready,blocked)){
                        Random rand2 = new Random();
                        int randNum2 = rand2.nextInt(mem/2) + 1;
                        if(randNum2 < mem){
                            Process n = new Process(randNum,0,0,0,randNum2);
                            mem=mem-randNum2;
                            ready.add(n);
                            randProcessCounter++;
                        }
                    } 
                } else{
                    System.out.println("Memory is full. The command could not be completed.");
                }
                
            }
    }
    
    public static void execute(Process[] memSet,LinkedList<Process> ready,LinkedList<Process> blocked){
        
        //SET UP VARIABLES
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained
        Process b = itReady.next();
        memSet[0] = b;
        ready.remove(b);
        int counter = 0;
        boolean inMem = true;
       
        //LOOP UNTIL ALL PROCESSES HAVE BEEN COMPLETED OR BLOCKED
        while(inMem == true || ready.size() > 0){
            //CPU TERM IS ADDED AS IT ENTERS MEMORY
            Random rand = new Random();
            int randNum = rand.nextInt(10000);
            b.setCPUterm(randNum);   
            itReady = ready.iterator();
            itBlocked = blocked.iterator();
            b.setCPUterm(b.getCPUterm()-1);
            counter++;

            //IF THERE THE PROCESS HAS COMPLETED, REMOVE IT and add the next one
            if(memSet[0].getCPUterm() == 0){
                if(itReady.hasNext()){
                    b = itReady.next();
                    memSet[0] = b;
                } else{
                    memSet[0] = null;
                }
                
            }
            
            //MEMORY IS EMPTY, SATISFY CONDITION TO END WHILE LOOP
            if(memSet[0] == null){
                //WAS SUPPOSED TO REMOVE THE FINAL ELEMENT IN MEMORY
                inMem = false;
            }
            
            //reduce each wait term by 1 per iteration
            while(itReady.hasNext()){
                Process p = itReady.next();
                if(p.getWaitingTerm() == 0){
                    p.setWaitingTerm(b.getCPUterm());
                }
                p.setWaitingTerm(p.getWaitingTerm()-1);
            }
            while(itBlocked.hasNext()){
                Process g = itBlocked.next();
                if(g.getWaitingTerm() == 0){
                    g.setWaitingTerm(b.getCPUterm());
                }
                g.setWaitingTerm(g.getWaitingTerm()-1);
            }           
            for(int i=0;i<memSet.length;i++){
                if(memSet[i] != null){
                    memSet[i].setWaitingTerm(memSet[i].getWaitingTerm()-1);
                    memSet[i].setCPUterm(memSet[i].getCPUterm()-1);
                }                
            }

            //this is supposed to move the next ready process into memory
            //the other process is never getting kicked out of memory, so its not length 0
            if(memSet[0] == null){
                if(itReady.hasNext()){
                    memSet[0] = itReady.next();
                } else{
                    
                }
            }

            if(counter == 10){
                int wheelOfFortune = rand.nextInt(3);
                if(wheelOfFortune == 0){
                    showProcess(b.getIdNum(),memSet,ready,blocked);
                    deleteProcess(b.getIdNum(),memSet,ready,blocked);
                } else if(wheelOfFortune == 1){
                    unblockProcess(b.getIdNum(),memSet,ready,blocked);
                } else if(wheelOfFortune == 2){
                    blockProcess(b.getIdNum(),memSet,ready,blocked);
                } else if(wheelOfFortune == 3){
                    blockProcess(b.getIdNum(),memSet,ready,blocked);
                } else{
                    System.out.println("Something went wrong in random number generation");
                }
                counter = 0;
            }
        }
        
    }
}