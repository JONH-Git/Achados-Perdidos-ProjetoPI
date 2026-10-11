package controllers;

import models.Status;
import models.TipoUsuario;
import models.Usuario;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;
import java.util.Objects;

@With(Secure.class)
public class Usuarios extends Controller {

    public static void form() {
        render();
    }

    public static void salvar(@Valid Usuario usuario) {

        if (usuario.tipoUsuario != TipoUsuario.USUARIO) {
            flash.error("Permissão negada!");
            renderTemplate("Usuarios/form.html", usuario);
        }

        if (Validation.hasErrors()) {
            flash.error("Falha no cadastro!");
            renderTemplate("Usuarios/form.html", usuario);
        }

        if (usuario.id != null) {
            flash.success("Informações atualizadas!");
        } else {
            flash.success("Boas vindas, " + usuario.nome + "!");
            flash.put("info", "Realize o login!");
        }

        usuario.save();
        Login.form();
    }

    public static void perfil() {
        Long id = Long.valueOf(session.get("usuarioId"));
        Usuario usuario = Usuario.findById(id);
        render(usuario);
    }

    public static void listar(String filtro) {
        List<Usuario> usuarios;
        if (filtro != null && !filtro.trim().isEmpty()) {
            String termo = "%" + filtro.toLowerCase() + "%";
            usuarios = Usuario.find("status like ?1 and lower(nome) like ?2", Status.ATIVO, termo).fetch();
        } else {
            usuarios = Usuario.find("status = ?1", Status.ATIVO).fetch();
        }

        render(usuarios, filtro);
    }

    public static void editar(long id) {
        Usuario usuario = Usuario.find("id = ?1 and status = ?2", id, Status.ATIVO).first();
        Usuario usuLogado = Usuario.findById(Long.parseLong(session.get("usuarioId")));

        if (usuario == null) {
            flash.put("info", "Usuário não encontrado!");
            listar(null);
        } else if (usuLogado.tipoUsuario != TipoUsuario.ADM && id != usuLogado.getId()) {
            flash.error("O usuário não tem permissão para esta operação!");
            listar(null);
        }

        renderTemplate("Usuarios/form.html", usuario);
    }

    public static void remover(long id) {
        Usuario usuario = Usuario.find("id = ?1 and status = ?2", id, Status.ATIVO).first();
        Usuario usuLogado = Usuario.findById(Long.parseLong(session.get("usuarioId")));

        if (usuario == null) {
            flash.put("info", "Usuário não encontrado!");
            listar(null);
        } else if (usuLogado.id == id) {
            usuario.status = Status.INATIVO;
            usuario.save();
            flash.put("info", "Usuario removido!");
            Login.deslogar();
        } else if (usuLogado.tipoUsuario == TipoUsuario.ADM) {
            usuario.status = Status.INATIVO;
            usuario.save();
            flash.put("info", "Usuario removido!");
            listar(null);
        } else {
            flash.error("O usuário não tem permissão para esta operação!");
            listar(null);
        }
    }
}