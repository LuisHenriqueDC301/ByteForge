(() => {
  const clientForm = document.getElementById('clientForm');
  const clientModalLabel = document.getElementById('clientModalLabel');
  const clientIdInput = document.getElementById('clientId');
  const clientNameInput = document.getElementById('clientName');
  const clientCpfInput = document.getElementById('clientCpf');
  const clientSalarioInput = document.getElementById('clientSalario');
  const clientNascimentoInput = document.getElementById('clientNascimento');
  const clientIdCategoriaInput = document.getElementById('clientCategoria');
  const clientsTableBody = document.getElementById('clientsTableBody');
  const noClientsMessage = document.getElementById('noClientsMessage');
  const searchInput = document.getElementById('searchInput');
  const btnAddClient = document.getElementById('btnAddClient');

  let clients = [];

  const API_BASE = 'http://localhost:8080/clientes';

  async function loadClients() {
    try {
      const response = await fetch(`${API_BASE}/listar`);
      if (!response.ok) throw new Error('Erro ao carregar clientes');
      clients = await response.json();
    } catch (error) {
      console.error(error);
      clients = [];
    }
  }

  function renderClients(filter = '') {
    clientsTableBody.innerHTML = '';
    const filtered = clients.filter(c =>
      c.nome.toLowerCase().includes(filter.toLowerCase()) ||
      c.cpf.includes(filter) ||
      String(c.salario).includes(filter) ||
      (c.nascimento && c.nascimento.includes(filter)) ||
      String(c.idCategoria).includes(filter)
    );
    if (filtered.length === 0) {
      noClientsMessage.style.display = 'block';
      return;
    } else {
      noClientsMessage.style.display = 'none';
    }
    filtered.forEach(client => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${client.nome}</td>
        <td>${client.cpf}</td>
        <td>${client.salario.toFixed(2)}</td>
        <td>${client.nascimento}</td>
        <td>${client.idCategoria}</td>
        <td class="text-center" style="width: 150px;">
          <button class="btn btn-sm btn-warning me-2 btn-edit" data-id="${client.id}" title="Editar">Editar</button>
          <button class="btn btn-sm btn-danger btn-delete" data-id="${client.id}" title="Excluir">Excluir</button>
        </td>
      `;
      clientsTableBody.appendChild(tr);
    });
    bindActionButtons();
  }

  function bindActionButtons() {
    document.querySelectorAll('.btn-edit').forEach(button => {
      button.onclick = () => {
        const id = button.getAttribute('data-id');
        openEditModal(id);
      }
    });

    document.querySelectorAll('.btn-delete').forEach(button => {
      button.onclick = () => {
        const id = button.getAttribute('data-id');
        if (confirm('Deseja realmente excluir este cliente?')) {
          deleteClient(id);
        }
      }
    });
  }

  btnAddClient.addEventListener('click', () => {
    clientModalLabel.textContent = 'Novo Cliente';
    clientIdInput.value = '';
    clientNameInput.value = '';
    clientCpfInput.value = '';
    clientSalarioInput.value = '';
    clientNascimentoInput.value = '';
    clientIdCategoriaInput.value = '';
  });

  function openEditModal(id) {
    const client = clients.find(c => c.id == id);
    if (!client) return;
    clientModalLabel.textContent = 'Editar Cliente';
    clientIdInput.value = client.id;
    clientNameInput.value = client.nome;
    clientCpfInput.value = client.cpf;
    clientSalarioInput.value = client.salario;
    clientNascimentoInput.value = client.nascimento;
    clientIdCategoriaInput.value = client.idCategoria;
    const modal = new bootstrap.Modal(document.getElementById('clientModal'));
    modal.show();
  }

  clientForm.addEventListener('submit', async e => {
    e.preventDefault();

    const id = clientIdInput.value;
    const nome = clientNameInput.value.trim();
    const cpf = clientCpfInput.value.trim(); // sem máscara, só número
    const salario = parseFloat(clientSalarioInput.value);
    const nascimento = clientNascimentoInput.value;
    const idCategoria = parseInt(clientIdCategoriaInput.value);

    const clientData = { nome, cpf, salario, nascimento, idCategoria };

    try {
      if (id) {
        const response = await fetch(`${API_BASE}/alterar/${id}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(clientData),
        });
        if (!response.ok) throw new Error('Erro ao atualizar cliente');
        const updatedClient = await response.json();
        const index = clients.findIndex(c => c.id == id);
        if (index !== -1) clients[index] = updatedClient;
      } else {
        const response = await fetch(`${API_BASE}/adicionar`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(clientData),
        });
        if (!response.ok) throw new Error('Erro ao criar cliente');
        const newClient = await response.json();
        clients.push(newClient);
      }
      renderClients(searchInput.value);
      bootstrap.Modal.getInstance(document.getElementById('clientModal')).hide();
    } catch (error) {
      alert(error.message);
    }
  });

  async function deleteClient(id) {
    try {
      const response = await fetch(`${API_BASE}/remover?id=${id}`, {
        method: 'DELETE',
      });
      if (!response.ok) throw new Error('Erro ao excluir cliente');
      clients = clients.filter(c => c.id != id);
      renderClients(searchInput.value);
    } catch (error) {
      alert(error.message);
    }
  }

  searchInput.addEventListener('input', () => {
    renderClients(searchInput.value);
  });

  async function init() {
    await loadClients();
    renderClients();
  }

  init();
})();
