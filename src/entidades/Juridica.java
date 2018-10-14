/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Lucas Gabriel
 *
 */
public class Juridica extends Pessoa {

    private Empresa empresa;

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public String imprimirDados() {
        return toString();
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", super.toString(), empresa).replace("{tipo}", "Pessoa Jurídica");
    }

}
