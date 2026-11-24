package actuadores;

import dispositivos.Actuador;

public class Alarma extends Actuador {
    private static final int VOLUMEN_MIN = 0;
    private static final int VOLUMEN_MAX = 10;

    private int volumen;

    public Alarma(String id, int volumen) {
        super(id);
        setVolumen(volumen);
    }

    public int getVolumen() {
        return volumen;
    }

    public void setVolumen(int volumen) {
        if (volumen < VOLUMEN_MIN || volumen > VOLUMEN_MAX) {
            throw new IllegalArgumentException(
                    "Volumen inválido (" + volumen + "): debe estar entre " + VOLUMEN_MIN + " y " + VOLUMEN_MAX
            );
        }
        this.volumen = volumen;
    }

    @Override
    public String ejecutarAccion(boolean alerta) {
        if (alerta) {
            encender();
            return getId() + " encendida sonando a volumen " + volumen;
        }
        apagar();
        return getId() + " apagada en silencio";
    }

    @Override
    public String toString() {
        return "Actuador " + super.toString() + " alarma, volumen " + volumen;
    }
}