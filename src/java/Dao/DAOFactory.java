package Dao;

public class DAOFactory {

    private static DAOFactory instance;

    private PersonaDAO personaDAO;
    private PacienteDAO pacienteDAO;
    private CitaMedicaDAO citaMedicaDAO;

    private DAOFactory() {
        personaDAO = PersonaDAO.getInstance();
        pacienteDAO = PacienteDAO.getInstance();
        citaMedicaDAO = CitaMedicaDAO.getInstance();
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public PersonaDAO getPersonaDAO() {
        return personaDAO;
    }

    public PacienteDAO getPacienteDAO() {
        return pacienteDAO;
    }

    public CitaMedicaDAO getCitaMedicaDAO() {
        return citaMedicaDAO;
    }
}
