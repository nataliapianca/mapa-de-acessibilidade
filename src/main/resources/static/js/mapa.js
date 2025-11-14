document.addEventListener('DOMContentLoaded', () => {

    const mapa = L.map('mapa').setView([-20.3155, -40.3128], 15);
    
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(mapa);

    async function carregarPontosDeAcessibilidade() {
        try {
            const response = await fetch('http://localhost:8080/api/locais');
            if (!response.ok) {
                throw new Error('Algo deu errado ao buscar os dados.');
            }
            const locais = await response.json();
            desenharPontosNoMapa(locais);
        } catch (error) {
            console.error("Erro ao carregar os dados:", error);
            alert("Não foi possível carregar os dados. Tente novamente mais tarde.");
        }
    }

    function desenharPontosNoMapa(locais) {
        console.log("Desenhando", locais.length, "pontos no mapa...");
        
        locais.forEach(local => {
            const marcador = L.marker([local.latitude, local.longitude]);
            marcador.bindPopup(`
                <strong>${local.nome}</strong>
                <p>${local.endereco}</p>
                <p style="font-style: italic; font-size: 0.9em;">${local.descricao}</p>
                <small>ID: ${local.id}</small>
            `);
            marcador.addTo(mapa);
        });
    }

    carregarPontosDeAcessibilidade();

    const modal = document.getElementById('modal-novo-local');
    const btnAbrirModal = document.getElementById('btn-abrir-modal');
    const btnCancelarModal = document.getElementById('btn-cancelar-modal');
    const formNovoLocal = document.getElementById('form-novo-local');

    function abrirModal() {
        modal.style.display = 'flex';
    }

    function fecharModal() {
        modal.style.display = 'none';
        formNovoLocal.reset(); 
    }

    btnAbrirModal.addEventListener('click', abrirModal);
    btnCancelarModal.addEventListener('click', fecharModal);
    o
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            fecharModal();
        }
    });

    formNovoLocal.addEventListener('submit', async (e) => {
        e.preventDefault(); 

        // 1. Pegar os valores dos inputs
        const nome = document.getElementById('local-nome').value;
        const endereco = document.getElementById('local-endereco').value;
        const descricao = document.getElementById('local-descricao').value;

        // 2. Pegar as tags selecionadas
        const tagCheckboxes = document.querySelectorAll('.tag-checkbox:checked');
        const tagIds = [];
        tagCheckboxes.forEach(checkbox => {
            tagIds.push(checkbox.value); 
        });

        if (!nome || !endereco || !descricao) {
            alert("Por favor, preencha todos os campos (nome, endereço e descrição).");
            return;
        }
        if (tagIds.length === 0) {
            alert("Selecione pelo menos um item de acessibilidade.");
            return;
        }

        await cadastrarNovoLocal(nome, endereco, descricao, tagIds);

        // 5. Fechar o modal
        fecharModal();
    });

    async function cadastrarNovoLocal(nome, endereco, descricao, tagIds) {
        
        const novoLocal = {
            nome: nome,
            endereco: endereco,
            descricao: descricao,
            latitude: 0,  
            longitude: 0 
        };

        try {
            const response = await fetch(`http://localhost:8080/api/locais?tagIds=${tagIds.join(',')}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(novoLocal),
            });

            if (response.ok) {
                const localCriado = await response.json();
                alert(`Local "${localCriado.nome}" cadastrado com sucesso!`);
                
                desenharPontosNoMapa([localCriado]); 

                mapa.setView([localCriado.latitude, localCriado.longitude], 17);

            } else {
                const erro = await response.text();
                console.error("Erro do backend:", erro);
                alert(`Erro ao cadastrar local: ${erro}`);
            }
        } catch (error) {
            console.error("Erro na comunicação com o backend:", error);
            alert("Erro de rede ao tentar cadastrar o local.");
        }
    }

}); // Fim do 'DOMContentLoaded'