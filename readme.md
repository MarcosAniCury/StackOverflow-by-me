# Projeto Fórum
### Membros:
    - Danniel Henrique Correa Vieira
    - Letícia Americano Lucas
    - Marcos Ani Cury Vinagre Silva
## Diretórios e Arquivos:
### Principal:
    - Main.java: Classe Main, classe principal do projeto ao qual todo o sistema gira em torno.
### Diretório CRUD:
    - CRUD.java: Classe CRUD, no qual faz todo o gerênciamento de acesso aos bancos de dados.
    - Registro.java: Interface que apresenta os métodos que os objetos a serem incluídos no CRUD devem conter.
### Diretório arvoreBPlus:
    - ArvoreBMais_ChaveComposta_Int_Int.java: Contém o par de chaves idUsuario e idPergunta, e facilita na busca por perguntas de um mesmo usuário.
### Diretório dados: 
    - arvore.db: Contém os dados gerados pela arvore e são armazenados em memória secundária.
    - email_hash_c.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em cesto do email.
    - email_hash_d.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório do email.
    - pergunta_hash_d.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório das perguntas.
    - perguntas.db: Contém os dados gerados pelo hash sobre perguntas e são armazenados em memória secundária.
    - usuario_hash_c: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em cesto do usuario.
    - usuario_hash_d: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório do usuario.
    - usuarios.db: Contém os dados gerados pelo hash sobre usuarios e são armazenados em memória secundária.
### Diretório Entidades: 
    - Pergunta.java: Entidade Pergunta utilizada no projeto.
    - Usuario.java: Entidade Usuario utilizada no projeto.
### Diretório HashExtensivel:
    - HashExtensivel.java: Arquivo utilizado para criação da tabela Hash Extensivel.
    - RegistroHashExtensivel.java: Interface que apresenta os métodos que os objetos a serem incluídos na tabela hash extensível devem conter.
### Diretório pvc:
    - pcvEmail.java: Esta classe representa o par chave valor de um email, no caso, o email e o idUsuario referente ao email.
    - pcvPergunta.java: Esta classe representa o par chave valor de uma pergunta, no caso, o idUsuario e o idPergunta.
    - pcvUsuario.java: Esta classe representa o par chave valor de um usuario, no caso, o idUsuario e o endereço no arquivo.
 