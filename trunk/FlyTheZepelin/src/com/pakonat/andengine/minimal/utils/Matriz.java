package com.pakonat.andengine.minimal.utils;

import java.util.Random;

public class Matriz {

    

    private static final int altoPantalla=15;

    private static final int dif=7;//Hueco o arco de dificultad

    private static final int mindif=4;//Minimo hueco para pasar

    private static final int randomizeSCN=2;//Añado un poco mas a los bordes para que sea mas aleatorio

    private static final int tamBuffer=55;  

    private int[][] data;

    private Random r;



    public Matriz(){

          r=new Random(System.currentTimeMillis());

          data=new int[tamBuffer][2];

          data[0]=new int[]{0,altoPantalla};

          for(int i=1;i<tamBuffer;i++)

                data[i]=nuevaFila(data[i-1]);

   

    }

   

    private int[] nuevaFila(int[] anterior) {

          int[] result;          

          int maxPrimerValor=altoPantalla-dif;

         

          int primerValor=Math.abs(r.nextInt())%maxPrimerValor;

          int segundoValor=primerValor+dif;

          result=new int[]{primerValor,segundoValor};

         

          //Ahora compruebo que hay hueco suficiente, sino regenero

          while(  Math.min(anterior[1], result[1]) - Math.max(anterior[0], result[0])< mindif ){

                primerValor=Math.abs(r.nextInt())%maxPrimerValor;

                segundoValor=primerValor+dif;

                result=new int[]{primerValor,segundoValor};

          }                

         

          //Para hacerlo mas randomize le añado uno por arriba o abajo segun un random

          int masAleatorio=Math.abs(r.nextInt())%3;           

          if(masAleatorio==1){

                for(int j=0;j<randomizeSCN || result[0]==0;j++)

                      result[0]--;

          }else if(masAleatorio==2){

                for(int j=0;j<randomizeSCN || result[1]==altoPantalla;j++)

                      result[1]+=3;

          }

         

          return result;

    }



    public static void main(String args[]){

          Matriz m=new Matriz();

          System.out.println(m.toString());

          System.out.println("_____");

    }

   

    public String toString(){

          String result="";

         

          for(int par[]:data){              

                int inicio=par[0];

                int fin=par[1];

                result+="\n"+inicio+","+fin+"\t|";

                for(int i=0;i<altoPantalla;i++){

                      if(i<inicio){

                           result+="X";

                      }else if(i>=inicio && i<fin){

                           result+="·";

                      }else{

                           result+="X";

                      }

                }

                result+="|";

          }

          return result;

    }

}