package CriptoGram;


import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * @author Yacoobs 
 * Para contactar conmigo: reyrelampago2005@gamil.com
 * https://www.youtube.com/channel/UChFlaF3Ji9vWhizWyhBQdBg?view_as=subscriber
 * Ultima modificacon del codigo 21-03-2019
 * Programa sencillo de encriptacion de 4 pasos.
 * Las claves por defecto del programa.
 * Clave2 = 32487965;
 * Clave1 = 48768947;
 */

    

public final class Interface extends javax.swing.JFrame
{

    private final String titulo;
    //Variables Privadas del programa.
    private final JFileChooser JfileDestino;
    //Filtrado de archivos ecy.
    private final FileNameExtensionFilter filter;		
    //Establece la Clave por defecto.
    private int Clave1;					
    private int Clave2;
    
    private boolean activar;
    //Variables String utlizadas en la gestion del programa.
    private String mensaje, copi;			
    private final File file;
    //Establece la ruta por defecto donde se guardaran tus archivos ecy.
    private String ruta;
    //Utilizado en el guardado y carga de los documentos ecy.
    private FileWriter filewrite;					
    private FileReader filereader;
    private BufferedWriter buffwrite;
    private BufferedReader buffreader;
    private PrintWriter textoGuardar;
    private final String extension;

    //Instancia de CLASES externas.
    private final DesEncrip desencrip;
    private final EnCripta encripta;
    
    //Constructor de la CLASE principal.............
    public Interface(String argumento)
    {
        initComponents();
        
        this.desencrip = new DesEncrip(this);
        this.encripta = new EnCripta(this);
        
        this.extension = "ecy";
        this.file = new File("");
        this.ruta = file.getAbsolutePath();
        this.titulo = "Cripto_Gram_V.4.7";
        this.activar = true;
        this.Clave2 = 32487965;
        this.Clave1 = 48768947;
        this.filter = new FileNameExtensionFilter("Solo encriptación", extension);
        this.JfileDestino = new JFileChooser();


        JL_destino.setText(ruta);
        jTextField_Clave1.setText("" + Clave1);
        jTextField_Clave2.setText("" + Clave2);
        setTitle(titulo);
        
        if (argumento!=null)
        {
            ruta = argumento;
            CargarDocument();
        }
        
        setFileDrop_JTextArea();
        
        setVisible(true);  
    }
    
    
    
    private void setFileDrop_JTextArea()
    {
        new FileDrop(jTextArea_Pantalla1, (File[] files) -> 
        {
            for (File f : files)
                ruta = f.getAbsolutePath();
            
            ObtenerDocumentFileDrop();
            
        });
    }
    
    public void ObtenerDocumentFileDrop()
    {
        JL_destino.setText(ruta);
        CargarDocument();
    }
    
