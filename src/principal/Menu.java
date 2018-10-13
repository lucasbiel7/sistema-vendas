/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import entidades.Cliente;
import entidades.Empresa;
import entidades.Fisica;
import entidades.Governo;
import entidades.Juridica;
import entidades.Pessoa;
import entidades.Status;
import entidades.Venda;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author lucas
 */
public class Menu {

    public static void main(String[] args) {
        int op;
        Scanner scanner = new Scanner(System.in);
        List<Cliente> clientes = new ArrayList<>();
        List<Venda> vendas = new ArrayList<>();
        do {
            System.out.println("1 - Inserir cliente");
            System.out.println("2 - Inserir venda");
            System.out.println("0 - Sair");
            op = scanner.nextInt();
            switch (op) {
                case 1:
                    int tipoCliente;
                    do {
                        System.out.println("Tipo do cliente: ");
                        System.out.println("1 - Cliente físico");
                        System.out.println("2 - Cliente Juridico");
                        System.out.println("3 - Cliente Governo");
                        tipoCliente = scanner.nextInt();
                    } while (tipoCliente > 3 || tipoCliente < 1);

                    switch (tipoCliente) {
                        case 1:
                            Fisica fisica = new Fisica();
                            perguntasFisica(fisica, scanner);
                            perguntasPadraoPessoa(fisica, scanner);
                            perguntasPadraoCliente(fisica, scanner, clientes);
                            break;
                        case 2:
                            Juridica juridica = new Juridica();
                            juridica.setEmpresa(perguntasEmpresa(scanner));
                            perguntasPadraoPessoa(juridica, scanner);
                            perguntasPadraoCliente(juridica, scanner, clientes);
                            break;
                        case 3:
                            Governo governo = new Governo();
                            governo.setEmpresa(perguntasEmpresa(scanner));
                            perguntasGoverno(governo, scanner);
                            perguntasPadraoCliente(governo, scanner, clientes);
                            break;
                    }
                    break;
                case 2:
                    break;
            }
        } while (op != 0);
    }

    public static void perguntasFisica(Fisica fisica, Scanner scanner) {
        System.out.println("Digite seu nome");
        fisica.setNome(scanner.nextLine());
        System.out.println("Digite seu CPF");
        fisica.setCpf(scanner.nextLine());
    }

    public static void perguntasPadraoCliente(Cliente cliente, Scanner scanner, List<Cliente> clientes) {
        char status;
        scanner.nextLine();
        do {
            System.out.println("Qual status do cliente? \nA - Ativo\nI - Invativo");

            status = scanner.nextLine().toUpperCase().charAt(0);
            System.out.println(status);
        } while (status != 'A' && status != 'I');
        cliente.setStatus(status == 'A' ? Status.ATIVO : Status.INATIVO);
        int id;
        do {
            id = new Random().nextInt();
            System.out.println(id);
        } while (clientes.stream().map(Cliente::getId).collect(Collectors.toList()).contains(id));
        cliente.setId(id);
        System.out.printf("Seu id é %d\n", id);
    }

    public static void perguntasPadraoPessoa(Pessoa pessoa, Scanner scanner) {
        do {
            System.out.println("Qual é o seu limite de credito?\nNão é permitido valores negativos");
            pessoa.setLimiteCredito(scanner.nextDouble());
        } while (pessoa.getValorAberto() < 0);

        do {
            System.out.println("Você possui algum valor em aberto?\nNão é permitido valores negativos");
            pessoa.setValorAberto(scanner.nextDouble());
        } while (pessoa.getValorAberto() < 0);
    }

    private static Empresa perguntasEmpresa(Scanner scanner) {
        Empresa empresa = new Empresa();
        System.out.println("Qual é sua Razão social?");
        empresa.setRazaoSocial(scanner.nextLine());
        System.out.println("");
        empresa.setCnpj(scanner.nextLine());
        return empresa;
    }

    private static void perguntasGoverno(Governo governo, Scanner scanner) {
        System.out.println("Digite o nome do contato?");
        governo.setNomeContato(scanner.nextLine());
    }
}
