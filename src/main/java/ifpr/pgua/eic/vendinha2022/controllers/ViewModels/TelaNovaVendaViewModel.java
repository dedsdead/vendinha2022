package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.repositories.ClientesRepository;
import ifpr.pgua.eic.vendinha2022.model.repositories.ProdutosRepository;
import ifpr.pgua.eic.vendinha2022.model.repositories.VendaRepository;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TelaNovaVendaViewModel {
    
    private LocalDateTime dataHora;
    private StringProperty dataProperty = new SimpleStringProperty();
    private StringProperty quantidadeProperty = new SimpleStringProperty();
    private StringProperty valorTotalProperty = new SimpleStringProperty();

    private ObjectProperty<Cliente> clienteProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Produto> produtoProperty = new SimpleObjectProperty<>();

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private ObservableList<Produto> produtos = FXCollections.observableArrayList();

    private ObservableList<ItemVenda> itensVenda = FXCollections.observableArrayList();
    
    private ProdutosRepository produtosRepository;
    private ClientesRepository clientesRepository;
    private VendaRepository vendaRepository;
    

    public TelaNovaVendaViewModel(ProdutosRepository produtosRepository, 
                                  ClientesRepository clientesRepository,
                                  VendaRepository vendaRepository ){
        this.produtosRepository = produtosRepository;
        this.clientesRepository = clientesRepository;
        this.vendaRepository = vendaRepository;

        dataHora = LocalDateTime.now();
        dataProperty.set(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(dataHora));

        
    }

    public ObservableList<Produto> getProdutos(){
        return produtos;
    }

    public ObservableList<Cliente> getClientes(){
        return clientes;
    }

    public ObservableList<ItemVenda> getItensVenda(){
        return itensVenda;
    }

    public void carregaListas(){
        clientes.clear();
        clientes.addAll(clientesRepository.getClientes());
        
        produtos.clear();
        produtos.addAll(produtosRepository.getProdutos());
    }

    public StringProperty getDataProperty(){
        return dataProperty;
    }

    public ObjectProperty<Cliente> getClienteProperty(){
        return clienteProperty;
    }

    public ObjectProperty<Produto> getProdutoProperty(){
        return produtoProperty;
    }

    public StringProperty getQuantidadeProperty(){
        return quantidadeProperty;
    }

    public StringProperty getValorTotalProperty(){
        return valorTotalProperty;
    }

    public Result adicionaItem(){
        
        if(produtoProperty.get() == null){
            return Result.fail("Nenhum produto adicionado!");
        }

        double quantidade = 0;
        try{
            quantidade = Double.parseDouble(quantidadeProperty.getValue());
        }catch(NumberFormatException e){
            return Result.fail("Quantidade inválida!");
        }

        if(quantidade == 0){
            return Result.fail("Quantidade deve ser maior que 0!");
        }

        ItemVenda item = new ItemVenda();
        Produto produto = produtoProperty.get();

        if(quantidade > produto.getQuantidadeEstoque()){
            return Result.fail("Quantidade deve ser menor que o estoque do produto!");

        }

        // ATUALIZA A QUANTIDADE EM ESTOQUE DO PRODUTO
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);

        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorVenda(produto.getValor());

        itensVenda.add(item);

        quantidadeProperty.set("");

        double valor=0;

        valor = pegaTotal();

        valorTotalProperty.setValue("R$ "+valor);
        
        return Result.success("Adicionado!");

    }

    public Result finalizarVenda(){

        if(clienteProperty.get() == null){
            return Result.fail("Cliente não selecionado!");
        }

        if(itensVenda.size() == 0){
            return Result.fail("Não foram inseridos produtos!");
        }

        Cliente cliente = clienteProperty.get();

        // ATUALIZA OS PRODUTOS NO BANCO, COM ESTOQUE NOVO
        for (Produto p : getProdutos())
            produtosRepository.atualizarProduto(p.getId(), p.getNome(), p.getDescricao(), p.getValor(), p.getQuantidadeEstoque());

        return vendaRepository.cadastrar(dataHora,cliente,itensVenda, pegaTotal(), 0);

    }

    public double pegaTotal(){
        return itensVenda.stream().map(it->it.getQuantidade()*it.getProduto().getValor()).reduce(0.0,Double::sum);
    }



}
