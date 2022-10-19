package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCProdutoDAO implements ProdutoDAO {
    private static final String INSERT = "INSERT INTO oo2_produtos(nome,descricao,valor,quantidadeEstoque) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE oo2_produtos set nome=?, descricao=?, valor=?, quantidadeEstoque=? WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM oo2_produtos";
    private static final String SELECT_ID = "SELECT * FROM oo2_produtos WHERE id=?";
    
    
    private FabricaConexoes fabricaConexoes;

    public JDBCProdutoDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public Result create(Produto obj) {
        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection(); 
            
            //preparando o comando sql
            PreparedStatement pstm = con.prepareStatement(INSERT);
            
            //ajustando os parâmetros do comando
            pstm.setString(1, obj.getNome());
            pstm.setString(2, obj.getDescricao());
            pstm.setDouble(3, obj.getValor());
            pstm.setDouble(4, obj.getQuantidadeEstoque());

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Cliente criado com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Result update(int id, Produto obj) {
        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection(); 
            
            //preparando o comando sql
            PreparedStatement pstm = con.prepareStatement(UPDATE);
            
            //ajustando os parâmetros do comando
            pstm.setString(1, obj.getNome());
            pstm.setString(2, obj.getDescricao());
            pstm.setDouble(3, obj.getValor());
            pstm.setDouble(4, obj.getQuantidadeEstoque());
            pstm.setInt(5, id);

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Produto atualizado com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    private Produto buildFrom(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String descricao = rs.getString("descricao");
        Double valor = rs.getDouble("valor");
        Double quantidadeEstoque = rs.getDouble("quantidadeEstoque");

        Produto produto = new Produto(id,nome, descricao, valor, quantidadeEstoque);
        
        return produto;
    }

    @Override
    public List<Produto> getAll() {
        List<Produto> produtos = new ArrayList<>();
        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                Produto produto = buildFrom(rs);
                produtos.add(produto);
            }

            rs.close();
            pstm.close();
            con.close();

            return produtos;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }

    }

    @Override
    public Produto getById(int id) {
        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ID);

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            Produto produto = null; 

            while(rs.next()){
                produto = buildFrom(rs); 
            }

            rs.close();
            pstm.close();
            con.close();

            return produto;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Result delete(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
