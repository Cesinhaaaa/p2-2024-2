package br.ufal.ic.p2.jackut.code.session;

import br.ufal.ic.p2.jackut.code.user.User;
import br.ufal.ic.p2.jackut.exceptions.login.UserNotRegisteredException;

import java.util.Map;
import java.util.TreeMap;

/**
 * A classe SessionManager é responsável por gerenciar as sessões de usuários dentro da aplicação.
 * Ela atribui IDs de sessão para os usuários, mantém o controle de sessões ativas
 * e fornece funcionalidades para gerenciar e interagir com essas sessões.
 */
public class SessionManager {
    private Map<String, User> sessionsOn;
    private Integer sessionIdCount;

    /**
     * Constrói uma nova instância de {@code SessionManager}.
     *
     * Este construtor inicializa uma estrutura para gerenciar as sessões de usuários ativas
     * e define o contador de IDs de sessão para zero. A estrutura de dados usada para gerenciar
     * as sessões permite o rastreamento eficiente e a recuperação de sessões de usuários ativos.
     */
    public SessionManager() {
        this.sessionsOn = new TreeMap<String, User>();
        this.sessionIdCount = 0;
    }

    /**
     * Limpa todas as sessões de usuários ativas e reseta o contador de IDs de sessão.
     *
     * Este método remove todas as entradas da estrutura interna de rastreamento de sessões, encerrando,
     * efetivamente, todas as sessões existentes. O contador de IDs de sessão é redefinido para zero,
     * permitindo que novas sessões comecem a partir de um estado inicial. Isso é usado, normalmente,
     * quando os dados das sessões precisam ser completamente apagados ou reinicializados dentro da aplicação.
     */
    public void clearSessions() {
        this.sessionsOn.clear();
        this.sessionIdCount = 0;
    }

    /**
     * Cria uma nova sessão para o usuário especificado, atribui um ID de sessão único
     * e rastreia a sessão no sistema de gerenciamento de sessões.
     *
     * @param user o usuário para quem a sessão está sendo criada
     * @return o ID único da sessão atribuído ao usuário
     */
    public String newSession(User user) {
        this.sessionIdCount++;
        String strId = this.sessionIdCount.toString();
        this.sessionsOn.put(strId, user);
        return strId;
    }

    /**
     * Verifica se uma sessão com o ID de sessão fornecido está atualmente ativa.
     *
     * @param id o identificador único da sessão a ser verificada
     * @return true se a sessão estiver ativa e existir no mapa de sessões atual, false caso contrário
     */
    public boolean sessionIsOnline(String id) {
        return this.sessionsOn.containsKey(id);
    }

    /**
     * Recupera o usuário associado ao ID de sessão fornecido, caso a sessão esteja ativa.
     *
     * @param id o ID de sessão associado ao usuário que será recuperado
     * @return o objeto User associado ao ID de sessão fornecido
     * @throws UserNotRegisteredException se nenhum usuário estiver associado ao ID de sessão fornecido ou se a sessão estiver inativa
     */
    public User getUserBySessionId(String id) throws UserNotRegisteredException {
        if (this.sessionIsOnline(id)) {
            return this.sessionsOn.get(id);
        }
        throw new UserNotRegisteredException();
    }

    /**
     * Sessão a ser removida do gerenciador.
     *
     * @param sessionId ID da sessão a ser removida.
     */
    public void removeSession(String sessionId) {
        this.sessionsOn.remove(sessionId);
    }
}