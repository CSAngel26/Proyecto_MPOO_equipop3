package actuadores;

import dispositivos.Actuador;
import interfaces.ControlManual;

public class Bomba extends Actuador implements ControlManual {
    private static final double CAUDAL_MINIMO = 0.0;

    private final ModoBomba modo;
    private double caudal;
    private boolean manual;

    public Bomba(String id, ModoBomba modo, double caudal) {
        super(id);
        if (modo == null) {
            throw new IllegalArgumentException("Modo inválido (null): debe ser LLENADO o VACIADO");
        }
        this.modo = modo;
        setCaudal(caudal);
        this.manual = false;
    }

    public ModoBomba getModo() {
        return modo;
    }

    public double getCaudal() {
        return caudal;
    }

    public void setCaudal(double caudal) {
        if (caudal <= CAUDAL_MINIMO) {
            throw new IllegalArgumentException(
                    "Caudal inválido (" + caudal + "): debe ser mayor que " + CAUDAL_MINIMO + " L/min"
            );
        }
        this.caudal = caudal;
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
        if (manual) {
            return getId() + " (" + modo + ") en modo manual, " + (isEstado() ? "encendida" : "apagada");
        }
        if (modo == ModoBomba.LLENADO) {
            if (alerta) {
                apagar();
                return getId() + " (LLENADO) apagada por alerta";
            }
            encender();
            return getId() + " (LLENADO) encendida a " + caudal + " L/min";
        }
        if (alerta) {
            encender();
            return getId() + " (VACIADO) encendida a " + caudal + " L/min por alerta";
        }
        apagar();
        return getId() + " (VACIADO) apagada";
    }

    @Override
    public String toString() {
        return "Actuador " + super.toString() + " bomba " + modo + ", caudal " + caudal + " L/min"
                + (manual ? ", MANUAL" : ", AUTO");
    }
}