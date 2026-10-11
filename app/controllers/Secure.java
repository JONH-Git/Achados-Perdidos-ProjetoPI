package controllers;

import models.TipoUsuario;
import models.Usuario;
import play.mvc.Before;
import play.mvc.Controller;

public class Secure extends Controller {

    @Before(unless = {"Objetos.listar", "Usuarios.form", "Usuarios.salvar"}, priority = 1)
    static void autenticar() {
        if (!session.contains("usuarioId")) {
            flash.error("Login necessário!");
            Login.form();
        }
    }

    @Before(only = {"Usuarios.listar"}, priority = 2)
    static void verificarAdm() {
        Usuario usu = Usuario.findById(Long.parseLong(session.get("usuarioId")));
        if (usu.tipoUsuario == TipoUsuario.USUARIO) {
            flash.error("O usuário não tem permissão para esta operação!");
            Application.index();
        }
    }
}