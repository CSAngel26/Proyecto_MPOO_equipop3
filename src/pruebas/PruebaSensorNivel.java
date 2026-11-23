package pruebas;

import sensores.SensorNivel;

public class PruebaSensorNivel {

    public static void main(String[] args) {

        SensorNivel sensor = new SensorNivel("N01", 90, 20);

        System.out.println("=== PRUEBA SENSOR DE NIVEL ===");

        sensor.recibir(50);
        System.out.println("Lectura: 50%");
        System.out.println("¿Hay alerta?: " + sensor.hayAlerta());

        sensor.recibir(95);
        System.out.println("Lectura: 95%");
        System.out.println("¿Hay alerta?: " + sensor.hayAlerta());

        sensor.recibir(10);
        System.out.println("Lectura: 10%");
        System.out.println("¿Hay alerta?: " + sensor.hayAlerta());

        try {
            sensor.recibir(110);
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println("\n" + sensor);
    }
}