    private void GuardadoDocument()
    {
        try
        {									
            if (JOptionPane.showConfirmDialog(null, "Estas seguro de guardar el archivo en la ruta: \n" + ruta, "Atención", JOptionPane.CANCEL_OPTION, 3) == 2)
            {
                JfileDestino.setDialogTitle("Guardar Archivo");
                JfileDestino.setCurrentDirectory(new File(ruta));
                JfileDestino.setFileFilter(filter);
                if (JfileDestino.showSaveDialog(this) == 0)
                {
                    char cadena=' ';
                    //Capturamos posibles errores con un try Catch.. 
                    try
                    {
                        //Nos da la cantidad de elementos de objeto de la cadena string.
                        int evalua = JfileDestino.getSelectedFile().toString().length();
                        //Nos proporciona el objeto que ocupa en la posicion evalua -4.
                        cadena = JfileDestino.getSelectedFile().toString().charAt(evalua-4);			

                    }				                   
                    catch (NullPointerException e){}	
                    //Evaluamos si el objeto de cadena es un . quiere decir que introducimos su extension por lo tanto se cumple la condicion.        
                    if (cadena=='.')
                    {					
                        ruta=JfileDestino.getSelectedFile().toString();						
                    }
                    else 
                    {					
                        ruta= JfileDestino.getSelectedFile().toString().concat("." + extension);
                    }
                    JL_destino.setText("Destino Archivo: " + ruta);
                }
                else
                    return;
            }
            
            filewrite = new FileWriter(ruta);
            buffwrite = new BufferedWriter(filewrite);
            textoGuardar = new PrintWriter(buffwrite);
            
            //Evaluamos la condicion false de la variable activar  que no ejecutar la condicion no encriptar.
            if (!activar)
            {
                ArrayList<String> arrayString = new ArrayList<>();
                arrayString.add(jTextArea_Pantalla1.getText());
                JL_funciones.setText("Proyecto Guardado..");
                for (String objeto:arrayString)
                {													
                    textoGuardar.write(objeto);							
                }
                    
            }//Por lo contrario si no se cumple la condicion ejecuta la encriptacion del mensaje.
            else 
            {
                //------------------------------Empezamos la encriptacion--------------------------------------------------
                //Capturamos el contenido de del JTextArea y lo almacenamos en mensaje una variable String.
                mensaje = jTextArea_Pantalla1.getText();
                
                String Obt_Documt = encripta.Encriptar(mensaje, Clave1, Clave2, jCheckBox_Encrip4.isSelected(), 1);
                
                //Con este método almacenamos el contenido final guardar en un texto ecy.
                textoGuardar.write(Obt_Documt);
                
                //Imprimimos JTextArea el contenido de guardar.
                jTextArea_Pantalla2.setText(Obt_Documt);                                                    
            }										
            buffwrite.close();//Cerramos el bufferwrite
            textoGuardar.close();//Cerramos el printWrite										
        }
        catch (IOException e) 
        {
            JOptionPane.showConfirmDialog(null, "La ruta a la que haces referencia precisa de un archivo!!! \n" + e, "Atención", JOptionPane.DEFAULT_OPTION, 0);
        }
        catch(NullPointerException e)
        {
            JOptionPane.showConfirmDialog(null, "Error!!! \n" + e, "Atención", JOptionPane.DEFAULT_OPTION, 0);
        }
    }
    
    private void AbrirDocument()
    {
         //Establecemos el destino donde se guardara o cargara el archivo.
        JfileDestino.setDialogTitle("Ruta Destino Archivo");
        JfileDestino.setCurrentDirectory(new File(ruta));
        JfileDestino.setFileFilter(filter);
        int accion = JfileDestino.showOpenDialog(null);
        
        JLabel_Funciones("Ruta y archivo", Color.BLACK);

        char cadena=' ';
        //Capturamos posibles errores con un try Catch.. 
        try
        {
            //Nos da la cantidad de elementos de objeto de la cadena string.
            int evalua = JfileDestino.getSelectedFile().toString().length();
            //Nos proporciona el objeto que ocupa en la posicion evalua -4.	
            cadena = JfileDestino.getSelectedFile().toString().charAt(evalua-4); 			
        }
        catch (NullPointerException errr){}					
        if (accion==0)
        {
            //Evaluamos si el objeto de cadena es un . quiere decir que introducimos su extension por lo tanto se cumple la condicion.
            if (cadena=='.')
                ruta = JfileDestino.getSelectedFile().toString();						
            else 
                ruta = JfileDestino.getSelectedFile().toString().concat("." + extension);		           
            
            JL_destino.setText(ruta);
            
            CargarDocument();
        }
    }
    
    private void Encriptacion()
    {	
        //Capturamos el contenido de del JTextArea y lo almacenamos en mensaje una variable String.
        mensaje = jTextArea_Pantalla1.getText();				
        if (activar)
            //Imprimimos JTextArea el contenido de guardar.
            jTextArea_Pantalla2.setText(encripta.Encriptar(mensaje, Clave1, Clave2, jCheckBox_Encrip4.isSelected(), 0));
    }
    
