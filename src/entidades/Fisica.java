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
public class Fisica extends Pessoa {
    
    private String nome;
    private String cpf;
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    @Override
    public String imprimirDados() {
        return toString();
    }
    
    @Override
    public String toString() {
        return String.format("%s\nNome: %s\nCPF: %s", super.toString(), nome, cpf).replace("{tipo}", "Pessoa Física");
    }
    
}
