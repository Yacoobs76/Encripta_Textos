package CriptoGram;

/**
 *
 * @author Yacoobs
 * 28-10-2018
 */

import java.awt.Color;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EnCripta 
{
    private String guardar;	
    private int codigoAscii;
    
    private final Interface intF;
    
    public EnCripta(Interface intF)
    {
        this. intF = intF;
    }
	
    public String  Encriptar(String TextoDocumets, int Clave1, int Clave2, boolean paso4, int seleccion)
    { 
        String PreCambio = "";
        //----------------------------Empezamos la Encriptacion------------------------------------------------------------	

        int total = getTiempoEjecucion();

        //Creamos un blucle for este se repite mientras su condicion se cumpla.
        for (int a=0;a<TextoDocumets.length();a++)
        {
            //Obtengo su codigo Ascii.
            codigoAscii = TextoDocumets.charAt(a);
            
            //Le damos el resultado de nada a Binario cada vuelta de for.			
            String Binario = "";
        //Creamos un blucle for este se repite mientras su condicion se cumpla este menos de crecer decrece.    
        for (int b=7;b>=0;b--)
        {						
            if (codigoAscii>=Math.pow(2, b))
            {		
                //Creamos una condicion bastante compleja....Devuelve un número especificado elevado a la potencia especificada.
                codigoAscii-=Math.pow(2, b);
                //Con este método la variable Binario va concatenando una cadena de unos (1).
                Binario = Binario.concat("1");		
            }
            else 
            {
                //Con este método la variable Binario va concatenando una cadena de ceros (0).
                Binario = Binario.concat("0");		
            }					
        }
        //Concatenamos la variable PreCambio obteniendo el contenido de la variable Binario.
        PreCambio = PreCambio.concat(Binario);		
        }					


        String cambio="";
        //Evaluamos con el metodo length la longitud en caracteres de la variable PreCambio.
        for (int x=0;x<PreCambio.length();x++)
        {	
            //Con el metodo charAt(). evaluamos el caracter en la posicion x y la comparamos con 1.
            if (PreCambio.charAt(x)=='1')
            {
                //Concatenamos la variable cambio y le damos un nuevo numero 0.	
                cambio=cambio.concat("0");			
            }
            if (PreCambio.charAt(x)=='0')
            {
                cambio=cambio.concat("1");
            }			
        }
        int matematica;
        int x=0;
        String resultado = "";
        //Evalumaos la condicion mientras esta se cumpla la longitud del contenido de la variable cambio.
        for (int f=0;f<cambio.length();f+=8)
        {						
            if (x==0)
            {
                matematica= Integer.parseInt(cambio.substring(f,8+f)) + Clave1;
                resultado = resultado.concat(String.valueOf(matematica));
                x=1;
            }
            else
            {
                matematica= Integer.parseInt(cambio.substring(f,8+f)) + Clave2;				
                resultado = resultado.concat(String.valueOf(matematica));				
                x=0;				
            }	
            int entero= Integer.parseInt(PreCambio.substring(f, 8+f), 2);
            char chh = (char)entero;			
            //System.out.println(chh + "  " +PreCambio.substring(f,8+f)+" "+cambio.substring(f,8+f)+"  "+x+"  "+resultado.substring(f, 8+f)+"  "+f);
        }		
        //El paso 4 cambiamos los numero por signos.
        if (paso4)
        {	
            String resultado3 = "";
            for (int i=0;i<resultado.length();i++)
            {
                char compare = resultado.charAt(i);
                if (compare=='0'){resultado3= resultado3.concat(">");}
                if (compare=='1'){resultado3= resultado3.concat("%");}
                if (compare=='2'){resultado3= resultado3.concat("+");}
                if (compare=='3'){resultado3= resultado3.concat("!");}
                if (compare=='4'){resultado3= resultado3.concat(";");}
                if (compare=='5'){resultado3= resultado3.concat("Ç");}
                if (compare=='6'){resultado3= resultado3.concat("-");}
                if (compare=='7'){resultado3= resultado3.concat("ª");}
                if (compare=='8'){resultado3= resultado3.concat("¶");}
                if (compare=='9'){resultado3= resultado3.concat("$");}
            }
            guardar = resultado3;
        }
        else
        {
            guardar = resultado;	
        }
        
        if (seleccion == 1)
            intF.JLabel_Funciones("Proyecto Guardado en " + getTiempoEjecucionTotal(total) + " segundos", Color.WHITE);	
        else
            intF.JLabel_Funciones("Encriptacion Terminada en " + getTiempoEjecucionTotal(total) + " segundos", Color.WHITE);
        
        return guardar;
    }
    
    private int getTiempoEjecucion()
    {
        int Ensegundo = new GregorianCalendar().get(Calendar.SECOND);
        int Enminuto =new GregorianCalendar().get(Calendar.MINUTE)*60;
        return  Ensegundo + Enminuto;
    }
    
    private int getTiempoEjecucionTotal(int total)
    {
        int Ensegundo1 = new GregorianCalendar().get(Calendar.SECOND);	
        int Enminuto1 = new GregorianCalendar().get(Calendar.MINUTE)*60;
        return (Ensegundo1 + Enminuto1) - total;
    }
	
}