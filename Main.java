import java.io.*;
import java.util.*;
import java.text.*;

public class Main {
  // Arquivo declarado fora de main() para ser poder ser usado por outros métodos
  private static CRUD<Usuario, pcvUsuario> arqPessoas;
  private static CRUD<Pergunta, pcvPergunta> arqPerguntas;
  private static CRUD<Resposta, pcvResposta> arqRespostas;
  private static CRUD<Voto, pcvVoto> arqVotoPerguntas;
  private static CRUD<Voto, pcvVoto> arqVotoRespostas;
  private static HashExtensivel<pcvEmail> hashEmail;
  private static ArvoreBMais_ChaveComposta_Int_Int arvB_UsuarioPergunta;
  private static ArvoreBMais_ChaveComposta_Int_Int arvB_PerguntaResposta;
  private static ArvoreBMais_ChaveComposta_Int_Int arvB_UsuarioResposta;
  private static ArvoreBMais_ChaveComposta_Int_Int arvB_VotoPergunta;
  private static ArvoreBMais_ChaveComposta_Int_Int arvB_VotoResposta;

  private static ListaInvertida indInver;

  public static int usuarioAtual;
  public static int perguntaAtual;
  public static int respostaAtual;

  public static void main(String[]args) {
    try {
      // criacao do crud
      arqPessoas = new CRUD<>(Usuario.class.getConstructor(),
          pcvUsuario.class.getDeclaredConstructor(int.class, long.class), 
          pcvUsuario.class.getConstructor(),
          "dados/usuario_hash_d.db", "dados/usuario_hash_c.db", "dados/usuarios.db");
      arqPerguntas = new CRUD<>(Pergunta.class.getConstructor(),
          pcvPergunta.class.getDeclaredConstructor(int.class, long.class), 
          pcvPergunta.class.getConstructor(),
          "dados/pergunta_hash_d.db", "dados/pergunta_hash_c.db", "dados/perguntas.db");
      arqRespostas = new CRUD<>(Resposta.class.getConstructor(),
          pcvResposta.class.getDeclaredConstructor(int.class, long.class),
          pcvResposta.class.getConstructor(),
          "dados/resposta_hash_d.db","dados/resposta_hash_c.db","dados/resposta.db");
      arqVotoPerguntas = new CRUD<>(Voto.class.getConstructor(),
          pcvVoto.class.getDeclaredConstructor(int.class, long.class),
          pcvVoto.class.getConstructor(),
          "dados/voto_pergunta_hash_d.db","dados/voto_pergunta_hash_c.db","dados/voto_pergunta.db"); 
      arqVotoRespostas = new CRUD<>(Voto.class.getConstructor(),
          pcvVoto.class.getDeclaredConstructor(int.class, long.class),
          pcvVoto.class.getConstructor(),
          "dados/voto_resposta_hash_d.db","dados/voto_resposta_hash_c.db","dados/voto_resposta.db");  

      // criacao hashEmail
      hashEmail = new HashExtensivel<>(pcvEmail.class.getConstructor(), 4, "dados/email_hash_d.db",
          "dados/email_hash_c.db");

      // criacao arvoreB+_idUsuario_idPergunta
      arvB_UsuarioPergunta = new ArvoreBMais_ChaveComposta_Int_Int(255, "dados/arvore_usuario_pergunta.db");
      arvB_PerguntaResposta = new ArvoreBMais_ChaveComposta_Int_Int(255, "dados/arvore_pergunta_resposta.db");
      arvB_UsuarioResposta = new ArvoreBMais_ChaveComposta_Int_Int(255, "dados/arvore_usuario_resposta.db");
      arvB_VotoPergunta = new ArvoreBMais_ChaveComposta_Int_Int(255, "dados/arvore_voto_pergunta.db");
      arvB_VotoResposta = new ArvoreBMais_ChaveComposta_Int_Int(255, "dados/arvore_voto_resposta.db");
      
      // criacao indice invertido
      indInver = new ListaInvertida(255, "dados/listainvertida_dict.db", 
        "dados/listainvertida_blocos.db");

      Scanner sc = new Scanner(System.in);
      // menu principal
      sistemaUsuario(sc);
      sc.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // ---------------------Menus------------------------
  public static void menuPrincipal() {
    System.out.println("\n=============");
    System.out.println("PERGUNTAS 1.0");
    System.out.println("=============");
    System.out.println("\nACESSO\n");
    System.out.println("1) Acesso ao sistema");
    System.out.println("2) Novo usuario (primeiro acesso)");
    System.out.println("3) Esqueci a senha\n");
    System.out.println("0) Sair\n");
    System.out.print("Digite sua escolha:");
  }

  public static void menuPerguntas(int notificacoes) {
    menuPadrao();
    System.out.println("\n\n1) Criacao/Gerenciamento de perguntas");
    System.out.println("2) Consulta/responder perguntas");
    System.out.println("3) Gerenciar suas respostas");
    System.out.println("4) Notificacoes: " + notificacoes);
    System.out.println("\n0) Retornar ao menu anterior\n");
    System.out.print("Opcao:");
  }

  public static void menuCriacaoPergunta() {
    menuPadrao();
    System.out.println("> CRIACAO DE PERGUNTAS");
    System.out.println("\n1) Listar");
    System.out.println("2) Incluir");
    System.out.println("3) Alterar");
    System.out.println("4) Arquivar");
    System.out.println("5) Desarquivados");
    System.out.println("\n0) Retornar ao menu anterior");
    System.out.print("\nOpcao:");
  }

  public static void menuConsultaPerguntas() {
    menuPadrao();
    System.out.println("> PERGUNTAS\n");
    System.out.println("Busque as perguntas por palavra chave separadas por ponto e vírgula");
    System.out.println("Ex: política;Brasil;eleições\n");
    System.out.print("Palavras chave: ");
  }

  public static void menuRespostas() {
    menuPadrao();
    System.out.println("> GERENCIADOR DE RESPOSTAS");
  }

  public static void menuGerenciarRespostas() {
    System.out.println("\n\n1) Listar Respostas");
    System.out.println("2) Alterar Respostas");
    System.out.println("3) Arquivar Respostas");
    System.out.println("\n0) Retornar ao menu anterior");
    System.out.print("Opção: ");
  }

  public static void menuAvaliacao() {
    menuPadrao();
    System.out.println("> Avaliacao");
    System.out.println("\nEscolha qual voce deseja avaliar:\n");
    System.out.println("1)Pergunta");
    System.out.println("2)Resposta");
    System.out.println("\n0)Sair");
    System.out.print("Opção: ");
  }

  public static void menuPadrao() {
    System.out.println("\n=============");
    System.out.println("PERGUNTAS 1.0");
    System.out.println("=============");
    System.out.print("\nINICIO ");
  }

  // -------------------Usuarios-------------------------
  public static void sistemaUsuario(Scanner sc) {
    int opcode = -1;
    do {
      menuPrincipal();
      opcode = sc.nextInt();
      sc.nextLine();
      switch (opcode) {
      case 1:
        acessoSistemaUsuario(sc);
        break;
      case 2:
        novoUsuario(sc);
        break;
      case 3:
        esqueciSenha(sc);
        break;
      case 0:
        System.out.println("\nFechando sistema...");
        break;
      default:
        System.out.println("\n\nValor invalido, digite novamente");
      }
    } while (opcode != 0);
  }

  public static void acessoSistemaUsuario(Scanner sc) {
    try {
      System.out.println("\n=============");
      System.out.println("ACESSO AO SISTEMA");
      String email = "";
      int tam_email;
      do {
        System.out.print("E-mail: ");
        email = sc.nextLine();// Ler email e verificar se esse usuario ta cadastrado
        tam_email = email.length();
        if (tam_email >= 34)
          System.out.println("O email não pode passar de 34 caracteres, atualmente tem " + tam_email);
        else if (tam_email == 0)
          System.out.println("Email nao pode contar 0 caracteres");
      } while (tam_email == 0 || tam_email >= 34);
      pcvEmail verificacao = hashEmail.read(email.hashCode());
      if (verificacao != null) {
        Usuario user = arqPessoas.read(verificacao.getValor());
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        if (senha.hashCode() == user.getSenha()) {
          // rediciona para a tela principal
          usuarioAtual = user.getId();
          sistemaPerguntas(sc);
        } else
          System.out.println("\nSenha invalida");
      } else
        System.out.println("\nEmail nao cadastrado");
    } catch (Exception erro) {
      erro.printStackTrace();
    }
  }

  public static void novoUsuario(Scanner sc) {
    try {
      System.out.println("\n=============");
      System.out.println("NOVO USUARIO");
      System.out.print("E-mail: ");
      // Ler email e verificar se ele está no sistema
      String email = sc.nextLine();
      String nome, senha, pergunta, resposta;
      if (email.length() == 0)
        System.out.println("Email inválido. Voltando para o menu...");
      else if (email.length() >= 34)
        System.out.println("O email não pode passar de 34 caracteres, atualmente tem " + email.length());
      else {
        pcvEmail verificacao = hashEmail.read(email.hashCode());
        if (verificacao != null)
          System.out.println("Email já cadastrado. Voltando para o menu...");
        else {
          // criar um novo usuario no sistema com os dados inseridos
          Usuario user = new Usuario();
          do {
            System.out.print("Nome: ");
            nome = sc.nextLine();
          } while (nome.length() == 0);
          do {
            System.out.print("Senha: ");
            senha = sc.nextLine();
            System.out.print("Digite sua senha novamente: ");
          } while (!sc.nextLine().equals(senha) || senha.length() == 0);
          do {
            System.out.print("\nDigite uma pergunta secreta que sera usada caso esqueça a senha:");
            pergunta = sc.nextLine();
            System.out.print("\nAgora a resposta da pergunta:");
            resposta = sc.nextLine();
          } while (pergunta.length() == 0 && resposta.length() == 0);
          System.out.println("\n\nConfirme seus dados:");
          System.out.println("Email: " + email);
          System.out.println("Nome: " + nome);
          System.out.println("Senha: " + senha);
          System.out.println("Pergunta Secreta: " + pergunta);
          System.out.println("Resposta Secreta: " + resposta);
          System.out.print("Confirmar? (Y/N) ");
          if (sc.nextLine().toUpperCase().equals("Y")) {
            user.setEmail(email);
            user.setNome(nome);
            user.setSenha(senha.hashCode());
            user.setPerguntaSecreta(pergunta);
            user.setRespostaSecreta(resposta);
            arqPessoas.create(user);
            pcvEmail criacao = new pcvEmail(user.getEmail(), user.getId());
            hashEmail.create(criacao);
            System.out.println("\nUsuário cadastrado com sucesso\n");
          } else {
            System.out.println("\nCadastro de Usuário cancelado.\n");
          }
        }
      }
    } catch (Exception erro) {
      erro.printStackTrace();
    }
  }

  public static void esqueciSenha(Scanner sc) {
    try {
      System.out.println("\n=============");
      System.out.println("ESQUECI A SENHA");
      System.out.print("E-mail: ");
      String email = sc.nextLine();
      pcvEmail verificacao;
      if (email.length() == 0)
        System.out.println("\nEmail invalido. Voltando para o menu...");
      else {
        verificacao = hashEmail.read(email.hashCode());
        if (verificacao == null)
          System.out.println("\nEmail não cadastrado. Voltando para o menu...");
        else {
          Usuario user = arqPessoas.read(verificacao.getValor());
          System.out.print("\nPergunta Secreta: " + user.getPerguntaSecreta() + ":");
          String resposta = sc.nextLine();
          String senha;
          if (!resposta.equals(user.getRespostaSecreta()))
            System.out.println("\nResposta errada. Voltando para o menu...");
          else {
            do {
              System.out.print("\nNova senha: ");
              senha = sc.nextLine();
              System.out.print("Digite novamente a nova senha: ");
            } while (!sc.nextLine().equals(senha) && senha.length() == 0);
            System.out.println("\n\nConfirme seus dados:");
            System.out.println("Senha: " + senha);
            System.out.print("Confirmar? (Y/N) ");
            if (sc.nextLine().toUpperCase().equals("Y")) {
              user.setSenha(senha.hashCode());
              arqPessoas.update(user);
              System.out.println("\nSenha atualizada com sucesso\n");
            } else
              System.out.println("\nAtualização de senha cancelada.\n");
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  // ---------------------------Perguntas------------------------

  public static void sistemaPerguntas(Scanner sc) throws Exception {
    int opcode = -1;
    // menu Perguntas
    while (opcode != 0) {
      menuPerguntas(0);
      opcode = sc.nextInt();
      sc.nextLine();
      switch (opcode) {
      case 1:
        criacaoPerguntas(sc);
        break;
      case 2:
        consultaPerguntas(sc);
        break;
      case 3:
        gerenciarTodasRespostas(sc);
        break;
      case 4:
        System.out.println("\nNotificacoes sera implementado no futuro");
        break;
      case 0:
        System.out.println("\nFechando sistema...");
        break;
      default:
        System.out.println("\n\nValor invalido, digite novamente");
      }
    }
  }

  public static void criacaoPerguntas(Scanner sc) {
    int opcode = -1;
    // menu Perguntas
    while (opcode != 0) {
      menuCriacaoPergunta();
      opcode = sc.nextInt();
      sc.nextLine();
      switch (opcode) {
      case 1:
        listaPerguntas(sc);
        System.out.println("Pressione Enter para continuar...");
        sc.nextLine();
        break;
      case 2:
        incluirPerguntas(sc);
        break;
      case 3:
        alterarPerguntas(sc);
        break;
      case 4:
        arquivamentoPerguntas(sc);
        break;
      case 5:
        desarquivamentoPerguntas(sc);
        break;
      case 0:
        System.out.println("\nRetornando ao menu...");
        break;
      default:
        System.out.println("\n\nValor invalido, digite novamente");
      }
    }
  }

  public static ArrayList<Integer> listaPerguntas(Scanner sc) {
    int[] arrayIdPerguntas = new int[0];
    ArrayList<Integer> arrayIdPerguntasSelecionadas = new ArrayList<Integer>();
   // int[] arrayIdPerguntasSelecionadas = new int[0];
    try {
      System.out.println("\n=============");
      System.out.println("MINHAS PERGUNTAS\n");
      System.out.println("=============\n");
      // Lê-se todos os idPerguntas do usuario atual
      arrayIdPerguntas = arvB_UsuarioPergunta.read(usuarioAtual);

      // Imprime cada pergunta a partir do idPergunta pesquisado no indice.
      Pergunta p;
      for (int i = 0; i < arrayIdPerguntas.length; i++) {
        p = arqPerguntas.read(arrayIdPerguntas[i]);
        if (p.isAtiva()) {
          arrayIdPerguntasSelecionadas.add(p.getId());
          System.out.print(i + 1 + ".");
          printPergunta(p);
        }
      }
      if (arrayIdPerguntas.length == 0)
        System.out.println("\nNenhuma pergunta disponivel");
    } catch (Exception e) {
      e.printStackTrace();
    }

    return arrayIdPerguntasSelecionadas;
  }

  public static void incluirPerguntas(Scanner sc) {
    try {
      String pergunta = "";
      do {
        System.out.println("\nDigite a sua pergunta:");
        System.out.print(">>>");
        pergunta = sc.nextLine();
      } while (pergunta.length() == 0);

      String palavrasChave = "";
      do {
        System.out.println("\nDigite as palavras-chaves da sua pergunta(ex.: galinha;atravessou;rua):");
        System.out.print(">>> ");
        palavrasChave = sc.nextLine();
      } while (palavrasChave.length() == 0);

      System.out.println("\n\nConfirme seus dados:");
      System.out.println("Pergunta: " + pergunta);
      System.out.println("Palavras-chave: " + palavrasChave);
      System.out.print("Confirmar? (Y/N) ");
      if (sc.nextLine().toUpperCase().equals("Y")) {
        Pergunta question = new Pergunta();
        question.setIdUsuario(usuarioAtual);
        question.setPergunta(pergunta);
        question.setNota((short) 0);
        question.setCriacao(); // Metodo interno já obtem a data e a hora.
        question.setAtiva(true);
        question.setPalavrasChave(palavrasChave);

        arqPerguntas.create(question);
        arvB_UsuarioPergunta.create(usuarioAtual, question.getId());
        criarIndInver(question.getId(), formatarPalavrasChave(palavrasChave));
        System.out.println("\nPergunta criada com sucesso.\n");
      } else
        System.out.println("\nCriação de pergunta cancelada.\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void alterarPerguntas(Scanner sc) {
    try {
      ArrayList<Integer> listaPerguntas = listaPerguntas(sc);
     // int[] listaPerguntas = listaPerguntas(sc);
      if (listaPerguntas.size() == 0) {
        System.out.println("\nNão há perguntas para alterar.");
      } else {
        System.out.println(
            "\nVeja qual pergunta voce deseja alterar, lembrando que apenas as perguntas que nao possuem repostas podem ser alteradas");

        System.out.println("\nDigite o número da pergunta que queira alterar.");
        System.out.println("\n0) Retornar para CRIACAO DE PERGUNTAS");
        System.out.print("\nOpcao:");
        int iPergunta = sc.nextInt();
        sc.nextLine();
        if (iPergunta != 0 && iPergunta <= listaPerguntas.size()) {
          Pergunta pergunta = arqPerguntas.read(listaPerguntas.get(iPergunta - 1));

          if (pergunta.isAtiva()) {
            System.out.println("\nPergunta:");
            printPergunta(pergunta);

            String perguntaAlt;
            do {
              System.out.print("Digite a pergunta alterada:");
              perguntaAlt = sc.nextLine();
            } while (perguntaAlt.length() == 0);

            String palavrasChaveAlt;
            do {
              System.out.println("Digite as palavras-chave(ex.: galinha;atravessou;rua):");
              palavrasChaveAlt = sc.nextLine();
            } while (palavrasChaveAlt.length() == 0);

            if (perguntaAlt.length() != 0) {
              System.out.println("\nConfirme seus dados:");
              System.out.println("Pergunta: " + perguntaAlt);
              System.out.println("Palavras-chave: " + palavrasChaveAlt);
              System.out.print("Confirmar? (Y/N) ");
              if (sc.nextLine().toUpperCase().equals("Y")) {
                pergunta.setPergunta(perguntaAlt);

                alterarIndInver(pergunta, palavrasChaveAlt);
                arqPerguntas.update(pergunta);
                System.out.println("\nPergunta atualizada com sucesso.\n");
              } else
                System.out.println("\nAtualização de pergunta cancelada.\n");
            } else
              System.out.println("\nAtualização vazia.\n");
          } else
            System.out.println("\nPergunta arquivada não pode ser alterada.\n");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void arquivamentoPerguntas(Scanner sc) 
  {
    try 
    { 
      ArrayList<Integer> listaPerguntas = listaPerguntas(sc);
      if(listaPerguntas.size() == 0) 
        System.out.println("\nNão há perguntas para arquivar."); 
      else 
      {
        System.out.println("\n0) Retornar para CRIACAO DE PERGUNTAS");
        System.out.println("\nDigite o número da pergunta que queira arquivar.");
        System.out.println("\nEscolha a pergunta que vc deseja arquivar.");
        System.out.print("\nOpcao:"); 
        int iPergunta = sc.nextInt(); 
        sc.nextLine(); 
        if (iPergunta != 0 || iPergunta <= listaPerguntas.size()) 
        {
          Pergunta pergunta = arqPerguntas.read(listaPerguntas.get(iPergunta-1));

          System.out.println("\n\nConfirme seus dados:"); 
          System.out.print("Pergunta:"); 
          printPergunta(pergunta); 
          System.out.print("\nConfirmar? (Y/N) ");

          if(sc.nextLine().toUpperCase().equals("Y")) 
          {
            pergunta.setAtiva(false);

            String[] split = pergunta.getPalavrasChave().split(";"); 
            for(String str : split)
              indInver.delete(str,pergunta.getId());

            arqPerguntas.update(pergunta); 
            System.out.println("\nPergunta arquivada com sucesso.\n"); 
          } 
          else 
            System.out.println("\nArquivamento de pergunta cancelada.\n"); 
        } 
      } 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    }
  }

  public static void desarquivamentoPerguntas(Scanner sc) 
  {
    try
    { 
      ArrayList<Integer> listaPerguntas = listaPerguntas(sc); 
      if(listaPerguntas.size() == 0) 
        System.out.println("\nNão há perguntas arquivadas."); 
      else
      {
        System.out.println("\n0) Retornar para CRIACAO DE PERGUNTAS");
        System.out.println("\nDigite o número da pergunta que queira desarquivar.");
        System.out.println("\nEscolha a pergunta que vc deseja desarquivar.");
        System.out.print("\nOpcao:"); 
        int iPergunta = sc.nextInt(); 
        sc.nextLine(); 
        if (iPergunta != 0 || iPergunta <= listaPerguntas.size()) 
        { 
          Pergunta pergunta = arqPerguntas.read(listaPerguntas.get(iPergunta-1));
      
          System.out.println("\n\nConfirme seus dados:"); 
          System.out.print("Pergunta: "); 
          printPergunta(pergunta); 
          System.out.print("\nConfirmar? (Y/N) ");
          if(sc.nextLine().toUpperCase().equals("Y")) 
          { 
            pergunta.setAtiva(true);
          
            criarIndInver(pergunta.getId(), formatarPalavrasChave(pergunta.getPalavrasChave()));
            arqPerguntas.update(pergunta); 
            System.out.println("\nPergunta desarquivada com sucesso.\n"); 
          } 
          else 
            System.out.println("\nDesarquivamento de pergunta cancelada.\n"); 
        } 
      } 
    }
    catch(Exception e)
    { 
      e.printStackTrace(); 
    }
  }
  // ------------Consulta/Responder pergunta---------------

  public static void consultaPerguntas(Scanner sc) {
    try {
      ArrayList<Pergunta> listaPerguntas = listaPerguntasUsuario(sc);

      int iPergunta = sc.nextInt();
      sc.nextLine();
      if (iPergunta != 0 || iPergunta <= listaPerguntas.size()) {
        Pergunta perguntaSelecionada = listaPerguntas.get(iPergunta - 1);
        printPerguntaExibida(perguntaSelecionada);

        perguntaAtual = perguntaSelecionada.getId();perguntaSelecionada.getId();

        listarTodasRespostas(sc);

        System.out.println(
        "1) Responder\n"+
        "2) Avaliar\n"+
        "3) Gerenciar Respostas\n\n"+
        "0) Retornar\n");
        System.out.print("Opção: ");  

        int opcode = sc.nextInt();
        sc.nextLine();
        switch (opcode) {
        case 0:
          System.out.println("\nVoltando para o menu...");
          break;
        case 1:
          incluirResposta(sc);
          break;
        case 2:
          avaliacao(sc);
          break;
        case 3:
          gerenciarRespostas(sc, perguntaSelecionada);
          break;
        default:
          System.out.println("\nEm breve...");
        }
      } else {
        System.out.println("\nVoltando para o menu...");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<Pergunta> listaPerguntasUsuario(Scanner sc) {
    menuConsultaPerguntas();
    String palavrasChave = sc.nextLine();

    palavrasChave = formatarPalavrasChave(palavrasChave);
    ArrayList<Pergunta> listaPerguntas = buscarPerguntas(palavrasChave);

    System.out.println("\nRESULTADO DA PESQUISA:\n");
    int i = 1;
    for (Pergunta p : listaPerguntas) {
      System.out.print("\n"+i++ + ".");
      printPerguntaResumido(p);
    }
    if (listaPerguntas.size() == 0)
      System.out.println("\nNenhuma pergunta disponivel");
    System.out.println("\n0) Retornar para CRIACAO DE PERGUNTAS");
    System.out.println("\nDigite o número da pergunta que queira visualizar.");
    return listaPerguntas;
  }

  public static ArrayList<Pergunta> buscarPerguntas(String palavrasChave) {
    ArrayList<Pergunta> listPerguntas = new ArrayList<Pergunta>();
    try {
      String[] split = palavrasChave.split(";");

      int[] listIds = indInver.read(split[0]);
      HashSet<Integer> setId = new HashSet<Integer>();
      for (int i : listIds) {
        setId.add(i);
      }

      for (String str : split) {
        listIds = indInver.read(str);
        HashSet<Integer> setNewId = new HashSet<Integer>();
        for (int i : listIds) {
          setNewId.add(i);
        }

        setId.retainAll(setNewId);
      }

      for (int id : setId) {
        Pergunta p = arqPerguntas.read(id);
        listPerguntas.add(p);
      }

      Collections.sort(listPerguntas, Pergunta.comparadorNota);
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    }

    return listPerguntas;

  }

  // ------------Respostas---------------

  public static void incluirResposta(Scanner sc){
    try{
      String resposta = "";
      do {
        System.out.print("\nDigite a sua resposta:");
        resposta = sc.nextLine();
      } while (resposta.length() == 0);

      System.out.println("\n\nConfirme seus dados:");
      System.out.println("Resposta: " + resposta);
      System.out.print("Confirmar? (Y/N) ");
      if (sc.nextLine().toUpperCase().equals("Y")) {
        Resposta answer = new Resposta();
        answer.setIdUsuario(usuarioAtual);
        answer.setIdPergunta(perguntaAtual);
        answer.setResposta(resposta);
        answer.setCriacao(); 

        arqRespostas.create(answer);
        arvB_PerguntaResposta.create(perguntaAtual, answer.getId());
        arvB_UsuarioResposta.create(usuarioAtual, answer.getId());
        System.out.println("\nResposta criada com sucesso.\n");
      } else
        System.out.println("\nInclusão de resposta cancelada.\n");
    }
    catch(Exception e){

    }
  }

  public static void alterarResposta(Scanner sc) {
    try{
      System.out.println("\n");

      int[] listaRespostas = listarRespostas(sc);

      System.out.println("\nDigite o número da resposta que queira alterar.");
      System.out.println("\n0) Retornar para o Gerenciamento de Respostas.");
      System.out.print("\nOpcao:");
      int iResposta = sc.nextInt();
      sc.nextLine();
      if(iResposta != 0 || iResposta <= listaRespostas.length){
        Resposta resposta = arqRespostas.read(listaRespostas[iResposta - 1]);

        if(resposta.isAtiva()){
          System.out.println("\nResposta:");
          printResposta(resposta);
          String respostaAlt;
          do {
            System.out.print("Digite a resposta alterada:");
            respostaAlt = sc.nextLine();
          } while (respostaAlt.length() == 0);

          if (respostaAlt.length() != 0) {
            System.out.println("\nConfirme seus dados:");
            System.out.println("resposta: " + respostaAlt);
            System.out.print("Confirmar? (Y/N) ");
            if (sc.nextLine().toUpperCase().equals("Y")) {
              resposta.setResposta(respostaAlt);

              arqRespostas.update(resposta);
              System.out.println("\nresposta atualizada com sucesso.\n");
            } else
              System.out.println("\nAtualização de resposta cancelada.\n");
          } else
            System.out.println("\nAtualização vazia.\n");

        }
        else
          System.out.println("\nResposta arquivada não pode ser alterada.\n");        
      }
      else{
        System.out.println("\nOpção invalida.\n");
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void arquivarResposta(Scanner sc) {
    try{
      int[] listaRespostas = listarRespostas(sc);

      System.out.println("\nDigite o número da resposta que queira arquivar.");
      System.out.println("\n0) Retornar para o Gerenciamento de Respostas.");
      System.out.print("\nOpcao:");
      int iResposta = sc.nextInt();
      sc.nextLine();
      if(iResposta != 0 || iResposta <= listaRespostas.length){
        Resposta resposta = arqRespostas.read(listaRespostas[iResposta - 1]);

        System.out.println("\nConfirme seus dados:");
        System.out.println("Resposta: ");
        printResposta(resposta);
        System.out.print("Confirmar? (Y/N) ");
        if (sc.nextLine().toUpperCase().equals("Y")) {
          resposta.setAtiva(false);

          arqRespostas.update(resposta);
          arvB_PerguntaResposta.delete(perguntaAtual, resposta.getId());
          arvB_UsuarioResposta.delete(usuarioAtual, resposta.getId());
          System.out.println("\nResposta arquivada com sucesso.\n");
        } else
          System.out.println("\nArquivamento de resposta cancelada.\n");
      }
      else{
        System.out.println("\nOpção invalida.\n");
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void gerenciarRespostas(Scanner sc, Pergunta p) throws Exception{
    menuRespostas();

    printPerguntaExibida(p);

    menuGerenciarRespostas();
    int opcode = sc.nextInt();
    sc.nextLine();
    switch (opcode) {
    case 0:
      System.out.println("\nVoltando para o menu anterior...");
      break;
    case 1:
      listarRespostas(sc);
      break;
    case 2:
      alterarResposta(sc);
      break;
    case 3:
      arquivarResposta(sc);
      break;
    default:
      System.out.println("\nEm breve...");
    }
  }

  public static void gerenciarTodasRespostas(Scanner sc){}

  public static int[] listarTodasRespostas(Scanner sc){
    int[] arrayIdRespostas = new int[0];

    System.out.println("RESPOSTAS");
    System.out.println("---------\n");
    try{
      arrayIdRespostas = arvB_PerguntaResposta.read(perguntaAtual);

      Resposta r;
      for (int i = 0; i < arrayIdRespostas.length; i++) {
        r = arqRespostas.read(arrayIdRespostas[i]);
        if (r.isAtiva()) {
          System.out.println(i + 1 + ".");
          printResposta(r);
        }
      }
      if (arrayIdRespostas.length == 0)
        System.out.println("Nenhuma resposta disponivel\n");         
    }
    catch(Exception e){
      e.printStackTrace();
    }

    return arrayIdRespostas;
  }

  public static int[] listarRespostas(Scanner sc){
    int[] arrayIdRespostas = new int[0];
    int[] arrayIdRespostasSelecionadas = new int[0];
    try{
      arrayIdRespostas = arvB_PerguntaResposta.read(perguntaAtual);

      Resposta r;
      for (int i = 0; i < arrayIdRespostas.length; i++) {
        r = arqRespostas.read(arrayIdRespostas[i]);
        if (r.isAtiva() && r.getIdUsuario() == usuarioAtual) {
          arrayIdRespostasSelecionadas = VetorIntVariavel(arrayIdRespostas, arrayIdRespostas[i]);
          System.out.println(i + 1 + ".");
          printResposta(r);
        }
      }
      if (arrayIdRespostas.length == 0)
        System.out.println("\nNenhuma resposta disponivel");         
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return arrayIdRespostasSelecionadas;
  }

  // -------------Avaliacao-------------

  public static void avaliacao(Scanner sc){
    try{
      int op = -1;
      do{
        menuAvaliacao();
        op = sc.nextInt();;
        switch (op) {
          case 0:
            System.out.println("\nVoltando a Pergunta...");
            break;
          case 1:
            avaliarVoto(sc,'P');
            break;
          case 2:
            System.out.println("\n");
            int[] idRespostas = listarTodasRespostas(sc);
            System.out.println("TESTE");
            for(int i = 0; i < idRespostas.length; i++){
              System.out.println(idRespostas[i]);
            }
            if(idRespostas.length > 0){
              System.out.println("Selecione a resposta pelo número:");
              int iResposta = sc.nextInt();
              sc.nextLine();
              if(iResposta != 0 || iResposta <= idRespostas.length){
                Resposta resposta = arqRespostas.read(idRespostas[iResposta - 1]);
                System.out.println(idRespostas.length);
                respostaAtual = resposta.getId();

                avaliarVoto(sc,'R');
              }
            }

            break;
          default:
            System.out.println("\nValor invalido! Digite novamente");
        }
      }while(op != 0);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void avaliarVoto(Scanner sc, char tipo){
    try{  
      int[] arrayIdEntidadesVotadas = (tipo == 'P') ? arvB_VotoPergunta.read(usuarioAtual) :
                                                      arvB_VotoResposta.read(usuarioAtual);
      boolean existe = false;
      int entidadeAtual = (tipo == 'P') ? perguntaAtual : respostaAtual;
      for(int i = 0; i < arrayIdEntidadesVotadas.length; i++){
        if(arrayIdEntidadesVotadas[i] == entidadeAtual){
          i = arrayIdEntidadesVotadas.length;
          existe = true;
        }
      }

      if(!existe){ 
        int opVoto;
        boolean valorVoto = true;
        do{
          System.out.println("\nQual o seu voto?\n1) Like\n2) Deslike\n >> ");
          opVoto = sc.nextInt();
          sc.nextLine();
          if(opVoto == 1)
            valorVoto = true;
          else if(opVoto == 2)
            valorVoto = false;
          else{
            System.out.println("Valor inválido. Digite novamente.");
          }
        }while(opVoto != 1 && opVoto != 2);

        System.out.println("Confirmar voto "+ ((valorVoto) ? "positivo" : "negativo") + "? (Y/N)");
        if (sc.nextLine().toUpperCase().equals("Y")) {
          Voto voto = new Voto(usuarioAtual, tipo, entidadeAtual, valorVoto);

          if(tipo == 'P'){
            arqVotoPerguntas.create(voto);
            Pergunta pergunta = arqPerguntas.read(entidadeAtual);
            Integer temp = ((valorVoto) ? pergunta.getNota() + 1 : pergunta.getNota() - 1);
            short nota = temp.shortValue();
            pergunta.setNota(nota);
            arqPerguntas.update(pergunta);
            arvB_VotoPergunta.create(usuarioAtual, entidadeAtual);
          }
          else{
            arqVotoRespostas.create(voto);
            Resposta resposta = arqRespostas.read(entidadeAtual);
            Integer temp = (valorVoto) ? resposta.getNota() + 1 : resposta.getNota() -1;
            short nota = temp.shortValue();
            resposta.setNota(nota);
            arqRespostas.update(resposta);
            arvB_VotoResposta.create(usuarioAtual, entidadeAtual);
          }

          System.out.println("\nVotação processada! Voltando ao menu...");
        } else
          System.out.println("\nVotação cancelada.\n");
        
      }
      else{
        System.out.println("Isso já foi avaliado! Voltando para o menu...");
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  // ------------IndiceInvertido------------

  public static void criarIndInver(int id, String palavrasChave) throws Exception {
    String[] split = palavrasChave.split(";");
    for (int i = 0; i < split.length; i++) {
      indInver.create(split[i], id);
    }
  }

  public static void alterarIndInver(Pergunta pergunta, String palavrasChaveAlt) throws Exception {
    HashSet<String> keyWords = new HashSet<String>(), keyWordsAlt = new HashSet<String>(),
        keyWordsRemove = new HashSet<String>(), keyWordsAltRemove = new HashSet<String>();

    String[] splitKeyWords = pergunta.getPalavrasChave().split(";"), splitKeyWordsAlt = palavrasChaveAlt.split(";");

    for (String str : splitKeyWords) {
      keyWords.add(str);
    }
    for (String str : splitKeyWordsAlt) {
      keyWordsAlt.add(str);
    }

    keyWordsRemove = keyWords;
    keyWordsAltRemove = keyWordsAlt;

    keyWordsRemove.removeAll(keyWordsAlt);
    keyWordsAltRemove.removeAll(keyWords);

    for (String str : keyWordsRemove) {
      indInver.delete(str, pergunta.getId());
    }
    for (String str : keyWordsAltRemove) {
      indInver.create(str, pergunta.getId());
    }

    pergunta.setPalavrasChave(palavrasChaveAlt);
  }

  // -----------Funcoes Gerais-----------

  public static int[] VetorIntVariavel(int[] vetor, int valor) {
    int[] NovoVetor = null;
    int tamVetor = vetor.length;
    if (vetor == null || vetor.length <= 1) {
      System.out.println("valor "+valor);
      NovoVetor = new int[1];
      NovoVetor[0] = valor;
    } else {
      System.out.println("valor "+valor);

      NovoVetor = new int[tamVetor + 1];
      NovoVetor[tamVetor] = valor;
    }
    return NovoVetor;
  }

  public static String formatarPalavrasChave(String palavrasChave) {
    palavrasChave = Normalizer.normalize(palavrasChave, Normalizer.Form.NFD);
    palavrasChave = palavrasChave.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

    palavrasChave = palavrasChave.toLowerCase().trim();
    return palavrasChave;
  }

  public static void printPerguntaResumido(Pergunta p) {
    if (p.isAtiva()) {
      System.out.println("\n" + p.getPergunta());
      System.out.println("Palavras-chaves: " + p.getPalavrasChave());
      System.out.println("Nota: " + p.getNota() + "\n");
    }
  }

  public static void printPerguntaExibida(Pergunta p) throws Exception {
    menuPadrao();
    System.out.println("> PERGUNTAS\n");
    System.out.println("+---------------------------------------------------------------------+");
    System.out.println(p.getPergunta());
    System.out.println("+---------------------------------------------------------------------+");
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
    calendar.setTimeInMillis(p.getCriacao());
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Usuario u = arqPessoas.read(p.getIdUsuario());
    System.out.println("Criada em " + formatter.format(calendar.getTime()) + " por " + u.getNome());

    System.out.println("Palavras chave: " + p.getPalavrasChave());
    System.out.println("Nota: " + p.getNota() + "\n");

  }

  public static void printResposta(Resposta r) throws Exception{
    System.out.println(r.getResposta());
    System.out.print("Respondido em ");

    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
    calendar.setTimeInMillis(r.getCriacao());
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    Usuario u = arqPessoas.read(r.getIdUsuario());

    System.out.println(formatter.format(calendar.getTime()) + " por "+ u.getNome());
    System.out.println("Nota: "+r.getNota()+"\n");
  }

  public static void printPergunta(Pergunta p) {
    System.out.print("\n");
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
    calendar.setTimeInMillis(p.getCriacao());
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    System.out.print(formatter.format(calendar.getTime()));
    if (!p.isAtiva())
      System.out.print(" (ARQUIVADA)");
    System.out.println("\n" + p.getPergunta());
    System.out.println("Palavras-chaves: " + p.getPalavrasChave());
    System.out.println("Nota: " + p.getNota() +"\n");
  }
}
