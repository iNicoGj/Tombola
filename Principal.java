package paquete;
import java.util.Scanner;
import java.util.Random;
/*
TÓMBOLA VER 0.1.8
Autores: Nicolás Graña, Alex Coppola, Nahuel Quintana
*/
public class Principal {
    //Se declaran las variables globales
    Scanner entrada = new Scanner(System.in);
    Random azar = new Random();
    
    /*
    Este método se encarga de inicializar el programa, dentro del mismo se 
        encuentran todos los llamamientos a los demás métodos para colaborar
        entre sí y brindar la funcionalidad requerida y adecuada correspondiente
        a la "Tómbola".
    */
    void iniciar (){
        /*
        Definimos variables que almacenan los valores obtenidos por medio de los
        diferentes metodos dentro del método iniciar para que se puedan
        reutilizar los valores dentro de los demás métodos.
        */
        double apuesta = cargaApuesta();
        int modalidad = cargaModalidad();
        boolean[] numeros = cargaNumeros(modalidad);
        int cantidadPremio = 0;
        
        /*dineroFinal es la variable que define la cantidad que se gano o perdio
            jugando los sorteos y también considera perdidas si no hubo premio 
            ya que no recupera el dinero de la apuesta.
        */
        double dineroFinal = 0;
        int cantidadSorteos = (cargaCantidadSorteos());
        
        //Utilizamos un for para que haga el trabajo de generar los diversos
        //sorteos, también le pedimos que sume a una variable llamada
        //cantidadPremio, para ir acumulando la cantidad de veces que se logró
        //un premio.
        for (int i = 0; i < cantidadSorteos; i++) {
            boolean[] arregloSorteo = cargaSorteo();
           if(hayPremio(aciertos(arregloSorteo,numeros))== true){
               cantidadPremio++;
           }else {
               //Si no hay premio, se le resta al usuario el dineroFinal ya que
               //habría perdido el dinero de la apuesta de ese sorteo
               dineroFinal -= apuesta;
           }
            System.out.println(" ");
            System.out.println("Sorteo numero " + (i+1));
            System.out.println(" ");
            imprimir(arregloSorteo);
            System.out.println(" ");
            System.out.println("La cantidad de números acertados es " + aciertos(arregloSorteo,numeros));
            System.out.println(" ");
            imprimir(numeros);
            System.out.println(" ");
            dineroFinal += premio(modalidad,aciertos(arregloSorteo,numeros),apuesta);
        } //Cuando termina el for, imprime los datos finales
        total(numeros,cantidadPremio,dineroFinal);
    }
   
    /////////////////////////////////////////
    /*  Método cargaModalidad:
     
    Este método permite al usuario elegir la modalidad deseada, de este método
    dependerá el método cargaNumeros para determinar la cantidad de números
    elegidos por el usuario.
    */
    
    int cargaModalidad(){
        System.out.println("Ingrese la modalidad que desea jugar");
        System.out.println("3 Números (3) 4 números (4) 5 números (5) "
                + "6 números (6) 7 números (7)  ");
        int modalidad = entrada.nextInt();
        /* Se utiliza un while para no permitir al usuario 
                ingresar una modalidad érronea
        */
        while (modalidad < 3 || modalidad > 7){
            System.out.println("Ingrese una modalidad válida");
            modalidad = entrada.nextInt();
        }
        return modalidad;
        
    }
    //////////////////////////////////////////////////////////
    /*  Método cargaNumeros:
    
     Este método permite al usuario elegir los números a los 
        cuales el usuario quiere apostar
    */
    boolean [] cargaNumeros(int modalidad){
        boolean [] jugar = new boolean[100];
        int numero;
        for (int i = 0; i < modalidad; i++) {
            //Se asume que no se ingresan números repetidos ni mayores a 100
            System.out.print("Ingrese número: ");
            numero = entrada.nextInt();
            jugar [numero] = true;
        }
        return jugar;
    }
    
    ////////////////////////////////////////////////////////////
    /*    Método Aciertos:
    
    Devuelve la cantidad de números acertados
    de j (jugada) sobre s(sorteos)
    */
    
