package dispositivos;

public abstract class Sensor extends DispositivoIndustrial{
    private final String unidad;
    private double valorActual;

    public Sensor(String id, String unidad){
        super(id);
        if (unidad == null || unidad.trim().isEmpty()){
            throw new IllegalArgumentException("La unidad del sensor no puede estar vacia");
        }
        this.unidad = unidad.trim();
        this.valorActual = 0.0;
        setEstado(true); //simpre tiene que estar midiendo nuestro sensor
    }

    public String getUnidad(){
        return unidad;
    }

    public double getValorActual(){
        return valorActual;
    }

    public void recibir(double valor){ //cada sensor va a definir las lecturas que le son posibles fisicamente
        validarLectura(valor);
        this.valorActual = valor;
    }

    protected abstract void validarLectura(double valor); //cada sensor va a definir su propio rango, un ejemplo podria ser que no pueda pasar del 100%, o que no mas de ciertos grados y asi
    
    public abstract boolean hayAlerta();

    @Override 
    public String evaluarEstado(){
        return getId() + " mide " + valorActual + " " + unidad + (hayAlerta() ? " ALERTA" : "normal");
    }
}
