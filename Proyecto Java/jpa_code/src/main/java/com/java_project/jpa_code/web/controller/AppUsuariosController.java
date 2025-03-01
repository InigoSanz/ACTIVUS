package com.java_project.jpa_code.web.controller;

import com.java_project.jpa_code.config.details.SuperCustomerUserDetails;
import com.java_project.jpa_code.dto.Email;
import com.java_project.jpa_code.dto.UsersDto;
import com.java_project.jpa_code.dto.UsersDtoPsw;
import com.java_project.jpa_code.model.Users;
import com.java_project.jpa_code.service.EmailService;
import com.java_project.jpa_code.service.RolesService;
import com.java_project.jpa_code.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.java_project.jpa_code.util.CadenaAleatoria;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para las vistas de usuarios.
 */
@Controller
public class AppUsuariosController extends AbstractController<UsersDto> {

    private final UsersService service;
    private final RolesService roleService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AppUsuariosController(UsersService service, RolesService roleService) {
        super();
        this.service = service;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String Home() {
        return "index";
    }

    @GetMapping("/index")
    public String indexHome() {
        return "index";
    }

    @GetMapping("/contact")
    public String contacto() {
        return "contact";
    }

    @GetMapping("/logout")
    public String salir() {
        return "logout";
    }

    @GetMapping("/proyect")
    public String proyecto() {
        return "proyect";
    }

    /**
     * Método que muestra la vista de términos y condiciones.
     * Contiene seguridad para mostrar el nombre del usuario,
     * para el usuario anónimo se muestra un nombre genérico.
     *
     * @param interfazConPantalla Interfaz con la pantalla.
     * @return Vista de términos y condiciones.
     */
    @GetMapping("/termsandconditions")
    public String vistaTermAndConditions(ModelMap interfazConPantalla) {
        String userName = "no informado";
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            userName = "anonimo@anonimo";
        } else {
            userName = ((SuperCustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        }

        return "termsandconditions";
    }

    /**
     * Método que muestra la vista de lista de usuarios.
     *
     * @param page Número de página.
     * @param size Tamaño de la página.
     * @param interfazConPantalla Modelo.
     * @return Vista de lista de usuarios.
     */
    @GetMapping("/users")
    public String vistaUsuarios(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                ModelMap interfazConPantalla) {

        Integer pagina = page.orElse(1) - 1;
        Integer maxelementos = size.orElse(10);

        Page<UsersDto> usuarioDtoPage = this.service.buscarTodos(PageRequest.of(pagina, maxelementos));
        System.out.println("Elementos encontrados en vistaUsuarios");
        System.out.println(usuarioDtoPage);

        interfazConPantalla.addAttribute(pageNumbersAttributeKey, dameNumPaginas(usuarioDtoPage));
        interfazConPantalla.addAttribute("listausuarios", usuarioDtoPage);
        return "users/listaUsuariosPagina";
    }

    /**
     * Método que muestra la vista de datos de un usuario.
     * Contiene seguridad para mostrar los datos del usuario.
     *
     * @param id Identificador del usuario.
     * @param interfazConPantalla Modelo.
     * @return Vista de datos de un usuario.
     */
    @GetMapping("/users/{idusr}")
    public String vistaDatosUsuario(@PathVariable("idusr") Integer id, ModelMap interfazConPantalla) {
        Integer userId = ((SuperCustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserID();
        Optional<Users> usuario = service.getRepo().findById(userId);

        if (usuario.isPresent()) {
            if (userId.equals(id) || usuario.get().getRol().getRolName().equals("ROLE_ADMIN")) { // Si el usuario es el mismo o es un administrador
                Optional<UsersDto> usuarioDto = this.service.encuentraPorId(id); // Buscar usuario por id, optional para evitar null

                // Si el usuario existe, mostrar los datos
                if (usuarioDto.isPresent()) {
                    UsersDto attr = usuarioDto.get();
                    interfazConPantalla.addAttribute("datosUsuario", attr);
                    return "users/edit";
                } else {
                    return "users/datosNoEncontrados";
                }
            } else {
                return "users/datosNoEncontrados";
            }
        } else {
            return "users/datosNoEncontrados";
        }
    }

    /**
     * Método que guarda la edición de datos de un usuario.
     *
     * @param id Identificador del usuario.
     * @param usuarioDtoEntrada Datos del usuario.
     * @return Redirección a la vista de datos del usuario.
     * @throws Exception En caso de error.
     */
    @PostMapping("/users/{idusr}")
    public String guardarEdicionDatosUsuario(@PathVariable("idusr") Integer id, UsersDto usuarioDtoEntrada) throws Exception {

        // Hay que tener cuidado ya que la contraseña no viene en el objeto
        // Hay que copiar la información que viene excepto la contraseña
        // Con el ID se busca el usuario
        Optional<UsersDto> usuarioDtoControl = this.service.encuentraPorId(id);

        if (usuarioDtoControl.isPresent()) {

            // Se llama al método de guardar, se le pasa el objeto con la información
            UsersDto usuarioDtoGuardar = new UsersDto();
            usuarioDtoGuardar.setId(id);
            usuarioDtoGuardar.setEmail(usuarioDtoEntrada.getEmail());
            usuarioDtoGuardar.setNombreUsuario(usuarioDtoEntrada.getNombreUsuario());

            // Se busca el usuario por ID
            Optional<Users> usuario = service.encuentraPorIdEntity(id);
            if (usuario.isPresent()) {
                this.service.guardar(usuarioDtoGuardar, usuario.get().getPassword());
            } else {
                this.service.guardar(usuarioDtoGuardar);
            }

            // Redirigir a la vista de la lista de usuarios
            return "redirect:/users";
        } else {
            // Se muestra la vista de datos no encontrados
            return "users/datosNoEncontrados";
        }
    }

    /**
     * Método de la lista de usuarios para eliminar un usuario.
     *
     * @param id Identificador del usuario.
     * @return Redirección a la vista de lista de usuarios.
     */
    @PostMapping("/users/{idusr}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarDatosUsuario(@PathVariable("idusr") Integer id) {

        // Con el ID se busca el registro a nivel de entidad
        Optional<UsersDto> usuarioDto = this.service.encuentraPorId(id);

        if (usuarioDto.isPresent()) {
            // Eliminar el usuario
            this.service.eliminarPorId(id);
            return "redirect:/users";
        } else {
            return "users/datosNoEncontrados";
        }
    }

    /**
     * Método de la lista de usuarios para habilitar o deshabilitar un usuario.
     *
     * @param id Identificador del usuario.
     * @return Redirección a la vista de lista de usuarios.
     */
    @PostMapping("/users/{idusr}/habilitar")
    @PreAuthorize("hasRole('ADMIN')")
    public String habilitarDatosUsuario(@PathVariable("idusr") Integer id) {

        Optional<Users> usuario = this.service.encuentraPorIdEntity(id);

        if (usuario.isPresent()) {
            Users attr = usuario.get();
            attr.setActive(!attr.isActive());
            this.service.getRepo().save(attr);
            return "redirect:/users";
        } else {
            return "users/datosNoEncontrados";
        }
    }

    /**
     * Método que muestra la vista para restablecer la contraseña.
     *
     * @param intefrazConPantalla Modelo.
     * @return Vista para restablecer la contraseña.
     */
    @GetMapping("/users/hasOlvidadoTuPassword")
    public String hasOlvidadoTuPassword(ModelMap intefrazConPantalla) {
        // Se obtiene el objeto de la clase UsersDtoPsw
        UsersDtoPsw usersDtoPsw = new UsersDtoPsw();
        intefrazConPantalla.addAttribute("datos", usersDtoPsw);
        return "users/emailResetearPassword";
    }

    /**
     * Método que envía un correo para restablecer la contraseña.
     *
     * @param dto Datos del usuario.
     * @param lang Idioma.
     * @param model Modelo.
     * @return Redirección a la vista de correo enviado para restablecer la contraseña.
     */
    @PostMapping("/users/hasOlvidadoTuPassword")
    public String  hasOlvidadoTuPasswordPOst(@ModelAttribute  UsersDtoPsw  dto,
                                             @RequestParam(value = "lang", required = false) String lang,
                                             Model model) {
        // Se obtiene el idioma
        String language = "es";
        if (lang!= null) {
            language = lang;
        }
        var locale = new Locale(language);
        var messages = ResourceBundle.getBundle("messages", locale);

        // Si el nombre de usuario no es nulo, se busca el usuario
        if (dto.getNombreUsuario() != null){

            Optional<Users> usuario = service.getRepo().findByNombreUsuarioAndActiveTrue(dto.getNombreUsuario()); // Buscar usuario por nombre de usuario

            // Si el usuario existe, se envía un correo con el token
            if (usuario.isPresent()) {
                String token = usuario.get().getToken();
                System.out.println("------------------------- --------------"+ dto.getNombreUsuario() + " token: "  + token);

                Email correoCambioContrasenia = new Email();
                correoCambioContrasenia.setFrom("notificaciones@appactivus.com");
                correoCambioContrasenia.setTo(usuario.get().getEmail());
                correoCambioContrasenia.setSubject("Cambio de contraseña");
                correoCambioContrasenia.setContent("http://localhost:8082/users/resetpass/" + dto.getNombreUsuario() +"/" + token);

                emailService.sendMail(correoCambioContrasenia);

            } else {
                model.addAttribute("errorMessage","El usuario no existe");
                return "redirect:/users/hasOlvidadoTuPassword/" + dto.getNombreUsuario();
            }

            return "users/emailEnviadoParaCambioPass";
        }
        model.addAttribute("errorMessage","El usuario no existe");
        return "redirect:/users/hasOlvidadoTuPassword/" + dto.getNombreUsuario();
    }

    /**
     * Método que muestra la vista de restablecer la contraseña.
     * El token se utiliza para garantizar la seguridad.
     *
     * @param username Nombre de usuario.
     * @param token Token.
     * @param intefrazConPantalla Modelo.
     * @return Vista de restablecer la contraseña.
     */
    @GetMapping("/users/resetpass/{username}/{token}")
    public String cambiopass(@PathVariable("username") String username, @PathVariable("token") String token, ModelMap intefrazConPantalla) {

        Optional<Users> usuario = service.getRepo().findByNombreUsuarioAndTokenAndActiveTrue(username,token ); // Buscar usuario por nombre de usuario y token
        System.out.println(username + ":" + token );
        UsersDtoPsw usuarioCambioPsw = new UsersDtoPsw();

        // Si el usuario existe, se muestra la vista de restablecer la contraseña
        // Si no existe, se muestra la vista de datos no encontrados
        if (usuario.isPresent()){
            usuarioCambioPsw.setId(usuario.get().getId());
            usuarioCambioPsw.setNombreUsuario(username);
            usuarioCambioPsw.setEmail(usuario.get().getEmail());
            usuarioCambioPsw.setPassword("******************"); // Se oculta la contraseña
            usuarioCambioPsw.setNewpassword("******************"); // Se oculta la contraseña
            intefrazConPantalla.addAttribute("datos", usuarioCambioPsw);
            return "users/resetearPassword";
        }else {

            //Mostrar página usuario no existe
            return "users/datosNoEncontrados";
        }
    }

    /**
     * Método que guarda la nueva contraseña.
     *
     * @param dto Datos del usuario.
     * @param lang Idioma.
     * @param redirectAttributes Atributos de redirección.
     * @return Redirección a la vista de inicio de sesión.
     * @throws Exception En caso de error.
     */
    @PostMapping("/users/resetpass")
    public String saveUserPasswMod(@ModelAttribute UsersDtoPsw dto,
                                   @RequestParam(value = "lang", required = false) String lang,
                                   RedirectAttributes redirectAttributes) throws Exception {

        // Se obtiene el idioma
        System.out.println("saveUserPasswMod");
        String step = "10";
        String language = "es";
        if (lang != null) {
            language = lang;
        }
        var locale = new Locale(language);
        step = "20";
        System.out.println("saveUserPasswMod: " + step);
        var messages = ResourceBundle.getBundle("messages", locale);
        // Si las contraseñas coinciden
        if (dto.getPassword().equals(dto.getNewpassword())) {
            step = "30";
            System.out.println("saveUserPasswMod: " + step);
            // Buscar datos del usuario por su id
            Optional<Users> usuario = service.getRepo().findById((int) dto.getId());
            if (usuario.isPresent()) {
                step = "40";
                Users user = usuario.get();
                // Actualiza la contraseña después de codificarla
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                // Modificar el token por seguridad
                String newtoken = CadenaAleatoria.obtenerToken(250);
                user.setToken(newtoken);
                step = "50";
                System.out.println("saveUserPasswMod: " + step);
                // Guardar el usuario
                Users usuarioguarado = service.guardarEntidadEntidad(user);
                step = "60";
                System.out.println("saveUserPasswMod: " + step);
                Email correoCambioContrasenia = new Email();
                correoCambioContrasenia.setFrom(messages.getString("principal.email.from"));
                correoCambioContrasenia.setTo(user.getEmail());
                correoCambioContrasenia.setSubject(messages.getString("principal.email.subject1"));
                correoCambioContrasenia.setContent(messages.getString("principal.email.content1") + dto.getEmail());
                step = "70";
                System.out.println("saveUserPasswMod: " + step);
                emailService.sendMail(correoCambioContrasenia);
                redirectAttributes.addFlashAttribute("successMessage", messages.getString("cambio.password.success"));
                return "redirect:/users/login";

            } else {
                return "users/datosNoEncontrados";
            }

        } else {
            return "users/resetearPassword";
        }
    }

    /**
     * Método que muestra la vista de login de usuario.
     * Muestra un mensaje de éxito si el usuario ha iniciado sesión correctamente.
     *
     * @param successMessage Mensaje de éxito.
     * @param model Modelo.
     * @return Vista de login de usuario.
     */
    @GetMapping("/users/login")
    public String vistaLogin(@RequestParam(value = "successMessage", required = false) String successMessage, ModelMap model) {
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }
        return "users/login";
    }

    @GetMapping("/users/register")
    public String vistaRegistro() {
        return "users/register";
    }

    /**
     * Método para mostrar un error en el login.
     *
     * @param request Petición.
     * @param model Modelo.
     * @return Vista de login de usuario.
     */
    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;

        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        return "users/login";
    }

    @GetMapping("/aplicacion/medicamentos")
    public String medicamentos() {
        return "aplicacion/medicamentos";
    }

    @GetMapping("/aplicacion/comparador")
    public String comparador() {
        return "aplicacion/comparador";
    }

    @GetMapping("/aplicacion/pacientes")
    public String pacientes() {
        return "aplicacion/pacientes";
    }
}