    int aciertos (boolean[] j, boolean[] s){
        int aciertos = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] && j[i] == true){
                aciertos++;
            }
        }
        return aciertos;
    }
    
    /////////////////////////////////////////////////////////////
    /*   Método "cargaApuesta"
    
    Permite al usuario escribir el monto que
    desea apostar y devolver ese valor.
    */
    
    double cargaApuesta(){
        // El usuario ingresa el monto deseado para apostar
        System.out.println("Ingrese un monto que desee apostar entre 40 y 400");
        double dineroApostado = entrada.nextDouble(); 
        
        /*Se utiliza un while para avisarle al usuario que el monto ingresado 
        no está permitido y le permite escribir un nuevo monto
        */
        while (dineroApostado < 40 || dineroApostado > 400){
            System.out.println("Ingrese un monto válido");
            dineroApostado =  entrada.nextDouble();
        }
        return dineroApostado;
    }
    
    /////////////////////////////////////////////////////////////
    /*  Método cargaCantidadSorteos:
    
    Permite al usuario elegir la cantidad de sorteos
    en las que el mismo desea participar y devuelve ese valor.
    */
    int cargaCantidadSorteos(){
        System.out.println("Elija la cantidad de sorteos en los que quiere participar");
        System.out.println("Una jugada(1) Una semana (2), Un mes (3), Un año (4)");
        int cantidadSorteos = entrada.nextInt();
        /*Se utiliza un while para prevenir que el usuario elija otro valor que
            no sean los designados al repetir el proceso de entrada.
        */
        while (cantidadSorteos > 4 || cantidadSorteos < 1){
            System.out.println("Ingrese una cantidad válida");
            cantidadSorteos = entrada.nextInt();
        }
        /*Se utiliza un switch para transformar la elección del usuario
        simplificada al valor real que tendría, en este caso, una semana seria
        equivalente a 11 sorteos.
        
        Por lo tanto para que el usuario se ahorre ese trabajo de escribir el 
        número exacto optamos por simplificar a través del Switch. 
        Creemos que esto crea un input más "limpio" y menos confuso a los ojos 
        del usuario promedio.
        */
        switch (cantidadSorteos){
            case 1: cantidadSorteos = 1;
                break;
            case 2: cantidadSorteos = 11;
                break;
            case 3: cantidadSorteos = 44;
                break;
            case 4: cantidadSorteos = 528;
                break;
        }
        return cantidadSorteos;
    }
    
    //////////////////////////////////////////////////////////////
    /*  Método CargaSorteo: 
    
    Este método se encarga de generar los arreglos de los
    sorteos y asignarles los valores "true" a las casillas donde corresponda,
    donde en cada arreglo habrán 20 valores donde se considera acierto (true)
    y 80 valores donde se considera fallo (false)
    
    No requiere input del usuario así que su única funcionalidad es la de
    colaborar con los otros métodos.
    */
    boolean[] cargaSorteo (){
        boolean [] sortear = new boolean [100];
        int numeros = 0;
        int bolilla;
        while (numeros < 20){
            bolilla = azar.nextInt(100);
            if (sortear[bolilla] == false){
                sortear[bolilla] = true;
                numeros++;
            }
        }
        return sortear;
    }
    
    //////////////////////////////////////////////////////////////
    /*  Método hayPremio
    
    Permite devolver un valor booleano que nos confirma si
    hay o no premio en función del número de aciertos en una jugada.
    
    */
    
    boolean hayPremio (int aciertos){
        boolean premio = false; 
        
        if (aciertos > 2) {
            premio = true;
        }
        return premio;
    }
    
    /*  
        Para que se pueda confirmar que hay un premio, debe haber un mínimo de
        3 aciertos, por lo tanto si el número de aciertos del método "aciertos"
        devuelve un valor inferior a 3, este método devolverá falso como valor
        por defecto. En el caso contrario, indicará que hay premio y devolverá 
        true.
        */
    ////////////////////////////////////////////////////////////////
    
    /* Método Imprimir
    
    Este método se encarga de imprimir los resultados de cada sorteo o jugada
    dependiendo del arreglo que se le asigne
    */
    void imprimir(boolean[] a){
        int cantidadTrue = 0; //Cantidad de valores booleanos true en el arreglo 
        for (int i = 0; i < a.length; i++) {
                if (a[i] == true ){
                    cantidadTrue++;
                }
            }
        if (cantidadTrue == 7){
            for (int m = 0; m < a.length; m++) {
                if (a[m] == true){
                    if (m < 10) {
                        System.out.print("[0" + m + "] ");
                    } else {
                        System.out.print("[" + m + "] ");
                    }
                }
            }
        }
        if (cantidadTrue == 20){
            int bandera = 0;
            int [] arreglo = new int[20];
            for (int n = 0; n < a.length; n++) {
                if (a[n] == true){
                    arreglo[bandera] = n;
                    bandera++;
                }
            }
            for (int p = 0; p < arreglo.length; p++) {
                if (arreglo[p] < 10) {
                        System.out.print("[0" + arreglo[p] + "] ");
                    } else {
                    if (p == 9){
                        System.out.println("[" + arreglo[p] + "] ");
                    } else {
                        System.out.print("[" + arreglo[p] + "] ");
                    }
                        }
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////
    /* Método "premio":
    Devuelve el premio obtenido en función de la modalidad, aciertos, y el
    dinero apostado.
    */
    
    double premio (int m, int a, double d){
        double premio = 0;
            /*
            Utilizamos un switch anidado para reducir mayormente la cantidad de
        lineas de código, en el caso 3 y 4, es posible estructurarlo con if else
        anidados pero para no complicar aún más la lectura preferimos empezar
        con el switch para que haya cierta estructura simplificada para 
        facilitar la lectura de los casos posteriores.
        
        El Switch principal considera la modalidad, los siguientes se encuentran
        en función de los aciertos.
        */
            
        switch (m){ // Switch Principal 
            // Caso 3
            /*
            En este caso consideramos la modalidad 3 y la cantidad de aciertos,
            dependiendo de la cantidad de aciertos, se le atribuye la cantidad
            del premio acorde al monto apostado que será devuelto.
            */
            case 3: switch (a){
                case 3: premio = d * 60;
                break;
                
                default: premio = d * -1;
            }
            break;
            
            /* Caso 4:
            En este caso consideramos la modalidad 4 y la cantidad de aciertos,
            dependiendo de la cantidad de aciertos, se le atribuye la cantidad
            del premio acorde al monto apostado que será devuelto.
            */
            
            case 4: switch (a){
                case 3: premio = d * 9;
                break;
                
                case 4: premio = d * 180;
                break;
                
                default: premio = d * -1;
            }
            break;
            
            /* Caso 5:
            En este caso consideramos la modalidad 5 y la cantidad de aciertos,
            dependiendo de la cantidad de aciertos, se le atribuye la cantidad
            del premio acorde al monto apostado que será devuelto.
            
            */
            case 5: switch (a){
                case 3: premio = d * 3;
                break;
                case 4: premio = d * 24;
                break;
                case 5: premio = d * 900;
                break;
                default: premio = d * -1;
            }
            break;
            
            /* Caso 6:
            En este caso consideramos la modalidad 6 y la cantidad de aciertos,
            dependiendo de la cantidad de aciertos, se le atribuye la cantidad
            del premio acorde al monto apostado que será devuelto.
            
            */
            case 6: switch (a){
                case 3: premio = d *1.5;
                break;
                
                case 4: premio = d * 9;
                break;
                
                case 5: premio = d * 90;
                break;
                
                case 6: premio = d * 3600;
                break;
                
                default: premio = d * -1;
            }
            break;
            
            /* Caso 7:
            En este caso consideramos la modalidad 7 y la cantidad de aciertos,
            dependiendo de la cantidad de aciertos, se le atribuye la cantidad
            del premio acorde al monto apostado que será devuelto.
            
            */
            case 7: switch (a){
                case 3: premio = d * 1;
                break;
                
                case 4: premio = d * 3;
                break;
                
                case 5: premio = d * 30;
                break;
                
                case 6: premio = d * 600;
                break;
                
                case 7: premio = d * 12000;
                break;
                
                default: premio = d * -1;
            }
            break;
        }   
        return premio;
    }
    ////////////////////////////////////////////////////////////////////////
    /*
    Método Total: Este método imprime los números jugados por el usuario (j), 
    la cantidad de veces que obtuvo premio (g) y el dinero total en premios (p).
    
    */
    void total (boolean[] j, int g, double p){
    //Imprime los números jugados por el usuario
        System.out.println("Los numeros jugados por el usuario son: ");
    for (int i = 0; i < j.length; i++) {
                if (j[i] == true){
                    if (i < 10) {
                        System.out.print("[0" + i + "] ");
                    } else {
                        System.out.print("[" + i + "] ");
                    }
                }
            }
            //Termina el for
        System.out.println("");
        System.out.println("La cantidad de veces que se obtuvo premio fue "
                + g + " veces");
        System.out.println("El dinero total en premios es = " + p);
    }
    
    
    ////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        Principal p = new Principal ();
        p.iniciar();



    }
    
}
