package com.ceica.modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Task extends ModeloBase{
    private int id;
    private String title, description;
    private boolean status;
    private LocalDateTime creation_time, deadline;
    private User user;

    public Task() {
        //this.id = idTask++;
    }

    public Task(String title, String description, LocalDateTime deadline, User user) {
        this.title = title;
        this.description = description;
        this.status = false;
        this.creation_time = LocalDateTime.now();
        this.deadline = deadline;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(LocalDateTime creation_time) {
        this.creation_time = creation_time;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", creation_time=" + creation_time +
                ", deadline=" + deadline +
                '}';
    }

    @Override
    protected String getNombreTabla() {
        return "tasks";
    }

    @Override
    protected String getVista() {
        return "users_tasks";
    }

    public static List<Task> getTasksBD() {
        List<Task> taskList = new ArrayList<>();
        List<Object> objectList = new Task().leerTodos();
        for (Object obj : objectList) {
            Object[] objects = (Object[]) obj;
            Task task = new Task();
            User user = new User();
            Rol rol = new Rol();
            task.setId((int) objects[0]);
            task.setTitle((String) objects[1]);
            task.setDescription((String) objects[2]);
            task.setCreation_time((LocalDateTime) objects[3]);
            task.setDeadline((LocalDateTime) objects[4]);
            task.setStatus((Integer) objects[5]!=0);
            user.setId((int) objects[6]);
            user.setUsername((String) objects[7]);
            user.setPassword((String) objects[8]);
            rol.setId((int) objects[9]);
            rol.setDescription((String) objects[10]);
            user.setRol(rol);
            task.setUser(user);
            taskList.add(task);
        }
        return taskList;
    }

    public List<Task> getByUser(int id) {
        List<Task> taskList = Task.getTasksBD();
        return taskList.stream()
                .filter(task -> id == task.getUser().getId())
                .collect(Collectors.toList());
    }
}
