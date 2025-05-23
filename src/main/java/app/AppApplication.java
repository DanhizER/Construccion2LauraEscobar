package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import app.console.MenuConsola;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		/*ConfigurableApplicationContext context = SpringApplication.run(AppApplication.class, args);
		MenuConsola menu = context.getBean(MenuConsola.class);
        menu.mostrarMenu();*/

		SpringApplication.run(AppApplication.class, args);
	}

}