    private void Desencriptacion()
    {
        //Boton de la Desencriptacion del texto.
        jTextArea_Pantalla2.setText("");
        if (activar)
            //Imprimimos el contenido de la variable guardar en el JTextArea.
            jTextArea_Pantalla2.append(desencrip.desEnciptar(jTextArea_Pantalla1.getText(), Clave1, Clave2, jCheckBox_Encrip4.isSelected(), 0));
    }
    
    
    public String getExtensionFile(String rutafile)
    {
        String extensionFile = "";
        boolean estado = false;
        
        for (int i=0;i<rutafile.length();i++)
        {
            char caracter = rutafile.charAt(i);
            if (estado)
                    extensionFile += caracter;
            
            if (caracter == '.')
            {
                extensionFile = "";
                estado =  true;
            } 
        }
        
        if (extensionFile.equals(""))
            return null;
        
        return extensionFile;
    }
    
    private void CargarDocument()
    {
        String rutaFile = getExtensionFile(ruta);
                
        String contexto;
        String Precambio = null;
        jTextArea_Pantalla1.setText("");
        try 
        {														
            filereader = new FileReader(ruta);
            buffreader = new BufferedReader(filereader);					

            while((contexto=buffreader.readLine())!=null)
                Precambio = contexto;
             
            if (rutaFile.equals("ecy"))
            {  
                if (activar)
                    jTextArea_Pantalla1.setText(desencrip.desEnciptar(Precambio, Clave1, Clave2, jCheckBox_Encrip4.isSelected(), 2)); 
            }
            else if (rutaFile.equals("txt") || rutaFile.equals("dat") || rutaFile.equals("log"))
            {
                if (!activar)
                    jTextArea_Pantalla1.setText(Precambio);  
                else
                    JOptionPane.showConfirmDialog(null, "Desactiva 3 y 4 Encrip!!!!","Atención", JOptionPane.DEFAULT_OPTION, 2);
            }
            else
                JOptionPane.showConfirmDialog(null, "Ese tipo de extension no esta soportada por el programa!!!","Atención", JOptionPane.DEFAULT_OPTION, 0);

            //Cerramos el buffreader
            buffreader.close();	
            //Cerramos el filereader
            filereader.close();																			
        }
        catch (IOException e1) 
        {
            JOptionPane.showConfirmDialog(null, "Especifica la ruta y nombre de archivo!!","Atención", JOptionPane.DEFAULT_OPTION, 0);
        }
        catch(NullPointerException a2)
        {
            System.out.println("Error Metodo CargarDocument() " + a2);
        }
        
    }
    
    private void AccionCheckBox()
    {
        activar = false;
        //Hacemos que el JCheckBox pase ha estado inavilitado.
        jCheckBox_Encrip4.setEnabled(false);
        //Hacemos que el HCheckBox pase a su seleccion off.
        jCheckBox_Encrip4.setSelected(false);
        
        if (jCheckBox_Encrip3.getSelectedObjects()!=null)
        {
            activar = true;
            jCheckBox_Encrip4.setEnabled(true);
        }
    }
    
    private void JTetF_Clave2()
    {
        try
        {
            //Obtenemos el numero de clave1 contenido en el JTextField.
            Clave2 = Integer.parseInt(jTextField_Clave2.getText());					
            //Camturamos la cantidad de	caracteres y lo almacenamos en una variable String.
            String obtener = String.valueOf(String.valueOf(Clave2).length());
            //Evaluamos la condicion si obtener contiene 9 caracteres.
            if (obtener.equals("9"))
            {
                //Clave1 capturamos solamente 8 digitos.
                Clave2 = Integer.parseInt(jTextField_Clave2.getText().substring(0, 8));
            }

            }
        catch (NumberFormatException e2) 
        {
            System.out.println("No introdujo ningun numero o introdujo un caracter");
        }
        JLabel_Funciones("Nueva clave2 es " + Clave2, Color.GREEN);
        jTextField_Clave2.setText(""+Clave2);
    }
    
