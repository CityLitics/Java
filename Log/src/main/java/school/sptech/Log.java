package school.sptech;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Log {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        LocalDate data = LocalDate.now();
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter dataHoraFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss.SSSSSS");

        Integer qtdRelatorios = ThreadLocalRandom.current().nextInt(2, 6);;
        Integer tamanhoRelatorio = ThreadLocalRandom.current().nextInt(6, 13);

        for (int i = 1; i <= qtdRelatorios; i++) {
            System.out.printf("Arquivo %d: %s-%d.xlsx\n", i, dataFormatada.format(data), i);
        }

        System.out.print("Escolha um log para análise: ");
        Integer relatorioEscolhido = scanner.nextInt();
        Integer contadorArquivo = relatorioEscolhido;

        while (relatorioEscolhido <= 0 || relatorioEscolhido > qtdRelatorios) {
            System.out.print("Escolha um log válido para análise: ");
            relatorioEscolhido = scanner.nextInt();
        }
        contadorArquivo = relatorioEscolhido;

        System.out.printf("[A#00%d] %s [SYSTEM] Server starting\n", contadorArquivo, dataHoraFormatada.format(dataHora));
        System.out.printf("[A#00%d] %s [SYSTEM] Console Mode Activated\n", contadorArquivo, dataHoraFormatada.format(dataHora));
        for (int i = 0; i < tamanhoRelatorio; i++) {
            Integer qtd = i;
            System.out.printf("[A#00%d] %s [SYSTEM] reading archive %s.%d.csv\n", contadorArquivo, dataHoraFormatada.format(dataHora), dataFormatada.format(data), i);

            if (qtd == tamanhoRelatorio - 1) {
                System.out.printf("[A#00%d] %s [SYSTEM] reading archive %s.%d.csv ERROR; Cannot read file;\n", contadorArquivo, dataHoraFormatada.format(dataHora), dataFormatada.format(data), i+1);
            }
        }
        throw new Exception("Valor nulo, reveja esse excel.");
    }
}

