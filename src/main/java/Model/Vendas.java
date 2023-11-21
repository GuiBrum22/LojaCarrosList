package Model;

public class Vendas {

    private String data;
    private String cliente;
    private String valor;
    private String tipoCarro;

    public Vendas(String data, String cliente, String valor, String tipoCarro) {
        this.data = data;
        this.cliente = cliente;
        this.valor = valor;
        this.tipoCarro = tipoCarro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoCarro() {
        return tipoCarro;
    }

    public void setTipoCarro(String tipoCarro) {
        this.tipoCarro = tipoCarro;
    }

}
