package com.ceica.taskapp.viewcontroller;

import com.ceica.taskapp.controller.AppController;

public abstract class ViewController {
    protected AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
        cargaInicial();
    }

    public abstract void cargaInicial();
}
