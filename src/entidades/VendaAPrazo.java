/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import exceptions.ClienteInvalidoException;
import exceptions.ValorAcrescimoInvalidoException;
import exceptions.VendaInvalidaException;

/**
 *
 * @author lucas
 */
public class VendaAPrazo extends Venda {

    private double acrescimo;

    @Override
    public void setCliente(Cliente cliente) {
        if (cliente instanceof Governo) {
            throw new ClienteInvalidoException("Não é permitido clientes do tipo governo para compras a prazo!");
        }
        super.setCliente(cliente);
    }

    public double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(double acrescimo) {
        if (acrescimo < 0 || acrescimo > 100) {
            throw new ValorAcrescimoInvalidoException("Não é permitido valores de acrescimo menor que 0 e maior que 100%!");
        }
        this.acrescimo = acrescimo;
        atualizarValorTotal();
    }

    public double calcularAcrescimo() {
        return getValorVenda() * acrescimo / 100;
    }

    @Override
    public String imprimirCupom() {
        return String.format("Venda a prazo\n%s\n", toString());
    }

    @Override
    public void atualizarValorTotal() {
        valorTotal = getValorVenda() + calcularAcrescimo() - calcularDesconto() + calcularImposto();
    }

    @Override
    public void vendaValida() {
        super.vendaValida();
        if (getCliente().getStatus().equals(Status.INATIVO)) {
            throw new VendaInvalidaException("Não é permitido realizar venda para clientes invativos!");
        }
        if (getCliente() instanceof Governo) {
            throw new VendaInvalidaException("Não é permitido realizar venda a prazo para clientes governamentais");
        }
        if (((Pessoa) getCliente()).consultarLimiteDisponivel() - getValorTotal() < 0) {
            throw new VendaInvalidaException("Não há limite de credito disponivel para realizar a transação!");
        }
    }

    @Override
    public String toString() {
        return String.format("%s\nAcrescimo: %.2f", super.toString(), calcularAcrescimo());
    }
}
