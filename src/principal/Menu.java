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
import entidades.VendaAPrazo;
import entidades.VendaAVista;
import exceptions.ClienteInvalidoException;
import java.io.IOException;
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

    public static final String VALORES_NEGATIVOS = "Não é permitido valores negativos";
    public static final int MAX_ID = 300;

    public static void main(String[] args) throws IOException, InterruptedException {
        int op;
        Scanner scanner = new Scanner(System.in);
        Scanner scannerNumerico = new Scanner(System.in);
        List<Cliente> clientes = new ArrayList<>();
        List<Venda> vendas = new ArrayList<>();
        //Menu dos cadastros
        do {
            System.out.println("1 - Inserir cliente");
            System.out.println("2 - Inserir venda");
            System.out.println("3 - Listar clientes");
            System.out.println("0 - Sair");
            op = scannerNumerico.nextInt();
            switch (op) {
                case 1:
                    int tipoCliente;
                    do {
                        System.out.println("Tipo do cliente: ");
                        System.out.println("1 - Cliente físico");
                        System.out.println("2 - Cliente Juridico");
                        System.out.println("3 - Cliente Governo");
                        tipoCliente = scannerNumerico.nextInt();
                    } while (tipoCliente > 3 || tipoCliente < 1);
                    Cliente cliente = null;
                    switch (tipoCliente) {
                        case 1:
                            cliente = new Fisica();
                            perguntasFisica((Fisica) cliente, scanner);
                            break;
                        case 2:
                            cliente = new Juridica();
                            ((Juridica) cliente).setEmpresa(perguntasEmpresa(scanner));
                            break;
                        case 3:
                            cliente = new Governo();
                            ((Governo) cliente).setEmpresa(perguntasEmpresa(scanner));
                            perguntasGoverno((Governo) cliente, scanner);
                            break;
                    }
                    if (cliente instanceof Pessoa) {
                        perguntasPadraoPessoa((Pessoa) cliente, scannerNumerico);
                    }
                    perguntasPadraoCliente(cliente, scanner, clientes);
                    clientes.add(cliente);
                    break;
                case 2:
                    if (!clientes.isEmpty()) {
                        int tipoVenda;
                        do {
                            System.out.println("Selecione o tipo de venda");
                            System.out.println("1 - Venda à vista");
                            System.out.println("2 - Venda à prazo");
                            tipoVenda = scannerNumerico.nextInt();
                        } while (tipoVenda != 1 && tipoVenda != 2);
                        Venda venda = null;
                        switch (tipoVenda) {
                            case 1:
                                venda = new VendaAVista();
                                break;
                            case 2:
                                venda = new VendaAPrazo();
                                perguntasVendaAPrazo((VendaAPrazo) venda, scannerNumerico);
                                break;
                        }
                        perguntasVenda(venda, clientes, scannerNumerico, vendas);
                        if (venda.vendaValida()) {
                            System.out.printf("Venda %d registrada com sucesso!\n", venda.getId());
                            if (venda instanceof VendaAPrazo) {
                                if (venda.getCliente() instanceof Pessoa) {
                                    Pessoa pessoa = (Pessoa) venda.getCliente();
                                    pessoa.setValorAberto(pessoa.getValorAberto() + venda.getValorTotal());
                                }
                            }
                            vendas.add(venda);
                        } else {
                            System.out.println("Parece que há algo incorreto nessa venda\n"
                                    + "Não foi possível continuar com o seu registro!");
                        }
                    } else {
                        System.out.println("É necessário cadastrar clientes primeiramente!");
                    }
                    break;
                case 3:
                    imprimirClientes(clientes);
                    break;
            }
        } while (op != 0);
        //Menu das vendas
        System.out.println("-----------------Gerenciar suas vendas-------------------------");
        do {
            System.out.println("1- Imprimir cupom");
            System.out.println("0- Sair");
            op = scannerNumerico.nextInt();
            switch (op) {
                case 1:
                    System.out.println("Digite o id da venda");
                    int id = scannerNumerico.nextInt();
                    Venda venda = vendas.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
                    if (venda != null) {
                        System.out.println(venda.imprimirCupom());

                    } else {
                        System.out.println("Não foi encontrado está venda!");
                    }
                    break;
                default:
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
        do {
            System.out.println("Qual status do cliente? \nA - Ativo\nI - Invativo");
            status = scanner.nextLine().toUpperCase().charAt(0);
            System.out.println(status);
        } while (status != 'A' && status != 'I');
        cliente.setStatus(status == 'A' ? Status.ATIVO : Status.INATIVO);
        cliente.setId(geradorId(clientes.stream().map(Cliente::getId).collect(Collectors.toList())));
        System.out.printf("Seu id é %d\n", cliente.getId());
    }

    public static void perguntasPadraoPessoa(Pessoa pessoa, Scanner scanner) {
        do {
            System.out.println("Qual é o seu limite de credito?\n" + VALORES_NEGATIVOS);
            pessoa.setLimiteCredito(scanner.nextDouble());
        } while (pessoa.getValorAberto() < 0);

        do {
            System.out.println("Você possui algum valor em aberto?\n" + VALORES_NEGATIVOS);
            pessoa.setValorAberto(scanner.nextDouble());
        } while (pessoa.getValorAberto() < 0);
    }

    private static Empresa perguntasEmpresa(Scanner scanner) {
        Empresa empresa = new Empresa();
        System.out.println("Qual é sua Razão social?");
        empresa.setRazaoSocial(scanner.nextLine());
        System.out.println("Qual é o seu CNPJ?");
        empresa.setCnpj(scanner.nextLine());
        return empresa;
    }

    private static void perguntasGoverno(Governo governo, Scanner scanner) {
        System.out.println("Digite o nome do contato?");
        governo.setNomeContato(scanner.nextLine());
    }

    private static void imprimirClientes(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            System.out.println(cliente.imprimirDados());
            System.out.println("---------------------------------------------------------------------------");
        }
    }

    private static void perguntasVenda(Venda venda, List<Cliente> clientes, Scanner scannerNumerico, List<Venda> vendas) {
        venda.setId(geradorId(vendas.stream().map(Venda::getId).collect(Collectors.toList())));
        do {
            System.out.println("Digite o id do cliente");
            int id = scannerNumerico.nextInt();
            try {
                venda.setCliente(clientes.stream().filter(t -> t.getId() == id).findFirst().orElse(null));
            } catch (ClienteInvalidoException e) {
                System.out.println(e.getMessage());
            }
        } while (venda.getCliente() == null);
        do {
            System.out.println("Digite o valor do desconto?\n" + VALORES_NEGATIVOS);
            venda.setDesconto(scannerNumerico.nextDouble());
        } while (venda.getDesconto() < 0);
        do {
            System.out.println("Digite o valor do percentual de imposto?\n" + VALORES_NEGATIVOS);
            venda.setPercentualImposto(scannerNumerico.nextDouble());
        } while (venda.getPercentualImposto() < 0);
        do {
            System.out.println("Digite o prazo de entrega?\n" + VALORES_NEGATIVOS);
            venda.setPrazoEntrega(scannerNumerico.nextInt());
        } while (venda.getPrazoEntrega() < 0);
        do {
            System.out.println("Digite o valor da venda?\n" + VALORES_NEGATIVOS);
            venda.setValorVenda(scannerNumerico.nextDouble());
        } while (venda.getValorVenda() < 0);
    }

    private static int geradorId(List<Integer> identificadores) {
        int id;
        do {
            id = new Random().nextInt(MAX_ID);
        } while (identificadores.contains(id));
        return id;
    }

    private static void perguntasVendaAPrazo(VendaAPrazo venda, Scanner scannerNumerico) {
        do {
            System.out.println("Digite o valor do acrescimo?\n" + VALORES_NEGATIVOS);
            venda.setAcrescimo(scannerNumerico.nextDouble());
        } while (venda.getAcrescimo() < 0);
    }
}
