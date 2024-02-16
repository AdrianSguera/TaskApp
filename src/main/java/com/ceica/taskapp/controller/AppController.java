package com.ceica.taskapp.controller;

import com.ceica.taskapp.modelos.Rol;
import com.ceica.taskapp.modelos.Task;
import com.ceica.taskapp.modelos.User;

import java.time.LocalDateTime;
import java.util.List;

public class AppController {
    private User userLogged;

    public AppController() {

    }

    public User getUserLogged() {
        return userLogged;
    }

    public boolean login(String usu, String pass) {
        this.userLogged = User.login(usu, pass);
        return userLogged != null;
    }

    public boolean isAdmin(User userLogged) {
        return userLogged.getRol().getId()==2;
    }

    public List<User> getUsers(){
        return User.getUsersBD();
    }

    public List<Task> getTasks(){
        return Task.getTasksBD();
    }

    public List<Task> getTasksByUser(){
        return new Task().getByUser(userLogged.getId());
    }

    public List<Rol> getRoles(){
        return Rol.getRolesBD();
    }

    public boolean newTask(String title, String description, LocalDateTime deadline){
        return new Task().insertar("(title,description,deadline,iduser) values (?,?,?,?)", title, description, deadline, userLogged.getId());
    }

    public boolean newUser(String username, String password, int idrol){
        return new User().insertar("(username,password,idrol) values (?,?,?)", username, password, idrol);
    }

    public boolean deleteTaskById(int idTask) {
        return new Task().eliminar("idtask=?", idTask);
    }

    public boolean deleteUserById(int idUser) {
        return new User().eliminar("iduser=?", idUser);
    }

    public boolean changeUsernameUser(int idUser, String dato) {
        return new User().modificar("username = ? WHERE iduser = ?", dato, idUser);
    }

    public boolean changePasswordUser(String username, String dato) {
        return new User().modificar("password = ? WHERE username = ?", dato, username);
    }

    public boolean changeRolUser(int idUser, int dato) {
        return new User().modificar("idrol = ? WHERE iduser = ?", dato, idUser);
    }

    public boolean changeTitleTask(int idTask, String dato) {
        return new Task().modificar("title = ? WHERE idtask = ?", dato, idTask);
    }

    public boolean changeDescriptionTask(int idTask, String dato) {
        return new Task().modificar("description = ? WHERE idtask = ?", dato, idTask);
    }

    public boolean changeCreationTimeTask(int idTask, LocalDateTime dato) {
        return new Task().modificar("create_time = ? WHERE idtask = ?", dato, idTask);
    }

    public boolean changeDeadlineTask(int idTask, LocalDateTime dato) {
        return new Task().modificar("deadline = ? WHERE idtask = ?", dato, idTask);
    }

    public boolean changeStatusTask(int idTask, boolean dato) {
        int truedato = 0;
        if (dato)
            truedato = 1;
        return new Task().modificar("status = ? WHERE idtask = ?", truedato, idTask);
    }

    public boolean changeUserTask(int idTask, int dato) {
        return new Task().modificar("iduser = ? WHERE idtask = ?", idTask);
    }


}
