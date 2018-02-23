/*
Matt Strand
2/19/2018
Process Class for PCB project
 */
package pcb;

public class Process {
    int idNum;
    int CPUterm;
    int IOrequest;
    int waitingTerm;
    int memoryUsage;
    
    public Process(){
        this.idNum = 0;
        this.CPUterm = 0;
        this.IOrequest = 0;
        this.waitingTerm = 0;
        this.memoryUsage = 0;
    }
    
    public Process(int id, int cpu, int io, int wait, int mem){
        this.idNum = id;
        this.CPUterm = cpu;
        this.IOrequest = io;
        this.waitingTerm = wait;
        this.memoryUsage = mem;
    }
    //SETTERS
    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public void setCPUterm(int CPUterm) {
        this.CPUterm = CPUterm;
    }

    public void setIOrequest(int IOrequest) {
        this.IOrequest = IOrequest;
    }

    public void setWaitingTerm(int waitingTerm) {
        this.waitingTerm = waitingTerm;
    }

    public void setMemoryUsage(int memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    //GETTERS
    public int getIdNum() {
        return idNum;
    }

    public int getCPUterm() {
        return CPUterm;
    }

    public int getIOrequest() {
        return IOrequest;
    }

    public int getWaitingTerm() {
        return waitingTerm;
    }

    public int getMemoryUsage() {
        return memoryUsage;
    }
}
