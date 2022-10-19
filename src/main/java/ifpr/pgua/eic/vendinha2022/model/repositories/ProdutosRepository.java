package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.daos.ProdutoDAO;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class ProdutosRepository {
    
    private List<Produto> produtos;
    private ProdutoDAO dao;

    public ProdutosRepository(ProdutoDAO dao){
        this.dao = dao;
    }

    public Result adicionarProduto(String nome, String descricao, double valor, double quantidade){

        //a busca poder ser omitida se for configurada a restrição
        //no banco
        //validação dos atributos
        if(valor < 0){
            return Result.fail("Valor inválido!");
        }

        if(quantidade < 0){
            return Result.fail("Quantidade inválida!");
        }

        Produto produto = new Produto(nome,descricao,valor,quantidade);
        return dao.create(produto);
        

    }

    public List<Produto> getProdutos(){
        produtos = dao.getAll();
        return Collections.unmodifiableList(produtos);
    }

    public Result atualizarProduto(int id, String nome, String descricao, Double valor, Double quantidade) {
        
        if(valor < 0){
            return Result.fail("Valor inválido!");
        }

        if(quantidade < 0){
            return Result.fail("Quantidade inválida!");
        }

        Produto produto = new Produto(nome,descricao,valor,quantidade);
        return dao.update(id,produto);   
        
    }

}
