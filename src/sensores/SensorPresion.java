
package sensores;

import dispositivos.Sensor;
import interfaces.Configurable;

public class SensorPresion extends Sensor implements Configurable {

    private static final double MIN_FISICO = 0.0;
    private static final double MAX_FISICO = 100.0;

    private double umbral;

    public SensorPresion(String id, double umbral) {
        super(id, "bar");
        setUmbral(umbral);
    }

    @Override
    public void setUmbral(double umbral) {
        if (umbral < MIN_FISICO || umbral > MAX_FISICO) {
            throw new IllegalArgumentException(
                "Umbral de presion invalido (" + umbral +
                "): debe estar entre " + MIN_FISICO +
                " y " + MAX_FISICO + " bar"
            );
        }
        this.umbral = umbral;
    }

    @Override
    public double getUmbral() {
        return umbral;
    }

    @Override
    public boolean hayAlerta() {
        return getValorActual() > umbral;
    }

    @Override
    protected void validarLectura(double valor) {
        if (valor < MIN_FISICO || valor > MAX_FISICO) {
            throw new IllegalArgumentException(
                "Lectura de presion imposible (" + valor +
                "): el sensor mide entre " + MIN_FISICO +
                " y " + MAX_FISICO + " bar"
            );
        }
    }

    @Override
    public String toString() {
        return "Sensor " + super.toString() +
               " presion, umbral " + umbral + " " + getUnidad();
    }
}
