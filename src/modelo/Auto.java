package modelo;

/**
 *
 * @author Asus
 */
public class Auto {
    private Integer id;
    private String placa;
    private String color;
    private Double precio;
    private Double velocidadMax;
    private Boolean estado;
    private Integer id_marca;

    public Auto() {
    }

    public Auto(Integer id, String placa, String color, Double precio, Double velocidadMax, Boolean estado, Integer id_marca) {
        this.id = id;
        this.placa = placa;
        this.color = color;
        this.precio = precio;
        this.velocidadMax = velocidadMax;
        this.estado = estado;
        this.id_marca = id_marca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getVelocidadMax() {
        return velocidadMax;
    }

    public void setVelocidadMax(Double velocidadMax) {
        this.velocidadMax = velocidadMax;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String verEstado(){
        if(this.estado == true)
            return "Disponible";
        else
            return "Vendido";
    }
    
    public void fijarEstado(String estado){
        if(estado.equalsIgnoreCase("Disponible"))
            this.estado = true;
        else
            this.estado = false;
    }
    
    public Integer getId_marca() {
        return id_marca;
    }

    public void setId_marca(Integer id_marca) {
        this.id_marca = id_marca;
    }

    @Override
    public String toString() {
        return placa;
    }
    
    
}
