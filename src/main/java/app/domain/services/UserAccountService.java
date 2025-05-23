package app.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.adapters.owner.entity.OwnerEntity;
import app.adapters.owner.repository.OwnerRepository;
import app.domain.models.Person;
import app.domain.models.UserAccount;
import app.domain.types.Role;
import app.ports.PersonPort;
import app.ports.UserAccountPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserAccountService {
    @Autowired
    private UserAccountPort userPort;
    @Autowired
    private PersonPort personPort;
    @Autowired
    private OwnerRepository ownerRepository;


    private UserAccount currentUser; //Usuario actualmente logeado(Autenticado)

    //Se logea el usuario y se valida que exista en la base de datos
    public UserAccount login(UserAccount user) throws Exception {
            UserAccount userValidate= userPort.findByUserName(user.getUserName());
            if(userValidate== null) {
                log.error("Usuario o contraseña invalida");
                throw new Exception("Usuario o contraseña invalido");
            }
            if(!user.getPassword().equals(userValidate.getPassword())) {
                log.error("Usuario o contraseña invalida");
                throw new Exception("Usuario o contraseña invalido");
            }
            currentUser= userValidate;
            log.info("Inicio de sesion exitoso");
            return userValidate;
        }
    
    public void saveUser(UserAccount user) {
    	if (user == null) {
            log.error("No se puede guardar un usuario nulo");
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        userPort.saveUser(user);
        log.info("Usuario guardado exitosamente: {}", user.getUserName());
    	
    }

    //Se cierra la sesion del usuario actual
    public void logout() {
        if(currentUser!= null) {
            currentUser= null;
            log.info("Cierre de sesion exitoso");
        }else {
            log.error("No hay ninguna sesion iniciada");
        }
    }

    //Verificamos si la sesion esta iniciada
    public boolean isLogged() {
        return currentUser!= null;
    }

    //Registra un nuevo usuario
    public void registerUser(UserAccount user,Person person) throws Exception {
         if(userPort.findByUserName(user.getUserName())!= null) {
            log.error("El nombre de usuario ya existe");
            throw new Exception("El nombre de usuario ya existe");
        }
        if(personPort.existPerson(person.getDocument())) {
            log.error("La persona ya existe");
            throw new Exception("La persona ya existe");
        }
        log.info("Guardando persona...");
        personPort.savePerson(person);
        log.info("Persona guardada");

        log.info("Guardando usuario...");
        userPort.registerUser(user, person);
        log.info("Usuario guardado");

        if (person.getRole() == Role.USER) {
            log.info("Guardando owner...");
            ownerRepository.save(new OwnerEntity(person));
            log.info("Owner guardado");
        }
        log.info("Usuario registrado exitosamente");
    }

    //Cambia la contraseña del usuario actual
    public void changePassword(String username,String oldPassword, String newPassword) throws Exception {
        UserAccount user= userPort.findByUserName(username);
        if(user== null) {
            log.error("Usuario no encontrado");
            throw new Exception("Usuario no encontrado");
        }
        if(!user.getPassword().equals(oldPassword)) {
            log.error("Contraseña incorrecta");
            throw new Exception("Contraseña incorrecta");
        }
        user.setPassword(newPassword);
        userPort.saveUser(user);
        log.info("Contraseña cambiada exitosamente");
    }

    //Elimina a un usuario por su username
    @Transactional
    public void deleteUser(Long document) throws Exception {
        UserAccount user= userPort.findByDocument(document);
        if(user== null) {
            log.error("Usuario no encontrado");
            throw new Exception("Usuario no encontrado");
        }
        // Eliminar de Owner solo si existe y el rol es USER
        if (user.getRole() == Role.USER && ownerRepository.existsByDocument(document)) {
            ownerRepository.deleteByDocument(document);
            log.info("Usuario eliminado de Owner exitosamente");
        }
        // Eliminar de UserAccount
        userPort.deleteUser(document);
        log.info("Usuario eliminado de UserAccount exitosamente");
        // Eliminar de Person
        personPort.deleteByDocument(document);
        log.info("Usuario eliminado de Person exitosamente");
        log.info("Usuario eliminado exitosamente");
    }

    //Obtenemos el usuario que esta logeado
    public UserAccount getCurrentUser() {
        if(isLogged()){
            log.info("Usuario actualmente en sesion: "+currentUser.getUserName());
            return currentUser;
        }
        log.error("No hay ninguna sesion iniciada");
        return null;
    }

    //Buscar usuario por su username
    public UserAccount findByUserName(String username) {
        UserAccount user= userPort.findByUserName(username);
        if(user== null) {
            log.error("Usuario no encontrado");
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        log.info("Usuario {} encontrado",username);
        return user;
    }

    //Listamos todos los usuarios registrados
    public List<UserAccount> findAllUsers() {
        List<UserAccount> users= userPort.findAllUsers();
        log.info("Usuarios registrados: {}",users.size());
        return users;
    }

    public UserAccount findByDocument(Long document) {
        UserAccount user = userPort.findByDocument(document);
        if (user == null) {
            log.error("Usuario no encontrado con documento: {}", document);
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        log.info("Usuario encontrado con documento: {}", document);
        return user;
    }

}
