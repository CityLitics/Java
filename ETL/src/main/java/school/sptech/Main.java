package school.sptech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import school.sptech.config.DBConexao;
import school.sptech.service.ExcelService;
import school.sptech.service.S3Service;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        String chaveObjeto = args.length > 0 ? args[0] : "municipio_agua_esgoto.xlsx";

        log.info("Iniciando download do S3: {}", chaveObjeto);

        try {
            S3Service s3 = new S3Service();
            InputStream is = s3.obterArquivo(chaveObjeto);
            log.info("Arquivo obtido do S3. Inserindo no banco...");

            DBConexao conexao = new DBConexao();
            ExcelService service = new ExcelService(conexao.getConexao());
            service.processar(is);

            long fim = System.currentTimeMillis();
            log.info("Processamento concluído! Tempo total: {} segundos", (fim - inicio) / 1000);
        } catch (S3Exception e) {
            log.error("Falha ao acessar o S3: {}", e.awsErrorDetails().errorMessage());
            log.error("Verifique as credenciais AWS ou o nome do arquivo no bucket.");
            System.exit(1);
        } catch (Exception e) {
            log.error("Erro: {}", e.getMessage(), e);
            System.exit(1);
        }

   //    String caminho = "/home/andre/Downloads/municipio_agua_esgoto.xlsx";
   //    String nomeArquivo = caminho.substring(caminho.lastIndexOf('/') + 1);

   //    log.info("Iniciando o arquivo: {}", nomeArquivo);

   //    try (InputStream arquivo = new FileInputStream(caminho)) {
   //        DBConexao conexao = new DBConexao();
   //        ExcelService service = new ExcelService(conexao.getConexao());
   //        service.processar(arquivo);
   //        long fim = System.currentTimeMillis();
   //        log.info("Processamento concluído com sucesso. Tempo total: {} segundos", (fim - inicio) / 1000.0);
   //    } catch (Exception e) {
   //        log.error("Erro durante o processamento do arquivo: {}:", caminho, e);
   //        System.exit(1);
        }
    }