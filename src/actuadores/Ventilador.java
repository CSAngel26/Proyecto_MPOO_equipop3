package actuadores;

import dispositivos.Actuador;
import interfaces.ControlManual;

public class Ventilador extends Actuador implements ControlManual {
    private int velocidad;      // 1 a 3
    private boolean manual;

    public Ventilador(String id, int velocidad) {
        super(id);
        setVelocidad(velocidad);
        this.manual = false;    // por defecto trabaja solo
    }

    public void setVelocidad(int velocidad) {
        if (velocidad < 1 || velocidad > 3) {
            throw new IllegalArgumentException("Velocidad inválida (" + velocidad + "): el ventilador acepta 1, 2 o 3");
        }
        this.velocidad = velocidad;
    }

    public int getVelocidad() {
        return velocidad;
    }

    @Override
    public void activarManual() {
        manual = true;
    }

    @Override
    public void desactivarManual() {
        manual = false;
    }

    @Override
    public boolean isManual() {
        return manual;
    }

    @Override
    public String ejecutarAccion(boolean alerta) {
        if (manual) {   // en manual, la alerta no cambia nada: manda el operador
            return getId() + " en modo manual, " + (isEstado() ? "encendido" : "apagado");
        }
        if (alerta) {
            encender();
            return getId() + " enfriando a velocidad " + velocidad;
        }
        apagar();
        return getId() + " en reposo";
    }

    @Override
    public String toString() {
        return "Actuador " + super.toString() + " ventilador, velocidad " + velocidad + (manual ? ", MANUAL" : ", AUTO");
    }
}