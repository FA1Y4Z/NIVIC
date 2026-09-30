import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    private Scanner scanner;
    private Menu menu;
    private Calculo calculo;

    private ArrayList<Usuario> usuarios;

    private UsuarioDAO usuarioDAO;
    private ReceitaDAO receitaDAO;
    private DespesaDAO despesaDAO;
    private LoginDAO loginDAO;

    private Usuario usuarioAtual;

    public Controller(Scanner scanner) {

        usuarioDAO = new UsuarioDAO();
        receitaDAO = new ReceitaDAO();
        despesaDAO = new DespesaDAO();
        loginDAO = new LoginDAO();

        this.scanner = scanner;
        this.menu = new Menu(scanner);
        this.calculo = new Calculo();

        usuarios = new ArrayList<>();
    }

    public void iniciar() {

        boolean executando = true;

        while (executando) {

            if (usuarioAtual == null) {

                int opcao = menu.mostrarMenuInicial();

                switch (opcao) {

                    case 1:
                        fazerLogin();
                        break;

                    case 2:
                        cadastrarUsuario();
                        break;

                    case 0:
                        executando = false;
                        System.out.println("\nSaindo do NIVIC...");
                        break;

                    default:
                        System.out.println("\nOpção inválida!");
                }

            } else {

                int opcao = menu.mostrarMenuUsuario(usuarioAtual);

                switch (opcao) {

                    case 1:
                        cadastrarReceita();
                        break;

                    case 2:
                        cadastrarDespesa();
                        break;

                    case 3:
                        listarReceitas();
                        break;

                    case 4:
                        listarDespesas();
                        break;

                    case 5:
                        verSaldo();
                        break;

                    case 6:
                        usuarioDAO.listar();
                        break;

                    case 7:
                        fazerLogout();
                        break;

                    case 0:
                        executando = false;
                        System.out.println("\nSaindo do NIVIC...");
                        break;

                    default:
                        System.out.println("\nOpção inválida!");
                }
            }
        }
    }

    private void fazerLogin() {

        scanner.nextLine();

        System.out.println("\n============================");
        System.out.println("            LOGIN");
        System.out.println("============================");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = loginDAO.autenticar(email, senha);

        if (usuario != null) {

            usuarioAtual = usuario;

            System.out.println("\nLogin realizado com sucesso!");
            System.out.println("Bem-vindo, " + usuario.getNome() + "!");

        } else {

            System.out.println("\nEmail ou senha incorretos.");
        }
    }

    private void fazerLogout() {

        usuarioAtual = null;

        System.out.println("\nLogout realizado com sucesso!");
    }

    private void cadastrarUsuario() {

        scanner.nextLine();

        System.out.println("\n============================");
        System.out.println("      CADASTRAR USUÁRIO");
        System.out.println("============================");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = new Usuario(
                nome,
                email,
                senha
        );

        usuarioDAO.cadastrar(usuario);

        usuarios.add(usuario);

        System.out.println("\nUsuário cadastrado com sucesso!");
        System.out.println("ID: " + usuario.getId());

        System.out.println("\nAgora você pode fazer login.");
    }

    private void cadastrarReceita() {

        scanner.nextLine();

        System.out.println("\n============================");
        System.out.println("       CADASTRAR RECEITA");
        System.out.println("============================");

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Tipo: ");
        String tipo = scanner.nextLine();

        System.out.print("Data: ");
        String data = scanner.nextLine();

        Receita receita = new Receita(
                descricao,
                valor,
                tipo,
                data
        );

        boolean cadastrou = receitaDAO.cadastrar(
                receita,
                usuarioAtual.getId()
        );

        if (cadastrou) {

            System.out.println("\nReceita cadastrada com sucesso!");
            System.out.println("ID: " + receita.getId());

        } else {

            System.out.println("\nNão foi possível cadastrar a receita.");
        }
    }

    private void cadastrarDespesa() {

        scanner.nextLine();

        System.out.println("\n============================");
        System.out.println("       CADASTRAR DESPESA");
        System.out.println("============================");

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        System.out.print("Data: ");
        String data = scanner.nextLine();

        Despesa despesa = new Despesa(
                0,
                descricao,
                valor,
                categoria,
                data
        );

        boolean cadastrou = despesaDAO.cadastrar(
                despesa,
                usuarioAtual.getId()
        );

        if (cadastrou) {

            System.out.println("\nDespesa cadastrada com sucesso!");
            System.out.println("ID: " + despesa.getId());

        } else {

            System.out.println("\nNão foi possível cadastrar a despesa.");
        }
    }

    private void listarReceitas() {

        System.out.println("\n============================");
        System.out.println("          RECEITAS");
        System.out.println("============================");

        receitaDAO.listar(usuarioAtual.getId());
    }

    private void listarDespesas() {

        System.out.println("\n============================");
        System.out.println("          DESPESAS");
        System.out.println("============================");

        despesaDAO.listar(usuarioAtual.getId());
    }

    private void verSaldo() {

        int usuarioId = usuarioAtual.getId();

        double totalReceitas =
                receitaDAO.calcularTotal(usuarioId);

        double totalDespesas =
                despesaDAO.calcularTotal(usuarioId);

        double saldo = calculo.calcularSaldo(
                totalReceitas,
                totalDespesas
        );

        System.out.println("\n============================");
        System.out.println("           SALDO");
        System.out.println("============================");

        System.out.printf(
                "Total de receitas: R$ %.2f%n",
                totalReceitas
        );

        System.out.printf(
                "Total de despesas: R$ %.2f%n",
                totalDespesas
        );

        System.out.printf(
                "Saldo atual: R$ %.2f%n",
                saldo
        );
    }
}