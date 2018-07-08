package com.alfainmo.beans;

public class Referencia extends BeanObject {
    private int oficina;
    private int codigo;

    public void setOficina(int oficina) {
        this.oficina = oficina;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getOficina() {
        return oficina;
    }

    public int getCodigo() {
        return codigo;
    }
}
