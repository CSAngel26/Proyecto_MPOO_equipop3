package nucleo;

import dispositivos.Actuador;
import dispositivos.Sensor;
import excepciones.LimiteSeguroExcepcion;
import interfaces.Configurable;
import interfaces.ControlManual;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Planta {
    private final String nombre;
    private final List<Sensor> sensores;
    private final List<Actuador> actuadores;

    public Planta(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la planta no puede estar vacío");
        }
        this.nombre = nombre.trim();
        this.sensores = new ArrayList<>();
        this.actuadores = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarSensor(Sensor sensor) {
        if (sensor == null) {
            throw new IllegalArgumentException("El sensor no puede ser null");
        }
        sensores.add(sensor);
    }

    public void agregarActuador(Actuador actuador) {
        if (actuador == null) {
            throw new IllegalArgumentException("El actuador no puede ser null");
        }
        actuadores.add(actuador);
    }

    public List<Sensor> getSensores() {
        return Collections.unmodifiableList(sensores);
    }

    public List<Actuador> getActuadores() {
        return Collections.unmodifiableList(actuadores);
    }

    public Sensor buscarSensor(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del sensor no puede estar vacío");
        }
        for (Sensor s : sensores) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return s;
            }
        }
        throw new IllegalArgumentException("No se encontró el sensor con ID: " + id);
    }

    public Actuador buscarActuador(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del actuador no puede estar vacío");
        }
        for (Actuador a : actuadores) {
            if (a.getId().equalsIgnoreCase(id.trim())) {
                return a;
            }
        }
        throw new IllegalArgumentException("No se encontró el actuador con ID: " + id);
    }

    public void recibirLectura(String idSensor, double valor) {
        Sensor sensor = buscarSensor(idSensor);
        sensor.recibir(valor);
    }

    public void configurarUmbralSensor(String idSensor, double nuevoUmbral) {
        Sensor sensor = buscarSensor(idSensor);
        if (!(sensor instanceof Configurable)) {
            throw new IllegalArgumentException("El sensor " + sensor.getId() + " no es configurable");
        }
        ((Configurable) sensor).setUmbral(nuevoUmbral);
    }

    public void cambiarModoManual(String idActuador, boolean activar) {
        Actuador actuador = buscarActuador(idActuador);
        if (!(actuador instanceof ControlManual)) {
            throw new IllegalArgumentException("El actuador " + actuador.getId() + " no admite control manual");
        }
        ControlManual cm = (ControlManual) actuador;
        if (activar) {
            cm.activarManual();
        } else {
            cm.desactivarManual();
        }
    }

    public List<String> ejecutarCiclo() throws LimiteSeguroExcepcion {
        boolean hayAlertaGlobal = false;
        StringBuilder detallesAlerta = new StringBuilder();

        for (Sensor s : sensores) {
            if (s.hayAlerta()) {
                hayAlertaGlobal = true;
                if (detallesAlerta.length() > 0) {
                    detallesAlerta.append(" | ");
                }
                detallesAlerta.append(s.evaluarEstado());
            }
        }

        List<String> resultados = new ArrayList<>();
        for (Actuador a : actuadores) {
            resultados.add(a.ejecutarAccion(hayAlertaGlobal));
        }

        if (hayAlertaGlobal) {
            throw new LimiteSeguroExcepcion("Alerta en " + nombre + ": " + detallesAlerta);
        }

        return resultados;
    }

    public List<String> obtenerEstados() {
        List<String> estados = new ArrayList<>();
        for (Sensor s : sensores) {
            estados.add(s.evaluarEstado());
        }
        for (Actuador a : actuadores) {
            estados.add(a.evaluarEstado());
        }
        return estados;
    }
}