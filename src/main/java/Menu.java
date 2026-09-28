import java.util.Scanner;

public class Menu {

    private Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public int mostrarMenu() {

        System.out.println("\n============================");
        System.out.println("           NIVIC");
        System.out.println("============================");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Cadastrar receita");
        System.out.println("3 - Cadastrar despesa");
        System.out.println("4 - Listar receitas");
        System.out.println("5 - Listar despesas");
        System.out.println("6 - Listar usuários");
        System.out.println("7 - Ver saldo");
        System.out.println("0 - Sair");
        System.out.println("============================");

        System.out.print("Escolha: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        return opcao;
    }
}