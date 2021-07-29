/***
 * Esta classe representa o PAR CHAVE VALOR de um Voto (Pergunta/Resposta)
 * Chave: idVoto
 * Valor: endereco
***/

import java.io.*;

public class pcvVoto implements RegistroHashExtensivel<pcvVoto> 
{
  private int chave; // idUsuario
  private long valor; // endereco 
  private short TAMANHO = 34;

  public pcvVoto()
  {
    this(-1,-1l);
  }

  public pcvVoto(int chave, long valor)
  {
    this.chave = chave;
    this.valor = valor; 
  }

  @Override
  public int hashCode() 
  {
    return this.chave;
  }

  @Override
  public short size() 
  {
    return this.TAMANHO;
  }

  public byte[] toByteArray() throws IOException 
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.chave); // Possibilidade de transformar criar um HashCode para Voto e armazenar apenas o hash (?)
    dos.writeLong(this.valor);
    byte[] ba = baos.toByteArray();
    byte[] ba2 = new byte[TAMANHO];
    for (int i = 0; i < TAMANHO; i++)
      ba2[i] = ' ';
    for (int i = 0; i < ba.length && i < TAMANHO; i++)
      ba2[i] = ba[i];
    return ba2;
  }

  public void fromByteArray(byte[] ba) throws IOException 
  {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.chave = dis.readInt();
    this.valor = dis.readLong();
  }

  public int getChave() 
  {
    return chave;
  }

  public void setChave(int chave) 
  {
    this.chave = chave;
  }

  public long getValor()
  {
    return valor;
  }

  public void setValor(long valor) 
  {
    this.valor = valor;
  }

  public Long getValorLong()
  {
    long o = 0;
    return o;
  }

  public Integer getValorInt(){
    return 0;
  }
}
