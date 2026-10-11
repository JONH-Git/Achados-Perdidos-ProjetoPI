package controllers;

import models.Status;
import models.TipoUsuario;
import models.Usuario;
import play.mvc.Controller;

public class Login extends Controller {

    public static void form() {
        render();
    }

    public static void logar(String email, String senha) {
        if (session.contains("usuarioId")) {
            flash.put("info", "O usuário já está logado!");
            Application.index();
        }

        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            flash.error("As credenciais devem ser informadas!");
            form();
        }

        Usuario usuario = Usuario.find("email = ?1 and senha = ?2 and status = ?3", email, senha, Status.ATIVO).first();

        if (usuario == null) {
            flash.error("Credenciais inválidas!");
            form();
        }

        session.put("usuarioId", usuario.id);
        session.put("username", usuario.nome);

        if (usuario.tipoUsuario == TipoUsuario.ADM) {
            session.put("ADM", true);
        }

        flash.success("Saudações, " + usuario.nome);

        Application.index();
    }

    public static void deslogar() {

        if (session.contains("usuarioId")) {
            flash.put("info", "Usuario deslogado");
        }

        session.clear();
        Application.index();
    }

}