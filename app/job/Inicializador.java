package job;

import models.TipoUsuario;
import models.Usuario;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {

    @Override
    public void doJob() throws Exception {
        if (Usuario.count() == 0) {
            Usuario user1 = new Usuario("ADM1", "ADM1", "adm1@adm.com", "7777-7777", TipoUsuario.ADM);
            user1.save();
            Usuario user2 = new Usuario("ADM2", "ADM2", "adm2@adm.com", "8888-8888", TipoUsuario.ADM);
            user2.save();
            Usuario user3 = new Usuario("ADM3", "ADM3", "adm3@adm.com", "9999-9999", TipoUsuario.ADM);
            user3.save();
        }
    }

}
