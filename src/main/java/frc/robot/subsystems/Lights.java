package frc.robot.subsystems;


import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lights extends SubsystemBase {

  private AddressableLED m_led; 
  private AddressableLEDBuffer m_ledBuffer;
  private int m_rainbowFirstPixelHue = 0;
  private int count = 0;
  private boolean count2 = false;
  private int count3 = 0;
  public int lightState = 0;

  
  //Three colors for 3ColorSwitch
  private final int r1 = 0, g1 = 0, b1 = 200;
  private final int r2 = 150, g2 = 150, b2 = 150;

  public Lights() {
    // PWM port 0
    // Must be a PWM header, not MXP or DIO
    m_led = new AddressableLED(0);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(150);
    m_led.setLength(m_ledBuffer.getLength());

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();


    
  }

  @Override
  public void periodic() {
    //Chooses and uses a mode for the lights based on lightState
    if (lightState == 0){
        rainbow(m_rainbowFirstPixelHue);
        m_led.setData(m_ledBuffer);
    } else if (lightState == 1) {
        LightColor(0, 0, 150);
        m_led.setData(m_ledBuffer);
    } else if (lightState == 2) {
      if (count == 13) {
        ColorSwitch();
        m_led.setData(m_ledBuffer);
        count = 0;
      }
      count ++;
    }
  }

  /**
   * makes a fun rainbow pattern
   * 
   * Copied from the addressable LED library WPILib documentation
   */
  private void rainbow(int m_rainbowFirstPixelHue) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setHSV(i, hue, 255, 128);
    }

    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;

  }

  /**
   * sets all LED's to the rgb color specified from the three aproprately named variables
   */
  public void LightColor(int ColorRed,int ColorGreen,int ColorBlue) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        // Sets the specified LED to the RGB values for red
        m_ledBuffer.setRGB(i, ColorRed, ColorGreen, ColorBlue);
     }
  }

  /**
   * Alternates colors for each led, then switches them every time it's called
   */
  public void ColorSwitch() {

    if (count2 == false) {

      for (var i = 0; i < m_ledBuffer.getLength(); i+=2) {
        m_ledBuffer.setRGB(i, r1, g1, b1);
      }

      for (var i = 1; i < m_ledBuffer.getLength(); i+=2) {
        m_ledBuffer.setRGB(i, r2, g2, b2);
      }
      count2 = true;
    } else if (count2 == true) {

      for (var i = 1; i < m_ledBuffer.getLength(); i+=2) {
        m_ledBuffer.setRGB(i, r1, g1, b1);
      }

      for (var i = 0; i < m_ledBuffer.getLength(); i+=2) {
        m_ledBuffer.setRGB(i, r2, g2, b2);
      }
      count2 = false;
    }
    
  }

}

