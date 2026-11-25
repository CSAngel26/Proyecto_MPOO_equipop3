package app;

import excepciones.LimiteSeguroExcepcion;
import java.util.List;
import java.util.Scanner;
import nucleo.Planta;

public class Menu {
    private static final int OPCION_SALIR = 0;
    private static final int OPCION_VER_ESTADOS = 1;
    private static final int OPCION_REGISTRAR_LECTURA = 2;
    private static final int OPCION_EJECUTAR_CICLO = 3;
    private static final int OPCION_MODO_MANUAL = 4;
    private static final int OPCION_CONFIGURAR_UMBRAL = 5;

    private final Planta planta;
    private final Scanner scanner;

    public Menu(Planta planta) {
        if (planta == null) {
            throw new IllegalArgumentException("La planta no puede ser null");
        }
        this.planta = planta;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean ejecutando = true;

        while (ejecutando) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case OPCION_VER_ESTADOS:
                    mostrarEstados();
                    break;
                case OPCION_REGISTRAR_LECTURA:
                    registrarLectura();
                    break;
                case OPCION_EJECUTAR_CICLO:
                    ejecutarCiclo();
                    break;
                case OPCION_MODO_MANUAL:
                    gestionarModoManual();
                    break;
                case OPCION_CONFIGURAR_UMBRAL:
                    configurarUmbral();
                    break;
                case OPCION_SALIR:
                    System.out.println("Saliendo del menú de " + planta.getNombre() + "...");
                    ejecutando = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intenta con un número del 0 al 5.");
                    break;
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n=== CONTROL DE PLANTA: " + planta.getNombre() + " ===");
        System.out.println("1. Ver estado de sensores y actuadores");
        System.out.println("2. Registrar lectura en un sensor");
        System.out.println("3. Ejecutar ciclo de control");
        System.out.println("4. Activar / desactivar modo manual en actuador");
        System.out.println("5. Configurar umbral de un sensor");
        System.out.println("0. Salir");
    }

    private void mostrarEstados() {
        List<String> estados = planta.obtenerEstados();
        System.out.println("\n--- Estado actual ---");
        for (String estado : estados) {
            System.out.println("- " + estado);
        }
    }

    private void registrarLectura() {
        System.out.print("ID del sensor (ej. N01, P01, T01): ");
        String id = scanner.nextLine().trim();
        double valor = leerDecimal("Valor de lectura: ");

        try {
            planta.recibirLectura(id, valor);
            System.out.println("Lectura registrada correctamente en " + id + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar lectura: " + e.getMessage());
        }
    }

    private void ejecutarCiclo() {
        try {
            List<String> acciones = planta.ejecutarCiclo();
            System.out.println("\n--- Ciclo completado sin alertas ---");
            for (String accion : acciones) {
                System.out.println("- " + accion);
            }
        } catch (LimiteSeguroExcepcion e) {
            System.out.println("\n[ALERTA DE SEGURIDAD] " + e.getMessage());
            for (String accion : planta.obtenerEstados()) {
                System.out.println("- " + accion);
            }
        }
    }

    private void gestionarModoManual() {
        System.out.print("ID del actuador (ej. B01, V01): ");
        String id = scanner.nextLine().trim();
        System.out.print("¿Activar modo manual? (s/n): ");
        String resp = scanner.nextLine().trim().toLowerCase();

        boolean activar;
        if (resp.equals("s") || resp.equals("si") || resp.equals("sí")) {
            activar = true;
        } else if (resp.equals("n") || resp.equals("no")) {
            activar = false;
        } else {
            System.out.println("Respuesta inválida. Escribe 's' o 'n'.");
            return;
        }

        try {
            planta.cambiarModoManual(id, activar);
            System.out.println("Modo " + (activar ? "MANUAL" : "AUTOMÁTICO") + " aplicado en " + id + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de modo manual: " + e.getMessage());
        }
    }

    private void configurarUmbral() {
        System.out.print("ID del sensor (ej. N01, P01, T01): ");
        String id = scanner.nextLine().trim();
        double umbral = leerDecimal("Nuevo umbral: ");

        try {
            planta.configurarUmbralSensor(id, umbral);
            System.out.println("Umbral actualizado en " + id + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al configurar umbral: " + e.getMessage());
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: escribe un número entero.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: escribe un número válido.");
            }
        }
    }
}