    private void JTetF_Clave1()
    {
        //Establece la Clave2 en el JTextField.
        try
        {
            Clave1 = Integer.parseInt(jTextField_Clave1.getText());							
            String obtener = String.valueOf(String.valueOf(Clave1).length());			
            if (obtener.equals("9"))
            {
                Clave1 = Integer.parseInt(jTextField_Clave1.getText().substring(0, 8));
            }    
        }
        catch (NumberFormatException e2) 
        {
            System.out.println("No introdujo ningun numero o introdujo un caracter");   
        }
        JLabel_Funciones("Nueva clave1 es " + Clave1, Color.GREEN);
        jTextField_Clave1.setText(""+Clave1);
    }
    
    private void ResetClaves()
    {
        //Se encarga de volver a predefinir las claves por defecto a su estado inicial.
        Clave1=48768947;					
        Clave2=32487965;
        jTextField_Clave1.setText(""+Clave1);
        jTextField_Clave2.setText(""+Clave2);
        JLabel_Funciones("Las claves fueron reseteadas...", Color.ORANGE);
    }
   
    public void JLabel_Funciones(String texto, Color color)
    {
        JL_funciones.setText(texto);
        JL_funciones.setForeground(color);
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Titulo = new javax.swing.JLabel();
        jCheckBox_Encrip3 = new javax.swing.JCheckBox();
        jCheckBox_Encrip4 = new javax.swing.JCheckBox();
        jTextField_Clave1 = new javax.swing.JTextField();
        jTextField_Clave2 = new javax.swing.JTextField();
        Boton_Encrip = new javax.swing.JButton();
        Boton_DesEn = new javax.swing.JButton();
        Buton_Guardado = new javax.swing.JButton();
        Boton_Abrir = new javax.swing.JButton();
        Boton_Copiar = new javax.swing.JButton();
        Boton_Pegar = new javax.swing.JButton();
        Boton_Borrado = new javax.swing.JButton();
        Boton_LimpiarTodo = new javax.swing.JButton();
        Boton_Cargar = new javax.swing.JButton();
        jButton_ResetClaves = new javax.swing.JButton();
        JL_destino = new javax.swing.JLabel();
        JL_funciones = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        J_Panel_1 = new javax.swing.JScrollPane();
        jTextArea_Pantalla1 = new javax.swing.JTextArea();
        J_Panel_2 = new javax.swing.JScrollPane();
        jTextArea_Pantalla2 = new javax.swing.JTextArea();
        J_Fondo_Interface = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Titulo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 153, 51));
        Titulo.setText("Programado por Yacoobs Cort Mart 28-10-2018");
        getContentPane().add(Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 390, 30));

        jCheckBox_Encrip3.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox_Encrip3.setSelected(true);
        jCheckBox_Encrip3.setText("3 Encrip");
        jCheckBox_Encrip3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_Encrip3ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox_Encrip3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, -1, -1));

        jCheckBox_Encrip4.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox_Encrip4.setSelected(true);
        jCheckBox_Encrip4.setText("4 Encrip");
        jCheckBox_Encrip4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_Encrip4ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox_Encrip4, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 100, -1, -1));

        jTextField_Clave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_Clave1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField_Clave1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 180, 80, -1));

        jTextField_Clave2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_Clave2ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField_Clave2, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 240, 80, -1));

        Boton_Encrip.setText("Encriptar");
        Boton_Encrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_EncripActionPerformed(evt);
            }
        });
        getContentPane().add(Boton_Encrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        Boton_DesEn.setText("Desencriptar");
        Boton_DesEn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_DesEnActionPerformed(evt);
            }
        });
        getContentPane().add(Boton_DesEn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        Buton_Guardado.setText("Guardar");
        Buton_Guardado.setToolTipText("Encripta y guarda el contenido en un archivo ecy");
        Buton_Guardado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buton_GuardadoActionPerformed(evt);
            }
        });
        getContentPane().add(Buton_Guardado, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, -1, -1));

        Boton_Abrir.setText("Abrir/Ruta");
        Boton_Abrir.setToolTipText("Establece la ruta y el nombre de archivo");
        Boton_Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_AbrirActionPerformed(evt);
            }
        });
        getContentPane().add(Boton_Abrir, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 420, -1, -1));

        Boton_Copiar.setText("Copiar");
        Boton_Copiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_CopiarActionPerformed(evt);
            }
        });
        getContentPane().add(Boton_Copiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, -1, -1));

        Boton_Pegar.setText("Pegar");
        Boton_Pegar.setToolTipText("Pega el contenido en el panel principal");
        Boton_Pegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_PegarActionPerformed(evt);
            }
        });
        getContentPane().add(Boton_Pegar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, -1, -1));

        Boton_Borrado.setForeground(new java.awt.Color(255, 0, 51));
        Boton_Borrado.setText("Borrar");
        Boton_Borrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_BorradoActionPerformed(evt);
            }
        });
        getContentPane().add(Boton_Borrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, -1));

        Boton_LimpiarTodo.setForeground(new java.awt.Color(255, 0, 0));
        Boton_LimpiarTodo.setText("Borrar");
        Boton_LimpiarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_LimpiarTodoActionPerformed(evt);
            }
        });
        getContentPane().add(Boton_LimpiarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        Boton_Cargar.setText("Cargar Archivo");
        Boton_Cargar.setToolTipText("Desencripta y abre el archivo segun la ruta");
        Boton_Cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_CargarActionPerformed(evt);
            }
        });
        getContentPane().add(Boton_Cargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 380, -1, -1));

        jButton_ResetClaves.setForeground(new java.awt.Color(255, 51, 51));
        jButton_ResetClaves.setText("Reset Claves");
        jButton_ResetClaves.setToolTipText("Inicia las claves a su punto inicial");
        jButton_ResetClaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ResetClavesActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_ResetClaves, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 290, -1, -1));

        JL_destino.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        JL_destino.setForeground(new java.awt.Color(255, 255, 255));
        JL_destino.setText("----------------------------");
        getContentPane().add(JL_destino, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 590, 710, 20));

        JL_funciones.setForeground(new java.awt.Color(255, 255, 255));
        JL_funciones.setText("----------------------------");
        getContentPane().add(JL_funciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 620, 710, 20));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Clave 1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 160, -1, -1));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Clave 2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 220, -1, -1));

        jTextArea_Pantalla1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea_Pantalla1.setColumns(20);
        jTextArea_Pantalla1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea_Pantalla1.setLineWrap(true);
        jTextArea_Pantalla1.setRows(5);
        jTextArea_Pantalla1.setCaretColor(new java.awt.Color(255, 0, 51));
        J_Panel_1.setViewportView(jTextArea_Pantalla1);

        getContentPane().add(J_Panel_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 710, 270));

        jTextArea_Pantalla2.setEditable(false);
        jTextArea_Pantalla2.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea_Pantalla2.setColumns(20);
        jTextArea_Pantalla2.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea_Pantalla2.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea_Pantalla2.setLineWrap(true);
        jTextArea_Pantalla2.setRows(5);
        J_Panel_2.setViewportView(jTextArea_Pantalla2);

        getContentPane().add(J_Panel_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 710, 200));

        J_Fondo_Interface.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/encriptar.jpg"))); // NOI18N
        J_Fondo_Interface.setMaximumSize(new java.awt.Dimension(800, 1000));
        J_Fondo_Interface.setMinimumSize(new java.awt.Dimension(800, 1000));
        J_Fondo_Interface.setPreferredSize(new java.awt.Dimension(800, 1000));
        getContentPane().add(J_Fondo_Interface, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Buton_GuardadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buton_GuardadoActionPerformed
        GuardadoDocument();
    }//GEN-LAST:event_Buton_GuardadoActionPerformed

    private void Boton_AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_AbrirActionPerformed
        AbrirDocument();       
    }//GEN-LAST:event_Boton_AbrirActionPerformed

    private void Boton_CopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_CopiarActionPerformed
        copi = jTextArea_Pantalla2.getText();
        JLabel_Funciones("Proyecto copiado..", Color.RED);
    }//GEN-LAST:event_Boton_CopiarActionPerformed

    private void Boton_PegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_PegarActionPerformed
        jTextArea_Pantalla1.setText(copi);
        JLabel_Funciones("Proyecto pegado", Color.RED);
    }//GEN-LAST:event_Boton_PegarActionPerformed

    private void Boton_EncripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_EncripActionPerformed
        Encriptacion();        		
    }//GEN-LAST:event_Boton_EncripActionPerformed

    private void Boton_DesEnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_DesEnActionPerformed
        Desencriptacion();
    }//GEN-LAST:event_Boton_DesEnActionPerformed

    private void Boton_BorradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_BorradoActionPerformed
        jTextArea_Pantalla2.setText("");
        JLabel_Funciones("Borrado..", Color.ORANGE);
    }//GEN-LAST:event_Boton_BorradoActionPerformed

    private void Boton_LimpiarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_LimpiarTodoActionPerformed
        jTextArea_Pantalla1.setText("");				
        JLabel_Funciones("Borrado", Color.ORANGE);
        
    }//GEN-LAST:event_Boton_LimpiarTodoActionPerformed

    private void Boton_CargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_CargarActionPerformed
        CargarDocument();
    }//GEN-LAST:event_Boton_CargarActionPerformed

    private void jCheckBox_Encrip3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_Encrip3ActionPerformed
        AccionCheckBox();
    }//GEN-LAST:event_jCheckBox_Encrip3ActionPerformed

    private void jCheckBox_Encrip4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_Encrip4ActionPerformed
  
    }//GEN-LAST:event_jCheckBox_Encrip4ActionPerformed

    private void jTextField_Clave2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Clave2ActionPerformed
        JTetF_Clave2();
    }//GEN-LAST:event_jTextField_Clave2ActionPerformed

    private void jTextField_Clave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Clave1ActionPerformed
        JTetF_Clave1();
    }//GEN-LAST:event_jTextField_Clave1ActionPerformed

    private void jButton_ResetClavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ResetClavesActionPerformed
        ResetClaves();
    }//GEN-LAST:event_jButton_ResetClavesActionPerformed

    
    public static void main(String[] args) 
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try
        {
            new Interface(args[0]);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            new Interface(null).setVisible(true);
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Boton_Abrir;
    private javax.swing.JButton Boton_Borrado;
    private javax.swing.JButton Boton_Cargar;
    private javax.swing.JButton Boton_Copiar;
    private javax.swing.JButton Boton_DesEn;
    private javax.swing.JButton Boton_Encrip;
    private javax.swing.JButton Boton_LimpiarTodo;
    private javax.swing.JButton Boton_Pegar;
    private javax.swing.JButton Buton_Guardado;
    private javax.swing.JLabel JL_destino;
    private javax.swing.JLabel JL_funciones;
    private javax.swing.JLabel J_Fondo_Interface;
    private javax.swing.JScrollPane J_Panel_1;
    private javax.swing.JScrollPane J_Panel_2;
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton jButton_ResetClaves;
    private javax.swing.JCheckBox jCheckBox_Encrip3;
    private javax.swing.JCheckBox jCheckBox_Encrip4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextArea jTextArea_Pantalla1;
    private javax.swing.JTextArea jTextArea_Pantalla2;
    private javax.swing.JTextField jTextField_Clave1;
    private javax.swing.JTextField jTextField_Clave2;
    // End of variables declaration//GEN-END:variables
}
