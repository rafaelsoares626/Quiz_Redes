package negocio;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		try {
			Socket client = new Socket("", 1234);
			Scanner in = new Scanner(client.getInputStream());
			Scanner inUser = new Scanner(System.in);
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);			
			//Contabiliza a quantidade de quest�es que v�o sendo respondidas
			int contador = 0;
			while (true) {				
				//Mostra para o cliente a quest�o
				System.out.println(in.nextLine());				
				// Se ja percorreu todas as quest�es do quiz, encerra-se a participa��o do cliente.
				if (contador == 5) {
					break;
				}
				//Pede para o cliente escolher a sua alternativa
				System.out.println("Escolha uma alternativa: ");				
				//Envia para o servidor a resposta do cliente
				out.println(inUser.nextLine());				
				//Mostra ao cliente se ele acertou a quest�o
				System.out.println(in.nextLine());
				System.out.println("======================================================================================");				
				//Incrementa o contador de quest�es
				contador++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
