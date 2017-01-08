package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import util.ServerConnectionException;

/** @author Kayler Renslow<br>
 * <br> */
public class ServerConnection{
	private final String params;
	private final String url;

	public ServerConnection(String params, String url) {
		this.params = params;
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	/** Sends the parameters of this class to the url contained in this class and returns the result
	 * 
	 * @return result of the send
	 * @throws ServerConnectionException when an error occured */
	public String send() throws ServerConnectionException {
		URL url;
		try{
			url = new URL(this.url);
		}catch (MalformedURLException e){
			e.printStackTrace();
			throw new ServerConnectionException("Connection URL broke.");
		}
		URLConnection conn;
		try{
			conn = url.openConnection();
		}catch (IOException e){
			e.printStackTrace();
			throw new ServerConnectionException("Connection could not be opened.");
		}

		conn.setDoOutput(true);

		OutputStreamWriter writer;
		try{
			writer = new OutputStreamWriter(conn.getOutputStream());
		}catch (IOException e){
			e.printStackTrace();
			throw new ServerConnectionException("Could not create stream to server.");
		}

		try{
			writer.write(params);
			writer.flush();
		}catch (Exception e){
			throw new ServerConnectionException("Could not write to stream.");
		}

		String received = "";
		try{
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while ((line = reader.readLine()) != null){
				received += line;
			}
			writer.close();
			reader.close();
		}catch (Exception e){
			e.printStackTrace();
			throw new ServerConnectionException("Error occured while reading the stream for developer token verification.");
		}
		return received;

	}

}
