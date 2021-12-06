package negocio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Quiz implements Runnable {
	Socket client; // Recebe o cliente
	int contador; // Controla o n�mero de perguntas do quiz
	int acertos; // Controla o n�mero de acertos do cliente
	char[] respostas = { 'A', 'C', 'A', 'C', 'B', 'A','B' }; //Armazena as respostas corretas
	ArrayList<Integer> conteiner = new ArrayList();

	String[] perguntas = { "Esporte mais popular do Brasil: A) Futebol B) Volei C) Boxe",
			"Luta marcial brasileira: A) Briga de Galo B) Pipoca de Kannario C) Capoeira",
			"Ritmo matriz da Bolsa Nova: A) Samba B) Arrocha C) Pagodao",
			"Festa popular: A) Eleicoes B) Paredao do Posto Shell do Imbui C) Carnaval",
			"Atrás do Trio Eletrico: A) Ninguém vai B) Só nao vai quem ja morreu C) Todo mundo vai",
			"Maior campeao do Nordestao: A) Vitoria B) Jahia  C) Vitoria da Conquista",
			"Iguaria tipica da Bahia: A) Dogao da Lapa B) Acaraje  C) Pao com ovo" };


	//M�todo construtor, que recebe um cliente como par�metro
	public Quiz(Socket client) {
		this.client = client;
		this.contador = 0;
		this.acertos = 0;
	}

	@Override
	public void run() {
		try {
			
		
			for (this.contador = 0; this.contador < 5 ; this.contador++) {
				
				Random gerador = new Random();
				Scanner in = new Scanner(this.client.getInputStream());
				PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
				
				int number = gerador.nextInt(5); 

				
				boolean contains = conteiner.contains(number);

				


	            if (contains) {
	            	this.contador = this.contador -1;
	            }
	            else {
	
						
						out.println(this.perguntas[number]);
						
						//Recebe as respostas do cliente, tratando-as (convertendo para mai�sculas)
						char s = this.tratarResposta(in.nextLine().charAt(0));
						//Se a resposta estiver correta
						if (s == this.respostas[number]) {
							this.acertos++; //Contabiliza-se o n�mero de acertos
							out.println("Voce acertou!"); //Informa ao cliente que ele acertou a quest�o
						} else {

							out.println("Voce errou! A resposta correta eh a letra " + respostas[number]);
						}
						//Se a o atributo "contador" for igual a 4, é porque o quiz encerrou
						if (this.contador == 4) {
							out.println("Voce acertou " + this.acertos + " de 5 questoes!");
							//O cliente � informado da quantidade total de quest�es que ele acertou
						}
						conteiner.add(number);
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
