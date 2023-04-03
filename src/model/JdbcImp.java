package model;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcImp {
    public DataSource dataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource() ;
        dataSource.setUser("postgres");
        dataSource.setPassword("Happylife-12");
        dataSource.setDatabaseName("postgres");
        return dataSource;
    }

//    public DriverManager driverManager(){
    //method 2;
//        String url="jdbc:postgresql://localhost:5432/postgres";
//        String username="postgres";
//        String password="Happylife-12";
//    }


}
