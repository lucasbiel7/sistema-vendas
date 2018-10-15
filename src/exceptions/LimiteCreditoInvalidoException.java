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
public class LimiteCreditoInvalidoException extends RuntimeException {

    public LimiteCreditoInvalidoException(String mensagem) {
        super(mensagem);
    }

}
