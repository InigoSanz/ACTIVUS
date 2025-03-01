package com.java_project.jpa_code;

import com.java_project.jpa_code.config.service.UserService;
import com.java_project.jpa_code.model.Roles;
import com.java_project.jpa_code.model.Users;
import com.java_project.jpa_code.repository.RolesRepository;
import com.java_project.jpa_code.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * Clase principal para la aplicación JPA Code.
 */
@SpringBootApplication
public class JpaCodeApplication {

	private static RolesRepository rolesRepository;
	private static UsersRepository usersRepository;
	private static UserService userService;
	private static Environment env;

	/**
	 * Constructor de JpaCodeApplication.
	 *
	 * @param rolesRepository Repositorio de roles.
	 * @param usersRepository Repositorio de usuarios.
	 * @param userService Servicio de usuario.
	 * @param env Entorno de la aplicación.
	 */
	@Autowired
	public JpaCodeApplication(RolesRepository rolesRepository, UsersRepository usersRepository,
							  UserService userService, Environment env) {
		JpaCodeApplication.rolesRepository = rolesRepository;
		JpaCodeApplication.usersRepository = usersRepository;
		JpaCodeApplication.userService = userService;
		JpaCodeApplication.env = env;
	}

	/**
	 * Método principal de la aplicación.
	 *
	 * @param args Argumentos de la línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(JpaCodeApplication.class, args);
		String dbInitialize = env.getProperty("execution.mode");
		System.out.println("db_initialize: " + dbInitialize);
		if (Objects.equals(dbInitialize, "1")) {
			shouldStoreRoles();
			shouldStoreUsers();
		}
	}

	/**
	 * Método para almacenar roles predefinidos en la base de datos.
	 */
	public static void shouldStoreRoles() {
		Roles rolesAdmin = new Roles();
		rolesAdmin.setRolName("ROLE_ADMIN");
		rolesAdmin.setRolName_01("Administrator");
		rolesAdmin.setRolName_02("Administrator");
		rolesAdmin.setRolName_03("Administrator");
		rolesAdmin.setRolName_es("Administrador");
		rolesAdmin.setRolName_en("Administrator");
		rolesAdmin.setShowOnCreate(0);
		rolesRepository.save(rolesAdmin);

		Roles rolesAnonymous = new Roles();
		rolesAnonymous.setId(2);
		rolesAnonymous.setRolName("ROLE_ANONIMOUS");
		rolesAnonymous.setRolName_01("Anonymous");
		rolesAnonymous.setRolName_02("Anonymous");
		rolesAnonymous.setRolName_03("Anonymous");
		rolesAnonymous.setRolName_en("Anonymous");
		rolesAnonymous.setRolName_es("Anónimo");
		rolesAnonymous.setShowOnCreate(0);
		rolesRepository.save(rolesAnonymous);

		Roles rolesUser = new Roles();
		rolesUser.setId(3);
		rolesUser.setRolName("ROLE_USER");
		rolesUser.setRolName_01("Medical Specialist");
		rolesUser.setRolName_02("Medical Specialist");
		rolesUser.setRolName_03("Medical Specialist");
		rolesUser.setRolName_es("Medico Especialista");
		rolesUser.setRolName_en("Medical Specialist");
		rolesUser.setShowOnCreate(1);
		rolesRepository.save(rolesUser);
	}

	/**
	 * Método para almacenar usuarios predefinidos en la base de datos.
	 */
	public static void shouldStoreUsers() {
		Roles rolesAdmin = rolesRepository.findByRolName("ROLE_ADMIN");
		Roles rolesAnonymous = rolesRepository.findByRolName("ROLE_ANONIMOUS");
		Roles rolesUser = rolesRepository.findByRolName("ROLE_USER");

		Users adminUser = new Users();
		adminUser.setEmail("i.sanzdelg@gmail.com"); // Sería admin@appactivus.com
		adminUser.setNombreUsuario("administrator");
		adminUser.setPassword(userService.getEncodedPasswordstr("Admin1234."));
		adminUser.setRol(rolesAdmin);
		usersRepository.save(adminUser);

		Users anonymousUser = new Users();
		anonymousUser.setEmail("anonimo@appactivus.com"); // Sería anonimo@appactivus.com
		anonymousUser.setNombreUsuario("anonimo");
		anonymousUser.setPassword("");
		anonymousUser.setRol(rolesAnonymous);
		usersRepository.save(anonymousUser);

		Users medicoUser = new Users();
		medicoUser.setEmail("medico@appactivus.com"); // Sería medico@appactivus.com
		medicoUser.setNombreUsuario("medico");
		medicoUser.setPassword(userService.getEncodedPasswordstr("Medico1234."));
		medicoUser.setRol(rolesUser);
		usersRepository.save(medicoUser);
	}
}
