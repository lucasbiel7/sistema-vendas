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
public abstract class Pessoa extends Cliente{
    
    public double limiteCredito;
    public double valorAberto;

    protected double getLimiteCredito() {
        return limiteCredito;
    }

    protected void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    protected double getValorAberto() {
        return valorAberto;
    }

    protected void setValorAberto(double valorAberto) {
        this.valorAberto = valorAberto;
    }
    
    public double consultarLimiteDisponivel(){
        return getLimiteCredito()-getValorAberto();
    }
    
    
}
