package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;

public class main {

    public static void main(String[] args) {

        Conexao instancia = new Conexao();
        JdbcTemplate template = new JdbcTemplate(instancia.getConexao());

        template.update(
                "UPDATE empresa SET email_empresa = ? WHERE id_empresa = ?",
                "novoemail@empresa.com",
                1
        );

    }
}
