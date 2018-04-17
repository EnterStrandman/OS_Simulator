/*
 * Matt Strand
 * PCB
 * BEGIN DATE: 2/19/2018
 * LAST EDIT DATE: 4/17/2018
 * RESOURCES:
 * https://www.tutorialspoint.com/java/util/priorityqueue_iterator.htm
 * https://www.tutorialspoint.com/java/java_arrays.htm
 * https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html
 */
package pcb;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*
TO DO LIST:
    PROJECT 4
    UPDATE DOCUMENTATION
*/

public class PCB {

    public static void main(String[] args) throws java.io.IOException {
        // The name of the file to open.
        String fileName = "Trace.txt";
        // This will reference one line at a time
        try {
            //IN ORDER TO SEE CONTENTS OF FILE, BE SURE TO USE THE EXIT FUNCTION SO THE FILE IS CLOSED.
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            
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
        int traceLineCount = 0;  
        
        //add trace
        bufferedWriter.write(traceLineCount + "|" + "User Max: " +userMax);
        bufferedWriter.newLine();
        traceLineCount++;
        
        while(menu){
            instrutionsMain();
            int userChoice = sc.nextInt();
            
            bufferedWriter.write(traceLineCount + "|" + "User Chose: " + userChoice);
            bufferedWriter.newLine();
            traceLineCount++;
            //MAIN FUNCTIONALITY BELOW
            if(userChoice == 1){
                //CREATE PROCESS
                System.out.print("Type the process id(needs to be an int):");
                int userID = sc.nextInt();
                
                //add to trace
                bufferedWriter.write(traceLineCount + "|" + "Process ID chosen: " + userID);
                bufferedWriter.newLine();
                traceLineCount++;
                
                System.out.print("Type the memory size(needs to be an int):");
                int userMemSize = sc.nextInt();
                
                //add to trace
                bufferedWriter.write(traceLineCount + "|" + "User Memory Size: " +userMemSize);
                bufferedWriter.newLine();
                traceLineCount++;
                
                System.out.print("Type the class (1=3x more likely, 2=3x less likely, 3=normal):");
                int type = sc.nextInt();
                
                //add to trace
                bufferedWriter.write(traceLineCount + "|" + "User Type: " + type);
                bufferedWriter.newLine();
                traceLineCount++;
                
                if(type == 1 || type == 2 || type == 3){
                    createProcess(userID, userMemSize, remainingMemory, memory, ready, blocked,type);
                    //add to trace
                    bufferedWriter.write(traceLineCount + "|" + "User Create Single Process Successful:");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }else{
                    System.out.println("Please input a 1, 2, or 3 for the type.");
                    //add to trace
                    bufferedWriter.write(traceLineCount + "|" + "User Create Single Process Failed.: ");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }
                
            }else if(userChoice == 2){
                //DELETE PROCESS
                    //add to trace
                    bufferedWriter.write(traceLineCount + "|" + "User Chose Option 2");
                    bufferedWriter.newLine();
                    traceLineCount++;
                System.out.println("Type the ID of the process to be deleted:");
                int userDelete = sc.nextInt();
                if(deleteProcess(userDelete, memory, ready, blocked)){
                    System.out.println("The process has been removed.");
                    //add to trace
                    bufferedWriter.write(traceLineCount + "|" + "User Delete of Process with ID " + userDelete + " Succeeded.");
                    bufferedWriter.newLine();
                    traceLineCount++;
                } else{
                    System.out.println("The process was not found. Check your input and try again.");
                    //add to trace
                    bufferedWriter.write(traceLineCount + "|" + "User Delete of Process with ID " + userDelete + " Failed.");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }
            }else if(userChoice == 3){
                //BLOCK
                System.out.print("Type the ID of the process to be deleted:");
                int userBlock = sc.nextInt();
                
                //add to trace
                bufferedWriter.write(traceLineCount + "|" + "User ID entered: " + userBlock);
                bufferedWriter.newLine();
                traceLineCount++;
                
                if(blockProcess(userBlock,memory,ready, blocked)){
                    System.out.println("The process has been moved to the blocked queue.");
                    
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "Block Succeeded");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }else{
                    System.out.println("Failure. Check your input and try again.");
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "Block Failed");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }
            }else if(userChoice == 4){
                //UNBLOCK
                System.out.print("Type the ID of the process to be unblocked:");
                int unblock = sc.nextInt();
                
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "Unblock ID Entered: " + unblock);
                bufferedWriter.newLine();
                traceLineCount++;
                
                if(unblockProcess(unblock, memory, ready, blocked)){
                    System.out.println("The process has been moved from the blocked queue to the ready queue:");
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "UnBlock Succeeded");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }else{
                    System.out.println("Failure. Check your input and try again.");
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "UnBlock Failed");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }
            }else if(userChoice == 5){
                //SHOW SINGLE PROCESS INFO
                System.out.print("Type the process ID to have the information displayed:");
                int showOne = sc.nextInt();
                
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "Show info ID entered: " + showOne);
                bufferedWriter.newLine();
                traceLineCount++;
                
                if(showProcess(showOne, memory, ready, blocked)){
                    System.out.println();
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "Id lookUp succeded and returned " + showProcess(showOne, memory, ready, blocked));
                    bufferedWriter.newLine();
                    traceLineCount++;
                }else{
                    System.out.println("Failure. Check your input and try again.");
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "Show info failed.");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }
            }else if(userChoice == 6){
                //SHOW ALL PROCESS INFO
                showAll( memory, ready, blocked);
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "All Processes Shown. ");
                bufferedWriter.newLine();
                traceLineCount++;
            }else if(userChoice == 7){
                //SHOW READY
                showReady(ready);
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "The ready queue is displayed.");
                bufferedWriter.newLine();
                traceLineCount++;
            }else if(userChoice == 8){
                //SHOW BLOCKED
                showBlocked(blocked);
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "The blocked queue is displayed.");
                bufferedWriter.newLine();
                traceLineCount++;
            }else if(userChoice == 9){
                //GENERATE RANDOM PROCESSES
                System.out.print("How many processes would you like to generate?:");
                int numRandProcesses = sc.nextInt();
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "Number of processes to be generated: " + numRandProcesses);
                bufferedWriter.newLine();
                traceLineCount++;
                
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "The processes were stored in a file");
                bufferedWriter.newLine();
                traceLineCount++;
                generateProcesses(numRandProcesses,userMax,memory,ready, blocked);
                Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
                Process b = itReady.next();
                BufferedWriter processWriter = new BufferedWriter(new FileWriter("Processes.csv", true));
                while(itReady.hasNext()){ 
                    processWriter.append(b.processString());
                    processWriter.newLine();
                    b = itReady.next();
                }
                processWriter.close();
                System.out.println("The process generator has finished.");
            }else if(userChoice == 10){
                //EXECUTE
                System.out.print("Which scheduler(1.Current; 2.RR; 3.MFQ):");
                int  scheduleSelect = sc.nextInt();
                
                if(scheduleSelect == 1){
                    //CURRENT METHOD
                    execute(memory,ready,blocked);
                    
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "Current method was selected.");
                    bufferedWriter.newLine();
                    traceLineCount++;
                    
                    System.out.println("Execute current method has completed.");
                }else if(scheduleSelect == 2){
                    //ROUND ROBIN SCHEDULER
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "Round Robin method was selected.");
                    bufferedWriter.newLine();
                    traceLineCount++;
                    System.out.print("What time quantum would you like to use for Round Robin Scehduling:");
                    int timeQuantum = sc.nextInt();
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "The time quantum " + timeQuantum + " was selected.");
                    bufferedWriter.newLine();
                    traceLineCount++;
                    
                    if(timeQuantum > 0){
                        roundRobin(memory,ready,blocked,timeQuantum);
                        System.out.println("The round robin scheduler has completed");
                    }else{
                        System.out.println("Invalid Entry. Type a number greater than 0.");
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "Invalid Time quantum entered.");
                        bufferedWriter.newLine();
                        traceLineCount++;
                    }                     
                }else if(scheduleSelect == 3){
                    //MULTI LEVEL FEEDBACK QUEUE
                    System.out.print("How many queues would you like to use?:");
                    int queues = sc.nextInt();
                    
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + queues + " Queues were selected");
                    bufferedWriter.newLine();
                    traceLineCount++;
                    
                    System.out.print("Type the Time Quantum: ");
                    int quantum = sc.nextInt();
                    
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + quantum + " time quantam was chosen");
                    bufferedWriter.newLine();
                    traceLineCount++;
                    
                    System.out.print("Type the Time Before moving all process to top priority: ");
                    int timeTop = sc.nextInt();
                    
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + timeTop + " cycles were chosen to run until all processes are top priority");
                    bufferedWriter.newLine();
                    traceLineCount++;
                    
                    
                    if(queues > 0){
                        MLFQ(memory,ready,blocked,quantum,queues,timeTop);
                    } else{
                        System.out.println("Please Enter a number greater than 0.");
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "Improper input for number of queues");
                        bufferedWriter.newLine();
                        traceLineCount++;
                    }
                } else{
                    System.out.println("Improper Input, type 1, 2, or 3");
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "Improper input for type");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }
            }else if(userChoice == 11){
                //COMPARE 
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "User selected to compare the methods of scheduling");
                bufferedWriter.newLine();
                traceLineCount++;
                ready.clear();

                String csvFile = "Processes.csv";
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ",";

                try {
                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {
                        // use comma as separator
                        String[] country = line.split(cvsSplitBy);
                        String number0 = country[0]; 
                        String number1 = country[1];
                        String number2 = country[2];
                        int result0 = Integer.parseInt(number0);
                        int result1 = Integer.parseInt(number1);
                        int result2 = Integer.parseInt(number2);
                        Process n = new Process(result0,0,0,0,result1,result2,0);
                        ready.add(n);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        br.close();
                    }
                }
                
                System.out.print("Time Quantum: ");
                int quantum = sc.nextInt();
                
                System.out.print("Queues: ");
                int queues = sc.nextInt();
                
                System.out.print("Move back to top: ");
                int timeTop = sc.nextInt();
                
                LinkedList readyClone0 = new LinkedList();
                LinkedList readyClone1 = new LinkedList();
                LinkedList readyClone2 = new LinkedList();
                LinkedList blockedClone0 = new LinkedList();
                LinkedList blockedClone1 = new LinkedList();
                LinkedList blockedClone2 = new LinkedList();
                readyClone0 = (LinkedList) ready.clone();
                readyClone1 = (LinkedList) ready.clone();
                readyClone2 = (LinkedList) ready.clone();
                  
                int totalExecCyc = execute(memory,readyClone0,blockedClone0);
                int totalRRCyc = roundRobin(memory,readyClone1,blockedClone1,quantum);
                int totalMLFQcyc = MLFQ(memory,readyClone2,blockedClone2,quantum,queues,timeTop);
                
                System.out.println("Execute Cycles: " + totalExecCyc);
                System.out.println("RR Cycles: " + totalRRCyc);
                System.out.println("MLFQ Cycles: " + totalMLFQcyc);
                
            }else if(userChoice == 12){
                //EXIT
                sc.nextLine();
                System.out.print("Are you sure you want to exit?(1(y)/2(n)):");
                int exiter = sc.nextInt();
                               
                if(exiter == 1){
                    System.out.println("Goodbye.");
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "The user exited the program");
                    bufferedWriter.newLine();
                    traceLineCount++;
                    menu = false;
                } else{
                    System.out.println("Yay! Thanks for staying.");
                    //add trace
                    bufferedWriter.write(traceLineCount + "|" + "The user stayed in the program!");
                    bufferedWriter.newLine();
                    traceLineCount++;
                }
                
            } else if(userChoice == 13){
                //SYSTEM INFO
                
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "User entered Interface");
                bufferedWriter.newLine();
                traceLineCount++;
                
                //objects
                Date myDate = new Date();
                Interface myOS = new Interface("0.0.4");

                //data structures
                String[] originalCommands;
                originalCommands = new String [8];
                originalCommands(originalCommands);
                Vector userHistory = new Vector(10, 5);

                //menu for the options the OS sim can perform
                boolean menu2 = true;
                while(menu2){
                    //prints the instructions
                    System.out.println();
                    for(int i=0;i<8;i++){
                        System.out.print(i+1 + " ");
                        System.out.println(originalCommands[i]);
                    }
                    System.out.print("Enter the corresponding menu item which you wish to perform:");
                    int userChoice2 = sc.nextInt(); 
                    userHistory.add(userChoice2);

                    if(userChoice == 0){
                        //ADD INSTRUCTIONS HERE
                        //OR DONT PROBABLY BEST IF WE DIDNT, YEA I'M NOT
                    } else if(userChoice == 1){
                        //VERSION
                        System.out.println("Version: " + myOS.getVersion());
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "Version Displayed: " + myOS.getVersion());
                        bufferedWriter.newLine();
                        traceLineCount++;
                    } else if(userChoice == 2){
                        //DATE
                        System.out.println("1.Display current date");
                        System.out.println("2.Change the year");
                        System.out.println("3.Change the month");
                        System.out.println("4.Change the day");
                        System.out.print("Enter the corresponding menu item which you wish to perform: ");
                        int dateMenu = sc.nextInt();
                        
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "User entered Date menu");
                        bufferedWriter.newLine();
                        traceLineCount++;
                        
                        if(dateMenu == 1){
                            SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd");
                            System.out.println("Current Date: " + ft.format(myDate));
                            
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User changed date to: " + ft.format(myDate));
                            bufferedWriter.newLine();
                            traceLineCount++;
                        } else if(dateMenu == 2){
                            System.out.print("Change to what year? ");
                            int yearChange = sc.nextInt();
                            myDate.setYear(yearChange-1900);//subtract 1900 because of the notes in the method to make desired value
                            
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User changed year to: " + yearChange);
                            bufferedWriter.newLine();
                            traceLineCount++;
                            
                        } else if(dateMenu == 3){
                            System.out.print("Change to what month? ");
                            int monthChange = sc.nextInt();
                            
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User entered date month change");
                            bufferedWriter.newLine();
                            traceLineCount++;
                            
                            if(monthChange < 13 && monthChange > 0){
                                myDate.setMonth(monthChange-1);
                                //add trace
                                bufferedWriter.write(traceLineCount + "|" + "User changed month to " + monthChange);
                                bufferedWriter.newLine();
                                traceLineCount++;
                            } else{
                                
                                //add trace
                                bufferedWriter.write(traceLineCount + "|" + "User provided invalid entry to change the month");
                                bufferedWriter.newLine();
                                traceLineCount++;
                                
                                System.out.println("Invalid Entry");
                            }
                        } else if(dateMenu == 4){
                            
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User entered day change");
                            bufferedWriter.newLine();
                            traceLineCount++;
                            System.out.print("Change to what Day? ");
                            int dayChange = sc.nextInt();
                            if(dayChange < 31 && dayChange > 0){
                                myDate.setDate(dayChange);
                                
                                //add trace
                                bufferedWriter.write(traceLineCount + "|" + "User changed day to " + dayChange);
                                bufferedWriter.newLine();
                                traceLineCount++;
                            } else{
                                System.out.println("Invalid Entry");
                                //add trace
                                bufferedWriter.write(traceLineCount + "|" + "User entered invalid date");
                                bufferedWriter.newLine();
                                traceLineCount++;
                            }
                        }
                    } else if(userChoice == 3){
                        //FILE DIRECTORY
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "User entered file directory");
                        bufferedWriter.newLine();
                        traceLineCount++;
                        
                        System.out.println("1.Add a file");
                        System.out.println("2.Print list of files");
                        System.out.print("Type the menu choice:");
                        int fileMenu = sc.nextInt();
                        if(fileMenu == 1){
                            sc.nextLine();
                            System.out.print("Type the file name:");
                            String fileAdd = sc.nextLine();
                            myOS.addFile(fileAdd);
                            System.out.println("The file has been added to the directory.");
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User added a file to the directory");
                            bufferedWriter.newLine();
                            traceLineCount++;
                        } else if(fileMenu == 2){
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User printed file names");
                            bufferedWriter.newLine();
                            traceLineCount++;
                            myOS.printFileNames();
                        } else{
                            System.out.println("Invalid Entry.");
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User entered invalid input in the files menu");
                            bufferedWriter.newLine();
                            traceLineCount++;
                        }
                    } else if(userChoice == 4){
                        //HISTORY
                        System.out.print("History of Main menu Commands: " + userHistory.toString() );
                        System.out.println();
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "History of commands in the interface menu are displayed");
                        bufferedWriter.newLine();
                        traceLineCount++;
                    } else if(userChoice == 5){
                        //BATCH FILES
                        //Reference3 used
                        String line;
                        System.out.print("Enter the file name:");
                        String fileIn = sc.nextLine();
                        
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "User trying to process batch files: contents are printed");
                        bufferedWriter.newLine();
                        traceLineCount++;
                        try {
                            FileReader fileReader = new FileReader(fileIn);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            while((line = bufferedReader.readLine()) != null) {
                                System.out.println(line);
                            }   
                            bufferedReader.close();         
                        }
                        catch(FileNotFoundException ex) {
                            System.out.println(
                                "Unable to open file '" + 
                                fileName + "'");                
                        }
                    } else if(userChoice == 6){
                        //ALIASING
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "User is changing names of commands");
                        bufferedWriter.newLine();
                        traceLineCount++;
                        System.out.print("Type the number of the command you wish to change or type 0 to reset to the original command names: ");
                        int numChange = sc.nextInt();
                        
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "User wants to change command " + numChange);
                        bufferedWriter.newLine();
                        traceLineCount++;
                        
                        if(numChange > 0 && numChange < 9 ){
                            System.out.print("What would you like to change " + originalCommands[numChange-1] + " to:");//subtract 1 to account for position 0 in array
                            sc.nextLine();//used to consume the last new line character and allow newCommand to recieve input
                            String newCommand = sc.nextLine();
                            originalCommands[numChange-1] = newCommand;
                            
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "New name of command " + numChange + " is " + newCommand);
                            bufferedWriter.newLine();
                            traceLineCount++;
                            System.out.println("Success, the change has been made.");
                        } else if(numChange == 0){
                            originalCommands(originalCommands);
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User has reset the commands to their original names");
                            bufferedWriter.newLine();
                            traceLineCount++;
                            System.out.println("The command names have been reset.");
                        } else{
                            System.out.println("Invalid Input.");
                        }
                    } else if(userChoice == 7){
                        //EXIT
                        
                        System.out.print("Are you sure you want to exit?(y=0,n=1)");
                        int exitConfirm = sc.nextInt();
                        if(exitConfirm == 0){
                            System.out.println("Back to the main menu!");
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User exited back to main menu");
                            bufferedWriter.newLine();
                            traceLineCount++;
                            menu = false;
                        } else{
                            System.out.println("Yay, thanks for staying in the interface menu.");
                            //add trace
                            bufferedWriter.write(traceLineCount + "|" + "User stayed in the interface menu");
                            bufferedWriter.newLine();
                            traceLineCount++;
                            userChoice = 0;
                        }
                    } else if(userChoice == 8){
                        //HELP
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "Help was called and command descriptions were displayed");
                        bufferedWriter.newLine();
                        traceLineCount++;
                        System.out.println("1. "+originalCommands[0]+" is used to return the current version number.");
                        System.out.println("2. "+originalCommands[1]+" is used to print or set the date for the OS simulator.");
                        System.out.println("3. "+originalCommands[2]+" is used to navigate the file directory.");
                        System.out.println("4. "+originalCommands[3]+" displays the users history of commands.");
                        System.out.println("5. "+originalCommands[4]+" this command runs commands from a batch file.");
                        System.out.println("6. "+originalCommands[5]+" this allows the user to change the name of the commands being used(ex. exit->quit)");
                        System.out.println("7. "+originalCommands[6]+" stops the program upon accepting the extra check to confirm the user wants to stop the session.");
                        System.out.println("8. "+originalCommands[7]+" This is the current option you have entered and it is useful for understanding what each option can do.");

                    } else{
                        //INVALID ENTRY
                        //add trace
                        bufferedWriter.write(traceLineCount + "|" + "User entered invalid entry to the inferface menu");
                        bufferedWriter.newLine();
                        traceLineCount++;
                        System.out.println("Invalid Entry. Try Again.");
                    }  
                }
            }else{
                System.out.println("Invalid Input");
                //add trace
                bufferedWriter.write(traceLineCount + "|" + "The user entered invalid input.");
                bufferedWriter.newLine();
                traceLineCount++;
            }
            
        }
        // Always close files.
        bufferedWriter.close();
                    
        }catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");     
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
        System.out.println("11.Compare Execute Methods (NOTE:this will take some time and processes need to be generated first)");
        System.out.println("12.Exit");
        System.out.println("13.System Information");
        System.out.print("Type the corresponding number to the process:");
    }    
    
    public static void originalCommands(String commands[]){
        commands[0] = "Version";
        commands[1] = "Date";
        commands[2] = "Directory";
        commands[3] = "History";
        commands[4] = "Batch Files";
        commands[5] = "Aliasing";
        commands[6] = "Exit";
        commands[7] = "Help";
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
        
    public static void createProcess(int uniqueID, int memory, int remainingMem, Process[] memSet, LinkedList<Process> ready, LinkedList<Process> blocked, int type){
        if(createHelper(uniqueID,memory,remainingMem,memSet,ready,blocked) == true){
            Process newProcess = new Process(uniqueID, 0, 0, 0, memory,type,0);
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
                blocked.remove(b);
                ready.add(b);
                return true;
            }
        }           
        for(int i=0;i<memSet.length;i++){
            if(memSet[i] != null){
                if(memSet[i].getIdNum() == uniqueID){
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
                System.out.println("Type:" + p.getProcessType());
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
                System.out.println("Type:" + b.getProcessType());
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
                    System.out.println("Type:" + memSet[i].getProcessType());
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
                System.out.print("Mem:" + p.getMemoryUsage()+ ",");
                System.out.println("Type:" + p.getProcessType()+ ";");
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
                System.out.print("Mem:" + b.getMemoryUsage()+ ",");
                System.out.println("Type:" + b.getProcessType()+ ";");
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
                System.out.print("Mem:" + memSet[i].getMemoryUsage()+ ",");
                System.out.println("Type:" + memSet[i].getProcessType() + ";");
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
                System.out.println("Mem:" + b.getMemoryUsage()+ ",");
                System.out.println("Type:" + b.getProcessType()+ ";");
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
                System.out.println("Mem:" + b.getMemoryUsage()+ ",");
                System.out.println("Type:" + b.getProcessType()+ ";");
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
            if (p.getIdNum() == uniqueID){
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
                            Process n = new Process(randNum,0,0,0,randNum2,createType(),0);
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
        
    public static int execute(Process[] memSet,LinkedList<Process> ready,LinkedList<Process> blocked){
        
        //SET UP VARIABLES
        Iterator<Process> itReady = ready.iterator();//creates pointer at the priority queue so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the priority queue so data can be obtained
        Process b = itReady.next();
        memSet[0] = b;
        ready.remove(b);
        int counter = 0;
        boolean inMem = true;
        int cycles = 0;
       
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
                    b.setCPUterm(b.getCPUterm() + 10);//penalty for moving into memory
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
                if(b.getProcessType() == 1){
                    //GET RANDOM NUMBER
                    int wheelOfFortune = rand.nextInt(5);
                    //3 TIMES MORE LIKELY TO NEED USER IO
                    if(wheelOfFortune == 0){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 1){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 2 || wheelOfFortune == 3 || wheelOfFortune == 4){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 5){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Invalid Number for process type.");
                    }
                } else if(b.getProcessType() == 2){
                    //3 TIMES LESS LIKELY TO NEED USER IO
                    int wheelOfFortune = rand.nextInt(10);
                    if(wheelOfFortune == 0 || wheelOfFortune == 1 || wheelOfFortune == 2){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 3 || wheelOfFortune == 4 || wheelOfFortune == 5){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 6){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 7|| wheelOfFortune == 8 || wheelOfFortune == 9){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Invalid Number for process type.");
                    }
                } else if(b.getProcessType() == 3){
                    //EVEN CHANCE OF NEEDING USER IO
                    int wheelOfFortune = rand.nextInt(4);
                    if(wheelOfFortune == 0){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 1){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 2){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 3){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Something went wrong in random number generation");
                    }
                } else{
                    System.out.println("Invalid Number for process type.");
                }
                cycles++;
                counter = 0;
            }
        }
        return cycles;
    }

    public static int createType(){
        Random rand = new Random();
        int randNum = rand.nextInt(3)+1;
        return randNum;
    }
        
    public static int roundRobin(Process[] memSet,LinkedList<Process> ready,LinkedList<Process> blocked, int quantum){
        //SET UP VARIABLES
        Iterator<Process> itReady = ready.iterator();//creates pointer at the linked list so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the linked list so data can be obtained
        Process b = itReady.next();
        memSet[0] = b;
        ready.remove(b);
        int counter = 0;
        int cycles = 0;
        boolean inMem = true;
        int timeQuantum = quantum;
       
        //LOOP UNTIL ALL PROCESSES HAVE BEEN COMPLETED OR BLOCKED
        while(inMem == true || ready.size() > 0){
            //CPU TERM IS ADDED AS IT ENTERS MEMORY
            System.out.println("Time quantum:" + timeQuantum);
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
                    b.setCPUterm(b.getCPUterm() + 10);//penalty for moving into memory
                    memSet[0] = b;
                    timeQuantum = quantum;
                } else{
                    memSet[0] = null;
                }
            } else if(timeQuantum == 0){
                //move the process from memory and into ready queue 
                //bring the next proccess in, add context switch penatly of 10, set switch to true
                //if true, add 10 cycle penalty for the waiting term and reset at bottom.
                itReady = ready.iterator();
                if(itReady.hasNext()){
                    blockProcess(b.getIdNum(),memSet,ready,blocked);
                    unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    b.setCPUterm(b.getCPUterm() + 10);//penalty for moving into memory, because this is done here, the waiting terms are updated below
                    memSet[0] = b;
                }
                timeQuantum = quantum;
            }
            
            
            //MEMORY IS EMPTY, SATISFY CONDITION TO END WHILE LOOP
            if(memSet[0] == null){
                inMem = false;
            }
            
            //reduce each wait term by 1 per iteration
            itReady = ready.iterator();
            itBlocked = blocked.iterator();
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

            //moves next ready process into memory
            if(memSet[0] == null){
                if(itReady.hasNext()){
                    memSet[0] = itReady.next();
                } else{
                    
                }
            }

            if(counter == 10){
                if(b.getProcessType() == 1){
                    //GET RANDOM NUMBER
                    int wheelOfFortune = rand.nextInt(5);
                    //3 TIMES MORE LIKELY TO NEED USER IO
                    if(wheelOfFortune == 0){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 1){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 2 || wheelOfFortune == 3 || wheelOfFortune == 4){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 5){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Invalid Number for process type.");
                    }
                } else if(b.getProcessType() == 2){
                    //3 TIMES LESS LIKELY TO NEED USER IO
                    int wheelOfFortune = rand.nextInt(10);
                    if(wheelOfFortune == 0 || wheelOfFortune == 1 || wheelOfFortune == 2){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 3 || wheelOfFortune == 4 || wheelOfFortune == 5){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 6){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 7|| wheelOfFortune == 8 || wheelOfFortune == 9){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Invalid Number for process type.");
                    }
                } else if(b.getProcessType() == 3){
                    //EVEN CHANCE OF NEEDING USER IO
                    int wheelOfFortune = rand.nextInt(4);
                    if(wheelOfFortune == 0){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 1){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 2){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 3){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Something went wrong in random number generation");
                    }
                } else{
                    System.out.println("Invalid Number for process type.");
                }
                
                counter = 0;
            }
        timeQuantum --;
        cycles++;
        }
        return cycles;
    }
    
    public static int MLFQ(Process[] memSet,LinkedList<Process> ready,LinkedList<Process> blocked, int quantum, int queues, int timeTop){
        /*  Rule 1: If Priority(A) > Priority(B), A runs (B doesn’t).
            Rule 2: If Priority(A) = Priority(B), A & B run in RR.
            Rule 3: When a job enters the system, it is placed at the highest
                priority (the topmost queue).
            Rule 4: Once a job uses up its time allotment at a given level (regardless
                of how many times it has given up the CPU), its priority is
                reduced (i.e., it moves down one queue).
            Rule 5: After some time period S, move all the jobs in the system
                to the topmost queue
        */
        
         //SET UP VARIABLES
        Iterator<Process> itReady = ready.iterator();//creates pointer at the linked list so data can be obtained
        Iterator<Process> itBlocked = blocked.iterator();//creates pointer at the linked list so data can be obtained
        Process b = itReady.next();
        memSet[0] = b;
        ready.remove(b);
        int counter = 0;
        int cycles = 0;
        int cyclesBeforeReset = timeTop;
        boolean inMem = true;
        int timeQuantum = quantum;
       
        //LOOP UNTIL ALL PROCESSES HAVE BEEN COMPLETED OR BLOCKED
        while(inMem == true || ready.size() > 0){
            //IF TIME TOP IS 0, CHANGE PRIORITY OF ALL PROCESSES TO THE TOP(0)
             if(timeTop == 0){
                itReady = ready.iterator();
                itBlocked = blocked.iterator();
                while(itReady.hasNext()){
                    Process p = itReady.next();
                    p.setPriority(0);
                }
                while(itBlocked.hasNext()){
                    Process g = itBlocked.next();
                    g.setPriority(0);
                }           
                for(int i=0;i<memSet.length;i++){
                    if(memSet[i] != null){
                        memSet[i].setPriority(0);
                    }                
                }

                //moves next ready process into memory
                if(memSet[0] == null){
                    if(itReady.hasNext()){
                        memSet[0] = itReady.next();
                    } else{
                        inMem = false;
                    }
                }
             }
            
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
                    
                    b.setCPUterm(b.getCPUterm() + 10);//penalty for moving into memory
                    memSet[0] = b;
                    timeQuantum = quantum;
                } else{
                    memSet[0] = null;
                }
            } else if(timeQuantum == 0){
                //move the process from memory and into ready queue 
                //bring the next proccess in, add context switch penatly of 10, set switch to true
                //if true, add 10 cycle penalty for the waiting term and reset at bottom.
                itReady = ready.iterator();
                if(itReady.hasNext()){
                    blockProcess(b.getIdNum(),memSet,ready,blocked);
                    unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    b.setCPUterm(b.getCPUterm() + 10);//penalty for moving into memory, because this is done here, the waiting terms are updated below
                    if(b.getPriority() < queues){
                        b.setPriority(b.getPriority()+1);
                    }
                    memSet[0] = b;
                }
                timeQuantum = quantum;
            }
            
            
            //MEMORY IS EMPTY, SATISFY CONDITION TO END WHILE LOOP
            if(memSet[0] == null){
                inMem = false;
            }
            
            //reduce each wait term by 1 per iteration
            itReady = ready.iterator();
            itBlocked = blocked.iterator();
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

            //moves next ready process into memory
            if(memSet[0] == null){
                if(itReady.hasNext()){
                    memSet[0] = itReady.next();
                } else{
                    
                }
            }

            if(counter == 10){
                if(b.getProcessType() == 1){
                    //GET RANDOM NUMBER
                    int wheelOfFortune = rand.nextInt(5);
                    //3 TIMES MORE LIKELY TO NEED USER IO
                    if(wheelOfFortune == 0){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 1){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 2 || wheelOfFortune == 3 || wheelOfFortune == 4){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 5){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Invalid Number for process type.");
                    }
                } else if(b.getProcessType() == 2){
                    //3 TIMES LESS LIKELY TO NEED USER IO
                    int wheelOfFortune = rand.nextInt(10);
                    if(wheelOfFortune == 0 || wheelOfFortune == 1 || wheelOfFortune == 2){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 3 || wheelOfFortune == 4 || wheelOfFortune == 5){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 6){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    }else if(wheelOfFortune == 7|| wheelOfFortune == 8 || wheelOfFortune == 9){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Invalid Number for process type.");
                    }
                } else if(b.getProcessType() == 3){
                    //EVEN CHANCE OF NEEDING USER IO
                    int wheelOfFortune = rand.nextInt(4);
                    if(wheelOfFortune == 0){
                        //TERMINATE PROCESS AND REMOVE FROM THE SYSTEM
                        showProcess(b.getIdNum(),memSet,ready,blocked);
                        deleteProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 1){
                        //RETURN TO READY QUEUE AND WAIT FOR ITS TURN
                        unblockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 2){
                        //GO TO BLOCKED UNTIL USER IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else if(wheelOfFortune == 3){
                        //GO TO BLOCKED UNTIL HARD DRIVE IO
                        blockProcess(b.getIdNum(),memSet,ready,blocked);
                    } else{
                        System.out.println("Something went wrong in random number generation");
                    }
                } else{
                    System.out.println("Invalid Number for process type.");
                } 
                counter = 0;
            }
        timeQuantum --;    
        timeTop--;
        cycles++;
        }
        return cycles;
    }
}