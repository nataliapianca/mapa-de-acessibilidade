// Em app.js

// -------- SIMULAÇÃO DE DADOS DO BACK-END --------

// CASO 1: Usuário com atividades
const usuarioComDados = {
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
    ]
};

// CASO 2: Usuário novo, sem atividades
const usuarioSemDados = {
    nome: "Ana Clara",
    email: "anaclara@email.com",
    comentarios: [], // Lista vazia
    locais: []     // Lista vazia
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

    // 2. Preencher Comentários (com lógica!)
    const commentsContainer = document.getElementById('comments-container');
    
    if (usuario.comentarios.length === 0) {
        // --- Esse é o seu "Estado Vazio"! ---
        commentsContainer.innerHTML = `
            <h3>Comentários:</h3>
            <p class="empty-state">Nenhum comentário ainda.</p>
        `;
    } else {
        // Gera o HTML para CADA comentário e depois junta tudo
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
        }).join(''); // .join() junta todos os pedaços de HTML em um texto só

        commentsContainer.innerHTML = '<h3>Comentários:</h3>' + comentariosHtml;
    }

    // 3. Preencher Locais Marcados (com lógica!)
    const locationsContainer = document.getElementById('locations-container');

    if (usuario.locais.length === 0) {
        // --- O outro "Estado Vazio"! ---
        locationsContainer.innerHTML = `
            <h3>Locais marcados como acessível:</h3>
            <p class="empty-state">Nenhum local marcado ainda.</p>
        `;
    } else {
        // Gera o HTML para CADA local
        const locaisHtml = usuario.locais.map(local => {
            return `
                <li><a href="${local.url}">${local.nome}</a></li>
            `;
        }).join('');

        locationsContainer.innerHTML = `
            <h3>Locais marcados como acessível:</h3>
            <ul class="locations-list">${locaisHtml}</ul>
        `;
    }
}

// -------- QUANDO A PÁGINA CARREGAR, EXECUTE A MÁGICA --------

// 'DOMContentLoaded' é um evento que dispara assim que o HTML foi
// completamente lido pelo navegador. É o momento seguro para o JS rodar.
document.addEventListener('DOMContentLoaded', () => {
    
    // ---- TESTE AQUI ----
    // Troque para 'usuarioSemDados' para ver o outro cenário!
    carregarPerfil(usuarioSemDados);
    // carregarPerfil(usuarioSemDados); 

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
});
