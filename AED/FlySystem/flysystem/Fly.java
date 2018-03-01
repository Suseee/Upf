package flysystem;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
import flysystem.Date;

import java.util.Scanner;


/**
 *
 * @author Suse Ribeiro
 */
public class Fly {

    private int numero;

    private int id_aviao;

    private String aeroporto_chegada;

    private String aeroporto_partida;

    private String custo;

    private Date data_voo;





    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAeroporto_chegada() {
        return aeroporto_chegada;
    }

    public void setAeroporto_chegada(String aeroporto_chegada) {
        this.aeroporto_chegada = aeroporto_chegada;
    }

    public String getAeroporto_partida() {
        return aeroporto_partida;
    }

    public void setAeroporto_partida(String aeroporto_partida) {
        this.aeroporto_partida = aeroporto_partida;
    }

    public String getCusto() {
        return custo;
    }

    public void setCusto(String custo) {
        this.custo = custo;
    }

    public Date getData_voo() {
        return data_voo;
    }

    public void setData_voo(Date data_voo) {
        this.data_voo = data_voo;
    }

    public int getId_aviao() {
        return id_aviao;
    }

    public void setId_aviao(int id_aviao) {
        this.id_aviao = id_aviao;
    }


    public Fly(int numero,int id_aviao,String aeroporto_chegada, String aeroporto_partida, String custo, Date data_voo) {
        this.numero = numero;
        this.id_aviao = id_aviao;
        this.aeroporto_chegada = aeroporto_chegada;
        this.aeroporto_partida = aeroporto_partida;
        this.custo = custo;
        this.data_voo = data_voo;


    }

    public static void loadFromFileFlyST(RedBlackBST<Integer, Fly> flyST, String path) {
        In in = new In(path);
        while (!in.isEmpty()) {
            String[] texto = in.readLine().split(";");

            int numero = Integer.parseInt(texto[0]);
            int id_avisao =Integer.parseInt(texto[1]);
            String aeroporto_partida = texto[2];
            String aeroporto_chegada = texto[3];
            String custo = texto[4];

            String[] data = texto[5].split("-");
            Date data_voo = new Date(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]));

            Fly f = new Fly(numero,id_avisao,aeroporto_partida,aeroporto_chegada,custo,data_voo);
            flyST.put(numero, f);
        }
    }

    public static void printFlys(RedBlackBST<Integer, Fly> flyST) {
        System.out.println();
        for (int numero : flyST.keys()) {
            Fly f = (Fly) flyST.get(numero);
            StdOut.println("Numero: " + f.numero +"\nId Aviao:"+f.id_aviao +"\nAeroporto Partida: " + f.getAeroporto_partida() + "\nAeroporto Chegada: " + f.getAeroporto_chegada() + "\nCusto: " + f.getCusto() + "\nData: " + f.data_voo.getSecound() +"s "+f.data_voo.getMinute()+"m "+f.data_voo.getHour()+"h "+f.data_voo.getDay()+"d "+f.data_voo.getMonth()+"m "+f.data_voo.getYear()+"y "+"\n\n");
        }
    }

    public static void addFlys(RedBlackBST<Integer, Fly> flyST) {
        int numero, p_dia, p_mes, p_ano, p_minuto, p_hora, p_segundo,id_aviao;
        String aeroporto_chegada, aeroporto_partida, custo,id_aviao_aux;

        Scanner in = new Scanner(System.in);
        if(flyST.isEmpty()){
            numero=1;
        }
        else{
            numero = flyST.max() + 1;
        }

        System.out.print("Id Aviao: ");
        id_aviao_aux = in.nextLine();

        System.out.print("Aeroporto Partida: ");
        aeroporto_partida = in.nextLine();

        System.out.print("Aeroporto Chegada: ");
        aeroporto_chegada = in.nextLine();


        System.out.print("Custo voo: ");
        custo = in.nextLine();

        System.out.print("Segundo Partida voo: ");
        p_segundo = in.nextInt();

        System.out.print("Minuto Partida voo: ");
        p_minuto = in.nextInt();

        System.out.print("Hora Partida voo: ");
        p_hora = in.nextInt();

        System.out.print("Dia Partida voo: ");
        p_dia = in.nextInt();

        System.out.print("Mes Partida voo: ");
        p_mes = in.nextInt();

        System.out.print("Ano Partida voo: ");
        p_ano = in.nextInt();

        id_aviao=Integer.parseInt(id_aviao_aux);
        Date data_voo = new Date( p_segundo,p_minuto, p_hora,p_dia, p_mes, p_ano);


        Fly f = new Fly(numero,id_aviao,aeroporto_chegada, aeroporto_partida, custo, data_voo);
        flyST.put(numero, f);
    }

    public static void saveFly(RedBlackBST<Integer, Fly> flyST, String path) {
        Out o = new Out(path);
        for (int fly : flyST.keys()) {
            Fly f = (Fly) flyST.get(fly);
            o.println(f.getNumero() + ";"+f.getId_aviao()+ ";" + f.getAeroporto_partida() + ";" + f.getAeroporto_chegada() + ";" + f.getCusto() + ";" + f.getData_voo());
        }
    }


}
