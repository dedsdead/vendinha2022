package ifpr.pgua.eic.vendinha2022.model.entities;

public class ItemVenda {
    
    private int id;
    private Produto produto;
    private double valor;
    private double quantidade;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public double getValor() {
        return valor;
    }
    public void setValorVenda(double valor) {
        this.valor = valor;
    }
    public double getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    
}
