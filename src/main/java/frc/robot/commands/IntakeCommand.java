package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
    private Intake intake;
    private Supplier<Boolean> button;

    public IntakeCommand(Intake subsystem, Supplier<Boolean> button) {
        this.button=button;
        this.intake=subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
       if(button.get()){
        intake.runIntake();
       }else{
        intake.stopIntake();
       }
    }

    @Override
    public void end(boolean interrupted) {
    }

}