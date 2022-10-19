package ifpr.pgua.eic.vendinha2022.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Venda {
    

    private int id;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private LocalDateTime dataHora;
    private double total;
    private double desconto;


    public Venda(int id, Cliente cliente, List<ItemVenda> itens, LocalDateTime dataHora, double total,
            double desconto) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.dataHora = dataHora;
        this.total = total;
        this.desconto = desconto;
    }

    public Venda(Cliente cliente, LocalDateTime dataHora,List<ItemVenda> itens, double total, double desconto){
        this.itens = new ArrayList<>();
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.itens = itens;
        this.total = total;
        this.desconto = desconto;
    }

    public void adicionarProduto(Produto p, double quantidade){

        //busca um item na lista de itens da venda que contenha o produto
        Optional<ItemVenda> item = itens.stream().filter((it)->it.getProduto().getId()==p.getId()).findFirst();

        
        if(item.isPresent()){
            //se achou, acrescenta a quantidade
            ItemVenda it = item.get();
            it.setQuantidade(it.getQuantidade()+quantidade);
        }else{
            //se não, cria um novo item de venda e adiciona na lista de itens
            ItemVenda it = new ItemVenda();
            it.setProduto(p);
            it.setQuantidade(quantidade);
            itens.add(it);
        }
    }

    public boolean removerProduto(Produto p, double quantidade){
        
        //busca um item na lista de itens da venda que contenha o produto
        Optional<ItemVenda> item = itens.stream().filter((it)->it.getProduto().getId()==p.getId()).findFirst();

        if(item.isPresent()){
            ItemVenda it = item.get();
            it.setQuantidade(it.getQuantidade() - quantidade);
            if(it.getQuantidade() <=0 ){
                itens.remove(it);
            }
            return true;
        }

        return false;



    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public List<ItemVenda> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void setItens(List<ItemVenda> itens){
        this.itens = itens;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public double getDesconto() {
        return desconto;
    }
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        return "Venda [id=" + id + ", cliente=" + cliente + ", itens=" + itens + ", dataHora=" + dataHora + ", total="
                + total + ", desconto=" + desconto + "]";
    }
    
}
