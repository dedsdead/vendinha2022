package ifpr.pgua.eic.vendinha2022.model.daos;

import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public interface VendaDAO {
    Result create(Venda venda);
    List<Venda> getAll();
    List<ItemVenda>getItens(int id);
    
}
