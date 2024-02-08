package com.ceica.modelos;

import java.util.ArrayList;
import java.util.List;

public class Rol extends ModeloBase{
    private static int idRol = 1;
    private int id;
    private String description;

    public Rol() {
        //this.id = idRol++;
    }

    public Rol(String description) {
        this.id = idRol++;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public static List<Rol> getRolesBD() {
        List<Rol> rolList = new ArrayList<>();
        List<Object> objectList = new Rol().leerTodos();
        for (Object obj : objectList) {
            Object[] objects = (Object[]) obj;
            Rol rol = new Rol();
            rol.setId((int) objects[0]);
            rol.setDescription((String) objects[1]);
            rolList.add(rol);
        }
        return rolList;
    }

    @Override
    protected String getNombreTabla() {
        return "roles";
    }

    @Override
    protected String getVista() {
        return null;
    }
}
