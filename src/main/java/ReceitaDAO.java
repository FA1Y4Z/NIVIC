import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReceitaDAO {

    public boolean cadastrar(Receita receita, int usuarioId) {

        String sql = """
                INSERT INTO receitas (descricao, valor, tipo, data, usuario_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            comando.setString(1, receita.getDescricao());
            comando.setDouble(2, receita.getValor());
            comando.setString(3, receita.getTipo());
            comando.setString(4, receita.getData());
            comando.setInt(5, usuarioId);

            comando.executeUpdate();

            ResultSet resultado = comando.getGeneratedKeys();

            if (resultado.next()) {

                receita.setId(resultado.getInt(1));

                System.out.println("Receita salva no banco!");

                return true;
            }

        } catch (SQLException e) {

            System.out.println("Erro ao cadastrar receita no banco!");
            e.printStackTrace();
        }

        return false;
    }

    public void listar(int usuarioId) {

        String sql = """
                SELECT *
                FROM receitas
                WHERE usuario_id = ?
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, usuarioId);

            ResultSet resultado = comando.executeQuery();

            if (!resultado.next()) {
                System.out.println("Nenhuma receita cadastrada.");
                return;
            }

            do {

                int id = resultado.getInt("id");
                String descricao = resultado.getString("descricao");
                double valor = resultado.getDouble("valor");
                String tipo = resultado.getString("tipo");
                String data = resultado.getString("data");

                System.out.println("\nID: " + id);
                System.out.println("Descrição: " + descricao);
                System.out.printf("Valor: R$ %.2f%n", valor);
                System.out.println("Tipo: " + tipo);
                System.out.println("Data: " + data);

            } while (resultado.next());

        } catch (SQLException e) {

            System.out.println("Erro ao listar receitas!");
            e.printStackTrace();
        }
    }

    public double calcularTotal(int usuarioId) {

        String sql = """
                SELECT COALESCE(SUM(valor), 0)
                FROM receitas
                WHERE usuario_id = ?
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, usuarioId);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                return resultado.getDouble(1);
            }

        } catch (SQLException e) {

            System.out.println("Erro ao calcular total de receitas!");
            e.printStackTrace();
        }

        return 0;
    }
}