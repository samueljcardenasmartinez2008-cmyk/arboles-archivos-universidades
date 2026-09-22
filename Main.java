import java.util.ArrayList;
import java.util.List;

public class Main {

    static class Nodo {
        String nombre;
        boolean esCarpeta;
        List<Nodo> hijos = new ArrayList<>();

        Nodo(String nombre, boolean esCarpeta) {
            this.nombre = nombre;
            this.esCarpeta = esCarpeta;
        }

        void agregar(Nodo hijo) {
            if (esCarpeta) {
                hijos.add(hijo);
            } else {
                System.out.println("Error: " + nombre + " es un archivo, no admite hijos.");
            }
        }
    }

    static void mostrar(Nodo n, String sangria) {
        String marca = n.esCarpeta ? "[Carpeta] " : "[Archivo] ";
        System.out.println(sangria + marca + n.nombre);
        for (Nodo h : n.hijos) {
            mostrar(h, sangria + "    ");
        }
    }

    static int contarArchivos(Nodo n) {
        if (!n.esCarpeta) {
            return 1;
        }
        int total = 0;
        for (Nodo h : n.hijos) {
            total += contarArchivos(h);
        }
        return total;
    }

    static int altura(Nodo n) {
        if (n.hijos.isEmpty()) {
            return 0;
        }
        int max = 0;
        for (Nodo h : n.hijos) {
            max = Math.max(max, altura(h));
        }
        return max + 1;
    }

    static Nodo buscar(Nodo n, String nombre) {
        if (n.nombre.equalsIgnoreCase(nombre)) {
            return n;
        }
        for (Nodo h : n.hijos) {
            Nodo encontrado = buscar(h, nombre);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Nodo raiz = new Nodo("Universidad", true);
        Nodo programacion = new Nodo("Programacion", true);
        Nodo calculo = new Nodo("Calculo", true);
        Nodo fisica = new Nodo("Fisica", true);
        Nodo laboratorio = new Nodo("Laboratorio", true);

        raiz.agregar(programacion);
        raiz.agregar(calculo);
        raiz.agregar(fisica);

        programacion.agregar(new Nodo("Taller1.java", false));
        programacion.agregar(new Nodo("Taller2.java", false));
        calculo.agregar(new Nodo("Parcial.pdf", false));
        fisica.agregar(new Nodo("Serway_Cap2.pdf", false));
        fisica.agregar(laboratorio);
        laboratorio.agregar(new Nodo("Informe1.docx", false));

        System.out.println("=== ARBOL DE CARPETAS ===");
        mostrar(raiz, "");

        System.out.println("\nTotal de archivos (hojas): " + contarArchivos(raiz));
        System.out.println("Altura del arbol: " + altura(raiz));

        Nodo resultado = buscar(raiz, "Informe1.docx");
        System.out.println("Busqueda 'Informe1.docx': "
                + (resultado != null ? "encontrado" : "no encontrado"));
    }
}
