import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    private Scanner scanner;
    private Menu menu;
    private Calculo calculo;

    private ArrayList<Usuario> usuarios;
    private ArrayList<Receita> receitas;
    private ArrayList<Despesa> despesas;

    public Controller(Scanner scanner) {

        this.scanner = scanner;
        this.menu = new Menu(scanner);
        this.calculo = new Calculo();

        usuarios = new ArrayList<>();
        receitas = new ArrayList<>();
        despesas = new ArrayList<>();
    }

    public void iniciar() {

        int opcao;

        do {

            opcao = menu.mostrarMenu();

            switch (opcao) {

                case 1:
                    cadastrarUsuario();
                    break;

                case 2:
                    if (usuarios.isEmpty()) {
                        System.out.println("cadastre o usuario");
                    } else {
                        cadastrarReceita();
                    }
                    break;

                case 3:
                    if (usuarios.isEmpty()) {
                        System.out.println("cadastre o usuario");
                    } else {
                        cadastrarDespesa();
                    }
                    break;

                case 4:
                    if (receitas.isEmpty()) {
                        System.out.println("Nao ha receitas cadastradas");
                    } else {
                        listarReceitas();
                    }

                    break;

                case 5:
                    if (despesas.isEmpty()) {
                        System.out.println("Nao ha despesas cadastradas");
                    } else {
                        listarDespesas();
                    }
                    break;

                case 6:
                    verSaldo();
                    break;

                case 0:
                    System.out.println("\nSaindo do Nico...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrarUsuario() {

        System.out.println("\n============================");
        System.out.println("      CADASTRAR USUÁRIO");
        System.out.println("============================");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        int id = usuarios.size() + 1;

        Usuario usuario = new Usuario(
                id,
                nome,
                email,
                senha
        );

        usuarios.add(usuario);

        System.out.println("\nUsuário cadastrado com sucesso!");
        System.out.println("ID: " + usuario.getId());
    }

    private void cadastrarReceita() {

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

        int id = receitas.size() + 1;

        Receita receita = new Receita(
                id,
                descricao,
                valor,
                tipo,
                data
        );

        receitas.add(receita);

        System.out.println("\nReceita cadastrada com sucesso!");
    }

    private void cadastrarDespesa() {

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

        int id = despesas.size() + 1;

        Despesa despesa = new Despesa(
                id,
                descricao,
                valor,
                categoria,
                data
        );

        despesas.add(despesa);

        System.out.println("\nDespesa cadastrada com sucesso!");
    }

    private void listarReceitas() {

        System.out.println("\n============================");
        System.out.println("          RECEITAS");
        System.out.println("============================");

        if (receitas.isEmpty()) {
            System.out.println("Nenhuma receita cadastrada.");
            return;
        }

        for (Receita receita : receitas) {

            System.out.println("\nID: " + receita.getId());
            System.out.println("Descrição: " + receita.getDescricao());
            System.out.println("Valor: R$ " + receita.getValor());
            System.out.println("Tipo: " + receita.getTipo());
            System.out.println("Data: " + receita.getData());
        }
    }

    private void listarDespesas() {

        System.out.println("\n============================");
        System.out.println("          DESPESAS");
        System.out.println("============================");

        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa cadastrada.");
            return;
        }

        for (Despesa despesa : despesas) {

            System.out.println("\nID: " + despesa.getId());
            System.out.println("Descrição: " + despesa.getDescricao());
            System.out.println("Valor: R$ " + despesa.getValor());
            System.out.println("Categoria: " + despesa.getCategoria());
            System.out.println("Data: " + despesa.getData());
        }
    }

    private void verSaldo() {

        double totalReceitas = 0;
        double totalDespesas = 0;

        for (Receita receita : receitas) {
            totalReceitas += receita.getValor();
        }

        for (Despesa despesa : despesas) {
            totalDespesas += despesa.getValor();
        }

        double saldo = calculo.calcularSaldo(
                totalReceitas,
                totalDespesas
        );

        System.out.println("\n============================");
        System.out.println("           SALDO");
        System.out.println("============================");

        System.out.printf("Total de receitas: R$ %.2f%n", totalReceitas);
        System.out.printf("Total de despesas: R$ %.2f%n", totalDespesas);
        System.out.printf("Saldo atual: R$ %.2f%n", saldo);
    }
}