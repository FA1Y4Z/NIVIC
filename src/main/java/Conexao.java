import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mariadb://localhost:3306/nivic";
    private static final String USUARIO = "nivic";
    private static final String SENHA = "nivic123";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                SENHA
        );
    }
}