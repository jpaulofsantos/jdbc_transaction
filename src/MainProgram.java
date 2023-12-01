import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainProgram {
    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;

        try {

            connection = DB.getConnection();

            connection.setAutoCommit(false); //aguardando uma confirmação do commit

            statement = connection.createStatement();

            int rows1 = statement.executeUpdate("UPDATE seller SET BaseSalary = 2070 WHERE DepartmentId = 1");

            int x = 1;

            /*if (x<2) {
                throw new SQLException("error");  //este if força uma exception para validar o connection.commit() abaixo
            }*/

            int rows2 = statement.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            connection.commit(); //o bloco acima está protegido pelo commit, que será realizado somente se não ocorrer nenhuma falha.

            System.out.println("Rows 1: " + rows1);
            System.out.println("Rows 2: " + rows2);


        }catch (SQLException e) {
            try {
                connection.rollback();
                throw new DbException("Transaction rolled back. Caused by: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbException("Error trying to rollback. Caused by: " + e1.getMessage());
            }


        }
    }
}
