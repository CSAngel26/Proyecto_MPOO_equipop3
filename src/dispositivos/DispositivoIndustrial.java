package dispositivos;

public abstract class DispositivoIndustrial{
    private final String id;
    private boolean estado; // true = activo

    public DispositivoIndustrial(String id){
        if(id == null || id.trim().isEmpty()){ //el .trim() quita los espacios en blanco y el .isEmpty() verifica si la cadena está vacía
            throw new IllegalArgumentException("El id del dispositivo no puede estar vacio");
        }
        this.id = id.trim();
        this.estado = false; //por defecto esta apagado
    }

    public String getId() {
        return id;
    }

    public boolean isEstado() {
        return estado;
    }

    protected void setEstado(boolean estado) {
        this.estado = estado;
    }

    public abstract String evaluarEstado();

    @Override 
    public String toString(){
        return id + " [" + (estado ? "ON" : "OFF") + "]";
    }
}
