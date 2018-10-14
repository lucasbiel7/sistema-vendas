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
        this.prazoEntrega = prazoEntrega;
    }

    public double getPercentualImposto() {
        return percentualImposto;
    }

    public void setPercentualImposto(double percentualImposto) {
        this.percentualImposto = percentualImposto;
        atualizarValorTotal();
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
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
        this.valorVenda = valorVenda;
        atualizarValorTotal();
    }

    public double calcularDesconto() {
        return valorVenda * desconto / 100;
    }

    public double calcularImposto() {
        return valorVenda * percentualImposto / 100;
    }

    public boolean vendaValida() {
        return getValorTotal() >= 0 && getValorVenda() >= 0 && getPrazoEntrega() >= 0
                && getPercentualImposto() >= 0 && getDesconto() >= 0;
    }

    public abstract String imprimirCupom();

    public abstract void atualizarValorTotal();

    @Override
    public String toString() {
        return String.format("Valor da venda: %.2f\nDesconto: %.2f\nImposto: %.2f\nValor total: %.2f \nPrazo de entrega: %d\n%s\n", getValorVenda(), calcularDesconto(), calcularImposto(), getValorTotal(), getPrazoEntrega(), cliente);
    }
}
