package negocio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Quiz implements Runnable {
	Socket client; // Recebe o cliente
	int n; // Controla o número de perguntas do quiz
	int acertos; // Controla o número de acertos do cliente
	char[] respostas = { 'A', 'C', 'A', 'C', 'B' }; //Armazena as respostas corretas
	String[] perguntas = { "Esporte mais popular do Brasil: A) Futebol B) Volei C) Boxe",
			"Luta marcial brasileira: A) Briga de Galo B) Pipoca de Kannário C) Capoeira",
			"Ritmo matriz da Bolsa Nova: A) Samba B) Arrocha C) Pagodão",
			"Festa popular: A) Eleições B) Paredão do Posto Shell do Imbuí C) Carnaval",
			"Iguaria tipica da Bahia: A) Dogão da Lapa B) Acarajé  C) Pão com ovo" };
	//O vetor "perguntas" armazena as questões do quiz

	//Método construtor, que recebe um cliente como parâmetro
	public Quiz(Socket client) {
		this.client = client;
		this.n = 0;
		this.acertos = 0;
	}

	@Override
	public void run() {
		try {
			//Loop que percorrerá as questões
			for (this.n = 0; this.n < 5; this.n++) {				
				Scanner in = new Scanner(this.client.getInputStream());
				PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
				//Manda as questões para o cliente
				out.println(this.perguntas[this.n]);
				//Recebe as respostas do cliente, tratando-as (convertendo para maiúsculas)
				char s = this.tratarResposta(in.nextLine().charAt(0));
				//Se a resposta estiver correta
				if (s == this.respostas[this.n]) {
					this.acertos++; //Contabiliza-se o número de acertos
					out.println("Voce acertou!"); //Informa ao cliente que ele acertou a questão
				} else {
					//Se a resposta estiver errada, o cliente é informado desse fato, recebendo, também, o gabarito
					out.println("Voce errou! A resposta correta é a letra " + respostas[n]);
				}
				//Se a o atributo "n" for igual a 4, é porque o quiz encerrou
				if (this.n == 4) {
					out.println("Voce acertou " + this.acertos + " de 5 questões!");
					//O cliente é informado da quantidade total de questões que ele acertou
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
