package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
    private Intake intake;
    private Supplier<Boolean> runConeButton;
    private Supplier<Boolean> reverseConeButton;
    private Supplier<Boolean> runCubeButton;
    private Supplier<Boolean> reverseCubeButton;
    private Supplier<Boolean> transportUp;

    public IntakeCommand(Intake subsystem, Supplier<Boolean> runConeButton, Supplier<Boolean> reverseConeButton,
            Supplier<Boolean> runCubeButton, Supplier<Boolean> reverseCubeButton, Supplier<Boolean> transportUp) {
        this.runConeButton = runConeButton;
        this.reverseConeButton = reverseConeButton;
        this.runCubeButton = runCubeButton;
        this.reverseCubeButton = reverseCubeButton;
        this.transportUp = transportUp;
        this.intake = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (runCubeButton.get()) {
            intake.runIntake(.3);
            intake.setDeploy(true);
            intake.setTransport(true);
        } else if (runConeButton.get()) {
            intake.runIntake(.5);
            intake.setDeploy(true);
            intake.setTransport(true);
        } else if (reverseCubeButton.get()) {
            intake.runIntake(-.4);
            intake.setDeploy(true);
            intake.setTransport(true);
        } else if (reverseConeButton.get()) {
            intake.runIntake(-.7);
            intake.setDeploy(true);
            intake.setTransport(true);
        } else {
            intake.runIntake(.3);
            intake.setDeploy(false);
        }
        if (transportUp.get()) {
            // intake.setTransport(false);
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

}