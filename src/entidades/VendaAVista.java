/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author lucas
 */
public class VendaAVista extends Venda {

    @Override
    public String imprimirCupom() {
        return String.format("Venda a Vista\n%s", super.toString());
    }

    @Override
    public void atualizarValorTotal() {
        valorTotal = getValorVenda() + calcularImposto() - calcularDesconto();
    }

}
