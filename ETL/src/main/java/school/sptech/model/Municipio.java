package school.sptech.model;

public class Municipio {
    private Integer ano;
    private Integer idMunicipio;
    private String siglaUf;
    private Integer populacaoAtendidaAgua;
    private Integer populacaoAtendidaEsgoto;
    private Integer populacaoUrbana;
    private Integer populacaoUrbanaResidenteAgua;
    private Integer populacaoUrbanaAtendidaAgua;
    private Integer populacaoUrbanaAtendidaAguaIbge;
    private Integer populacaoUrbanaResidenteEsgoto;
    private Integer populacaoUrbanaAtendidaEsgoto;
    private Integer populacaoUrbanaResidenteEsgotoIbge;

    public Municipio(Integer ano, Integer idMunicipio, String siglaUf,
                     Integer populacaoAtendidaAgua, Integer populacaoAtendidaEsgoto,
                     Integer populacaoUrbana, Integer populacaoUrbanaResidenteAgua,
                     Integer populacaoUrbanaAtendidaAgua, Integer populacaoUrbanaAtendidaAguaIbge,
                     Integer populacaoUrbanaResidenteEsgoto, Integer populacaoUrbanaAtendidaEsgoto,
                     Integer populacaoUrbanaResidenteEsgotoIbge) {
        this.ano = ano;
        this.idMunicipio = idMunicipio;
        this.siglaUf = siglaUf;
        this.populacaoAtendidaAgua = populacaoAtendidaAgua;
        this.populacaoAtendidaEsgoto = populacaoAtendidaEsgoto;
        this.populacaoUrbana = populacaoUrbana;
        this.populacaoUrbanaResidenteAgua = populacaoUrbanaResidenteAgua;
        this.populacaoUrbanaAtendidaAgua = populacaoUrbanaAtendidaAgua;
        this.populacaoUrbanaAtendidaAguaIbge = populacaoUrbanaAtendidaAguaIbge;
        this.populacaoUrbanaResidenteEsgoto = populacaoUrbanaResidenteEsgoto;
        this.populacaoUrbanaAtendidaEsgoto = populacaoUrbanaAtendidaEsgoto;
        this.populacaoUrbanaResidenteEsgotoIbge = populacaoUrbanaResidenteEsgotoIbge;
    }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Integer getIdMunicipio() { return idMunicipio; }
    public void setIdMunicipio(Integer idMunicipio) { this.idMunicipio = idMunicipio; }

    public String getSiglaUf() { return siglaUf; }
    public void setSiglaUf(String siglaUf) { this.siglaUf = siglaUf; }

    public Integer getPopulacaoAtendidaAgua() { return populacaoAtendidaAgua; }
    public void setPopulacaoAtendidaAgua(Integer populacaoAtendidaAgua) { this.populacaoAtendidaAgua = populacaoAtendidaAgua; }

    public Integer getPopulacaoAtendidaEsgoto() { return populacaoAtendidaEsgoto; }
    public void setPopulacaoAtendidaEsgoto(Integer populacaoAtendidaEsgoto) { this.populacaoAtendidaEsgoto = populacaoAtendidaEsgoto; }

    public Integer getPopulacaoUrbana() { return populacaoUrbana; }
    public void setPopulacaoUrbana(Integer populacaoUrbana) { this.populacaoUrbana = populacaoUrbana; }

    public Integer getPopulacaoUrbanaResidenteAgua() { return populacaoUrbanaResidenteAgua; }
    public void setPopulacaoUrbanaResidenteAgua(Integer populacaoUrbanaResidenteAgua) { this.populacaoUrbanaResidenteAgua = populacaoUrbanaResidenteAgua; }

    public Integer getPopulacaoUrbanaAtendidaAgua() { return populacaoUrbanaAtendidaAgua; }
    public void setPopulacaoUrbanaAtendidaAgua(Integer populacaoUrbanaAtendidaAgua) { this.populacaoUrbanaAtendidaAgua = populacaoUrbanaAtendidaAgua; }

    public Integer getPopulacaoUrbanaAtendidaAguaIbge() { return populacaoUrbanaAtendidaAguaIbge; }
    public void setPopulacaoUrbanaAtendidaAguaIbge(Integer populacaoUrbanaAtendidaAguaIbge) { this.populacaoUrbanaAtendidaAguaIbge = populacaoUrbanaAtendidaAguaIbge; }

    public Integer getPopulacaoUrbanaResidenteEsgoto() { return populacaoUrbanaResidenteEsgoto; }
    public void setPopulacaoUrbanaResidenteEsgoto(Integer populacaoUrbanaResidenteEsgoto) { this.populacaoUrbanaResidenteEsgoto = populacaoUrbanaResidenteEsgoto; }

    public Integer getPopulacaoUrbanaAtendidaEsgoto() { return populacaoUrbanaAtendidaEsgoto; }
    public void setPopulacaoUrbanaAtendidaEsgoto(Integer populacaoUrbanaAtendidaEsgoto) { this.populacaoUrbanaAtendidaEsgoto = populacaoUrbanaAtendidaEsgoto; }

    public Integer getPopulacaoUrbanaResidenteEsgotoIbge() { return populacaoUrbanaResidenteEsgotoIbge; }
    public void setPopulacaoUrbanaResidenteEsgotoIbge(Integer populacaoUrbanaResidenteEsgotoIbge) { this.populacaoUrbanaResidenteEsgotoIbge = populacaoUrbanaResidenteEsgotoIbge; }

    @Override
    public String toString() {
        return "MunicipioBasico{" +
                "ano=" + ano +
                ", idMunicipio=" + idMunicipio +
                ", siglaUf='" + siglaUf + '\'' +
                ", populacaoAtendidaAgua=" + populacaoAtendidaAgua +
                ", populacaoAtendidaEsgoto=" + populacaoAtendidaEsgoto +
                ", populacaoUrbana=" + populacaoUrbana +
                ", populacaoUrbanaResidenteAgua=" + populacaoUrbanaResidenteAgua +
                ", populacaoUrbanaAtendidaAgua=" + populacaoUrbanaAtendidaAgua +
                ", populacaoUrbanaAtendidaAguaIbge=" + populacaoUrbanaAtendidaAguaIbge +
                ", populacaoUrbanaResidenteEsgoto=" + populacaoUrbanaResidenteEsgoto +
                ", populacaoUrbanaAtendidaEsgoto=" + populacaoUrbanaAtendidaEsgoto +
                ", populacaoUrbanaResidenteEsgotoIbge=" + populacaoUrbanaResidenteEsgotoIbge +
                '}';
    }
}