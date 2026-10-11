package models;

import play.data.validation.*;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Usuario extends Model {

    @Required
    @MinSize(value = 4, message = "O nome deve ter, no mínimo, 4 caracteres!")
    public String nome;

    @Required
    @MinSize(value = 6, message = "A senha deve ter, no mínimo, 6 caracteres!")
    public String senha;

    @Required
    @Email
    @Unique(message= "Este email já pertence a um usuário!")
    public String email;

    @Required
    public String turma;

    @Required
    @MinSize(value = 9, message = "O telefone deve ter, no mínimo, 9 digitos!")
    @MaxSize(value = 11, message = "O telefone deve ter, no máximo, 11 digitos!")
    @Unique
    public String telefone;

    @Enumerated(EnumType.STRING)
    public Status status;

    @Enumerated(EnumType.STRING)
    public TipoUsuario tipoUsuario;

    public Usuario() {
        this.status = Status.ATIVO;
        this.tipoUsuario = TipoUsuario.USUARIO;
    }

    public Usuario(String nome, String senha, String email, String telefone, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
        this.status = Status.ATIVO;
        this.tipoUsuario = tipoUsuario;
    }
}