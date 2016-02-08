package curisteando.com.semaforonutrimental.entities;

import android.content.Context;

import org.apache.commons.lang3.builder.ToStringBuilder;

import curisteando.com.semaforonutrimental.utilidades.TipoAlimento;
import curisteando.com.semaforonutrimental.utilidades.TipoMedidas;

/**
 * <p>
 * Clase encargada de mapear valores y tipo de valores para cada valor requerido en el calculo.
 * </p>
 *
 * @author Capitan Durango
 * @since 02/11/2014.
 */
public class ParametrosCalculo {

    private TipoAlimento tipoAlimento;
    private double tamanioPorcion;
    private TipoMedidas tamanioPorcionMedida;
    private double azucares;
    private TipoMedidas azucaresMedida;
    private double grasas;
    private TipoMedidas grasasMedida;
    private double sodio;
    private TipoMedidas sodioMedida;
    private int tipoProducto;

    public ParametrosCalculo() {
    }


    public int getTipoProducto() { return tipoProducto; }

    public void setTipoProducto(int tipoProducto) {this.tipoProducto = tipoProducto; }

    public TipoAlimento getTipoAlimento() {
        return tipoAlimento;
    }

    public void setTipoAlimento(TipoAlimento tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }

    public double getTamanioPorcion() {
        return tamanioPorcion;
    }

    public void setTamanioPorcion(double tamanioPorcion) {
        this.tamanioPorcion = tamanioPorcion;
    }

    public TipoMedidas getTamanioPorcionMedida() {
        return tamanioPorcionMedida;
    }

    public void setTamanioPorcionMedida(TipoMedidas tamanioPorcionMedida) {
        this.tamanioPorcionMedida = tamanioPorcionMedida;
    }

    public double getAzucares() {
        return azucares;
    }

    public void setAzucares(double azucares) {
        this.azucares = azucares;
    }

    public TipoMedidas getAzucaresMedida() {
        return azucaresMedida;
    }

    public void setAzucaresMedida(TipoMedidas azucaresMedida) {
        this.azucaresMedida = azucaresMedida;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    public TipoMedidas getGrasasMedida() {
        return grasasMedida;
    }

    public void setGrasasMedida(TipoMedidas grasasMedida) {
        this.grasasMedida = grasasMedida;
    }

    public double getSodio() {
        return sodio;
    }

    public void setSodio(double sodio) {
        this.sodio = sodio;
    }

    public TipoMedidas getSodioMedida() {
        return sodioMedida;
    }

    public void setSodioMedida(TipoMedidas sodioMedida) {
        this.sodioMedida = sodioMedida;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tipoAlimento",tipoAlimento)
                .append("tamanioPorcion",tamanioPorcion)
                .append("tamanioPorcionMedida",tamanioPorcionMedida)
                .append("azucares",azucares)
                .append("azucaresMedida",azucaresMedida)
                .append("grasas",grasas)
                .append("grasasMedida",grasasMedida)
                .append("sodio",sodio)
                .append("sodioMedida",sodioMedida).toString();
    }
}
