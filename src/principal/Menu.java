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
import exceptions.DescontoInvalidoException;
import exceptions.LimiteCreditoInvalidoException;
import exceptions.PercentualImpostoException;
import exceptions.PrazoEntregaInvalidoException;
import exceptions.ValorAbertoInvalidoException;
import exceptions.ValorAcrescimoInvalidoException;
import exceptions.ValorVendaInvalidoExeption;
import exceptions.VendaInvalidaException;
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
                case 0:
                    break;
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
                        try {
                            if (venda != null) {
                                venda.vendaValida();
                                System.out.printf("Venda %d registrada com sucesso!\n", venda.getId());
                                if (venda instanceof VendaAPrazo) {
                                    if (venda.getCliente() instanceof Pessoa) {
                                        Pessoa pessoa = (Pessoa) venda.getCliente();
                                        pessoa.setValorAberto(pessoa.getValorAberto() + venda.getValorTotal());
                                    }
                                }
                                vendas.add(venda);
                            }
                        } catch (VendaInvalidaException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("É necessário cadastrar clientes primeiramente!");
                    }
                    break;
                case 3:
                    imprimirClientes(clientes);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (op != 0);
        //Menu das vendas
        System.out.println("-----------------Gerenciar suas vendas-------------------------");
        do {
            System.out.println("1- Imprimir cupom");
            System.out.println("2- Vendas registradas");
            System.out.println("3- Consultar limite disponível");
            System.out.println("4 - Imprimir cliente");
            System.out.println("0- Sair");
            op = scannerNumerico.nextInt();
            switch (op) {
                case 0:
                    break;
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
                case 2:
                    imprimirVendas(vendas);
                case 3:
                    System.out.println("Digite o identificador do usuário");
                    id = scannerNumerico.nextInt();
                    Cliente cliente = clientes.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
                    if (cliente == null) {
                        System.out.println("Cliente não encontrado");
                    } else if (cliente instanceof Pessoa) {
                        System.out.printf("Limite de credito: %.2f\n", ((Pessoa) cliente).consultarLimiteDisponivel());
                    } else {
                        System.out.println("Esse cliente não possui limite de credito!");
                    }
                    break;
                case 4:
                    System.out.println("Digite o identificador do usuário");
                    id = scannerNumerico.nextInt();
                    cliente = clientes.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
                    System.out.println(cliente.imprimirDados());
                    break;
                default:
                    System.out.println("Opção inválida!");
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
        boolean invalido;
        do {
            invalido = false;
            System.out.println("Qual é o seu limite de credito?");
            try {
                pessoa.setLimiteCredito(scanner.nextDouble());
            } catch (LimiteCreditoInvalidoException e) {
                System.out.println(e.getMessage());
                invalido = true;
            }
        } while (invalido);
        do {
            invalido = false;
            System.out.println("Você possui algum valor em aberto?");
            try {
                pessoa.setValorAberto(scanner.nextDouble());
            } catch (ValorAbertoInvalidoException e) {
                System.out.println(e.getMessage());
                invalido = true;
            }
        } while (invalido);
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
        boolean invalido;
        do {
            invalido = false;
            System.out.println("Digite o valor do desconto?");
            try {
                venda.setDesconto(scannerNumerico.nextDouble());
            } catch (DescontoInvalidoException e) {
                System.out.println(e.getMessage());
                invalido = true;
            }
        } while (invalido);
        do {
            invalido = false;
            System.out.println("Digite o valor do percentual de imposto?");
            try {
                venda.setPercentualImposto(scannerNumerico.nextDouble());
            } catch (PercentualImpostoException e) {
                System.out.println(e.getMessage());
                invalido = true;
            }
        } while (invalido);
        do {
            invalido = false;
            System.out.println("Digite o prazo de entrega?");
            try {
                venda.setPrazoEntrega(scannerNumerico.nextInt());
            } catch (PrazoEntregaInvalidoException e) {
                System.out.println(e.getMessage());
                invalido = true;
            }
        } while (invalido);
        do {
            invalido = false;
            System.out.println("Digite o valor da venda?");
            try {
                venda.setValorVenda(scannerNumerico.nextDouble());
            } catch (ValorVendaInvalidoExeption e) {
                System.out.println(e.getMessage());
                invalido = true;
            }
        } while (invalido);
    }

    private static int geradorId(List<Integer> identificadores) {
        int id;
        do {
            id = new Random().nextInt(MAX_ID);
        } while (identificadores.contains(id));
        return id;
    }

    private static void perguntasVendaAPrazo(VendaAPrazo venda, Scanner scannerNumerico) {
        boolean invalido;
        do {
            invalido = false;
            System.out.println("Digite o valor do acrescimo?\n" + VALORES_NEGATIVOS);
            try {
                venda.setAcrescimo(scannerNumerico.nextDouble());
            } catch (ValorAcrescimoInvalidoException e) {
                System.out.println(e.getMessage());
                invalido = true;
            }
        } while (invalido);
    }

    private static void imprimirVendas(List<Venda> vendas) {
        for (Venda venda : vendas) {
            System.out.printf("Venda: %d Cliente: %d", venda.getId(), venda.getCliente().getId());
        }
    }
}
