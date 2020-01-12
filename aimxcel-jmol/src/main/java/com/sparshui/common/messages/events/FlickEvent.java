package com.sparshui.common.messages.events;

import com.sparshui.common.Event;
import com.sparshui.common.utils.Converter;

public class FlickEvent implements Event {
	private static final long serialVersionUID = -2305607021385835330L;		

	private float xDirection;
	private float yDirection;
	private float speedLevel;
	
	
	public FlickEvent() {
		xDirection = 0;
		yDirection = 0;
		speedLevel = 0;
	}
	public FlickEvent(float absx, float absy) {

	}
	
	public FlickEvent(int _speedLevel, int _xDirection, int _yDirection){
		speedLevel = _speedLevel;
		xDirection = _xDirection;
		yDirection = _yDirection;
	}
	

  public FlickEvent(byte[] data) {
    if (data.length < 12) {
      // TODO add error handling
      System.err.println("Error constructing Flick Event.");
    } else {
      speedLevel = Converter.byteArrayToFloat(data, 0);
      xDirection = Converter.byteArrayToFloat(data, 4);
      yDirection = Converter.byteArrayToFloat(data, 8);
    }
  }
	
	public float getSpeedLevel() {
		return speedLevel;
	}

	public float getXdirection() {
		return xDirection;
	}
	public float getYdirection() {
		return yDirection;
	}


	//@override
	public int getEventType() {
		return EventType.FLICK_EVENT;
	}

	//@override
	@Override
  public String toString() {
		String ret = "Flick Event";
		return ret;
	}

	/**
	 * Constructs the data packet with this event data. Message format for this
	 * event: 
	 * 
	 * - 4 bytes : EventType 
	 * - 4 bytes : SpeedLevel 
	 * - 4 bytes : X Direction
	 * - 4 bytes : Y Direction
	 * - 16 bytes total
   * @return serialized data
	 */
	public byte[] serialize() {
		byte[] data = new byte[16];
    Converter.intToByteArray(data, 0, getEventType());
    Converter.floatToByteArray(data, 4, speedLevel);
    Converter.floatToByteArray(data, 8, xDirection);
    Converter.floatToByteArray(data, 12, yDirection);
		return data;
	}

}
