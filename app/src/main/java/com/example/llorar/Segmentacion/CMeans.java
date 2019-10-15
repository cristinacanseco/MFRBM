package com.example.llorar.Segmentacion;

import java.util.ArrayList;
import java.util.Random;

public class CMeans {
    // conjunto de instancias
    private ArrayList<Patron> instancias;
    // numero de clusters
    private int c;
    // centroides
    private ArrayList<Patron[]> centroides;
    int[] contadores;


    public CMeans(ArrayList<Patron> instancias, int c) {
        this.instancias = instancias;
        this.centroides = new ArrayList<>();
        this.c = c;
    }

    public void clasifica (){
        // generar mis centroides iniciales aleatorios
        Random ran = new Random();
        Patron[] centroides = new Patron[c];
        for (int x=0; x < this.c;x++){
            int pos = ran.nextInt(this.instancias.size());
            centroides[x] = new Patron(this.instancias.get(pos).getVectorCa().clone(),"Centroide "+x, 0);
        }
        // agregamos a la coleccion de centroides los centroides iniciales
        this.getCentroides().add(centroides);
        // etiquetar por primera ocasión (clasificar por primera ocasión)
        etiquetar(centroides);
        // generar un proceso iterativo
        // que modifique o ajuste los centroides
        int contador = 0;

        do {
            // recalcular centroides
            // necesitamos donde acumular
            Patron[] centroidesNuevos = new Patron[c];
            contadores = new int[c];
            inicializarNuevosCentroides(centroidesNuevos);
            // acumulamos(recorrer todas las instancias)
            for (Patron instancia: this.instancias){
                String nombreCluster = instancia.getClase_resultado();
                forCentroides: for (int x=0; x < centroidesNuevos.length;x++){
                    if (centroidesNuevos[x].getClase_resultado().equals(nombreCluster)){
                        centroidesNuevos[x].setVectorCa(sumaVectores(centroidesNuevos[x].getVectorCa(),instancia.getVectorCa()));
                        contadores[x]++;
                        break forCentroides;
                    }
                }
            }
            // agregar los centroides a la coleccion
            this.getCentroides().add(centroidesNuevos);
            // dividimos
            dividirUltimosCentroides(contadores);

            // re etiquetar
            etiquetar(this.getCentroides().get(this.getCentroides().size()-1));

            contador++;
        }while (!verificaCentroides()&&contador<500);


    }

    private void etiquetar (Patron[] centroides){
        // recorrer las instancias y etiquetar
        // cada una de ellas en base a distancias
        for (Patron patron: this.instancias){
            double menor = calculaDistanciaEuclidiana(patron,centroides[0]);
            patron.setClase_resultado(centroides[0].getClase());
            for (int x=1; x < this.c; x++){
                // calculamos distancias
                double dist = calculaDistanciaEuclidiana(patron,centroides[x]);
                if (dist< menor){
                    menor = dist;
                    patron.setClase_resultado(centroides[x].getClase());
                }
            }

        }

    }

    private boolean verificaCentroides() {
        // verificar si los centroides nuevos
        // son iguales a los anteriores
        int numCentroides = this.getCentroides().size();
        Patron[] ultimo = this.getCentroides().get(numCentroides-1);
        Patron[] penultimo = this.getCentroides().get(numCentroides-2);
        for (int x=0; x < ultimo.length;x++){
            if (!ultimo[x].equals(penultimo[x]))
                return false;
        }
        System.out.println("Convergen los centroides!");
        return true;
    }


    private void inicializarNuevosCentroides(Patron[] centroidesNuevos) {
        // recorro el arreglo
        for (int x=0; x < centroidesNuevos.length;x++){
            centroidesNuevos[x] = new Patron(new double[this.instancias.get(0).getVectorCa().length],this.getCentroides().get(this.getCentroides().size()-1)[x].getClase_resultado(), 0);
        }
    }

    private double[] sumaVectores(double[] vector, double[] vector0) {
        double aux[] = new double[vector.length];
        for (int x=0; x < aux.length;x++)
            aux[x] = vector[x]+vector0[x];

        return aux;
    }

    private void dividirUltimosCentroides(int[] contadores) {
        Patron[] aux = this.getCentroides().get(this.getCentroides().size()-1);

        for (int x=0; x < aux.length;x++){
            double[] vector = aux[x].getVectorCa();
            for (int y=0;y<vector.length;y++){
                vector[y]/=contadores[x];
            }
        }

    }

    /**
     * @return the centroides
     */
    public ArrayList<Patron[]> getCentroides() {
        return centroides;
    }

    public static double calculaDistanciaEuclidiana(Patron p1,Patron p2){
        double acum = 0;

        for (int x=0;x < p1.getVectorCa().length;x++){
            acum+=Math.pow((p2.getVectorCa()[x]-p1.getVectorCa()[x]),2);

        }
        return Math.sqrt(acum);
    }
}
