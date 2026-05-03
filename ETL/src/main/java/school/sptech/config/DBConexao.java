package school.sptech.config;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

public class DBConexao {
    private DataSource conexao;

    public DBConexao() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/exemplo_conexao?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        ds.setUsername("dev_user");
        ds.setPassword("senha123");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setInitialSize(5);
        ds.setMaxTotal(10);
        this.conexao = ds;
    }

    public DataSource getConexao() {
        return this.conexao;
    }
}