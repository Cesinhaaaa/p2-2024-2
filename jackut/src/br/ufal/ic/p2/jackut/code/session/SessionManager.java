package br.ufal.ic.p2.jackut.code.session;

import br.ufal.ic.p2.jackut.code.user.User;
import br.ufal.ic.p2.jackut.exceptions.login.UserNotRegisteredException;

import java.util.Map;
import java.util.TreeMap;

/**
 * A classe SessionManager � respons�vel por gerenciar as sess�es de usu�rios dentro da aplica��o.
 * Ela atribui IDs de sess�o para os usu�rios, mant�m o controle de sess�es ativas
 * e fornece funcionalidades para gerenciar e interagir com essas sess�es.
 */
public class SessionManager {
    private Map<String, User> sessionsOn;
    private Integer sessionIdCount;

    /**
     * Constr�i uma nova inst�ncia de {@code SessionManager}.
     *
     * Este construtor inicializa uma estrutura para gerenciar as sess�es de usu�rios ativas
     * e define o contador de IDs de sess�o para zero. A estrutura de dados usada para gerenciar
     * as sess�es permite o rastreamento eficiente e a recupera��o de sess�es de usu�rios ativos.
     */
    public SessionManager() {
        this.sessionsOn = new TreeMap<String, User>();
        this.sessionIdCount = 0;
    }

    /**
     * Limpa todas as sess�es de usu�rios ativas e reseta o contador de IDs de sess�o.
     *
     * Este m�todo remove todas as entradas da estrutura interna de rastreamento de sess�es, encerrando,
     * efetivamente, todas as sess�es existentes. O contador de IDs de sess�o � redefinido para zero,
     * permitindo que novas sess�es comecem a partir de um estado inicial. Isso � usado, normalmente,
     * quando os dados das sess�es precisam ser completamente apagados ou reinicializados dentro da aplica��o.
     */
    public void clearSessions() {
        this.sessionsOn.clear();
        this.sessionIdCount = 0;
    }

    /**
     * Cria uma nova sess�o para o usu�rio especificado, atribui um ID de sess�o �nico
     * e rastreia a sess�o no sistema de gerenciamento de sess�es.
     *
     * @param user o usu�rio para quem a sess�o est� sendo criada
     * @return o ID �nico da sess�o atribu�do ao usu�rio
     */
    public String newSession(User user) {
        this.sessionIdCount++;
        String strId = this.sessionIdCount.toString();
        this.sessionsOn.put(strId, user);
        return strId;
    }

    /**
     * Verifica se uma sess�o com o ID de sess�o fornecido est� atualmente ativa.
     *
     * @param id o identificador �nico da sess�o a ser verificada
     * @return true se a sess�o estiver ativa e existir no mapa de sess�es atual, false caso contr�rio
     */
    public boolean sessionIsOnline(String id) {
        return this.sessionsOn.containsKey(id);
    }

    /**
     * Recupera o usu�rio associado ao ID de sess�o fornecido, caso a sess�o esteja ativa.
     *
     * @param id o ID de sess�o associado ao usu�rio que ser� recuperado
     * @return o objeto User associado ao ID de sess�o fornecido
     * @throws UserNotRegisteredException se nenhum usu�rio estiver associado ao ID de sess�o fornecido ou se a sess�o estiver inativa
     */
    public User getUserBySessionId(String id) throws UserNotRegisteredException {
        if (this.sessionIsOnline(id)) {
            return this.sessionsOn.get(id);
        }
        throw new UserNotRegisteredException();
    }

    /**
     * Sess�o a ser removida do gerenciador.
     *
     * @param sessionId ID da sess�o a ser removida.
     */
    public void removeSession(String sessionId) {
        this.sessionsOn.remove(sessionId);
    }
}