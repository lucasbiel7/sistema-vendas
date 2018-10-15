/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import exceptions.LimiteCreditoInvalidoException;
import exceptions.ValorAbertoInvalidoException;

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
        if (limiteCredito < 0) {
            throw new LimiteCreditoInvalidoException("Não é permitido inserir valores menores que 0 no limite de credito!");
        }
        this.limiteCredito = limiteCredito;
    }

    public double getValorAberto() {
        return valorAberto;
    }

    public void setValorAberto(double valorAberto) {
        if (valorAberto < 0) {
            throw new ValorAbertoInvalidoException("Não é permitido que o valor em aberto seja menor do que 0!");
        }
        if (valorAberto > limiteCredito) {
            throw new ValorAbertoInvalidoException("Não é permitido ter um valor aberto maior que o limite de credito!");
        }
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
