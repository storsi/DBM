package Oggetti;


import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BorderFactory;

public class FinalVariable {

    //Valore del panel
    public static final int PANEL_WIDTH = 1200;
    public static final int PANEL_HEIGHT = 700;
    public static final int CHARACTER_WIDTH = 12;
    
    //STRINGHE - array
    public static String PATH_TO_DB_FISSO = "C:\\Users\\Matteo\\OneDrive - Politecnico di Milano\\Programmazione\\SqLite\\Databases\\";
    public static String PATH_TO_DB_PORTATILE = "C:\\Users\\sprea\\OneDrive - Politecnico di Milano\\Programmazione\\SqLite\\Databases\\";
    //STRINGHE - valori
    public static String[] ALL_DATABASES = {"calcio.db"};

    //INTERI - array

    //INTERI - valori
    public static final int MAX_LENGTH = 20;

    public static final int AGG_COLONNA = 2, AGG_TAB = 6;
    public static final int CREA_TAB = 0, CANC_TAB = 1, ELIM_NUOVA_COLONNA = 5;
    public static final int BOTT_TAB = 3, BOTT_DB = 4, BOTT_AGG_TAB = 7, BOTT_AGG_DB = 8;

    public static final int BTN_ELIMINA = 0, BTN_MODIFICA = 1, BTN_OK = 2, BTN_CANC = 3, BTN_AGGIUNGI = 4;
    public static final int BTN_INDIETRO = 5, BTN_ELIMINA_TAB = 6; 

    //COLORI
    public static final Color ERROR_COLOR = new Color(233, 116, 81);
    public static final Color CELLA_COLONNA = Color.YELLOW;
    public static final Color CELLA_DATO = Color.WHITE;
    public static final Color CELLA_MODIFICA = new Color(176,224,230);

    //LAYOUT
    public static final FlowLayout FL_C20_20 = new FlowLayout(FlowLayout.CENTER, 20, 20);
    public static final FlowLayout FL_C10_10 = new FlowLayout(FlowLayout.CENTER, 10, 10);
    public static final FlowLayout FL_L10_10 = new FlowLayout(FlowLayout.LEFT, 10, 10);
    public static final FlowLayout FL_L0_0 = new FlowLayout(FlowLayout.LEFT, 0, 0);
    public static final FlowLayout FL_C2_2 = new FlowLayout(FlowLayout.CENTER, 2, 2);
    public static final FlowLayout FL_C3_3 = new FlowLayout(FlowLayout.CENTER, 3, 3);
    public static final FlowLayout FL_R40_40 = new FlowLayout(FlowLayout.RIGHT, 40, 40);

    public static final Component hSpace100 = Box.createHorizontalStrut(100);
    public static final Component vSpace100 = Box.createVerticalStrut(100);
    
    //Boolean

    public static final boolean IS_BTN_AGGIUNGI = true, NOT_BTN_AGGIUNGI = false;
}
