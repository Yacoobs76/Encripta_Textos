package CriptoGram;

/**
 *
 * @author Yacoobs
 * 28-10-2018
 */

import java.awt.Color;
import java.util.*;
import javax.swing.*;

public class DesEncrip 
{
    private String guardar;
    private final ArrayList<String> Arraytexto;
    private final Interface intF;

    //Constructor de la CLASE............
    public DesEncrip(Interface intF) 
    {
        this.Arraytexto = new ArrayList<>();
        this.intF = intF;
    }
 

    public String desEnciptar(String TextoDocument, int Clave1, int Clave2, boolean paso4, int seleccion)
    {
        Arraytexto.clear();
        //Empezamos la Desencriptacion------------------------------------------------------------			
        String cambio = TextoDocument;
        
        //Metodo usado para el calculo del tiempo de ejecucion.
        int total = getTiempoEjecucion();

        if (paso4)
        {
            String resultado3="";
            for (int i=0;i<TextoDocument.length();i++)
            {
                char compare = TextoDocument.charAt(i);
                if (compare=='>'){resultado3= resultado3.concat("0");}
                if (compare=='%'){resultado3= resultado3.concat("1");}
                if (compare=='+'){resultado3= resultado3.concat("2");}
                if (compare=='!'){resultado3= resultado3.concat("3");}
                if (compare==';'){resultado3= resultado3.concat("4");}
                if (compare=='Ç'){resultado3= resultado3.concat("5");}
                if (compare=='-'){resultado3= resultado3.concat("6");}
                if (compare=='ª'){resultado3= resultado3.concat("7");}
                if (compare=='¶'){resultado3= resultado3.concat("8");}
                if (compare=='$'){resultado3= resultado3.concat("9");}
            }
            cambio = resultado3;
        }
        
        int matematica=0;
        int Xx=0;
        String resultado = "", NPosi, anadir;
        try
        {
            for (int f=0;f<cambio.length();f+=8)
            {						
                anadir="";
                if (Xx==0){
                    matematica = Integer.parseInt(cambio.substring(f, 8 + f)) - Clave1;
                    NPosi = String.valueOf(matematica);
                    if (NPosi.length()==1){anadir="0000000";}
                    else if (NPosi.length()==2){anadir="000000";}
                    else if (NPosi.length()==3){anadir="00000";}		
                    else if (NPosi.length()==4){anadir="0000";}
                    else if (NPosi.length()==5){anadir="000";}
                    else if (NPosi.length()==6){anadir="00";}
                    else if (NPosi.length()==7){anadir="0";}

                    resultado = resultado.concat(String.valueOf(anadir+matematica));

                    Xx=1;
                }
                else
                {
                    matematica = Integer.parseInt(cambio.substring(f, 8 + f)) - Clave2;				
                    NPosi = String.valueOf(matematica);
                    if (NPosi.length()==1){anadir="0000000";}
                    else if (NPosi.length()==2){anadir="000000";}
                    else if (NPosi.length()==3){anadir="00000";}		
                    else if (NPosi.length()==4){anadir="0000";}
                    else if (NPosi.length()==5){anadir="000";}
                    else if (NPosi.length()==6){anadir="00";}
                    else if (NPosi.length()==7){anadir="0";}

                    resultado = resultado.concat(String.valueOf(anadir+matematica));

                    Xx=0;				
                }									
            }
        }
        catch (StringIndexOutOfBoundsException e) 
        { 
            JOptionPane.showConfirmDialog(null, "DesEncriptacion imposible de realizar...\n" + e,"FATAL ERROR",JOptionPane.DEFAULT_OPTION,0);
            guardar = "";
            return "";
        }
        catch (NumberFormatException e)
        {	
            JOptionPane.showConfirmDialog(null, "DesEncriptacion imposible de realizar...\n" + e,"FATAL ERROR",JOptionPane.DEFAULT_OPTION,0);
            guardar = "";
            return "";
        }
 
        cambio = "";
        TextoDocument = resultado;
        
        for (int x=0;x<TextoDocument.length();x++)
        {
            if (TextoDocument.charAt(x)=='1')
            {
                cambio=cambio.concat("0");				
            }
            if (TextoDocument.charAt(x)=='0')
            {
                cambio=cambio.concat("1");
            }			
        }
        guardar = cambio;
        //Esto lo que hace es guardar en un ArrayList el contenido de la variable guardar que se encuentra concatenada, la separa en grupos de 8 objetos.
        String finaltexto = "";	
        int x=0;
        try
        {
            for (int f=0;f<guardar.length();f+=8)
            {
                //Almacenamos en Arraytexto el contenido de la variable guardar en las posiciones asignadas por el metodo subString.		
                Arraytexto.add(guardar.substring(f,8+f));
                //Pasamos de binario a decimal o AscII.	
                int Ascci = Integer.parseInt(Arraytexto.get(x),2);
                //Pasamos de AscII a su caracter correspondiente.	
                char caracter= (char)Ascci;
                //Concatenamos el contenido guardar con el contenido de conversionCaracter.	
                finaltexto = finaltexto.concat(String.valueOf(caracter));			
                x++;
            }	

        }
        catch (StringIndexOutOfBoundsException e) 
        {
            JOptionPane.showConfirmDialog(null, "Error de incompativilidad de clave o encriptacion\n" + e,"FATAL ERROR", JOptionPane.DEFAULT_OPTION,0);
            guardar = "";
            return "";
        }			

        if (seleccion == 2)
            intF.JLabel_Funciones("Proyecto cargado en " + getTiempoEjecucionTotal(total) + " segundos", Color.WHITE);
        else
            intF.JLabel_Funciones("Desencriptacion Terminada en "+ getTiempoEjecucionTotal(total) +" segundos", Color.WHITE);

        guardar = "";
        guardar = finaltexto;
        
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



