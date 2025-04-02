package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MessageManager � uma classe utilit�ria que gerencia uma lista de mensagens,
 * permitindo adicionar e recuperar mensagens. Implementa a interface Serializable
 * para serializa��o de objetos.
 */
public class MessageManager implements Serializable {
    private List<String> messages;

    /**
     * Constr�i uma nova inst�ncia da classe MessageManager.
     * Inicializa uma lista interna para armazenar mensagens.
     */
    public MessageManager() {
        this.messages = new ArrayList<String>();
    }

    /**
     * L� e remove a primeira mensagem da lista de mensagens, se dispon�vel.
     *
     * @return a primeira mensagem da lista de mensagens
     * @throws NoMessageException se a lista de mensagens estiver vazia
     */
    public String read() throws NoMessageException {
        if (!this.messages.isEmpty()) {
            String message = this.messages.get(0);
            this.messages.remove(0);
            return message;
        } else {
            throw new NoMessageException();
        }
    }

    /**
     * Adiciona uma mensagem � lista interna de mensagens.
     *
     * @param message a mensagem a ser adicionada � lista
     */
    public void receive(String message) {
        this.messages.add(message);
    }
}