import java.io.*;

public class Voto implements Registro{
  private int idVoto;
  private int idUsuario;
  private char tipo;
  private int idPR;
  private boolean voto;

  public Voto(){
    this(-1,'-',-1,false);
  }

  public Voto(int idUsuario, char tipo, int idPR, boolean voto){
    this.idVoto = -1;
    this.idUsuario = idUsuario;
    this.tipo = tipo;
    this.idPR = idPR;
    this.voto = voto;
  }

  public Voto(int idVoto, int idUsuario, char tipo, int idPR, boolean voto){
    this.idVoto = idVoto;
    this.idUsuario = idUsuario;
    this.tipo = tipo;
    this.idPR = idPR;
    this.voto = voto;
  }

  public int getId(){
    return idVoto;
  }

  public void setId(int id){
    this.idVoto = id;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeInt(idVoto);
    dos.writeInt(idUsuario);
    dos.writeChar(tipo);
    dos.writeInt(idPR);
    dos.writeBoolean(voto);
    
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    
    this.idVoto = dis.readInt();
    this.idUsuario = dis.readInt();
    this.tipo = dis.readChar();
    this.idPR = dis.readInt();
    this.voto = dis.readBoolean();

  }
}