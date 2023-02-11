package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Claw;

public class ClawCommand extends CommandBase {
    private Supplier<Boolean> coneSupplier, cubeSupplier;
    private Claw claw;

    public ClawCommand(Claw claw, Supplier<Boolean> coneButton, Supplier<Boolean> cubeButton) {
        this.coneSupplier = coneButton;
        this.cubeSupplier = cubeButton;
        this.claw = claw;
        addRequirements(claw);
    }

    public void execute() {
        if (cubeSupplier.get()) {
            //claw.cube();
            claw.grab(Constants.ClawBools.GRAB_BLCK);
        } else if (coneSupplier.get()) {
            //claw.cone();
            claw.grab(Constants.ClawBools.GRAB_CONE);
        } else {
            claw.open();
        }
    }
}
