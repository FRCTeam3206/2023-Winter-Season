package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;

public class ClawCommand extends CommandBase{
    private Supplier<Boolean> coneSupplier,cubeSupplier;
    private Claw claw;
    public ClawCommand(Supplier<Boolean> coneButton,Supplier<Boolean> cubeButton,Claw claw){
        this.coneSupplier=coneButton;
        this.cubeSupplier=cubeButton;
        this.claw=claw;
        addRequirements(claw);
    }
    public void execute(){
        if(cubeSupplier.get()){
            claw.cube();
        }else if(coneSupplier.get()){
            claw.cone();
        }else{
            claw.open();
        }
    }
}
