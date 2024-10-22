package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        UserService testService = new UserServiceImpl();

        testService.createUsersTable();

        testService.saveUser("Глеб", "LastName1", (byte) 20);
        System.out.println("User с именем – Глеб добавлен в базу данных");

        testService.saveUser("Среда", "Пупкин", (byte) 25);
        System.out.println("User с именем – Среда добавлен в базу данных");

        testService.saveUser("Олег", "LastName3", (byte) 31);
        System.out.println("User с именем – Олег добавлен в базу данных");

        testService.saveUser("Жаба", "LastName4", (byte) 54);
        System.out.println("User с именем – Жаба добавлен в базу данных");

        List<User> testList = testService.getAllUsers();

        for (User user : testList) {
            System.out.println(user);
        }

        testService.cleanUsersTable();

        testService.dropUsersTable();

    }
}