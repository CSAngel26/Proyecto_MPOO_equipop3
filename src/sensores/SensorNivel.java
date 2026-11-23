
package sensores;

import dispositivos.Sensor;
import interfaces.Configurable;

public class SensorNivel extends Sensor implements Configurable {

    private static final double MIN_FISICO = 0.0;
    private static final double MAX_FISICO = 100.0;

    private double umbral;
    private double nivelMinimo;

    public SensorNivel(String id, double umbral, double nivelMinimo) {
        super(id, "%");

        this.nivelMinimo = 0.0;

        setUmbral(umbral);
        setNivelMinimo(nivelMinimo);
    }

    @Override
    public void setUmbral(double umbral) {
        if (umbral < MIN_FISICO || umbral > MAX_FISICO) {
            throw new IllegalArgumentException(
                "Umbral invalido (" + umbral +
                "): debe estar entre 0 y 100 %"
            );
        }

        if (umbral <= nivelMinimo) {
            throw new IllegalArgumentException(
                "El umbral debe ser mayor que el nivel minimo"
            );
        }

        this.umbral = umbral;
    }

    @Override
    public double getUmbral() {
        return umbral;
    }

    public void setNivelMinimo(double nivelMinimo) {
        if (nivelMinimo < MIN_FISICO || nivelMinimo > MAX_FISICO) {
            throw new IllegalArgumentException(
                "Nivel minimo invalido (" + nivelMinimo +
                "): debe estar entre 0 y 100 %"
            );
        }

        if (nivelMinimo >= umbral) {
            throw new IllegalArgumentException(
                "El nivel minimo debe ser menor que el umbral"
            );
        }

        this.nivelMinimo = nivelMinimo;
    }

    public double getNivelMinimo() {
        return nivelMinimo;
    }

    @Override
    public boolean hayAlerta() {
        return getValorActual() > umbral ||
               getValorActual() < nivelMinimo;
    }

    @Override
    protected void validarLectura(double valor) {
        if (valor < MIN_FISICO || valor > MAX_FISICO) {
            throw new IllegalArgumentException(
                "Lectura de nivel imposible (" + valor +
                "): el sensor mide entre 0 y 100 %"
            );
        }
    }

    @Override
    public String toString() {
        return "Sensor " + super.toString() +
               " nivel, minimo " + nivelMinimo +
               " %, umbral " + umbral + " " + getUnidad();
    }
}
