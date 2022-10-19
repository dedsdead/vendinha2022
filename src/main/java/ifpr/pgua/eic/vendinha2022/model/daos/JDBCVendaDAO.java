package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCVendaDAO implements VendaDAO {

    private static final String INSERT = "INSERT INTO oo2_vendas(dataHora,idCliente,total,desconto) VALUES (?,?,?,?)";
    private static final String INSERT_ITEM = "INSERT INTO oo2_itensvenda(idVenda,idProduto,valor,quantidade) VALUES (?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM oo2_vendas";
    private static final String SELECT_ITENS = "SELECT * FROM oo2_itensvenda WHERE idVenda=?";
    
    private FabricaConexoes fabricaConexoes;
    private JDBCClienteDAO clienteDAO;
    private JDBCProdutoDAO produtoDAO;

    public JDBCVendaDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
        this.clienteDAO = new JDBCClienteDAO(fabricaConexoes);
        this.produtoDAO = new JDBCProdutoDAO(fabricaConexoes);
    }


    @Override
    public Result create(Venda venda) {
        try{
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);

            pstm.setTimestamp(1, Timestamp.valueOf(venda.getDataHora()));
            pstm.setInt(2, venda.getCliente().getId());
            pstm.setDouble(3, venda.getTotal());
            pstm.setDouble(4, venda.getDesconto());

            pstm.execute();

            //Pegar o id gerado por autoincremento
            ResultSet resultSet = pstm.getGeneratedKeys();
            resultSet.next();
            int idVenda = resultSet.getInt(1);

            PreparedStatement pstmItem = con.prepareStatement(INSERT_ITEM);

            for(ItemVenda item:venda.getItens()){
                pstmItem.setInt(1, idVenda);
                pstmItem.setInt(2, item.getProduto().getId());
                pstmItem.setDouble(3, item.getValor());
                pstmItem.setDouble(4, item.getQuantidade());

                pstmItem.execute();
            }

            pstmItem.close();
            pstm.close();
            resultSet.close();
            con.close();

            return Result.success("Venda criada com sucesso!");


        }catch(SQLException e){
            return Result.fail(e.getMessage());
        }
    }

    private Venda buildFrom(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        Cliente c = clienteDAO.getById(rs.getInt("idCliente"));
        LocalDateTime dataHora = rs.getTimestamp("dataHora").toLocalDateTime();
        Double total = rs.getDouble("total");
        Double desconto = rs.getDouble("desconto");
        List<ItemVenda> i = getItens(id);

        Venda venda = new Venda(id, c, i, dataHora, total, desconto);

        return venda;
        
    }

    @Override
    public List<Venda> getAll() {
        List<Venda> vendas = new ArrayList<>();
        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rsl = pstm.executeQuery();

            while(rsl.next()){
                Venda v = buildFrom(rsl);
                vendas.add(v);
            }

            rsl.close();
            pstm.close();
            con.close();

            return vendas;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }


    @Override
    public List<ItemVenda> getItens(int id) {
        List<ItemVenda> itens = new ArrayList<>();

        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ITENS);

            pstm.setInt(1, id);

            ResultSet rsi = pstm.executeQuery();
            
            while(rsi.next()){
                int idI = rsi.getInt("id");
                Produto p = produtoDAO.getById(rsi.getInt("idProduto"));
                Double valor = rsi.getDouble("valor");
                Double quantidade = rsi.getDouble("quantidade");

                ItemVenda item = new ItemVenda();
                item.setId(idI);
                item.setProduto(p);
                item.setValorVenda(valor);
                item.setQuantidade(quantidade);
                itens.add(item);
            }

            rsi.close();
            pstm.close();
            con.close();

            return itens;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }

    }
    
}
