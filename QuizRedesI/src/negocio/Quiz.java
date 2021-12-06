package negocio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Quiz implements Runnable {
	Socket client; // Recebe o cliente
	int n; // Controla o n�mero de perguntas do quiz
	int acertos; // Controla o n�mero de acertos do cliente
	char[] respostas = { 'A', 'C', 'A', 'C', 'B' }; //Armazena as respostas corretas
	String[] perguntas = { "Esporte mais popular do Brasil: A) Futebol B) Volei C) Boxe",
			"Luta marcial brasileira: A) Briga de Galo B) Pipoca de Kann�rio C) Capoeira",
			"Ritmo matriz da Bolsa Nova: A) Samba B) Arrocha C) Pagod�o",
			"Festa popular: A) Elei��es B) Pared�o do Posto Shell do Imbu� C) Carnaval",
			"Iguaria tipica da Bahia: A) Dog�o da Lapa B) Acaraj�  C) P�o com ovo" };
	//O vetor "perguntas" armazena as quest�es do quiz

	//M�todo construtor, que recebe um cliente como par�metro
	public Quiz(Socket client) {
		this.client = client;
		this.n = 0;
		this.acertos = 0;
	}

	@Override
	public void run() {
		try {
			//Loop que percorrer� as quest�es
			for (this.n = 0; this.n < 5; this.n++) {				
				Scanner in = new Scanner(this.client.getInputStream());
				PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
				//Manda as quest�es para o cliente
				out.println(this.perguntas[this.n]);
				//Recebe as respostas do cliente, tratando-as (convertendo para mai�sculas)
				char s = this.tratarResposta(in.nextLine().charAt(0));
				//Se a resposta estiver correta
				if (s == this.respostas[this.n]) {
					this.acertos++; //Contabiliza-se o n�mero de acertos
					out.println("Voce acertou!"); //Informa ao cliente que ele acertou a quest�o
				} else {
					//Se a resposta estiver errada, o cliente � informado desse fato, recebendo, tamb�m, o gabarito
					out.println("Voce errou! A resposta correta � a letra " + respostas[n]);
				}
				//Se a o atributo "n" for igual a 4, � porque o quiz encerrou
				if (this.n == 4) {
					out.println("Voce acertou " + this.acertos + " de 5 quest�es!");
					//O cliente � informado da quantidade total de quest�es que ele acertou
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Tratamento da alternativa informada
	public char tratarResposta(char resposta) {
		return Character.toUpperCase(resposta);
	}
}
