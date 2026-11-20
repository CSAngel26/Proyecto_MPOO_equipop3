package sensores;

import dispositivos.Sensor;
import interfaces.Configurable;

public class SensorTemperatura extends Sensor implements Configurable{
    private static final double MIN_FISICO = -50.0;
    private static final double MAX_FISICO = 500.0;
    private double umbral;

    public SensorTemperatura(String id, double umbral) {
        super(id, "°C");    // esta es la unidad fija para nuestro sensor
        setUmbral(umbral);  //reutilizamos el setter de la interfaz configurable 
    }

    @Override 
    public void setUmbral(double umbral){
        if (umbral < MIN_FISICO || umbral > MAX_FISICO) {
            throw new IllegalArgumentException("Umbral de temperatura inválido (" + umbral + "): debe estar entre " + MIN_FISICO + " y " + MAX_FISICO + " °C");
        }
        this.umbral = umbral;
    }

    @Override 
    public double getUmbral(){
        return umbral;
    }

    @Override 
    public boolean hayAlerta(){
        return getValorActual() > umbral;
    }

    @Override
    protected void validarLectura(double valor) {
        if (valor < MIN_FISICO || valor > MAX_FISICO) {
            throw new IllegalArgumentException("Lectura de temperatura imposible (" + valor + "): el sensor mide entre " + MIN_FISICO + " y " + MAX_FISICO + " °C");
        }
    }

    @Override
    public String toString(){
        return "Sensor " + super.toString() + " temperatura, umbral " + umbral + " " + getUnidad();
    }
    
}
