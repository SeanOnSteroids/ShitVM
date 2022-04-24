//Stack-based VM - Model: Fetch->Decode->Execute
public class VirtualMachine
{
    private CPU _cpu;
    
    public VirtualMachine()
    {
        /*
            int xyAdded = 1+1;
            System.out.println(xyAdded);

            int xySubtracted = 5-1;
            System.out.println(xySubtracted);

            return;
        */

        int[] addAndSubInstruction = 
        {
            CPU.Instructions.PUSH, 1,   //Push 1 onto the stack
            CPU.Instructions.PUSH, 1,   //Push 1 onto the stack
            CPU.Instructions.ADD,       //Pop 1 and 1 from the stack, add them together and push the result onto the stack
            CPU.Instructions.PRINT,     //Print pushed result from the stack
            CPU.Instructions.POP,       //Pop the result off from the stack
            CPU.Instructions.PUSH, 5,   //Push 5 onto the stack
            CPU.Instructions.PUSH, 1,   //Push 1 onto the stack
            CPU.Instructions.SUB,       //Pop 5 and 1 from the stack, subtract 1 from 5 and push the result onto the stack
            CPU.Instructions.PRINT,     //Print pushed result from the stack
            CPU.Instructions.POP,       //Pop the result off from the stack
            CPU.Instructions.HLT        //Halt the CPU cycle
        };
        
        _cpu = new CPU(addAndSubInstruction, 100);
        _cpu.execute();
    }
}
