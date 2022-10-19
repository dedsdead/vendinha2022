package ifpr.pgua.eic.vendinha2022.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.ItemRow;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaVendasViewModel;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.VendaRow;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TelaVendas extends BaseController implements Initializable{
    @FXML
    private TableColumn<VendaRow,String> tbcId;

    @FXML
    private TableColumn<VendaRow, String> tbcCliente;

    @FXML
    private TableColumn<VendaRow, String> tbcDataHora;

    @FXML
    private TableColumn<VendaRow, String> tbcTotalVenda;

    @FXML
    private TableColumn<VendaRow, String> tbcDesconto;

    @FXML
    private TableView<VendaRow> tbVendas;

    @FXML
    private TableColumn<ItemRow,String> tbcItemProduto;

    @FXML
    private TableColumn<ItemRow, String> tbcItemQuantidade;

    @FXML
    private TableColumn<ItemRow, String> tbcItemValor;

    @FXML
    private TableView<ItemRow> tbItens;

    private TelaVendasViewModel viewModel;

    public TelaVendas(TelaVendasViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //define quais serão as propriedades que servirão para preencher
        //o valor da coluna. Note que o nome da propriedade deve possuir
        //um get equivalente no modelo que representa a linha da tabela.
        tbcCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tbcDataHora.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        tbcTotalVenda.setCellValueFactory(new PropertyValueFactory<>("total"));
        tbcDesconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        //liga o conjunto de itens da tabela com a lista de clientes do viewmodel
        tbVendas.setItems(viewModel.getVendas());

        //liga a propriedade selecionado do viewmodel com a tabela
        viewModel.selecionadoProperty().bind(tbVendas.getSelectionModel().selectedItemProperty());

        viewModel.alertProperty().addListener((ChangeListener<Result>) (observable, oldVal, newVal) -> {
            // TODO Auto-generated method stub
            showMessage(newVal);
        });
        
    }

    @FXML
    private void atualizar(MouseEvent event){
        if(event.getClickCount() == 2){
            viewModel.atualizar();

            tbcItemProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
            tbcItemQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            tbcItemValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
            tbItens.setItems(viewModel.getItems());
            
        }
        
    }
    
}
