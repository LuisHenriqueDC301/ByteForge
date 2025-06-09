# ByteForge

## 🧠 O que o trabalho faz?

O projeto **ByteForge** é uma API REST desenvolvida com **Spring Boot**, baseada em um Sistema Gerenciador de Banco de Dados (SGBD) feito à mão, originalmente implementado na disciplina de Algoritmos e Estruturas de Dados III (AEDs 3). A proposta do trabalho foi adaptar o funcionamento da SGBD — construída com estruturas como árvores, tabelas hash e manipulação de arquivos binários — para o contexto de uma aplicação moderna orientada a serviços web.

Com isso, o sistema passou a permitir que as operações de CRUD (criação, leitura, atualização e remoção) fossem feitas por meio de requisições HTTP, utilizando JSON como formato de entrada e saída.


---

## 📦 Estrutura e Classes

### `Main.java`
- Classe principal da aplicação Spring Boot.

### `controller/ClienteController.java`
Controla as requisições HTTP referentes ao recurso `Cliente`.

#### Endpoints disponíveis:

- `GET /clientes/listar`  
  Retorna a lista de todos os clientes cadastrados.

- `POST /clientes/adicionar`  
  Adiciona um novo cliente (JSON no corpo da requisição).

- `DELETE /clientes/remover?id=1`  
  Remove o cliente com o ID especificado.

- `PUT /clientes/alterar/{id}`  
  Atualiza os dados do cliente com o ID informado (JSON no corpo).

### `services/MenuClientes.java`
- Camada responsável por orquestrar a lógica do CRUD.
- Utiliza internamente a SGBD feita à mão.

#### Principais métodos:
- `incluirCliente(Cliente cliente)`
- `listarClientes()`
- `excluirCliente(int id)`
- `alterarCliente(int id, Cliente cliente)`

### `models/Cliente.java`
Classe de modelo com os seguintes campos:
- `id`, `nome`, `email`, `telefone`, entre outros.
- Métodos getters/setters, `toString()`.

### `dados/`
Pasta responsável pelo armazenamento dos arquivos binários que compõem a base de dados persistente do sistema.

---

## 💬 Experiência de Desenvolvimento

O desenvolvimento do ByteForge foi uma experiência desafiadora e enriquecedora. O projeto exigiu a adaptação de uma lógica previamente feita em Java puro para um modelo de API REST utilizando Spring Boot. Enfrentamos questões como:

- Transformar a entrada/saída de dados de interações via console para JSON.
- Integrar classes manuais com as anotações e ciclos de vida do Spring.
- Garantir que as estruturas (árvores, hash, arquivos binários) funcionassem corretamente com chamadas HTTP assíncronas.

Todas as funcionalidades esperadas foram implementadas com sucesso. O resultado é um sistema funcional, robusto e didático, que une os conceitos de estrutura de dados com os princípios de desenvolvimento web moderno.

---

## ✅ Checklist

A interface web (HTML, CSS e JavaScript) foi criada? SIM

O CRUD foi ajustado para incorporar a Spring Boot? SIM

As operações do CRUD podem ser usadas via a interface web? SIM

Há um tutorial explicando como criar uma interface web para um CRUD? SIM

O trabalho está funcionando corretamente? SIM

O trabalho está completo? SIM

O trabalho é original e não a cópia de um trabalho de um colega? SIM
