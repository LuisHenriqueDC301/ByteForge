package com.AEDS3.ByteForge.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.AEDS3.ByteForge.collections.*;
import com.AEDS3.ByteForge.models.Cliente;

import jakarta.annotation.PostConstruct;

@Service
public class MenuClientes {

    Arquivo<Cliente> arqClientes;
    private static Scanner console = new Scanner(System.in);

    @PostConstruct
    public void init() {
        try {
            arqClientes = new Arquivo<>("clientes", Cliente.class.getConstructor());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar Arquivo<Cliente>", e);
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            clientes = arqClientes.readAll(); // Lê todos os clientes do arquivo
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
            } else {
                System.out.println("\nLista de Clientes:");
                for (Cliente cliente : clientes) {
                    mostraCliente(cliente); // Exibe os detalhes de cada cliente
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public void buscarCliente() {
        System.out.print("\nID do cliente: ");
        int id = console.nextInt(); // Lê o ID digitado pelo usuário
        console.nextLine(); // Limpar o buffer após o nextInt()

        if (id > 0) {
            try {
                Cliente cliente = arqClientes.read(id); // Chama o método de leitura da classe Arquivo
                if (cliente != null) {
                    mostraCliente(cliente); // Exibe os detalhes do cliente encontrado
                } else {
                    System.out.println("Cliente não encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível buscar o cliente!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    public Cliente incluirCliente(Cliente cliente) {
        try {
            arqClientes.create(cliente);
            System.out.println("Cliente incluído com sucesso.");
            return cliente; // Retorna o cliente incluído
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível incluir o cliente!");
            return null;
        }
    }

    public boolean alterarCliente(int id, Cliente clienteNovo) {
        if (id > 0) {
            try {
                // Tenta ler o cliente com o ID fornecido
                Cliente cliente = arqClientes.read(id);
                if (cliente != null) {
                    System.out.println("Cliente encontrado:");
                    mostraCliente(cliente);  // Exibe os dados do cliente para confirmação
                        boolean alterado = arqClientes.update(clienteNovo);
                            if (alterado) {
                                return true; // Retorna true se a alteração foi bem-sucedida
                        } else {
                            return false; // Retorna false se a alteração falhou
                        }
                    }
                else {
                    System.out.println("Cliente não encontrado.");
                    return false; // Retorna false se o cliente não foi encontrado
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível alterar o cliente!");
                e.printStackTrace();
            }
        }
        return false; // Retorna false se o ID for inválido
    }

    public boolean excluirCliente(int id) {
        if (id > 0) {
            try {
                // Tenta ler o cliente com o ID fornecido
                Cliente cliente = arqClientes.read(id);
                if (cliente != null) {
                    System.out.println("Cliente encontrado:");
                    mostraCliente(cliente); // Exibe os dados do cliente para confirmação

                        boolean excluido = arqClientes.delete(id); // Chama o método de exclusão no arquivo
                        if (excluido) {
                            System.out.println("Cliente excluído com sucesso.");
                            return true; // Retorna true se a exclusão foi bem-sucedida
                        } else {
                            System.out.println("Erro ao excluir o cliente.");
                            return false; // Retorna false se a exclusão falhou
                        }
                } else {
                    System.out.println("Cliente não encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível excluir o cliente!");
                e.printStackTrace();
            }
        }
        return false; // Retorna false se o ID for inválido ou se ocorrer um erro
    }

    public void mostraCliente(Cliente cliente) {
        if (cliente != null) {
            System.out.println("\nDetalhes do Cliente:");
            System.out.println("----------------------");
            System.out.printf("Nome......: %s%n", cliente.nome);
            System.out.printf("CPF.......: %s%n", cliente.cpf);
            System.out.printf("Salário...: R$ %.2f%n", cliente.salario);
            System.out.printf("Nascimento: %s%n", cliente.nascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("----------------------");
        }
    }
}
