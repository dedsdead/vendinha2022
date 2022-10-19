package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.repositories.VendaRepository;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe para representar os dados da tela de vendas, bem
 * como controlar o que irá ocorrer.
 */

public class TelaVendasViewModel {
    //controle de renderização da venda selecionada
    // private boolean atualizar = false;

    /* Lista que será utilizada para povar a TableView */
    private ObservableList<VendaRow> obsVendas = FXCollections.observableArrayList();
    private ObservableList<ItemRow> obsItens = FXCollections.observableArrayList();

    /* Objeto que serve para indicar qual linha da tabela está selecionada. */
    private ObjectProperty<VendaRow> selecionado = new SimpleObjectProperty<>();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private VendaRepository repository;

    public TelaVendasViewModel(VendaRepository repository) {
        this.repository = repository;

        updateList();

    }

    /*
     * Atualiza a lista observável de vendas, que por consequência irá
     * atualizar o conteúdo mostrado pela TableView.
     */
    private void updateList() {
        obsVendas.clear();
        for (Venda v : repository.getVendas()) {
            obsVendas.add(new VendaRow(v));
        }
    }

    public ObservableList<VendaRow> getVendas() {
        return this.obsVendas;
    }

    public ObservableList<ItemRow> getItems() {
        return this.obsItens;
    }

    public ObjectProperty<Result> alertProperty() {
        return alertProperty;
    }

    /* Métodos para acesso as propriedades. */

    public ObjectProperty<VendaRow> selecionadoProperty() {
        return selecionado;
    }

    public void atualizar() {
        obsItens.clear();
        Venda v = selecionado.get().getVenda();

        for (ItemVenda iv : v.getItens()){
            obsItens.add(new ItemRow(iv));
        }

    }

}
