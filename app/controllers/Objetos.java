package controllers;

import models.*;
import org.hibernate.resource.beans.container.spi.BeanLifecycleStrategy;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;
import java.util.Objects;

@With(Secure.class)
public class Objetos extends Controller {

    public static void form() {
        render();
    }

    public static void salvar(@Valid Objeto objeto) {
        if (Validation.hasErrors()) {
            flash.error("Falha ao cadastrar o objeto!");
            renderTemplate("Objetos/form.html", objeto);
        }

        if (objeto.usuario == null) {
            objeto.usuario = Usuario.findById(Long.parseLong(session.get("usuarioId")));
        }

        objeto.save();
        flash.success("Objeto registrado com sucesso!");
        listar(null);
    }

    public static void listar(String filtro) {
        List<Objeto> objetos;

        if (filtro != null && !filtro.trim().isEmpty()) {
            String termo = "%" + filtro.toLowerCase() + "%";
            objetos = Objeto.find("lower(nome) like ?1 and status = ?2 and statusObjeto = ?3", termo, Status.ATIVO, StatusObjeto.REGISTRADO).fetch();
        } else {
            objetos = Objeto.find("status = ?1 and statusObjeto = ?2", Status.ATIVO, StatusObjeto.REGISTRADO).fetch();
        }

        render(objetos);
    }

    public static void devolver(Long id) {
        Objeto objeto = Objeto.find("id = ?1 and status = ?2", id, Status.ATIVO).first();
        Usuario usuario = Usuario.findById(Long.parseLong(session.get("usuarioId")));

        if (objeto == null) {
            flash.put("info", "Objeto não encontrado!");
        } else if (usuario.tipoUsuario == TipoUsuario.USUARIO && !Objects.equals(objeto.usuario.id, usuario.id)) {
            flash.error("O usuário não tem permissão para esta operação!");
        } else {
            objeto.devolver();
            flash.success("Objeto devolvido!");
        }

        listar(null);
    }

    public static void editar(long id) {
        Objeto objeto = Objeto.find("id = ?1 and status = ?2", id, Status.ATIVO).first();
        Usuario usuario = Usuario.findById(Long.parseLong(session.get("usuarioId")));

        if (objeto == null) {
            flash.put("info", "Objeto não encontrado!");
            listar(null);
        }

        if (usuario.tipoUsuario != TipoUsuario.ADM && !Objects.equals(objeto.usuario.id, usuario.id)) {
            flash.error("O usuário não tem permissão de editar este objeto!");
            listar(null);
        }

        renderTemplate("Objetos/form.html", objeto);
    }

    public static void remover(long id) {
        Objeto objeto = Objeto.find("id = ?1 and status = ?2", id, Status.ATIVO).first();
        Usuario usuLogado = Usuario.findById(Long.parseLong(session.get("usuarioId")));

        if (objeto == null) {
            flash.put("info", "Objeto não encontrado!");
            listar(null);
        }

        if (usuLogado.tipoUsuario != TipoUsuario.ADM && objeto.usuario.id != usuLogado.id) {
            flash.error("O usuário não tem permissão para remover este objeto!");
            listar(null);
        }

        objeto.status = Status.INATIVO;
        objeto.save();
        flash.put("info", "Objeto removido com sucesso!");
        listar(null);

    }
}