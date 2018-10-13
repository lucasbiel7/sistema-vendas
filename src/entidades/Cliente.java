/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Lucas Gabriel
 */
public abstract class Cliente {

    private int id;
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public abstract String imprimirDados();

    @Override
    public String toString() {
        return String.format("Cliente\nId: %d\nStatus: %s", getId(), status.getDescricao());
    }

}
