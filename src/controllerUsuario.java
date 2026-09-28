import java.util.ArrayList;
import java.util.Scanner;

public class controllerUsuario {


    private Scanner scanner;
    private Menu menu;

    private ArrayList<Usuario> usuarios;

    public controllerUsuario(Scanner scanner) {

        this.scanner = scanner;
        this.menu = new Menu(scanner);

        usuarios = new ArrayList<>();
    }


}
