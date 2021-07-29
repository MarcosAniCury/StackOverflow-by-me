# Projeto Fórum
## Acesso ao sistema:
  - https://replit.com/@MarcosAniCury/StackOverflow-by-me#Main.java
### Membros:
    - Danniel Henrique Correa Vieira
    - Letícia Americano Lucas
    - Marcos Ani Cury Vinagre Silva

## Quinta Sprint (Metodos e funcoes feitos pra essa entrega): 
  ### Entidades/Voto.java
    - Criação da classe voto.

  ### Main.java
    - public static void avaliacao(Scanner sc): Função principal para avaliações
    - public static void menuAvaliacao(): Menu da avaliação

## Diretórios e Arquivos:
  ### Principal:
      - Main.java: Classe Main, classe principal do projeto ao qual todo o sistema gira em torno.
  ### Diretório CRUD:
      - CRUD.java: Classe CRUD, no qual faz todo o gerênciamento de acesso aos bancos de dados.
      - Registro.java: Interface que apresenta os métodos que os objetos a serem incluídos no CRUD devem conter.
  ### Diretório arvoreBPlus:
      - ArvoreBMais_ChaveComposta_Int_Int.java: Contém o par de chaves idUsuario e idPergunta, e facilita na busca por perguntas de um mesmo usuário.
  ### Diretório dados: 
      - arvore_pergunta_respostas.db: Contém os dados que associa perguntas às respostas dela.
      - arvore_usuario_pergunta.db: Contém os dados que associa usuário às perguntas dela.
      - arvore_usuario_resposta.db: Contém os dados que associa usuário às respostas dela.
      - arvore_voto_pergunta.db: Contém os dados que associa votos às perguntas dela, tendo como principal objetivo gravar votos de usuário em pergunta.
      - arvore_voto_resposta.db: Contém os dados que associa votos às respostas dela, tendo como principal objetivo gravar votos de usuário em respostas.
      - email_hash_c.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em cesto do email.
      - email_hash_d.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório do email.
      - listainvertida_blocos.db: Armazenamento dos blocos na lista invertida.
      - listainvertida_dict.db: Armazenamento dos dicionario na lista invertida.
      - pergunta_hash_c.db:Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em cesto das perguntas.
      - pergunta_hash_d.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório das perguntas.
      - perguntas.db: Contém os dados gerados pelo hash sobre perguntas e são armazenados em memória secundária.
      - resposta.hash.c.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em cesto da resposta.
      - resposta.hash.d.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório da respostas.
      - resposta.db: Contém os dados gerados pelo hash sobre respostas e são armazenados em memória secundária.
      - usuario_hash_c.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em cesto do usuario.
      - usuario_hash_d.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório do usuario.
      - usuarios.db: Contém os dados gerados pelo hash sobre usuarios e são armazenados em memória secundária.
      - voto_pergunta_hash_c.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em cesto do voto/pergunta.
      - voto_pergunta_hash_d.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório do voto/pergunta.
      - voto_resposta_hash_c.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em cesto do voto/resposta.
      - voto_resposta_hash_d.db: Contém os dados gerados que foram armazenados em memória secundária sobre a tabela hash em diretório do voto/resposta.
  ### Diretório Entidades: 
      - Pergunta.java: Entidade Pergunta utilizada no projeto.
      - Resposta.java: Entidade Resposta utilizada no projeto.
      - Voto.java: Entidade Voto utilizada no projeto.
      - Usuario.java: Entidade Usuario utilizada no projeto.
  ### Diretório HashExtensivel:
      - HashExtensivel.java: Arquivo utilizado para criação da tabela Hash Extensivel.
      - RegistroHashExtensivel.java: Interface que apresenta os métodos que os objetos a serem incluídos na tabela hash extensível devem conter.
  ### Diretório pvc:
      - pcvEmail.java: Esta classe representa o par chave valor de um email, no caso, o email e o idUsuario.
      - pcvPergunta.java: Esta classe representa o par chave valor de uma pergunta, no caso, o idUsuario e o idPergunta.
      - pcvResposta.java: Esta classe representa o par chave valor de uma resposta, no caso, o idUsuario e o endereço no arquivo.
      - pcvUsuario.java: Esta classe representa o par chave valor de um usuario, no caso, o idUsuario e o endereço no arquivo.
      - pcvVoto.java: Esta classe representa o par chave valor de um voto, no caso, o idVoto e o endereço no arquivo.
  ### Diretório listaInvertida:
      - ListaInvertida.java: Classe da criação da lista invertida para acrescentar ou deletar itens da lista, diferente da padrão a lista invertida inverte a hierarquia da informação, sendo assim, ao invés de uma lista de documentos contendo termos, é obtida uma lista de termos, referenciando documentos.

