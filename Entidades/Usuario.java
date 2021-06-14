import java.io.*;

public class Usuario implements Registro {
    private int id;
    private String nome;
    private String email;
    private int senha;

    private String perguntaSecreta;
    private String respostaSecreta;
    
    @Override
    public int getId(){
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(email);
        dos.writeInt(senha);
        dos.writeUTF(perguntaSecreta);
        dos.writeUTF(respostaSecreta);

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
        this.senha = dis.readInt();
        this.perguntaSecreta = dis.readUTF();
        this.respostaSecreta = dis.readUTF();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public String getPerguntaSecreta() {
        return perguntaSecreta;
    }

    public void setPerguntaSecreta(String perguntaSecreta) {
        this.perguntaSecreta = perguntaSecreta;
    }

    public String getRespostaSecreta() {
        return respostaSecreta;
    }

    public void setRespostaSecreta(String respostaSecreta) {
        this.respostaSecreta = respostaSecreta;
    }

    public Usuario(){
        this.id = -1;
        this.nome = "";
        this.email = "";
        this.senha = 0;
        this.perguntaSecreta = "";
        this.respostaSecreta = "";
    }

    public Usuario(int id, String nome, String email, int senha, String perguntaSecreta, String respostaSecreta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perguntaSecreta = perguntaSecreta;
        this.respostaSecreta = respostaSecreta;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + 
        senha + ", perguntaSecreta=" + perguntaSecreta + ", respostaSecreta="+ respostaSecreta +"]";
    }

    
    
}