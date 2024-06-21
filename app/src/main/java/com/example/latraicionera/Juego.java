package com.example.latraicionera;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;

public class Juego {
    //Funcion para empezar juego
    Juego() {
        empezarJuego();
    }

    //Clase que define carta
    private class Carta {
        String valor;
        String tipo;
        Carta(String valor, String tipo){
            this.valor = valor;
            this.tipo = tipo;
        }

        //pilla nombre del archivo y lo devuelve a un formato estandar
        public String nombreString() {
            return valor + "-" + tipo ;
        }

        //lee valor de la carta y devuelve el numero
        public int getValor(){
            switch (valor) {
                case "J":
                case "Q":
                case "K":
                    return 10;
                case "A":
                    return 11;
                default:
                    return Integer.parseInt(valor);
            }
        }

        //comprueba si la carta es as
        public boolean isAs() {
            return Objects.equals(valor, "A");
        }
    }

    //Define variables
    ArrayList<Carta> mazo;
    Random random = new Random();

    Carta laMentirosa;
    ArrayList<Carta> manoCrupier;
    int sumaCrupier;
    int asesCrupier;

    ArrayList<Carta> manoJugador;
    int sumaJugador;
    int asesJugador;

    //Es una funcion para iniciar el juego
    public void empezarJuego() {
        //siempre son las primeras acciones
        iniciarMazo();
        barajarMazo();

        //inicializa la mano del crupier
        manoCrupier = new ArrayList<Carta>();
        sumaCrupier = 0;
        asesCrupier = 0;

        //se ocupa de la carta oculta del crupier
        laMentirosa = mazo.remove(mazo.size()- 1);
        sumaCrupier = sumaCrupier + laMentirosa.getValor();
        asesCrupier = laMentirosa.isAs() ? 1 : 0;

        //se ocupa de la carta revelada
        Carta carta = mazo.remove(mazo.size()-1);
        sumaCrupier = sumaCrupier + carta.getValor();
        asesCrupier = asesCrupier + (carta.isAs() ? 1 : 0);
        manoCrupier.add(carta);

        //inicializa la mano del jugador
        manoJugador = new ArrayList<Carta>();
        sumaJugador= 0;
        asesJugador = 0;

        for (int i = 0; i<2;i++){
            carta = mazo.remove(mazo.size()-1);
            sumaJugador = sumaJugador + carta.getValor();
            asesJugador = asesJugador + (carta.isAs() ? 1 : 0);
            manoJugador.add(carta);
        }
    }

    //Monta el mazo inicial
    public void iniciarMazo () {
        mazo = new ArrayList<Carta>();
        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] tipos = {"CORAZON", "DIAMANTE", "PICAS", "TREBOLES"};

        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 13; k++) {
                Carta carta = new Carta(valores[k], tipos[i]);
                mazo.add(carta);
            }
        }
    }

    //Coje dos posiciones random y las intercambia
    public void barajarMazo () {
        for (int i = 0; i < mazo.size(); i++){
            int nRandom = random.nextInt(mazo.size());
            Carta carta1 = mazo.get(i);
            Carta carta2 = mazo.get(nRandom);
            mazo.set(i,carta2);
            mazo.set(nRandom,carta1);
        }
    }

    //Pide otra carta
    public void dale () {
        Carta carta = mazo.remove(mazo.size() -1) ;
        sumaJugador = sumaJugador + carta.getValor();
        asesJugador = asesJugador + (carta.isAs() ? 1 : 0);
        manoJugador.add(carta);

    }

    public int reducirAsJugador() {
        while (sumaJugador > 21 && asesJugador >0 ) {
            sumaJugador = sumaJugador - 10;
            asesJugador = asesJugador - 1;
        }
        return sumaJugador;
    }
    public int reducirAsCrupier() {
        while (sumaCrupier > 21 && asesCrupier >0 ) {
            sumaCrupier = sumaCrupier - 10;
            asesCrupier = asesCrupier - 1;
        }
        return sumaCrupier;
    }
    public void comprobarJugador (){
        if (reducirAsJugador() > 21) {
            //hacer que no se puede darle
            System.out.print("no puedes darle");
        }
    }
    public void comprobarCrupier (){
        if (sumaCrupier > 17) {
            Carta carta = mazo.remove(mazo.size()-1);
            sumaCrupier = carta.getValor();
            asesCrupier = asesCrupier + (carta.isAs()? 1 : 0);
            manoCrupier.add(carta);

            System.out.print("crupier para");
        }
    }
    public void comprobarVictoria () {
        if (sumaJugador > 21  || sumaJugador<sumaCrupier){
            System.out.print("has perdido");
        }
        else if (sumaCrupier>21 || sumaJugador>sumaCrupier){
            System.out.print("has ganado");
        }
        else if (sumaCrupier == sumaJugador);{
            System.out.print("has empatado");
        }

    }
}