## Log de Tarefas passadas
### Terceira Sprint (Metodos e funcoes feitos pra essa entrega):
  #### Entidades/Pergunta.java
    - Adicionado atributo palavrasChave. Feito alterações necessárias nos construtores além dos getters e setters de palavrasChave.
    - O setter de palavrasChave já possui um formatador para padronizar as palavras-chave.
  
  #### listaInvertida e listaInvertida/ListaInvertida.java
    - Adicionado classe de lista invertida para indexar palavras-chave com os ids da perguntas respectivas.  

  #### Main.java
    - public static void menuConsultaPerguntas(): Apresenta o menu de opções de consulta.
    - public static void consultaPerguntas(Scanner sc): Solicita do usuário as palavras-chave, apresenta os resultados que contém as palavras-chave, e permite o usuário selecionar uma pergunta entre os resultados.
    - public static ArrayList<Pergunta> buscarPerguntas(String palavrasChave): Executa o processo de selecionar apenas as perguntas com palavras-chave em comum através de uma lista invertida de palavras chave e id.

    - Metódo de criarIndInver e alterarIndInver: Contêm o detalhamento para criar um indice para cada palavra-chave e alterar caso necessário.

    - int[] VetorIntVariavel(int[] vetor, int valor) - MAIN: Função criada para adicionar mais um de tamanho a um vetor de int. 
    - void desarquivamentoPerguntas(Scanner sc) - MAIN: Função criada para mostrar perguntas arquivadas e assim desarquiva-las.
    - int[] listaPerguntas(Scanner sc, boolean ativa) - MAIN (alteração): agora a função pega a lista das perguntas ou arquivadas ou desarquivadas.

### Quarta Sprint (Metodos e funcoes feitos pra essa entrega):
  #### Entidades/Resposta.java
    - Criação da entidade Resposta 
  #### Main.java: 
    - void incluirResposta(Scanner sc): A partir de uma resposta selecionada, recebe uma resposta do usuário e a inclui no arquivo e árvores respectivas.
    - void alterarResposta(Scanner sc): A partir de uma resposta selecionada, recebe uma resposta alterada e atualiza a resposta no arquivo respectivo.
    - void arquivarResposta(Scanner sc): A partir de uma resposta selecionada, altera o atributo ativa para false e deleta o par chave valor das árvores.
    - void gerenciarRespostas(Scanner sc, Pergunta p): A partir de uma pergunta selecionada apresenta as opções do CRUD. ALTERAÇÃO DO ENUNCIADO: Escolhemos colocar a opção de incluir resposta no menu anterior (consultaPerguntas()), pois acreditamos que esse era o propósito da opção 1) Responder.
    - void gerenciarTodasRespostas(Scanner sc): Metódo a ser implementado para o usuário gerenciar todas as suas respostas diretamente.
    - int[] listarTodasRespostas(Scanner sc): Imprime todas as respostas de uma pergunta.
    - int[] listarRespostas(Scanner sc): Imprime as respostas do usuário para uma pergunta.
    - void printResposta(Resposta r) throws Exception: Imprime uma resposta.
    - void menuRespostas(): Método para imprimir menu de respostas.
    - void menuGerenciarRespostas(): Método para imprimir opções do menu de respostas.
    - Fixed bugs on listaPerguntas return.
  #### Arquivos de dados:
    - resposta.db
    - resposta_hash_*.db
    - arvore_pergunta_respostas.db
    - arvore_usuario_respostas.db
 