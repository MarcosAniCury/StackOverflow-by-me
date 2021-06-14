import java.io.*;
import java.lang.reflect.Constructor;
/***
Fim da Inclusão
Começo da classe CRUD
***/
public class CRUD<T extends Registro, T2 extends RegistroHashExtensivel<T2>>
{
  /***
  Declaração de variaveis
  ***/
  private RandomAccessFile raf;
  private Constructor<T> construtor;
  private Constructor<T2> construtorHashWithParams;
  private Constructor<T2> construtorHashWithoutParams;
  private HashExtensivel<T2> hash;
  private String fileString;
  /***
  Construtor
  ***/
  public CRUD(Constructor<T> construtor, Constructor<T2> construtorHashWithParams, Constructor<T2> construtorHashWithoutParams, String db_d, String db_c, String fileString) throws Exception 
  {
    this.construtor = construtor;
    this.construtorHashWithParams = construtorHashWithParams;
    this.construtorHashWithoutParams = construtorHashWithoutParams;
    this.fileString = fileString;
    this.hash = new HashExtensivel<>(this.construtorHashWithoutParams, 4, db_d, db_c);
  }
  /***
  Abertura de arquivo
  ***/
  public void openFile() throws IOException 
  {
    raf = new RandomAccessFile(this.fileString, "rw");
    if (raf.length() == 0)
      raf.writeInt(0);
  }
  /***
  Fechamento de arquivo
  ***/
  public void closeFile() throws IOException 
  {
    raf.close();
  }
  /*** 
  Criação do registro no arquivo sequencial.
  * @param object
  * @return Id do registro
  * @throws Exception
  ***/
  public int create(T object) throws Exception 
  {
    int ultimoId;
    //Abre o arquivo no inicio do mesmo.
    openFile();
    raf.seek(0);
    //Lê o últimoId e atribui o novo id ao objeto.
    ultimoId = raf.readInt();
    object.setId(ultimoId + 1);
    //Atualiza o ultimoId.
    raf.seek(0);
    raf.writeInt(object.getId()); 
    //Vai para o final do arquivo e escreve o registro.
    long endereco = raf.length();
    raf.seek(endereco);
    createRegistro(object);
    hash.create(construtorHashWithParams.newInstance(object.getId(), endereco));     
    closeFile();
    return object.getId();
  }
  /*** 
  Lê um registro no arquivo sequencial através de um Id. 
  * @param id
  * @return registro
  * @throws Exception
  ***/
  public T read(int id) throws Exception
  {
    T objeto = null;
    byte[] ba;
    int tam;
    boolean isDeleted;
    try
    {
      //Lê-se o hash procurando pelo endereco do id.
      T2 pcv = hash.read(Integer.valueOf(id).hashCode());
      openFile();
      //Caso o endereço exista e seja diferente de -1, prosseguir.
      if(pcv != null && !pcv.getValorLong().equals(-1))
      {
        objeto = this.construtor.newInstance();
        //Pular no arquivo para o endereço salvo no indice.
        raf.seek(pcv.getValorLong());     
        //Lê-se a lápide.
        isDeleted = raf.readBoolean();    
        //Caso o registro não tenha sido deletado, prossiga.
        if (!isDeleted) 
        {
          tam = raf.readInt();
          //Lê se o registro e o transforma em objeto.
          ba = new byte[tam];
          raf.read(ba);
          objeto.fromByteArray(ba);
          //If de segurança, para caso o indice falhe.
          if (objeto.getId() != id) 
          {
            objeto = null;
          }
        } 
        else 
        {
          objeto = null;
        }
      }
      closeFile();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return objeto;
  }
  /*** 
  Atualiza um registro no arquivo sequencial. 
  * @param registro
  * @return true caso tenha sucesso.
  * @throws Exception
  */
  public boolean update(T novoObjeto) throws Exception 
  {
    boolean resp = false, isDeleted;
    T objeto = this.construtor.newInstance();
    byte[] ba,novoBa;
    long pos;
    int tam;
    try
    {
      //Lê-se o hash procurando pelo endereco do id.
      T2 pcv = hash.read(Integer.valueOf(novoObjeto.getId()).hashCode());
      openFile();
      //Caso o endereço exista e seja diferente de -1, prosseguir.
      if(pcv != null && !pcv.getValorLong().equals(-1))
      {
        //Pular no arquivo para o endereço salvo no indice.
        raf.seek(pcv.getValorLong());
        objeto = this.construtor.newInstance();
        //Guarda a posição no arquivo e lê a lapide.
        pos = raf.getFilePointer();
        isDeleted = raf.readBoolean();
        //Caso o registro não tenha sido deletado, prossiga.
        if (!isDeleted) 
        {
          tam = raf.readInt();
          //Lê se o registro e o transforma em objeto.
          ba = new byte[tam];
          raf.read(ba);
          objeto.fromByteArray(ba);
          //Pega o objeto atualizado e o transforma em byte array.
          novoBa = novoObjeto.toByteArray();
          //If de segurança, para caso o indice falhe.
          if (objeto.getId() == novoObjeto.getId()) 
          {
            if (novoBa.length <= ba.length) 
            {
              raf.seek(pos+5); 
              //createRegistro(novoObjeto);
              raf.write(novoBa);
            }
            else 
            {
              //Deleta o registro.
              raf.seek(pos); 
              raf.writeBoolean(true);
              //Cria um novo registro no final.
              pos = raf.length();
              raf.seek(pos);
              createRegistro(novoObjeto);
              //Atualiza o indíce.
              resp = hash.update(construtorHashWithParams.newInstance(novoObjeto.getId(), pos));
            }
          }
        } 
      }
      closeFile();
    }
    catch(Exception e)
    {
      System.out.println("Erro: [ "+e.getCause()+" ]");
    }
    return resp;
  } 
  /*** 
  Deleta um registro no arquivo sequencial. 
  * @param id
  * @return true caso tenha sucesso.
  * @throws Exception
  ***/
  public boolean delete(int id) throws Exception 
  {
    boolean resp = false, isDeleted;
    T objeto = this.construtor.newInstance();
    byte[] ba;
    int tam;
    try
    {
      //Lê-se o hash procurando pelo endereco do id.
      T2 pcv = hash.read(id);
      openFile();
      //Caso o endereço exista e seja diferente de -1, prosseguir.
      if(pcv != null && pcv.getValorLong() != -1)
      {
        //Pula para o endereco do objeto. E armazena o endereço. 
        raf.seek(pcv.getValorLong());
        objeto = this.construtor.newInstance();
        //Lê-se a lápide
        isDeleted = raf.readBoolean();
        //Caso o registro não tenha sido deletado, prossiga.
        if (!isDeleted) 
        {
          tam = raf.readInt();
          //Lê se o registro e o transforma em objeto.
          ba = new byte[tam];
          raf.read(ba);
          objeto.fromByteArray(ba);
          //If de segurança, para caso o indice falhe.
          if (objeto.getId() == id) 
          {
            //Altera a lápide, deletando o registro.
            raf.seek(pcv.getValorLong());
            raf.writeBoolean(true);
            //Deleta o indice no hash
            resp = hash.delete(id);
          }
        }
      }
      closeFile();
    }
    catch(Exception e)
    {
      System.out.println("Erro: [ "+e.getCause()+" ]");
    } 
    return resp;
  }
  //Metódo para criar registro.
  public void createRegistro(T objeto) throws Exception
  {
    if(raf != null)
    {
      raf.writeBoolean(false);
      byte[] ba = objeto.toByteArray();
      raf.writeInt(ba.length);
      raf.write(ba);
    }
  }
}