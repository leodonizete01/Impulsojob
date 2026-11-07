package impulsojob.service;

import impulsojob.config.SessaoUsuario;
import impulsojob.dao.CurriculoDAO;
import impulsojob.model.Curriculo;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CurriculoService {

    private final CurriculoDAO curriculoDAO;

    public CurriculoService() {
        this.curriculoDAO = new CurriculoDAO();
    }

    public CurriculoService(CurriculoDAO curriculoDAO) {
        this.curriculoDAO = curriculoDAO;
    }

    public void salvar(Curriculo curriculo) throws Exception {
        if (!SessaoUsuario.isAutenticado()) {
            throw new IllegalStateException("Nenhum usuário conectado.");
        }

        if (curriculo == null) {
            throw new IllegalArgumentException("Currículo inválido.");
        }

        if (curriculo.getNome() == null || curriculo.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe um título ou nome para identificar seu currículo.");
        }

        curriculo.setClienteId(SessaoUsuario.getUsuarioLogado().getId());

        if (curriculo.getId() > 0) {
            curriculoDAO.atualizar(curriculo);
        } else {
            curriculoDAO.cadastrar(curriculo);
        }
    }

    public Curriculo buscarPorId(int id) throws Exception {
        if (!SessaoUsuario.isAutenticado()) {
            throw new IllegalStateException("Nenhum usuário conectado.");
        }
        return curriculoDAO.buscarPorId(id, SessaoUsuario.getUsuarioLogado().getId());
    }

    public List<Curriculo> listarDoUsuarioLogado() throws Exception {
        if (!SessaoUsuario.isAutenticado()) {
            return Collections.emptyList();
        }
        return curriculoDAO.listarPorCliente(SessaoUsuario.getUsuarioLogado().getId());
    }

    public void excluir(int id) throws Exception {
        if (!SessaoUsuario.isAutenticado()) {
            throw new IllegalStateException("Nenhum usuário conectado.");
        }
        curriculoDAO.excluir(id, SessaoUsuario.getUsuarioLogado().getId());
    }
}
