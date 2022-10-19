package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import java.time.format.DateTimeFormatter;

import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Classe que representa uma linha da tabela de vendas. Cada
 * uma das colunas da tabela é ligada com um dos métodos definidos nesta
 * classe, a fim de determinar qual o valor que irá ser mostrado na coluna.
 */

public class VendaRow {
    
    private Venda venda;
    
    public VendaRow(Venda venda){
        this.venda = venda;
    }

    public Venda getVenda(){
        return venda;
    }


    /**
     * Propriedade para representar o atributo id da venda.
     * 
     * @return SimpleStringProperty com o valor do id da venda.
     */
    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(venda.getId()));
    }

    /**
     * Propriedade para representar o atributo nome do cliente.
     * 
     * @return SimpleStringProperty com o valor do nome do cliente.
     */

    public StringProperty clienteProperty(){
        return new SimpleStringProperty(venda.getCliente().getNome());
    }

    /**
     * Propriedade para representar o atributo data/hora da venda.
     * 
     * @return SimpleStringProperty com o valor da data/hora da venda.
     */
    public StringProperty dataHoraProperty(){
        return new SimpleStringProperty(venda.getDataHora().format(DateTimeFormatter.ofPattern("d/MM/uuuu HH:mm:ss")));
    }

    /**
     * Propriedade para representar o atributo total da venda.
     * 
     * @return SimpleStringProperty com o valor do total da venda.
     */
    public StringProperty totalProperty(){
        return new SimpleStringProperty(String.valueOf(venda.getTotal()));
    }

    /**
     * Propriedade para representar o atributo desconto da venda.
     * 
     * @return SimpleStringProperty com o valor do desconto da venda.
     */
    public StringProperty descontoProperty(){
        return new SimpleStringProperty(String.valueOf(venda.getDesconto()));
    }



}
