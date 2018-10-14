/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import exceptions.ClienteInvalidoException;

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
        this.acrescimo = acrescimo;
        atualizarValorTotal();
    }

    public double calcularAcrescimo() {
        return getValorVenda() * acrescimo / 100;
    }

    @Override
    public String imprimirCupom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizarValorTotal() {
        valorTotal = getValorVenda() + calcularAcrescimo() - calcularDesconto() + calcularImposto();
    }

    @Override
    public boolean vendaValida() {
        return super.vendaValida() && !(getCliente() instanceof Governo)
                && getCliente().getStatus().equals(Status.ATIVO)
                && ((Pessoa) getCliente()).getLimiteCredito() - getValorTotal() >= 0;
    }

}
