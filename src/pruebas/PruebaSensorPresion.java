
package pruebas;

import sensores.SensorPresion;

public class PruebaSensorPresion {

    public static void main(String[] args) {

        SensorPresion sensor = new SensorPresion("P01", 60);

        System.out.println("=== PRUEBA SENSOR DE PRESION ===");

        sensor.recibir(40);
        System.out.println("Lectura: 40 bar");
        System.out.println("¿Hay alerta?: " + sensor.hayAlerta());

        sensor.recibir(70);
        System.out.println("Lectura: 70 bar");
        System.out.println("¿Hay alerta?: " + sensor.hayAlerta());

        try {
            sensor.recibir(110);
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println("\n" + sensor);
    }
}
