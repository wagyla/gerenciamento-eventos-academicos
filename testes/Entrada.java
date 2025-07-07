package pessoas;

import java.util.Scanner;

public class Entrada {
    private static Entrada entrada;

    public static Entrada getInstance(){
        if(entrada == null){
            entrada = new Entrada();
        }

        return entrada;
    }

    private Scanner scaner;
    private Entrada(){
        this.scaner = new Scanner(System.in);
    }

    public String lerString(){
        return this.scaner.nextLine();
    }
}
