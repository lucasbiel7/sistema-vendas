/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author aluno
 */
public class Governo extends Cliente {
    
    private String cnpj;
    private String razaoSocial;
    private String nomeContato;

    public Governo() {
        
    }

    @Override
    public double getValorTotal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
