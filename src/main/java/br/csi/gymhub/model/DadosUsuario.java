package br.csi.gymhub.model;

public record DadosUsuario(Long id, String login, String permissao) {
    public DadosUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getPermissao());
    }
}
