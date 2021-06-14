import java.util.Calendar;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.io.*;

public class Resposta implements Registro
{
  private int idResposta;
  private int idPergunta;
  private int idUsuario;
  private long criacao;
  private short nota;
  private String resposta;
  private boolean ativa;

  public Resposta() { this(-1,-1,-1,""); }

  public Resposta(int idResposta,int idPergunta, int idUsuario, String resposta)
  {
    this.idResposta = idResposta;
    this.idPergunta = idPergunta;
    this.idUsuario = idUsuario;
    this.criacao = -1;
    this.nota = 0;
    this.resposta = resposta;
    this.ativa = true;
  }

  @Override
  public byte[] toByteArray() throws IOException
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(idResposta);
    dos.writeInt(idPergunta);
    dos.writeInt(idUsuario);
    dos.writeLong(criacao);
    dos.writeShort(nota);
    dos.writeBoolean(ativa);
    dos.writeUTF(resposta);
    return baos.toByteArray();
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException
  {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais); 
    this.idResposta = dis.readInt(); 
    this.idPergunta = dis.readInt();
    this.idUsuario = dis.readInt();
    this.criacao = dis.readLong();
    this.nota = dis.readShort();
    this.ativa = dis.readBoolean();
    this.resposta = dis.readUTF();
  }

  public void setId(int id)
  {
    this.idResposta = id;
  }

  public int getId()
  {
    return idResposta;
  }

  public void setIdPergunta(int idPergunta)
  {
    this.idPergunta = idPergunta;
  }

  public int getIdPergunta()
  {
    return idPergunta;
  }

  public void setIdUsuario(int idUsuario)
  {
    this.idUsuario = idUsuario;
  }

  public int getIdUsuario()
  {
    return idUsuario;
  }

  public void setCriacao(long criacao) 
  {
    this.criacao = criacao;
  }

  public void setCriacao() 
  {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
    this.criacao = calendar.getTimeInMillis();
  }

  public long getCriacao() 
  {
    return criacao;
  }

  public short getNota() 
  {
    return nota;
  }
  
  public void setNota(short nota) 
  {
    this.nota = nota;
  }

  public boolean isAtiva(){
    return this.ativa;
  }

  public void setAtiva(boolean ativa){
    this.ativa = ativa;
  }

  public String getResposta() 
  {
    return resposta;
  }

  public void setResposta(String resposta) 
  {
    this.resposta = resposta;
  }
}