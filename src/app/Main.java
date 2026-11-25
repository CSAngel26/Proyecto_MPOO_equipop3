package app;

import actuadores.Alarma;
import actuadores.Bomba;
import actuadores.ModoBomba;
import actuadores.Ventilador;
import nucleo.Planta;
import sensores.SensorNivel;
import sensores.SensorPresion;
import sensores.SensorTemperatura;

public class Main {
    public static void main(String[] args) {
        Planta planta = new Planta("Planta Piloto 01");

        planta.agregarSensor(new SensorNivel("N01", 90.0, 20.0));
        planta.agregarSensor(new SensorPresion("P01", 60.0));
        planta.agregarSensor(new SensorTemperatura("T01", 100.0));

        planta.agregarActuador(new Bomba("B01", ModoBomba.LLENADO, 25.0));
        planta.agregarActuador(new Bomba("B02", ModoBomba.VACIADO, 30.0));
        planta.agregarActuador(new Ventilador("V01", 2));
        planta.agregarActuador(new Alarma("A01", 7));

        Menu menu = new Menu(planta);
        menu.iniciar();
    }
}