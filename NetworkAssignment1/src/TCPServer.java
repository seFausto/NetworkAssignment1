import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private static final int _port = 1337;

	public static void main(String[] args) throws Exception {

		String input;
		String modifiedValue;
		String operation;
		Boolean isEncoding = true;
		
		ServerSocket welcomeSocket = new ServerSocket(_port);

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			input = inFromClient.readLine();
			operation = inFromClient.readLine();
			
			isEncoding = !operation.toLowerCase().equals("decode");

			Log("Received: " + input + " | Is Encoding: " + isEncoding);

			modifiedValue = Modify(input, isEncoding);

			// Send new value to user
			outToClient.writeBytes(modifiedValue);
			outToClient.flush();
			outToClient.close();
		}

	}

	private static void Log(String value) {
		System.out.println(value);
	}

	private static String Modify(String value, Boolean shouldEncode) {
		String result = "";

		if (shouldEncode)
			result = Encode(value);
		else
			result = Decode(value);

		return result;
	}

	private static String Encode(String value) {
		String result = "";

		for (int i = 0; i < value.length(); i++) {

			result += (char) ((int) value.charAt(i) + 1);
		}

		return result.toString();

	}

	private static String Decode(String value) {
		String result = "";

		for (int i = 0; i < value.length(); i++) {

			result += (char) ((int) value.charAt(i) - 1);
		}

		return result.toString();

	}

}
