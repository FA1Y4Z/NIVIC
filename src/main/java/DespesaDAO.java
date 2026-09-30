import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DespesaDAO {

    public boolean cadastrar(Despesa despesa, int usuarioId) {

        String sql = """
                INSERT INTO despesas (descricao, valor, categoria, data, usuario_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            comando.setString(1, despesa.getDescricao());
            comando.setDouble(2, despesa.getValor());
            comando.setString(3, despesa.getCategoria());
            comando.setString(4, despesa.getData());
            comando.setInt(5, usuarioId);

            comando.executeUpdate();

            ResultSet resultado = comando.getGeneratedKeys();

            if (resultado.next()) {

                despesa.setId(resultado.getInt(1));

                System.out.println("Despesa salva no banco!");

                return true;
            }

        } catch (SQLException e) {

            System.out.println("Erro ao cadastrar despesa no banco!");
            e.printStackTrace();
        }

        return false;
    }

    public void listar(int usuarioId) {

        String sql = """
                SELECT *
                FROM despesas
                WHERE usuario_id = ?
                """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, usuarioId);

            ResultSet resultado = comando.executeQuery();

            if (!resultado.next()) {
                System.out.println("Nenhuma despesa cadastrada.");
                return;
            }

            do {

                int id = resultado.getInt("id");
                String descricao = resultado.getString("descricao");
                double valor = resultado.getDouble("valor");
                String categoria = resultado.getString("categoria");
                String data = resultado.getString("data");

                System.out.println("\nID: " + id);
                System.out.println("Descrição: " + descricao);
                System.out.printf("Valor: R$ %.2f%n", valor);
                System.out.println("Categoria: " + categoria);
                System.out.println("Data: " + data);

            } while (resultado.next());

        } catch (SQLException e) {

            System.out.println("Erro ao listar despesas!");
            e.printStackTrace();
        }
    }

    public double calcularTotal(int usuarioId) {

        String sql = """
                SELECT COALESCE(SUM(valor), 0)
                FROM despesas
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

            System.out.println("Erro ao calcular total de despesas!");
            e.printStackTrace();
        }

        return 0;
    }
}