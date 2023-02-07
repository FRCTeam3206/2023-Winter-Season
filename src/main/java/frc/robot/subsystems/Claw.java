package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
public class Claw extends SubsystemBase{
    Solenoid masterPiston=new Solenoid(PneumaticsModuleType.CTREPCM, Ports.CLAW_MASTER_PISTON);
    Solenoid secondaryPiston = new Solenoid(PneumaticsModuleType.CTREPCM, Ports.CLAW_SECOND_PISTON);
    public Claw(){}
    public void cone(){
        masterPiston.set(true);
        secondaryPiston.set(true);
    }
    public void cube(){
        masterPiston.set(true);
        secondaryPiston.set(true);
    }
    public void open(){
        masterPiston.set(false);
        secondaryPiston.set(false);
    }
}
