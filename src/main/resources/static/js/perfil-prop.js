// Em perfil-prop.js

// -------- SIMULAÇÃO DE DADOS DO BACK-END --------

// AJUSTE: Renomeei para 'proprietario' e adicionei a nova lista
const proprietarioComDados = {
    nome: "André Lucas Silva",
    email: "andrelucaslive@gmail.com",
    comentarios: [
        {
            titulo: "Shopping Vitória",
            autor: "André Lucas S.",
            texto: "As calçadas novas em frente ao shopping estão bem niveladas e com piso tátil. Só senti falta de avisos sonoros nos semáforos e sinalização em braile em lugares internos."
        },
        {
            titulo: "Parque da Cidade",
            autor: "André Lucas S.",
            texto: "Rampas de acesso em todos os lugares, muito bom!"
        }
    ],
    locais: [
        { nome: "Shopping Vitória", url: "#" },
        { nome: "Faesa", url: "#" },
        { nome: "Museu Solar Monjardim", url: "#" },
        { nome: "Hospital Santa Rita", url: "#" },
        { nome: "Unisales", url: "#" }
    ],
    // NOVO: Lista de locais que o proprietário adicionou
    locaisAdicionados: [
        { nome: "Shopping Vitória", url: "#" },
        { nome: "Faesa", url: "#" },
        { nome: "Hospital Santa Rita", url: "#" },
        { nome: "Unisales", url: "#" }
    ]
};

// AJUSTE: Renomeei e adicionei a nova lista vazia
const proprietarioSemDados = {
    nome: "Ana Clara",
    email: "anaclara@email.com",
    comentarios: [], // Lista vazia
    locais: [],     // Lista vazia
    locaisAdicionados: [] // NOVO: Lista vazia
};

// -------------------------------------------------

/**
 * Função que atualiza a página com os dados de um usuário.
 * @param {object} usuario - O objeto do usuário vindo do "back-end".
 */
function carregarPerfil(usuario) {
    
    // 1. Preencher informações simples (Nome e E-mail)
    document.getElementById('user-name').textContent = usuario.nome;
    document.getElementById('user-email').textContent = usuario.email;

    // 2. Preencher Comentários (Sua lógica original, sem mudanças)
    const commentsContainer = document.getElementById('comments-container');
    
    if (usuario.comentarios.length === 0) {
        commentsContainer.innerHTML = `
            <h2>Comentários:</h2>
            <p class="empty-state">Nenhum comentário ainda.</p>
        `;
    } else {
        const comentariosHtml = usuario.comentarios.map(comentario => {
            return `
                <article class="comment-card">
                    <h4>${comentario.titulo}</h4>
                    <div class="comment-author">
                        <small>${comentario.autor}</small>
                    </div>
                    <p>${comentario.texto}</p>
                </article>
            `;
        }).join('');

        commentsContainer.innerHTML = '<h2>Comentários:</h2>' + comentariosHtml;
    }

    // 3. Preencher Locais Marcados (Sua lógica original, sem mudanças)
    const locationsContainer = document.getElementById('locations-container');

    if (usuario.locais.length === 0) {
        locationsContainer.innerHTML = `
            <h2>Locais marcados como acessível:</h2>
            <p class="empty-state">Nenhum local marcado ainda.</p>
        `;
    } else {
        const locaisHtml = usuario.locais.map(local => {
            return `
                <li><a href="${local.url}">${local.nome}</a></li>
            `;
        }).join('');

        locationsContainer.innerHTML = `
            <h2>Locais marcados como acessível:</h2>
            <ul class="locations-list">${locaisHtml}</ul>
        `;
    }

    // 4. NOVO: Preencher Locais Adicionados (A nova lógica!)
    const addLocationsContainer = document.getElementById('add-locations-container');
    
    // Gera o HTML para CADA local adicionado.
    // Se a lista 'locaisAdicionados' estiver vazia, isso aqui vai
    // gerar uma string vazia (""), o que é perfeito.
    const locaisAdicionadosHtml = usuario.locaisAdicionados.map(local => {
        return `
            <li><a href="${local.url}">${local.nome}</a></li>
        `;
    }).join('');

    // HTML do botão "Adicionar" que vai aparecer SEMPRE
    // (Este HTML foi baseado na sugestão de CSS da nossa conversa anterior)
    const addBtnHtml = `
        <li>
            <a href="#" class="add-location-btn">
                <span class="add-icon">+</span>
                <span>Adicionar novos locais</span>
            </a>
        </li>
    `;

    // Monta o bloco final de HTML
    // Nós colocamos o título, a lista de locais (que pode estar vazia)
    // e o botão de adicionar (que está sempre lá).
    addLocationsContainer.innerHTML = `
        <h2>Locais adicionados:</h2>
        <ul class="add-locations-list">
            ${locaisAdicionadosHtml}  ${addBtnHtml}             </ul>
    `;
}

// -------- QUANDO A PÁGINA CARREGAR, EXECUTE A MÁGICA --------

document.addEventListener('DOMContentLoaded', () => {
    
    // ---- TESTE AQUI ----
    // Troque para 'proprietarioSemDados' para ver a página
    // com as 3 colunas em "estado vazio".
    carregarPerfil(proprietarioSemDados);
    // carregarPerfil(proprietarioSemDados); 

    // Dentro de document.addEventListener('DOMContentLoaded', () => { ... })

    // ... (seu código de carregarPerfil) ...

    // --- LÓGICA DO MODAL DE AVATAR ---

    // 1. Pegar os elementos do DOM
    const btnTrocarImagem = document.getElementById('btn-trocar-imagem');
    const modalAvatar = document.getElementById('modal-avatar');
    const btnCancelar = document.getElementById('btn-cancelar-avatar');
    const avatarOptions = document.querySelectorAll('.avatar-option');
    const perfilImgContainer = document.querySelector('.profile-picture .img-container');

    // 2. Função para ABRIR o modal
    function abrirModal(e) {
        e.preventDefault(); // Impede o link <a> de pular para o topo
        modalAvatar.classList.add('is-open');
    }

    // 3. Função para FECHAR o modal
    function fecharModal() {
        modalAvatar.classList.remove('is-open');
    }

    // 4. Ligar os botões
    btnTrocarImagem.addEventListener('click', abrirModal);
    btnCancelar.addEventListener('click', fecharModal);

    // 5. Fechar o modal clicando FORA dele (no overlay)
    modalAvatar.addEventListener('click', (e) => {
        // Se o clique foi no "overlay" (o pai) e não no "content" (o filho)
        if (e.target === modalAvatar) {
            fecharModal();
        }
    });

    // 6. Lógica para ESCOLHER um avatar
    avatarOptions.forEach(img => {
        img.addEventListener('click', () => {
            // Pega o "src" (caminho) da imagem clicada
            const novaImgSrc = img.src;

            // Coloca a imagem escolhida dentro do container de perfil
            // (Isso vai substituir o ícone <i class="fa-user"></i>)
            perfilImgContainer.innerHTML = `<img src="${novaImgSrc}" alt="Avatar do usuário">`;

            // Fecha o modal
            fecharModal();
        });
    });
    
// --- FIM DA LÓGICA DO MODAL ---

//}); // Fechamento do DOMContentLoaded
});