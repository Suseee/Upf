package flysystem;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;


import java.util.Scanner;

public class Airplane {

    private int id_aviao;

    private String modelo;

    private String nome;

    private String companhia_aerea;

    private int velocidade_cruzeiro;

    private int altitude_cruzeiro;

    private int distancia_maxima;

    private String cod_aeroporto;

    private int capacidade_passageiros;

    private int capacidade_deposito;


    public int getId_aviao() {
        return id_aviao;
    }

    public void setId_aviao(int id_aviao) {
        this.id_aviao = id_aviao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCompanhia_aerea() {
        return companhia_aerea;
    }

    public void setCompanhia_aerea(String companhia_aerea) {
        this.companhia_aerea = companhia_aerea;
    }

    public int getVelocidade_cruzeiro() {
        return velocidade_cruzeiro;
    }

    public void setVelocidade_cruzeiro(int velocidade_cruzeiro) {
        this.velocidade_cruzeiro = velocidade_cruzeiro;
    }

    public int getAltitude_cruzeiro() {
        return altitude_cruzeiro;
    }

    public void setAltitude_cruzeiro(int altitude_cruzeiro) {
        this.altitude_cruzeiro = altitude_cruzeiro;
    }

    public int getDistancia_maxima() {
        return distancia_maxima;
    }

    public void setDistancia_maxima(int distancia_maxima) {
        this.distancia_maxima = distancia_maxima;
    }

    public String getCod_aeroporto() {
        return cod_aeroporto;
    }

    public void setCod_aeroporto(String cod_aeroporto) {
        this.cod_aeroporto = cod_aeroporto;
    }

    public int getCapacidade_passageiros() {
        return capacidade_passageiros;
    }

    public void setCapacidade_passageiros(int capacidade_passageiros) {
        this.capacidade_passageiros = capacidade_passageiros;
    }

    public int getCapacidade_deposito() {
        return capacidade_deposito;
    }

    public void setCapacidade_deposito(int capacidade_deposito) {
        this.capacidade_deposito = capacidade_deposito;
    }


    public Airplane(int id_aviao, String modelo, String nome, String companhia_aerea, int velocidade_cruzeiro, int altitude_cruzeiro, int distancia_maxima, String cod_aeroporto, int capacidade_passageiros, int capacidade_deposito) {
        this.id_aviao = id_aviao;
        this.modelo = modelo;
        this.nome = nome;
        this.companhia_aerea = companhia_aerea;
        this.velocidade_cruzeiro = velocidade_cruzeiro;
        this.altitude_cruzeiro = altitude_cruzeiro;
        this.distancia_maxima = distancia_maxima;
        this.cod_aeroporto = cod_aeroporto;
        this.capacidade_passageiros = capacidade_passageiros;
        this.capacidade_deposito = capacidade_deposito;
    }

    public static void loadFromFileAirplanetST(SeparateChainingHashST<String, Airport> airportST, String path) {
        In in = new In(path);
        while (!in.isEmpty()) {
            String[] texto = in.readLine().split(";");

            int id_aviao = Integer.parseInt(texto[0]);
            String modelo = texto[1];
            String nome = texto[2];
            String companhia_aerea = texto[3];
            int velocidade_cruzeiro = Integer.parseInt(texto[4]);
            int altitude_cruzeiro = Integer.parseInt(texto[5]);
            int distancia_maxima = Integer.parseInt(texto[6]);
            String cod_aeroporto = texto[7];
            int capacidade_passageiros = Integer.parseInt(texto[8]);
            int capacidade_deposito = Integer.parseInt(texto[9]);
            Airplane a = new Airplane(id_aviao, modelo, nome, companhia_aerea, velocidade_cruzeiro, altitude_cruzeiro, distancia_maxima, cod_aeroporto, capacidade_passageiros, capacidade_deposito);
            airportST.get(cod_aeroporto).getAirportAirplaneST().put(id_aviao, a);
        }
    }

    public static void printAirplane(SeparateChainingHashST<String, Airport> airportST) {
        System.out.println();
        int j = 0;
        System.out.println("Airplanes:\n");
        for (String key_aeroporto : airportST.keys()) {
            if(!airportST.get(key_aeroporto).getAirportAirplaneST().isEmpty()){
                System.out.println("\nAeroporto "+ key_aeroporto+"\n");
            }
            for (int key_aviao : airportST.get(key_aeroporto).getAirportAirplaneST().inOrder()) {
                System.out.println("ID:" + airportST.get(key_aeroporto).getAirportAirplaneST().get(key_aviao).getId_aviao() + " \nNome:" + airportST.get(key_aeroporto).getAirportAirplaneST().get(key_aviao).getNome() + " \nCodigo Aeroporto:" + airportST.get(key_aeroporto).getAirportAirplaneST().get(key_aviao).getCod_aeroporto() + "\n");
                j++;
            }

        }
        System.out.println("Existem " + j + " aeroportos");
    }

    /**
     * Metodo add airplanes
     *
     * @param airportST
     */

    public static  void client_addAirplane(SeparateChainingHashST<String, Airport> airportST){
        String velocidade_cruzeiro, distancia_maxima, capacidade_passageiros, capacidade_deposito, altitude_cruzeiro, modelo, nome, companhia_aerea, cod_aeroporto;
        int id, aux = 0, max = 0;

        Scanner in = new Scanner(System.in);


        for (String key_aeroporto : airportST.keys()) {
            if (airportST.get(key_aeroporto).getAirportAirplaneST().isEmpty()) {
                id = 0;
            } else {
                aux = airportST.get(key_aeroporto).getAirportAirplaneST().max();
                if (aux > max) {
                    max = aux;
                }
            }
        }

        id = max + 1;


        System.out.print("\nModelo: ");
        modelo = in.nextLine();

        System.out.print("Nome: ");
        nome = in.nextLine();

        System.out.print("Companhia aerea: ");
        companhia_aerea = in.nextLine();

        System.out.print("Velocidade Cruzeiro: ");
        velocidade_cruzeiro = in.nextLine();

        System.out.print("Altitude Cruzeiro: ");
        altitude_cruzeiro = in.nextLine();

        System.out.print("Distancia Maxima: ");
        distancia_maxima = in.nextLine();

        System.out.print("Codigo Aeroporto: ");
        cod_aeroporto = in.nextLine();
        while (!airportExist(airportST, cod_aeroporto)) {
            System.out.println("Esse não existe. Insira novamente.");
            System.out.print("Codigo Aeroporto: ");
            cod_aeroporto = in.nextLine();
        }


        System.out.print("Capacidade Passageiros: ");
        capacidade_passageiros = in.nextLine();

        System.out.print("Capacidade Deposito: ");
        capacidade_deposito = in.nextLine();

        Airplane a = new Airplane(id, modelo, nome, companhia_aerea, Integer.parseInt(velocidade_cruzeiro), Integer.parseInt(altitude_cruzeiro), Integer.parseInt(distancia_maxima), cod_aeroporto, Integer.parseInt(capacidade_passageiros), Integer.parseInt(capacidade_deposito));
        addAirplane(airportST,a,cod_aeroporto,id);
    }


    public static void addAirplane(SeparateChainingHashST<String, Airport> airportST,Airplane a,String cod_aeroporto,int id) {

        airportST.get(cod_aeroporto).getAirportAirplaneST().put(id, a);

    }

    public static boolean airportExist(SeparateChainingHashST<String, Airport> airportST, String cod_airport) {

        for (String key_aeroporto : airportST.keys()) {
            if(key_aeroporto.compareTo(cod_airport)==0){
                return true;
            }

        }


        return false;
    }

    /**
     * @param airportST
     * @param path
     */

    public static void saveAirplane(SeparateChainingHashST<String, Airport> airportST, String path) {
        Out o = new Out(path);
        for (String key_aeroporto : airportST.keys()) {
            for (int key_aviao : airportST.get(key_aeroporto).getAirportAirplaneST().keys()) {
                Airplane a = (Airplane) airportST.get(key_aeroporto).getAirportAirplaneST().get(key_aviao);
                o.println(a.getId_aviao() + ";" + a.getModelo() + ";" + a.getNome() + ";" + a.getCompanhia_aerea()
                        + ";" + a.getVelocidade_cruzeiro() + ";" + a.getAltitude_cruzeiro() + ";" + a.getDistancia_maxima() + ";" + a.getCod_aeroporto() + ";" + a.getCapacidade_passageiros() + ";" + a.getCapacidade_deposito());
            }

        }
    }


    public static void client_editAirplane(SeparateChainingHashST<String, Airport> airportST){
        String aux, velocidade_cruzeiro, distancia_maxima, capacidade_passageiros, capacidade_deposito, altitude_cruzeiro, modelo, nome, companhia_aerea, cod_aeroporto;

        int id;

        Scanner in = new Scanner(System.in);

        System.out.println("Introduza o id do aviao que quer editar:");
        aux = in.nextLine();
        id = Integer.parseInt(aux);

        System.out.print("\nModelo: ");
        modelo = in.nextLine();


        System.out.print("\nNome: ");
        nome = in.nextLine();


        System.out.print("\nCompanhia aerea: ");
        companhia_aerea = in.nextLine();


        System.out.print("\nVelocidade Cruzeiro: ");
        velocidade_cruzeiro = in.nextLine();


        System.out.print("\nAltitude Cruzeiro: ");
        altitude_cruzeiro = in.nextLine();


        System.out.print("\nDistancia Maxima: ");
        distancia_maxima = in.nextLine();


        System.out.print("\nCodigo Aeroporto: ");
        cod_aeroporto = in.nextLine();


        System.out.print("\nCapacidade Passageiros: ");
        capacidade_passageiros = in.nextLine();


        System.out.print("\nCapacidade Deposito: ");
        capacidade_deposito = in.nextLine();

        Airplane a = new Airplane(id, modelo, nome, companhia_aerea, Integer.parseInt(velocidade_cruzeiro), Integer.parseInt(altitude_cruzeiro), Integer.parseInt(distancia_maxima), cod_aeroporto, Integer.parseInt(capacidade_passageiros), Integer.parseInt(capacidade_deposito));
        editAirplane(airportST,cod_aeroporto,id,a);
    }

    public static void editAirplane(SeparateChainingHashST<String, Airport> airportST,String cod_aeroporto,int id,Airplane a) {

        deleteAirplane(airportST,id);
        airportST.get(cod_aeroporto).getAirportAirplaneST().put(id, a);

    }

    public static void client_deleteAirplane(SeparateChainingHashST<String, Airport> airportST){
        Scanner in = new Scanner(System.in);

        System.out.println("Introduza o id do aviao que quer editar:");
        int id = in.nextInt();
        deleteAirplane(airportST,id);
    }

    public static void deleteAirplane(SeparateChainingHashST<String, Airport> airportST,int id) {


        for (String key_aeroporto : airportST.keys()) {
            for (int key_aviao : airportST.get(key_aeroporto).getAirportAirplaneST().keys()) {
                if (airportST.get(key_aeroporto).getAirportAirplaneST().get(key_aviao).id_aviao == id) {
                    airportST.get(key_aeroporto).getAirportAirplaneST().delete(key_aviao);
                }
            }
        }

    }
}
