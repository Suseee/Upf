package flysystem;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import java.util.Scanner;


public class Main {

    public static final String PATH_AIRPLANE = ".//data//airplanes.txt";
    public static final String PATH_AIRPORT = ".//data//airports.txt";
    public static final String PATH_FLY = ".//data//fly.txt";




    public static void main(String[] args) {

        SeparateChainingHashST<String, Airport> airportST = new SeparateChainingHashST<>();
        RedBlackBST<Integer, Fly> flyST = new RedBlackBST<>();
        main_menu(airportST,flyST);

    }

    public static void main_menu(SeparateChainingHashST<String, Airport> airportST,RedBlackBST<Integer, Fly> flyST) {


        //Loads
        Airport.loadFromFileAirportST(airportST,PATH_AIRPORT);
        Airplane.loadFromFileAirplanetST(airportST, PATH_AIRPLANE);
        //Airplane a = new Airplane(1,"asda","asda" ,"asd",10 , 10, 10, "FRA", 10, 10);
        //airportST.get("FRA").getAirportAirplaneST().put(1,a);


        //Fly.loadFromFileFlyST(flyST, PATH_FLY);

        Scanner e = new Scanner(System.in);
        System.out.println("-----Menu Principal----- ");
        System.out.println("1. Airplane");
        System.out.println("2. Airport");
        System.out.println("3. Fly");
        System.out.println("0. Sair");

        String opcao = e.nextLine();

        switch (opcao) {
            case "1":
                menuAirplane(airportST);
                break;
            case "2":
                menuAirport(airportST);
                break;
            case "3":
                menuFly(flyST);
                break;
            case "0":

                break;
            default:
                System.out.println("Opção inválida.");

        }

        Airplane.saveAirplane(airportST, PATH_AIRPLANE);
        Airport.saveAirport(airportST, PATH_AIRPORT);
        Fly.saveFly(flyST, PATH_FLY);
    }

    public static void menuAirplane(SeparateChainingHashST<String, Airport> airportST) {

        String option;
        Scanner a = new Scanner(System.in);
        System.out.println("\n\n-----Menu Airplane----- ");
        System.out.println("Escolha a opção pretendida: ");
        System.out.println("1. Listar ");
        System.out.println("2. Adicionar ");
        System.out.println("3. Editar");
        System.out.println("4. Remover ");
        option = a.nextLine();

        switch (option) {
            case "1":
                Airplane.printAirplane(airportST);
                break;
            case "2":
                Airplane.client_addAirplane(airportST);
                break;
            case "3":
                Airplane.client_editAirplane(airportST);
                break;
            case "4":
                Airplane.client_deleteAirplane(airportST);
                break;
        }
    }

    public static void menuAirport(SeparateChainingHashST<String, Airport> airportST) {

        String option;
        Scanner a = new Scanner(System.in);
        System.out.println("\n\n-----  Menu Airport  ----- ");
        System.out.println("Escolha a opção pretendida: ");
        System.out.println("1. Listar ");
        System.out.println("2. Adicionar ");
        System.out.println("3. Editar");
        System.out.println("4. Remover ");
        option = a.nextLine();

        switch (option) {
            case "1":
                Airport.printAirports(airportST);
                break;
            case "2":
                Airport.client_addAirport(airportST);
                break;
            case "3":
                Airport.client_editAirport(airportST);
                break;
            case "4":
                Airport.client_deleteAirport(airportST);
                break;
        }
    }

    public static void menuFly(RedBlackBST<Integer, Fly> flyST) {

        String option;
        Scanner a = new Scanner(System.in);
        System.out.println("\n\n-----  Menu Fly  ----- ");
        System.out.println("Escolha a opção pretendida: ");
        System.out.println("1. Listar ");
        System.out.println("2. Adicionar ");
        //System.out.println("3. Editar");
        System.out.println("4. Remover ");
        option = a.nextLine();

        switch (option) {
            case "1":
                Fly.printFlys(flyST);
                break;
            case "2":
                Fly.addFlys(flyST);
                Fly.printFlys(flyST);
                break;
            case "4":

                break;
        }
    }

}
