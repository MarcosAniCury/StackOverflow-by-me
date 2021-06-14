import java.io.IOException;

public interface Registro 
{
  public int getId();
  public void setId(int n);
  public byte[] toByteArray() throws IOException;
  public void fromByteArray(byte[] ba) throws IOException; 
  public String toString();
}