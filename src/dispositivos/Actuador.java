package dispositivos;

public abstract class Actuador extends DispositivoIndustrial {

    public Actuador(String id) {
        super(id);
    }

    public void encender() {
        setEstado(true);
    }

    public void apagar() {
        setEstado(false);
    }

    // La planta le avisa si hay alerta global, cada actuador reacciona a su modo
    public abstract String ejecutarAccion(boolean alerta);

    @Override
    public String evaluarEstado() {
        return getId() + " está " + (isEstado() ? "encendido" : "apagado");
    }
}