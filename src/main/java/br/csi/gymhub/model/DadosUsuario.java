package br.csi.gymhub.model;

public record DadosUsuario(Long id, String nome, String login, String permissao) {
    public DadosUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPermissao());
    }
}