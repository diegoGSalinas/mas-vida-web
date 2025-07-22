package MasVida.Log;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class LoggerUtil {
    private static final String LOG_FILE_PATH = "c:\\Users\\Diego\\Documents\\NetBeansProjects\\Mas_Vida_Web\\build\\web\\logs\\masvida.log";
    
    private LoggerUtil() {}
    
    /**
     * Método de prueba para generar logs sin necesidad de hacer login
     */
    public static void testLogger() {
        System.out.println("Iniciando testLogger...");
        Logger logger = getLogger(LoggerUtil.class.getName());
        logger.info("=== Test de Logger ===");
        logger.info("Este es un mensaje de prueba");
        logger.warning("Este es un warning de prueba");
        System.out.println("TestLogger completado");
    }
    
    public static Logger getLogger(String className) {
        System.out.println("Obteniendo logger para: " + className);
        Logger logger = null;
        
        try {
            logger = Logger.getLogger(className);
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            System.out.println("FileHandler creado para: " + LOG_FILE_PATH);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            
            // Configurar el logger para que no herede handlers de la jerarquía
            logger.setUseParentHandlers(false);
            System.out.println("Logger configurado exitosamente");
        } catch (IOException e) {
            System.err.println("Error al configurar el logger: " + e.getMessage());
            e.printStackTrace();
            System.err.println("Path del archivo: " + LOG_FILE_PATH);
        }
        
        return logger;
    }
}
