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
public class Governo extends Cliente {

    private Empresa empresa;
    private String nomeContato;

    public Governo() {

    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    @Override
    public String imprimirDados() {
        return toString();
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\nNome do Contato: %s", super.toString(), empresa, nomeContato);
    }

}
