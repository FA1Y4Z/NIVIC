import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class UsuarioDAO {

    public void cadastrar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios (nome, email, senha)
                VALUES (?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());

            comando.executeUpdate();

            ResultSet resultado = comando.getGeneratedKeys();

            if (resultado.next()) {
                int id = resultado.getInt(1);
                usuario.setId(id);
            }

            System.out.println("Usuário salvo no banco!");
        } catch (SQLException e) {

            System.out.println("Erro ao cadastrar usuário no banco!");
            e.printStackTrace();
        }
    }

    public void listar() {

        String sql = "SELECT * FROM usuarios";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String email = resultado.getString("email");

                System.out.println("\nID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Email: " + email);
            }

        } catch (SQLException e) {

            System.out.println("Erro ao listar usuários!");
            e.printStackTrace();
        }
    }
}