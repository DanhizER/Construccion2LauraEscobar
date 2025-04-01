package app.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.UserAccount;
import app.ports.UserAccountPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserAccountService {
    @Autowired
    private UserAccountPort userPort;

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
    public void saveUser(UserAccount user) throws Exception {
        if(userPort.findByUserName(user.getUserName())!= null) {
            log.error("El nombre de usuario ya existe");
            throw new Exception("El nombre de usuario ya existe");
        }
        userPort.saveUser(user);
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
    public void deleteUser(Long document) throws Exception {
        UserAccount user= userPort.findByDocument(document);
        if(user== null) {
            log.error("Usuario no encontrado");
            throw new Exception("Usuario no encontrado");
        }
        userPort.deleteUser(document);
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

}
