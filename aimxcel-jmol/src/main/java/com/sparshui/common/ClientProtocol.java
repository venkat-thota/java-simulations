package com.sparshui.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public abstract class ClientProtocol {

	/**
	 * 
	 */
	protected Socket _socket;
	
	/**
	 * 
	 */
	protected DataInputStream _in;
	
	/**
	 * 
	 */
	protected DataOutputStream _out;

	/**
	 * Create a new ClientProtocol object.
	 * 
	 * @param socket 
	 * 		The socket that has been opened.
	 * @throws IOException
	 * 		If there is a communication error.
	 */
	public ClientProtocol(Socket socket) throws IOException {
		_socket = socket;
		_in = new DataInputStream(_socket.getInputStream());
		_out = new DataOutputStream(_socket.getOutputStream());
	}
	
	/**
	 * 
	 * @author tross
	 *
	 */
	protected class MessageType {
		
		/**
		 * 
		 */
	  public static final int EVENT = 0;
		
		/**
		 * 
		 */
	  public static final int GET_GROUP_ID = 1;
		
		/**
		 * 
		 */
	  public static final int GET_ALLOWED_GESTURES = 2;
	}
}
