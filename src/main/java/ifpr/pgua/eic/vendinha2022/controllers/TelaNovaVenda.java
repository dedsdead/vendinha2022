package ifpr.pgua.eic.vendinha2022.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaNovaVendaViewModel;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TelaNovaVenda extends BaseController implements Initializable {

    @FXML
    private MFXButton btAdicionar;

    @FXML
    private MFXButton btFinalizar;

    @FXML
    private MFXComboBox<Cliente> cbCliente;

    @FXML
    private MFXComboBox<Produto> cbProduto;

    @FXML
    private Label lbTotal;

    @FXML
    private TableView<ItemVenda> tbItens;

    @FXML
    private TableColumn<ItemVenda,String> tbcProduto;

    @FXML
    private TableColumn<ItemVenda,String> tbcQuantidade;

    @FXML
    private TableColumn<ItemVenda,String> tbcValorUnitario;

    @FXML
    private TableColumn<ItemVenda,String> tbcValorItem;


    @FXML
    private MFXTextField tfData;

    @FXML
    private MFXTextField tfQuantidade;

    private TelaNovaVendaViewModel viewModel;

    public TelaNovaVenda(TelaNovaVendaViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.cbCliente.setItems(viewModel.getClientes());
        this.cbProduto.setItems(viewModel.getProdutos());

        this.tfQuantidade.textProperty().bindBidirectional(viewModel.getQuantidadeProperty());
        this.tfData.textProperty().bindBidirectional(viewModel.getDataProperty());

        this.lbTotal.textProperty().bindBidirectional(viewModel.getValorTotalProperty());

        this.cbCliente.selectedItemProperty().addListener(new ChangeListener<Cliente>() {
            @Override
            public void changed(ObservableValue<? extends Cliente> arg0, Cliente arg1, Cliente arg2) {
                viewModel.getClienteProperty().set(arg2);
            }
        });

        this.cbProduto.selectedItemProperty().addListener(new ChangeListener<Produto>() {
            @Override
            public void changed(ObservableValue<? extends Produto> arg0, Produto arg1, Produto arg2) {
                viewModel.getProdutoProperty().set(arg2);
            }
        });

        tbcProduto.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getProduto().getNome()));
        tbcQuantidade.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getQuantidade()+""));
        tbcValorUnitario.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getProduto().getValor()+""));
        tbcValorItem.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getProduto().getValor()*item.getValue().getQuantidade()+""));

        tbItens.setItems(viewModel.getItensVenda());

        viewModel.carregaListas();

    }

    @FXML
    private void adicionarItem(){
        Result resultado = viewModel.adicionaItem();
        showMessage(resultado);
    }

    @FXML
    private void finalizar(){
        Result resultado = viewModel.finalizarVenda();
        showMessage(resultado);
    }

}

