package controlador.Listas;
import controlador.TDALista.LinkedList;
import controlador.listas.DAO.DataAccesObject;
import modelo.AgenteVendedor;

/**
 *
 * @author Asus
 */
public class VendedorControllerListas extends DataAccesObject<AgenteVendedor>{
    private LinkedList<AgenteVendedor> vendedores = new LinkedList<>();
    private AgenteVendedor vendedor;
    private Integer index = -1;
    
    public VendedorControllerListas() {
        super(AgenteVendedor.class);
    }

    public Boolean save(){
        this.vendedor.setId(generated_id());
        return save(vendedor);
    }
    
    public Boolean update(Integer index) {
        return update(vendedor, index);
    }
    
    public LinkedList<AgenteVendedor> getVendedores() {
        if(vendedores.isEmpty())
            vendedores = listall();
        return vendedores;
    }

    public void setVendedores(LinkedList<AgenteVendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public AgenteVendedor getVendedor() {
        if(vendedor == null)
            vendedor = new AgenteVendedor();
        return vendedor;
    }

    public void setVendedor(AgenteVendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
    
    
}
