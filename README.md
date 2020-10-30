# FoodFast
## O que é?
- O projeto FoodFast consiste em uma aplicação Android para consultar receitas e gerenciar o inventário de comida da própria casa.
### Justificativa
- O projeto foi baseado na compreensão de uma determinada dificuldade para organizar e ter o controle da comida disponível na cozinha.
- E muitas pessoas não possuem o conhecimento de uma grande variedade de receitas e desconhecem os ingredientes necessários.
- Além de que o gasto excessivo no mercado reflete no grande armazenamento de comida e o desperdício é gerado.
### Funcionalidades
- Inventário com toda a comida que o usuário possui na cozinha, dividido em diversas categorias.
- Cadastro da comida manualmente de acordo com diversos atributos (opcionais) - quantidade, marca, validade, etc.
- Consulta de receitas.
- Publicação de receitas.
- Receitas possíveis e não possíveis com base na disponibilidade dos ingredientes do inventário do usuário.
- Receitas favoritas definidas pelo usuário.
- Receitas filtradas por categorias (favoritas, baixa caloria, vegetarianas, etc).
## Qual o público alvo?
- Pessoas com dificuldade de organização da comida, falta de conhecimento em receitas e desejo de uma rotina com alimentação saúdavel.
## Quem faz parte da equipe e o papel de cada?
- Lorenzo Windmoller Martins (Desenvolvedor)
- Samuel Albuquerque de Paiva (Designer)
- Vinícius Andrade Perrone (Analista)
## Como baixar?
- Para baixar o projeto execute o comando:
```bash
// Clonar o repositório da aplicação
$ git clone https://github.com/lorenzowind/FoodFast.git
```
## Como instalar?
1. Para rodar o backend siga os seguintes passos:
- Navegue até a pasta do backend e instale as dependências:
```bash
// Navegar até a pasta backend
$ cd backend

// Instalar as dependências da aplicação
$ yarn
```
- Instale as imagens Docker do MySQL, MongoDB, Redis e Adminer usando docker-compose:
```bash
// Rodar as imagens do Docker
$ docker-compose up -d
```
- Crie um arquivo chamado .env com base no .env.example e insira as credenciais da AWS;
- Crie um arquivo chamado .ormconfig.json com base no .ormconfig.example.json e insira o host e porta do MySQL e MongoDB de acordo com as imagens Docker instaladas anteriormente, além de trocar o destinatário src por dist e .ts por .js;
- Configure as credenciais da imagem Docker do MySQL através dos seguintes comandos:
```bash
// Entrar no bash da imagem MySQL
$ docker exec -it NOME_DA_IMAGEM bash
// Entrar no root da imagem MySQL
$ mysql -u root -p
// Alterar a senha de acesso
$ ALTER USER root IDENTIFIED WITH mysql_native_password BY ‘SENHA_DO_USUÁRIO_ROOT’;
```
- Rode as migrations do banco de dados através do comando:
```bash
// Rodar as migrations
$ node_modules/.bin/typeorm migration:run
```
- Adicione configuração de não auto-reinicialização para cada imagem Docker através do comando:
```bash
// Alterar a configuração das imagens Docker
$ docker update --restart=unless-stopped ID_DA_IMAGEM
```
- Inicie o servidor através do comando:
```bash
// Iniciar o servidor
$ yarn dev:server
```
2. Para instalar a aplicação em algum dispositivo siga os seguintes passos:
- Navegue até a localização do arquivo .apk e abra no dispositivo
```bash
// Navegar até o arquivo .apk
$ cd mobile/app/build/outputs/apk
```
## Tecnologias
- O backend da aplicação foi desenvolvido utilizando as seguintes tecnologias:
  - [Node.js](https://nodejs.org/en/)
  - [Express](https://expressjs.com/pt-br/)
  - [MySQL](https://www.mysql.com/)
  - [MongoDB](https://www.mongodb.com/)
  - [Redis](https://redis.io/)
- O aplicação mobile foi desenvolvida utilizando as seguintes tecnologias:
  - [Android](https://www.android.com/intl/pt-BR_br/)
  - [Volley](https://developer.android.com/training/volley)