/***
 * Esta classe representa o PAR CHAVE VALOR de um Usuario.
 * Chave: Email
 * Valor: Id
***/

import java.io.*;

public class pcvEmail implements RegistroHashExtensivel<pcvEmail> 
{
  private String chave; // email
  private int    valor; // id 
  private short TAMANHO = 34;

  public pcvEmail()
  {
    this("",-1);
  }

  public pcvEmail(String chave, int valor)
  {
    this.chave = chave;
    this.valor = valor; 
  }

  @Override
  public int hashCode() 
  {
    return this.chave.hashCode();
  }

  @Override
  public short size() 
  {
    return this.TAMANHO;
  }

  @Override
  public byte[] toByteArray() throws IOException 
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeUTF(this.chave); // Possibilidade de transformar criar um HashCode para email e armazenar apenas o hash (?)
    dos.writeInt(this.valor);
    byte[] ba = baos.toByteArray();
    byte[] ba2 = new byte[TAMANHO];
    for (int i = 0; i < TAMANHO; i++)
      ba2[i] = ' ';
    for (int i = 0; i < ba.length && i < TAMANHO; i++)
      ba2[i] = ba[i];
    return ba2;
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException 
  {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.chave = dis.readUTF();
    this.valor = dis.readInt();
  }

  public String getChave() 
  {
    return chave;
  }

  public void setChave(String chave) 
  {
    this.chave = chave;
  }

  public Integer getValor()
  {
    return getValorInt();
  }

  public Integer getValorInt() 
  {
    return valor;
  }

  public void setValor(int valor) 
  {
    this.valor = valor;
  }

  public Long getValorLong()
  {
    long o = 0;
    return o;
  }
}
