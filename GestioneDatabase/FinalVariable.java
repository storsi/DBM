

import java.awt.Color;
import java.awt.FlowLayout;

public class FinalVariable {
    
    //STRINGHE - array
    public static String PATH_TO_DB_FISSO = "C:\\Users\\Matteo\\OneDrive - Politecnico di Milano\\Programmazione\\SqLite\\";
    public static String PATH_TO_DB_PORTATILE = "C:\\Users\\sprea\\OneDrive - Politecnico di Milano\\Programmazione\\SqLite\\";
    //STRINGHE - valori
    public static String[] ALL_DATABASES = {"calcio.db"};

    //INTERI - array

    //INTERI - valori
    public static final int MAX_LENGTH = 20;

    public static final int AGG_COLONNA = 2, AGG_TAB = 6;
    public static final int CREA_TAB = 0, CANC_TAB = 1, ELIM_NUOVA_COLONNA = 5;
    public static final int BOTT_TAB = 3, BOTT_DB = 4, BOTT_AGG_TAB = 7, BOTT_AGG_DB = 8;

    //COLORI
    public static final Color ERROR_COLOR = new Color(233, 116, 81);

    //LAYOUT
    public static final FlowLayout FL_C20_20 = new FlowLayout(FlowLayout.CENTER, 20, 20);
    public static final FlowLayout FL_L10_10 = new FlowLayout(FlowLayout.LEFT, 10, 10);
    public static final FlowLayout FL_L0_0 = new FlowLayout(FlowLayout.LEFT, 0, 0);
    
}
