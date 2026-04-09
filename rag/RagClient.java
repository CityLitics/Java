import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RagClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("🚀 Conectado ao motor RAG em Python!");
        System.out.println("Digite sua pergunta sobre o PDF (ou 'sair' para encerrar):");

        while (true) {
            System.out.print("\nSua Pergunta > ");
            String pergunta = scanner.nextLine();

            if (pergunta.equalsIgnoreCase("sair")) break;

            try {
                // Tratando espaços e caracteres especiais para a URL
                String perguntaEncoded = URLEncoder.encode(pergunta, StandardCharsets.UTF_8);
                String url = "http://127.0.0.1:8000/ask?question=" + perguntaEncoded;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                System.out.println("⏳ A IA local está processando...");
                
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    System.out.println("🤖 RESPOSTA: " + response.body());
                } else {
                    System.out.println("❌ Erro no servidor: " + response.statusCode());
                }

            } catch (Exception e) {
                System.out.println("❌ Falha ao conectar: " + e.getMessage());
                System.out.println("Certifique-se que o VS Code está rodando o servidor!");
            }
        }
        scanner.close();
    }
}


