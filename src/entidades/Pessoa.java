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
public abstract class Pessoa extends Cliente {

    public double limiteCredito;
    public double valorAberto;

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public double getValorAberto() {
        return valorAberto;
    }

    public void setValorAberto(double valorAberto) {
        this.valorAberto = valorAberto;
    }

    public double consultarLimiteDisponivel() {
        return getLimiteCredito() - getValorAberto();
    }

    @Override
    public String toString() {
        return String.format("%s\nLimite de credito: %.2f\nValor em aberto: %.2f\nLimite disponível: %.2f", super.toString(), limiteCredito, valorAberto, consultarLimiteDisponivel());
    }

}
