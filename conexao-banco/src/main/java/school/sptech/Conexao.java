package school.sptech;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class Conexao {

    private DataSource conexao;

    public Conexao(){
        DriverManagerDataSource driver = new DriverManagerDataSource();

        driver.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driver.setUrl("jdbc:mysql://127.0.0.1:3306/EasyData?useSSL=false&serverTimezone=UTC");
        driver.setUsername("root");
        driver.setPassword("2003#JoaoMelo");

        this.conexao = driver;
    }

    public DataSource getConexao() {
        return this.conexao;
    }
}