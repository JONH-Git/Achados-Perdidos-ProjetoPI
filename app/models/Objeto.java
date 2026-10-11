package models;

import play.data.validation.InPast;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Objeto extends Model {

    @Required
    @MinSize(value = 4, message = "O nome deve ter, no mínimo, 4 caracteres!")
    public String nome;

    @Required
    @MinSize(value = 10, message = "A descrição deve ter, no mínimo, 10 caracteres!")
    public String descricao;

    @Required
    @MinSize(value = 10, message = "A descrição do loca ter, no mínimo, 10 caracteres!")
    public String local;

    @InPast(message = "A data que o objeto foi encontrado deve estar no passado!")
    public Date dataAchado;

    @Enumerated(EnumType.STRING)
    public Status status;

    @Enumerated(EnumType.STRING)
    public StatusObjeto statusObjeto;

    @ManyToOne
    public Usuario usuario;

    public Objeto() {
        this.status = Status.ATIVO;
        this.statusObjeto = StatusObjeto.REGISTRADO;
    }

    public void devolver() {
        this.statusObjeto = StatusObjeto.DEVOLVIDO;
        this.save();
    }
}