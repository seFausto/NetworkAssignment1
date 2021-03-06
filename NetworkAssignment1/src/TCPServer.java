import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private static final int _port = 1337;
	private static final String _phraseToQuit = "goodbye!";

	public static void main(String[] args) throws Exception {

		String input;
		String modifiedValue;
		String operation;
		Boolean isEncoding = true;

		ServerSocket welcomeSocket = new ServerSocket(_port);

		// this is to keep the server listening
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			input = inFromClient.readLine();
			operation = inFromClient.readLine();

			Boolean isClosing = input.toLowerCase().equals(_phraseToQuit);

			if (operation.isEmpty())
				isEncoding = true;
			else {
				isEncoding = !operation.substring(0, 1).toLowerCase()
						.equals("d");
			}
			Log("Received: " + input + " | Is Encoding: " + isEncoding);

			
			if (isClosing)
				modifiedValue = "I am stopping now. Good bye :)";
			else
			{
				modifiedValue = Encoder.Encode(input, isEncoding);
			}
			
			// Send new value to user
			outToClient.writeBytes(modifiedValue);
			outToClient.flush();
			outToClient.close();
			
			if (isClosing)
			{
				connectionSocket.close();
				break;
			}

		}

	}

	private static void Log(String value) {
		System.out.println(value);
	}

	static class Encoder {
		private static String Encode(String value, Boolean shouldEncode) {
			String result = "";
			int charMove = shouldEncode ? 1 : -1;
			for (int i = 0; i < value.length(); i++) {
				result += (char) ((int) value.charAt(i) + (1 * charMove));
			}

			return result.toString();

		}

	}

}
