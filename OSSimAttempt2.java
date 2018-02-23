package ossimattempt2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OSSimAttempt2 {
    public static void main(String[] args) {
        //objects
        Date myDate = new Date();
        Scanner sc = new Scanner(System.in);
        OS myOS = new OS("0.0.1");
        
        //data structures
        String[] originalCommands;
        originalCommands = new String [8];
        originalCommands(originalCommands);
        Vector userHistory = new Vector(10, 5);

        //menu for the options the OS sim can perform
        boolean menu = true;
        while(menu){
            //prints the instructions
            System.out.println();
            for(int i=0;i<8;i++){
                System.out.print(i+1 + " ");
                System.out.println(originalCommands[i]);
            }
            System.out.print("Enter the corresponding menu item which you wish to perform:");
            int userChoice = sc.nextInt(); 
            userHistory.add(userChoice);
            
            if(userChoice == 0){
                //ADD INSTRUCTIONS HERE
                //OR DONT PROBABLY BEST IF WE DIDNT, YEA I'M NOT
            } else if(userChoice == 1){
                //VERSION
                System.out.println("Version: " + myOS.getVersion());
            } else if(userChoice == 2){
                //DATE
                System.out.println("1.Display current date");
                System.out.println("2.Change the year");
                System.out.println("3.Change the month");
                System.out.println("4.Change the day");
                System.out.print("Enter the corresponding menu item which you wish to perform: ");
                int dateMenu = sc.nextInt();
                if(dateMenu == 1){
                    SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd");
                    System.out.println("Current Date: " + ft.format(myDate));
                } else if(dateMenu == 2){
                    System.out.print("Change to what year? ");
                    int yearChange = sc.nextInt();
                    myDate.setYear(yearChange-1900);//subtract 1900 because of the notes in the method to make desired value
                } else if(dateMenu == 3){
                    System.out.print("Change to what month? ");
                    int monthChange = sc.nextInt();
                    if(monthChange < 13 && monthChange > 0){
                        myDate.setMonth(monthChange-1);
                    } else{
                        System.out.println("Invalid Entry");
                    }
                } else if(dateMenu == 4){
                    System.out.print("Change to what Day? ");
                    int dayChange = sc.nextInt();
                    if(dayChange < 31 && dayChange > 0){
                        myDate.setDate(dayChange);
                    } else{
                        System.out.println("Invalid Entry");
                    }
                }
            } else if(userChoice == 3){
                //FILE DIRECTORY
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
                } else if(fileMenu == 2){
                    myOS.printFileNames();
                } else{
                    System.out.println("Invalid Entry.");
                }
            } else if(userChoice == 4){
                //HISTORY
                System.out.print("History of Main menu Commands: " + userHistory.toString() );
                System.out.println();
            } else if(userChoice == 5){
                //BATCH FILES
                //Reference3 used
                String line;
                System.out.print("Enter the file name:");
                String fileName = sc.nextLine();
                try {
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    while((line = bufferedReader.readLine()) != null) {
                        
                    }   
                    bufferedReader.close();         
                }
                catch(FileNotFoundException ex) {
                    System.out.println(
                        "Unable to open file '" + 
                        fileName + "'");                
                }
                catch(IOException ex) {
                    System.out.println("Error reading file '" + fileName + "'");                  
                }
            } else if(userChoice == 6){
                //ALIASING
                System.out.print("Type the number of the command you wish to change or type 0 to reset to the original command names: ");
                int numChange = sc.nextInt();
                if(numChange > 0 && numChange < 9 ){
                    System.out.print("What would you like to change " + originalCommands[numChange-1] + " to:");
                    sc.nextLine();//used to consume the last new line character and allow newCommand to recieve input
                    String newCommand = sc.nextLine();
                    originalCommands[numChange-1] = newCommand;
                    System.out.println("Success, the change has been made.");
                } else if(numChange == 0){
                    originalCommands(originalCommands);
                    System.out.println("The command names have been reset.");
                } else{
                    System.out.println("Invalid Input.");
                }
            } else if(userChoice == 7){
                //EXIT
                System.out.print("Are you sure you want to exit?(y=0,n=1)");
                int exitConfirm = sc.nextInt();
                if(exitConfirm == 0){
                    System.out.println("Goodbye.");
                    menu = false;
                } else{
                    System.out.println("Yay, thanks for staying.");
                    userChoice = 0;
                }
            } else if(userChoice == 8){
                //HELP
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
                System.out.println("Invalid Entry. Try Again.");
            }  
        }
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
}
