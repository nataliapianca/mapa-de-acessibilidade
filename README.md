# 🛒 Gerenciador de Produtos (Spring Boot + H2 + Frontend Simples)

Projeto simples que mostra como criar um **CRUD de produtos** usando **Spring Boot (Java)**, **banco H2 em memória** e um **frontend leve em HTML, CSS e JavaScript**.

O sistema permite **adicionar e remover produtos**, e a **listagem aparece automaticamente** na tela — sem precisar clicar em “Listar”.

---

## 🚀 Como Rodar o Projeto

### ✅ Pré-requisitos

Tenha instalado:
- **Java 17+**
- **VS Code**
- Extensões:
  - `Extension Pack for Java`
  - `Spring Boot Extension Pack`
- **Git** (para clonar o projeto)
- **Navegador Web**
- *(opcional)* **Postman** para testar a API

---

### ⚙️ 1. Clonar o projeto

No terminal:
```bash
git clone https://github.com/SEU_USUARIO/gerenciador-produtos.git
cd gerenciador-produtos
code .
```

---

### ▶️ 2. Rodar o backend (Spring Boot)

No VS Code:
1. Pressione **Ctrl+Shift+P**  
2. Procure por **Spring Boot: Run current project**  
3. Aguarde iniciar — o backend rodará em:  
   👉 **http://localhost:8080**

---

### 💻 3. Abrir o frontend

Com o backend rodando:
1. Vá até a pasta  
   ```
   src/main/resources/static/html/
   ```
2. Abra o arquivo **produtos.html** no navegador  

➡️ A página mostra automaticamente os produtos cadastrados e permite **adicionar** ou **remover** itens.

---

### 🗄️ 4. (Opcional) Ver o Banco H2

1. Acesse:  
   👉 **http://localhost:8080/h2-console**
2. Use os dados:
   ```
   JDBC URL: jdbc:h2:mem:meubanco
   User Name: sa
   Password: (deixe em branco)
   ```
3. Clique em **Connect**
4. Execute:
   ```sql
   SELECT * FROM PRODUTO;
   ```

---

## 🧩 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/br/mapa_de_acessibilidade/mapa_de_acessibilidade/
│   │   ├── controller/ProdutoController.java
│   │   ├── model/Produto.java
│   │   ├── repository/ProdutoRepository.java
│   │   └── service/ProdutoService.java
│   └── resources/
│       ├── static/
│       │   ├── html/produtos.html
│       │   ├── css/produtos.css
│       │   └── js/produtos.js
│       └── application.properties
```

---

## ⚠️ Dicas e Erros Comuns

| Problema | Causa | Solução |
|-----------|--------|----------|
| ❌ `No static resource html/produtos.html` | Caminho errado do HTML | Coloque em `src/main/resources/static/html/` |
| ⚠️ `Database "mem:meubanco" not found` | App não iniciado | Rode o backend antes de abrir o H2 Console |

---

## 🧾 Observações

- O banco H2 é **em memória**, então os dados **somem ao reiniciar** a aplicação.  
- Projeto voltado para **aprendizado e testes**, não para produção.

---

### 👩‍💻 Tecnologias
- **Spring Boot, H2, HTML, CSS e JavaScript.**
