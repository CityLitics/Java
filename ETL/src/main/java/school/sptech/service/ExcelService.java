package school.sptech.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.model.Municipio;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelService {
    private static final Logger log = LoggerFactory.getLogger(ExcelService.class);
    private final JdbcTemplate jdbc;
    private static final int TAMANHO_LOTE = 500;

    public ExcelService(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("DROP TABLE IF EXISTS municipios");
        jdbc.execute("CREATE TABLE municipios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "ano INT, " +
                "id_municipio INT, " +
                "sigla_uf VARCHAR(2), " +
                "populacao_atendida_agua INT, " +
                "populacao_atendida_esgoto INT, " +
                "populacao_urbana INT, " +
                "populacao_urbana_residente_agua INT, " +
                "populacao_urbana_atendida_agua INT, " +
                "populacao_urbana_atendida_agua_ibge INT, " +
                "populacao_urbana_residente_esgoto INT, " +
                "populacao_urbana_atendida_esgoto INT, " +
                "populacao_urbana_residente_esgoto_ibge INT" +
                ")");
        log.info("Tabela criada.");
    }

    private void salvarLote(List<Municipio> lote) {
        String sql = "INSERT INTO municipios (" +
                "ano, id_municipio, sigla_uf, " +
                "populacao_atendida_agua, populacao_atendida_esgoto, populacao_urbana, " +
                "populacao_urbana_residente_agua, populacao_urbana_atendida_agua, populacao_urbana_atendida_agua_ibge, " +
                "populacao_urbana_residente_esgoto, populacao_urbana_atendida_esgoto, populacao_urbana_residente_esgoto_ibge" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object[]> parametros = new ArrayList<>();
        for (Municipio m : lote) {
            parametros.add(new Object[]{
                    m.getAno(), m.getIdMunicipio(), m.getSiglaUf(),
                    m.getPopulacaoAtendidaAgua(), m.getPopulacaoAtendidaEsgoto(), m.getPopulacaoUrbana(),
                    m.getPopulacaoUrbanaResidenteAgua(), m.getPopulacaoUrbanaAtendidaAgua(), m.getPopulacaoUrbanaAtendidaAguaIbge(),
                    m.getPopulacaoUrbanaResidenteEsgoto(), m.getPopulacaoUrbanaAtendidaEsgoto(), m.getPopulacaoUrbanaResidenteEsgotoIbge()
            });
        }

        jdbc.batchUpdate(sql, parametros);
    }

    private int lerInteiro(Cell cell) {
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        try {
            return Integer.parseInt(cell.toString().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void processar(InputStream inputStream) throws Exception {
        log.info("Iniciando leitura da planilha...");

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            Map<String, Integer> cabecalho = new LinkedHashMap<>();
            int inseridos = 0;
            List<Municipio> lote = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    for (Cell cell : row) {
                        cabecalho.put(cell.toString().trim(), cell.getColumnIndex());
                    }
                    log.info("Cabeçalho lido. Colunas: {}", cabecalho.keySet());
                    continue;
                }

                Cell celulaAno = row.getCell(cabecalho.get("ano"));
                if (celulaAno == null) continue;

                Cell celulaId = row.getCell(cabecalho.get("id_municipio"));
                Cell celulaUf = row.getCell(cabecalho.get("sigla_uf"));
                Cell celulaPAtAgua = row.getCell(cabecalho.get("populacao_atendida_agua"));
                Cell celulaPAtEsgoto = row.getCell(cabecalho.get("populacao_atendida_esgoto"));
                Cell celulaPUrbana = row.getCell(cabecalho.get("populacao_urbana"));
                Cell celulaPUrbResAgua = row.getCell(cabecalho.get("populacao_urbana_residente_agua"));
                Cell celulaPUrbAtAgua = row.getCell(cabecalho.get("populacao_urbana_atendida_agua"));
                Cell celulaPUrbAtAguaIbge = row.getCell(cabecalho.get("populacao_urbana_atendida_agua_ibge"));
                Cell celulaPUrbResEsgoto = row.getCell(cabecalho.get("populacao_urbana_residente_esgoto"));
                Cell celulaPUrbAtEsgoto = row.getCell(cabecalho.get("populacao_urbana_atendida_esgoto"));
                Cell celulaPUrbResEsgotoIbge = row.getCell(cabecalho.get("populacao_urbana_residente_esgoto_ibge"));

                lote.add(new Municipio(
                        lerInteiro(celulaAno),
                        lerInteiro(celulaId),
                        celulaUf != null ? celulaUf.toString().trim() : "",
                        lerInteiro(celulaPAtAgua),
                        lerInteiro(celulaPAtEsgoto),
                        lerInteiro(celulaPUrbana),
                        lerInteiro(celulaPUrbResAgua),
                        lerInteiro(celulaPUrbAtAgua),
                        lerInteiro(celulaPUrbAtAguaIbge),
                        lerInteiro(celulaPUrbResEsgoto),
                        lerInteiro(celulaPUrbAtEsgoto),
                        lerInteiro(celulaPUrbResEsgotoIbge)
                ));

                if (lote.size() >= TAMANHO_LOTE) {
                    salvarLote(lote);
                    inseridos += lote.size();
                    log.info("Total de lotes inseridos até agora: {}", inseridos);
                    lote.clear();
                }
            }

            if (!lote.isEmpty()) {
                salvarLote(lote);
                inseridos += lote.size();
            }

            log.info("Fim da planilha. Registros inseridos: {}", inseridos);
        }
    }
}