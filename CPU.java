public class CPU
{
    /**Code(instruction) section memory. (.text)*/
    int[] codeSection;
    /**Local and global variable section memory. (.data)*/
    int[] dataSection;
    /**Stack memory. Main memory of the VM. */
    int[] stackMemory;

    int instructionPointer;
    int stackPointer = -1;

    public CPU(int[] codeInstructions, int stackMemorySize) 
    {
        this.codeSection = codeInstructions;
        this.instructionPointer = 0;
        this.stackMemory = new int[stackMemorySize];

        int dataValueCount = 0;

        //Detecting global and data section variables
        for(int instructIndex = 0; instructIndex < codeSection.length; instructIndex++)
        {
            if(codeSection[instructIndex] == Instructions.STORE) 
                dataValueCount++;
        }

        if(dataValueCount >= 1)
            this.dataSection = new int[dataValueCount];
    }

    public void execute()
    {
        //Limiting instructionPointer so we don't run outside of the code(.text) section memory.
        //Whilst the instructionPointer is valid:
        while(instructionPointer < codeSection.length)
        {
            int currentExecutingInstruction = codeSection[instructionPointer]; //fetch instruction
            //System.out.println("Instruction Pointer: " + instructionPointer + ", Instruction: " + currentExecutingInstruction);
            instructionPointer++; //pre increment instruction pointer for the next CPU cycle
    
            //Decode instruction and execute
            switch(currentExecutingInstruction)
            {
                case Instructions.ADD:
                {
                    int secVal = stackMemory[stackPointer--]; //get value from top of the stack
                    int firstVal = stackMemory[stackPointer--]; //get value underneath the top of the stack

                    stackMemory[++stackPointer] = firstVal + secVal; //push added value onto the stack
                    break;
                }
                case Instructions.SUB:
                {
                    int secVal = stackMemory[stackPointer--]; //get value from top of the stack
                    int firstVal = stackMemory[stackPointer--]; //get value underneath the top of the stack

                    stackMemory[++stackPointer] = firstVal - secVal; //push subtracted value onto the stack
                    break;
                }
                case Instructions.MUL:
                {
                    int secVal = stackMemory[stackPointer--]; //get value from top of the stack
                    int firstVal = stackMemory[stackPointer--]; //get value underneath the top of the stack

                    stackMemory[++stackPointer] = secVal * firstVal; //push multiplied value onto the stack
                    break;
                }
                case Instructions.ILT:
                {
                    int secVal = stackMemory[stackPointer--]; //get value from top of the stack
                    int firstVal = stackMemory[stackPointer--]; //get value underneath the top of the stack

                    if(firstVal < secVal)
                        stackMemory[++stackPointer] = 1; //true
                    else
                        stackMemory[++stackPointer] = 0; //false

                    break;
                }
                case Instructions.IEQ:
                {
                    int secVal = stackMemory[stackPointer--]; //get value from top of the stack
                    int firstVal = stackMemory[stackPointer--]; //get value underneath the top of the stack

                    if(firstVal == secVal)
                        stackMemory[++stackPointer] = 1; //true
                    else
                        stackMemory[++stackPointer] = 0; //false

                    break;
                }
                case Instructions.PUSH:
                {
                    int val = codeSection[instructionPointer]; //get value from top of the stack
                    instructionPointer++; //pre increment instruction pointer so it points to the next instruction

                    stackPointer++; //increment stack pointer for stack space reservation
                    stackMemory[stackPointer] = val; //push value
                    break;
                }
                case Instructions.POP:
                {
                    --stackPointer; //pre decrement the stack pointer and return decremented pointer value. Basically the next usage of an int would be current top value in the stack memory.
                    break;
                }
                case Instructions.STORE:
                {
                    int val = stackMemory[stackPointer]; //get value from top of the stack
                    stackPointer--; //pop/delete off
                    
                    int addrOfValueToStore = codeSection[instructionPointer]; //set address of value stored in offset provided after the STORE instruction. => address = value that is pushed after the STORE instruction
                    instructionPointer++; //increment instruction pointer so it points to the next instruction

                    dataSection[addrOfValueToStore] = val; //store value in the data section memory pointed by the address.
                    break;
                }
                case Instructions.RETRV:
                {
                    int addrOfStoredGVal = codeSection[instructionPointer]; //get the address of value stored in the code.
                    instructionPointer++; //increment instruction pointer so it points to the next instruction

                    int storedGVal = dataSection[addrOfStoredGVal]; //get stored value from address in data section.

                    stackPointer++; //increment stack pointer for stack space reservation
                    stackMemory[stackPointer] = storedGVal; //push stored value on to the stack.
                    break;
                }
                case Instructions.PRINT:
                {
                    int val = stackMemory[stackPointer]; //get value from top of the stack
                    System.out.println(val);
                    break;
                }
                case Instructions.HLT:
                    return;
            }
        }
    }

    public class Instructions
    {
        /**Add */
        public static final short ADD = 1;
        /**Subtract */
        public static final short SUB = 2;
        /**Multiply */
        public static final short MUL = 3;
        /**If less than */
        public static final short ILT = 4;
        /**If equal*/
        public static final short IEQ = 5;
        /**Push value onto the stack */
        public static final short PUSH = 6;
         /**Pop top value off the stack*/
         public static final short POP = 7;
        /**Store value into data section */
        public static final short STORE = 8;
        /**Retrieves value from data section */
        public static final short RETRV = 9;
        /**Print top stack value */
        public static final short PRINT = 10;
        /**Halt: Stop execution */
        public static final short HLT = 11; 
    }
}
