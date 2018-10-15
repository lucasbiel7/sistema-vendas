/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author lucas
 */
public class ValorVendaInvalidoExeption extends RuntimeException {

    public ValorVendaInvalidoExeption(String msg) {
        super(msg);
    }

}
