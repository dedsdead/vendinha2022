package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Classe que representa uma linha da tabela de clientes. Cada
 * uma das colunas da tabela é ligada com um dos métodos definidos nesta
 * classe, a fim de determinar qual o valor que irá ser mostrado na coluna.
 */

public class ItemRow {
    
    private ItemVenda item;
    
    public ItemRow(ItemVenda item){
        this.item = item;
    }

    public ItemVenda getItem(){
        return item;
    }

    /**
     * Propriedade para representar o atributo nome do produto.
     * 
     * @return SimpleStringProperty com o valor do nome do produto.
     */

    public StringProperty produtoProperty(){
        return new SimpleStringProperty(item.getProduto().getNome());
    }

    /**
     * Propriedade para representar o atributo quantidade do item.
     * 
     * @return SimpleStringProperty com o valor da quantidade do item.
     */
    public DoubleProperty quantidadeProperty(){
        return new SimpleDoubleProperty(item.getQuantidade());
    }

    /**
     * Propriedade para representar o atributo valor de venda do item.
     * 
     * @return SimpleStringProperty com o valor de venda do item.
     */
    public DoubleProperty valorProperty(){
        return new SimpleDoubleProperty(item.getValor());
    }

}
