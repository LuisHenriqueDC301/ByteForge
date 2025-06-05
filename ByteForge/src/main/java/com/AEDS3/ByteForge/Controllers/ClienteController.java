package com.AEDS3.ByteForge.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AEDS3.ByteForge.models.Cliente;
import com.AEDS3.ByteForge.services.MenuClientes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final MenuClientes menuClientes;

    @Autowired
    public ClienteController(MenuClientes menuClientes) {
        this.menuClientes = menuClientes;
    }

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return menuClientes.listarClientes();
    }

    @PostMapping("/adicionar")
    public Cliente adicionar(@RequestBody Cliente entity) {
        try {
            menuClientes.incluirCliente(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar cliente: " + e.getMessage(), e);
        }
        return entity;
    }
    
    @DeleteMapping("/remover")
    public ResponseEntity<String> remover(@RequestParam int id) {
        try {
            if(menuClientes.excluirCliente(id)){
                return new ResponseEntity<>("Cliente removido com sucesso.", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao remover cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("alterar/{id}")
    public Cliente alterarCliente(@PathVariable String id, @RequestBody Cliente entity) {
        //TODO: process PUT request
        try{
            Cliente cliente = menuClientes.alterarCliente(Integer.parseInt(id), entity);
            return cliente;
        }catch(Exception e){
            return null;
        }
    }

    
}