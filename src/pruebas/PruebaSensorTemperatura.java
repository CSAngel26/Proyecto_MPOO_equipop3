package pruebas;

import sensores.SensorTemperatura;

public class PruebaSensorTemperatura {

    public static void main(String[] args) {

        SensorTemperatura sensor = new SensorTemperatura("T01", 100);

        System.out.println("=== PRUEBA SENSOR DE TEMPERATURA ===");

        sensor.recibir(50);
        System.out.println("Lectura: 50 °C");
        System.out.println("¿Hay alerta?: " + sensor.hayAlerta());

        sensor.recibir(120);
        System.out.println("Lectura: 120 °C");
        System.out.println("¿Hay alerta?: " + sensor.hayAlerta());

        try {
            sensor.recibir(600);
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println("\n" + sensor);
    }
}