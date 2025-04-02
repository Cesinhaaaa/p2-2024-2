package br.ufal.ic.p2.jackut.code;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MessageManager é uma classe utilitária que gerencia uma lista de mensagens,
 * permitindo adicionar e recuperar mensagens. Implementa a interface Serializable
 * para serialização de objetos.
 */
public class MessageManager implements Serializable {
    private List<String> messages;

    /**
     * Constrói uma nova instância da classe MessageManager.
     * Inicializa uma lista interna para armazenar mensagens.
     */
    public MessageManager() {
        this.messages = new ArrayList<String>();
    }

    /**
     * Lê e remove a primeira mensagem da lista de mensagens, se disponível.
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
     * Adiciona uma mensagem à lista interna de mensagens.
     *
     * @param message a mensagem a ser adicionada à lista
     */
    public void receive(String message) {
        this.messages.add(message);
    }
}