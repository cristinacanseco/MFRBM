package com.example.llorar.Segmentacion;

public class Patron {
    private String clase;
    // a la que clasificado
    private String clase_resultado;
    // vector de caracteristicas
    private double[] vectorCa;
    private int nada;
    private int x;
    private int y;

    // constructor por defecto
    // n = numero de caracteristicas
    public Patron (int n){
        this.vectorCa = new double[n];
        this.clase = "Desconocida";
        this.clase_resultado = "Desconocida";
        this.x = -1;
        this.y = -1;
    }
    public Patron (double [] vector, String clase){
        this.vectorCa = vector.clone();
        this.clase = clase;
        this.clase_resultado = "Desconocida";
        this.x = -1;
        this.y = -1;
    }

    public Patron (double [] vector, String clase, int nada){
        this.vectorCa = vector.clone();
        this.clase = clase;
        this.clase_resultado = clase;
        this.nada = nada;
    }

    public Patron (Patron patron){
        this.clase = patron.getClase();
        this.clase_resultado = patron.getClase_resultado();
        this.vectorCa = patron.getVectorCa().clone();

    }

    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the clase_resultado
     */
    public String getClase_resultado() {
        return clase_resultado;
    }

    /**
     * @param clase_resultado the clase_resultado to set
     */
    public void setClase_resultado(String clase_resultado) {
        this.clase_resultado = clase_resultado;
    }

    /**
     * @return the vectorCa
     */
    public double[] getVectorCa() {
        return vectorCa;
    }

    /**
     * @param vectorCa the vectorCa to set
     */
    public void setVectorCa(double[] vectorCa) {
        this.vectorCa = vectorCa;
    }

    public double calculaDistancia(Patron patron){
        double distancia = 0;
        // recorrer el vector
        for (int x=0; x<this.vectorCa.length;x++){
            distancia+=Math.pow
                    ((this.vectorCa[x]-patron.getVectorCa()[x]),2);
        }
        return Math.sqrt(distancia);
    }

    public void trasladar(Patron aux){
        // recorrer el numero de caracteristicas
        for (int x=0; x < this.vectorCa.length;x++){
            this.vectorCa[x]-=aux.getVectorCa()[x];
        }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /*public int obtenerRGB (){
        Color aux = new Color((int)vectorCa[0],(int) vectorCa[1],(int)vectorCa[2]);
        return aux.getRGB();
    }*/
}
