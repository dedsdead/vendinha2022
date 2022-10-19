package ifpr.pgua.eic.vendinha2022;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import ifpr.pgua.eic.vendinha2022.controllers.TelaClientes;
import ifpr.pgua.eic.vendinha2022.controllers.TelaNovaVenda;
import ifpr.pgua.eic.vendinha2022.controllers.TelaPrincipal;
import ifpr.pgua.eic.vendinha2022.controllers.TelaProdutos;
import ifpr.pgua.eic.vendinha2022.controllers.TelaVendas;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaNovaVendaViewModel;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaProdutosViewModel;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaVendasViewModel;
import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.daos.ClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.JDBCClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.JDBCProdutoDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.JDBCVendaDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.ProdutoDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.VendaDAO;
import ifpr.pgua.eic.vendinha2022.model.repositories.ClientesRepository;
import ifpr.pgua.eic.vendinha2022.model.repositories.ProdutosRepository;
import ifpr.pgua.eic.vendinha2022.model.repositories.VendaRepository;
import ifpr.pgua.eic.vendinha2022.utils.Navigator.BaseAppNavigator;
import ifpr.pgua.eic.vendinha2022.utils.Navigator.ScreenRegistryFXML;


/**
 * JavaFX App
 */
public class App extends BaseAppNavigator {

    private ClienteDAO clienteDao;
    private ClientesRepository clientesRepository;

    private ProdutoDAO produtoDao;
    private ProdutosRepository produtosRepository;

    private VendaDAO vendaDao;
    private VendaRepository vendaRepository;

    @Override
    public void init() throws Exception {
        // TODO Auto-generated method stub
        super.init();
        
        clienteDao = new JDBCClienteDAO(FabricaConexoes.getInstance());
        clientesRepository = new ClientesRepository(clienteDao);
        
        produtoDao = new JDBCProdutoDAO(FabricaConexoes.getInstance());
        produtosRepository = new ProdutosRepository(produtoDao);

        vendaDao = new JDBCVendaDAO(FabricaConexoes.getInstance());
        vendaRepository = new VendaRepository(vendaDao);
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        //gerenciador.salvar();
    }



    @Override
    public String getHome() {
        // TODO Auto-generated method stub
        return "PRINCIPAL";
    }

    @Override
    public String getAppTitle() {
        // TODO Auto-generated method stub
        return "Vendinha";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(getClass(), "fxml/principal.fxml", (o)->new TelaPrincipal()));
        registraTela("CLIENTES", new ScreenRegistryFXML(getClass(), "fxml/clientes.fxml", (o)->new TelaClientes(new TelaClientesViewModel(clientesRepository))));  
        registraTela("PRODUTOS", new ScreenRegistryFXML(getClass(), "fxml/produtos.fxml", (o)->new TelaProdutos(new TelaProdutosViewModel(produtosRepository))));  
        registraTela("NOVAVENDA", new ScreenRegistryFXML(getClass(), "fxml/novavenda.fxml", (o)-> new TelaNovaVenda(new TelaNovaVendaViewModel(produtosRepository, clientesRepository,vendaRepository))));
        registraTela("VENDAS", new ScreenRegistryFXML(getClass(), "fxml/vendas.fxml", (o)-> new TelaVendas(new TelaVendasViewModel(vendaRepository))));
    
    }


}