import java.util.Scanner;

public class Menu {

    private Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public int mostrarMenuInicial() {

        System.out.println("\n============================");
        System.out.println("           NIVIC");
        System.out.println("============================");
        System.out.println("1 - Login");
        System.out.println("2 - Cadastrar usuário");
        System.out.println("0 - Sair");
        System.out.println("============================");

        System.out.print("Escolha: ");

        return scanner.nextInt();
    }

    public int mostrarMenuUsuario(Usuario usuario) {

        System.out.println("\n============================");
        System.out.println("           NIVIC");
        System.out.println("============================");
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("============================");
        System.out.println("1 - Cadastrar receita");
        System.out.println("2 - Cadastrar despesa");
        System.out.println("3 - Listar receitas");
        System.out.println("4 - Listar despesas");
        System.out.println("5 - Ver saldo");
        System.out.println("6 - Listar usuários");
        System.out.println("7 - Logout");
        System.out.println("0 - Sair");
        System.out.println("============================");

        System.out.print("Escolha: ");

        return scanner.nextInt();
    }
}