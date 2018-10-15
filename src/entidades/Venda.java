/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import exceptions.DescontoInvalidoException;
import exceptions.PercentualImpostoException;
import exceptions.PrazoEntregaInvalidoException;
import exceptions.ValorVendaInvalidoExeption;
import exceptions.VendaInvalidaException;

/**
 *
 * @author Lucas Gabriel
 *
 */
public abstract class Venda {

    private int id;
    private Cliente cliente;
    private int prazoEntrega;
    private double percentualImposto;
    private double valorVenda;
    private double desconto;
    protected double valorTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(int prazoEntrega) {
        if (prazoEntrega < 0) {
            throw new PrazoEntregaInvalidoException("Não é permitido prazo de entrega menor que 0!");
        }
        this.prazoEntrega = prazoEntrega;
    }

    public double getPercentualImposto() {
        return percentualImposto;
    }

    public void setPercentualImposto(double percentualImposto) {
        if (percentualImposto < 0 || percentualImposto > 100) {
            throw new PercentualImpostoException("Só é permitido valores entre 0 e 100% para percentual de imposto!");
        }
        this.percentualImposto = percentualImposto;
        atualizarValorTotal();
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        if (desconto < 0 || desconto > 100) {
            throw new DescontoInvalidoException("Só é permitido valores entre 0 e 100% para desconto!");
        }
        this.desconto = desconto;
        atualizarValorTotal();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        if (valorVenda < 0) {
            throw new ValorVendaInvalidoExeption("Não é permitido valor de venda menor que 0!");
        }
        this.valorVenda = valorVenda;
        atualizarValorTotal();
    }

    public double calcularDesconto() {
        return (valorVenda + calcularImposto()) * desconto / 100D;
    }

    public double calcularImposto() {
        return valorVenda * percentualImposto / 100D;
    }

    public void vendaValida() {
        if (getValorTotal() < 0) {
            throw new VendaInvalidaException("O valor total da venda possui valor negativo, por isso não é possível finalizar a venda!");
        }
    }

    public abstract String imprimirCupom();

    public abstract void atualizarValorTotal();

    @Override
    public String toString() {
        return String.format("Valor da venda: %.2f\nDesconto: %.2f\nImposto: %.2f\nValor total: %.2f \nPrazo de entrega: %d\n%s\n", getValorVenda(), calcularDesconto(), calcularImposto(), getValorTotal(), getPrazoEntrega(), cliente);
    }
}
