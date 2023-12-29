package lk.ijse.InrichDesignStudio.dao;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null)? daoFactory = new DAOFactory() : daoFactory ;
    }

    public enum DAOTypes{

    }
}
