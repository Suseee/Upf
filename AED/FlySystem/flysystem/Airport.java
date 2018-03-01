package flysystem;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;
import java.util.Scanner;

/**
 *
 * @author Suse Ribeiro
 */
public class Airport {

    private String nome;

    private String codigo;

    private String cidade;

    private String pais;

    private String continente;

    private float classificacao;
    
    private RedBlackBST<Integer, Airplane> airportAirplaneST = new RedBlackBST<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public float getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(float classificacao) {
        this.classificacao = classificacao;
    }
    
    
    /**
     * @return the airportAirplaneST
     */
    public RedBlackBST<Integer, Airplane> getAirportAirplaneST() {
        return airportAirplaneST;
    }

    /**
     * @param airportAirplaneST the airportAirplaneST to set
     */
    public void setAirportAirplaneST(RedBlackBST<Integer, Airplane> airportAirplaneST) {
        this.airportAirplaneST = airportAirplaneST;
    }

    public Airport(String nome, String codigo, String cidade, String pais, String continente, float classificacao) {
        this.nome = nome;
        this.codigo = codigo;
        this.cidade = cidade;
        this.pais = pais;
        this.continente = continente;
        this.classificacao = classificacao;
    }

    public static void loadFromFileAirportST(SeparateChainingHashST<String, Airport> airportST, String path) {
        In in = new In(path);
        while (!in.isEmpty()) {
            String[] texto = in.readLine().split(";");

            String nome = texto[0];
            String codigo = texto[1];
            String cidade = texto[2];
            String pais = texto[3];
            String continente = texto[4];
            float classificacao = Float.parseFloat(texto[5]);

            Airport a = new Airport(nome, codigo, cidade, pais, continente, classificacao);
            airportST.put(codigo, a);

        }
    }

    public static void printAirports(SeparateChainingHashST<String, Airport> airportST) {
        System.out.println();
        for (String codigo : airportST.keys()) {
            Airport a = (Airport) airportST.get(codigo);
            StdOut.println("Nome: " + a.getNome() + "; Codigo: " + a.getCodigo() + "; Cidade: " + a.getCidade() + "; Pais: " + a.getPais() + "; Continente: " + a.getContinente() + "; Classificacao: " + a.classificacao);
        }
    }
    public static void client_addAirport(SeparateChainingHashST<String, Airport> airportST){
        String nome, codigo, cidade, pais, continente, classificacao;

        Scanner in = new Scanner(System.in);

        System.out.print("\nNome: ");
        nome = in.nextLine();
        while (airportExist(airportST, nome)) {
            System.out.println("Este aeroporto ja existe. Insira novamente..");
            System.out.print("\nNome: ");
            nome = in.nextLine();
        }

        System.out.print("Codigo: ");
        codigo = in.nextLine();

        System.out.print("Cidade: ");
        cidade = in.nextLine();

        System.out.print("Pais: ");
        pais = in.nextLine();

        System.out.print("Continente: ");
        continente = in.nextLine();

        System.out.print("Classificacao: ");
        classificacao = in.nextLine();

        Airport a1 = new Airport(nome, codigo, cidade, pais, continente, Float.parseFloat(classificacao));
        addAirport(airportST,a1,codigo);
    }

    public static void addAirport(SeparateChainingHashST<String, Airport> airportST,Airport a1,String codigo) {

        airportST.put(codigo, a1);
    }

    public static boolean airportExist(SeparateChainingHashST<String, Airport> airportST, String airport) {
        for (String nome : airportST.keys()) {
            Airport a = (Airport) airportST.get(nome);
            if (a.getNome().equals(airport)) {
                return true;
            }
        }
        return false;
    }

    public static void saveAirport(SeparateChainingHashST<String, Airport> airportST, String path) {
        Out o = new Out(path);
        for (String codigo : airportST.keys()) {
            Airport a = (Airport) airportST.get(codigo);
            o.println(a.getNome() + ";" + a.getCodigo() + ";" + a.getCidade() + ";" + a.getPais() + ";" + a.getContinente() + ";" + a.classificacao + ";");
        }
    }

    public static void client_editAirport(SeparateChainingHashST<String, Airport> airportST){
        String nome, codigo, cidade, pais, continente,aux;
        Float classificacao;
        Scanner in = new Scanner(System.in);

        System.out.println("\nIntroduza o Codigo do aeroporto que quer editar:");
        codigo = in.nextLine();
        System.out.print("Nome: ");
        nome = in.nextLine();
        System.out.print("Cidade: ");
        cidade = in.nextLine();
        System.out.print("Pais: ");
        pais = in.nextLine();
        System.out.print("Continente: ");
        continente = in.nextLine();
        System.out.print("Classificacao: ");
        aux = in.nextLine();
        classificacao=Float.parseFloat(aux);

        Airport a = new Airport(nome, codigo, cidade, pais, continente, classificacao);
        editAirport(airportST,a,codigo);

    }
    public static void editAirport(SeparateChainingHashST<String, Airport> airportST,Airport a,String codigo) {


        airportST.get(codigo).codigo = a.codigo;

        airportST.get(codigo).nome = a.nome;


        airportST.get(codigo).cidade = a.cidade;


        airportST.get(codigo).pais = a.pais;


        airportST.get(codigo).continente = a.continente;


        airportST.get(codigo).classificacao = a.classificacao;

    }

    public static void client_deleteAirport(SeparateChainingHashST<String, Airport> airportST){
        printAirports(airportST);
        String codigo_airport;
        Scanner in = new Scanner(System.in);
        System.out.print("\n\nInsira o Codigo do Aeroporto a remover : \n");
        codigo_airport = in.nextLine();
        deleteAirport(airportST,codigo_airport);
    }

    public static void deleteAirport(SeparateChainingHashST<String, Airport> airportST,String codigo_airport) {

        for (String codigo : airportST.keys()) {
           if(codigo.compareTo(codigo_airport)==0){
               airportST.delete(codigo);
           }
        }



    }
    
//    public static void validarAirport(RedBlackBST<Integer, Airplane> airplaneST, SeparateChainingHashST<Integer, Airport> airportST) {
//
//        System.out.println("Os seguintes aeroportos estao validados:");
//        for (int a : airportST.keys()) {
//            Airport air = airportST.get(a);
//            RedBlackBST<String, Airport> airplane = air.get();
//            for (String cod : airplane.keys()) {
//                Airplane id = airplane.get(cod);
//                if (!airplaneST.contains(cod)) {
//                    System.out.println("Aeroporto não validado.");
//                } else {
//
//                    System.out.println("  está Validado");
//                }
//
//            }
//        }
//    }


}
