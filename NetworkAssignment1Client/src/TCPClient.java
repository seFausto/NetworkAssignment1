import java.io.*;
import java.net.*;

class TCPClient {

	private static final int _port = 1337;
	private static final String _serverAddress = "localhost";

	public static void main(String argv[]) {
		String sentence;
		String modifiedSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));

		Socket clientSocket = null;
		try {
			while (true) {
				clientSocket = new Socket(_serverAddress, _port);
				DataOutputStream outToServer = new DataOutputStream(
						clientSocket.getOutputStream());

				BufferedReader inFromServer = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				System.out.println("Please input a string to encode/decode.");
				System.out.println("To Stop the server send \"goodbye!\"): ");
				sentence = inFromUser.readLine();
				System.out
						.print("Should this be Encoded or Decoded (E or D): ");
				String operation = inFromUser.readLine();
				// Check for Size... should be less than 256 characters

				outToServer.writeBytes(sentence + "\n");
				outToServer.flush();
				outToServer.writeBytes(operation + "\n");
				outToServer.flush();

				modifiedSentence = inFromServer.readLine();
				System.out.println("FROM SERVER: " + modifiedSentence);
				clientSocket.close();

				if (sentence.toLowerCase().equals("goodbye!")) {
					System.out.println("Closing. Thank you :)");
					return;
				}
			}

		} catch (UnknownHostException e) {
			System.out
					.println("Please try again later. The host is not reachable.");
			return;
		} catch (IOException e) {
			System.out
					.println("Please try again later. The host is not reachable.");
			return;
		}

	}

	private static Boolean IsServerReachable() {
		try {
			return InetAddress.getByName(_serverAddress).isReachable(100);
		} catch (UnknownHostException e) {
			return false;
		} catch (ConnectException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

}